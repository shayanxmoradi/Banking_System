package util;

import com.sun.tools.javac.Main;
import config.DataSource;
import entity.transaction.Transaction;
import menu.MainMenu;
import menu.SignUpMenu;
import menu.login.*;
import menu.util.Input;
import menu.util.Message;
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

    private static MainMenu mainMenu;

    private static final ApplicationContext INSTANCE = new ApplicationContext();


    private ApplicationContext() {
        // do here
        Input input = new Input();
        Message message = new Message();
        Connection connection = DataSource.getConnection();

        UserRepo userRepo = new UserRepoImpl(connection);
        AuthHolder authHolder = new AuthHolder();
        AccountRepo accountRepo = new AccountRepoImpl(connection);
        CardRepo cardRepo = new CardRepoImpl(connection);
        TransactionRepo transactionRepo = new TransactionRepoImp(connection);


        AccountService accountService = new AccountServiceImpl(accountRepo);
        CardService cardService = new CardServiceImpl(cardRepo);
        UserService userService = new UserServiceImpl(userRepo, accountService);
        TransactionService transactionService = new TransactionServiceImpl(transactionRepo);
        SignUpMenu signUpMenu = new SignUpMenu(input, message, userService);
        CardMenu cardMenu = new CardMenu(input, message, accountService, transactionService, cardService);
        MoneyTransactionMenu moneyTransactionMenu = new MoneyTransactionMenu(input, message, accountService, transactionService, cardService);
        AccountMenu accountMenu = new AccountMenu(input, message, userService);

        LoggedInMenu loggedInMenu = new LoggedInMenu(input, message, cardMenu, moneyTransactionMenu, accountMenu);
        LoginMenu loginMenu = new LoginMenu(input, message, userService, authHolder, loggedInMenu);

         mainMenu = new MainMenu(input, message, signUpMenu, loginMenu);


    }

    public static ApplicationContext getInstance() {
        return INSTANCE;
    }

    public MainMenu getMenu() {
        return mainMenu;
    }


}
