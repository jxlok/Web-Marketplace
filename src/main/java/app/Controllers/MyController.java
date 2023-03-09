package app.Controllers;
import app.Service.CartService;
import app.Service.ItemService;
import app.Service.OrderService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class MyController {

    boolean customerLoggedIn=true;
    boolean adminLoggedIn=false;

    @Autowired
    ItemService itemService;

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    boolean search=false;

    @GetMapping("/")
    public String index(RedirectAttributes redirectAttributes, Model model){

        if(search){
            model.addAttribute("searchedItems", redirectAttributes.getFlashAttributes().get("searchedItems"));
            search=false;
        }
        else {
            model.addAttribute("myItems", itemService.getUnhiddenItems());
        }

        return "index.html";
    }

    @PostMapping("/")
    public String search(@RequestParam String search_query, RedirectAttributes redirectAttributes){
        search=true;
        redirectAttributes.addFlashAttribute("searchedItems", itemService.getSearchedItems(search_query));
        return "redirect:/";
    }

    @GetMapping("/checkout")
    public String checkout(){
        return "checkout.html";
    }


    @GetMapping("/purchase-history")
    public String purchasehistory(Model model, HttpServletResponse response){

        if(!customerLoggedIn){

            try {
                response.sendRedirect("/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("customerLoggedIn", customerLoggedIn);
        model.addAttribute("adminLoggedIn", adminLoggedIn);
        model.addAttribute("customerOrders", orderService.getFullOrderInfoByCustomer(200));

        var cartItems = cartService.getCart(111);
        model.addAttribute("basketCount", cartItems.stream().map(ci -> ci.getCartItem().getQuantity()).reduce(0, Integer::sum));
        return "purchase-history.html";
    }

    @GetMapping("/item")
    public String item(){
        return "item.html";
    }


}
