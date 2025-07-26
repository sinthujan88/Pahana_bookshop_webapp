<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pahana Edu - Login</title>
<link rel="stylesheet" type="text/css" href="styles/login.css">
<link rel="stylesheet" type="text/css" href="styles/main.css">



</head>

<body>
    <div class="login-container">
        <h1>System Login</h1>
        
        <%-- Display error message if login fails --%>
        <% 
            String error = (String) request.getAttribute("errorMessage");
            if (error != null) {
        %>
            <p class="error-message"><%= error %></p>
        <% 
            }
        %>
        
        <form action="login" method="post">
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <button type="submit">Login</button>
        </form>
        
     
        <p style="text-align:center; margin-top:1rem;"><a href="register">Register a new user</a></p>
   
  
    </div>
  
   
    
</body>
   <jsp:include page="footer.jsp" />
</html>