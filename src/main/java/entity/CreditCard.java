package entity;

import util.random.RandomGenerator;

import java.time.LocalDate;
import java.util.Date;

public class CreditCard extends BaseEntity {
    private String cardNumber;
    private LocalDate expiryDate;
    private Integer cvv;
    private double balance;
    private Long accountId;
    private boolean isActive = true;
    private String accountName;

    public CreditCard( double balance, Long accountId,String accountName) {

        this.cardNumber = RandomGenerator.generateRandomCardNumber();
        this.expiryDate = RandomGenerator.generateExpireDate();
        this.cvv = RandomGenerator.generateRandomCCV2();
        this.balance = balance;
        this.accountId = accountId;
        this.accountName = accountName;
    }

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

    public String getAccountName() {
        return accountName;
    }
}
