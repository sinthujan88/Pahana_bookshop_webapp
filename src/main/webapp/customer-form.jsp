<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahana.model.Customer" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer Information</title>
<link rel="stylesheet" type="text/css" href="styles/main.css">

<%-- Reusing the same styles from our other pages for consistency --%>
<style>
    body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif; margin: 0; background-color: #f8f9fa; }
    .header-bar { background-color: #343a40; color: white; padding: 1rem 2rem; display: flex; justify-content: space-between; align-items: center; }
    .header-bar a { color: white; text-decoration: none; font-weight: 500; }
    .container { max-width: 600px; margin: 2rem auto; padding: 2rem; background: white; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.08); }
    .form-group { margin-bottom: 1rem; }
    .form-group label { display: block; margin-bottom: .5rem; font-weight: 500; }
    .form-group input, .form-group textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
    .form-actions button { padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; }
    .btn-primary { background-color: #007bff; color: white; }
    .page-title { font-size: 2rem; color: #343a40; }
</style>


</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <% 
            // --- Teaching Point: View Logic ---
            // This JSP is smart. It checks if a 'customer' object exists in the request.
            // If it exists, we are EDITING. If not, we are ADDING.
            Customer customer = (Customer) request.getAttribute("customer");
            boolean isEditMode = (customer != null);
        %>

        <h1 class="page-title">
            <% if (isEditMode) { %>
                Edit Customer Details
            <% } else { %>
                Add New Customer
            <% } %>
        </h1>
        
        <form action="customer" method="post">
            
            <!-- This hidden field tells the servlet whether to INSERT or UPDATE -->
            <input type="hidden" name="action" value="<%= isEditMode ? "update" : "insert" %>">
            
            <div class="form-group">
                <label for="accountNumber">Account Number</label>
                <input type="text" id="accountNumber" name="accountNumber" 
                       value="<%= isEditMode ? customer.getAccountNumber() : "" %>" 
                       <%= isEditMode ? "readonly" : "required" %>>
                <%-- We make the account number readonly in edit mode to prevent changing the primary key --%>
            </div>
            
            <div class="form-group">
                <label for="fullName">Full Name</label>
                <input type="text" id="fullName" name="fullName" 
                       value="<%= isEditMode ? customer.getFullName() : "" %>" required>
            </div>
            
            <div class="form-group">
                <label for="address">Address</label>
                <textarea id="address" name="address" rows="3"><%= isEditMode ? customer.getAddress() : "" %></textarea>
            </div>
            
            <div class="form-group">
                <label for="telephone">Telephone</label>
                <input type="text" id="telephone" name="telephone" 
                       value="<%= isEditMode ? customer.getTelephone() : "" %>">
            </div>
            
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" 
                       value="<%= isEditMode ? customer.getEmail() : "" %>">
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn-primary">Save Customer</button>
            </div>
        </form>
    </div>
    
    <jsp:include page="footer.jsp" />
    
</body>
</html>