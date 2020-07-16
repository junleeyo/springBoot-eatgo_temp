package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.MemuItemRepository;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuItemsService {

    @Autowired
    MemuItemRepository memuItemRepository;

    public MenuItemsService(MemuItemRepository memuItemRepository) {
        this.memuItemRepository = memuItemRepository;
    }

    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems){
        for (MenuItem menuItem : menuItems){
            update(restaurantId, menuItem);
        }

    }

    private void update(Long restaurantId, MenuItem menuItem) {
        if(menuItem.getDestory()){
            memuItemRepository.deleteById(menuItem.getId());
            return;
        }

        menuItem.setRestaurantId(restaurantId);
        memuItemRepository.save(menuItem);
    }

    public List<MenuItem> getMenuItems(long restaurantId) {
        List<MenuItem> menuItems = memuItemRepository.findAllByRestaurantId(restaurantId);
        return menuItems;
    }
}
