<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahana.model.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pahana Edu - Dashboard</title>
<link rel="stylesheet" type="text/css" href="styles/main.css">
<link rel="stylesheet" href="styles/dashboard.css">

<body>

    <jsp:include page="header.jsp" />

    <%
        String flashMessage = (String) session.getAttribute("flashMessage");
        if (flashMessage != null) {
    %>
        <div class="flash-success">
            <%= flashMessage %>
        </div>
    <%
            session.removeAttribute("flashMessage");
        }
    %>

    <h1 class="page-title">Pahana Edu Management System</h1>

    <div class="dashboard-container">
        
        <!-- Card for Item Management -->
        <a href="item?action=list" class="card">
            <div class="card-body">
                <h2 class="card-title">Manage Items</h2>
                <p class="card-text">Add, update, and delete bookstore items.</p>
            </div>
        </a>

        <!-- Card for Customer Management -->
        <a href="customer?action=list" class="card">
            <div class="card-body">
                <h2 class="card-title">Manage Customers</h2>
                <p class="card-text">Add and edit customer account details.</p>
            </div>
        </a>
        
        <!-- Card for Billing -->
       <a href="billing" class="card">
            <div class="card-body">
                <h2 class="card-title">Calculate & Print Bill</h2>
                <p class="card-text">Create new bills for customer purchases.</p>
            </div>
        </a>
        
        <!-- Card for Account Details / Reports -->
          <a href="reports?action=select" class="card">
            <div class="card-body">
                <h2 class="card-title">Display Account Details</h2>
                <p class="card-text">View customer information and bill history.</p>
            </div>
        </a>
        
        <!-- Card for Help Section -->
        <a href="help.jsp" class="card">
            <div class="card-body">
                <h2 class="card-title">Help Section</h2>
                <p class="card-text">Learn how to use the system.</p>
            </div>
        </a>
        
         <!-- Card for Exit System (Logout) -->
        <a href="logout" class="card">
            <div class="card-body">
                <h2 class="card-title">Exit System</h2>
                <p class="card-text">Securely log out and end your session.</p>
            </div>
        </a>
        
    </div>


<jsp:include page="footer.jsp" />

</body>
</html>