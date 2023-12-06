package edu.bbte.idde.szim2182.backend.datasource;

import java.io.Serializable;

public class DatabaseConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private String url;
    private String username;
    private String password;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}