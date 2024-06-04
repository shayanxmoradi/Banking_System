package entity;

public class Bank extends BaseEntity {
    private String name;
    private String address;
    private String branch;


    public Bank( String name, String address) {

        this.name = name;
        this.address = address;

    }
}
