package entity.transaction;

import entity.BaseEntity;
import entity.transaction.enums.TransactionStatus;
import entity.transaction.enums.TransactionType;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public  class Transaction extends BaseEntity {
    private TransactionType type;
    private TransactionStatus transactionStatus;
    private Double amount;
    private Long senderUserId;
    private LocalTime transactionTime;
    private LocalDate transactionDate;
    private double transactionFee;
    private String senderAccountNummber;
    private String receiverAccountNummber;
    private Long senderId;
    private Long receiverId;


    public Transaction(TransactionType type, TransactionStatus transactionStatus, Double amount, Long senderUserId, double transactionFee) {
        this.type = type;
        this.transactionStatus = transactionStatus;
        this.amount = amount;
        this.senderUserId = senderUserId;
        this.transactionFee = transactionFee;
        this.transactionDate= LocalDate.now();
        this.transactionTime= LocalTime.now();
    }


    public TransactionType getType() {
        return type;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public Double getAmount() {
        return amount;
    }

    public Long getSenderUserId() {
        return senderUserId;
    }

    public LocalTime getTransactionTime() {
        return transactionTime;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public double getTransactionFee() {
        return transactionFee;
    }

    public String getSenderAccountNummber() {
        return senderAccountNummber;
    }

    public String getReceiverAccountNummber() {
        return receiverAccountNummber;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setSenderUserId(Long senderUserId) {
        this.senderUserId = senderUserId;
    }

    public void setTransactionTime(LocalTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setTransactionFee(double transactionFee) {
        this.transactionFee = transactionFee;
    }

    public void setSenderAccountNummber(String senderAccountNummber) {
        this.senderAccountNummber = senderAccountNummber;
    }

    public void setReceiverAccountNummber(String receiverAccountNummber) {
        this.receiverAccountNummber = receiverAccountNummber;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
               "type=" + type +
               ", transactionStatus=" + transactionStatus +
               ", amount=" + amount +
               ", senderUserId=" + senderUserId +
               ", transactionTime=" + transactionTime +
               ", transactionDate=" + transactionDate +
               ", transactionFee=" + transactionFee +
               ", senderAccountNummber='" + senderAccountNummber + '\'' +
               ", receiverAccountNummber='" + receiverAccountNummber + '\'' +
               ", senderId=" + senderId +
               ", receiverId=" + receiverId +
               '}';
    }
}
