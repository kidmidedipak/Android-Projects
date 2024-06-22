package com.dipak.volleydemo;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("brand")
    private String brand;
    @SerializedName("imgname")
    private String imgname;
    @SerializedName("quantity")
    private String quantity;
     @SerializedName("category")
     private Category category;
    @SerializedName("price")
     private String price;
    @SerializedName("status")
     private String status;

    public Product(String id, String name, String description, String brand, String imgname, String quantity, Category category, String price, String status) {
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

    public String getId() {
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

    public String getQuantity() {
        return quantity;
    }

    public Category getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", imgname='" + imgname + '\'' +
                ", quantity='" + quantity + '\'' +
                ", category=" + category +
                ", price='" + price + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
