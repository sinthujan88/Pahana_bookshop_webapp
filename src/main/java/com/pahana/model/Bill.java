package com.pahana.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Bill {
    private int billId;
    private String customerAccountNumber;
    private Date billDate;
    private BigDecimal totalAmount;
    private List<Bill_Item> billItems; // To hold the list of items on this bill

    // --- Getters and Setters for all fields ---
    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }
    public String getCustomerAccountNumber() { return customerAccountNumber; }
    public void setCustomerAccountNumber(String customerAccountNumber) { this.customerAccountNumber = customerAccountNumber; }
    public Date getBillDate() { return billDate; }
    public void setBillDate(Date billDate) { this.billDate = billDate; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public List<Bill_Item> getBillItems() { return billItems; }
    public void setBillItems(List<Bill_Item> billItems) { this.billItems = billItems; }
}