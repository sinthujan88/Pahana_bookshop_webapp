<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>System Help</title>
<link rel="stylesheet" href="styles/form-styles.css"> <%-- Reusing form styles for the container --%>
<link rel="stylesheet" type="text/css" href="styles/main.css">
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="container">
        <h1 class="page-title">System Usage Guidelines</h1>
        
        <h3>Dashboard</h3>
        <p>The dashboard is the main screen after you log in. It provides access to all major functions of the system.</p>
        
        <h3>Manage Items</h3>
        <p>Click this card to view, add, edit, or delete books from the store's inventory. You must have items in the system before you can create a bill.</p>
        
        <h3>Manage Customers</h3>
        <p>Click this card to add new customers or edit the details of existing ones. A customer must be registered before you can create a bill for them.</p>

        <h3>Calculate & Print Bill</h3>
        <p>This is the main point-of-sale screen. To create a bill:</p>
        <ol>
            <li>Use the "Select Item" dropdown and Quantity field on the left to add items to the current bill.</li>
            <li>As you add items, they will appear in the "Current Bill" section on the right.</li>
            <li>Once all items are added, select the correct customer from the "Bill To Customer" dropdown.</li>
            <li>Click "Finalize & Save Bill". The system will save the bill and automatically start a PDF download of the receipt.</li>
        </ol>

        <h3>Display Account Details</h3>
        <p>This section is for reporting. Select a customer from the dropdown to see their contact information and a full history of every bill they have ever received.</p>
        
        <h3>Logout</h3>
        <p>Click the "Logout" button in the top-right corner at any time to securely exit the system and return to the login screen.</p>
    </div>
    
     <jsp:include page="footer.jsp" />
   
</body>
</html>