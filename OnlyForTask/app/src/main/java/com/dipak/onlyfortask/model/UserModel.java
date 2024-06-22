package com.dipak.onlyfortask.model;

public class UserModel {

    String name;
    String number;
    String email;

    public UserModel(String name, String number, String email) {
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }
}
