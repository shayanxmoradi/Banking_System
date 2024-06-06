package menu.login;

import entity.User;
import menu.util.Input;
import menu.util.Message;
import service.user.UserService;
import util.ApplicationContext;
import util.AuthHolder;

import java.sql.SQLException;

public class LoggedInMenu {
    private final Input INPUT;
    private final Message MESSAGE;
    private final CardMenu CARD_MENU;
    private final MoneyTransactionMenu MONEY_TRANSACTION_MENU;
    private final AccountMenu ACCOUNT_MENU;

    public LoggedInMenu(Input input, Message message, CardMenu cardMenu, MoneyTransactionMenu transactionMenu, AccountMenu accountMenu) {
        this.INPUT = input;
        this.MESSAGE = message;
        this.CARD_MENU = cardMenu;
        this.MONEY_TRANSACTION_MENU = transactionMenu;
        this.ACCOUNT_MENU = accountMenu;
    }

    public  void show() throws SQLException {
        loggedInMenu:
        while (true) {
            System.out.println("""
                    1 -> Card Operations Menu
                    2 -> Money Operations Menu
                    3 -> Bank(Account) Operations Menu
                    4 -> Previous Menu
                    """);

            switch (INPUT.scanner.next()) {
                case "1" : {
                   CARD_MENU.show();
                    break ;
                }
                case "2" : {
                    MONEY_TRANSACTION_MENU.show();

                    break ;
                }
                case "3" : {
                    ACCOUNT_MENU.show();
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
