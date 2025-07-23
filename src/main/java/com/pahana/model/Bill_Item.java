package com.pahana.model;

import java.math.BigDecimal;

public class Bill_Item {
    private int billItemId;
    private int billId;
    private int itemId;
    private int quantity;
    private BigDecimal priceAtTimeOfSale;
    
    // We can also store the item name for easier display on the receipt
    private String itemName; 

    // --- Getters and Setters for all fields ---
    public int getBillItemId() { return billItemId; }
    public void setBillItemId(int billItemId) { this.billItemId = billItemId; }
    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getPriceAtTimeOfSale() { return priceAtTimeOfSale; }
    public void setPriceAtTimeOfSale(BigDecimal priceAtTimeOfSale) { this.priceAtTimeOfSale = priceAtTimeOfSale; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
}