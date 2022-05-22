package com.example.crudangular.controller;

import com.example.crudangular.entity.Order;
import com.example.crudangular.service.OrderService;
import com.example.crudangular.specification.OrderSpecification;
import com.example.crudangular.specification.SearchCriteria;
import com.example.crudangular.specification.SearchCriteriaOperator;
import com.example.crudangular.util.DateTimeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping(method = RequestMethod.POST)
    public Order save(@RequestBody Order order) {
        return orderService.save(order);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String userName,
            @RequestParam(defaultValue = "") String userPhone,
            @RequestParam(defaultValue = "1") int status) {

        Specification<Order> specification = Specification.where(null);
        DateTimeHelper dateTimeHelper;
        if (keyword != null && keyword.length() > 0) {
            SearchCriteria searchCriteria
                    = new SearchCriteria("keyword", SearchCriteriaOperator.JOIN, keyword);
            OrderSpecification filter = new OrderSpecification(searchCriteria);
            specification = specification.and(filter);
        }
        if (status != 0) {
            SearchCriteria searchCriteria
                    = new SearchCriteria("status", SearchCriteriaOperator.EQUALS, status);
            OrderSpecification filter = new OrderSpecification(searchCriteria);
            specification = specification.and(filter);
        }
        if (userName != null) {
            SearchCriteria searchCriteria
                    = new SearchCriteria("name", SearchCriteriaOperator.JOIN_USER, userName);
            OrderSpecification filter = new OrderSpecification(searchCriteria);
            specification = specification.and(filter);
        }
        if (userPhone != null){
            SearchCriteria searchCriteria
                    = new SearchCriteria("phone", SearchCriteriaOperator.JOIN_PHONE, userPhone);
            OrderSpecification filter = new OrderSpecification(searchCriteria);
            specification = specification.and(filter);
        }
//        if (startDate != null){
//            SearchCriteria searchCriteria
//                    = new SearchCriteria("createdAt", SearchCriteriaOperator.GREATER_THAN_OR_EQUALS,DateTimeHelper.convertLocalDateToString(startDate));
//            OrderSpecification filter = new OrderSpecification(searchCriteria);
//            specification = specification.and(filter);
//        }
//        if (endDate != null){
//            SearchCriteria searchCriteria
//                    = new SearchCriteria("createdAt", SearchCriteriaOperator.LESS_THAN_OR_EQUALS, endDate);
//            OrderSpecification filter = new OrderSpecification(searchCriteria);
//            specification = specification.and(filter);
//        }
        Page<Order> result = this.orderService.findAll(page, size, specification);
        return ResponseEntity.ok().body(result);
    }
}