package com.example.demoproject;

public class Model {
    String name,price;
    int img;

    public Model() {
    }

    public Model(String name, int img,String price) {
        this.name = name;
        this.img = img;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
