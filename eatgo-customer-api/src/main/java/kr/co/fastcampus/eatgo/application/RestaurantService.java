package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    MemuItemRepository memuItemRepository;

    @Autowired
    ReviewRepository reviewRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, MemuItemRepository memuItemRepository, ReviewRepository reviewRepository) {
        this.restaurantRepository = restaurantRepository;
        this.memuItemRepository = memuItemRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<Restaurant> getRestaurants(String region, long categoryId) {
        List<Restaurant> restaurants = restaurantRepository.findAllByAddressContainingAndCategoryId(region, categoryId);

        return restaurants;
    }

    public Restaurant getRestaurant(Long id){
      Restaurant restaurant = restaurantRepository.findById(id)
              .orElseThrow(() -> new RestaurantNotFoundException(id));

      List<MenuItem> menuItems = memuItemRepository.findAllByRestaurantId(id);
      restaurant.setMenuItems(menuItems);

      List<Review> reviews = reviewRepository.findAllByRestaurantId(id);
      restaurant.setReviews(reviews);

      return restaurant;
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
}
