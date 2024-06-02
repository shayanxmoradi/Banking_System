package entity;

public abstract class BaseEntity {
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {

        return id;
    }
}
