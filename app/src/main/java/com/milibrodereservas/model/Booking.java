package com.milibrodereservas.model;

public class Booking {
    private int id;
    private String Customer;
    private String When;

    public Booking(int id, String customer, String when) {
        this.id = id;
        Customer = customer;
        When = when;
    }

    public Booking(String customer, String when) {
        Customer = customer;
        When = when;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public String getWhen() {
        return When;
    }

    public void setWhen(String when) {
        When = when;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
