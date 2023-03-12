package app.Service;

import app.models.CartDisplayItem;

import java.util.List;

public interface CartService {
    List<CartDisplayItem> getCart(int customerId);
    Boolean updateCartItemQuantity(int customerId, int cartItemId, int newQuantity);

    Boolean deleteCartItem(int customerId, int cartItemId);

    Boolean clearCart(int customerId);
}
