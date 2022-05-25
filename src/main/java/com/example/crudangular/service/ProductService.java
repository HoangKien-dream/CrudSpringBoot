package com.example.crudangular.service;

import com.example.crudangular.entity.Order;
import com.example.crudangular.entity.Product;
import com.example.crudangular.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Page<Product> findAll(int page, int limit,
                               Specification<Product> productSpecification) {
        return productRepository.findAll(
                productSpecification, PageRequest.of(page - 1, limit));
    }
}
