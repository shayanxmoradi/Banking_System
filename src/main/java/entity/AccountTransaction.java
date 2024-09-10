package entity;

public class AccountTransaction {
    private final String destAccountNumber;
    private final Double amount;
    private final Double fee;

    public AccountTransaction(String destAccountNumber, double amount,double fee) {
        this.destAccountNumber = destAccountNumber;
        this.amount = amount;
        this.fee = fee;
    }

    public String getDestAccountNumber() {
        return destAccountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public Double getFee() {
        return fee;
    }
}

