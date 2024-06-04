package repository.card;

import entity.CreditCard;
import util.AuthHolder;

import java.sql.*;
import java.util.List;

public class CardRepoImpl implements CardRepo {
    private final Connection connection;

    public CardRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public CreditCard addCard(CreditCard card) {

        String insertQuery = """
                            insert into card ( number, balance, is_active,
                              account_id_fk, name, expire_date, ccv2,user_id_fk
                              
                ) values (?,?,?,?,?,?,?,?)
                            """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            //  preparedStatement.setInt(1, card.getId().intValue());
            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setDouble(2, card.getBalance());
            preparedStatement.setBoolean(3, card.isActive());
            preparedStatement.setLong(4, card.getAccountId());
            preparedStatement.setString(5, card.getCardName().toLowerCase());
            preparedStatement.setDate(6, Date.valueOf(card.getExpiryDate()));
            preparedStatement.setInt(7, card.getCvv());
            preparedStatement.setInt(8, AuthHolder.totkenUserId.intValue());

            if (preparedStatement.executeUpdate() > 0) {
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        card.setId((long) keys.getInt("id"));
                        return card;
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
    public boolean removeCard(CreditCard card) throws SQLException {
        String selectQuery = """
                                Delete  
                From card c where c.id= ?
                                """;
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setInt(1, card.getId().intValue());
        int affectedRows = preparedStatement.executeUpdate();
        preparedStatement.close();
        return affectedRows > 0;

    }

    @Override
    public CreditCard getCardByName(String name) throws SQLException {
        String selectQuery = """
                                select * from card  
                where name = ? AND user_id_fk =?
                                """;
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setString(1, name.toLowerCase());
        preparedStatement.setInt(2, AuthHolder.totkenUserId.intValue());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            CreditCard card = new CreditCard();
            card.setCardNumber(resultSet.getString("number"));
            card.setBalance(resultSet.getDouble("balance"));
            card.setActive(resultSet.getBoolean("is_active"));
            card.setAccountId(resultSet.getLong("account_id_fk"));
            card.setCardName(resultSet.getString("name"));
            card.setExpiryDate(resultSet.getDate("expire_date").toLocalDate());
            card.setCvv(resultSet.getInt("ccv2"));
            return card;

        }

        return null;
    }



    @Override
    public CreditCard getCardByAccountName(String accountName) {
        return null;
    }

    @Override
    public List<CreditCard> getAllCards() {
        return List.of();
    }
}
