package com.pahana.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pahana.model.Item;

// --- Teaching Point: DAO Pattern Consistency ---
// Notice how this DAO class is structured almost identically to CustomerDAO.
// It has methods for getting all records, getting a single record by its ID,
// inserting, updating, and deleting. This consistent structure makes your
// data layer predictable and easy to work with.

public class ItemDAO {

    // (This method already exists)
    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM dbo.Items ORDER BY item_id";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {
            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getInt("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setAuthor(rs.getString("author"));
                item.setItemPrice(rs.getBigDecimal("item_price"));
                item.setStockQuantity(rs.getInt("stock_quantity"));
                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemList;
    }

    // --- NEW METHODS START HERE ---

    // Method to get a single item by its ID (for the edit form)
    public Item getItemById(int itemId) {
        Item item = null;
        String sql = "SELECT * FROM Items WHERE item_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, itemId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                item = new Item();
                item.setItemId(rs.getInt("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setAuthor(rs.getString("author"));
                item.setItemPrice(rs.getBigDecimal("item_price"));
                item.setStockQuantity(rs.getInt("stock_quantity"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }
    
    // Method to add a new item
    public void addItem(Item item) {
        String sql = "INSERT INTO Items (item_name, author, item_price, stock_quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getAuthor());
            pstmt.setBigDecimal(3, item.getItemPrice());
            pstmt.setInt(4, item.getStockQuantity());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Method to update an existing item
    public void updateItem(Item item) {
        String sql = "UPDATE Items SET item_name = ?, author = ?, item_price = ?, stock_quantity = ? WHERE item_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getItemName());
            pstmt.setString(2, item.getAuthor());
            pstmt.setBigDecimal(3, item.getItemPrice());
            pstmt.setInt(4, item.getStockQuantity());
            pstmt.setInt(5, item.getItemId()); // For the WHERE clause
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to delete an item
    public void deleteItem(int itemId) {
        String sql = "DELETE FROM Items WHERE item_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, itemId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            // This might fail if the item is part of an existing bill.
            // A real application would handle this gracefully.
            e.printStackTrace();
        }
    }
}