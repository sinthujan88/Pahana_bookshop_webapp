# Pahana Edu - Bookstore Billing System

PahanaEduWebApp is a comprehensive, web-based billing and inventory management system designed for the "Pahana Edu" bookstore. It replaces manual processes with an efficient, secure, and user-friendly Java application.
---
## Pahana Edu System Video Demonstration
https://drive.google.com/drive/folders/1e2icZcPfxOHAHcE0Jc2lu2MeG4t8lHuM?usp=sharing
---
## Database file 
https://drive.google.com/drive/folders/1skJnWzLmtxH-vx71PsW4eixWcBAsI6ai?usp=sharing
---
## Table of Contents
1. [Project Overview](#project-overview)
2. [Key Features](#key-features)
3. [Technology Stack](#technology-stack)
4. [Setup and Installation](#setup-and-installation)
5. [API Endpoints](#api-endpoints)
6. [Project Structure](#project-structure)
7. [Author](#author)

---

## Project Overview

This system provides a complete solution for managing customer accounts, bookstore inventory, and billing information. It allows staff to perform essential tasks such as user authentication, managing customer and item data, calculating bills, and generating reports, all through a secure web interface. The application is built as a distributed system, exposing key data through a RESTful API.

---

## Key Features

-   **Secure User Authentication:** Role-based login system with securely hashed passwords using jBCrypt.
-   **Customer Management (CRUD):** Add, view, update, and delete customer accounts.
-   **Item Management (CRUD):** Full control over the bookstore's inventory, including adding books, updating prices, and managing stock levels with validation to prevent negative stock.
-   **Dynamic Bill Calculation:** An interactive interface for creating new bills for customers, calculating totals in real-time.
-   **PDF Receipt Generation:** Dynamically generate and print PDF receipts for completed transactions using the iTextPDF library.
-   **Reporting:** View reports on customer data and sales history.
-   **Distributed API:** A RESTful API built with JAX-RS provides programmatic access to core data like items and customers.
-   **Robust Validation:** Includes both client-side (HTML/JS) and server-side (Java) validation to ensure data integrity.

---

## Technology Stack

-   **Backend:** Java 17, Jakarta Servlets, JavaServer Pages (JSP)
-   **Database:** Microsoft SQL Server
-   **Build Tool:** Apache Maven
-   **Web Server:** Apache Tomcat v11.0
-   **Key Libraries:**
    -   `mssql-jdbc`: For database connectivity.
    -   `jbcrypt`: For secure password hashing.
    -   `itext7-core`: For PDF generation.
    -   `Jakarta EE API`: For web application development.
    -   `JAX-RS (Jersey)`: For creating the REST API.
-   **Frontend:** HTML5, CSS3, JavaScript
-   **Version Control:** Git & GitHub

---

## Setup and Installation

Follow these steps to get the project running on a local machine.

### 1. Prerequisites
-   **JDK 17** or higher
-   **Apache Maven** 3.6 or higher
-   **Microsoft SQL Server** with an accessible instance
-   **Apache Tomcat 11.0** configured in your IDE
-   **Git** for version control

### 2. Database Setup
1.  Open SQL Server Management Studio (SSMS).
2.  Create a new database named `PahanaEduDB`.
3.  Run the following SQL scripts to create the necessary tables:
    ```sql
    -- Example for the 'users' table. Create similar tables for items, customers, bills, etc.
    CREATE TABLE users (
        user_id INT PRIMARY KEY IDENTITY(1,1),
        username VARCHAR(50) NOT NULL UNIQUE,
        password_hash VARCHAR(100) NOT NULL,
        role VARCHAR(20) NOT NULL
    );
    ```

### 3. Configuration
1.  Clone the repository from GitHub.
2.  Open the project in your IDE (e.g., Eclipse).
3.  Navigate to the `DatabaseManager.java` file located at `src/main/java/com/pahana/db/DatabaseManager.java`.
4.  Update the database connection string with your specific SQL Server details (server name, instance name, username, and password).
    ```java
    // Example connection string in DatabaseManager.java
    private static final String URL = "jdbc:sqlserver://YOUR_SERVER_NAME\\YOUR_INSTANCE;databaseName=PahanaEduDB;encrypt=false;trustServerCertificate=true;";
    private static final String USER = "your_sql_username";
    private static final String PASSWORD = "your_sql_password";
    ```

### 4. Running the Application
1.  In Eclipse, ensure the project is configured as a Maven project.
2.  Right-click the project -> **Maven -> Update Project...**
3.  Right-click the project -> **Run As -> Run on Server**, and choose your configured Tomcat server.
4.  The application will be accessible at `http://localhost:8080/PahanaEduWebApp/`.

---

## API Endpoints

The application exposes several RESTful API endpoints under the `/api` path.

-   `GET /api/items`: Retrieves a list of all items in the inventory.
-   `GET /api/items/{id}`: Retrieves a single item by its ID.
-   `GET /api/customers`: Retrieves a list of all customers.

---

## Project Structure

The project follows a standard layered architecture pattern to separate concerns:

-   `com.pahana.web`: **Controller Layer** - Contains all the Servlets and Filters that handle user requests and control application flow.
-   `com.pahana.service`: **Service Layer** - Contains business logic, such as the `PdfGenerationService`.
-   `com.pahana.db`: **Data Access Layer (DAO)** - Contains classes responsible for all database interactions (SQL queries).
-   `com.pahana.model`: **Model Layer** - Contains the POJOs (Plain Old Java Objects) that represent the data (e.g., `User.java`, `Item.java`).
-   `webapp`: **View Layer** - Contains all the JSPs, CSS, and JavaScript files that make up the user interface.

---

## Author
**S.Sinthujan**
