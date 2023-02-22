package app;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

@Controller
public class MyController {

    //items for sale
    LinkedHashMap<Integer, ItemJ> myItems = new LinkedHashMap<>(){
        {
            put(1, new ItemJ(1, "item1", "description1", 1, 1));
            put(2,  new ItemJ(2, "item2", "description2", 2, 2));
            put(3, new ItemJ(3, "item3", "description3", 3, 3));
            put(4, new ItemJ(4, "item4", "description4", 4, 4));
            put(5, new ItemJ(5, "item5", "description1", 1, 1));
            put(6,  new ItemJ(6, "item6", "description2", 2, 2));
            put(7, new ItemJ(7, "item7", "description3", 3, 3));
            put(8, new ItemJ(8, "item8", "description4", 4, 4));
        }
    };
    private int count=9;

    //hidden items
    LinkedHashMap<Integer, ItemJ> hiddenItems = new LinkedHashMap<>();

    List<ItemJ> setOne = new ArrayList<>(){{
        add(new ItemJ(1, "item1", "description1", 1, 1));
        add(new ItemJ(2, "item2", "description2", 2, 2));
    }};

    List<Integer> quantityOne = new ArrayList<>(){{
        add(1);
        add(2);
    }};

    List<ItemJ> setTwo = new ArrayList<>(){{
        add(new ItemJ(3, "item3", "description3", 3, 3));
    }};

    List<Integer> quantityTwo = new ArrayList<>(){{
        add(1);
    }};

    List<ItemJ> setThree = new ArrayList<>(){{
        add(new ItemJ(4, "item4", "description4", 4, 4));
    }};

    List<Integer> quantityThree = new ArrayList<>(){{
        add(1);
    }};

    LinkedHashMap<Integer, OrderJ> orders = new LinkedHashMap<>(){
        {
            put(1, new OrderJ(1, setOne, quantityOne, "new", 6000, "09/02/2023", 100));
            put(2, new OrderJ(2, setTwo, quantityTwo, "new", 850, "09/02/2023", 100));
            put(3, new OrderJ(3, setThree, quantityThree, "new", 2000, "09/02/2023", 410));
            put(4, new OrderJ(4, setOne, quantityOne, "new", 6000, "09/02/2023", 100));
            put(5, new OrderJ(5, setTwo, quantityTwo, "new", 850, "09/02/2023", 100));
        }
    };

    @GetMapping("/manageItems")
    public String manageItems(Model model){
        model.addAttribute("myItems", myItems);
        return "manageItems.html";
    }

    @PostMapping("/manageItems")
    public @ResponseBody void addItem(ItemJ item, HttpServletResponse response) {

        //find item
        int id=-1;
        for(ItemJ myItem: myItems.values()) {
            if(myItem.getName().equals(item.getName())){
                id=myItem.getId();
            }
        }

        //replace existing item with updated item details
        if(id!=-1){
            item.setId(id);
            myItems.replace(id, item);
        }
        //else put new item in hashmap
        else {
            item.setId(count);
            myItems.put(count++, item);
        }

        try {
            response.sendRedirect("/manageItems");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/hiddenItems")
    public String hiddenItems(Model model){
        model.addAttribute("hiddenItems", hiddenItems);
        return "hiddenItems.html";
    }

    @GetMapping("/hiddenItems/{flag}/{id}")
    public void hiddenItems(@PathVariable String flag,@PathVariable int id, HttpServletResponse response){

        if(flag.equals("hide")){
            ItemJ temp = myItems.remove(id);
            hiddenItems.put(temp.getId(), temp);
            try {
                response.sendRedirect("/manageItems");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            ItemJ temp = hiddenItems.remove(id);
            myItems.put(temp.getId(), temp);
            try {
                response.sendRedirect("/hiddenItems");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/hiddenItems")
    public @ResponseBody void editHiddenItem(ItemJ item, HttpServletResponse response) {

        //find item
        int id=-1;
        for(ItemJ myItem: hiddenItems.values()) {
            if(myItem.getName().equals(item.getName())){
                id=myItem.getId();
            }
        }

        //replace existing item with updated item details
        if(id!=-1){
            item.setId(id);
            hiddenItems.replace(id, item);
        }
        //else put new item in hashmap
        else {
            item.setId(count);
            hiddenItems.put(count++, item);
        }

        try {
            response.sendRedirect("/hiddenItems");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @GetMapping("/admin")
    public String admin(Model model){
        //total-sales statistic
        float total_sales=0;
        for(OrderJ order : orders.values()){
            total_sales+=order.getTotal_price();
        }
        model.addAttribute("total_sales", total_sales);

        //best-selling statistic
        LinkedHashMap<String, Integer> sold_items = new LinkedHashMap<>();
        for(OrderJ order: orders.values()){
            for(int i=0; i<order.getItems().size();i++){
                if(sold_items.containsKey(order.getItems().get(i).getName())) {
                    sold_items.put(order.getItems().get(i).getName(), sold_items.get(order.getItems().get(i).getName())+ order.getItem_quantities().get(i));
                }
                else{
                    sold_items.put(order.getItems().get(i).getName(), order.getItem_quantities().get(i));
                }
            }
        }

        List<Map.Entry<String, Integer>> list = new LinkedList<>(sold_items.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        model.addAttribute("best_seller", list.get(0).getKey());

        model.addAttribute("sold_count", sold_items);

        model.addAttribute("orders", orders);

        //sorted availability
        HashMap<String, Integer> item_availability = new HashMap<>();
        for(ItemJ item : myItems.values()){
            item_availability.put(item.getName(), item.getQuantity());
        }
        List<Map.Entry<String, Integer>> availability_list = new LinkedList<>(item_availability.entrySet());
        availability_list.sort(Map.Entry.comparingByValue());
        model.addAttribute("availability_list", availability_list);
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

    @GetMapping("/purchase-history")
    public String purchasehistory(Model model){

        List<OrderJ> customerOrders = new ArrayList<>();
        for(OrderJ order: orders.values()){
            if(order.getCustomer_id()==100){
                customerOrders.add(order);
            }
        }

        model.addAttribute("customerOrders", customerOrders);
        return "purchase-history.html";
    }

    @GetMapping("/item")
    public String item(){
        return "item.html";
    }

}
