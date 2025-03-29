<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bowerzlabs.phonedirectory.bean.Contact" %>
<html>
<head>
    <title>Contacts Directory</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex justify-content-center mt-4">
    <div class="container-fluid" style="max-width: 90%;">  
    <h2 class="mb-3">Contacts Directory</h2>
    
    <a href="?action=create" class="btn btn-primary mb-3">Create New Contact</a>
    
    <h3>Search Contacts</h3>
    <form action="" method="get" class="mb-3">
        <div class="row g-2">
            <div class="col-md-3">
                <label for="organization" class="form-label">Organization:</label>
                <input type="text" id="organization" name="organization" class="form-control">
            </div>
            <div class="col-md-3">
                <label for="phoneHash" class="form-label">Phone Hash:</label>
                <input type="text" id="phoneHash" name="phoneHash" class="form-control">
            </div>
            <div class="col-md-3">
                <label for="maskedName" class="form-label">Masked Name:</label>
                <input type="text" id="maskedName" name="maskedName" class="form-control">
            </div>
            <div class="col-md-3">
                <label for="maskedPhone" class="form-label">Masked Phone:</label>
                <input type="text" id="maskedPhone" name="maskedPhone" class="form-control">
            </div>
        </div>
        <button type="submit" class="btn btn-success mt-3">Search</button>
    </form>
    
    <hr>
    
    <% String error = (String) request.getAttribute("error");
       List<Contact> contacts = (List<Contact>) request.getAttribute("contacts");
       Boolean isSearch = (Boolean) request.getAttribute("isSearch");
       if (error != null) { %>
        <p class="text-danger"><%= error %></p>
    <% } else if (contacts != null && !contacts.isEmpty()) { %>
        <h3><%= isSearch != null && isSearch ? "Search Results" : "All Contacts" %></h3>
        <div class="table-responsive">
            <table class="table table-striped table-bordered">
                <thead class="table-dark">
                    <tr>
                        <th>Id</th>
                        <th>Full Name</th>
                        <th>Phone Number</th>
                        <th>Organization</th>
                        <th>Email</th>
                        <th>Id Number</th>
                        <th>Masked Name</th>
                        <th>Masked Phone</th>
                        <th>Hashed Phone</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody> 
                <% for (Contact contact : contacts) { %>
                    <tr>
                        <td><%= contact.getId() %></td>
                        <td><%= contact.getFullName() %></td>
                        <td><%= contact.getPhoneNumber() %></td>
                        <td><%= contact.getOrganization() %></td>
                        <td><%= contact.getEmail() %></td>
                        <td><%= contact.getIdNumber() %></td>
                        <td><%= contact.getMaskedName() %></td>
                        <td><%= contact.getMaskedPhoneNumber() %></td>
                        <td><%= contact.getHashedPhoneNumber() %></td>
                        <td>
                            <a href="?action=edit&id=<%= contact.getId() %>" class="btn btn-warning btn-sm">Edit</a>
                            <a href="?action=delete&id=<%= contact.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this contact?');">Delete</a>
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    <% } else { %>
        <p class="text-muted">No contacts found.</p>
    <% } %>
    </div>
</body>
</html>
