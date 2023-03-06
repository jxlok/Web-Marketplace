package app.Dao;

import app.Entities.CartItem;

import java.util.List;
import java.util.Optional;

// Cart represents items in shopping carts
public interface CartDao {

    List<CartItem> getByCustomerId(int customerId);

    int setQuantity(int customerId, int cartItemId, int newQuantity);

    int deleteCartItem(int customerId, int id);

}
