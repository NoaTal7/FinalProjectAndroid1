package com.example.sela.finalprojectandroid1;

public class User
{
    private String email;
    private String id;
    private String phoneNumber;
    private String name;
    private String password;
    private int score;

    public User() {
    }

    public User(String email, String id, String phoneNumber, String name, String password) {
        this.email = email;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
