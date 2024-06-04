package menu.login;

import entity.CreditCard;
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
                    //todo handle Account
                    Long accocuntId = 1L;
                    System.out.println(Message.getInputMessage("Your Card number"));
                    String cardName = Input.scanner.next();
                    System.out.println(Message.getInputMessage("Your Card initial Balance"));
                    double balance = Input.scanner.nextDouble();
                    CreditCard card = new CreditCard(balance, accocuntId, cardName);
                    if (ApplicationContext.getInstance().getCardService().addCard(card)) {
                        System.out.println(Message.getSuccessfulMessage("Creating new Card"));
                       String cardDetail="""
                                Your created Card Information:
                                Card Nummber : %s
                                CCV2 : %s
                                Expire Date : %s
                                Balance : %s
                                """;
                        System.out.println(cardDetail.formatted(card.getCardNumber(),card.getCvv(),card.getExpiryDate(),card.getBalance()));

                        break;
                    }
                    System.out.println(Message.getFailedMessage("creating new Card"));
                    break;
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
