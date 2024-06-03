package repository.user;

import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepoImpl implements UserRepo {
    private final Connection connection;

    public UserRepoImpl(Connection connection) {
        this.connection = connection;
    }

    public User addUser(User user) throws SQLException {
        String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        user.setId(resultSet.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                return null;
            } else {
                throw e;
            }
        }

        return user;
    }

    @Override
    public User findByUsernamePassword(String username, String password)throws SQLException {
        User user = null;

        String insertQuery = """
                SELECT * from users where username =? and password=?
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            user = new User(resultSet.getString("username"),
                    resultSet.getString("password"));
            user.setId((long) resultSet.getInt("id"));

        }
        resultSet.close();
        preparedStatement.close();
        return user;
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

    /**
     * carefull this uses an extra Query,meanwhile we can check for duplicate username
     * easier by managing sql exceptions
     * @param username
     * @return
     * @throws SQLException
     */
    @Override
    public boolean isUserExist(String username) throws SQLException {

        String insertQuery = """
                SELECT count(id)  from users where username =? 
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean result = resultSet.next() && resultSet.getInt(1) > 0;
        resultSet.close();
        preparedStatement.close();
        return result;

    }
}
