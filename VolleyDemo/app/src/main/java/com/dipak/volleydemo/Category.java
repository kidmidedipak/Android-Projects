package com.dipak.volleydemo;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("id")
    private String id;
    @SerializedName("cname")
    private String cname;
    @SerializedName("description")
    private String description;
    @SerializedName("status")
    private String status;

    public Category(String id, String cname, String description, String status) {
        this.id = id;
        this.cname = cname;
        this.description = description;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getCname() {
        return cname;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", cname='" + cname + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
