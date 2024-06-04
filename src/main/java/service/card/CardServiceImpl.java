package service.card;

import entity.CreditCard;
import repository.card.CardRepo;
import repository.card.CardRepoImpl;

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
    public CreditCard getCardByAccountName(String accountName) {
        return null;
    }

    @Override
    public List<CreditCard> getAllCards() {
        return List.of();
    }
}
