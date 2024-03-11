import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static final String DatabaseName = "passwordmanager";
    static final String TableName = "userpasswords";
    static final String UserIdColumnName = "User_id";
    static final String UsernameColumnName = "username";
    static final String PasswordColumnName = "password";
    static List<Integer> userId = new ArrayList<>();
    static final Scanner scanner  = new Scanner(System.in);

    static void addUserUI(){
        System.out.println("please enter the username");
        String username = scanner.nextLine();
        System.out.println("Please enter the password");
        String password = scanner.nextLine();
        addNewUser(username,password);
        System.out.println("Enter y to add add another user");
        String again = scanner.nextLine();
        if (again.equals("y")){
            addUserUI();
        }
    }
    static void changePasswordUi(){
        System.out.println("Please input the username of the password you wish to update.");
        String username = scanner.nextLine();
        System.out.println("Please enter the new password.");
        String password = scanner.nextLine();
        updatePassword(username,password);


    }

    static void addNewUser(String username,String password){
        int newUserId = userId.get(userId.size()-1) + 1;
        String sqlStatement = "INSERT INTO `" + DatabaseName + "`.`" + TableName + "`(`"+UserIdColumnName+"`, `" + UsernameColumnName + "`,`"
                + PasswordColumnName + "`) VALUES (" + newUserId + ",'"+ username + "','" + password + "');" ;
        System.out.println(sqlStatement);
        executeStatement(sqlStatement);
        userId.add(newUserId);
    }
    static void updatePassword(String username,String newPassword){
        String sqlStatement = "UPDATE `"  + DatabaseName + "`.`" + TableName + "`" + " SET " + PasswordColumnName + " = '" + newPassword + "' WHERE " + UsernameColumnName + " = '" + username + "';";
        System.out.println(sqlStatement);
        executeStatement(sqlStatement);
    }



     static ResultSet executeQuery(String sqlQuery) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/passwordmanager", "liam", "password");
            Statement statement = connection.createStatement();
            return statement.executeQuery(sqlQuery);
        } catch (Exception e) {
            System.out.println("Well SADNESS HAPPENED AGAIN\n");
            e.printStackTrace();
        }



        return null;
    }
    static void executeStatement(String sqlQuery) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/passwordmanager", "USER", "PASSWORD");//these would be replaced with the actual user and password.
            Statement statement = connection.createStatement();
            statement.execute(sqlQuery);
        } catch (Exception e) {
            System.out.println("Well SADNESS HAPPENED AGAIN\n");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        //following line murders all the rows, so we can start fresh, optional.
            executeStatement("TRUNCATE TABLE " + TableName);

            ResultSet resultSet = executeQuery("select * from userpasswords");
            try {
                while (resultSet.next()) {
                    userId.add(resultSet.getInt(UserIdColumnName));
                }
            System.out.println(userId);
        }catch (Exception e) {
            e.printStackTrace();
        }
            userId.add(0);
            addUserUI();
            changePasswordUi();



    }
}