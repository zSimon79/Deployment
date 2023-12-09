package edu.bbte.idde.szim2182.backend.datasource;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApplicationConfig implements Serializable {
    private transient DatabaseConfig databaseConfig;
    private LoginConfig loginConfig;
    private String profile;

}