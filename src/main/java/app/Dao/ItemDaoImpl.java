package app.Dao;

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
            newItem.setPrice((BigDecimal) item.get("price"));
            newItem.setStock((Integer) item.get("stock"));
            newItem.setVisibility((Integer) item.get("visibility"));

            result.add(newItem);
        }
        return result;
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
            newItem.setPrice((BigDecimal) item.get("price"));
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
            newItem.setPrice((BigDecimal) item.get("price"));
            newItem.setStock((Integer) item.get("stock"));
            newItem.setVisibility((Integer) item.get("visibility"));

            result.add(newItem);
        }
        return result;
    }
}
