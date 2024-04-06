package com.proj.projblogplatform.controller;

import com.proj.projblogplatform.model.Blog;
import com.proj.projblogplatform.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String testRout(ModelMap model) {
        List<Blog> blogs = blogRepository.findAll();

        model.addAttribute("data", blogs);
        return "blog/index";
    }
}
