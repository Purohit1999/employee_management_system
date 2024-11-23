package com.employee.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateName")
public class UpdateNameServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String newName = request.getParameter("name");

        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {
                String sql = "UPDATE employees SET name = ? WHERE id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, newName);
                    stmt.setInt(2, id);

                    int rowsUpdated = stmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        response.getWriter().println("Name updated successfully for Employee ID: " + id);
                    } else {
                        response.getWriter().println("Employee ID not found.");
                    }
                }
            } else {
                response.getWriter().println("Database connection failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred: " + e.getMessage());
        }
    }
}
