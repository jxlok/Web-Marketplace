package app;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;

@Controller
public class MyController {

    HashMap<Integer, ItemJ> myItems = new HashMap<>(){
        {
            put(1, new ItemJ(1, "item1", "description1", 1, 1));
            put(2,  new ItemJ(2, "item2", "description2", 2, 2));
            put(3, new ItemJ(3, "item3", "description3", 3, 3));
            put(4, new ItemJ(4, "item4", "description4", 4, 4));
        }
    };


    @GetMapping("/admin")
    public String admin(Model model){
        model.addAttribute("myItems", myItems);
        return "admin.html";
    }

    @GetMapping("/cart")
    public String cart(){
        return "cart.html";
    }

    @GetMapping("/checkout")
    public String checkout(){
        return "checkout.html";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

    @GetMapping("/purhcase-history")
    public String purchasehistory(){
        return "purchase-history.html";
    }

    @GetMapping("/item")
    public String item(){
        return "item.html";
    }

}
