package com.example.crudangular.service;

import com.example.crudangular.entity.*;
import com.example.crudangular.repository.OrderRepository;
import com.example.crudangular.repository.ProductRepository;
import com.example.crudangular.repository.UserRepository;
import com.example.crudangular.specification.OrderSpecification;
import com.example.crudangular.specification.SearchBody;
import com.example.crudangular.specification.SearchCriteria;
import com.example.crudangular.specification.SearchCriteriaOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    public Page<Order> findAll(int page, int limit,
                               Specification<Order> orderSpecification) {
        return orderRepository.findAll(
                orderSpecification, PageRequest.of(page - 1, limit));
    }

//    public Page<Order> findAll(SearchBody param){
//        Specification<Order> specification = Specification.where(null);
//        if (param.getKeyword() != null && param.getKeyword().length() > 0) {
//            SearchCriteria searchCriteria
//                    = new SearchCriteria("keyword", SearchCriteriaOperator.JOIN, param.getKeyword());
//            OrderSpecification filter = new OrderSpecification(searchCriteria);
//            specification = specification.and(filter);
//        }
//        if (param.getStatus() != 0) {
//            SearchCriteria searchCriteria
//                    = new SearchCriteria("status", SearchCriteriaOperator.EQUALS, param.getStatus());
//            OrderSpecification filter = new OrderSpecification(searchCriteria);
//            specification = specification.and(filter);
//        }
//        if (param.getUserId() != null) {
//            SearchCriteria searchCriteria
//                    = new SearchCriteria("userId", SearchCriteriaOperator.EQUALS, param.getUserId());
//            OrderSpecification filter = new OrderSpecification(searchCriteria);
//            specification = specification.and(filter);
//        }
//        return orderRepository.findAll(
//                specification, PageRequest.of(param.getPage() - 1, param.getLimit()));
//    }

    public Order save(Order order){
        int totalPrice = 0;
        Set<OrderDetail> orderDetailSet = new HashSet<>();
        User user = userRepository.findById(order.getUserId()).orElse(null);
        order.setUser(user);
        for (OrderDetail orderDetail:
             order.getOrderDetails()) {
            Product product = productRepository.findById(orderDetail.getProductId()).orElse(null);
            if (product != null){
                orderDetail.setId(new OrderDetailKey(order.getId(), product.getId()));
                orderDetail.setProduct(product);
                orderDetail.setOrder(order);
                orderDetail.setUnitPrice(product.getPrice());
                orderDetailSet.add(orderDetail);
                totalPrice += orderDetail.getUnitPrice() * orderDetail.getQuantity();
            }
        }
        order.setTotalPrice(totalPrice);
        order.setOrderDetails(orderDetailSet);
        return orderRepository.save(order);
    }
}
