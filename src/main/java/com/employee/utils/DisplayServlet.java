package com.employee.utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/display")
public class DisplayServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(DisplayServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String[]> employees = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM employees");

                while (rs.next()) {
                    employees.add(new String[]{
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("salary")
                    });
                }
            } else {
                logger.log(Level.SEVERE, "Database connection failed. Employees cannot be retrieved.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while fetching employees from the database", e);
        }

        // Set the employee list as a request attribute and forward it to the JSP
        request.setAttribute("employees", employees);
        request.getRequestDispatcher("display.jsp").forward(request, response);
    }
}
