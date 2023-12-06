package edu.bbte.idde.szim2182.backend.datasource;

import java.io.Serializable;

public class ApplicationConfig implements Serializable {
    private transient DatabaseConfig databaseConfig;
    private LoginConfig loginConfig; // Add this field

    private String profile;

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public LoginConfig getLoginConfig() {
        return loginConfig;
    }

    public void setLoginConfig(LoginConfig loginConfig) {
        this.loginConfig = loginConfig;
    }
}