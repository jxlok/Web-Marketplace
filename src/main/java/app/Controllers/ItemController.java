package app.Controllers;

import app.Entities.Item;
import app.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ItemController {

    @Autowired
    ItemService itemService;


    @GetMapping("/item")
    public String item(@RequestParam("id") int id, Model model) {
        Item item = itemService.getItem(id);

        model.addAttribute("item", item);
        return "item.html";
    }



}