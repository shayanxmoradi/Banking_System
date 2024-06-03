package menu;

import entity.User;
import menu.util.Input;
import menu.util.Message;
import util.ApplicationContext;

import java.sql.SQLException;

public class SignUpMenu {
    public static void show() throws SQLException {
        signup:
        while (true) {
            System.out.println("""
                    1 -> Enter Information
                    2 -> Previous Menu
                    """);

            switch (Input.scanner.next()) {
                case "1" : {
                    System.out.println(Message.getInputMessage("Username"));
                    String username = Input.scanner.next();
                    System.out.println(Message.getInputMessage("password"));
                    String password = Input.scanner.next();
                    User user = new User(username, password);
                    if (ApplicationContext.getInstance().getUserService().signUp(user)) {
                        System.out.println(Message.getSuccessfulMessage("sign up"));
                        break signup;
                    }
                    System.out.println(Message.getExistMessage("username"));
                    break ;
                }
                case "2" : {
                    break signup;
                }
                default : System.out.println(Message.getInvalidInputMessage());

            }

        }
    }
}
