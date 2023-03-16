package app.Service;

import app.Dao.ItemDao;
import app.Entities.Item;
import org.hibernate.annotations.DialectOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    ItemDao itemDao;

    @Override
    public List<Item> getAllItems() {
        return itemDao.getAllItems();
    }

    @Override
    public void insertItem(Item item) {
        itemDao.insertItem(item);
    }

    @Override
    public void editItem(int id, Item item){
        itemDao.editItem(id, item);
    }

    @Override
    public void updateStock(int id, int delta) {
        itemDao.updateStock(id, delta);
    }

    @Override
    public void hideItem(int id){
        itemDao.hideItem(id);
    }

    @Override
    public void unhideItem(int id){
        itemDao.unhideItem(id);
    }

    @Override
    public List<Item> getHiddenItems(){
        return itemDao.getHiddenItems();
    }

    @Override
    public List<Item> getUnhiddenItems(){
        return itemDao.getUnhiddenItems();
    }

    @Override
    public List<Item> getSortedUnhiddenItems(){
        return itemDao.getSortedUnhiddenItems();
    }

    @Override
    public Item getItem(int id){
        return itemDao.getItem(id);
    }

    @Override
    public List<Item> getSearchedItems(String search_query){ return itemDao.getSearchedItems(search_query);
    }

    @Override
    public void switchCartItemStatus(int cartItemId){
        itemDao.switchCartItemStatus(cartItemId);
    }
}
