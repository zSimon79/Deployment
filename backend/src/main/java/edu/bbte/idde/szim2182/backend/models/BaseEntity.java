package edu.bbte.idde.szim2182.backend.models;

import lombok.Getter;

@Getter
public abstract class BaseEntity {
    protected Long id;

    public BaseEntity(Long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}