package repository.Transaction;

import entity.transaction.Transaction;
import entity.transaction.enums.TransactionType;

import java.util.Date;
import java.util.List;

public interface TransactionRepo {
    boolean addTransaction(Transaction transaction);
    boolean deleteTransaction(Transaction transaction);
    boolean updateTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByUserId(int userId);
    List<Transaction> getTransactionsByUserIdWithType(int userId, TransactionType transactionType);
    List<Transaction> getTransactionsByUserIdWithAmount(int userId, float minAmount);
    List<Transaction> getTransactionsByUserIdWithInDate(int userId, Date startDate, Date endDate);
    List<Transaction> getTransactionsByUserIdWithDay(int userId, Date getDateFrom);

}
