package menu.login;

import entity.CreditCard;
import menu.util.Input;
import menu.util.Message;
import util.ApplicationContext;

import javax.smartcardio.Card;
import java.sql.SQLException;
import java.util.List;

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
                case "1": {
                    //todo Normal Money Transaction(Card to Card)
                    System.out.println("choose Card you want to use to transfer Money");
                    List<CreditCard> cardList = showAllCards(ApplicationContext.getInstance().getCardService().getAllCards(), "any Card");
                    System.out.println(Message.getInputMessage("number Of Card"));
                    int pickedCard = Input.scanner.nextInt();
                    CreditCard chosedCard = cardList.get(pickedCard + 1);
                    System.out.println(Message.getInputMessage("Chose a Destination Card"));
                    String destCardNumber = chosedCard.getCardNumber();
                 boolean isSucsesful=   cardTransaction(chosedCard.getCardName(),destCardNumber);
                    break;
                }
                case "2": {
                    //todo  Do Bank Transfer(to Person)
                    break;
                }
                case "3": {

                    //todo Do Muliple Bank Transfer
                    break;
                }
                case "4": {
                    //todo Do Sepsical Bank Transfer(SATNA)
                    break;
                }
                case "5": {
                    break moneyTransMenu;
                }
                default:
                    System.out.println(Message.getInvalidInputMessage());

            }

        }
    }

    private static boolean cardTransaction(String cardName, String destCardNumber) {

        //todo find Account BaseOn CardNummber

        return false;
    }

    private static List<CreditCard> showAllCards(List<CreditCard> AllCards, String any_Card) throws SQLException {
        List<CreditCard> cards = AllCards;
        if (!cards.isEmpty()) {
            System.out.println(Message.getSuccessfulMessage("Cards found"));

            for (int i = 1; i < cards.size() + 1; i++) {
                System.out.println("Card " + i + ": ");
                CreditCard card = cards.get(i);
                System.out.println("bank name: " + card.getBankName());
                System.out.println("card name: " + card.getCardName());
                System.out.println("card number: " + card.getCardNumber());
                System.out.println("card expire date: " + card.getExpiryDate());
                System.out.println("card balance: " + card.getBalance());
                System.out.println("ccv2: " + card.getCvv());
            }
            return cards;
        }
        System.out.println(Message.getFailedMessage(any_Card));
        return null;
    }
}
