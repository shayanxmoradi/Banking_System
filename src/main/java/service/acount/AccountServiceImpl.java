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
        return accountRepo.deleteAccount(account);
    }

    @Override
    public boolean updateAccountBalance(Long accountId, double balance) {
        return accountRepo.updateAccountBalance(accountId, balance);
    }

    @Override
    public Account getAccountById(Long accountId) {
        return accountRepo.getAccountById(accountId);
    }

    @Override
    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepo.getAccountByAccountNumber(accountNumber);
    }

    @Override
    public Account getAccountByUserId(Long userId) {
        return accountRepo.getAccountByUserId(userId);
    }

    @Override
    public boolean updateAccount(Account account) {
        return accountRepo.updateAccount(account);
    }
}
