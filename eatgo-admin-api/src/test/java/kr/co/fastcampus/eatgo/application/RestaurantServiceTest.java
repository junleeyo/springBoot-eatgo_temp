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
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class RestaurantServiceTest {
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        mockRestaurantRepository();

        restaurantService = new RestaurantService(restaurantRepository);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        restaurants.add(
               Restaurant.builder()
                    .id(1004L)
                    .name("Bob zip")
                    .address("Seoul")
                    .menuItems(new ArrayList<MenuItem>())
                    .build()
        );
        given(restaurantRepository.findAll()).willReturn(restaurants);

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

    @Test
    public void getRestaurants(){
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId(), is(1004L));
    }

    @Test
    public void getRestaurantWithExisted(){
      Restaurant restaurant = restaurantService.getRestaurant(1004L);
      assertThat(restaurant.getId(), is(1004L));
    }

    @Test(expected = RestaurantNotFoundException.class)
    public void getRestaurantWithNotExisted(){
        restaurantService.getRestaurant(404L);
    }

    @Test
    public void addRestaurant(){
        Restaurant restaurant = Restaurant.builder()
                .name("BeRyong")
                .address("Busan")
                .build();

        Restaurant created = restaurantService.addRestaurant(restaurant);
        assertThat(created.getId(), is(1234L));
    }

    @Test
    public void updateRestaurant() {
        Restaurant updated = restaurantService.updateRestaurant(1004L, "Sool zip", "Buson");
        assertThat(updated.getName(), is("Sool zip"));
        assertThat(updated.getAddress(), is("Buson"));
    }
}