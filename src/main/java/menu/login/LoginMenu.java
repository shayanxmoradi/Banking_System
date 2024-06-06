package menu.login;

import menu.util.Input;
import menu.util.Message;
import service.user.UserService;
import util.ApplicationContext;
import util.AuthHolder;

import java.sql.SQLException;

public class LoginMenu {
    private final Input INPUT;
    private final Message MESSAGE;
    private final UserService USER_SERVICE;
    private final AuthHolder AUTH_HOLDER;
    private final LoggedInMenu LOGGED_IN_MENU;

    public LoginMenu(Input input, Message message, UserService userService, AuthHolder AUTH_HOLDER, LoggedInMenu LOGGED_IN_MENU) {
        this.INPUT = input;
        this.MESSAGE = message;
        this.USER_SERVICE = userService;
        this.AUTH_HOLDER = AUTH_HOLDER;
        this.LOGGED_IN_MENU = LOGGED_IN_MENU;
    }

        public  void show() throws SQLException {
            login:
            while (true) {

                System.out.println("""
                    1 -> Enter Information
                    2 -> Previous Menu
                    """);
                switch (INPUT.scanner.next()) {
                    case "1": {
                        System.out.println(MESSAGE.getInputMessage("userName"));
                        String username = INPUT.scanner.next();
                        System.out.println(MESSAGE.getInputMessage("password"));
                        String password = INPUT.scanner.next();
                        if (USER_SERVICE.login(username, password)) {
                            System.out.println(MESSAGE.getSuccessfulMessage("login "));
                          //todo sub menu
                            LOGGED_IN_MENU.show();
                            //todo watchout
                            AUTH_HOLDER.reset();
                            break login;
                        }
                        System.out.println(MESSAGE.getNotFoundMessage(username));
                        break;
                    }
                    case "2": {
                        break login;
                    }
                    default:
                        System.out.println(MESSAGE.getInvalidInputMessage());
                }

            }


        }
}
