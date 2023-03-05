package app.Controllers;

import app.Entities.Item;
import app.Service.ItemService;
import app.Service.OrderService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class AdminController {

    @Autowired
    ItemService itemService;

    @Autowired
    OrderService orderService;

    @GetMapping("/manageItems")
    public String manageItems(Model model){
        model.addAttribute("myItems", itemService.getUnhiddenItems());
        return "manageItems.html";
    }

    @PostMapping("/manageItems")
    public @ResponseBody
    void addItem(Item item, HttpServletResponse response) {

        item.setIsTrained(1);

        //find item
        Item existingItem = null;
        for(Item myItem: itemService.getAllItems()) {
            if(myItem.getItemName().equals(item.getItemName())){
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
    public String admin(Model model){
        //total-sales statistic
        model.addAttribute("total_sales", orderService.getTotalSales());

        model.addAttribute("best_seller", orderService.getBestSeller());

        model.addAttribute("sold_count", orderService.getSoldItemCount());

        model.addAttribute("orders", orderService.getFullOrderInfo());

        model.addAttribute("sorted_stock_list", itemService.getSortedUnhiddenItems());
        return "admin.html";
    }

    @GetMapping("/admin/{status}/{id}")
    public void changeStatus(@PathVariable String status, @PathVariable int id, HttpServletResponse response){

        orderService.updateOrderStatus(id, status);

        try {
            response.sendRedirect("/admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
