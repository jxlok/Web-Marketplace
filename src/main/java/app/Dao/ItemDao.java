package app.Dao;

import app.Entities.Item;

import java.util.List;

public interface ItemDao {
    List<Item> getAllItems();
    void insertItem(Item item);
    void editItem(int id, Item item);
    void updateStock(int id, int delta);
    void hideItem(int id);
    void unhideItem(int id);
    List<Item> getHiddenItems();
    List<Item> getUnhiddenItems();
    List<Item> getSortedUnhiddenItems();
    Item getItem(int id);
    List<Item> getSearchedItems(String search_query);
    Item getOppositeItem(String name, int isTrained);
    String switchCartItemStatus(int cartItemId);
}
