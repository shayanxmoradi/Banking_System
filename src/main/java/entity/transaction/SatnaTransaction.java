package entity.transaction;

import entity.transaction.enums.TransactionStatus;
import entity.transaction.enums.TransactionType;

import java.sql.Time;

public class SatnaTransaction extends Transaction {
    public SatnaTransaction(TransactionType type, TransactionStatus transactionStatus, Double amount, Long senderUserId, Time transactionTime, double transactionFee) {
        super(type, transactionStatus, amount, senderUserId, transactionTime, transactionFee);
    }
}
