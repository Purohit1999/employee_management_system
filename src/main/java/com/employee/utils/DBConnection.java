package com.employee.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/employee_servlet_jdbc";
    private static final String USER = "root";
    private static final String PASSWORD = "12345Param108";

    // Logger instance
    private static final Logger logger = Logger.getLogger(DBConnection.class.getName());

    /**
     * Establishes and returns a connection to the MySQL database.
     *
     * @return Connection object or null if connection fails
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "MySQL JDBC Driver not found", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while connecting to the database", e);
        }
        return connection;
    }
}
