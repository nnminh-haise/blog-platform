package com.proj.projblogplatform.controller;

import com.proj.projblogplatform.dto.RepositoryResponse;
import com.proj.projblogplatform.model.Category;
import com.proj.projblogplatform.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;

@Controller
@RequestMapping("categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String create(ModelMap model) {
        Category category = new Category(
                null,
                "cate01",
                LocalDateTime.now(),
                null,
                LocalDateTime.now(),
                null
        );
        RepositoryResponse response = categoryRepository.save(category);
        System.out.println(response.getMessage());
        model.addAttribute("message", response.getMessage());
        return "index";
    }
}
