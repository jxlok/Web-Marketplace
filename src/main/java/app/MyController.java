package app;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;

@Controller
public class MyController {

    LinkedHashMap<Integer, ItemJ> myItems = new LinkedHashMap<>(){
        {
            put(1, new ItemJ(1, "item1", "description1", 1, 1));
            put(2,  new ItemJ(2, "item2", "description2", 2, 2));
            put(3, new ItemJ(3, "item3", "description3", 3, 3));
            put(4, new ItemJ(4, "item4", "description4", 4, 4));
        }
    };
    private int count=5;

    LinkedHashMap<Integer, ItemJ> hiddenItems = new LinkedHashMap<>();

    @GetMapping("/manageItems")
    public String manageItems(Model model){
        model.addAttribute("myItems", myItems);
        return "manageItems.html";
    }

    @PostMapping("/manageItems")
    public @ResponseBody void addItem(ItemJ item, HttpServletResponse response) {

        //find item
        int id=-1;
        for(ItemJ myItem: myItems.values()) {
            if(myItem.getName().equals(item.getName())){
                id=myItem.getId();
            }
        }

        //replace existing item with updated item details
        if(id!=-1){
            item.setId(id);
            myItems.replace(id, item);
        }
        //else put new item in hashmap
        else {
            item.setId(count);
            myItems.put(count++, item);
        }

        try {
            response.sendRedirect("/manageItems");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/hiddenItems")
    public String hiddenItems(Model model){
        model.addAttribute("hiddenItems", hiddenItems);
        return "hiddenItems.html";
    }

    @GetMapping("/hiddenItems/{flag}/{id}")
    public void hiddenItems(@PathVariable String flag,@PathVariable int id, HttpServletResponse response){

        if(flag.equals("hide")){
            ItemJ temp = myItems.remove(id);
            hiddenItems.put(temp.getId(), temp);
            try {
                response.sendRedirect("/manageItems");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            ItemJ temp = hiddenItems.remove(id);
            myItems.put(temp.getId(), temp);
            try {
                response.sendRedirect("/hiddenItems");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


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
