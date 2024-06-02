package entity.transaction;

import entity.transaction.enums.TransactionStatus;
import entity.transaction.enums.TransactionType;

import java.sql.Time;

public class CardTransaction extends Transaction {
    private Long senderCardId;
    private Long receiverCardId;
    private String senderCardNumber;
    private String receiverCardNumber;
    private String senderName;
    private String receiverName;

    public CardTransaction(TransactionType type, TransactionStatus transactionStatus, Double amount, Long senderUserId, Time transactionTime, double transactionFee, String receiverName, String senderName, String receiverCardNumber, String senderCardNumber) {
        super(type, transactionStatus, amount, senderUserId, transactionTime, transactionFee);
        this.receiverName = receiverName;
        this.senderName = senderName;
        this.receiverCardNumber = receiverCardNumber;
        this.senderCardNumber = senderCardNumber;
    }
}
