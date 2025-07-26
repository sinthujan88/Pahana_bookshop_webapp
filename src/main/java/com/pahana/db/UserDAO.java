package com.pahana.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.pahana.model.User;
import java.sql.SQLException;

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
	
    
   
    public User getUserByUsername(String username) {
        User user = null;
        String sql = "SELECT * FROM Users WHERE username = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPasswordHash(rs.getString("password_hash")); // We MUST retrieve the hash
                    user.setRole(rs.getString("role"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user; // Will be null if no user was found
    }
}