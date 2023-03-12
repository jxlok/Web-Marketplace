package app.Service;

import app.Dao.CartDao;
import app.Dao.ItemDao;
import app.models.CartDisplayItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartDao carts;

    @Autowired
    ItemDao items;

    @Override
    public List<CartDisplayItem> getCart(int customerId) {
        return carts
                .getByCustomerId(customerId)
                .stream()
                .map(cartItem -> new CartDisplayItem(cartItem, items.getItem(cartItem.getItemID())))
                .toList();
    }

    public Boolean updateCartItemQuantity(int customerId, int cartItemId, int newQuantity) {
        var rowAffected = carts.setQuantity(customerId, cartItemId, newQuantity);
        return rowAffected > 0;
    }

    public Boolean deleteCartItem(int customerId, int cartItemId) {
        var rowAffected = carts.deleteCartItem(customerId, cartItemId);
        return rowAffected > 0;
    }

    public Boolean clearCart(int customerId) {
        var rowAffected = carts.deleteAllCartItems(customerId);
        return rowAffected > 0;
    }
}
