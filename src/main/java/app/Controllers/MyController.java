package app.Controllers;
import app.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    boolean customer=false;
    boolean passwordError=false;

    @Autowired
    OrderService orderService;

    @GetMapping("/cart")
    public String cart(){
        return "cart.html";
    }

    @GetMapping("/checkout")
    public String checkout(){
        return "checkout.html";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

    @GetMapping("/purchase-history")
    public String purchasehistory(Model model){

        model.addAttribute("customerOrders", orderService.getFullOrderInfoByCustomer(200));
        return "purchase-history.html";
    }

    @GetMapping("/item")
    public String item(){
        return "item.html";
    }

}
