package com.pahana.model;

public class Customer {

    private String accountNumber;
    private String fullName;
    private String address;
    private String telephone;
    private String email;

    // --- Teaching Point: Encapsulation (OOP) ---
    // The data (fields) are private, and they can only be accessed
    // through public methods (getters and setters). This gives us
    // control over how the data is used and modified.

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}