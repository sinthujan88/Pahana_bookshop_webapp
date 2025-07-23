package com.pahana.service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import com.pahana.model.Bill; // Add this import
import java.util.List;        // And this one
import java.text.SimpleDateFormat; // For formatting dates

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import com.pahana.model.Bill;
import com.pahana.model.Bill_Item;
import com.pahana.model.Customer;

public class PdfGenerationService {

    public ByteArrayOutputStream generateBillPdf(Bill bill, Customer customer) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // --- PDF Content ---
        document.add(new Paragraph("Pahana Edu Bookstore")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold().setFontSize(20));
        
        document.add(new Paragraph("Official Receipt")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(15));
        
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Bill ID: " + bill.getBillId()));
        document.add(new Paragraph("Date: " + bill.getBillDate().toString()));
        document.add(new Paragraph("Customer: " + customer.getFullName() + " (Acc: " + customer.getAccountNumber() + ")"));
        
        document.add(new Paragraph("\n"));

        // --- ALTERNATIVE TABLE CREATION ---
        // This is a more explicit way to define the table, which can be more stable.
        float[] columnWidths = {4, 1, 2, 2};
        Table table = new Table(columnWidths);
        table.setWidth(UnitValue.createPercentValue(100));
        
        // Add Headers
        table.addHeaderCell("Item Name");
        table.addHeaderCell("Qty");
        table.addHeaderCell("Unit Price");
        table.addHeaderCell("Subtotal");

        // Add Data Rows
        for (Bill_Item item : bill.getBillItems()) {
            BigDecimal subtotal = item.getPriceAtTimeOfSale().multiply(new BigDecimal(item.getQuantity()));
            table.addCell(item.getItemName());
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell(String.format("%.2f", item.getPriceAtTimeOfSale()));
            table.addCell(String.format("%.2f", subtotal));
        }
        
        document.add(table);
        
        document.add(new Paragraph("Total Amount: LKR " + String.format("%.2f", bill.getTotalAmount()))
                .setTextAlignment(TextAlignment.RIGHT)
                .setBold().setFontSize(14).setMarginTop(10));

        document.close();
        return baos;
    }
    
    
    // --- NEW METHOD ---
    /**
     * Generates a complete customer report PDF including their details and full bill history.
     * @param customer The customer object.
     * @param billHistory A list of all bills for that customer.
     * @return A ByteArrayOutputStream containing the generated PDF data.
     */
    public ByteArrayOutputStream generateCustomerReportPdf(Customer customer, List<Bill> billHistory) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        
        // --- PDF Content ---
        document.add(new Paragraph("Customer Account Report")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold().setFontSize(20));
        
        document.add(new Paragraph("\n"));

        // Add Customer Details Section
        document.add(new Paragraph("Customer Information").setBold().setFontSize(16));
        document.add(new Paragraph("Account #: " + customer.getAccountNumber()));
        document.add(new Paragraph("Full Name: " + customer.getFullName()));
        document.add(new Paragraph("Address: " + customer.getAddress()));
        document.add(new Paragraph("Telephone: " + customer.getTelephone()));
        document.add(new Paragraph("Email: " + customer.getEmail()));
        
        document.add(new Paragraph("\n\n"));
        
        // Add Billing History Section
        document.add(new Paragraph("Billing History").setBold().setFontSize(16));
        
        // Create the table for the bill history
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 3}));
        table.setWidth(UnitValue.createPercentValue(100)).setMarginTop(10);
        table.addHeaderCell("Bill ID");
        table.addHeaderCell("Bill Date");
        table.addHeaderCell("Total Amount");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Bill bill : billHistory) {
            table.addCell(String.valueOf(bill.getBillId()));
            table.addCell(sdf.format(bill.getBillDate()));
            table.addCell(String.format("LKR %.2f", bill.getTotalAmount()));
        }
        
        document.add(table);
        
        if (billHistory.isEmpty()) {
            document.add(new Paragraph("No billing history found for this customer."));
        }
        
        document.close();
        return baos;
    }
}