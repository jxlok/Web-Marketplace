package app.models;

import app.Entities.CartItem;
import app.Entities.Item;
//the information are needed by cart page come from both item and cartItem as an intermediate data
public class CartDisplayItem {
    private CartItem cartItem;

    private Item item;
    public CartDisplayItem(CartItem cartItem, Item item) {
        this.cartItem = cartItem;
        this.item = item;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
