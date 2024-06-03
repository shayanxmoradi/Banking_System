package menu.login;

import entity.User;
import menu.util.Input;
import menu.util.Message;
import util.ApplicationContext;

import java.sql.SQLException;

public class LoggedInMenu {
    public static void show() throws SQLException {
        loggedInMenu:
        while (true) {
            System.out.println("""
                    1 -> Card Operations Menu
                    2 -> Money Operations Menu
                    3 -> Bank(Account) Operations Menu
                    4 -> Previous Menu
                    """);

            switch (Input.scanner.next()) {
                case "1" : {
                   CardMenu.show();
                    break ;
                }
                case "2" : {
                    MoneyTransactionMenu.show();

                    break ;
                }
                case "3" : {
                    AccountMenu.show();
                    break;
                }
                case "4" : {
                    break loggedInMenu;
                }
                default : System.out.println(Message.getInvalidInputMessage());

            }

        }
    }
}
