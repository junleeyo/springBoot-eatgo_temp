package kr.co.fastcampus.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void creation(){
        User user = User.builder()
                .email("tester@example.com")
                .name("테스트")
                .level(100L)
                .build();

        assertThat(user.getName(), is("테스트"));
        assertThat(user.isAdmin(), is(true));

        user.deativate();
        assertThat(user.isAdmin(), is(false));
        assertThat(user.isActive(), is(false));

    }

}