package app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {

    @PostMapping("/cart")
    //collect card information and order information
    public void checkout() {

    }

}
