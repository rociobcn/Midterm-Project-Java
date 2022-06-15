package com.Ironhack.MidTermProject.Models.User;

import com.Ironhack.MidTermProject.Models.User.User;
import com.Ironhack.MidTermProject.Embeddables.Address;
import com.Ironhack.MidTermProject.Models.Account.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class AccountHolder extends User{
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birth;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "street_primaryAddress")),
            @AttributeOverride(name = "city", column = @Column(name = "city_primaryAddress")),
            @AttributeOverride(name = "country", column = @Column(name = "country_primaryAddress")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "postalCode_primaryAddress")),
    })
    private Address primaryAddress;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "street_mailingAddress")),
            @AttributeOverride(name = "city", column = @Column(name = "city_mailingAddress")),
            @AttributeOverride(name = "country", column = @Column(name = "country_mailingAddress")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "postalCode_mailingAddress")),
    })
    private Address mailingAddress;
    @OneToMany(mappedBy = "primaryOwner")
    @JsonIgnore
    private List<Account> accountListPrimaryOwner;
    @OneToMany(mappedBy = "secundaryOwner")
    @JsonIgnore
    private List<Account> accountListSecundaryOwner;


    public AccountHolder() {
    }

    public AccountHolder(Date birth, Address primaryAddress, Address mailingAddress, List<Account> accountListPrimaryOwner, List<Account> accountListSecundaryOwner) {
        this.birth = birth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
        this.accountListPrimaryOwner = accountListPrimaryOwner;
        this.accountListSecundaryOwner = accountListSecundaryOwner;
    }

    public AccountHolder(String username, String password, String fullName, Date birth, Address primaryAddress, Address mailingAddress) {
        super(username, password, fullName);
        this.birth = birth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    public AccountHolder(String username, String password, String fullName, Date birth, Address primaryAddress, Address mailingAddress, List<Account> accountListPrimaryOwner, List<Account> accountListSecundaryOwner) {
        super(username, password, fullName);
        this.birth = birth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;

    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public List<Account> getAccountListPrimaryOwner() {
        return accountListPrimaryOwner;
    }

    public void setAccountListPrimaryOwner(List<Account> accountListPrimaryOwner) {
        this.accountListPrimaryOwner = accountListPrimaryOwner;
    }

    public List<Account> getAccountListSecundaryOwner() {
        return accountListSecundaryOwner;
    }

    public void setAccountListSecundaryOwner(List<Account> accountListSecundaryOwner) {
        this.accountListSecundaryOwner = accountListSecundaryOwner;
    }
}
