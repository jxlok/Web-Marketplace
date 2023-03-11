package app.models;

public class OrderItemIdAndQuantity {
    public OrderItemIdAndQuantity(int itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }
    private int itemId;
    private int quantity;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
