package entity;

public class Bank extends BaseEntity {
    private String name;
    private String address;
    private Long userId;

    public Bank( String name, String address, Long userId) {

        this.name = name;
        this.address = address;
        this.userId = userId;
    }
}
