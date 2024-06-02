package repository;

import java.sql.Connection;

public class UserRepoImpl implements UserRepo {
    private final Connection connection;

    public UserRepoImpl(Connection connection) {
        this.connection = connection;
    }
}
