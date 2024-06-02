package entity;

public class User extends BaseEntity {
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public User(Long id, String username, String password, String firstName,String lastName) {
        super(id);
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
