package app.Controllers;
import app.Service.CartService;
import app.Service.ItemService;
import app.Service.OrderService;
import app.SessionVariables;
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

    @Autowired
    SessionVariables sessionVariables;

    @Autowired
    ItemService itemService;

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

//    @GetMapping("/checkout")
//    public String checkout(){
//        return "checkout.html";
//    }


    @GetMapping("/purchase-history")
    public String purchasehistory(Model model, HttpServletResponse response){

        if(!sessionVariables.isCustomerLoggedIn()){

            try {
                response.sendRedirect("/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("customerLoggedIn", sessionVariables.isCustomerLoggedIn());
        model.addAttribute("adminLoggedIn", sessionVariables.isAdminLoggedIn());
        model.addAttribute("customerOrders", orderService.getFullOrderInfoByCustomer(200));

        var cartItems = cartService.getCart(111);
        model.addAttribute("basketCount", cartItems.stream().map(ci -> ci.getCartItem().getQuantity()).reduce(0, Integer::sum));
        return "purchase-history.html";
    }




}
