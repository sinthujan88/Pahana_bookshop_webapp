<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pahana Edu - Register User</title>
<link rel="stylesheet" type="text/css" href="styles/main.css">
<style>
    /* Using the same styles as the login page for consistency */
    body { font-family: sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f0f2f5; }
    .login-container { padding: 2rem; background: white; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); width: 300px; }
    .login-container h1 { text-align: center; margin-bottom: 1.5rem; }
    .login-container input, .login-container select { width: 100%; padding: 10px; margin-bottom: 1rem; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
    .login-container button { width: 100%; padding: 10px; border: none; background-color: #28a745; color: white; border-radius: 4px; cursor: pointer; }
    .message { text-align: center; margin-bottom: 1rem; }
    .success { color: green; }
    .error { color: red; }
</style>
</head>
<body>
    <div class="login-container">
        <h1>Register New User</h1>
        
        <%-- Display feedback messages --%>
        <p class="message success">${successMessage}</p>
        <p class="message error">${errorMessage}</p>
        
        <form action="register" method="post">
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <select name="role">
                <option value="staff">Staff</option>
                <option value="admin">Admin</option>
            </select>
            <button type="submit">Register</button>
        </form>
        <p style="text-align:center; margin-top:1rem;"><a href="login">Back to Login</a></p>
    </div>
    
    <jsp:include page="footer.jsp" />
</body>
</html>