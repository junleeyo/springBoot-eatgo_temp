package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.CategoryService;
import kr.co.fastcampus.eatgo.domain.Category;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    CategoryService categoryService;

    @Test
    public void list() throws Exception {
        List<Category> mokCategorys = new ArrayList<>();
        mokCategorys.add(
                Category.builder()
                        .name("Korea Food")
                        .build()
        );
        given(categoryService.getCategorys()).willReturn(mokCategorys);

        mvc.perform(get("/categorys"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("Korea Food")
                ));
    }

    @Test
    public void create() throws Exception {
        List<Category> mokCategorys = new ArrayList<>();
        mokCategorys.add(
                Category.builder()
                        .name("Korea Food")
                        .build()
        );
        given(categoryService.addCategory("Korea Food")).willReturn(mokCategorys.get(0));

        mvc.perform(post("/categorys")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Korea Food\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("{}"));

        verify(categoryService).addCategory("Korea Food");
    }
}