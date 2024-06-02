package menu;

import menu.util.Input;
import menu.util.Message;
import util.ApplicationContext;
import util.AuthHolder;

import java.sql.SQLException;

public class LoginMenu {

        public static void show() throws SQLException {
            login:
            while (true) {
                System.out.println("""
                    1 -> Enter Information
                    2 -> Previous Menu
                    """);
                switch (Input.scanner.next()) {
                    case "1": {
                        System.out.println(Message.getInputMessage("userName"));
                        String username = Input.scanner.next();
                        System.out.println(Message.getInputMessage("password"));
                        String password = Input.scanner.next();
                        if (ApplicationContext.getInstance().getUserService().login(username, password)) {
                            System.out.println(Message.getSuccessfulMessage("login "));
                          //todo sub menu
                            AuthHolder.reset();
                            break login;
                        }
                        System.out.println(Message.getNotFoundMessage(username));
                        break;
                    }
                    case "2": {
                        break login;
                    }
                    default:
                        System.out.println(Message.getInvalidInputMessage());
                }

            }


        }
}
