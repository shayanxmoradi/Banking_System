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
    public Account getAccountByAccountName(String username) {
        //todo select by account name
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
}
