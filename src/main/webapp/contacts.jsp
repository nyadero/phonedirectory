<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bowerzlabs.phonedirectory.bean.Contact" %>
<html>
<head>
    <title>Contacts Directory</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h2>Contacts Directory</h2>

    <h3>Search Contacts</h3>
    <form action="" method="get">
        <label for="organization">Organization:</label>
        <input type="text" id="organization" name="organization">

        <label for="phoneHash">Phone Hash:</label>
        <input type="text" id="phoneHash" name="phoneHash">

        <label for="maskedName">Masked Name:</label>
        <input type="text" id="maskedName" name="maskedName">

        <label for="maskedPhone">Masked Phone:</label>
        <input type="text" id="maskedPhone" name="maskedPhone">

        <button type="submit">Search</button>
    </form>

    <hr>

    <%
        String error = (String) request.getAttribute("error");
        List<Contact> contacts = (List<Contact>) request.getAttribute("contacts");
        Boolean isSearch = (Boolean) request.getAttribute("isSearch");

        if (error != null) {
    %>
        <p style="color:red;"><%= error %></p>
    <%
        } else if (contacts != null && !contacts.isEmpty()) {
    %>
        <h3><%= isSearch != null && isSearch ? "Search Results" : "All Contacts" %></h3>

        <table>
            <tr>
                <th>Full Name</th>
                <th>Phone Number</th>
                <th>Organization</th>
                <th>Email</th>
                <th>Id</th>
                <th>Masked Name</th>
                <th>Masked Phone</th>
                <th>Hashed Phone</th>
            </tr>
        <%
            for (Contact contact : contacts) {
        %>
            <tr>
                <td><%= contact.getFullName() %></td>
                <td><%= contact.getPhoneNumber() %></td>
                <td><%= contact.getOrganization() %></td>
                <td><%= contact.getEmail() %></td>
                <td><%= contact.getIdNumber() %></td>
                <td><%= contact.getMaskedName() %></td>
                <td><%= contact.getMaskedPhoneNumber()%></td>
                <td><%= contact.getHashedPhoneNumber()%></td>
            </tr>
        <%
            }
        %>
        </table>
    <%
        } else {
    %>
        <p>No contacts found.</p>
    <%
        }
    %>
</body>
</html>
