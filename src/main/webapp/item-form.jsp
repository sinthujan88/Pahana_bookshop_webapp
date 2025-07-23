<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahana.model.Item, java.math.BigDecimal" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Item Information</title>
<%-- Using the same styles as the customer form for consistency --%>
<link rel="stylesheet" href="styles/form-styles.css"> <%-- We'll create this soon --%>
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <% 
            Item item = (Item) request.getAttribute("item");
            boolean isEditMode = (item != null);
        %>

        <h1 class="page-title">
            <% if (isEditMode) { %>
                Edit Item Details
            <% } else { %>
                Add New Item
            <% } %>
        </h1>
        
        <form action="item" method="post">
            
            <input type="hidden" name="action" value="<%= isEditMode ? "update" : "insert" %>">
            
            <%-- If editing, include the item ID so the servlet knows which item to update --%>
            <% if (isEditMode) { %>
                <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
            <% } %>
            
            <div class="form-group">
                <label for="itemName">Item Name (Book Title)</label>
                <input type="text" id="itemName" name="itemName" 
                       value="<%= isEditMode ? item.getItemName() : "" %>" required>
            </div>
            
            <div class="form-group">
                <label for="author">Author</label>
                <input type="text" id="author" name="author" 
                       value="<%= isEditMode ? item.getAuthor() : "" %>">
            </div>
            
            <div class="form-group">
                <label for="itemPrice">Price</label>
                <input type="number" step="0.01" id="itemPrice" name="itemPrice" 
                       value="<%= isEditMode ? item.getItemPrice() : "" %>" required>
            </div>
            
            <div class="form-group">
                <label for="stockQuantity">Stock Quantity</label>
                <input type="number" id="stockQuantity" name="stockQuantity" 
                       value="<%= isEditMode ? String.valueOf(item.getStockQuantity()) : "0" %>" required>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn-primary">Save Item</button>
            </div>
        </form>
    </div>
</body>
</html>