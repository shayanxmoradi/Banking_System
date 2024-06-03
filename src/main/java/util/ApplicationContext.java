package util;

import config.DataSource;
import repository.AccountRepo;
import repository.AccountRepoImpl;
import repository.UserRepo;
import repository.UserRepoImpl;
import service.AccountService;
import service.AccountServiceImpl;
import service.UserService;
import service.UserServiceImpl;

import java.sql.Connection;

public class ApplicationContext {
    private static final ApplicationContext INSTANCE = new ApplicationContext();

    private static final UserService userService;
    private static final AccountService accountService;

    private ApplicationContext() {
    }

    public static ApplicationContext getInstance() {
        return INSTANCE;
    }

    static {
        Connection connection = DataSource.getConnection();
        UserRepo userRepo = new UserRepoImpl(connection);
AccountRepo accountRepo = new AccountRepoImpl(connection);

        accountService = new AccountServiceImpl( accountRepo);
        userService = new UserServiceImpl(userRepo, accountService);


        // UserRepository userRepository=new UserRepositoryImpl(connection);
        //  TweetRepository tweetRepository=new TweetRepositoryImpl(connection);

        //  tweetService = new TweetServiceImpl(tweetRepository);
        //  userService = new UserServiceImpl(userRepository,tweetService);


    }
    public UserService getUserService() {
        return userService;
    }

}
