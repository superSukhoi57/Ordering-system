package com.example.pojo;

public class Order {
    private int num;
    private int seller;
    private String name;
    private int customer;
    private int rider;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

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

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public int getRider() {
        return rider;
    }

    public void setRider(int rider) {
        this.rider = rider;
    }

    @Override
    public String toString() {
        return "Order{" +
                "num=" + num +
                ", seller=" + seller +
                ", name='" + name + '\'' +
                ", customer=" + customer +
                ", rider=" + rider +
                '}';
    }
}
