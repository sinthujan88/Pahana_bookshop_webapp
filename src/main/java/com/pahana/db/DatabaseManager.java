package com.pahana.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String SERVER_NAME = "DESKTOP-133\\SQLEXPRESS";
    private static final String DATABASE_NAME = "PahanaEduDB";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345678"; // Your password

    private static final String URL = String.format(
        "jdbc:sqlserver://%s;databaseName=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=true;",
        SERVER_NAME, DATABASE_NAME, USER, PASSWORD);

    // --- NEW STATIC BLOCK TO MANUALLY LOAD THE DRIVER ---
    // This code runs once when the DatabaseManager class is first used.
    // It explicitly loads the SQL Server driver class, forcing it to register with the DriverManager.
    // This is the classic fix for "No suitable driver found" errors in web apps.
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            // This error will be thrown if the JAR file is truly not on the classpath.
            throw new IllegalStateException("SQL Server driver not found!", e);
        }
    }

    // This method remains the same.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}