package app.Controllers;

import app.Entities.Item;
import app.Service.AdminService;
import app.Service.CartService;
import app.Service.ItemService;
import app.Service.OrderService;
import app.SessionVariables;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class AdminController {

    @Autowired
    SessionVariables sessionVariables;

    @Autowired
    ItemService itemService;

    @Autowired
    OrderService orderService;

    @Autowired
    AdminService adminService;

    @Autowired
    CartService cartService;

    boolean passwordError=false;

    @GetMapping("/manageItems")
    public String manageItems(Model model){
        model.addAttribute("myItems", itemService.getUnhiddenItems());
        model.addAttribute("customerLoggedIn", sessionVariables.isCustomerLoggedIn());
        model.addAttribute("adminLoggedIn", sessionVariables.isAdminLoggedIn());
        var cartItems = cartService.getCart(111);
        model.addAttribute("basketCount", cartItems.stream().map(ci -> ci.getCartItem().getQuantity()).reduce(0, Integer::sum));
        return "manageItems.html";
    }

    @PostMapping("/manageItems")
    public @ResponseBody
    void addItem(Item item, @RequestParam String itemType, HttpServletResponse response) {

        if(itemType.equals("Trained"))
        {
            item.setIsTrained(1);
        }
        else{
            item.setIsTrained(0);
        }

        //find item
        Item existingItem = null;
        for(Item myItem: itemService.getAllItems()) {
            if(myItem.getItemName().equals(item.getItemName()) && myItem.getIsTrained() == item.getIsTrained()){
                existingItem=myItem;
            }
        }

        //replace existing item with updated item details
        if(existingItem!=null){
            itemService.editItem(existingItem.getItemId(), item);
        }
        //else put new item in hashmap
        else {
            item.setVisibility(1);

            itemService.insertItem(item);
        }

        try {
            response.sendRedirect("/manageItems");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/hiddenItems")
    public String hiddenItems(Model model){
        model.addAttribute("hiddenItems", itemService.getHiddenItems());
        model.addAttribute("customerLoggedIn", sessionVariables.isCustomerLoggedIn());
        model.addAttribute("adminLoggedIn", sessionVariables.isAdminLoggedIn());
        var cartItems = cartService.getCart(111);
        model.addAttribute("basketCount", cartItems.stream().map(ci -> ci.getCartItem().getQuantity()).reduce(0, Integer::sum));
        return "hiddenItems.html";
    }

    @GetMapping("/hiddenItems/{flag}/{id}")
    public void hiddenItems(@PathVariable String flag, @PathVariable int id, HttpServletResponse response){

        if(flag.equals("hide")){
            itemService.hideItem(id);
            try {
                response.sendRedirect("/manageItems");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            itemService.unhideItem(id);
            try {
                response.sendRedirect("/hiddenItems");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/hiddenItems")
    public @ResponseBody void editHiddenItem(Item item, HttpServletResponse response) {
        //find item
        int id = 0;
        for(Item myItem: itemService.getAllItems()) {
            if(myItem.getItemName().equals(item.getItemName())){
                id=myItem.getItemId();
            }
        }

        //replace existing item with updated item details
        itemService.editItem(id, item);

        try {
            response.sendRedirect("/hiddenItems");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @GetMapping("/admin")
    public String admin(Model model, HttpServletResponse response){

        if(!sessionVariables.isAdminLoggedIn()){
            try {
                response.sendRedirect("/admin-login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //total-sales statistic
        model.addAttribute("total_sales", orderService.getTotalSales());
        model.addAttribute("best_seller", orderService.getBestSeller());
        model.addAttribute("sold_count", orderService.getSoldItemCount());
        model.addAttribute("sorted_stock_list", itemService.getSortedUnhiddenItems());

        //order history
        model.addAttribute("orders", orderService.getFullOrderInfo());
        model.addAttribute("customerLoggedIn", sessionVariables.isCustomerLoggedIn());
        model.addAttribute("adminLoggedIn", sessionVariables.isAdminLoggedIn());

        var cartItems = cartService.getCart(111);
        model.addAttribute("basketCount", cartItems.stream().map(ci -> ci.getCartItem().getQuantity()).reduce(0, Integer::sum));

        return "admin.html";
    }

    @GetMapping("/admin/orders/{id}/set/{status}")
    public void changeStatus(@PathVariable String status, @PathVariable int id, HttpServletResponse response){

        orderService.updateOrderStatus(id, status);

        try {
            response.sendRedirect("/admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/admin-login")
    public String adminLogin(Model model){

        model.addAttribute("failedAttempt", passwordError);
        model.addAttribute("customerLoggedIn", sessionVariables.isCustomerLoggedIn());
        model.addAttribute("adminLoggedIn", sessionVariables.isAdminLoggedIn());
        var cartItems = cartService.getCart(111);
        model.addAttribute("basketCount", cartItems.stream().map(ci -> ci.getCartItem().getQuantity()).reduce(0, Integer::sum));

        return "admin-login.html";
    }

    @PostMapping("/admin-login")
    public void validateAdminLogin(
            @RequestParam(value = "admin-email", required = true) String email,
            @RequestParam(value = "admin-password", required = true) String password,
            HttpServletResponse response
    ){

        if(adminService.validateAdmin(email, password)){
            passwordError=false;
            sessionVariables.setAdminLoggedIn(true);
            try {
                response.sendRedirect("/admin");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            passwordError=true;
            try {
                response.sendRedirect("/admin-login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response){
        sessionVariables.setCustomerLoggedIn(false);
        sessionVariables.setAdminLoggedIn(false);

        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
