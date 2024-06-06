package service.transaction;

import entity.transaction.Transaction;
import entity.transaction.enums.TransactionType;
import repository.Transaction.TransactionRepo;

import java.util.Date;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepo transactionRepo;

    public TransactionServiceImpl(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @Override
    public boolean addTransaction(Transaction transaction) {
        return transactionRepo.addTransaction(transaction);
    }

    @Override
    public boolean deleteTransaction(Transaction transaction) {
        return transactionRepo.deleteTransaction(transaction);
    }

    @Override
    public boolean updateTransaction(Transaction transaction) {
        return transactionRepo.updateTransaction(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepo.getAllTransactions();
    }

    @Override
    public List<Transaction> getTransactionsByUserId(int userId) {
        return transactionRepo.getTransactionsByUserId(userId);
    }

    @Override
    public List<Transaction> getTransactionsByUserIdWithType(int userId, TransactionType transactionType) {
        return transactionRepo.getTransactionsByUserIdWithType(userId, transactionType);
    }

    @Override
    public List<Transaction> getTransactionsByUserIdWithAmount(int userId, float minAmount) {
        return transactionRepo.getTransactionsByUserIdWithAmount(userId, minAmount);
    }

    @Override
    public List<Transaction> getTransactionsByUserIdWithInDate(int userId, Date startDate, Date endDate) {
        return transactionRepo.getTransactionsByUserIdWithInDate(userId, startDate, endDate);
    }

    @Override
    public List<Transaction> getTransactionsByUserIdWithDay(int userId, Date getDateFrom) {
        return transactionRepo.getTransactionsByUserIdWithDay(userId, getDateFrom);
    }
}
