<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pahana Edu - Login</title>
<style>
    /* Add some basic styling for the login form */
    body { font-family: sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f0f2f5; }
    .login-container { padding: 2rem; background: white; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); width: 300px; }
    .login-container h1 { text-align: center; margin-bottom: 1.5rem; }
    .login-container input { width: 100%; padding: 10px; margin-bottom: 1rem; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
    .login-container button { width: 100%; padding: 10px; border: none; background-color: #007bff; color: white; border-radius: 4px; cursor: pointer; }
    .error-message { color: red; text-align: center; margin-bottom: 1rem; }
</style>
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
</html>