package com.employee.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateSalary")
public class UpdateSalaryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        double newSalary = Double.parseDouble(request.getParameter("salary"));

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "UPDATE employees SET salary = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setDouble(1, newSalary);
                stmt.setInt(2, id);

                int rowsUpdated = stmt.executeUpdate();
                response.getWriter().println(rowsUpdated > 0 ? "Salary updated successfully!" : "Employee ID not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred: " + e.getMessage());
        }
    }
}
