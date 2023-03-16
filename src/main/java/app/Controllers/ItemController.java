package app.Controllers;

import app.Entities.Item;
import app.Service.CartService;
import app.Service.ItemService;
import app.SessionVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


@Controller
public class ItemController {

    @Autowired
    private SessionVariables sessionVariables;

    @Autowired
    ItemService itemService;
    @Autowired
    CartService cartService;
    @Autowired
    HashMap<Integer,Integer> imageID;

    List<Item> searchedItems = new LinkedList<>();
    @GetMapping("/item")
    public String item(@RequestParam("id") int id, Model model) {

        if(sessionVariables.isSearching()){
            model.addAttribute("myItems", searchedItems);
        }
        else {
            model.addAttribute("myItems", itemService.getUnhiddenItems());
        }

        model.addAttribute("customerLoggedIn", sessionVariables.isCustomerLoggedIn());
        model.addAttribute("adminLoggedIn", sessionVariables.isAdminLoggedIn());
        model.addAttribute("searching", sessionVariables.isSearching());
        model.addAttribute("basketCount", sessionVariables.getBasketCount());

        Item item = itemService.getItem(id);

        if(item.getStock()==0){
            model.addAttribute("stock",0);
        }else{
            model.addAttribute("stock",item.getStock());
        }

        String itemName = item.getItemName();
        boolean anotherVersion = false;
        int itemIsTrained = item.getIsTrained();
        for (Item otherItem : itemService.getUnhiddenItems()) {
            if (otherItem.getItemName().equals(itemName) && otherItem.getIsTrained() != itemIsTrained) {
                anotherVersion=true;

                model.addAttribute("anotherID", otherItem.getItemId());
            }
        }
        model.addAttribute("anotherVersion", anotherVersion);

        if(item.getIsTrained()==1){
            String buttonStatus = "Trained";
            model.addAttribute("buttonStatus", buttonStatus);



        }else {
            String buttonStatus = "unTrained";
            model.addAttribute("buttonStatus", buttonStatus);
        }
        model.addAttribute("imageID", imageID);
        model.addAttribute("item", item);
        return "item.html";
    }

    @PostMapping("/item")
    public String search(@RequestParam String search_query){
        sessionVariables.setSearching(true);
        searchedItems = itemService.getSearchedItems(search_query);
        return "redirect:/item";
    }



}