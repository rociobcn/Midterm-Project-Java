package com.Ironhack.MidTermProject.Models.User;

import javax.persistence.Entity;

@Entity
public class Admin extends User{

    public Admin() {
    }

    public Admin(String username, String password, String fullName) {
        super(username, password, fullName);
    }
}
