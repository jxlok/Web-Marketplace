package app.Controllers;

import app.Service.CartService;
import app.models.CartDisplayItem;
import app.models.CartItemQuantityUpdateRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class CartController {
    // depends on interface instead of implementation is beneficial:
    // 1. it doesn't depend on concrete implementation, so there is no need to change attribute type when underneath implementation changes.
    // 2. In tests, you can create dummy implementation of dependencies for testing purpose
    @Autowired
    CartService cartService;

    @PostMapping("/cart")
    //collect card information and order information

    public String cartPage(Model model) {
        var cartItems = cartService.getCart(111);
        model.addAttribute("cartDisplayItems", cartItems);
        model.addAttribute(
                "cartItemsCount",
                cartItems.stream().map(ci -> ci.getCartItem().getQuantity()).reduce(0, Integer::sum)
        );

        return "cart.html";
    }

}
