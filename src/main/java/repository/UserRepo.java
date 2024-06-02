package repository;

import entity.User;

public interface UserRepo {
    boolean addUser(User user);
    User findByUsernamePassword(String username, String password);
    boolean deleteUser(User user);
    User getUserByUsername(String username);
    User getUserByID(Long id);
    boolean isUserExist(String username);

}
