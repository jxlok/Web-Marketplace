package app.Dao;

import app.Entities.CartItem;
import app.Entities.Item;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ItemDaoImpl extends JdbcDaoSupport implements ItemDao {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialise(){
        setDataSource(dataSource);
    }

    @Autowired
    CartDao cartDao;

    @Override
    public List<Item> getAllItems() {
        String sql = "SELECT * FROM items";
        List<Map<String, Object>> items = getJdbcTemplate().queryForList(sql);

        List<Item> result = new ArrayList<>();
        for(Map<String, Object> item :  items){
            Item newItem = new Item();
            newItem.setItemId((Integer) item.get("itemId"));
            newItem.setItemName((String) item.get("itemName"));
            newItem.setDescription((String) item.get("description"));
            newItem.setIsTrained((int) item.get("isTrained"));
            newItem.setPrice(((BigDecimal) item.get("price")).doubleValue());
            newItem.setStock((Integer) item.get("stock"));
            newItem.setVisibility((Integer) item.get("visibility"));

            result.add(newItem);
        }
        return result;
    }

    @Override
    public Item getItem(int id){
        String sql = "SELECT * FROM items WHERE itemId="+id;
        List<Map<String, Object>> items = getJdbcTemplate().queryForList(sql);

        Item item = new Item();
        item.setItemId((Integer) items.get(0).get("itemId"));
        item.setItemName((String) items.get(0).get("itemName"));
        item.setDescription((String) items.get(0).get("description"));
        item.setIsTrained((int) items.get(0).get("isTrained"));
        item.setPrice(((BigDecimal) items.get(0).get("price")).doubleValue());
        item.setStock((Integer) items.get(0).get("stock"));
        item.setVisibility((Integer) items.get(0).get("visibility"));

        return item;

    }
    @Override
    public void insertItem(Item item) {
        String sql = "INSERT INTO items " + "(itemId, itemName, description, isTrained, price, stock, visibility) VALUES (?, ?, ?, ?, ?, ?, ?)";
        getJdbcTemplate().update(sql, new Object[]{item.getItemId(), item.getItemName(), item.getDescription(), item.getIsTrained(), item.getPrice(), item.getStock(), item.getVisibility()});
    }

    @Override
    public void editItem(int id, Item item){
        String sql = "UPDATE items SET itemName=?, description=?, isTrained=?, price=?, stock=? WHERE itemId = ?";
        getJdbcTemplate().update(sql, new Object[]{item.getItemName(), item.getDescription(), item.getIsTrained(), item.getPrice(), item.getStock(), id});
    }

    @Override
    public void updateStock(int id, int delta){
        String sql = "UPDATE items SET stock = stock + ? WHERE itemId = ?";
        getJdbcTemplate().update(sql, delta, id);
    }

    @Override
    public void hideItem(int id){
        String sql = "UPDATE items SET visibility=0 WHERE itemId = ?";
        getJdbcTemplate().update(sql, new Object[]{id});
    }

    @Override
    public void unhideItem(int id){
        String sql = "UPDATE items SET visibility=1 WHERE itemId = ?";
        getJdbcTemplate().update(sql, new Object[]{id});
    }

    @Override
    public List<Item> getHiddenItems(){
        String sql = "SELECT * FROM items WHERE visibility = 0";
        List<Map<String, Object>> items = getJdbcTemplate().queryForList(sql);

        List<Item> result = new ArrayList<>();
        for(Map<String, Object> item :  items){
            Item newItem = new Item();
            newItem.setItemId((Integer) item.get("itemId"));
            newItem.setItemName((String) item.get("itemName"));
            newItem.setDescription((String) item.get("description"));
            newItem.setIsTrained((int) item.get("isTrained"));
            newItem.setPrice(((BigDecimal) item.get("price")).doubleValue());
            newItem.setStock((Integer) item.get("stock"));
            newItem.setVisibility((Integer) item.get("visibility"));

            result.add(newItem);
        }
        return result;
    }


    @Override
    public List<Item> getUnhiddenItems(){
        String sql = "SELECT * FROM items WHERE visibility = 1";
        List<Map<String, Object>> items = getJdbcTemplate().queryForList(sql);

        List<Item> result = new ArrayList<>();
        for(Map<String, Object> item :  items){
            Item newItem = new Item();
            newItem.setItemId((Integer) item.get("itemId"));
            newItem.setItemName((String) item.get("itemName"));
            newItem.setDescription((String) item.get("description"));
            newItem.setIsTrained((int) item.get("isTrained"));
            newItem.setPrice(((BigDecimal) item.get("price")).doubleValue());
            newItem.setStock((Integer) item.get("stock"));
            newItem.setVisibility((Integer) item.get("visibility"));

            result.add(newItem);
        }
        return result;
    }

    public List<Item> getSortedUnhiddenItems(){
        String sql = "SELECT * FROM items WHERE visibility=1 ORDER BY stock";
        List<Map<String, Object>> items = getJdbcTemplate().queryForList(sql);

        List<Item> result = new ArrayList<>();
        for(Map<String, Object> item :  items){
            Item newItem = new Item();
            newItem.setItemId((Integer) item.get("itemId"));
            newItem.setItemName((String) item.get("itemName"));
            newItem.setDescription((String) item.get("description"));
            newItem.setIsTrained((int) item.get("isTrained"));
            newItem.setPrice(((BigDecimal) item.get("price")).doubleValue());
            newItem.setStock((Integer) item.get("stock"));
            newItem.setVisibility((Integer) item.get("visibility"));

            result.add(newItem);
        }
        return result;
    }

    @Override
    public List<Item> getSearchedItems(String search_query){
        String sql = "SELECT * FROM items WHERE visibility=1 and LOWER(itemName) LIKE LOWER('%"+search_query+"%')";
        List<Map<String, Object>> items = getJdbcTemplate().queryForList(sql);

        List<Item> result = new ArrayList<>();
        for(Map<String, Object> item :  items){
            Item newItem = new Item();
            newItem.setItemId((Integer) item.get("itemId"));
            newItem.setItemName((String) item.get("itemName"));
            newItem.setDescription((String) item.get("description"));
            newItem.setIsTrained((int) item.get("isTrained"));
            newItem.setPrice(((BigDecimal) item.get("price")).doubleValue());
            newItem.setStock((Integer) item.get("stock"));
            newItem.setVisibility((Integer) item.get("visibility"));

            result.add(newItem);
        }
        return result;
    }

    @Override
    public Item getOppositeItem(String name, int isTrained){
        String sql = "SELECT * FROM items WHERE itemName ='"+ name+"' and isTrained!="+isTrained;
        List<Map<String, Object>> item = getJdbcTemplate().queryForList(sql);

        if(item.isEmpty()){
            return null;
        }
        else{
            Item newItem = new Item();
            newItem.setItemId((Integer) item.get(0).get("itemId"));
            newItem.setItemName((String) item.get(0).get("itemName"));
            newItem.setDescription((String) item.get(0).get("description"));
            newItem.setIsTrained((int) item.get(0).get("isTrained"));
            newItem.setPrice(((BigDecimal) item.get(0).get("price")).doubleValue());
            newItem.setStock((Integer) item.get(0).get("stock"));
            newItem.setVisibility((Integer) item.get(0).get("visibility"));
            return newItem;
        }
    }

    public CartItem getByCartID(int cartID){
        String sql = "SELECT * FROM carts WHERE id ="+cartID;
        List<Map<String, Object>> item = getJdbcTemplate().queryForList(sql);

        CartItem cartItem = new CartItem();
        cartItem.setId((Integer) item.get(0).get("id"));
        cartItem.setCustomerID((Integer) item.get(0).get("customerID"));
        cartItem.setItemID((Integer) item.get(0).get("itemID"));
        cartItem.setTrained((Integer)item.get(0).get("isTrained")==1 ? Boolean.TRUE : Boolean.FALSE);
        cartItem.setQuantity(((Integer) item.get(0).get("quantity")));
        return cartItem;

    }

    public List<CartItem> getCustomerCart(int customer_id){
        String sql = "SELECT * FROM carts WHERE customerID ="+customer_id;
        List<Map<String, Object>> items = getJdbcTemplate().queryForList(sql);

        List<CartItem> result = new ArrayList<>();
        for(Map<String, Object> item :  items){
            CartItem cartItem = new CartItem();
            cartItem.setId((Integer) item.get("id"));
            cartItem.setCustomerID((Integer) item.get("customerID"));
            cartItem.setItemID((Integer) item.get("itemID"));
            cartItem.setTrained((Integer) item.get("isTrained")==1 ? Boolean.TRUE : Boolean.FALSE);
            cartItem.setQuantity(((Integer) item.get("quantity")));

            result.add(cartItem);
        }
        return result;

    }

    @Override
    public String switchCartItemStatus(int cartItemId){
        //get cardItem
        CartItem cartItem = getByCartID(cartItemId);
        Item myItem = getItem(cartItem.getItemID());
        String name = myItem.getItemName();
        int isTrained = getItem(cartItem.getItemID()).getIsTrained();
        Item opposite_item = getOppositeItem(name, isTrained);

        if(opposite_item==null){
            return "Different version of AI is not available.";
        }

        //customer's cart
        List<CartItem> items = getCustomerCart(cartItem.getCustomerID());

        //check items for match, if match merge
        for (CartItem item: items ) {
            if (item.getItemID() == opposite_item.getItemId()) {
                String sql = "UPDATE carts SET quantity=? WHERE id = ?";
                getJdbcTemplate().update(sql, new Object[]{cartItem.getQuantity() + item.getQuantity(), item.getItemID()});
                cartDao.deleteCartItem(cartItem.getCustomerID(), cartItemId);
                return "SUCCESS";
            }
        }

        //no item match, so ensure change is possible
        if (cartItem.getQuantity() <= opposite_item.getStock() && !opposite_item.equals(new Item())) {
            String sql = "UPDATE carts SET itemID=? WHERE id = ?";
            getJdbcTemplate().update(sql, new Object[]{opposite_item.getItemId(), cartItemId});

            return "SUCCESS";
        }
        else if(cartItem.getQuantity() <= opposite_item.getStock()){
                return "Invalid quantity conversion.";
        }

        return "FAILURE";
    }

}
