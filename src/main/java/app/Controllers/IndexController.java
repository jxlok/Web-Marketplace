package app.Controllers;

import app.Entities.Item;
import app.Service.ItemService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class IndexController {

    @Autowired
    ItemService itemService;


    @GetMapping("/")
    public String index(Model model) {
        // Handle GET request
        System.out.println("session");
        List<Item> items = itemService.getUnhiddenItems();
        model.addAttribute("myItems", items);
        return "index.html";
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
        model.addAttribute("myItems",items);
        System.out.println(items);
        return "catalogue.html";
    }


}





