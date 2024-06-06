package entity;

public class AccountTransaction {
    private final String destAccountNumber;
    private final double amount;

    public AccountTransaction(String destAccountNumber, double amount) {
        this.destAccountNumber = destAccountNumber;
        this.amount = amount;
    }

    public String getDestAccountNumber() {
        return destAccountNumber;
    }

    public double getAmount() {
        return amount;
    }
}

