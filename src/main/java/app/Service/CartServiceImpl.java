package app.Service;

import app.Dao.CartDao;
import app.Dao.ItemDao;
import app.models.CartDisplayItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartDao carts;

    @Autowired
    ItemDao items;

    @Override
    public List<CartDisplayItem> getCart(int customerId) {
        return
                carts
                        .getByCustomerId(customerId)
                        .stream()
                        .map(cartItem -> new CartDisplayItem(cartItem, items.getItem(cartItem.getItemID())))
                        .toList();
    }


}
