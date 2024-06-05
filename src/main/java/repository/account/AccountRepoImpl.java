package repository.account;

import entity.Account;
import org.jetbrains.annotations.NotNull;
import util.AuthHolder;

import java.sql.*;

public class AccountRepoImpl implements AccountRepo {
    private final Connection connection;

    public AccountRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Account addAccount(Account account) {
        String insertQuery = """
                            insert into account(user_id_fk,user_first_name,name,number,paya_number,
                                                bank_id_fk,bank_name_fk,balance
                ) values (?,?,?,?,?,?,?,?)
                            """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, AuthHolder.totkenUserId.intValue());
            preparedStatement.setString(2, AuthHolder.tokenUsername);
            preparedStatement.setString(3, account.getAccountName());
            preparedStatement.setString(4, account.getAccountNummber());
            preparedStatement.setString(5, account.getPayaNummber());
            preparedStatement.setInt(6, account.getBankId().intValue());
            preparedStatement.setString(7, account.getBankName());
            preparedStatement.setDouble(8, account.getBalance());

            if (preparedStatement.executeUpdate() > 0) {
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        account.setId((long) keys.getInt("id"));
                        return account;
                    }
                }
            }
        } catch (SQLException SqlE) {
            SqlE.printStackTrace();
            throw new RuntimeException("Error adding account", SqlE);
        }
        return null;
    }


    @Override
    public Account getAccountByAccountName(String accountName1) {
        String selectQuery = "SELECT * FROM account WHERE name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, accountName1);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String accountName = resultSet.getString("name");
                    String accountNumber = resultSet.getString("number");
                    String payaNumber = resultSet.getString("paya_number");
                    Long userId = resultSet.getLong("user_id_fk");
                    String userFirstName = resultSet.getString("user_first_name");
                    Long bankId = resultSet.getLong("bank_id_fk");
                    String bankName = resultSet.getString("bank_name_fk");
                    Double balance = resultSet.getDouble("balance");
                    Account account = new Account(accountName, accountNumber, payaNumber, userId, userFirstName, bankId, bankName, balance);
                    account.setId(id);
                    return account;
                }
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            throw new RuntimeException("Error fetching account by ID", sqlE);
        }

        return null;
    }

    @Override
    public Account getAccountByAccountNumber(String accountNumber1) {
        String selectQuery = "SELECT * FROM account WHERE name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, accountNumber1);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String accountName = resultSet.getString("name");
                    String accountNumber = resultSet.getString("number");
                    String payaNumber = resultSet.getString("paya_number");
                    Long userId = resultSet.getLong("user_id_fk");
                    String userFirstName = resultSet.getString("user_first_name");
                    Long bankId = resultSet.getLong("bank_id_fk");
                    String bankName = resultSet.getString("bank_name_fk");
                    Double balance = resultSet.getDouble("balance");
                    Account account = new Account(accountName, accountNumber, payaNumber, userId, userFirstName, bankId, bankName, balance);
                    account.setId(id);
                    return account;
                }
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            throw new RuntimeException("Error fetching account by ID", sqlE);
        }

        return null;
    }


    @Override
    public boolean deleteAccount(Account account) {
        return false;
    }

    @Override
    public boolean updateAccount(Account account) {
        return false;
    }

    @Override
    public boolean updateAccountBalance(@NotNull Long accountId, double balance) {
//todo
        String insertQuery = """
                UPDATE account SET balance=  ? 
                WHERE id=?
                            """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setDouble(1, balance);
            preparedStatement.setInt(2, accountId.intValue());

            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException SqlE) {
            SqlE.printStackTrace();
            throw new RuntimeException("Error updating Balance", SqlE);
        }
        return false;
    }

    @Override
    public Account getAccountById(Long accountId) {
        String selectQuery = "SELECT * FROM account WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, accountId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String accountName = resultSet.getString("name");
                    String accountNumber = resultSet.getString("number");
                    String payaNumber = resultSet.getString("paya_number");
                    Long userId = resultSet.getLong("user_id_fk");
                    String userFirstName = resultSet.getString("user_first_name");
                    Long bankId = resultSet.getLong("bank_id_fk");
                    String bankName = resultSet.getString("bank_name_fk");
                    Double balance = resultSet.getDouble("balance");
                    Account account = new Account(accountName, accountNumber, payaNumber, userId, userFirstName, bankId, bankName, balance);
                    account.setId(id);
                    return account;
                }
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            throw new RuntimeException("Error fetching account by ID", sqlE);
        }

        return null;
    }
}
