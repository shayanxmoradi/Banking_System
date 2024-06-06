package service.acount;

import entity.Account;
import entity.AccountTransaction;
import repository.account.AccountRepoImpl;

import java.util.List;

public interface AccountService {
    boolean createAccount(Account account);
    Account getAccountByAccountName(String accountName);
    boolean deleteAccount(Account account);
    boolean updateAccountBalance(Long accountId, double balance);
Account getAccountById(Long accountId);
    Account getAccountByAccountNumber(String accountNumber);
    Account getAccountByUserId(Long userId);

    boolean updateAccount(Account account);
    boolean  performBatchTransactions(Long userId, List<AccountTransaction> transactions);

}
