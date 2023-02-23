package app;

import java.util.List;

public interface ItemService {
    List<Item> getAllItems();
    void insertItem(Item item);
}
