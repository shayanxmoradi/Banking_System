package menu.login;

import entity.Account;
import entity.AccountTransaction;
import entity.CreditCard;
import entity.transaction.Transaction;
import entity.transaction.enums.TransactionStatus;
import entity.transaction.enums.TransactionType;
import menu.util.Input;
import menu.util.Message;
import repository.account.AccountRepoImpl;
import util.ApplicationContext;
import util.AuthHolder;

import java.sql.SQLException;
import java.util.ArrayList;
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
                    //todo check Fee
                    System.out.println("choose Card you want to use to transfer Money");
                    List<CreditCard> cardList = showAllCards(ApplicationContext.getInstance().getCardService().getAllCards(), "any Card");
                    CreditCard chosedCard;
                    try {
                        System.out.println(Message.getInputMessage("Card nummber which you want to use "));
                        int pickedCard = Input.scanner.nextInt();
                        chosedCard = cardList.get(pickedCard - 1);
                    } catch (Exception e) {
                        System.out.println("invalid number");
                        break;
                    }

                    System.out.println(Message.getInputMessage(Message.getInputMessage(" a Destination Card")));
                    String destCardNumber = Input.scanner.next();
                    double amount;
                    validAmount:
                    while (true) {
                        System.out.println(Message.getInputMessage(Message.getInputMessage("Transaction amount under 150")));
                        amount = Input.scanner.nextDouble();
                        if (amount > 0 && amount < 150) {
                            break validAmount;
                        }
                        System.out.println("Invalid amount");
                    }
                    boolean isSucsesful = cardTransaction(chosedCard.getCardNumber(), destCardNumber, amount);
                    break;
                }

                case "2": {
                    //todo make picking account like card picker
                    System.out.println("this is for Transacatino between 150 and 500");
                    System.out.println(Message.getInputMessage(" Destinatin Account Nummber"));
                    String destAccountNumber = Input.scanner.next();
                    System.out.println(Message.getInputMessage("Transaction amount (150-500)"));
                    double amount = Input.scanner.nextDouble();
                    if (amount < 150 || amount > 500) {
                        System.out.println("Invalid amount");
                        break;
                    } else {
                        Account starterAccount;
                        Account desAccount;
                        try {
                            starterAccount = ApplicationContext.getInstance().getAccountService().getAccountByUserId(AuthHolder.totkenUserId);
                            System.out.println("starter account" + starterAccount.getAccountNummber());
                            desAccount = ApplicationContext.getInstance().getAccountService().getAccountByAccountNumber(destAccountNumber);
                            System.out.println("des account" + starterAccount.getAccountNummber());

                        } catch (Exception e) {
                            System.out.println("Account not found");
                            break;
                        }
                        if (starterAccount.getBalance() < amount) {
                            System.out.println(" you don't have enough money");
                            break;
                        } else {
                            //todo watch out for difrence of paya number and account number
                            try {

                                boolean reducingProcessIsSucess = ApplicationContext.getInstance().getAccountService().updateAccountBalance(starterAccount.getId(), starterAccount.getBalance() - amount);
                                boolean increasingProcessIsSucess = ApplicationContext.getInstance().getAccountService().updateAccountBalance(desAccount.getId(), desAccount.getBalance() + amount);
                                if (reducingProcessIsSucess && increasingProcessIsSucess) {

                                    System.out.println(Message.getSuccessfulMessage("Transfer was sucessfull"));
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println("Account not found");

                            }
                            System.out.println(Message.getFailedMessage("Transfer"));
                            break;
                        }
                    }
                }
                case "3": {

                    System.out.println("How many transactions do you want to perform in batch?");
                    int numberOfTransactions = Input.scanner.nextInt();

                    List<AccountTransaction> transactions = new ArrayList<>();

                    for (int i = 0; i < numberOfTransactions; i++) {
                        System.out.println("Transaction " + (i + 1) + " (amount between 150 and 500):");

                        System.out.println(Message.getInputMessage("Destination Account Number"));
                        String destAccountNumber = Input.scanner.next();

                        System.out.println(Message.getInputMessage("Transaction amount (150-500)"));
                        double amount = Input.scanner.nextDouble();

                        if (amount < 150 || amount > 500) {
                            System.out.println("Invalid amount. Please enter an amount between 150 and 500.");
                            i--; // Decrement the counter to redo this transaction
                            continue;
                        }
                        AccountTransaction accountTransaction = new AccountTransaction(destAccountNumber, amount);
                        transactions.add(accountTransaction);
                    }

                    // Perform batch transactions
                    try {
                        ApplicationContext.getInstance().getAccountService().performBatchTransactions(AuthHolder.totkenUserId, transactions);
                        System.out.println("Batch transactions completed successfully");
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Failed to complete batch transactions");
                        break;
                    }


                }
                case "4": {
                    System.out.println("SATNA is for Transacatino between 500 and 5000");
                    System.out.println(Message.getInputMessage(" Destinatin Account Nummber"));
                    String destAccountNumber = Input.scanner.next();
                    System.out.println(Message.getInputMessage("Transaction amount (500-5000)"));
                    double amount = Input.scanner.nextDouble();
                    if (amount < 500 || amount > 5000) {
                        System.out.println("Invalid amount");
                        break;
                    } else {
                        Account starterAccount;
                        Account desAccount;
                        try {
                            starterAccount = ApplicationContext.getInstance().getAccountService().getAccountByUserId(AuthHolder.totkenUserId);
                            desAccount = ApplicationContext.getInstance().getAccountService().getAccountByAccountNumber(destAccountNumber);
                        } catch (Exception e) {
                            System.out.println("Account not found");
                            break;
                        }
                        if (starterAccount.getBalance() < amount) {
                            System.out.println(" you don't have enough money");
                            break;
                        } else {
                            //todo watch out for difrence of paya number and account number
                            try {
                                double transactionFee = amount * 0.002;
                                boolean reducingProcessIsSucess = ApplicationContext.getInstance().getAccountService().updateAccountBalance(starterAccount.getId(), starterAccount.getBalance() - amount - transactionFee);
                                boolean increasingProcessIsSucess = ApplicationContext.getInstance().getAccountService().updateAccountBalance(desAccount.getId(), desAccount.getBalance() + amount);
                                if (reducingProcessIsSucess && increasingProcessIsSucess) {
                                    System.out.println(Message.getSuccessfulMessage("Transfer was sucessfull"));
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println("Account not found");

                            }
                            System.out.println(Message.getFailedMessage("Transfer"));
                            break;
                        }
                    }
                }
                case "5": {
                    break moneyTransMenu;
                }
                default:
                    System.out.println(Message.getInvalidInputMessage());

            }

        }
    }

    private static boolean cardTransaction(String cardName, String destCardNumber, double amount) throws SQLException {

        //todo find Account BaseOn CardNummber


        Account startAccount = ApplicationContext.getInstance().getCardService().getAccountByCardNumber(cardName);
        Account destAccount = ApplicationContext.getInstance().getCardService().getAccountByCardNumber(destCardNumber);
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
            boolean reducingProcessIsSucess = ApplicationContext.getInstance().getAccountService().updateAccountBalance(startAccount.getId(), startAccount.getBalance() - amount - fee);
            boolean increasingProcessIsSucess = ApplicationContext.getInstance().getAccountService().updateAccountBalance(destAccount.getId(), destAccount.getBalance() + amount);
            System.out.println("reducingProcessIsSucess" + reducingProcessIsSucess);
            System.out.println("increasingProcessIsSucess" + increasingProcessIsSucess);
            if (reducingProcessIsSucess && increasingProcessIsSucess) {
                System.out.println(Message.getSuccessfulMessage(amount + " Card Transfer to " + destAccount.getUserFristName()));
                // add sucessfull Transaction
                Transaction transaction = new Transaction(TransactionType.NORMAL, TransactionStatus.SUCCESSFUL, amount, AuthHolder.totkenUserId, 0);
                System.out.println("sender id " + startAccount.getAccountNummber());
                System.out.println("recvoiiii id " + destAccount.getAccountNummber());

                transaction.setSenderAccountNummber(startAccount.getAccountNummber());
                transaction.setReceiverAccountNummber(destAccount.getAccountNummber());
                transaction.setSenderId(startAccount.getId());
                transaction.setReceiverId(destAccount.getId());
                transaction.setAmount(amount);
                ApplicationContext.getInstance().getTransactionService().addTransaction(transaction);

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

        ApplicationContext.getInstance().getTransactionService().addTransaction(transaction);


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
