package service.user;

import entity.Account;
import entity.User;
import repository.user.UserRepo;
import service.acount.AccountService;
import util.AuthHolder;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserRepo userRepoImpl;
    private final AccountService accountService;
    private final AuthHolder AUTH_HOLDER;



    public UserServiceImpl(UserRepo userRepoImpl, AccountService accountService, AuthHolder authHolder) {
        this.userRepoImpl = userRepoImpl;
        this.accountService = accountService;
        this.AUTH_HOLDER = authHolder;
    }


    @Override
    public boolean login(String username, String password) throws SQLException {
        User user = userRepoImpl.findByUsernamePassword(username, password);
        if (user != null) {
            AUTH_HOLDER.setTotkenUserId( user.getId());
            AUTH_HOLDER.setTokenUsername( user.getUsername());
            return true;
        }

        return false;
    }

    /**
     * watch out double way of checking existing user
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public boolean signUp(User user) throws SQLException {
        // uncomment this if you think you need extra Query XD
//        if (userRepoImpl.isUserExist(user.getUsername())) {
//            System.out.println(" This user already exists");
//            return false;
//        }

        return userRepoImpl.addUser(user) != null;

    }

    @Override
    public boolean createAccount(Account account) {
      return   accountService.createAccount(account);
    }

}
