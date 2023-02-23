package app;

import java.math.BigDecimal;

public class Item {

    private int itemId;
    private String itemName;
    private String description;
    private BigDecimal trainedPrice;
    private BigDecimal unTrainedPrice;
    private int trainedStock;
    private int unTrainedStock;
    private int visibility;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTrainedPrice() {
        return trainedPrice;
    }

    public void setTrainedPrice(BigDecimal trainedPrice) {
        this.trainedPrice = trainedPrice;
    }

    public BigDecimal getUnTrainedPrice() {
        return unTrainedPrice;
    }

    public void setUnTrainedPrice(BigDecimal unTrainedPrice) {
        this.unTrainedPrice = unTrainedPrice;
    }

    public int getTrainedStock() {
        return trainedStock;
    }

    public void setTrainedStock(int trainedStock) {
        this.trainedStock = trainedStock;
    }

    public int getUnTrainedStock() {
        return unTrainedStock;
    }

    public void setUnTrainedStock(int unTrainedStock) {
        this.unTrainedStock = unTrainedStock;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }
}
