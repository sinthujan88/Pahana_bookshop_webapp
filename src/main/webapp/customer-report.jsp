<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahana.model.*, java.util.List, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer Report</title>
<link rel="stylesheet" href="styles/list-styles.css">
<link rel="stylesheet" type="text/css" href="styles/main.css">
<style>
    .customer-details { background: #e9ecef; padding: 1.5rem; border-radius: 8px; margin-bottom: 2rem; }
    .customer-details h2 { margin-top: 0; }
</style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="container">
        <% Customer customer = (Customer) request.getAttribute("customer"); %>
         <div class="page-header">
            <h1 class="page-title">Account Details for <%= customer.getFullName() %></h1>
            <%-- This button links to our servlet with the customer's ID and the new action --%>
            <a href="reports?action=downloadReport&customerId=<%= customer.getAccountNumber() %>" class="btn btn-success">Download Full Report</a>
        </div>
        
        <div class="customer-details">
            <h2>Customer Information</h2>
            <p><strong>Account #:</strong> <%= customer.getAccountNumber() %></p>
            <p><strong>Address:</strong> <%= customer.getAddress() %></p>
            <p><strong>Telephone:</strong> <%= customer.getTelephone() %></p>
            <p><strong>Email:</strong> <%= customer.getEmail() %></p>
        </div>
        
        <h2>Billing History</h2>
        <table>
            <thead>
                <tr>
                    <th>Bill ID</th>
                    <th>Bill Date</th>
                    <th>Total Amount</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Bill> billHistory = (List<Bill>) request.getAttribute("billHistory"); 
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                %>
                <% for (Bill bill : billHistory) { %>
                    <tr>
                        <td><%= bill.getBillId() %></td>
                        <td><%= sdf.format(bill.getBillDate()) %></td>
                        <td><%= String.format("LKR %.2f", bill.getTotalAmount()) %></td>
                        <td>
                            <a href="receipt?billId=<%= bill.getBillId() %>" class="btn btn-success">Download Receipt</a>
                        </td>
                    </tr>
                <% } %>
                <% if (billHistory.isEmpty()) { %>
                    <tr><td colspan="4">No billing history found for this customer.</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>
    
    <jsp:include page="footer.jsp" />
</body>
</html>