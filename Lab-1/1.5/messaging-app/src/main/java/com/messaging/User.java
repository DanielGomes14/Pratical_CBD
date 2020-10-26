package com.messaging;


public class User {
    private String username;
    private String password;

    public User(String username,String name){
        this.username=username;
        this.password=password;
    }


    public void setPassword(String password){
        this.password=password;
    }
    public String getUsername(){
        return this.username;
    }
}
