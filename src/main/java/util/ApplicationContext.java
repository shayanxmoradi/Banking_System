package util;

import config.DataSource;
import repository.UserRepo;
import repository.UserRepoImpl;
import service.UserService;
import service.UserServiceImpl;

import java.sql.Connection;

public class ApplicationContext {
    private static final ApplicationContext INSTANCE=new ApplicationContext();
   // private static final   UserService userService;
   // private static final TweetService tweetService;
    private static final UserService userService;

    private ApplicationContext() {
    }
    public  static ApplicationContext getInstance(){
        return INSTANCE;
    }
    static {
   Connection connection = DataSource.getConnection();
   UserRepo userRepo = new UserRepoImpl(connection);


userService = new UserServiceImpl(userRepo);

       // UserRepository userRepository=new UserRepositoryImpl(connection);
      //  TweetRepository tweetRepository=new TweetRepositoryImpl(connection);

      //  tweetService = new TweetServiceImpl(tweetRepository);
      //  userService = new UserServiceImpl(userRepository,tweetService);


    }

}
