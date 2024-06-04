package repository.card;

import entity.CreditCard;

import java.sql.SQLException;
import java.util.List;

public interface CardRepo {
    CreditCard addCard(CreditCard card);

    boolean removeCard(CreditCard card) throws SQLException;

    CreditCard getCardByName(String name) throws SQLException;

    List<CreditCard> getCardsByBankName(String bankName) throws SQLException;

    CreditCard getCardByAccountName(String accountName);

    List<CreditCard> getAllCards();

}
