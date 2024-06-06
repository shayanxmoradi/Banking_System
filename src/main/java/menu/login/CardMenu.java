package menu.login;

import entity.CreditCard;
import entity.User;
import entity.transaction.Transaction;
import entity.transaction.enums.TransactionStatus;
import entity.transaction.enums.TransactionType;
import menu.SignUpMenu;
import menu.util.Input;
import menu.util.Message;
import service.acount.AccountService;
import service.card.CardService;
import service.transaction.TransactionService;
import util.ApplicationContext;
import util.AuthHolder;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class CardMenu {

        private final Input INPUT;
        private final Message MESSAGE;
        private final AccountService ACCOUNT_SERVICE;
        private final TransactionService TRANSACTION_SERVICE;
        private final CardService CARD_SERVICE;

        public CardMenu(Input input, Message message, AccountService accountService, TransactionService transactionService, CardService cardService) {
            this.INPUT = input;
            this.MESSAGE = message;
            this.ACCOUNT_SERVICE = accountService;
            this.TRANSACTION_SERVICE = transactionService;
            this.CARD_SERVICE = cardService;


        }
    public  void show() throws SQLException {
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
//                    System.out.println(Message.getInputMessage("Your Card initial Balance"));
//                    double balance = Input.scanner.nextDouble();
                    CreditCard card = new CreditCard(accocuntId, cardName);
                    card.setBankName(bankName);
                    if (CARD_SERVICE.addCard(card)) {
                        System.out.println(Message.getSuccessfulMessage("Creating new Card"));
                        String cardDetail = """
                                Your created Card Information:
                                Card Nummber : %s
                                CCV2 : %s
                                Expire Date : %s
                                """;

                        System.out.println(cardDetail.formatted(card.getCardNumber(), card.getCvv(), card.getExpiryDate()));

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
                    if (CARD_SERVICE.removeCard(card)) {
                        System.out.println(Message.getSuccessfulMessage("Deleting Card"));
                        break;
                    }
                    System.out.println(Message.getFailedMessage("deleting Card: " + card.getCardNumber()));
                    break;
                }

                case "3": {
                    System.out.println(Message.getInputMessage(" Card name, which you are looking for "));
                    String cardName = Input.scanner.next();
                    CreditCard card = CARD_SERVICE.getCardByCardName(cardName);

                    if (card != null) {
                        System.out.println(Message.getSuccessfulMessage("Card found"));
                        System.out.println("card name: " + card.getCardName());
                        System.out.println("card number: " + card.getCardNumber());
                        System.out.println("card expire date: " + card.getExpiryDate());
                        System.out.println("ccv2: " + card.getCvv());
                        break;

                    }
                    System.out.println(Message.getFailedMessage("looking for Card: " + cardName));
                    break;
                }
                case "4": {
                    System.out.println(Message.getInputMessage(" Bank name, which you are looking for Cards "));
                    String bankName = Input.scanner.next();
                    showAllCards(CARD_SERVICE.getCardsByBankName(bankName), "looking for Cards with bank: " + bankName);
                    break;
                }
                case "5": {
                    showAllCards(CARD_SERVICE.getAllCards(), "any Card");
                    break;
                }
                case "6": {
                    handleFilterTransactoinsView();
                }
                case "7": {
                    System.out.println("here is list of all your Transactions");
                    System.out.println(TRANSACTION_SERVICE.getTransactionsByUserId(AuthHolder.totkenUserId.intValue()));
                    break;
                }

                case "8": {
                    break cardMenu;
                }
                default:
                    System.out.println(Message.getInvalidInputMessage());

            }

        }
    }

    private  void handleFilterTransactoinsView() {
        System.out.println("Choose a filter option:");
        System.out.println("1. Filter by type");
        System.out.println("2. Filter by date range");
        System.out.println("3. Filter by amount greater than");

        int choice = Input.scanner.nextInt();

        List<Transaction> filteredTransactions = null;

        switch (choice) {
            case 1:
                System.out.println("chose Transaction Type");
                System.out.println("""
                        1 -> NORMAL
                        2 ->PAYA
                        3 -> PAYA_TOGHEHER
                        4 -> Satna
                        """);
                TransactionType transactionType = null;
                try {
                    String type = Input.scanner.nextLine();
                    if (type.equalsIgnoreCase("1")) {
                        transactionType = TransactionType.NORMAL;
                    } else if (type.equalsIgnoreCase("2")) {
                        transactionType = TransactionType.PAYA;
                    } else if (type.equalsIgnoreCase("3")) {
                        transactionType = TransactionType.PAYA_TOGHEHER;
                    } else if (type.equalsIgnoreCase("4")) {
                        transactionType = TransactionType.SATNA;
                    }

                } catch (Exception e) {
                    break;
                }
            filteredTransactions = TRANSACTION_SERVICE.getTransactionsByUserIdWithType(AuthHolder.totkenUserId.intValue(), transactionType);
                break;
            case 2:
                System.out.println("Enter start date (YYYY-MM-DD):");
                LocalDate startLocalDate = LocalDate.parse(Input.scanner.nextLine());
                Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                System.out.println("Enter end date (YYYY-MM-DD):");
                LocalDate endLocalDate = LocalDate.parse(Input.scanner.nextLine());
                Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                filteredTransactions = TRANSACTION_SERVICE.getTransactionsByUserIdWithInDate(AuthHolder.totkenUserId.intValue(), startDate, endDate);
                break;
            case 3:
                System.out.println("Enter amount:");
                double amount = Input.scanner.nextDouble();
                filteredTransactions = TRANSACTION_SERVICE.getTransactionsByUserIdWithAmount(AuthHolder.totkenUserId.intValue(), (float) amount);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }

        if (filteredTransactions != null) {
            System.out.println("Filtered Transactions:");
            filteredTransactions.forEach(System.out::println);
        } else {
            System.out.println("No transactions found.");
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
                System.out.println("ccv2: " + card.getCvv());
            }
            return;
        }
        System.out.println(Message.getFailedMessage(any_Card));
        return;
    }
}
