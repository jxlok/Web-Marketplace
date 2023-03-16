package app.Controllers;

import app.Service.CustomerService;
import app.SessionVariables;
import app.models.LoginRequest;
import app.models.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
public class LoginController {
    @Autowired
    CustomerService customerService;

    @Autowired
    SessionVariables sessionVariables;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody LoginRequest request,
            @RequestParam Optional<String> redirect
    ) {
        var customer = customerService.authenticate(request.getEmail(), request.getPassword());
        if (customer.isPresent()) {
            // return token
            var token = customerService.createToken(customer.get());
            var url = redirect.orElse("/");
            // update login info in the session
            sessionVariables.addCustomerLogin(customer.get().getCustomerID(), token);
            sessionVariables.setCustomerLoggedIn(true);
            return new ResponseEntity<>(
                    new LoginResponse(customer.get().getCustomerID(), token, url),
                    HttpStatusCode.valueOf(200)
            );
        }

        return new ResponseEntity<>(HttpStatusCode.valueOf(401));
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("customerLoggedIn", sessionVariables.isCustomerLoggedIn());
        model.addAttribute("adminLoggedIn", sessionVariables.isAdminLoggedIn());
        model.addAttribute("basketCount", sessionVariables.getBasketCount());
        return "login.html";
    }

    // after successful registration, the customer will automatically login.
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody LoginRequest request) {
        customerService.create(request.getEmail(), request.getPassword());
        return this.authenticate(request, Optional.of("/"));
    }

    @GetMapping("/customer-logout")
    public String login(
            @RequestParam Optional<Integer> customerId
    ) {
        if (customerId.isPresent()) {
            sessionVariables.removeCustomerLogin(customerId.get());
            sessionVariables.setCustomerLoggedIn(sessionVariables.isCustomerLoggedIn(customerId.get()));
        }
        return "redirect:/";
    }
}
