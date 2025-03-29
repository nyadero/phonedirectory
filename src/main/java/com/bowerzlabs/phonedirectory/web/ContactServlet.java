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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author bronyst
 */
@WebServlet("/")
public class ContactServlet extends HttpServlet {
    private static final String API_URL = "http://localhost:8080/contactsregistry/api/contacts";

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("action " + action);

        // Fetch all or search contacts
        String apiUrl = API_URL;
        StringBuilder queryParams = new StringBuilder();

        String organization = request.getParameter("organization");
        String phoneHash = request.getParameter("phoneHash");
        String maskedPhone = request.getParameter("maskedPhone");
        String maskedName = request.getParameter("maskedName");


        if (organization != null && !organization.isEmpty()) {
            queryParams.append("organization=").append(URLEncoder.encode(organization, StandardCharsets.UTF_8)).append("&");
        }
        if (phoneHash != null && !phoneHash.isEmpty()) {
            queryParams.append("phoneHash=").append(URLEncoder.encode(phoneHash, StandardCharsets.UTF_8)).append("&");
        }
        if (maskedPhone != null && !maskedPhone.isEmpty()) {
            queryParams.append("maskedPhone=").append(URLEncoder.encode(maskedPhone, StandardCharsets.UTF_8)).append("&");
        }
        if (maskedName != null && !maskedName.isEmpty()) {
            queryParams.append("maskedName=").append(URLEncoder.encode(maskedName, StandardCharsets.UTF_8)).append("&");
        }

        if (queryParams.length() > 0) {
            apiUrl = API_URL + "/search?" + queryParams.toString().replaceAll("&$", "");
        }
        
        if ("edit".equals(action)) {
           // Fetch a single contact for editing
           String contactId = request.getParameter("id");
           if (contactId != null && !contactId.isEmpty()) {
               try {
                   HttpRequest requestApi = HttpRequest.newBuilder()
                           .uri(URI.create(API_URL + "/" + contactId))
                           .header("Accept", "application/json")
                           .GET()
                           .build();

                   HttpResponse<String> responseApi = client.send(requestApi, HttpResponse.BodyHandlers.ofString());
                   Contact contact = objectMapper.readValue(responseApi.body(), Contact.class);
                   request.setAttribute("contact", contact);
                   request.getRequestDispatcher("contact-form.jsp").forward(request, response);
                   return;
               } catch (Exception e) {
                   e.printStackTrace();
                   request.setAttribute("error", "Failed to fetch contact: " + e.getMessage());
               }
           }
       }else if("delete".equals(action)){
            doDelete(request, response);
       }else if("create".equals(action)) {
           request.getRequestDispatcher("contact-form.jsp").forward(request, response);
       }else{

        try {
            HttpRequest requestApi = HttpRequest.newBuilder().uri(URI.create(apiUrl)).GET()
                    .header("Accept", "application/json").build();

            HttpResponse<String> responseApi = client.send(requestApi, HttpResponse.BodyHandlers.ofString());
            System.out.println("responseApi" + responseApi);
            List<Contact> contacts = objectMapper.readValue(responseApi.body(), new TypeReference<>() {});
            
            request.setAttribute("contacts", contacts);
        } catch (Exception e) {
            request.setAttribute("error", "Failed to fetch data: " + e.getMessage());
            e.printStackTrace();
        }

        request.getRequestDispatcher("contacts.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
    
        if ("delete".equals(action)) {
             doDelete(request, response);
         } else if ("edit".equals(action)) {
             doPut(request, response);
         }else{
             System.out.println("inside create contact");
        // Create a new contact
            String fullName = request.getParameter("fullName");
            String phoneNumber = request.getParameter("phoneNumber");
            String email = request.getParameter("email");
            String idNumber = request.getParameter("idNumber");
            String dateOfBirth = request.getParameter("dateOfBirth");
            String gender = request.getParameter("gender");
            String organization = request.getParameter("organization");

            JsonObject json = new JsonObject();
            json.addProperty("fullName", fullName);
            json.addProperty("phoneNumber", phoneNumber);
            json.addProperty("organization", organization);
            json.addProperty("email", email);
            json.addProperty("idNumber", idNumber);
            json.addProperty("dateOfBirth", dateOfBirth);
            json.addProperty("gender", gender);

        HttpRequest requestApi = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        try {
            HttpResponse<String> responseApi = client.send(requestApi, HttpResponse.BodyHandlers.ofString());
            System.out.println("responseApi " + responseApi);
        } catch (InterruptedException ex) {
            Logger.getLogger(ContactServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

         response.sendRedirect(request.getContextPath() + "/");
 
        }
      
    }
    
        @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Inside edit contact method");
            int id = Integer.parseInt(request.getParameter("id"));
            String fullName = request.getParameter("fullName");
            String phoneNumber = request.getParameter("phoneNumber");
            String email = request.getParameter("email");
            String idNumber = request.getParameter("idNumber");
            String dateOfBirth = request.getParameter("dateOfBirth");
            String gender = request.getParameter("gender");
            String organization = request.getParameter("organization");

        JsonObject json = new JsonObject();
        json.addProperty("fullName", fullName);
        json.addProperty("phoneNumber", phoneNumber);
        json.addProperty("email", email);
        json.addProperty("organization", organization);
         json.addProperty("idNumber", idNumber);
            json.addProperty("dateOfBirth", dateOfBirth);
            json.addProperty("gender", gender);

        HttpRequest requestApi = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        try {
            client.send(requestApi, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException ex) {
            Logger.getLogger(ContactServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

         response.sendRedirect(request.getContextPath() + "/");

    }


        @Override
     protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                 System.out.println("Inside edit contact method");
         String contactId = request.getParameter("id");
            System.out.println("id " + contactId);

         if (contactId != null && !contactId.isEmpty()) {
             HttpRequest requestApi = HttpRequest.newBuilder()
                     .uri(URI.create(API_URL + "/" + contactId))
                     .DELETE()
                     .build();
             
             System.out.println("API" + requestApi);

             try {
                 client.send(requestApi, HttpResponse.BodyHandlers.ofString());
             } catch (InterruptedException ex) {
                 Logger.getLogger(ContactServlet.class.getName()).log(Level.SEVERE, null, ex);
             }
         }

            response.sendRedirect(request.getContextPath() + "/");
     }

}