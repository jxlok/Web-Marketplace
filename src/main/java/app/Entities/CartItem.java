package app.Entities;

public class CartItem {
    private int id;
    private int customerID;
    private int itemID;
    private Boolean isTrained;
    private int quantity;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public Boolean getTrained() {
        return isTrained;
    }

    public void setTrained(Boolean trained) {
        isTrained = trained;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
