package com.employee.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {
                String sql = "SELECT * FROM employees WHERE email = ? AND password = ?";
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, email);
                    stmt.setString(2, password);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            HttpSession session = request.getSession();
                            session.setAttribute("user", rs.getString("name"));
                            response.getWriter().println("Login Successful! Welcome, " + rs.getString("name"));
                            logger.log(Level.INFO, "User {0} logged in successfully.", rs.getString("name"));
                        } else {
                            response.getWriter().println("Invalid Credentials! Try again.");
                            logger.log(Level.WARNING, "Login failed for email: {0}", email);
                        }
                    }
                }
            } else {
                logger.log(Level.SEVERE, "Failed to establish database connection.");
                response.getWriter().println("Error: Could not connect to the database. Please try again later.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during login process", e);
            response.getWriter().println("Error: An unexpected error occurred. Please try again later.");
        }
    }
}
