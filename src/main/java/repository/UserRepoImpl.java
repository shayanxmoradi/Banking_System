package repository;

import entity.User;

import java.sql.Connection;

public class UserRepoImpl implements UserRepo {
    private final Connection connection;

    public UserRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public User findByUsernamePassword(String username, String password) {
        return null;
    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public User getUserByID(Long id) {
        return null;
    }

    @Override
    public boolean isUserExist(String username) {
        return false;
    }
}
