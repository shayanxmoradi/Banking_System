package entity;

import java.util.ArrayList;
import java.util.List;

public class User extends BaseEntity {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
   // private List<Long > idOfUserAccounts= new ArrayList<>();

    public User( String username, String password) {
        this.username = username;
        this.password = password;

    }



    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
