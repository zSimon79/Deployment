package edu.bbte.idde.szim2182.backend.datasource;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginConfig implements Serializable {
    private String username;
    private String password;


}
