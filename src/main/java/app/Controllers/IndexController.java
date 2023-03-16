package app.Controllers;

import app.Entities.Item;
import app.Service.CartService;
import app.Service.ItemService;

import jakarta.servlet.http.HttpServletResponse;

import app.SessionVariables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;


@Controller
public class IndexController {

    @Autowired
    private SessionVariables sessionVariables;

    @Autowired
    ItemService itemService;
    @Autowired
    CartService cartService;
    @Autowired
    HashMap<Integer, Integer> imageID;

    List<Item> searchedItems = new LinkedList<>();




    @GetMapping("/")
    public String index(
            @CookieValue(name = "customerid",required = false) String customerId,
            Model model){
        if(sessionVariables.isSearching()){
            model.addAttribute("myItems", searchedItems);
        }
        else {
            model.addAttribute("myItems", itemService.getUnhiddenItems());
        }
        sessionVariables.setCustomerLoggedIn(null != customerId && sessionVariables.isCustomerLoggedIn(Integer.parseInt(customerId)));

        var basketCount = sessionVariables.isCustomerLoggedIn() ?
                cartService.getCart(Integer.parseInt(customerId)).stream().map(ci -> ci.getCartItem().getQuantity()).reduce(0, Integer::sum) :
                0;
        sessionVariables.setBasketCount(basketCount);
        model.addAttribute("customerLoggedIn", sessionVariables.isCustomerLoggedIn());
        model.addAttribute("adminLoggedIn", sessionVariables.isAdminLoggedIn());
        model.addAttribute("searching", sessionVariables.isSearching());

        model.addAttribute("basketCount", sessionVariables.getBasketCount());
        sessionVariables.setSearching(false);

        int count = 1;
        Map<String, Integer> itemNumberMap = new HashMap<>();
        for (Item item : itemService.getAllItems()) {
            if (itemNumberMap.containsKey(item.getItemName())) {
                // item with the same name already has a number assigned
                int itemNumber = itemNumberMap.get(item.getItemName());
                imageID.put(item.getItemId(), itemNumber);
            } else {
                // item with the same name doesn't have a number assigned yet
                imageID.put(item.getItemId(), count);
                itemNumberMap.put(item.getItemName(), count);
                count++;
                if (count > 10) {
                    count = 1;
                }
            }
        }

        model.addAttribute("imageID", imageID);
        return "index.html";
    }

    @PostMapping("/")
    public String search(@RequestParam String search_query){
        sessionVariables.setSearching(true);
        searchedItems = itemService.getSearchedItems(search_query);
        return "redirect:/";
    }



    @GetMapping ("/{sort}/{trained}/{untrained}")
    public String  filter(@PathVariable String sort, @PathVariable String trained, @PathVariable String untrained, Model model, HttpServletResponse response) {
        List<String> filterValues = new ArrayList<>();
        filterValues.add(sort);
        filterValues.add(trained);
        filterValues.add(untrained);
        System.out.println(filterValues);
        // Filter the list of items based on the checked checkboxes
        List<Item> items = itemService.getUnhiddenItems();
        if (filterValues != null && !filterValues.isEmpty()) {
            if (filterValues.contains("price=checked")) {
                items.sort(Comparator.comparing(Item::getPrice));
            }
            if (filterValues.contains("untrained=unchecked")) {
                items = items.stream().filter(item -> item.getIsTrained() == 1).collect(Collectors.toList());
            }
            if (filterValues.contains("trained=unchecked")) {
                items = items.stream().filter(item -> item.getIsTrained() == 0).collect(Collectors.toList());
            }
        }
        model.addAttribute("imageID", imageID);
        model.addAttribute("myItems",items);
        return "catalogue.html";
    }


}





