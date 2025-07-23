package com.pahana.web;

import java.io.IOException;
import java.util.List;

import com.pahana.db.BillDAO;
import com.pahana.db.CustomerDAO;
import com.pahana.model.Bill;
import com.pahana.model.Customer;
import com.pahana.service.PdfGenerationService;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/reports")
public class ReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;
    private BillDAO billDAO;
    private PdfGenerationService pdfService;

    public void init() {
        customerDAO = new CustomerDAO();
        billDAO = new BillDAO();
        pdfService = new PdfGenerationService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "select"; // Default action is to show the selection page
        }

        switch (action) {
            case "view":
                viewCustomerReport(request, response);
                break;
                
            case "downloadReport":
                downloadCustomerReport(request, response);
                break;
            default: // "select"
                showCustomerSelection(request, response);
                break;
        }
    }

    private void showCustomerSelection(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch all customers to populate the dropdown menu
        List<Customer> customerList = customerDAO.getAllCustomers();
        request.setAttribute("customerList", customerList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("report-selection.jsp");
        dispatcher.forward(request, response);
    }

    private void viewCustomerReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerId = request.getParameter("customerId");
        
        // 1. Get the specific customer's details
        Customer customer = customerDAO.getCustomerById(customerId);
        
        // 2. Get that customer's entire bill history from the DAO
        List<Bill> billHistory = billDAO.getBillsByCustomer(customerId);
        
        // 3. Pass both the customer object and the list of bills to the JSP
        request.setAttribute("customer", customer);
        request.setAttribute("billHistory", billHistory);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer-report.jsp");
        dispatcher.forward(request, response);
    }
    
    // --- NEW METHOD ---
    /**
     * Handles the logic for generating and serving the full customer report as a PDF.
     */
    private void downloadCustomerReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String customerId = request.getParameter("customerId");
        
        // 1. Fetch all the data needed for the report
        Customer customer = customerDAO.getCustomerById(customerId);
        List<Bill> billHistory = billDAO.getBillsByCustomer(customerId);
        
        // 2. Generate the PDF file in memory using our service
        ByteArrayOutputStream baos = pdfService.generateCustomerReportPdf(customer, billHistory);
        
        // 3. Set the HTTP response headers to trigger a download
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"report-" + customerId + ".pdf\"");
        response.setContentLength(baos.size());
        
        // 4. Write the PDF data to the response
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
        os.close();
    }
}