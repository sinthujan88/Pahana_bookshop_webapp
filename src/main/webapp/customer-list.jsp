<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahana.model.Customer, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Customers</title>
<link rel="stylesheet" type="text/css" href="styles/main.css">
<%-- Reusing the styles from displayItems.jsp for a consistent look --%>
<style>
    body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif; margin: 0; background-color: #f8f9fa; }
    .header-bar { background-color: #343a40; color: white; padding: 1rem 2rem; display: flex; justify-content: space-between; align-items: center; }
    .header-bar a { color: white; text-decoration: none; font-weight: 500; }
    .container { padding: 2rem; }
    table { border-collapse: collapse; width: 100%; margin: 20px 0; box-shadow: 0 4px 12px rgba(0,0,0,0.08); background: white; }
    th, td { border-bottom: 1px solid #dee2e6; text-align: left; padding: 12px 15px; }
    th { background-color: #f8f9fa; font-weight: 600; }
    tr:hover { background-color: #f1f1f1; }
    .page-title { font-size: 2rem; color: #343a40; margin-bottom: 1rem; }
    .page-header { display: flex; justify-content: space-between; align-items: center; }
    .btn { padding: 10px 15px; text-decoration: none; border-radius: 4px; color: white; }
    .btn-success { background-color: #28a745; }
    .btn-warning { background-color: #ffc107; color: #212529; }
</style>
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="container">
        <div class="page-header">
            <h1 class="page-title">Customer Accounts</h1>
            <a href="customer?action=new" class="btn btn-success">Add New Customer</a>
        </div>
    
        <table>
            <thead>
                <tr>
                    <th>Account #</th>
                    <th>Full Name</th>
                    <th>Telephone</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% List<Customer> customerList = (List<Customer>) request.getAttribute("customerList"); %>
                <% for (Customer customer : customerList) { %>
                    <tr>
                        <td><%= customer.getAccountNumber() %></td>
                        <td><%= customer.getFullName() %></td>
                        <td><%= customer.getTelephone() %></td>
                        <td><%= customer.getEmail() %></td>
                        <td>
                            <a href="customer?action=edit&id=<%= customer.getAccountNumber() %>" class="btn btn-warning">Edit</a>
                            <%-- We will add a 'Delete' button here later --%>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        
       
    </div>
    
    <jsp:include page="footer.jsp" />
 
</body>
   

</html>