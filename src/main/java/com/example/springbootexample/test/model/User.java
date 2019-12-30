package com.example.springbootexample.test.model;

public class User {
    private String userName;
    private String passWorld;

    public User() {}

    public User(String userName, String passWorld) {
        this.userName = userName;
        this.passWorld = passWorld;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWorld() {
        return passWorld;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWorld(String passWorld) {
        this.passWorld = passWorld;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", passWorld='" + passWorld + '\'' +
                '}';
    }
}
