package com.Ironhack.MidTermProject.Embeddables;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class Address {
    private String street;
    private String city;
    private String country;
    private int postalCode;

    public Address() {
    }

    public Address(String street, String city, String country, int postalCode) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
}
