package util;

import config.DataSource;

import java.sql.Connection;

public class ApplicationContext {
    private static final ApplicationContext INSTANCE=new ApplicationContext();
   // private static final   UserService userService;
   // private static final TweetService tweetService;

    private ApplicationContext() {
    }
    public  static ApplicationContext getInstance(){
        return INSTANCE;
    }
    static {
    //    Connection connection = DataSource.getConnection();

       // UserRepository userRepository=new UserRepositoryImpl(connection);
      //  TweetRepository tweetRepository=new TweetRepositoryImpl(connection);

      //  tweetService = new TweetServiceImpl(tweetRepository);
      //  userService = new UserServiceImpl(userRepository,tweetService);


    }

}
