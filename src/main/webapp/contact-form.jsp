<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bowerzlabs.phonedirectory.bean.Contact" %>
<%
    String action = (request.getParameter("action") != null) ? request.getParameter("action") : "create";
    boolean isEdit = action.equals("edit");
    String pageTitle = isEdit ? "Edit Contact" : "Create Contact";
    String buttonLabel = isEdit ? "Update Contact" : "Create Contact";
%>
<html>
<head>
    <title><%= pageTitle %></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex justify-content-center mt-4">
    <div class="container card p-4 shadow-lg" style="max-width: 600px;">
        <h2 class="text-center mb-4"><%= pageTitle %></h2>
        <form action="ContactServlet" method="POST">
            <% if (isEdit) { %>
                <input type="hidden" name="id" value="${contact.id}">
            <% } %>

            <div class="mb-3">
                <label class="form-label">Full Name</label>
                <input type="text" name="fullName" value="${contact.fullName}" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Phone Number</label>
                <input type="text" name="phoneNumber" value="${contact.phoneNumber}" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" name="email" value="${contact.email}" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">ID Number</label>
                <input type="text" name="idNumber" value="${contact.idNumber}" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Date of Birth</label>
                <input type="date" name="dateOfBirth" value="${contact.dateOfBirth}" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Gender</label>
                <select name="gender" class="form-select" required>
                    <option value="Male" ${contact.gender == 'Male' ? 'selected' : ''}>Male</option>
                    <option value="Female" ${contact.gender == 'Female' ? 'selected' : ''}>Female</option>
                    <option value="Other" ${contact.gender == 'Other' ? 'selected' : ''}>Other</option>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Organization</label>
                <input type="text" name="organization" value="${contact.organization}" class="form-control" required>
            </div>

            <input type="hidden" name="action" value="<%= action %>">
            <div class="d-flex justify-content-between">
                <a href="index.jsp" class="btn btn-secondary">Cancel</a>
                <input type="submit" value="<%= buttonLabel %>" class="btn btn-primary">
            </div>
        </form>
    </div>
</body>
</html>
