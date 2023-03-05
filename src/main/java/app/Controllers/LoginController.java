package app.Controllers;

import app.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;

@Controller
public class LoginController {
    private HashMap<String,String> accounts = new HashMap<>();
    private int count=0;

    @PostMapping("/login")
    public void registration(@ModelAttribute User user, HttpServletResponse response){
        accounts.put(user.getEmail(),user.getPassword());
    }

    @GetMapping("/login")
    public String login(@ModelAttribute User use, HttpServletResponse response){
        if(accounts.get(use.getEmail()) == null){
            return "login.html";
        }
        else{
            return "login.html";
        }
    }

}
