package com.example.javaee.controller;

import com.example.javaee.dto.ResponseDto;
import com.example.javaee.model.Category;
import com.example.javaee.repository.CategoryRepository;
import com.example.javaee.service.CategoryService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/category")
public class CategoryController {
//    @Autowired
//    CategoryService categoryService;
//
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    public String list(ModelMap model) {
//
//        ResponseDto<List<Category>> responseDto = categoryService.findAll();
//        if (!responseDto.hasStatus(HttpStatus.OK)) {
//            return "category/index";
//        }
//
//        List<Category> categories = responseDto.getData();
//        for (Category category: categories) {
//            System.out.println("ID: " + category.getId() + ", Name: " + category.getName());
//        }
//        model.addAttribute("categories", categories);
//        return "category/index";
//    }
//
//    @RequestMapping(value ="/insert", method = RequestMethod.GET)
//    public String add(ModelMap model) {
//        model.addAttribute("category", new Category());
//        return "category/insert";
//    }
//
//    @RequestMapping(value = "/insert", method = RequestMethod.POST)
//    public String add(ModelMap model, @ModelAttribute("category") Category category ){
////        this.ca
//        return "redirect:/category/list.htm";
//    }
//
//    @RequestMapping(value = "/test")
//    public String test(ModelMap model) {
//        ResponseDto<List<Category>> responseDto = categoryService.findAll();
//        if (!responseDto.hasStatus(HttpStatus.OK)) {
//            return "category/test";
//        }
//
//        model.addAttribute("data", responseDto.getData());
//        return "category/test";
//    }
//
//    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
//    public String edit(ModelMap model, @PathVariable("id") UUID id  ){
//        List<Category> categories = categoryRepository.findAll();
//        model.addAttribute("categories", categories);
//        Category category = categoryRepository.findById(id);
//        model.addAttribute("cate", category);
//        System.out.println("ID: " + category.getId() + ", Name: " + category.getName());
//        System.out.println("ID: " + category.getCreateAt() + ", Name: " + category.getUpdateAt());
//        model.addAttribute("id", id);
//        return "category/edit";
//    }
//
//    @RequestMapping(value ="edit/{id}", method = RequestMethod.POST)
//    public String edit(ModelMap model, @PathVariable("id") UUID id,@ModelAttribute("cate") Category category){
//
//        category.setUpdateAt(Timestamp.valueOf(LocalDateTime.now()));
//        Category old = categoryRepository.findById(id);
//        category.setCreateAt(old.getCreateAt());
//
//        Session session = factory.openSession();
//        Transaction transaction = session.beginTransaction();
//
//        try {
//
//            session.update(category);
//            transaction.commit();
//            model.addAttribute("message", "Update success");
//        } catch (Exception e) {
//            transaction.rollback();
//            System.out.println("Error: " + e);
//            System.out.println("createat " + category.getCreateAt());
//            e.printStackTrace();
//            model.addAttribute("message", "Error: " + e);
//        } finally {
//            session.close();
//        }
//        return "redirect:/category/list.htm";
//    }
//    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
//    public String delete(ModelMap model, @PathVariable("id") UUID id) {
//        Category category = categoryRepository.findById(id);
//        Session session = factory.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            session.delete(category);
//            transaction.commit();
//            model.addAttribute("message", "Delete success");
//        } catch (Exception e) {
//            transaction.rollback();
//            System.out.println("Error: " + e);
//            e.printStackTrace();
//            model.addAttribute("message", "Error: " + e);
//        } finally {
//            session.close();
//
//        }
//        return "redirect:/category/list.htm";
//    }
}
