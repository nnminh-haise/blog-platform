package com.proj.projblogplatform.controller;

import com.proj.projblogplatform.dto.RepositoryResponse;
import com.proj.projblogplatform.model.Category;
import com.proj.projblogplatform.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequestMapping("categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String create(ModelMap model) {
        Category category = new Category(
                null,
                "cate03",
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                Timestamp.valueOf(LocalDateTime.now()),
                null
        );
        RepositoryResponse response = categoryRepository.save(category);
        System.out.println(response.getMessage());
        model.addAttribute("message", response.getMessage());
        return "index";
    }
}
