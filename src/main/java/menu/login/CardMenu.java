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

    public void show() throws SQLException {
        cardMenu:
        while (true) {
            System.out.println("""
                    1 -> Register a Card
                    2 -> Delete a Card
                    3 -> Show a Card based on name of Card
                    4 -> Show Card of specific Bank based on Name of Bank
                    5 -> Show all Cards
                    6 -> Show Transactions with filter
                    7 -> Show all Transactions of Customer
                    8 -> Previous Menu
                    """);

            switch (Input.scanner.next()) {
                case "1": {
                    registerCard();
                    break;
                }
                case "2": {
                    deleteCard();
                    break;
                }
                case "3": {
                    showCardByName();
                    break;
                }
                case "4": {
                    showCardsByBankName();
                    break;
                }
                case "5": {
                    showAllCards();
                    break;
                }
                case "6": {
                    handleFilterTransactoinsView();
                    break;
                }
                case "7": {
                    System.out.println("Here is a list of all your Transactions:");
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

    private void registerCard() throws SQLException {
        System.out.println(Message.getInputMessage("Your Card name"));
        String cardName = Input.scanner.next();
        CreditCard card = new CreditCard(1L, cardName);
        card.setBankName("sparksasse");
        if (CARD_SERVICE.addCard(card)) {
            System.out.println(Message.getSuccessfulMessage("Creating new Card"));
            System.out.println("Your created Card Information:");
            System.out.printf("Card Number: %s%nCCV2: %s%nExpire Date: %s%n",
                    card.getCardNumber(), card.getCvv(), card.getExpiryDate());
        } else {
            System.out.println(Message.getFailedMessage("Creating new Card"));
        }
    }

    private void deleteCard() throws SQLException {
        System.out.println("Which Card do you want to delete?");
        System.out.println(Message.getInputMessage("Your Card number"));
        String cardNumber = Input.scanner.next();
        CreditCard card = new CreditCard();
        card.setCardNumber(cardNumber);
        card.setId(1L);
        if (CARD_SERVICE.removeCard(card)) {
            System.out.println(Message.getSuccessfulMessage("Deleting Card"));
        } else {
            System.out.println(Message.getFailedMessage("Deleting Card: " + card.getCardNumber()));
        }
    }

    private void showCardByName() throws SQLException {
        System.out.println(Message.getInputMessage("Card name you are looking for:"));
        String cardName = Input.scanner.next();
        CreditCard card = CARD_SERVICE.getCardByCardName(cardName);
        if (card != null) {
            System.out.println(Message.getSuccessfulMessage("Card found"));
            System.out.println("Card name: " + card.getCardName());
            System.out.println("Card number: " + card.getCardNumber());
            System.out.println("Card expire date: " + card.getExpiryDate());
            System.out.println("CCV2: " + card.getCvv());
        } else {
            System.out.println(Message.getFailedMessage("Looking for Card: " + cardName));
        }
    }

    private void showCardsByBankName() throws SQLException {
        System.out.println(Message.getInputMessage("Bank name you are looking for Cards:"));
        String bankName = Input.scanner.next();
        showAllCards(CARD_SERVICE.getCardsByBankName(bankName), "Looking for Cards with bank: " + bankName);
    }

    private void showAllCards() throws SQLException {
        showAllCards(CARD_SERVICE.getAllCards(), "Any Card");
    }

    private void handleFilterTransactoinsView() {
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
                    transactionType = TransactionType.NORMAL;
                    System.out.println("invarlid input");
                    break;

                }
                filteredTransactions = TRANSACTION_SERVICE.getTransactionsByUserIdWithType(AuthHolder.totkenUserId.intValue(), transactionType);
                break;
            case 2:
                try {
                    System.out.println("Enter start date (YYYY-MM-DD):");
                    String startDate1 = Input.scanner.next();
                    LocalDate startLocalDate = LocalDate.parse(startDate1);
                    Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                    System.out.println("Enter end date (YYYY-MM-DD):");
                    String endDate1 = Input.scanner.next();
                    LocalDate endLocalDate = LocalDate.parse(endDate1);
                    Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    filteredTransactions = TRANSACTION_SERVICE.getTransactionsByUserIdWithInDate(AuthHolder.totkenUserId.intValue(), startDate, endDate);
                }catch (Exception e) {
                    System.out.println("invarlid DATE!!");
                }
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

    private void showAllCards(List<CreditCard> allCards, String anyCard) throws SQLException {
        if (!allCards.isEmpty()) {
            System.out.println(Message.getSuccessfulMessage("Cards found"));
            for (CreditCard card : allCards) {
                System.out.println();
                System.out.println("Bank name: " + card.getBankName());
                System.out.println("Card name: " + card.getCardName());
                System.out.println("Card number: " + card.getCardNumber());
                System.out.println("Card expire date: " + card.getExpiryDate());
                System.out.println("CCV2: " + card.getCvv());
            }
        } else {
            System.out.println(Message.getFailedMessage(anyCard));
        }
    }
}
