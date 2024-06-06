package menu;

import entity.User;
import menu.login.LoginMenu;
import menu.util.Input;
import menu.util.Message;
import service.user.UserService;
import util.ApplicationContext;

import java.sql.SQLException;

public class SignUpMenu {
    private final Input INPUT;
    private final Message MESSAGE;
    private final UserService USER_SERVICE;

    public SignUpMenu(Input input, Message message, UserService userService) {
        this.INPUT = input;
        this.MESSAGE = message;
        this.USER_SERVICE = userService;
    }

    public  void show() throws SQLException {
        signup:
        while (true) {
            System.out.println("""
                    1 -> Enter Information
                    2 -> Previous Menu
                    """);

            switch (INPUT.scanner.next()) {
                case "1" : {
                    System.out.println(MESSAGE.getInputMessage("Username"));
                    String username = INPUT.scanner.next();
                    System.out.println(MESSAGE.getInputMessage("password"));
                    String password = INPUT.scanner.next();
                    User user = new User(username, password);
                    if (USER_SERVICE.signUp(user)) {
                        System.out.println(MESSAGE.getSuccessfulMessage("sign up"));
                        break signup;
                    }
                    System.out.println(MESSAGE.getExistMessage("username"));
                    break ;
                }
                case "2" : {
                    break signup;
                }
                default : System.out.println(MESSAGE.getInvalidInputMessage());

            }

        }
    }
}
