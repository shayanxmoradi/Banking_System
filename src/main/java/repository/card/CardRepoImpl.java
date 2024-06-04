package repository.card;

import entity.CreditCard;

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
                              account_id_fk, account_name, expire_date, ccv2
                              
                ) values (?,?,?,?,?,?,?)
                            """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
          //  preparedStatement.setInt(1, card.getId().intValue());
            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setDouble(2, card.getBalance());
            preparedStatement.setBoolean(3, card.isActive());
            preparedStatement.setLong(4, card.getAccountId());
            preparedStatement.setString(5, card.getAccountName());
            preparedStatement.setDate(6, Date.valueOf( card.getExpiryDate()));
            preparedStatement.setInt(7, card.getCvv());

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
    public boolean removeCard(CreditCard card) {
        return false;
    }

    @Override
    public CreditCard getCardByCardName(String cardNumber) {
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
