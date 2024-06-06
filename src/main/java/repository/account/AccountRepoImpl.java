package repository.account;

import entity.Account;
import entity.AccountTransaction;
import org.jetbrains.annotations.NotNull;
import util.AuthHolder;

import java.sql.*;
import java.util.List;


public class AccountRepoImpl implements AccountRepo {
    private final Connection connection;

    public AccountRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Account addAccount(Account account) {
        String insertQuery = """
                INSERT INTO account(user_id_fk, user_first_name, name, number, paya_number,
                                    bank_id_fk, bank_name_fk, balance)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
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
                        account.setId(keys.getLong(1));
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
                    return extractAccountFromResultSet(resultSet);
                }
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            throw new RuntimeException("Error fetching account by account name", sqlE);
        }

        return null;
    }

    @Override
    public Account getAccountByAccountNumber(String accountNumber1) {
        String selectQuery = "SELECT * FROM account WHERE number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, accountNumber1);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractAccountFromResultSet(resultSet);
                }
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            throw new RuntimeException("Error fetching account by account number", sqlE);
        }

        return null;
    }

    @Override
    public boolean deleteAccount(Account account) {
        String deleteQuery = "DELETE FROM account WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setLong(1, account.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            throw new RuntimeException("Error deleting account", sqlE);
        }
    }

    @Override
    public boolean updateAccount(Account account) {
        String updateQuery = """
                UPDATE account SET user_id_fk = ?, user_first_name = ?, name = ?, number = ?,
                                    paya_number = ?, bank_id_fk = ?, bank_name_fk = ?, balance = ?
                WHERE id = ?
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setLong(1, account.getUserId());
            preparedStatement.setString(2, account.getUserFristName());
            preparedStatement.setString(3, account.getAccountName());
            preparedStatement.setString(4, account.getAccountNummber());
            preparedStatement.setString(5, account.getPayaNummber());
            preparedStatement.setLong(6, account.getBankId());
            preparedStatement.setString(7, account.getBankName());
            preparedStatement.setDouble(8, account.getBalance());
            preparedStatement.setLong(9, account.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            throw new RuntimeException("Error updating account", sqlE);
        }
    }

    @Override
    public boolean updateAccountBalance(@NotNull Long accountId, double balance) {
        String updateBalanceQuery = """
                UPDATE account SET balance = ?
                WHERE id = ?
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateBalanceQuery)) {
            preparedStatement.setDouble(1, balance);
            preparedStatement.setLong(2, accountId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException SqlE) {
            SqlE.printStackTrace();
            throw new RuntimeException("Error updating balance", SqlE);
        }
    }

    @Override
    public Account getAccountById(Long accountId) {
        String selectQuery = "SELECT * FROM account WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, accountId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractAccountFromResultSet(resultSet);
                }
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            throw new RuntimeException("Error fetching account by ID", sqlE);
        }

        return null;
    }

    private Account extractAccountFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String accountName = resultSet.getString("name");
        String accountNumber = resultSet.getString("number");
        String payaNumber = resultSet.getString("paya_number");
        Long userId = resultSet.getLong("user_id_fk");
        String userFirstName = resultSet.getString("user_first_name");
        Long bankId = resultSet.getLong("bank_id_fk");
        String bankName = resultSet.getString("bank_name_fk");
        Double balance = resultSet.getDouble("balance");

        Account account = new Account(accountName, bankId, bankName, balance);
        account.setId(id);
        account.setAccountNummber(accountNumber);
        account.setPayaNummber(payaNumber);
        account.setUserId(userId);
        account.setUserFristName(userFirstName);

        return account;
    }

    @Override
    public Account getAccountByUserId(Long userId) {
        String selectQuery = "SELECT * FROM account WHERE user_id_fk = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractAccountFromResultSet(resultSet);
                }
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            throw new RuntimeException("Error fetching account by user ID", sqlE);
        }

        return null;
    }

    @Override
    public boolean performBatchTransactions(Long userId, List<AccountTransaction> transactions, double fee) {
        try {
            connection.setAutoCommit(false);

            // Fetch the starter account
            Account starterAccount = getAccountByUserId(userId);

            // Deduct the total amount and fee from the starter account
            double totalAmount = transactions.stream().mapToDouble(AccountTransaction::getAmount).sum();
            boolean reducingProcessIsSuccess = updateAccountBalance(starterAccount.getId(), starterAccount.getBalance() - totalAmount - fee);

            if (!reducingProcessIsSuccess) {
                throw new RuntimeException("Failed to deduct the amount from the starter account");
            }

            // Prepare the batch for updating the destination accounts
            String updateQuery = "UPDATE account SET balance = balance + ? WHERE number = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                for (AccountTransaction transaction : transactions) {
                    Account destAccount = getAccountByAccountNumber(transaction.getDestAccountNumber());
                    if (destAccount == null) {
                        throw new RuntimeException("Destination account not found: " + transaction.getDestAccountNumber());
                    }

                    preparedStatement.setDouble(1, transaction.getAmount());
                    preparedStatement.setString(2, transaction.getDestAccountNumber());
                    preparedStatement.addBatch();
                }

                // Execute batch update
                int[] updateCounts = preparedStatement.executeBatch();
                for (int count : updateCounts) {
                    if (count != 1) {
                        throw new RuntimeException("Failed to update a destination account balance");
                    }
                }
            }

            // Commit the transaction
            connection.commit();
            return true;

        } catch (SQLException | RuntimeException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            throw new RuntimeException("Batch transfer failed", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
