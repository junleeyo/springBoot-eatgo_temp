package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.CategoryService;
import kr.co.fastcampus.eatgo.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categorys")
    public List<Category> list(){
        return categoryService.getCategorys();
    }

    @PostMapping("/categorys")
    public ResponseEntity<?> create(@Valid @RequestBody Category resource) throws URISyntaxException {
        Category category = categoryService.addCategory(resource.getName());

        return ResponseEntity.created(new URI("/categorys/"+ category.getId())).body("{}");
    }
}
