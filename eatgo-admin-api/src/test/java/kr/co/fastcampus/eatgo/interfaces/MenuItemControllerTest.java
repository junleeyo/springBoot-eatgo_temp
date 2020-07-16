package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.MenuItemsService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MenuItemController.class)
public class MenuItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MenuItemsService menuItemsService;

    @Test
    public void list() throws Exception {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        menuItems.add(
                MenuItem.builder()
                .restaurantId(1L)
                .name("Kimchi")
                .destory(false)
                .build()
        );

        given(menuItemsService.getMenuItems(eq(1L))).willReturn(menuItems);

        mvc.perform(get("/restaurants/1/menuItems"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"name\":\"Kimchi\"")
                ));
    }

    @Test
    public void bulkUpdate() throws Exception {
        mvc.perform(patch("/restaurants/1/menuItems")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[]"))
                .andExpect(status().isOk());

        verify(menuItemsService).bulkUpdate(eq(1L), any());

    }


}