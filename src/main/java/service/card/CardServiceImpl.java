package service.card;

import entity.Account;
import entity.CreditCard;
import repository.card.CardRepo;

import java.sql.SQLException;
import java.util.List;

public class CardServiceImpl implements CardService {
    private final CardRepo cardRepo;

    public CardServiceImpl(CardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }

    @Override
    public boolean addCard(CreditCard card) {
       return cardRepo.addCard(card)!=null;
    }

    @Override
    public boolean removeCard(CreditCard card) throws SQLException {
        return cardRepo.removeCard(card);
    }



    @Override
    public CreditCard getCardByCardName(String cardNumber) throws SQLException {
        return cardRepo.getCardByName(cardNumber);
    }

    @Override
    public List<CreditCard> getAllCards() throws SQLException {
        return cardRepo.getAllCards();
    }

    @Override
    public CreditCard getCardByAccountName(String accountName) {
        return null;
    }

    @Override
    public List<CreditCard> getCardsByBankName(String bankName) throws SQLException {
        return cardRepo.getCardsByBankName(bankName);
    }

    @Override
    public Account getAccountByCardNumber(String cardNumber) throws SQLException {
        return cardRepo.getAccountByCardNumber(cardNumber);
    }

    @Override
    public CreditCard getCardByNumber(String number) throws SQLException {
        return cardRepo.getCardByNumber(number);
    }


}
