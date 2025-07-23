package com.pahana.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.pahana.db.BillDAO;
import com.pahana.db.CustomerDAO;
import com.pahana.model.Bill;
import com.pahana.model.Customer;
import com.pahana.service.PdfGenerationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/receipt")
public class ReceiptServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BillDAO billDAO;
    private CustomerDAO customerDAO;
    private PdfGenerationService pdfService;

    public void init() {
        billDAO = new BillDAO();
        customerDAO = new CustomerDAO();
        pdfService = new PdfGenerationService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int billId = Integer.parseInt(request.getParameter("billId"));

        // 1. Fetch all necessary data from the database
        Bill bill = billDAO.getBillById(billId);
        Customer customer = customerDAO.getCustomerById(bill.getCustomerAccountNumber());

        // 2. Generate the PDF using our service
        ByteArrayOutputStream baos = pdfService.generateBillPdf(bill, customer);

        // --- Teaching Point: HTTP Response for File Download ---
        // These headers tell the browser how to handle the response.
        // setContentType tells it "this is a PDF file".
        // setHeader with "attachment" tells it to open a "Save As..." download dialog.
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"receipt-" + billId + ".pdf\"");
        response.setContentLength(baos.size());

        // 4. Write the PDF content to the response output stream
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
        os.close();
    }
}