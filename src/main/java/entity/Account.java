package entity;

public class Account extends BaseEntity {
    private String accountName;
    private String accountNummber;
    private String payaNummber;
    private Long userId;
    private Long BankId;
    private Double balance;

    public Account(Long id, String accountName, String accountNummber, Long userId, Long bankId, Double balance,String payaNummber) {
        super(id);
        this.accountName = accountName;
        this.accountNummber = accountNummber;
        this.userId = userId;
        BankId = bankId;
        this.balance = balance;
        this.payaNummber = payaNummber;
    }


}
