package repository.Transaction;

import entity.transaction.Transaction;
import entity.transaction.enums.TransactionStatus;
import entity.transaction.enums.TransactionType;
import util.AuthHolder;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionRepoImp implements TransactionRepo {
    private final Connection connection;

    public TransactionRepoImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addTransaction(Transaction transaction) {
        String insertQuery = """
                    INSERT INTO transactions ( amount, sender_user_id, transaction_time, transaction_date, 
                              transaction_fee, sender_account_number, receiver_account_number, sender_account_id, 
                              reciver_account_id,type,transaction_status)
                    VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?,cast(? as transaction_type_enum)
                                                       ,cast(? as transaction_status_enum)
                                                        )
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
//todo fukn type is missing
            preparedStatement.setDouble(1, transaction.getAmount());
            preparedStatement.setLong(2, transaction.getSenderUserId());
            preparedStatement.setTime(3, java.sql.Time.valueOf(transaction.getTransactionTime()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(transaction.getTransactionDate()));
            preparedStatement.setDouble(5, transaction.getTransactionFee());
            preparedStatement.setString(6, transaction.getSenderAccountNummber());
            preparedStatement.setString(7, transaction.getReceiverAccountNummber());
            preparedStatement.setLong(8, transaction.getSenderId());
            preparedStatement.setLong(9, transaction.getReceiverId());
            preparedStatement.setString(10, transaction.getType().name());
            preparedStatement.setString(11, transaction.getTransactionStatus().name());

            if (preparedStatement.executeUpdate() > 0) {
                try (var keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        transaction.setId((long) keys.getInt(1));
                        return !(transaction == null);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding transaction", e);
        }

        return false;
    }

    @Override
    public boolean deleteTransaction(Transaction transaction) {
        String deleteQuery = "DELETE FROM transactions WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setLong(1, transaction.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting transaction", e);
        }
    }

    @Override
    public boolean updateTransaction(Transaction transaction) {
        String updateQuery = """
                    UPDATE transactions SET type = ?, transaction_status = ?, amount = ?, sender_user_id = ?, transaction_time = ?, transaction_date = ?, transaction_fee = ?, sender_account_number = ?, receiver_account_number = ?, sender_account_id = ?, reciver_account_id = ?
                    WHERE id = ?
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, transaction.getType().name());
            preparedStatement.setString(2, transaction.getTransactionStatus().name());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setLong(4, transaction.getSenderUserId());
            preparedStatement.setTime(5, java.sql.Time.valueOf(transaction.getTransactionTime()));
            preparedStatement.setDate(6, java.sql.Date.valueOf(transaction.getTransactionDate()));
            preparedStatement.setDouble(7, transaction.getTransactionFee());
            preparedStatement.setString(8, transaction.getSenderAccountNummber());
            preparedStatement.setString(9, transaction.getReceiverAccountNummber());
            preparedStatement.setLong(10, transaction.getSenderId());
            preparedStatement.setLong(11, transaction.getReceiverId());
            preparedStatement.setLong(12, transaction.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating transaction", e);
        }
    }

    @Override
    public List<Transaction> getAllTransactions() {
        String selectQuery = "SELECT * FROM transactions";
        List<Transaction> transactions = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                transactions.add(createTransactionFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving all transactions", e);
        }

        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByUserId(int userId) {
        String selectQuery = "SELECT * FROM transactions WHERE sender_user_id = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(createTransactionFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving transactions by user ID", e);
        }

        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByUserIdWithType(int userId, TransactionType transactionType) {
        String selectQuery = "SELECT * FROM transactions WHERE sender_user_id = ? AND type = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, transactionType.name());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(createTransactionFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving transactions by user ID and type", e);
        }

        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByUserIdWithAmount(int userId, float minAmount) {
        String selectQuery = "SELECT * FROM transactions WHERE sender_user_id = ? AND amount >= ?";
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setFloat(2, minAmount);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(createTransactionFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving transactions by user ID and amount", e);
        }

        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByUserIdWithInDate(int userId, Date startDate, Date endDate) {
        String selectQuery = "SELECT * FROM transactions WHERE sender_user_id = ? AND transaction_date BETWEEN ? AND ?";
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(3, new java.sql.Date(endDate.getTime()));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(createTransactionFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving transactions by user ID within date range", e);
        }

        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByUserIdWithDay(int userId, Date getDateFrom) {
        String selectQuery = "SELECT * FROM transactions WHERE sender_user_id = ? AND transaction_date = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setDate(2, new java.sql.Date(getDateFrom.getTime()));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(createTransactionFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving transactions by user ID on specific day", e);
        }

        return transactions;
    }

    private Transaction createTransactionFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        TransactionType type;
        TransactionStatus transactionStatus;
        try {
            type = TransactionType.valueOf(resultSet.getString("type"));
            transactionStatus = TransactionStatus.valueOf(resultSet.getString("transaction_status"));

        } catch (Exception e) {
            type = TransactionType.NORMAL;
            transactionStatus = TransactionStatus.SUCCESSFUL;
        }
        Double amount = resultSet.getDouble("amount");
        Long senderUserId = resultSet.getLong("sender_user_id");
        LocalTime transactionTime = resultSet.getTime("transaction_time").toLocalTime();
        LocalDate transactionDate = resultSet.getDate("transaction_date").toLocalDate();
        double transactionFee = resultSet.getDouble("transaction_fee");
        String senderAccountNummber = resultSet.getString("sender_account_number");
        String receiverAccountNummber = resultSet.getString("receiver_account_number");
        Long senderId = resultSet.getLong("sender_account_id");
        Long receiverId = resultSet.getLong("reciver_account_id");

        Transaction transaction = new Transaction(type, transactionStatus, amount, senderUserId,  transactionFee);
        transaction.setId(id);
        transaction.setTransactionDate(transactionDate);
        transaction.setTransactionDate(transactionDate);
        transaction.setTransactionTime(transactionTime);

        transaction.setSenderAccountNummber(senderAccountNummber);
        transaction.setReceiverAccountNummber(receiverAccountNummber);
        transaction.setSenderId(senderId);
        transaction.setReceiverId(receiverId);
        transaction.setType(type);
        transaction.setTransactionStatus(transactionStatus);
        return transaction;
    }
}