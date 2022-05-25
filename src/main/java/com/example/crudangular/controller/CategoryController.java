package com.example.crudangular.controller;

import com.example.crudangular.entity.Category;
import com.example.crudangular.entity.User;
import com.example.crudangular.repository.CategoryRepository;
import com.example.crudangular.repository.UserRepository;
import com.example.crudangular.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/category")
public class CategoryController {
//    @Autowired
//     CategoryRepository categoryRepository;
//    private static List<Category> categoryList;
//    static {
//        categoryList = new ArrayList<>();
//        categoryList.add(new Category(1,"Macbook"));
//        categoryList.add(new Category(2,"Asus"));
//        categoryList.add(new Category(3,"MSI"));
//        categoryList.add(new Category(4,"Lenovo"));
//        categoryList.add(new Category(5,"Dell"));
//    };
//    @RequestMapping(method = RequestMethod.POST)
//    public List<Category> save(){
//        return categoryRepository.saveAll(categoryList);
//    }
    @Autowired
    CategoryService categoryService;
    @RequestMapping(method = RequestMethod.GET)
    public List<Category> findAll(){
        return categoryService.findAll();
    }
}
