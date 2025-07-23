package com.pahana.model;

public class User {
    private int id;
    private String username;
    private String passwordHash; // We will handle hashing later
    private String role;
    
    // Add Getters and Setters for all fields...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}