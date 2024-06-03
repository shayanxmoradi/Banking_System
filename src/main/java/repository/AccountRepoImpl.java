package repository;

import entity.Account;
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
            preparedStatement.setString(3,account.getAccountName());
            preparedStatement.setString(4,account.getAccountNummber());
            preparedStatement.setString(5,account.getPayaNummber());
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
            throw new RuntimeException("Error saving tweet", SqlE);
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
}
