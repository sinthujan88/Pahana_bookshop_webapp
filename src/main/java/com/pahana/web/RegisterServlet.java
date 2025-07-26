package com.pahana.web;

import java.io.IOException;

import com.pahana.db.UserDAO;
import com.pahana.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse; 
import org.mindrot.jbcrypt.BCrypt; 

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }
    
    
 // Show the registration form
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    // Process the form submission
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String plainPassword = request.getParameter("password");
        String username = request.getParameter("username");

        // 2. Hash the password using BCrypt
        // The gensalt() method automatically handles creating a secure, random salt
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPasswordHash(hashedPassword); // Store the HASHED password
        newUser.setRole(request.getParameter("role"));
        
        boolean isRegistered = userDAO.registerUser(newUser);
        
        if (isRegistered) {
            request.setAttribute("successMessage", "User registered successfully! You can now log in.");
        } else {
            request.setAttribute("errorMessage", "Registration failed. Username may already exist.");
        }
        
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}