package com.pahana.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.pahana.db.BillDAO;
import com.pahana.db.CustomerDAO;
import com.pahana.db.ItemDAO;
import com.pahana.model.Bill;
import com.pahana.model.Bill_Item;
import com.pahana.model.Customer;
import com.pahana.model.Item;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/billing")
public class BillingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;
    private ItemDAO itemDAO;
    private BillDAO billDAO;

    public void init() {
        customerDAO = new CustomerDAO();
        itemDAO = new ItemDAO();
        billDAO = new BillDAO();
    }

    // Handles showing the form and redirecting
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // This is the main entry point to the billing page.
        // We need to fetch all customers and items to populate the dropdowns.
        List<Customer> customers = customerDAO.getAllCustomers();
        List<Item> items = itemDAO.getAllItems();
        
        request.setAttribute("customers", customers);
        request.setAttribute("items", items);
        
        request.getRequestDispatcher("create-bill.jsp").forward(request, response);
    }

    // --- Teaching Point: Session Management for a "Shopping Cart" ---
    // HTTP is stateless, so we use the HttpSession to store the list of items
    // the user has added to the current bill. This "cart" persists across
    // multiple requests until the bill is saved or cancelled.
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        // Get the current cart from the session, or create a new one if it doesn't exist.
        List<Bill_Item> cart = (List<Bill_Item>) session.getAttribute("currentCart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        switch (action) {
            case "addItem":
                // Get item details from the form
                int itemId = Integer.parseInt(request.getParameter("itemId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                
                // Fetch the full item details from the DB to get its price
                Item itemToAdd = itemDAO.getItemById(itemId);
                
                // Create a new line item for the bill
                Bill_Item billItem = new Bill_Item();
                billItem.setItemId(itemId);
                billItem.setItemName(itemToAdd.getItemName());
                billItem.setQuantity(quantity);
                billItem.setPriceAtTimeOfSale(itemToAdd.getItemPrice());
                
                cart.add(billItem);
                
                // Save the updated cart back to the session
                session.setAttribute("currentCart", cart);
                response.sendRedirect("billing"); // Redirect back to the billing page
                break;

            case "saveBill":
                // Get the selected customer
                String customerId = request.getParameter("customerId");
                BigDecimal totalAmount = new BigDecimal(request.getParameter("totalAmount"));
                
                // Create the main Bill object
                Bill finalBill = new Bill();
                finalBill.setCustomerAccountNumber(customerId);
                finalBill.setTotalAmount(totalAmount);
                finalBill.setBillItems(cart); // Attach the list of items
                
                try {
                    // --- MODIFICATION ---
                    // The DAO method will now return the ID of the bill we just saved.
                    int savedBillId = billDAO.saveBill(finalBill);
                    
                    // --- Teaching Point: Flash Message Pattern ---
                    // We put a message into the session. The next page the user visits
                    // will display this message and then immediately remove it.
                    // This ensures the message is shown only ONCE.
                    session.setAttribute("flashMessage", "Bill #" + savedBillId + " was saved successfully!");
                    
                    session.removeAttribute("currentCart"); // Clear the cart on success
                    
                    // Redirect to a new receipt page that will handle the PDF download
                    response.sendRedirect("receipt?billId=" + savedBillId); 
                } catch (Exception e) {
                    request.setAttribute("errorMessage", "Error saving bill: " + e.getMessage());
                    doGet(request, response);
                }
                break;
        }
    }
}