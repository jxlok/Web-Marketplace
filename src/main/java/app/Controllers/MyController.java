package app.Controllers;
import app.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @Autowired
    OrderService orderService;

    @GetMapping("/checkout")
    public String checkout(){
        return "checkout.html";
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
