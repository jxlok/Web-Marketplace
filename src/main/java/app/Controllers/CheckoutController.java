package app.Controllers;

import app.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class CheckoutController {

    @Autowired
    OrderService orderService;

    @GetMapping("/checkout")
    //collect card information and order information
    public String checkout(
            Model model,
            @RequestParam(name = "orderId") Optional<Integer> orderId) {
        if (orderId.isPresent()) {
            return "checkout.html";
        } else {
            model.addAttribute("redirectReason", "Sorry, you don't have an valid order id.");
            return "redirect.html";
        }
    }
    @PostMapping("/checkout")
    public String completeOrder(
            Model model,
            @RequestParam(name = "orderId") Optional<Integer> orderId) {
        if (orderId.isPresent()) {
            orderService.completeOrderById(orderId.get());
            model.addAttribute("redirectReason", "Congratulations, your payment went through!");
        } else {
            model.addAttribute("redirectReason", "Sorry, you don't have an valid order id.");
        }
        return "redirect.html";
    }
}
