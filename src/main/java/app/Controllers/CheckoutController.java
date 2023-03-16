package app.Controllers;

import app.Service.CartService;
import app.Service.ItemService;
import app.Service.OrderService;
import app.SessionVariables;
import app.models.CartDisplayItem;
import app.models.OrderItemIdAndQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static app.Controllers.CartController.CUSTOMER_ID_HEADER;
import static app.Controllers.CartController.TOKEN_HEADER;

@Controller
public class CheckoutController {

    public static final String CUSTOMER_ID = "customerId";
    @Autowired
    OrderService orderService;
    @Autowired
    CartService cartService;

    @Autowired
    ItemService itemService;

    @Autowired
    SessionVariables sessionVariables;

    @GetMapping("/checkout")
    //collect card information and order information
    public String checkout(
            @RequestHeader Map<String, String> headers,
            @RequestParam(name = CUSTOMER_ID) Optional<Integer> customerId,
            Model model
    ) {
        if (customerId.isPresent()) {
            model.addAttribute("customerLoggedIn", sessionVariables.isCustomerLoggedIn());
            model.addAttribute("adminLoggedIn", sessionVariables.isAdminLoggedIn());
            model.addAttribute("basketCount", sessionVariables.getBasketCount());
            return "checkout.html";
        } else {
            return "redirect:/login";
        }
    }
    @PostMapping("/checkout")
    public String completeOrder(
            Model model,
            @RequestHeader Map<String, String> headers,
            @RequestParam(name = CUSTOMER_ID) Optional<Integer> customerId) {
        if (isCheckoutRequestValid(headers, customerId)) {
            List<OrderItemIdAndQuantity> itemIdsAndQuantities = getItemIdAndQuantities(cartService.getCart(customerId.get()));
            orderService.createOrder(customerId.get(),itemIdsAndQuantities);
            // deduct stock
            var itemIdsAndQuantitiesAfterDeduction =
                    itemIdsAndQuantities.stream()
                            .map(idQty ->
                                    new OrderItemIdAndQuantity(
                                            idQty.getItemId(),
                                            itemService.getItem(idQty.getItemId()).getStock() - idQty.getQuantity())
                            ).toList();
            if (itemIdsAndQuantitiesAfterDeduction.stream().anyMatch(idQty -> idQty.getQuantity() < 0)) {
                // if there is at least one item oversold, fail this checkout, show error and redirect customer to index page.
                model.addAttribute("redirectReason", "Checkout Failed! Not enough items in stock, please reduce amount of AI models in your cart!");
                model.addAttribute("customerLoggedIn", sessionVariables.isCustomerLoggedIn(customerId.get()));
                model.addAttribute("adminLoggedIn", sessionVariables.isAdminLoggedIn());
                model.addAttribute("basketCount", sessionVariables.getBasketCount());
                return "redirect.html";
            } else {
                // deduct stock
                itemIdsAndQuantitiesAfterDeduction.forEach(idQty -> itemService.updateStock(idQty.getItemId(), idQty.getQuantity()));
                // clear items from cart
                cartService.clearCart(customerId.get());
                model.addAttribute("redirectReason", "Congratulations, your payment went through!");
                model.addAttribute("customerLoggedIn", sessionVariables.isCustomerLoggedIn(customerId.get()));
                model.addAttribute("adminLoggedIn", sessionVariables.isAdminLoggedIn());
                model.addAttribute("basketCount", sessionVariables.getBasketCount());
                return "redirect.html";
            }
        } else {
            return "redirect:/login";
        }
    }
    private List<OrderItemIdAndQuantity> getItemIdAndQuantities(List<CartDisplayItem> displayItems) {
        return displayItems
                .stream()
                .map(item ->
                        new OrderItemIdAndQuantity(item.getItem().getItemId(), item.getCartItem().getQuantity())
                ).toList();
    }

    private boolean isCheckoutRequestValid(Map<String, String> headers, Optional<Integer> customerId) {
        return customerId.isPresent()
                && isLoginValid(headers)
                && customerId.get() == Integer.parseInt(headers.get(CUSTOMER_ID_HEADER));
    }
    private boolean isLoginValid(Map<String, String> headers) {

        return headers.containsKey(CUSTOMER_ID_HEADER) && headers.containsKey(TOKEN_HEADER) &&
                sessionVariables.isCustomerLoggedIn(Integer.parseInt(headers.get(CUSTOMER_ID_HEADER)));
    }
}
