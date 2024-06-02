package repository;

import entity.User;

import java.sql.SQLException;

public interface UserRepo {
    User addUser(User user) throws SQLException;
    User findByUsernamePassword(String username, String password) throws SQLException;
    boolean deleteUser(User user);
    User getUserByUsername(String username);
    User getUserByID(Long id);
    boolean isUserExist(String username) throws SQLException;

}
