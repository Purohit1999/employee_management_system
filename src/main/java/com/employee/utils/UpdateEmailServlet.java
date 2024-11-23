package com.employee.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateEmail")
public class UpdateEmailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String newEmail = request.getParameter("email");

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "UPDATE employees SET email = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, newEmail);
                stmt.setInt(2, id);

                int rowsUpdated = stmt.executeUpdate();
                response.getWriter().println(rowsUpdated > 0 ? "Email updated successfully!" : "Employee ID not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred: " + e.getMessage());
        }
    }
}
