package com.proj.projblogplatform.controller;

import com.proj.projblogplatform.dto.RepositoryResponse;
import com.proj.projblogplatform.model.Category;
import com.proj.projblogplatform.repository.CategoryRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {


//    @RequestMapping(method = RequestMethod.GET)
//    public String create(ModelMap model) {
//        Category category = new Category(
//                null,
//                "cate03",
//                Timestamp.valueOf(LocalDateTime.now()),
//                null,
//                Timestamp.valueOf(LocalDateTime.now()),
//                null
//        );
//        RepositoryResponse response = categoryRepository.save(category);
//        System.out.println(response.getMessage());
//        model.addAttribute("message", response.getMessage());
//        return "index";
//    }
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SessionFactory factory;
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap model) {

        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            System.out.println("ID: " + category.getId() + ", Name: " + category.getName());
        }
        model.addAttribute("categories", categories);
        return "category/index";
    }
    @RequestMapping(value ="/insert", method = RequestMethod.GET)
    public String add(ModelMap model) {
    model.addAttribute("category", new Category());
    return "category/insert";
}
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String add(ModelMap model, @ModelAttribute("category") Category category ){

        category.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        category.setUpdateAt(Timestamp.valueOf(LocalDateTime.now()));
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(category);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Error: " + e);
            System.out.println("createat " + category.getCreateAt());
            e.printStackTrace();
        } finally {
            session.close();

        }
        return "redirect:/category/list.htm";
    }
    @RequestMapping(value = "/test")
    public String test(ModelMap model) {
        List<Category> categories = categoryRepository.findAll();
        System.out.println("ID: ");
        model.addAttribute("data", categories);
        return "category/test";
    }
}
