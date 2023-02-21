package app.Controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;

public class LoginController {
    private HashMap<String,String> accounts = new HashMap<>();
    private int count=0;

    @PostMapping("/login")
    public void Registration(@ModelAttribute User user, HttpServletResponse response){
        accounts.put(user.getEmail(),user.getPassword());
    }

    @PostMapping("/login")
    public String Login(@ModelAttribute User use, HttpServletResponse response){
        if(accounts.get(use.getEmail()) == null){
            return "login.html";
        }
        else{
            return "login.html";
        }
    }

}
