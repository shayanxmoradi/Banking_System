package repository.card;

import entity.Account;
import entity.CreditCard;
import util.AuthHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardRepoImpl implements CardRepo {
    private final Connection connection;

    public CardRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public CreditCard addCard(CreditCard card) {

        String insertQuery = """
                            insert into card ( number,  is_active,
                              account_id_fk, name, expire_date, ccv2,user_id_fk,bank_name
                              
                ) values (?,?,?,?,?,?,?,?)
                            """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            //  preparedStatement.setInt(1, card.getId().intValue());
            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setBoolean(2, card.isActive());
            preparedStatement.setLong(3, card.getAccountId());
            preparedStatement.setString(4, card.getCardName().toLowerCase());
            preparedStatement.setDate(5, Date.valueOf(card.getExpiryDate()));
            preparedStatement.setInt(6, card.getCvv());
            preparedStatement.setInt(7, AuthHolder.totkenUserId.intValue());
            preparedStatement.setString(8, card.getBankName());

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
    public List<CreditCard> getCardsByBankName(String bankName) throws SQLException {
        String selectQuery = """
                                select * from card  
                where bank_name = ? AND user_id_fk =?
                                """;
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setString(1, bankName.toLowerCase());
        preparedStatement.setInt(2, AuthHolder.totkenUserId.intValue());
        ResultSet resultSet = preparedStatement.executeQuery();
        List<CreditCard> cards = new ArrayList<>();
        while (resultSet.next()) {
            CreditCard card = new CreditCard();
            card.setCardNumber(resultSet.getString("number"));
            card.setBankName(resultSet.getString("bank_name"));
            card.setActive(resultSet.getBoolean("is_active"));
            card.setAccountId(resultSet.getLong("account_id_fk"));
            card.setCardName(resultSet.getString("name"));
            card.setExpiryDate(resultSet.getDate("expire_date").toLocalDate());
            card.setCvv(resultSet.getInt("ccv2"));
            cards.add(card);
        }
        return cards;
    }


    @Override
    public CreditCard getCardByAccountName(String accountName) {
        return null;
    }

    @Override
    public List<CreditCard> getAllCards() throws SQLException {
        String selectQuery = """
                                select * from card  
                where  user_id_fk =?
                                """;
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setInt(1, AuthHolder.totkenUserId.intValue());

        ResultSet resultSet = preparedStatement.executeQuery();
        List<CreditCard> cards = new ArrayList<>();
        while (resultSet.next()) {
            CreditCard card = new CreditCard();
            card.setCardNumber(resultSet.getString("number"));
            card.setBankName(resultSet.getString("bank_name"));
            card.setActive(resultSet.getBoolean("is_active"));
            card.setAccountId(resultSet.getLong("account_id_fk"));
            card.setCardName(resultSet.getString("name"));
            card.setExpiryDate(resultSet.getDate("expire_date").toLocalDate());
            card.setCvv(resultSet.getInt("ccv2"));
            cards.add(card);
        }
        return cards;
    }

    @Override
    public Account getAccountByCardNumber(String cardNumber) throws SQLException {
        String selectQuery = """
                                         select a.id, a.name, a.number,   a.user_id_fk, a.bank_name_fk,  a.user_first_name,  a.paya_number, a.bank_id_fk, a.balance
                         from card  
                                  inner join banking.account a on a.id = card.account_id_fk
                where card.number = ?
                                         """;
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setString(1, cardNumber);

        ResultSet resultSet = preparedStatement.executeQuery();
        Account account = new Account();
        if (resultSet.next()) {

            account.setAccountName(resultSet.getString("name"));
            account.setBankId((long) resultSet.getInt("bank_id_fk"));
            account.setUserId((long) resultSet.getInt("user_id_fk"));
            account.setPayaNummber(resultSet.getString("paya_number"));
            account.setBalance(resultSet.getDouble("balance"));
            account.setUserFristName(resultSet.getString("user_first_name"));
            account.setId((long) resultSet.getInt("id"));
            System.out.println("id of retrieved Acc"+resultSet.getInt("id"));
            account.setBankName(resultSet.getString("bank_name_fk"));
        }
        return account;
    }
}
