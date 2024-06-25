package com.example.pojo;


public class Menu {
    private int seller;
    private String name;
    private int price;

    public int getSeller() {
        return seller;
    }

    public void setSeller(int seller) {
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "seller=" + seller +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
