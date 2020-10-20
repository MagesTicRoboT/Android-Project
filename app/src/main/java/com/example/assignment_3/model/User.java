package com.example.assignment_3.model;

public class User {
    private String FName;
    private String LName;
    private String UserName;
    private String Email;
    private String Address;
    private String Password;

    public User(String FName, String LName, String userName, String email, String address, String password) {
        this.FName = FName;
        this.LName = LName;
        UserName = userName;
        Email = email;
        Address = address;
        Password = password;
    }

    public User(String userName, String password) {

        UserName = userName;
        Password = password;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
