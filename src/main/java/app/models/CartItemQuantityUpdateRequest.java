package app.models;

// model of request from endpoint PATCH:/cart/items/{cartItemId}
public class CartItemQuantityUpdateRequest {
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
