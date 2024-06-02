package service;

import entity.User;
import repository.UserRepo;
import util.AuthHolder;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserRepo userRepoImpl;

    public UserServiceImpl(UserRepo userRepoImpl) {
        this.userRepoImpl = userRepoImpl;
    }


    @Override
    public boolean login(String username, String password) throws SQLException {
        User user = userRepoImpl.findByUsernamePassword(username, password);
        if (user != null) {
            AuthHolder.tokenId = user.getId();
            AuthHolder.tokenName = user.getUsername();
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
}
