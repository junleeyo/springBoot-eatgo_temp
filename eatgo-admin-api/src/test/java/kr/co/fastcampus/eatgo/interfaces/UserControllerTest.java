package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
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

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void list() throws Exception {
        List<User> users = new ArrayList<User>();
        users.add(
          User.builder()
              .email("tester@example.com")
              .name("user1")
              .level(100L)
              .build()
        );

        given(userService.getUsers()).willReturn(users);

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"name\":\"user1\"")
                ));
    }

    @Test
    public void create() throws Exception {
        String email = "admin@example.com";
        String name = "administrator";

        User mokUser = User.builder()
                .email(email)
                .name(name)
                .build();

        given(userService.addUser(email, name)).willReturn(mokUser);

        mvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"email\":\"admin@example.com\", \"name\":\"administrator\"}"))
                .andExpect(status().isCreated());

        verify(userService).addUser(email, name);

    }

    @Test
    public void update() throws Exception {
        Long id = 1004L;
        String email = "admin@example.com";
        String name = "administrator";
        Long level = 100L;

        User mokUser = User.builder()
                .id(id)
                .email(email)
                .name(name)
                .level(level)
                .build();

        given(userService.updateUser(id, email, name, level)).willReturn(mokUser);

        mvc.perform(patch("/users/1004")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"id\":\"1004\", \"email\":\"admin@example.com\", \"name\":\"administrator\", \"level\":\"100\"}"))
                .andExpect(status().isOk());


        verify(userService).updateUser(eq(id), eq(email), eq(name), eq(level));
    }

    @Test
    public void deactivate() throws Exception {
        mvc.perform(delete("/users/1004"))
                .andExpect(status().isOk());

        verify(userService).deactivateUser(1004L);
    }

}