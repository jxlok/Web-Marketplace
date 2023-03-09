package app.Controllers;

import app.Entities.Item;
import app.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    ItemService itemService;

    @GetMapping("/")
    public String index(Model model){

        List<Item> myItems = itemService.getUnhiddenItems();
        HashMap<String, Item> map = new HashMap<>();
        model.addAttribute("myItems", myItems);




        return "index.html";
    }



}