package com.pahana.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pahana.model.Bill;
import com.pahana.model.Bill_Item;

// --- Teaching Point: Transaction Management in a DAO ---
// This DAO handles a complex operation that must be "atomic"—it must either
// completely succeed or completely fail. If we insert a bill but fail to
// update the stock, our database is inconsistent. A transaction solves this.
// We manually control the transaction using setAutoCommit, commit, and rollback.

public class BillDAO {

	public int saveBill(Bill bill) throws Exception {
	    Connection conn = null;
	    int billId = -1; // Initialize billId
	    try {
	        conn = DatabaseManager.getConnection();
	        conn.setAutoCommit(false); 

	        String billSql = "INSERT INTO Bills (customer_account_number, total_amount) VALUES (?, ?)";
	        PreparedStatement billPstmt = conn.prepareStatement(billSql, Statement.RETURN_GENERATED_KEYS);
	        billPstmt.setString(1, bill.getCustomerAccountNumber());
	        billPstmt.setBigDecimal(2, bill.getTotalAmount());
	        billPstmt.executeUpdate();

	        ResultSet rs = billPstmt.getGeneratedKeys();
	        if (rs.next()) {
	            billId = rs.getInt(1); // Assign the generated ID
	        }
	        if (billId == -1) {
	            throw new Exception("Failed to create bill, no ID obtained.");
	        }

            // 4. Loop through each item on the bill and insert into Bill_Items and update stock
            String billItemSql = "INSERT INTO Bill_Items (bill_id, item_id, quantity, price_at_time_of_sale) VALUES (?, ?, ?, ?)";
            String updateStockSql = "UPDATE Items SET stock_quantity = stock_quantity - ? WHERE item_id = ?";
            
            for (Bill_Item item : bill.getBillItems()) {
                // Insert into Bill_Items
                PreparedStatement itemPstmt = conn.prepareStatement(billItemSql);
                itemPstmt.setInt(1, billId);
                itemPstmt.setInt(2, item.getItemId());
                itemPstmt.setInt(3, item.getQuantity());
                itemPstmt.setBigDecimal(4, item.getPriceAtTimeOfSale());
                itemPstmt.executeUpdate();
                
                // Update stock in Items table
                PreparedStatement stockPstmt = conn.prepareStatement(updateStockSql);
                stockPstmt.setInt(1, item.getQuantity());
                stockPstmt.setInt(2, item.getItemId());
                stockPstmt.executeUpdate();
            }

            // 5. If all operations were successful, commit the transaction
            conn.commit(); 
            
            return billId;

	    } catch (Exception e) {
	        if (conn != null) conn.rollback();
	        e.printStackTrace();
	        throw e; 
	    } finally {
	        if (conn != null) {
	            conn.setAutoCommit(true);
	            conn.close();
	        }
	    }
	}
	
	
	
	// ... inside BillDAO ...
	public Bill getBillById(int billId) {
	    Bill bill = null;
	    String billSql = "SELECT * FROM Bills WHERE bill_id = ?";
	    String itemsSql = "SELECT bi.*, i.item_name FROM Bill_Items bi JOIN Items i ON bi.item_id = i.item_id WHERE bi.bill_id = ?";
	    
	    try (Connection conn = DatabaseManager.getConnection()) {
	        // Get Bill Header
	        PreparedStatement billPstmt = conn.prepareStatement(billSql);
	        billPstmt.setInt(1, billId);
	        ResultSet rsBill = billPstmt.executeQuery();
	        if (rsBill.next()) {
	            bill = new Bill();
	            bill.setBillId(rsBill.getInt("bill_id"));
	            bill.setCustomerAccountNumber(rsBill.getString("customer_account_number"));
	            bill.setBillDate(rsBill.getTimestamp("bill_date"));
	            bill.setTotalAmount(rsBill.getBigDecimal("total_amount"));
	        }
	        
	        // Get Bill Line Items
	        if (bill != null) {
	            List<Bill_Item> billItems = new ArrayList<>();
	            PreparedStatement itemsPstmt = conn.prepareStatement(itemsSql);
	            itemsPstmt.setInt(1, billId);
	            ResultSet rsItems = itemsPstmt.executeQuery();
	            while(rsItems.next()) {
	                Bill_Item item = new Bill_Item();
	                item.setItemId(rsItems.getInt("item_id"));
	                item.setItemName(rsItems.getString("item_name"));
	                item.setQuantity(rsItems.getInt("quantity"));
	                item.setPriceAtTimeOfSale(rsItems.getBigDecimal("price_at_time_of_sale"));
	                billItems.add(item);
	            }
	            bill.setBillItems(billItems);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return bill;
	}
	
	
	
	 /**
     * Retrieves a list of all bills for a specific customer.
     * @param accountNumber The customer's unique account number.
     * @return A list of Bill objects, ordered by the most recent first.
     */
    public List<Bill> getBillsByCustomer(String accountNumber) {
        List<Bill> billHistory = new ArrayList<>();
        // Note: We don't need line items for this summary view, just the bill headers.
        String sql = "SELECT * FROM Bills WHERE customer_account_number = ? ORDER BY bill_date DESC";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setCustomerAccountNumber(rs.getString("customer_account_number"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                bill.setTotalAmount(rs.getBigDecimal("total_amount"));
                billHistory.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billHistory;
    }
	
	
	
	
	
	
}