package app.Controllers;

import app.Service.CartService;
import app.Service.OrderService;
import app.models.CartDisplayItem;
import app.models.OrderItemIdAndQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CheckoutController {

    public static final String CUSTOMER_ID = "customerId";
    @Autowired
    OrderService orderService;
    @Autowired
    CartService cartService;

    @GetMapping("/checkout")
    //collect card information and order information
    public String checkout(
            @RequestParam(name = CUSTOMER_ID) Optional<Integer> customerId) {
        if (customerId.isPresent()) {
            return "checkout.html";
        } else {
            return "redirect:/login";
        }
    }
    @PostMapping("/checkout")
    public String completeOrder(
            Model model,
            @RequestParam(name = CUSTOMER_ID) Optional<Integer> customerId) {
        if (customerId.isPresent()) {
            System.out.println("there");
            orderService.createOrder(customerId.get(), getItemIdAndQuantities(cartService.getCart(customerId.get())));
            cartService.clearCart(customerId.get());
            model.addAttribute("redirectReason", "Congratulations, your payment went through!");
            return "redirect.html";
        } else {
            System.out.println("here");
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
}
