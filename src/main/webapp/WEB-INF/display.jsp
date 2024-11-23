<%@ page import="java.util.List" %>
<%@ page import="java.lang.String" %>
<%@ page import="java.sql.ResultSet" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employee List</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<%
    // Retrieve the employee list from the servlet
    List<String[]> employees = (List<String[]>) request.getAttribute("employees");
%>
    <div class="container mt-5">
        <h1 class="text-center mb-4">Employee List</h1>
        <table class="table table-bordered table-hover shadow-sm">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Salary</th>
                </tr>
            </thead>
            <tbody>
                <% if (employees != null && !employees.isEmpty()) { %>
                    <% for (String[] employee : employees) { %>
                        <tr>
                            <td><%= employee[0] %></td>
                            <td><%= employee[1] %></td>
                            <td><%= employee[2] %></td>
                            <td><%= employee[3] %></td>
                        </tr>
                    <% } %>
                <% } else { %>
                    <tr>
                        <td colspan="4" class="text-center">No Employees Found</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
