package menu.login;

import entity.CreditCard;
import entity.User;
import menu.SignUpMenu;
import menu.util.Input;
import menu.util.Message;
import util.ApplicationContext;

import java.sql.SQLException;
import java.util.List;

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
                    String bankName = "sparksasse";
                    System.out.println(Message.getInputMessage("Your Card name"));
                    String cardName = Input.scanner.next();
                    System.out.println(Message.getInputMessage("Your Card initial Balance"));
                    double balance = Input.scanner.nextDouble();
                    CreditCard card = new CreditCard(balance, accocuntId, cardName);
                    card.setBankName(bankName);
                    if (ApplicationContext.getInstance().getCardService().addCard(card)) {
                        System.out.println(Message.getSuccessfulMessage("Creating new Card"));
                        String cardDetail = """
                                Your created Card Information:
                                Card Nummber : %s
                                CCV2 : %s
                                Expire Date : %s
                                Balance : %s
                                """;
                        System.out.println(cardDetail.formatted(card.getCardNumber(), card.getCvv(), card.getExpiryDate(), card.getBalance()));

                        break;
                    }
                    System.out.println(Message.getFailedMessage("creating new Card"));
                    break;
                }
                case "2": {
                    System.out.println("which Card you want to delete");
                    System.out.println(Message.getInputMessage("Your Card number"));
                    String cardNumber = Input.scanner.next();
                    //todo here handle card to delete
                    Long deleteCardId = 1L;
                    CreditCard card = new CreditCard();
                    card.setCardNumber(cardNumber);
                    card.setId(deleteCardId);
                    if (ApplicationContext.getInstance().getCardService().removeCard(card)) {
                        System.out.println(Message.getSuccessfulMessage("Deleting Card"));
                        break;
                    }
                    System.out.println(Message.getFailedMessage("deleting Card: " + card.getCardNumber()));
                }
                case "3": {
                    System.out.println(Message.getInputMessage(" Card name, which you are looking for "));
                    String cardName = Input.scanner.next();
                    CreditCard card = ApplicationContext.getInstance().getCardService().getCardByCardName(cardName);

                    if (card != null) {
                        System.out.println(Message.getSuccessfulMessage("Card found"));
                        System.out.println("card name: " + card.getCardName());
                        System.out.println("card number: " + card.getCardNumber());
                        System.out.println("card expire date: " + card.getExpiryDate());
                        System.out.println("card balance: " + card.getBalance());
                        System.out.println("ccv2: " + card.getCvv());
                        break;

                    }
                    System.out.println(Message.getFailedMessage("looking for Card: " + cardName));
                    break;
                }
                case "4": {
                    System.out.println(Message.getInputMessage(" Bank name, which you are looking for Cards "));
                    String bankName = Input.scanner.next();
                    showAllCards(ApplicationContext.getInstance().getCardService().getCardsByBankName(bankName), "looking for Cards with bank: " + bankName);
                    break;
                }
                case "5": {
                    showAllCards(ApplicationContext.getInstance().getCardService().getAllCards(), "any Card");
                    break;
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

    private static void showAllCards(List<CreditCard> AllCards, String any_Card) throws SQLException {
        List<CreditCard> cards = AllCards;
        if (!cards.isEmpty()) {
            System.out.println(Message.getSuccessfulMessage("Cards found"));

            for (CreditCard card : cards) {
                System.out.println();
                System.out.println("bank name: " + card.getBankName());
                System.out.println("card name: " + card.getCardName());
                System.out.println("card number: " + card.getCardNumber());
                System.out.println("card expire date: " + card.getExpiryDate());
                System.out.println("card balance: " + card.getBalance());
                System.out.println("ccv2: " + card.getCvv());
            }
            return;
        }
        System.out.println(Message.getFailedMessage(any_Card));
        return;
    }
}
