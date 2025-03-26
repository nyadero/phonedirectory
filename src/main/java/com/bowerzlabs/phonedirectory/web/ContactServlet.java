/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.bowerzlabs.phonedirectory.web;

import com.bowerzlabs.phonedirectory.bean.Contact;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author bronyst
 */
@WebServlet("/")
public class ContactServlet extends HttpServlet {
    private static final String API_URL_ALL = "http://localhost:8080/contactsregistry/api/contacts";
    private static final String API_URL_ORG = "http://localhost:8080/contactsregistry/api/contacts/organization";
    private static final String API_URL_SEARCH = "http://localhost:8080/contactsregistry/api/contacts/search";

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String organization = request.getParameter("organization");
    String phoneHash = request.getParameter("phoneHash");
    String maskedName = request.getParameter("maskedName");
    String maskedPhone = request.getParameter("maskedPhone");

    String apiUrl = API_URL_ALL; // Default to fetch all contacts
    boolean isSearch = false;

    StringBuilder queryParams = new StringBuilder();
    
    if (organization != null && !organization.isEmpty() || 
        phoneHash != null && !phoneHash.isEmpty() || 
        maskedName != null && !maskedName.isEmpty() || 
        maskedPhone != null && !maskedPhone.isEmpty()) {
        
        apiUrl = API_URL_SEARCH + "?"; // Use search API when any filter is provided
        isSearch = true;

        if (organization != null && !organization.isEmpty()) {
            queryParams.append("organization=").append(URLEncoder.encode(organization, StandardCharsets.UTF_8)).append("&");
        }
        if (phoneHash != null && !phoneHash.isEmpty()) {
            queryParams.append("phoneHash=").append(URLEncoder.encode(phoneHash, StandardCharsets.UTF_8)).append("&");
        }
        if (maskedName != null && !maskedName.isEmpty()) {
            queryParams.append("maskedName=").append(URLEncoder.encode(maskedName, StandardCharsets.UTF_8)).append("&");
        }
        if (maskedPhone != null && !maskedPhone.isEmpty()) {
            queryParams.append("maskedPhone=").append(URLEncoder.encode(maskedPhone, StandardCharsets.UTF_8)).append("&");
        }

        apiUrl += queryParams.toString().replaceAll("&$", ""); // Remove trailing '&'
    }

    try {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestApi = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> responseApi = client.send(requestApi, HttpResponse.BodyHandlers.ofString());
        String jsonResponse = responseApi.body();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Contact> contacts = objectMapper.readValue(jsonResponse, new TypeReference<List<Contact>>() {});

        request.setAttribute("contacts", contacts);
    } catch (Exception e) {
        request.setAttribute("error", "Failed to fetch data: " + e.getMessage());
        e.printStackTrace();
    }

    request.setAttribute("isSearch", isSearch);
    request.getRequestDispatcher("contacts.jsp").forward(request, response);
}


}
