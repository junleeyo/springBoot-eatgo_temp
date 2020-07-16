package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.MemuItemRepository;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MenuItemsServiceTest {

    MenuItemsService menuItemsService;

    @Mock
    private MemuItemRepository memuItemRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        menuItemsService = new MenuItemsService(memuItemRepository);
    }

    @Test
    public void bulkUpdate(){
        List<MenuItem> menuItems = new ArrayList<MenuItem>();

        menuItems.add(MenuItem.builder().name("Kimchi").destory(false).build());
        menuItems.add(MenuItem.builder().id(3L).name("Gukbob").destory(false).build());
        menuItems.add(MenuItem.builder().id(4L).destory(true).build());

        menuItemsService.bulkUpdate(1L, menuItems);

        verify(memuItemRepository, times(2)).save(any());
        verify(memuItemRepository, times(1)).deleteById(eq(4L));
    }

    @Test
    public void getMenuItems(){
        List<MenuItem> mockMenuItems = new ArrayList<MenuItem>();
        mockMenuItems.add(MenuItem.builder().name("Kimchi").destory(false).build());

        given(memuItemRepository.findAllByRestaurantId(eq(1004L)))
                .willReturn(mockMenuItems);
    }


}