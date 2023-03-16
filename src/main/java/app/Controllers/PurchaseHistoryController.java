package app.Controllers;
import app.Service.CartService;
import app.Service.ItemService;
import app.Service.OrderService;
import app.SessionVariables;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class PurchaseHistoryController {

    @Autowired
    SessionVariables sessionVariables;

    @Autowired
    ItemService itemService;

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @GetMapping("/purchase-history")
    public String purchasehistory(
            @CookieValue(name="customerid", required = false) String customerId,
            Model model,
            HttpServletResponse response){

        if (null != customerId && sessionVariables.isCustomerLoggedIn(Integer.parseInt(customerId))) {
            sessionVariables.setCustomerLoggedIn(true);
            var cartItems = cartService.getCart(Integer.parseInt(customerId));
            sessionVariables.setBasketCount(cartItems.stream().map(ci -> ci.getCartItem().getQuantity()).reduce(0, Integer::sum));

            model.addAttribute("customerLoggedIn", sessionVariables.isCustomerLoggedIn());
            model.addAttribute("adminLoggedIn", sessionVariables.isAdminLoggedIn());
            model.addAttribute("customerOrders", orderService.getFullOrderInfoByCustomer(Integer.parseInt(customerId)));
            model.addAttribute("basketCount", sessionVariables.getBasketCount());

            return "purchase-history.html";
        }
        return "redirect:/login";
    }

}
