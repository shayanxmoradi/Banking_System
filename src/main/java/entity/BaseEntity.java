package entity;

public abstract class BaseEntity {
    private Long id;

    public BaseEntity(Long id) {
        this.id = id;
    }

    public Long getId() {

        return id;
    }
}
