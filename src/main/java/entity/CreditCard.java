package entity;

import util.random.RandomGenerator;

import java.time.LocalDate;

public class CreditCard extends BaseEntity {
    private String cardNumber;
    private LocalDate expiryDate;
    private Integer cvv;
    private Long accountId;
    private boolean isActive = true;
    private String cardName;
    private String bankName;

    public CreditCard(Long accountId, String cardName) {

        this.cardNumber = RandomGenerator.generateRandomCardNumber();
        this.expiryDate = RandomGenerator.generateExpireDate();
        this.cvv = RandomGenerator.generateRandomCCV2();
        this.accountId = accountId;
        this.cardName = cardName;
    }

    public CreditCard() {
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


    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }
}
