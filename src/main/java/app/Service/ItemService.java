package app.Service;

import app.Entities.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAllItems();
    void insertItem(Item item);
    void editItem(int id, Item item);
    void hideItem(int id);
    void unhideItem(int id);
    List<Item> getHiddenItems();
    List<Item> getUnhiddenItems();
    List<Item> getSortedUnhiddenItems();
    Item getItem(int id);
    List<Item> getSearchedItems(String search_query);
}
