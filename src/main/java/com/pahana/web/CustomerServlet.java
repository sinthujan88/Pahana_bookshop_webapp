package com.pahana.web;

import java.io.IOException;
import java.util.List;

import com.pahana.db.CustomerDAO;
import com.pahana.model.Customer;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// --- Teaching Point: Front Controller Design Pattern (Simplified) ---
// This one servlet handles all actions related to customers (list, show form, insert, update).
// We check the 'action' parameter to decide which block of code to execute.
// This keeps our code organized and reduces the number of servlets needed.

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;

    public void init() {
        customerDAO = new CustomerDAO();
    }
    
    // Handles GET requests (showing pages)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list"; // Default action
        }
        
        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            default: // "list"
                listCustomers(request, response);
                break;
        }
    }
    
    // Handles POST requests (submitting forms)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        switch (action) {
            case "insert":
                insertCustomer(request, response);
                break;
            case "update":
                updateCustomer(request, response);
                break;
        }
    }
    
    // --- Action Methods ---

    private void listCustomers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> customerList = customerDAO.getAllCustomers();
        request.setAttribute("customerList", customerList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Customer existingCustomer = customerDAO.getCustomerById(id);
        request.setAttribute("customer", existingCustomer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Customer newCustomer = new Customer();
        newCustomer.setAccountNumber(request.getParameter("accountNumber"));
        newCustomer.setFullName(request.getParameter("fullName"));
        newCustomer.setAddress(request.getParameter("address"));
        newCustomer.setTelephone(request.getParameter("telephone"));
        newCustomer.setEmail(request.getParameter("email"));
        
        customerDAO.addCustomer(newCustomer);
        response.sendRedirect("customer?action=list"); // Go back to the list view
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Customer customer = new Customer();
        customer.setAccountNumber(request.getParameter("accountNumber")); // Important for the WHERE clause
        customer.setFullName(request.getParameter("fullName"));
        customer.setAddress(request.getParameter("address"));
        customer.setTelephone(request.getParameter("telephone"));
        customer.setEmail(request.getParameter("email"));

        customerDAO.updateCustomer(customer);
        response.sendRedirect("customer?action=list"); // Go back to the list view
    }
}