<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahana.model.User" %>

<div class="header-bar">
    <% 
        User user = (User) session.getAttribute("user");
        String username = (user != null) ? user.getUsername() : "Guest";
    %>
    <div class="header-left">
        <a href="dashboard.jsp" style="font-size: 1.2rem; font-weight: bold;">Pahana Edu</a>
    </div>
    <div class="header-right">
        <span>Welcome, <%= username %>!</span>
        <a href="logout" style="margin-left: 20px; background-color: #dc3545; padding: 8px 12px; border-radius: 4px;">Logout</a>
    </div>
</div>