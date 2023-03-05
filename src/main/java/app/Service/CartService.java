package app.Service;

import app.models.CartDisplayItem;

import java.util.List;

public interface CartService {
    List<CartDisplayItem> getCart(int customerId);

}
