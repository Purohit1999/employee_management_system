package com.employee.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(RegisterServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve the form data
        String idStr = request.getParameter("employeeId");
        int id = Integer.parseInt(idStr);
        String name = request.getParameter("employeeName");
        String email = request.getParameter("employeeEmail");
        String password = request.getParameter("employeePassword");
        String salaryStr = request.getParameter("employeeSalary");
        double salary = Double.parseDouble(salaryStr);

        try (Connection connection = DBConnection.getConnection()) {
            // SQL to insert employee data
            String sql = "INSERT INTO employees (id, name, email, password, salary) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set the parameters
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setDouble(5, salary);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                response.getWriter().println("Employee registered successfully.");
            } else {
                response.getWriter().println("Failed to register employee.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while registering employee", e);
            response.getWriter().println("An error occurred while registering the employee: " + e.getMessage());
        }
    }
}
