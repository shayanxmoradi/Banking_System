package entity.transaction;

import entity.BaseEntity;
import entity.transaction.enums.TransactionStatus;
import entity.transaction.enums.TransactionType;

import java.sql.Time;

public abstract class Transaction extends BaseEntity {
    private TransactionType type;
    private TransactionStatus transactionStatus;
    private Double amount;
    private Long senderUserId;
    private Time transactionTime;
    private double transactionFee;

    public Transaction(TransactionType type, TransactionStatus transactionStatus, Double amount, Long senderUserId, Time transactionTime, double transactionFee) {
        this.type = type;
        this.transactionStatus = transactionStatus;
        this.amount = amount;
        this.senderUserId = senderUserId;
        this.transactionTime = transactionTime;
        this.transactionFee = transactionFee;
    }
}
