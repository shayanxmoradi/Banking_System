package service;

import entity.Account;
import repository.AccountRepo;

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
}
