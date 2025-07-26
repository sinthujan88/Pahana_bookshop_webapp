<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahana.model.Customer, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Select Customer for Report</title>
<link rel="stylesheet" type="text/css" href="styles/main.css">
<link rel="stylesheet" href="styles/form-styles.css">
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="container">
        <h1 class="page-title">Customer Account Reports</h1>
        <p>Select a customer to view their details and complete billing history.</p>
        
        <form action="reports" method="get">
            <input type="hidden" name="action" value="view">
            
            <div class="form-group">
                <label for="customerId">Select Customer:</label>
                <select id="customerId" name="customerId" class="form-control" required>
                    <option value="">-- Choose a customer --</option>
                    <% List<Customer> customerList = (List<Customer>) request.getAttribute("customerList"); %>
                    <% for(Customer cust : customerList) { %>
                        <option value="<%= cust.getAccountNumber() %>"><%= cust.getFullName() %></option>
                    <% } %>
                </select>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn-primary">View Report</button>
            </div>
        </form>
    </div>
    
    <jsp:include page="footer.jsp" />
</body>
</html>