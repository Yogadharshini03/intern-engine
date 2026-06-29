package com.example.internengine.dto;

import lombok.Getter;

public class RegisterRequest {
    @Getter
    private String name;
    @Getter
    private String email;
    @Getter
    private String password;
    @Getter
    private String role;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {

        this.role = role;
    }
}
