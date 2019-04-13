package com.milibrodereservas.model;

import com.google.firebase.Timestamp;

public class Booking {
    private String Id;
    private String Customer;
    private Timestamp When;

    public Booking(String id, String customer, Timestamp when) {
        Id = id;
        Customer = customer;
        When = when;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public Timestamp getWhen() {
        return When;
    }

    public void setWhen(Timestamp when) {
        this.When = when;
    }
}
