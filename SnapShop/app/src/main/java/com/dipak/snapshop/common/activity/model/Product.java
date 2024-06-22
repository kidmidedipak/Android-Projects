package com.dipak.snapshop.common.activity.model;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("brand")
    String brand;
    @SerializedName("imgname")
    String imgname;
    @SerializedName("quantity")
    int quantity;
    @SerializedName("category")
    private Category category;
    @SerializedName("price")
    int price;
    @SerializedName("status")
    String status;

    public Product(int id, String name, String description, String brand, String imgname,
                   int quantity, Category category, int price, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.imgname = imgname;
        this.quantity = quantity;
        this.category = category;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public String getImgname() {
        return imgname;
    }

    public int getQuantity() {
        return quantity;
    }

    public Category getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", imgname='" + imgname + '\'' +
                ", quantity=" + quantity +
                ", category=" + category +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
