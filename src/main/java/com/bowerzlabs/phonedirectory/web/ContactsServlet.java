package com.bowerzlabs.phonedirectory.web;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bronyst
 */
@WebServlet("/")
public class ContactsServlet extends HttpServlet {

    private static final String API_URL = "http://localhost:8080/contactsregistry/api/contacts"; 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Get organization parameter from request
        String organization = request.getParameter("organization");
        if (organization == null || organization.isEmpty()) {
            out.print("{\"error\": \"Organization parameter is required\"}");
            return;
        }

        try {
            // Create HTTP client
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest requestApi = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "?organization=" + organization))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            // Send request and get response
            HttpResponse<String> responseApi = client.send(requestApi, HttpResponse.BodyHandlers.ofString());

            // Parse JSON response
            JsonObject jsonResponse = JsonParser.parseString(responseApi.body()).getAsJsonObject();
            out.print(jsonResponse);
        } catch (Exception e) {
            out.print("{\"error\": \"Failed to fetch data\"}");
            e.printStackTrace();
        }
    }
}