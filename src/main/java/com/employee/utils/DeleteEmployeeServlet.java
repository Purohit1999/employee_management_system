package com.employee.utils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/delete")
public class DeleteEmployeeServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(DeleteEmployeeServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve employee ID from the form
        String employeeId = request.getParameter("employeeId");

        try (Connection connection = DBConnection.getConnection()) {
            // SQL to delete employee based on ID
            String sql = "DELETE FROM employees WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(employeeId));

            int rowsAffected = preparedStatement.executeUpdate();

            // Check if deletion was successful
            if (rowsAffected > 0) {
                response.getWriter().println("Employee deleted successfully.");
            } else {
                response.getWriter().println("Employee with ID " + employeeId + " not found.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while deleting employee", e);
            response.getWriter().println("An error occurred while deleting the employee: " + e.getMessage());
        }
    }
}
