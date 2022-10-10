package com.example.fooddonation;

public class EmailUser {
    String Name,Email,Address;
    //String did;
    public EmailUser(){}

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public EmailUser(String name, String email, String add) {
        Name = name;
        Email = email;
        Address = add;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}


