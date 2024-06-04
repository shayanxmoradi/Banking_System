package entity;

import util.random.RandomGenerator;

import java.time.LocalDate;

public class CreditCard extends BaseEntity {
    private String cardNumber;
    private LocalDate expiryDate;
    private Integer cvv;
    private double balance;
    private Long accountId;
    private boolean isActive = true;
    private String cardName;

    public CreditCard( double balance, Long accountId,String cardName) {

        this.cardNumber = RandomGenerator.generateRandomCardNumber();
        this.expiryDate = RandomGenerator.generateExpireDate();
        this.cvv = RandomGenerator.generateRandomCCV2();
        this.balance = balance;
        this.accountId = accountId;
        this.cardName = cardName;
    }

    public CreditCard(){}

    public String getCardNumber() {
        return cardNumber;
    }



    public Integer getCvv() {
        return cvv;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public double getBalance() {
        return balance;
    }

    public Long getAccountId() {
        return accountId;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

}
