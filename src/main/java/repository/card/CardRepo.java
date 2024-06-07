package repository.card;

import entity.Account;
import entity.CreditCard;

import java.sql.SQLException;
import java.util.List;

public interface CardRepo {
    CreditCard addCard(CreditCard card);

    boolean removeCard(CreditCard card) throws SQLException;

    CreditCard getCardByName(String name) throws SQLException;
    CreditCard getCardByNumber(String number) throws SQLException;

    List<CreditCard> getCardsByBankName(String bankName) throws SQLException;

    List<CreditCard> getAllCards() throws SQLException;

    Account getAccountByCardNumber(String cardNumber) throws SQLException;

    CreditCard getCardByAccountName(String accountName);


}
