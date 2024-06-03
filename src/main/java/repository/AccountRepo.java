package repository;

import entity.Account;

public interface AccountRepo {
    Account addAccount(Account account);
    Account getAccountByAccountName(String username);
    boolean deleteAccount(Account account);
}
