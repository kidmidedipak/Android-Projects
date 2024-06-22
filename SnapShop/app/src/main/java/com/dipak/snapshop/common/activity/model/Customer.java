package com.dipak.snapshop.common.activity.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Customer {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("contact")
    private String contact;
    @SerializedName("gender")
    private String gender;
    @SerializedName("dob")
    private String dob;
    @SerializedName("password")
    private String password;
    @SerializedName("role")
    private String role;
    @SerializedName("cart_product")
    List<Product> cart_product;
    @SerializedName("order_product")
    List<Product> order_product;

    public Customer(int id, String name, String email, String contact, String gender, String dob, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.gender = gender;
        this.dob = dob;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", cart_product=" + cart_product +
                ", order_product=" + order_product +
                '}';
    }
}
