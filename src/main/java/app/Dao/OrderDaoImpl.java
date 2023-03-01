package app.Dao;

import app.Entities.FullOrderInfo;
import app.Entities.Item;
import app.Entities.Order;
import app.Entities.Order_Details;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class OrderDaoImpl extends JdbcDaoSupport implements OrderDao{

    @Autowired
    DataSource dataSource;

    @Autowired
    ItemDao itemDao;

    @PostConstruct
    private void initialise(){
        setDataSource(dataSource);
    }

    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders";
        List<Map<String, Object>> orders = getJdbcTemplate().queryForList(sql);

        List<Order> result = new ArrayList<>();
        for(Map<String, Object> order :  orders){
            Order newOrder = new Order();
            newOrder.setOrderID((Integer) order.get("orderID"));
            newOrder.setOrderPlacedTime((Timestamp) order.get("orderPlacedTime"));
            newOrder.setOrderStatus((String) order.get("orderStatus"));
            newOrder.setPaymentID((Integer) order.get("paymentID"));
            newOrder.setCustomerID((Integer) order.get("customerID"));

            result.add(newOrder);
        }
        return result;
    }

    @Override
    public List<Order_Details> getOrderDetails(Order order){
        String sql = "SELECT * FROM order_details WHERE orderID="+order.getOrderID();
//        getJdbcTemplate().update(sql, new Object[]{order.getOrderID()});

        List<Map<String, Object>> order_details = getJdbcTemplate().queryForList(sql);
        List<Order_Details> result = new ArrayList<>();
        for(Map<String, Object> myOrder :  order_details){
            Order_Details newOrderDetails = new Order_Details();
            newOrderDetails.setId((Integer) myOrder.get("id"));
            newOrderDetails.setOrderID((Integer) myOrder.get("orderID"));
            newOrderDetails.setItemID((Integer) myOrder.get("itemID"));
            newOrderDetails.setQuantity((Integer) myOrder.get("quantity"));

            result.add(newOrderDetails);
        }
        return result;
    }

    @Override
    public Item getItem(Order_Details order_details){
        String sql = "SELECT * FROM items WHERE itemId="+order_details.getItemID();
//        getJdbcTemplate().update(sql, new Object[]{order_details.getItemID()});
        List<Map<String, Object>> items = getJdbcTemplate().queryForList(sql);

        Item newItem = new Item();
        for(Map<String, Object> item :  items){
            newItem.setItemId((Integer) item.get("itemId"));
            newItem.setItemName((String) item.get("itemName"));
            newItem.setDescription((String) item.get("description"));
            newItem.setIsTrained((int) item.get("isTrained"));
            newItem.setPrice(((BigDecimal) item.get("price")).doubleValue());
            newItem.setStock((Integer) item.get("stock"));
            newItem.setVisibility((Integer) item.get("visibility"));

        }
        return newItem;
    }

    @Override
    public double getTotalSales(){
        double totalSales=0;

        for(Order order : getAllOrders()){
            List<Order_Details> details = getOrderDetails(order);
            for(Order_Details detail: details){
                totalSales += getItem(detail).getPrice() * detail.getQuantity();
            }
        }

        return totalSales;
    }

    @Override
    public HashMap<String, Integer> getSoldItemCount() {
        String sql = "SELECT itemName, SUM(quantity) as Count FROM order_details o, items i WHERE o.itemID = i.itemId GROUP BY i.ItemName ORDER BY Count desc";
        List<Map<String, Object>> itemCount = getJdbcTemplate().queryForList(sql);

        HashMap<String, Integer> result = new LinkedHashMap<>();
        for(Map<String, Object> item :  itemCount){
            result.put((String)item.get("itemName"), Integer.valueOf(item.get("Count").toString()));
        }
        return result;
    }

    @Override
    public String getBestSeller(){
        List<String> items = getSoldItemCount().keySet().stream().toList();
        return items.get(0);
    }

    @Override
    public List<FullOrderInfo> getFullOrderInfo(){
        List<Order> orders = getAllOrders();

        List<FullOrderInfo> result = new ArrayList<>();
        for(Order order: orders){
            FullOrderInfo fullOrderInfo = new FullOrderInfo();

            List<Order_Details> ordersDetails = getOrderDetails(order);
            List<Item> items = new ArrayList<>();
            double totalPrice= 0;
            for(Order_Details order_details: ordersDetails){
                Item item = itemDao.getItem(order_details.getItemID());
                items.add(item);
                totalPrice += item.getPrice()*order_details.getQuantity();
            }

            fullOrderInfo.setOrder(order);
            fullOrderInfo.setOrder_details(ordersDetails);
            fullOrderInfo.setItems(items);
            fullOrderInfo.setTotalPrice(totalPrice);

            result.add(fullOrderInfo);
        }

        return result;
    }

    @Override
    public void updateOrderStatus(int id, String status){
        String sql = "UPDATE orders SET orderStatus=? WHERE orderID = ?";
        getJdbcTemplate().update(sql, new Object[]{status, id});
    }


}
