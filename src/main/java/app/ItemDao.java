package app;

import java.util.List;

public interface ItemDao {
    List<Item> getAllItems();
    void insertItem(Item item);
}
