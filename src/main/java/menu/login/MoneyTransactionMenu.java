package menu.login;

import entity.Account;
import entity.AccountTransaction;
import entity.CreditCard;
import entity.transaction.Transaction;
import entity.transaction.enums.TransactionStatus;
import entity.transaction.enums.TransactionType;
import menu.util.Input;
import menu.util.Message;
import repository.Transaction.TransactionRepo;
import repository.account.AccountRepo;
import repository.account.AccountRepoImpl;
import repository.card.CardRepo;
import service.acount.AccountService;
import service.card.CardService;
import service.transaction.TransactionService;
import util.ApplicationContext;
import util.AuthHolder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoneyTransactionMenu {
    private final Input INPUT;
    private final Message MESSAGE;
    private final AccountService ACCOUNT_SERVICE;
    private final TransactionService TRANSACTION_SERVICE;
    private final CardService CARD_SERVICE;

    public MoneyTransactionMenu(Input input, Message message, AccountService accountService, TransactionService transactionService, CardService cardService) {
        this.INPUT = input;
        this.MESSAGE = message;
        this.ACCOUNT_SERVICE = accountService;
        this.TRANSACTION_SERVICE = transactionService;
        this.CARD_SERVICE = cardService;



    }
    public  void show() throws SQLException {
        moneyTransMenu:
        while (true) {
            System.out.println("""
                            
                            1 -> Normal Money Transaction(Card to Card)
                            2 -> Do Bank Transfer(to Person)
                            3 -> Do Muliple Bank Transfer
                            4 -> Do Sepsical Bank Transfer(SATNA)
                            5 -> Pervious Menu
                    """);

            switch (INPUT.scanner.next()) {
                case "1": {
                    CardToCardTransaction();
                    break;
                }

                case "2": {
                    PayaTransaction();
                    break;
                }
                case "3": {
                    //todo batch is fuped

                    batchPayaTransaction();
                    break;


                }
                case "4": {
                    satnaTransaction();
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

    private void satnaTransaction() {
        System.out.println("SATNA is for Transacatino between 500 and 5000");
        System.out.println(Message.getInputMessage(" Destinatin Paya Nummber"));
        String destAccPaya = Input.scanner.next();
        System.out.println(Message.getInputMessage("Transaction amount (500-5000)"));
        double amount = Input.scanner.nextDouble();
        if (amount < 500 || amount > 5000) {
            System.out.println("Invalid amount");
            return;
        } else {
            Account starterAccount;
            Account desAccount;
            try {
                starterAccount = ACCOUNT_SERVICE.getAccountByUserId(AuthHolder.totkenUserId);
                desAccount = ACCOUNT_SERVICE.getAccountByPayaNumber(destAccPaya);
            } catch (Exception e) {
                System.out.println("Account not found");
                return;
            }
            if (starterAccount.getBalance() < amount) {
                System.out.println(" you don't have enough money");
                Transaction transaction = new Transaction(TransactionType.SATNA, TransactionStatus.FAILED, amount, AuthHolder.totkenUserId, 0);

                transaction.setSenderAccountNummber(starterAccount.getAccountNummber());
                transaction.setReceiverAccountNummber(desAccount.getAccountNummber());
                transaction.setSenderId(starterAccount.getId());
                transaction.setReceiverId(desAccount.getId());
                transaction.setAmount(amount);
              TRANSACTION_SERVICE.addTransaction(transaction);
                System.out.println(Message.getSuccessfulMessage("Transfer was sucessfull"));
                return;
            } else {
                //todo watch out for difrence of paya number and account number
                try {
                    double transactionFee = amount * 0.002;
                    boolean reducingProcessIsSucess = ACCOUNT_SERVICE.updateAccountBalance(starterAccount.getId(), starterAccount.getBalance() - amount - transactionFee);
                    boolean increasingProcessIsSucess = ACCOUNT_SERVICE.updateAccountBalance(desAccount.getId(), desAccount.getBalance() + amount);
                    if (reducingProcessIsSucess && increasingProcessIsSucess) {
                        Transaction transaction = new Transaction(TransactionType.SATNA, TransactionStatus.SUCCESSFUL, amount, AuthHolder.totkenUserId, 0);

                        transaction.setSenderAccountNummber(starterAccount.getAccountNummber());
                        transaction.setReceiverAccountNummber(desAccount.getAccountNummber());
                        transaction.setSenderId(starterAccount.getId());
                        transaction.setReceiverId(desAccount.getId());
                        transaction.setAmount(amount);
                        TRANSACTION_SERVICE.addTransaction(transaction);
                        System.out.println(Message.getSuccessfulMessage("Transfer was sucessfull"));
                        System.out.println(Message.getSuccessfulMessage("Transfer was sucessfull"));
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("Account not found");

                }
                System.out.println(Message.getFailedMessage("Transfer"));
                return;
            }
        }
    }

    private void batchPayaTransaction() {
        System.out.println("How many transactions do you want to perform in batch?");
        int numberOfTransactions = Input.scanner.nextInt();

        List<AccountTransaction> transactions = new ArrayList<>();

        for (int i = 0; i < numberOfTransactions; i++) {
            System.out.println("Transaction " + (i + 1) + " (amount between 150 and 500):");

            System.out.println(Message.getInputMessage("Destination Paya Number"));
            String desAccountPaya = Input.scanner.next();

            System.out.println(Message.getInputMessage("Transaction amount (150-500)"));
            double amount = Input.scanner.nextDouble();

            if (amount < 150 || amount > 500) {
                System.out.println("Invalid amount. Please enter an amount between 150 and 500.");
                i--;
                continue;
            }
            AccountTransaction accountTransaction = new AccountTransaction(desAccountPaya, amount);
            transactions.add(accountTransaction);

        }

        try {
            ACCOUNT_SERVICE.performBatchTransactions(AuthHolder.totkenUserId, transactions);
            System.out.println("Batch transactions completed successfully");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to complete batch transactions");
            return;
        }
    }

    private void PayaTransaction() {
        //todo make picking account like card picker
        System.out.println("this is for Transacatino between 150 and 500");
        //todo change this to paya number
        System.out.println(Message.getInputMessage(" Destinatin Paya Nummber"));
        String desAccountPaya = Input.scanner.next();
        System.out.println(Message.getInputMessage("Transaction amount (150-500)"));
        double amount = Input.scanner.nextDouble();
        if (amount < 150 || amount > 500) {
            System.out.println("Invalid amount");
            return;
        } else {
            Account starterAccount;
            Account desAccount;
            try {
                starterAccount = ACCOUNT_SERVICE.getAccountByUserId(AuthHolder.totkenUserId);
                System.out.println("starter account" + starterAccount.getAccountNummber());
                desAccount = ACCOUNT_SERVICE.getAccountByPayaNumber(desAccountPaya);
                System.out.println("des account" + starterAccount.getAccountNummber());

            } catch (Exception e) {
                System.out.println("Account not found");
                return;
            }
            if (starterAccount.getBalance() < amount) {
                System.out.println(" you don't have enough money");
                Transaction transaction = new Transaction(TransactionType.PAYA, TransactionStatus.FAILED, amount, AuthHolder.totkenUserId, 0);

                transaction.setSenderAccountNummber(starterAccount.getAccountNummber());
                transaction.setReceiverAccountNummber(desAccount.getAccountNummber());
                transaction.setSenderId(starterAccount.getId());
                transaction.setReceiverId(desAccount.getId());
                transaction.setAmount(amount);
                TRANSACTION_SERVICE.addTransaction(transaction);

                return;
            } else {
                //todo watch out for difrence of paya number and account number
                try {

                    boolean reducingProcessIsSucess = ACCOUNT_SERVICE.updateAccountBalance(starterAccount.getId(), starterAccount.getBalance() - amount);
                    boolean increasingProcessIsSucess = ACCOUNT_SERVICE.updateAccountBalance(desAccount.getId(), desAccount.getBalance() + amount);
                    if (reducingProcessIsSucess && increasingProcessIsSucess) {
                        Transaction transaction = new Transaction(TransactionType.PAYA, TransactionStatus.SUCCESSFUL, amount, AuthHolder.totkenUserId, 0);

                        transaction.setSenderAccountNummber(starterAccount.getAccountNummber());
                        transaction.setReceiverAccountNummber(desAccount.getAccountNummber());
                        transaction.setSenderId(starterAccount.getId());
                        transaction.setReceiverId(desAccount.getId());
                        transaction.setAmount(amount);
                       TRANSACTION_SERVICE.addTransaction(transaction);
                        System.out.println(Message.getSuccessfulMessage("Transfer was sucessfull"));
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("Account not found");

                }
                System.out.println(Message.getFailedMessage("Transfer"));
                return;
            }
        }
    }

    private void CardToCardTransaction() throws SQLException {
        //todo check Fee
        System.out.println("choose Card you want to use to transfer Money");
        List<CreditCard> cardList = showAllCards(CARD_SERVICE.getAllCards(), "any Card");
        CreditCard chosedCard;
        try {
            System.out.println(MESSAGE.getInputMessage("Card nummber which you want to use "));
            int pickedCard = Input.scanner.nextInt();
            chosedCard = cardList.get(pickedCard - 1);
        } catch (Exception e) {
            System.out.println("invalid number");
            return;
        }

        System.out.println(MESSAGE.getInputMessage(Message.getInputMessage(" a Destination Card")));
        String destCardNumber = Input.scanner.next();
        double amount;
        validAmount:
        while (true) {
            System.out.println(MESSAGE.getInputMessage(Message.getInputMessage("Transaction amount under 150")));
            amount = Input.scanner.nextDouble();
            if (amount > 0 && amount < 150) {
                break validAmount;
            }
            System.out.println("Invalid amount");
        }
        boolean isSucsesful = cardTransaction(chosedCard.getCardNumber(), destCardNumber, amount);
        return;
    }

    private  boolean cardTransaction(String cardName, String destCardNumber, double amount) throws SQLException {

        //todo find Account BaseOn CardNummber


        Account startAccount = CARD_SERVICE.getAccountByCardNumber(cardName);
        Account destAccount = CARD_SERVICE.getAccountByCardNumber(destCardNumber);
        if (startAccount == null || destAccount == null) {
            System.out.println(Message.getFailedMessage("finding Cards "));
            return false;
        }
        if (startAccount.getBalance() >= amount) {
            System.out.println("hi");
            System.out.println(" id " + startAccount.getId());
            System.out.println(" des id " + destAccount.getId());
            double fee = 0;
            if (!startAccount.getBankName().equals(destAccount.getBankName())) {
                if (amount < 50) {
                    fee = 0.5;
                } else {
                    fee = 1;
                    double extraAmount = amount - 50;
                    while (extraAmount > 0) {
                        fee = fee + 0.1;
                        extraAmount = extraAmount - 10;
                    }
                }

            }
            boolean reducingProcessIsSucess = ACCOUNT_SERVICE.updateAccountBalance(startAccount.getId(), startAccount.getBalance() - amount - fee);
            boolean increasingProcessIsSucess = ACCOUNT_SERVICE.updateAccountBalance(destAccount.getId(), destAccount.getBalance() + amount);
            System.out.println("reducingProcessIsSucess" + reducingProcessIsSucess);
            System.out.println("increasingProcessIsSucess" + increasingProcessIsSucess);
            if (reducingProcessIsSucess && increasingProcessIsSucess) {
                System.out.println(Message.getSuccessfulMessage(amount + " Card Transfer to " + destAccount.getUserFristName()));
                // add sucessfull Transaction
                Transaction transaction = new Transaction(TransactionType.NORMAL, TransactionStatus.SUCCESSFUL, amount, AuthHolder.totkenUserId, 0);

                transaction.setSenderAccountNummber(startAccount.getAccountNummber());
                transaction.setReceiverAccountNummber(destAccount.getAccountNummber());
                transaction.setSenderId(startAccount.getId());
                transaction.setReceiverId(destAccount.getId());
                transaction.setAmount(amount);
                TRANSACTION_SERVICE.addTransaction(transaction);

                return true;
            } else System.out.println("unable to transfer money");
        } else
            System.out.println("you are low on your Currency!");
        Transaction transaction = new Transaction(TransactionType.NORMAL, TransactionStatus.FAILED, amount, AuthHolder.totkenUserId, 0);
        transaction.setSenderAccountNummber(startAccount.getAccountNummber());
        transaction.setReceiverAccountNummber(destAccount.getAccountNummber());
        transaction.setSenderId(startAccount.getId());
        transaction.setReceiverId(destAccount.getId());
        transaction.setAmount(amount);

       TRANSACTION_SERVICE.addTransaction(transaction);


        return false;
    }

    private static List<CreditCard> showAllCards(List<CreditCard> AllCards, String any_Card) throws SQLException {
        List<CreditCard> cards = AllCards;
        if (!cards.isEmpty()) {
            System.out.println(Message.getSuccessfulMessage("Cards found"));

            for (int i = 0; i < cards.size(); i++) {
                System.out.println("Card " + i + 1 + ": ");
                CreditCard card = cards.get(i);
                System.out.println("bank name: " + card.getBankName());
                System.out.println("card name: " + card.getCardName());
                System.out.println("card number: " + card.getCardNumber());
                System.out.println("card expire date: " + card.getExpiryDate());
                System.out.println("ccv2: " + card.getCvv());
            }
            return cards;
        }
        System.out.println(Message.getFailedMessage(any_Card));
        return null;
    }
}
