<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahana.model.Item, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Items</title>
<link rel="stylesheet" type="text/css" href="styles/main.css">
<%-- Using the same styles as the customer list for consistency --%>
<link rel="stylesheet" href="styles/list-styles.css"> <%-- We'll create this soon --%>
</head>


<body>

    <jsp:include page="header.jsp" />

    <div class="container">
        <div class="page-header">
            <h1 class="page-title">Bookstore Inventory</h1>
            <a href="item?action=new" class="btn btn-success">Add New Item</a>
        </div>
    
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Author</th>
                    <th>Price</th>
                    <th>Stock</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% List<Item> itemList = (List<Item>) request.getAttribute("itemList"); %>
                <% for (Item item : itemList) { %>
                    <tr>
                        <td><%= item.getItemId() %></td>
                        <td><%= item.getItemName() %></td>
                        <td><%= item.getAuthor() %></td>
                        <td><%= String.format("%.2f", item.getItemPrice()) %></td>
                        <td><%= item.getStockQuantity() %></td>
                        <td>
                            <a href="item?action=edit&id=<%= item.getItemId() %>" class="btn btn-warning">Edit</a>
                            <a href="item?action=delete&id=<%= item.getItemId() %>"
  							 style="background-color: red; color: white; padding: 10px 10px; text-decoration: none; border-radius: 4px;"
  							 onclick="return confirm('Are you sure you want to delete this item?')">
  							 Delete </a>
																												
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
<jsp:include page="footer.jsp" />
</body>
</html>