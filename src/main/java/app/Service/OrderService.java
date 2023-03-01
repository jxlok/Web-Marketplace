package app.Service;

import app.Entities.FullOrderInfo;
import app.Entities.Item;
import app.Entities.Order;
import app.Entities.Order_Details;

import java.util.HashMap;
import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    List<Order_Details> getOrderDetails(Order order);
    Item getItem(Order_Details order_details);
    double getTotalSales();
    HashMap<String, Integer> getSoldItemCount();
    String getBestSeller();
    List<FullOrderInfo> getFullOrderInfo();
    void updateOrderStatus(int id, String status);
}
