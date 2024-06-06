package repository.account;

import entity.Account;
import entity.AccountTransaction;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepo {
    Account addAccount(Account account);
    Account getAccountByAccountName(String accountName);
    Account getAccountByAccountNumber(String accountNumber);
     Account getAccountByUserId(Long userId);

        boolean deleteAccount(Account account);
    boolean updateAccount(Account account);
    boolean updateAccountBalance(Long accountId, double balance);
    Account getAccountById(Long accountId);
  boolean  performBatchTransactions(Long userId, List<AccountTransaction> transactions, double fee);
}
