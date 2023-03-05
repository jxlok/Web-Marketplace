package app.Dao;

import app.Entities.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CartDaoImpl implements CartDao {
    final static String SELECT_BY_CUSTOMER_ID_QUERY = "SELECT * FROM carts WHERE customerID = ?";
    final static String UPDATE_QUANTITY_BY_CUSTOMER_ID_AND_CART_ITEM_ID_QUERY = "UPDATE carts SET quantity= ? WHERE customerID = ? and id = ?";
    final static String DELETE_CART_ITEM_BY_ITEM_ID = "DELETE FROM carts WHERE customerID = ? and id = ?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CartItem> getByCustomerId(int customerId) {
        var results = jdbcTemplate.query(
                SELECT_BY_CUSTOMER_ID_QUERY,
                (rs, rowNum) -> {
                    var cardItem = new CartItem();
                    cardItem.setId(rs.getInt("id"));
                    cardItem.setCustomerID(rs.getInt("customerId"));
                    cardItem.setItemID(rs.getInt("itemId"));
                    cardItem.setTrained(rs.getBoolean("isTrained"));
                    cardItem.setQuantity(rs.getInt("quantity"));
                    return cardItem;
                },
                customerId
        );
        return results;
    }

    public int setQuantity(int customerId, int cartItemId, int newQuantity) {
        return jdbcTemplate.update(
                UPDATE_QUANTITY_BY_CUSTOMER_ID_AND_CART_ITEM_ID_QUERY,
                newQuantity,
                customerId,
                cartItemId
        );
    }

    public int deleteCartItem(int customerId, int id) {

        return jdbcTemplate.update(
                DELETE_CART_ITEM_BY_ITEM_ID,
                customerId,
                id
        );
    }
}
