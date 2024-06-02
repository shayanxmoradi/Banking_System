package service;

import repository.UserRepo;
import repository.UserRepoImpl;

public class UserServiceImpl implements UserService {
    private final UserRepo userRepoImpl;

    public UserServiceImpl(UserRepo userRepoImpl) {
        this.userRepoImpl = userRepoImpl;
    }
}
