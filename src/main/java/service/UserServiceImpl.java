package service;

import entity.User;
import repository.UserRepo;
import repository.UserRepoImpl;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserRepo userRepoImpl;

    public UserServiceImpl(UserRepo userRepoImpl) {
        this.userRepoImpl = userRepoImpl;
    }

    @Override
    public boolean login(String username, String password) throws SQLException {
        return  userRepoImpl.findByUsernamePassword(username, password) != null;
    }

    @Override
    public boolean signUp(User user) throws SQLException {
      if (userRepoImpl.isUserExist(user.getUsername())){
          System.out.println(" This user already exists");
          return false;
      }
      userRepoImpl.addUser(user);
      return true;

    }
}
