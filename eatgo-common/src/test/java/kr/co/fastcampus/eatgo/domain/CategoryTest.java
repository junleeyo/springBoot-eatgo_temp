package kr.co.fastcampus.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CategoryTest {

    @Test
    public void creation(){
        Category category = Category.builder()
                .name("Korea Food")
                .build();

        assertThat(category.getName(), is("Korea Food"));
    }


}