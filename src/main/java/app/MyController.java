package app;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/admin")
    public String admin(){
        return "admin.html";
    }

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

    @GetMapping("/purhcase-history")
    public String purchasehistory(){
        return "purchase-history.html";
    }

    @GetMapping("/item")
    public String item(){
        return "item.html";
    }

}
