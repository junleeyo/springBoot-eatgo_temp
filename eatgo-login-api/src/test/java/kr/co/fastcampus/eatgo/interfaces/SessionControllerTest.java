package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.EmailNotExistedException;
import kr.co.fastcampus.eatgo.application.PasswordWrongException;
import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserService userService;

    @MockBean
    JwtUtil jwtUtil;

    @Test
    public void createWithValid() throws Exception {
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder()
                .id(1004L)
                .name("Jone")
                .password("test")
                .level(1L)
                .build();

        given(userService.authenticate(email, password)).willReturn(mockUser);
        given(jwtUtil.createToken(1004L, "Jone", null)).willReturn("header.payload.signature");

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\", \"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/session"))
                .andExpect(content().string( containsString("{\"accessToken\":\"header.payload.signature\"}")));


        verify(userService).authenticate(eq("tester@example.com"), eq("test"));

    }

    @Test
    public void createWithNotExistedEmail() throws Exception {

        given(userService.authenticate("x@example.com", "test"))
                .willThrow(EmailNotExistedException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"x@example.com\", \"password\":\"test\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("x@example.com"), eq("test"));

    }

    @Test
    public void createWithWrongPassword() throws Exception {

        given(userService.authenticate("tester@example.com", "x"))
                .willThrow(PasswordWrongException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\", \"password\":\"x\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("tester@example.com"), eq("x"));

    }

    @Test
    public void createRestaurantOwner() throws Exception {
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder()
                .id(1004L)
                .name("Jone")
                .password("test")
                .level(50L)
                .restaurantId(2L)
                .build();

        given(userService.authenticate(email, password)).willReturn(mockUser);
        given(jwtUtil.createToken(1004L, "Jone", 2L)).willReturn("header.payload.signature");

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"tester@example.com\", \"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/session"))
                .andExpect(content().string( containsString("{\"accessToken\":\"header.payload.signature\"}")));


        verify(userService).authenticate(eq("tester@example.com"), eq("test"));

    }

}