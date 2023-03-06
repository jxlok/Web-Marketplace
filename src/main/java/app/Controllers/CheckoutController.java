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
            return "redirect:/";
        }
    }
}
