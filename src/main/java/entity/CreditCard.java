package entity;

import java.util.Date;

public class CreditCard extends BaseEntity {
    private String cardNumber;
    private Date expiryDate;
    private String cvv;
    private double balance;
    private Long accountId;
    private boolean isActive = true;

    public CreditCard(Long id, String cardNumber, Date expiryDate, String cvv, double balance, Long accountId, boolean isActive) {
        super(id);
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.balance = balance;
        this.accountId = accountId;
        this.isActive = isActive;
    }
}
