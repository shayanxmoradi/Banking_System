package entity;

public class Account extends BaseEntity {
    private String accountName;
    private String accountNummber;
    private String payaNummber;
    private Long userId;
    private String userFristName;
    private Long BankId;
    private String bankName;
    private Double balance;

    public Account( String accountName, String accountNummber, Long userId, Long bankId, Double balance,String payaNummber) {

        this.accountName = accountName;
        this.accountNummber = accountNummber;
        this.userId = userId;
        BankId = bankId;
        this.balance = balance;
        this.payaNummber = payaNummber;
    }


}
