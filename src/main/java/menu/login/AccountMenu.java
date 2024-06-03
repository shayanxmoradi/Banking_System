package menu.login;

import entity.User;
import menu.util.Input;
import menu.util.Message;
import util.ApplicationContext;

import java.sql.SQLException;

public class AccountMenu {
    public static void show() throws SQLException {
        accountMenu:
        while (true) {
            System.out.println("""
                            1 -> Create Bank Account
                            2 -> Close Bank Account *WARNING*
                            3 -> Previous Menu
                    """);

            switch (Input.scanner.next()) {
                case "1" : {
            //todo create Bank Account
                    break ;
                }
                case "2" : {
                  //todo handle CLOSE BANK ACCOUNT
                    break;
                }
                case "3":{
                    break accountMenu;
                }
                default : System.out.println(Message.getInvalidInputMessage());

            }

        }
    }
}
