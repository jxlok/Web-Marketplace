package app.Service;

import app.Dao.ItemDao;
import app.Dao.OrderDao;
import app.Entities.FullOrderInfo;
import app.Entities.Item;
import app.Entities.Order;
import app.Entities.Order_Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderDao orderDao;

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    @Override
    public List<Order_Details> getOrderDetails(Order order) {
        return orderDao.getOrderDetails(order);
    }

    @Override
    public Item getItem(Order_Details order_details){
        return orderDao.getItem(order_details);
    }

    @Override
    public double getTotalSales(){return orderDao.getTotalSales();}

    @Override
    public HashMap<String, Integer> getSoldItemCount(){ return orderDao.getSoldItemCount();}

    @Override
    public String getBestSeller(){ return orderDao.getBestSeller();}

    @Override
    public List<FullOrderInfo> getFullOrderInfo(){
        return orderDao.getFullOrderInfo();
    }

    @Override
    public void updateOrderStatus(int id, String status){ orderDao.updateOrderStatus(id, status); }

    @Override
    public List<FullOrderInfo> getFullOrderInfoByCustomer(int id){ return orderDao.getFullOrderInfoByCustomer(id);}

    @Override
    public List<Order> getAllOrdersOfCustomer(int id) {
        return orderDao.getAllOrdersOfCustomer(id);
    }
}
