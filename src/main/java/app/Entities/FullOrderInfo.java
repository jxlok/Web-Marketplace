package app.Entities;

import java.math.BigDecimal;
import java.util.List;

public class FullOrderInfo {

    private Order order;
    private List<Order_Details> order_details;
    private List<Item> items;
    private double totalPrice;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Order_Details> getOrder_details() {
        return order_details;
    }

    public void setOrder_details(List<Order_Details> order_details) {
        this.order_details = order_details;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
