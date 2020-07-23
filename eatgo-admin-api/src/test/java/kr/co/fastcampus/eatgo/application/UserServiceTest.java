package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void serUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);

    }

    @Test
    public void getUsers(){
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(
            User.builder()
                .email("tester@example.com")
                .name("user1")
                .level(100L)
                .build()
        );
        given(userRepository.findAll()).willReturn(mockUsers);

        List<User> users = userService.getUsers();
        User user = users.get(0);
        assertThat(user.getName(), is("user1"));
    }

    @Test
    public void addUser(){
        String email = "admin@example.com";
        String name = "administrator";

        User mokUser = User.builder()
                .email(email)
                .name(name)
                .build();

        given(userRepository.save(any())).willReturn(mokUser);

        User user = userService.addUser(email,name);

        assertThat(user.getName(), is(name));
    }

    @Test
    public void updateUser(){
        Long id = 1004L;
        String email = "admin@example.com";
        String name = "administrator";
        Long level = 0L;

        User mokUser = User.builder()
                .id(id)
                .email(email)
                .name(name)
                .level(level)
                .build();

        given(userRepository.findById(id)).willReturn(Optional.of(mokUser));

        User user = userService.updateUser(id, email, name, level);

        assertThat(user.getName(), is("administrator"));
        assertThat(user.isAdmin(), is(false));
    }

    @Test
    public void deactivateUser(){

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

        given(userRepository.findById(id)).willReturn(Optional.of(mokUser));

        User user = userService.deactivateUser(1004L);

        verify(userRepository).findById(1004L);

        assertThat(user.isAdmin(), is(false));
        assertThat(user.isActive(), is(false));
    }
}