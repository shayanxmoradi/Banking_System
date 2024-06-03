package menu.login;

import entity.User;
import menu.SignUpMenu;
import menu.util.Input;
import menu.util.Message;
import util.ApplicationContext;

import java.sql.SQLException;

public class CardMenu {
    public static void show() throws SQLException {
        cardMenu:
        while (true) {
            System.out.println("""
                    1 -> Register a Card
                    2 -> Delete a Card
                    3 -> Show a Card base on name of Card
                    4 -> show Card of spesefic Bank base on Name of Bank
                    5 -> show all Cards
                    6 -> show Transaction with filter OMGGG
                    7 -> show all Transaction of Customer
                    8 -> previous Menu
                    """);

            switch (Input.scanner.next()) {
                case "1": {
                    //todo handle 7 operations here
                }
                case "2": {
                }
                case "3": {
                }
                case "4": {
                }
                case "5": {
                }
                case "6": {
                }
                case "7":

                case "8": {
                    break cardMenu;
                }
                default:
                    System.out.println(Message.getInvalidInputMessage());

            }

        }
    }
}
