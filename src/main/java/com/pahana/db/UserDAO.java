package com.pahana.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.pahana.model.User;

public class UserDAO {
	
	
	 /**
     * Registers a new user in the database.
     * @param user The User object containing details to be saved.
     * @return true if registration is successful, false otherwise.
     */
    public boolean registerUser(User user) {
        // NOTE: HASH the password before calling this method in a real application!
        String sql = "INSERT INTO Users (username, password_hash, role) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash());
            pstmt.setString(3, user.getRole());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (Exception e) {
            e.printStackTrace(); // Could fail if username already exists
            return false;
        }
    }
	
    
    /**
     * Authenticates a user.
     * @param username The user's username.
     * @param password The user's plain text password.
     * @return A User object if authentication is successful, null otherwise.
     */
    public User authenticateUser(String username, String password) {
        User user = null;
        // IMPORTANT: In a real system, you would compare a HASH of the password, not plain text.
        // For this assignment, a direct comparison is acceptable if mentioned in your report.
        String sql = "SELECT * FROM Users WHERE username = ? AND password_hash = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}