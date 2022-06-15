package com.Ironhack.MidTermProject.Models.User;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class ThirdParty extends User{
    @NotNull
    private String hashedKey;

    public ThirdParty(String hashedKey) {
        this.hashedKey = hashedKey;
    }

    public ThirdParty(String username, String password, String fullName, String hashedKey) {
        super(username, password, fullName);
        this.hashedKey = hashedKey;
    }

    public ThirdParty() {
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }
}
