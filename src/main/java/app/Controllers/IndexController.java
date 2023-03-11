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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private SessionVariables sessionVariables;

    @Autowired
    ItemService itemService;

    @Autowired
    CartService cartService;

    List<Item> searchedItems = new LinkedList<>();
    @GetMapping("/")
    public String index(Model model){

        if(sessionVariables.isSearching()){
            model.addAttribute("myItems", searchedItems);
        }
        else {
            model.addAttribute("myItems", itemService.getUnhiddenItems());
        }

        model.addAttribute("customerLoggedIn", sessionVariables.isCustomerLoggedIn());
        model.addAttribute("adminLoggedIn", sessionVariables.isAdminLoggedIn());
        model.addAttribute("searching", sessionVariables.isSearching());
        var cartItems = cartService.getCart(111);
        model.addAttribute("basketCount", cartItems.stream().map(ci -> ci.getCartItem().getQuantity()).reduce(0, Integer::sum));        sessionVariables.setSearching(false);
        return "index.html";
    }

    @PostMapping("/")
    public String search(@RequestParam String search_query){
        sessionVariables.setSearching(true);
        searchedItems = itemService.getSearchedItems(search_query);
        return "redirect:/";
    }


}