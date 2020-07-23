package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class RestaurantServiceTest {
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MemuItemRepository memuItemRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        mockRestaurantRepository();
        mockMemuItemRepository();
        mockReviewRepository();

        restaurantService = new RestaurantService(restaurantRepository, memuItemRepository, reviewRepository);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        restaurants.add(
               Restaurant.builder()
                    .id(1004L)
                    .categoryId(1L)
                    .name("Bob zip")
                    .address("Seoul")
                    .menuItems(new ArrayList<MenuItem>())
                    .build()
        );
        given(restaurantRepository.findAllByAddressContainingAndCategoryId("Seoul", 1L)).willReturn(restaurants);

        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .menuItems(new ArrayList<MenuItem>())
                .build();
        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

        Restaurant saved = Restaurant.builder()
                .id(1234L)
                .name("BeRyong")
                .address("Busan")
                .menuItems(new ArrayList<MenuItem>())
                .build();

        given(restaurantRepository.save(any())).willReturn(saved);
    }

    private void mockMemuItemRepository() {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        menuItems.add(
            MenuItem.builder()
                    .name("Kimchi")
                    .build()
        );

        given(memuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    private void mockReviewRepository() {
        List<Review> reviews = new ArrayList<Review>();
        reviews.add(
           Review.builder()
           .name("BeRyong")
           .score(1)
           .description("Bad")
           .build()
        );

        given(reviewRepository.findAllByRestaurantId(1004L)).willReturn(reviews);
    }

    @Test
    public void getRestaurants(){
        String region = "Seoul";
        Long categoryId = 1L;
        List<Restaurant> restaurants = restaurantService.getRestaurants(region, categoryId);
        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId(), is(1004L));
    }

    @Test
    public void getRestaurantWithExisted(){
      Restaurant restaurant = restaurantService.getRestaurant(1004L);
      assertThat(restaurant.getId(), is(1004L));

      MenuItem menuItem = restaurant.getMenuItems().get(0);
      assertThat(menuItem.getName(), is("Kimchi"));

      Review review = restaurant.getReviews().get(0);
      assertThat(review.getName(), is("BeRyong"));
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void getRestaurantWithNotExisted(){
        restaurantService.getRestaurant(404L);
    }
}