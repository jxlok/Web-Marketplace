package app.Controllers;
import app.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes
public class MyController {

    boolean isLoggedIn=false;

    boolean passwordError=false;

    @Autowired
    OrderService orderService;

    @GetMapping("/checkout")
    public String checkout(){
        return "checkout.html";
    }


    @GetMapping("/purchase-history")
    public String purchasehistory(Model model){
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("customerOrders", orderService.getFullOrderInfoByCustomer(200));
        return "purchase-history.html";
    }

    @GetMapping("/item")
    public String item(){
        return "item.html";
    }

}
