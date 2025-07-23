package com.pahana.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.pahana.db.ItemDAO;
import com.pahana.model.Item;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// --- Teaching Point: Controller Reusability ---
// This is our Front Controller for Items. It handles all actions:
// - list: shows all items
// - new: shows the form to add a new item
// - insert: saves the new item
// - edit: shows the form to edit an existing item
// - update: saves the changes
// - delete: removes the item
// This pattern keeps related logic in one place.

@WebServlet("/item")
public class ItemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ItemDAO itemDAO;

    public void init() {
        itemDAO = new ItemDAO();
    }

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
            case "delete":
                deleteItem(request, response);
                break;
            default: // "list"
                listItems(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "insert":
                insertItem(request, response);
                break;
            case "update":
                updateItem(request, response);
                break;
        }
    }

    // --- Action Methods ---

    private void listItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Item> itemList = itemDAO.getAllItems();
        request.setAttribute("itemList", itemList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("item-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("item-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Item existingItem = itemDAO.getItemById(id);
        request.setAttribute("item", existingItem);
        RequestDispatcher dispatcher = request.getRequestDispatcher("item-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void insertItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Item newItem = new Item();
        newItem.setItemName(request.getParameter("itemName"));
        newItem.setAuthor(request.getParameter("author"));
        newItem.setItemPrice(new BigDecimal(request.getParameter("itemPrice")));
        newItem.setStockQuantity(Integer.parseInt(request.getParameter("stockQuantity")));
        
        itemDAO.addItem(newItem);
        response.sendRedirect("item?action=list");
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Item item = new Item();
        item.setItemId(Integer.parseInt(request.getParameter("itemId")));
        item.setItemName(request.getParameter("itemName"));
        item.setAuthor(request.getParameter("author"));
        item.setItemPrice(new BigDecimal(request.getParameter("itemPrice")));
        item.setStockQuantity(Integer.parseInt(request.getParameter("stockQuantity")));
        
        itemDAO.updateItem(item);
        response.sendRedirect("item?action=list");
    }
    
    private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        itemDAO.deleteItem(id);
        response.sendRedirect("item?action=list");
    }
}