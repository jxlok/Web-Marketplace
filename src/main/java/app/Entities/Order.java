package app.Entities;

import java.util.Optional;

public class Order {

    private int orderID;
    private java.sql.Timestamp OrderPlacedTime;
    private String orderStatus;
    private int paymentID;
    private int customerID;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public java.sql.Timestamp getOrderPlacedTime() {
        return OrderPlacedTime;
    }

    public void setOrderPlacedTime(java.sql.Timestamp orderPlacedTime) {
        OrderPlacedTime = orderPlacedTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}
