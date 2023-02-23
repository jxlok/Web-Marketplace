package app;

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
}
