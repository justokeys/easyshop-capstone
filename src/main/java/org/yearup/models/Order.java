//package org.yearup.models;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//
//@Entity
//@Table(name = "orders")
//public class Order {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int orderId;
//    private int userId;
//    private LocalDate date;
//    private String address;
//    private String city;
//    private String state;
//    private double shippingAmount;
//
//    public Order(int orderId, int userId, LocalDate date, String address, String city, String state, double shippingAmount) {
//        this.orderId = orderId;
//        this.userId = userId;
//        this.date = date;
//        this.address = address;
//        this.city = city;
//        this.state = state;
//        this.shippingAmount = shippingAmount;
//    }
//
//    public double getShippingAmount() {
//        return shippingAmount;
//    }
//
//    public void setShippingAmount(double shippingAmount) {
//        this.shippingAmount = shippingAmount;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public LocalDate getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    public int getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(int orderId) {
//        this.orderId = orderId;
//    }
//}
