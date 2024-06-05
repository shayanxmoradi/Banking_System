package entity;

import util.random.RandomGenerator;

public class Account extends BaseEntity {
    private String accountName;
    private String accountNummber;
    private String payaNummber;
    private Long userId;
    private String userFristName;
    private Long BankId;
    private String bankName;
    private Double balance;

    public Account(String accountName,  Long bankId, String bankName, Double balance) {
        this.accountName = accountName;
        this.accountNummber = RandomGenerator.generateRandomAccountNummber();//todo auto
        this.payaNummber = RandomGenerator.generateRandomIBAN();// todo auto
        BankId = bankId;
        this.bankName = bankName;
        this.balance = balance;
    }
public Account(){}
    public String getAccountName() {
        return accountName;
    }

    public String getAccountNummber() {
        return accountNummber;
    }

    public String getPayaNummber() {
        return payaNummber;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserFristName() {
        return userFristName;
    }

    public Long getBankId() {
        return BankId;
    }

    public String getBankName() {
        return bankName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAccountNummber(String accountNummber) {
        this.accountNummber = accountNummber;
    }

    public void setPayaNummber(String payaNummber) {
        this.payaNummber = payaNummber;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserFristName(String userFristName) {
        this.userFristName = userFristName;
    }

    public void setBankId(Long bankId) {
        BankId = bankId;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
