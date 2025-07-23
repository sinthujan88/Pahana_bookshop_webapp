package com.pahana.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.pahana.model.Customer;

// --- Teaching Point: Data Access Object (DAO) Design Pattern ---
// This class's only purpose is to interact with the 'Customers' table.
// It hides all the complex SQL logic from the rest of the application.
// Our servlet will just call simple methods like 'getAllCustomers()'
// without needing to know anything about SQL.

public class CustomerDAO {

    // Method to retrieve all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        String sql = "SELECT * FROM Customers ORDER BY full_name";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setFullName(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                customerList.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerList;
    }

    // Method to add a new customer
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO Customers (account_number, full_name, address, telephone, email) VALUES (?, ?, ?, ?, ?)";
        // --- Teaching Point: PreparedStatement and Validation ---
        // Using a PreparedStatement is a best practice for security. It prevents
        // a type of attack called "SQL Injection". We bind parameters using '?'
        // instead of concatenating strings.
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customer.getAccountNumber());
            pstmt.setString(2, customer.getFullName());
            pstmt.setString(3, customer.getAddress());
            pstmt.setString(4, customer.getTelephone());
            pstmt.setString(5, customer.getEmail());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Method to get a single customer by their account number (for editing)
    public Customer getCustomerById(String accountNumber) {
        Customer customer = null;
        String sql = "SELECT * FROM Customers WHERE account_number = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                customer = new Customer();
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setFullName(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    // Method to update an existing customer's information
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE Customers SET full_name = ?, address = ?, telephone = ?, email = ? WHERE account_number = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getFullName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getTelephone());
            pstmt.setString(4, customer.getEmail());
            pstmt.setString(5, customer.getAccountNumber()); // This is for the WHERE clause
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}