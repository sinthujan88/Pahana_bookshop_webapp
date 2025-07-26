package com.pahana.web;

import java.io.IOException;
import com.pahana.db.UserDAO;
import com.pahana.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        
        User userFromDB = userDAO.getUserByUsername(user);
        
      
        if (userFromDB != null && BCrypt.checkpw(pass, userFromDB.getPasswordHash())) {
            // Login successful
            // 1. Create a session
            HttpSession session = request.getSession();
            // 2. Store user information in the session
            session.setAttribute("user", userFromDB);
            // 3. Redirect to the main dashboard (for now,  item list)
            response.sendRedirect("dashboard.jsp");
        } else {
            // Login failed
            request.setAttribute("errorMessage", "Invalid username or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    
    // Also handle GET requests to show the login page
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}