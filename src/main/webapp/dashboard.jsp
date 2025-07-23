<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahana.model.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pahana Edu - Dashboard</title>
<style>
    body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif; margin: 0; background-color: #f8f9fa; }
    .dashboard-container { display: flex; flex-wrap: wrap; justify-content: center; padding: 2rem; }
    .card { background: #fff; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.08); margin: 1rem; width: 250px; text-decoration: none; color: inherit; transition: transform 0.2s, box-shadow 0.2s; }
    .card:hover { transform: translateY(-5px); box-shadow: 0 8px 20px rgba(0,0,0,0.12); }
    .card-body { padding: 1.5rem; text-align: center; }
    .card-title { font-size: 1.25rem; font-weight: 500; margin-bottom: 0.5rem; }
    .card-text { color: #6c757d; }
    .header-bar { background-color: #343a40; color: white; padding: 1rem 2rem; display: flex; justify-content: space-between; align-items: center; }
    .header-bar a { color: white; text-decoration: none; font-weight: 500; }
    .page-title { font-size: 2rem; text-align: center; color: #343a40; padding: 2rem 0 1rem 0; }
    
    /* Style for the new flash message */
    .flash-success {
        padding: 1rem;
        margin: 1rem 2rem;
        background-color: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
        border-radius: 4px;
        text-align: center;
    }
</style>
</head>
<body>

    <%-- STEP 1: Replace the old header div with the JSP include directive. --%>
    <%-- This makes your dashboard consistent with all other pages. --%>
    <jsp:include page="header.jsp" />

    <%-- STEP 2: Add the new logic to display and clear the flash message. --%>
    <%
        String flashMessage = (String) session.getAttribute("flashMessage");
        if (flashMessage != null) {
    %>
        <div class="flash-success">
            <%= flashMessage %>
        </div>
    <%
            // IMPORTANT: Remove the attribute so it doesn't show again on refresh
            session.removeAttribute("flashMessage");
        }
    %>

    <%-- The rest of your dashboard remains exactly the same. --%>
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
          <a href="reports?action=select" class="card"> <!-- CHANGED -->
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

</body>
</html>