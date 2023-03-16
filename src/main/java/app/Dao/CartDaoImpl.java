package app.Dao;

import app.Entities.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CartDaoImpl implements CartDao {
    public static final String SELECT_BY_CUSTOMER_ID_QUERY = "SELECT * FROM carts WHERE customerID = ?";
    public static final String UPDATE_QUANTITY_BY_CUSTOMER_ID_AND_CART_ITEM_ID_QUERY = "UPDATE carts SET quantity= ? WHERE customerID = ? and id = ?";
    public static final String DELETE_CART_ITEM_BY_ITEM_ID = "DELETE FROM carts WHERE customerID = ? and id = ?";
    public static final String DELETE_CART_ITEM_BY_CUSTOMER_ID = "DELETE FROM carts WHERE customerID = ?";
    public static final String ADD_ITEM_TO_CART_BY_CUSTOMER_ID_AND_ITEM_ID = "INSERT INTO carts (customerID, itemID, isTrained, quantity) VALUES (?, ?, ?, ?)";
    public static final String ID = "id";
    public static final String CUSTOMER_ID = "customerId";
    public static final String ITEM_ID = "itemId";
    public static final String IS_TRAINED = "isTrained";
    public static final String QUANTITY = "quantity";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CartItem> getByCustomerId(int customerId) {
        var results = jdbcTemplate.query(
                SELECT_BY_CUSTOMER_ID_QUERY,
                (rs, rowNum) -> {
                    var cardItem = new CartItem();
                    cardItem.setId(rs.getInt(ID));
                    cardItem.setCustomerID(rs.getInt(CUSTOMER_ID));
                    cardItem.setItemID(rs.getInt(ITEM_ID));
                    cardItem.setTrained(rs.getBoolean(IS_TRAINED));
                    cardItem.setQuantity(rs.getInt(QUANTITY));
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

    @Override
    public int deleteAllCartItems(int customerId) {

        return jdbcTemplate.update(
                DELETE_CART_ITEM_BY_CUSTOMER_ID,
                customerId
        );
    }

    @Override
    public int addItemToCart(int customerId, int itemId, int isTrained) {
        return jdbcTemplate.update(
                ADD_ITEM_TO_CART_BY_CUSTOMER_ID_AND_ITEM_ID,
                customerId,
                itemId,
                isTrained,
                1
        );
    }
}
