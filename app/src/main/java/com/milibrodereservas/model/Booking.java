package com.milibrodereservas.model;

import com.google.firebase.Timestamp;

public class Booking {
    private String Customer;
    private Timestamp when;

    public Booking(String customer, Timestamp when) {
        Customer = customer;
        this.when = when;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public Timestamp getWhen() {
        return when;
    }

    public void setWhen(Timestamp when) {
        this.when = when;
    }
}
