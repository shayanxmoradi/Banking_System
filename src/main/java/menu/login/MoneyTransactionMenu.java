package menu.login;

import menu.util.Input;
import menu.util.Message;

import java.sql.SQLException;

public class MoneyTransactionMenu {
    public static void show() throws SQLException {
        moneyTransMenu:
        while (true) {
            System.out.println("""
                            1 -> Normal Money Transaction(Card to Card)
                            2 -> Do Bank Transfer(to Person)
                            3 -> Do Muliple Bank Transfer
                            4 -> Do Sepsical Bank Transfer(SATNA)
                            5 -> Pervious Menu
                    """);

            switch (Input.scanner.next()) {
                case "1" : {
                    //todo Normal Money Transaction(Card to Card)
                    break ;
                }
                case "2" : {
                    //todo  Do Bank Transfer(to Person)
                    break;
                }
                case "3":{

                    //todo Do Muliple Bank Transfer
                    break ;
                }
                case "4":{
                    //todo Do Sepsical Bank Transfer(SATNA)
                    break ;
                }
                case "5":{
                    break moneyTransMenu;
                }
                default : System.out.println(Message.getInvalidInputMessage());

            }

        }
    }
}
