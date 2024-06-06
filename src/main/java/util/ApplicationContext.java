package util;

import config.DataSource;
import entity.transaction.Transaction;
import repository.Transaction.TransactionRepo;
import repository.Transaction.TransactionRepoImp;
import repository.account.AccountRepo;
import repository.account.AccountRepoImpl;
import repository.card.CardRepo;
import repository.card.CardRepoImpl;
import repository.user.UserRepo;
import repository.user.UserRepoImpl;
import service.acount.AccountService;
import service.acount.AccountServiceImpl;
import service.card.CardService;
import service.card.CardServiceImpl;
import service.transaction.TransactionService;
import service.transaction.TransactionServiceImpl;
import service.user.UserService;
import service.user.UserServiceImpl;

import java.sql.Connection;

public class ApplicationContext {
    private static final ApplicationContext INSTANCE = new ApplicationContext();

    private static final UserService userService;
    private static final AccountService accountService;
    private static final CardService cardService;
    private static final TransactionService transactionService;

    private ApplicationContext() {
    }

    public static ApplicationContext getInstance() {
        return INSTANCE;
    }

    static {
        Connection connection = DataSource.getConnection();

        UserRepo userRepo = new UserRepoImpl(connection);
        AccountRepo accountRepo = new AccountRepoImpl(connection);
        CardRepo cardRepo = new CardRepoImpl(connection);
        TransactionRepo transactionRepo = new TransactionRepoImp(connection);

        accountService = new AccountServiceImpl(accountRepo);
        cardService = new CardServiceImpl(cardRepo);
        userService = new UserServiceImpl(userRepo, accountService);
        transactionService = new TransactionServiceImpl(transactionRepo);


        // UserRepository userRepository=new UserRepositoryImpl(connection);
        //  TweetRepository tweetRepository=new TweetRepositoryImpl(connection);

        //  tweetService = new TweetServiceImpl(tweetRepository);
        //  userService = new UserServiceImpl(userRepository,tweetService);


    }

    public UserService getUserService() {
        return userService;
    }
    public CardService getCardService () {
        return cardService;
    }
    public AccountService getAccountService () {
        return accountService;
    }
    public TransactionService getTransactionService () {
        return transactionService;
    }

}
