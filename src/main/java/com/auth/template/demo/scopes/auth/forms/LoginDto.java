package com.auth.template.demo.scopes.auth.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginDto {
    @NotBlank
    @Size(max = 200)
    public String username;

    @NotBlank
    @Size(max = 200)
    public String password;


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


    @Override
    public String toString() {
        return "LoginDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
