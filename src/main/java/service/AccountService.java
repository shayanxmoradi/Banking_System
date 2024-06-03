package service;

import entity.Account;

public interface AccountService {
    boolean createAccount(Account account);
    Account getAccountByAccountName(String accountName);
    boolean deleteAccount(Account account);
}
