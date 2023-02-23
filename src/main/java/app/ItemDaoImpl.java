package app;

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
            newItem.setTrainedPrice((BigDecimal) item.get("trainedPrice"));
            newItem.setUnTrainedPrice((BigDecimal) item.get("unTrainedPrice"));
            newItem.setTrainedStock((Integer) item.get("trainedStock"));
            newItem.setUnTrainedStock((Integer) item.get("unTrainedStock"));
            newItem.setVisibility((Integer) item.get("visibility"));

            result.add(newItem);
        }
        return result;
    }

    @Override
    public void insertItem(Item item) {
        String sql = "INSERT INTO items " + "(itemId, itemName, description, trainedPrice, unTrainedPrice, trainedStock, unTrainedStock, visibility) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        getJdbcTemplate().update(sql, new Object[]{item.getItemId(), item.getItemName(), item.getDescription(), item.getTrainedPrice(), item.getUnTrainedPrice(), item.getTrainedStock(), item.getUnTrainedStock(), item.getVisibility()});
    }
}
