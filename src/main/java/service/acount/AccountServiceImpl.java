package service.acount;

import entity.Account;
import repository.account.AccountRepo;

public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;

    public AccountServiceImpl(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public boolean createAccount(Account account) {
        if (accountRepo.getAccountByAccountName(account.getAccountName()) != null) {
            return false;
        }
        return accountRepo.addAccount(account) != null;
    }

    @Override
    public Account getAccountByAccountName(String accountName) {
        return accountRepo.getAccountByAccountName(accountName);
    }

    @Override
    public boolean deleteAccount(Account account) {
        return false;
    }

    @Override
    public boolean updateAccountBalance(Long accountId, double balance) {
        return accountRepo.updateAccountBalance(accountId, balance);
    }
}
