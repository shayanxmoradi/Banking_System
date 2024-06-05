package repository.account;

import entity.Account;

import java.math.BigDecimal;

public interface AccountRepo {
    Account addAccount(Account account);
    Account getAccountByAccountName(String accountName);
    Account getAccountByAccountNumber(String accountNumber);
     Account getAccountByUserId(Long userId);

        boolean deleteAccount(Account account);
    boolean updateAccount(Account account);
    boolean updateAccountBalance(Long accountId, double balance);
    Account getAccountById(Long accountId);
}
