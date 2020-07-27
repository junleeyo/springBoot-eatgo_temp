package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, passwordEncoder);

    }

    @Test
    public void registerUser(){
        userService.registerUser("tester@example.com", "tester", "test");
        verify(userRepository).save(any());
    }

    @Test(expected = EmailExistedException.class)
    public void registerUserWithExistedEmail(){

        User mockUser = User.builder()
                .email("tester@example.com")
                .name("tester")
                .password("test")
                .build();

        given(userRepository.findByEmail("tester@example.com")).willReturn(Optional.of(mockUser));

        userService.registerUser("tester@example.com", "tester", "test");
        verify(userRepository, never()).save(any());
    }

    @Test
    public void authenticateWithValid(){
        User mockUser = User.builder()
                .email("tester@example.com")
                .name("tester")
                .build();

        given(userRepository.findByEmail("tester@example.com")).willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(), any())).willReturn(true);

        User user = userService.authenticate("tester@example.com","test");

        assertThat(user.getEmail(), is("tester@example.com"));
    }

    @Test(expected = EmailNotExistedException.class)
    public void authenticateWithEmailNotExisted(){
        String email = "x@example.com";
        String password = "test";

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        userService.authenticate(email,password);
    }

    @Test(expected = PasswordWrongException.class)
    public void authenticateWithPasswordWrong(){
        User mockUser = User.builder()
                .email("tester@example.com")
                .name("tester")
                .password("x")
                .build();

        given(userRepository.findByEmail("tester@example.com")).willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        userService.authenticate("tester@example.com","test");
    }
}