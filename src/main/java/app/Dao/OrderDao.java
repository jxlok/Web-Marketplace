package app.Dao;

import app.Entities.FullOrderInfo;
import app.Entities.Item;
import app.Entities.Order;
import app.Entities.Order_Details;
import app.models.OrderItemIdAndQuantity;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface OrderDao {

    List<Order> getAllOrders();
    List<Order_Details> getOrderDetails(Order order);
    Item getItem(Order_Details order_details);
    double getTotalSales();
    HashMap<String, Integer> getSoldItemCount();
    String getBestSeller();
    List<FullOrderInfo> getFullOrderInfo();
    void updateOrderStatus(int id, String status);
    List<FullOrderInfo> getFullOrderInfoByCustomer(int id);
    List<Order> getAllOrdersOfCustomer(int id);

    int createOrder(int customerId, List<OrderItemIdAndQuantity> idAndQtys);

    int updatePaymentIdByOrderId(int id);
}
