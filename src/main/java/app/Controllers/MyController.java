package app.Controllers;
import app.Service.CartService;
import app.Service.OrderService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.io.IOException;

@Controller
public class MyController {

    boolean customerLoggedIn=true;
    boolean adminLoggedIn=false;
    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

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
