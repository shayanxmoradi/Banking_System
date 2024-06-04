package service.card;


import entity.CreditCard;

import java.sql.SQLException;
import java.util.List;

public interface CardService {
    boolean addCard(CreditCard card);
    boolean removeCard(CreditCard card) throws SQLException;
    CreditCard getCardByCardName(String cardNumber) throws SQLException;
    CreditCard getCardByAccountName(String accountName);
    List<CreditCard> getCardsByBankName(String bankName) throws SQLException;

    List<CreditCard> getAllCards();

}
