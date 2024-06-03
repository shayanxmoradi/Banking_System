package service;

import entity.Account;
import entity.User;

import java.sql.SQLException;

public interface UserService {
    boolean login(String username, String password)throws SQLException;
    boolean signUp(User user)throws SQLException;
    boolean createAccount(Account account);

}
