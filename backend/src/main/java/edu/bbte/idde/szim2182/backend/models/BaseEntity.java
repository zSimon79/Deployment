package edu.bbte.idde.szim2182.backend.models;

public abstract class BaseEntity {
    protected Long id;

    // Constructor to set the id
    public BaseEntity(Long id) {
        this.id = id;
    }

    // Getter and setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}