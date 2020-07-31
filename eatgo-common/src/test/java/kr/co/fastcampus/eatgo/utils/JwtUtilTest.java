package kr.co.fastcampus.eatgo.utils;

import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class JwtUtilTest {

    private static final String secret = "12345678901234567890123456789012";
    private JwtUtil jwtUtil;

    @Before
    public void setUp(){
        jwtUtil = new JwtUtil(secret);
    }

    @Test
    public void createToken(){
        String token = jwtUtil.createToken(129L, "tester", 1L);
        assertThat(token, containsString("."));
        //assertThat(token, containsString("...."));
    }

    @Test
    public void getCalims(){
        String token = "eyJhbGciOiJIUzI1NiJ9." +
                "eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiJKb2huIn0." +
                "8hm6ZOJykSINHxL-rf0yV882fApL3hyQ9-WGlJUyo2A";

       Claims claims = jwtUtil.getClaims(token);
       assertThat(claims.get("name"), is("John"));
       assertThat(claims.get("userId", Long.class), is(1004L));
    }

}