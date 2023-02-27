package app.Dao;

import app.Entities.Item;

import java.util.List;

public interface ItemDao {
    List<Item> getAllItems();
    void insertItem(Item item);
    void editItem(int id, Item item);
    void hideItem(int id);
    void unhideItem(int id);
    List<Item> getHiddenItems();
    List<Item> getUnhiddenItems();
}
