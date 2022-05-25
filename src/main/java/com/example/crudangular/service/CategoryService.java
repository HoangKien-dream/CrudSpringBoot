package com.example.crudangular.service;

import com.example.crudangular.entity.Category;
import com.example.crudangular.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
}
