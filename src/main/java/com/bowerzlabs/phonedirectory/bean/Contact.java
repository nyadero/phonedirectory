/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bowerzlabs.phonedirectory.bean;

/**
 *
 * @author bronyst
 */
public class Contact {
       private int id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String idNumber;
    private String dateOfBirth;
    private String gender;
    private String organization;
    private String maskedName;
    private String maskedPhoneNumber;
    private String hashedPhoneNumber;

  
    public Contact(int id, String fullName, String phoneNumber, String email, String idNumber, String dateOfBirth, String gender, String organization, String maskedName, String maskedPhoneNumber, String hashedPhoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.organization = organization;
        this.maskedName = maskedName;
        this.maskedPhoneNumber = maskedPhoneNumber;
        this.hashedPhoneNumber = hashedPhoneNumber;
    }
    
    

    public Contact() {
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

 

    public String getPhoneNumber() {
        return phoneNumber;
    }

 
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getMaskedName() {
        return maskedName;
    }

    public String getMaskedPhoneNumber() {
        return maskedPhoneNumber;
    }

    public String getHashedPhoneNumber() {
        return hashedPhoneNumber;
    }

    @Override
    public String toString() {
        return "Contact{" + "id=" + id + ", fullName=" + fullName + ", phoneNumber=" + phoneNumber + ", email=" + email + ", idNumber=" + idNumber + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", organization=" + organization + ", maskedName=" + maskedName + ", maskedPhoneNumber=" + maskedPhoneNumber + ", hashedPhoneNumber=" + hashedPhoneNumber + '}';
    }

    public void setMaskedPhoneNumber(String maskedPhoneNumber) {
    }
}
