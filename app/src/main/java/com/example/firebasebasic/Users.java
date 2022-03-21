package com.example.firebasebasic;

public class Users {


    String name;
    String email;

    public Users(){

    }

    public Users( String name, String email) {

        this.name = name;
        this.email = email;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
