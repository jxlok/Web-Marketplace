package app.Controllers;

import app.Service.CartService;
import app.Service.ItemService;
import app.SessionVariables;
import app.models.CartItemQuantityUpdateRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
public class CartController {
    final static double TAX_RATE = 0.1d;
    public static final String CUSTOMER_ID_HEADER = "customerid";
    public static final String TOKEN_HEADER = "token";
    // depends on interface instead of implementation is beneficial:
    // 1. it doesn't depend on concrete implementation, so there is no need to change attribute type when underneath implementation changes.
    // 2. In tests, you can create dummy implementation of dependencies for testing purpose
    @Autowired
    CartService cartService;
    @Autowired
    ItemService itemService;

    @Autowired
    SessionVariables sessionVariables;

    @GetMapping("/cart")
    //collect card information and order information
    public String cartPage(
            @RequestParam Optional<Integer> customerId,
            Model model
    ) {
        if (customerId.isPresent() && sessionVariables.isCustomerLoggedIn(customerId.get())) {
            var cartItems = cartService.getCart(customerId.get());
            var totalItemCount = cartItems.stream().map(ci -> ci.getCartItem().getQuantity()).reduce(0, Integer::sum);
            var cartPretaxTotal = cartItems.stream().map(ci -> ci.getCartItem().getQuantity() * ci.getItem().getPrice()).reduce(0d, Double::sum);
            var cartTax = TAX_RATE * cartPretaxTotal;
            var cartTaxedTotal = cartTax + cartPretaxTotal;

            sessionVariables.setBasketCount(totalItemCount);
            model.addAttribute("customerLoggedIn", sessionVariables.isCustomerLoggedIn(customerId.get()));
            model.addAttribute("adminLoggedIn", sessionVariables.isAdminLoggedIn());
            model.addAttribute("basketCount", sessionVariables.getBasketCount());

            model.addAttribute("cartDisplayItems", cartItems);
            model.addAttribute("cartItemsCount", totalItemCount);
            model.addAttribute("cartPretaxTotal", cartPretaxTotal);
            model.addAttribute("cartTax", cartTax);
            model.addAttribute("cartTaxedTotal", cartTaxedTotal);
            model.addAttribute("taxRate", TAX_RATE);
            model.addAttribute("taxRateInPercentage", TAX_RATE * 100);

            return "cart.html";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/cart/items/{itemId}")
    public ResponseEntity<Void> addItemToCart(
            @RequestHeader Map<String, String> headers,
            @PathVariable int itemId
    ) {
        var customerId = headers.get(CUSTOMER_ID_HEADER);
        if (null != customerId && sessionVariables.isCustomerLoggedIn(Integer.parseInt(customerId))) {
            cartService.addToCart(Integer.parseInt(customerId), itemId);

            var cartItems = cartService.getCart(Integer.parseInt(customerId));
            var totalItemCount = cartItems.stream().map(ci -> ci.getCartItem().getQuantity()).reduce(0, Integer::sum);
            sessionVariables.setBasketCount(totalItemCount);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build(); // Unauthorised
        }
    }

    @PatchMapping("/cart/items/{cartItemId}")
    public ResponseEntity<Void> setCartItemQuantity(
            @RequestHeader Map<String, String> headers,
            @PathVariable(name = "cartItemId") int cartItemId,
            @RequestBody CartItemQuantityUpdateRequest request) {

        boolean succeeded = false;
        if (isLoginValid(headers)) {
            var customerId = Integer.parseInt(headers.get(CUSTOMER_ID_HEADER));
            succeeded = cartService.updateCartItemQuantity(customerId, cartItemId, request.getQuantity());

            var cartItems = cartService.getCart(customerId);
            var totalItemCount = cartItems.stream().map(ci -> ci.getCartItem().getQuantity()).reduce(0, Integer::sum);
            sessionVariables.setBasketCount(totalItemCount);

        }
        return succeeded ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/cart/items/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(
            @RequestHeader Map<String, String> headers,
            @PathVariable(name = "cartItemId") int cartItemId) {
        var succeeded = false;
        if (isLoginValid(headers)) {
            var customerId = Integer.parseInt(headers.get(CUSTOMER_ID_HEADER));
            succeeded = cartService.deleteCartItem(customerId, cartItemId);

            var cartItems = cartService.getCart(customerId);
            var totalItemCount = cartItems.stream().map(ci -> ci.getCartItem().getQuantity()).reduce(0, Integer::sum);
            sessionVariables.setBasketCount(totalItemCount);
        }

        return succeeded ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    private boolean isLoginValid(Map<String, String> headers) {
        return headers.containsKey(CUSTOMER_ID_HEADER) && headers.containsKey(TOKEN_HEADER) &&
                sessionVariables.isCustomerLoggedIn(Integer.parseInt(headers.get(CUSTOMER_ID_HEADER)));
    }

    @GetMapping("/cart/items/{cartItemId}")
    public String switchCartItemStatus(@PathVariable(name="cartItemId") int cartItemId,
                                       HttpServletResponse response){

        itemService.switchCartItemStatus(cartItemId);

        return "redirect:/cart";    }
}
