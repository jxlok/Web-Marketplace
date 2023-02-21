package app;

import java.io.Serializable;
import java.util.List;

public class OrderJ implements Serializable {

    private int order_id;
    private List<ItemJ> items;
    private List<Integer> item_quantities;
    private double total_price;
    private String date;
    private int customer_id;

    public OrderJ(int order_id, List<ItemJ> items, List<Integer> item_quantities, double total_price, String date, int customer_id) {
        this.order_id = order_id;
        this.items = items;
        this.item_quantities = item_quantities;
        this.total_price = total_price;
        this.date = date;
        this.customer_id = customer_id;
    }

    public OrderJ() {
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public List<ItemJ> getItems() {
        return items;
    }

    public void setItems(List<ItemJ> items) {
        this.items = items;
    }

    public List<Integer> getItem_quantities() {
        return item_quantities;
    }

    public void setItem_quantities(List<Integer> item_quantities) {
        this.item_quantities = item_quantities;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
}
