package repository.card;

import entity.CreditCard;

import java.util.List;

public interface CardRepo {
    CreditCard addCard(CreditCard card);
    boolean removeCard(CreditCard card);
    CreditCard getCardByCardName(String cardNumber);
    CreditCard getCardByAccountName(String accountName);
    List<CreditCard> getAllCards();

}
