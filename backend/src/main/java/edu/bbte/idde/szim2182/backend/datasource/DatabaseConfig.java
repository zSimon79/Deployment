package edu.bbte.idde.szim2182.backend.datasource;

import lombok.Data;

import java.io.Serializable;

@Data
public class DatabaseConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private String url;
    private String username;
    private String password;


}