<%-- 
    Document   : errorpage
    Created on : Mar 28, 2025, 8:46:47 PM
    Author     : bronyst
--%>


<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <h1>Error</h1>
        <h2><%= exception.getMessage() %></h2>
    </body>
</html>