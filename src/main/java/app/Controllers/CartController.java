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
    final static double TAX_RATE = 0.1d;
    // depends on interface instead of implementation is beneficial:
    // 1. it doesn't depend on concrete implementation, so there is no need to change attribute type when underneath implementation changes.
    // 2. In tests, you can create dummy implementation of dependencies for testing purpose
    @Autowired
    CartService cartService;

    @GetMapping("/cart")
    //collect card information and order information
    public String cartPage(Model model) {
        var cartItems = cartService.getCart(111);
        var totalItemCount = cartItems.stream().map(ci -> ci.getCartItem().getQuantity()).reduce(0, Integer::sum);
        var cartPretaxTotal = cartItems.stream().map(ci -> ci.getCartItem().getQuantity() * ci.getItem().getPrice()).reduce(0d, Double::sum);
        var cartTax = TAX_RATE * cartPretaxTotal;
        var cartTaxedTotal = cartTax + cartPretaxTotal;

        model.addAttribute("cartDisplayItems", cartItems);
        model.addAttribute("cartItemsCount", totalItemCount);
        model.addAttribute("cartPretaxTotal", cartPretaxTotal);
        model.addAttribute("cartTax", cartTax);
        model.addAttribute("cartTaxedTotal", cartTaxedTotal);
        model.addAttribute("taxRate", TAX_RATE);
        model.addAttribute("taxRateInPercentage", TAX_RATE * 100);

        return "cart.html";
    }

    @PatchMapping("/cart/items/{cartItemId}")
    public ResponseEntity<Void> setCartItemQuantity(
            @PathVariable(name = "cartItemId") int cartItemId,
            @RequestBody CartItemQuantityUpdateRequest request) {

        boolean succeeded = cartService.updateCartItemQuantity(111, cartItemId, request.getQuantity());

        return succeeded ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/cart/items/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(
            @PathVariable(name = "cartItemId") int cartItemId) {
        var succeeded = cartService.deleteCartItem(111, cartItemId);

        return succeeded ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
