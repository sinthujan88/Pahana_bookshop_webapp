<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahana.model.*, java.util.List, java.math.BigDecimal" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create New Bill</title>
<link rel="stylesheet" href="styles/form-styles.css">
<link rel="stylesheet" type="text/css" href="styles/main.css">
<style>
    .billing-layout { display: flex; gap: 2rem; }
    .billing-form { flex: 1; }
    .current-bill { flex: 1; background-color: #e9ecef; padding: 1.5rem; border-radius: 8px; }
    .current-bill table { width: 100%; }
    .current-bill th, .current-bill td { padding: 8px; text-align: left; }
    .total-row { font-weight: bold; border-top: 2px solid #343a40; }
</style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="container billing-layout">
        <!-- Left Side: Form for adding items -->
        <div class="billing-form">
            <h1 class="page-title">New Bill</h1>
            
            <!-- Form to Add an Item to the Bill -->
            <form action="billing" method="post">
                <input type="hidden" name="action" value="addItem">
                <div class="form-group">
                    <label>Select Item:</label>
                    <select name="itemId" class="form-control" required>
                        <option value="">-- Choose a book --</option>
                        <% List<Item> items = (List<Item>) request.getAttribute("items"); %>
                        <% for(Item item : items) { %>
                            <option value="<%= item.getItemId() %>"><%= item.getItemName() %> (Stock: <%= item.getStockQuantity() %>)</option>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label>Quantity:</label>
                    <input type="number" name="quantity" value="1" min="1" class="form-control" required>
                </div>
                <button type="submit" class="btn-primary">Add Item to Bill</button>
            </form>
        </div>

        <!-- Right Side: The current state of the bill (the "cart") -->
        <div class="current-bill">
            <h2>Current Bill</h2>
            <% 
                List<Bill_Item> cart = (List<Bill_Item>) session.getAttribute("currentCart");
                BigDecimal total = BigDecimal.ZERO;
                if (cart != null && !cart.isEmpty()) {
            %>
            <form action="billing" method="post">
                <input type="hidden" name="action" value="saveBill">
                
                <div class="form-group">
                    <label>Bill To Customer:</label>
                    <select name="customerId" class="form-control" required>
                         <option value="">-- Choose a customer --</option>
                         <% List<Customer> customers = (List<Customer>) request.getAttribute("customers"); %>
                         <% for(Customer cust : customers) { %>
                             <option value="<%= cust.getAccountNumber() %>"><%= cust.getFullName() %></option>
                         <% } %>
                    </select>
                </div>
                
                <table>
                    <thead><tr><th>Item</th><th>Qty</th><th>Price</th><th>Subtotal</th></tr></thead>
                    <tbody>
                    <% for(Bill_Item cartItem : cart) { 
                        BigDecimal subtotal = cartItem.getPriceAtTimeOfSale().multiply(new BigDecimal(cartItem.getQuantity()));
                        total = total.add(subtotal);
                    %>
                        <tr>
                            <td><%= cartItem.getItemName() %></td>
                            <td><%= cartItem.getQuantity() %></td>
                            <td><%= String.format("%.2f", cartItem.getPriceAtTimeOfSale()) %></td>
                            <td><%= String.format("%.2f", subtotal) %></td>
                        </tr>
                    <% } %>
                    <tr class="total-row">
                        <td colspan="3">Total</td>
                        <td><%= String.format("%.2f", total) %></td>
                    </tr>
                    </tbody>
                </table>
                <input type="hidden" name="totalAmount" value="<%= total %>">
                <br>
                <button type="submit" class="btn-primary" style="width:100%;">Finalize & Save Bill</button>
            </form>
            <% } else { %>
                <p>No items added yet.</p>
            <% } %>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
    
</body>
</html>