package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.MenuItemsService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuItemController {

    @Autowired
    MenuItemsService menuItemsService;

    @GetMapping("/restaurants/{restaurantId}/menuItems")
    public List<MenuItem> list(@PathVariable("restaurantId") Long restaurantId){
        List<MenuItem> menuItems = menuItemsService.getMenuItems(restaurantId);
        return menuItems;
    }

    @PatchMapping("/restaurants/{restaurantId}/menuItems")
    public String bulkUpdate(@PathVariable("restaurantId") Long restaurantId, @RequestBody List<MenuItem> menuItems){
        menuItemsService.bulkUpdate(restaurantId, menuItems);

        return "";
    }
}
