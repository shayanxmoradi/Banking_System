package entity;

public class User extends BaseEntity {
    private String username;
    private String password;
    private String name;

    public User(Long id, String username, String password, String name) {
        super(id);
        this.username = username;
        this.password = password;
        this.name = name;
    }

}
