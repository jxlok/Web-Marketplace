package app.Controllers;

import app.Service.CartService;
import app.Service.OrderService;
import app.models.OrderItemIdAndQuantity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    CartService cartService;
    @PostMapping("/orders")
    @ResponseBody
    public String createNewOrder() {
        var idAndQuantityStream = cartService.getCart(111)
                .stream()
                .map(item ->
                        new OrderItemIdAndQuantity(item.getItem().getItemId(), item.getCartItem().getQuantity())
                ).toList();

        return String.valueOf(orderService.createOrder(111, idAndQuantityStream));
    }
}
