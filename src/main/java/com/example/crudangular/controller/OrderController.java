package com.example.crudangular.controller;

import com.example.crudangular.entity.Order;
import com.example.crudangular.service.OrderService;
import com.example.crudangular.specification.OrderSpecification;
import com.example.crudangular.specification.SearchCriteria;
import com.example.crudangular.specification.SearchCriteriaOperator;
import com.example.crudangular.util.DateTimeHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@CrossOrigin("*")
@Log4j2
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
            @RequestParam(defaultValue = "") String startDate,
            @RequestParam(defaultValue = "") String endDate,
            @RequestParam(defaultValue = "1") int status) {
        Specification<Order> specification = Specification.where(null);
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
                    = new SearchCriteria("lastName", SearchCriteriaOperator.JOIN_USER, userName);
            OrderSpecification filter = new OrderSpecification(searchCriteria);
            specification = specification.and(filter);
        }
        if (userPhone != null){
            SearchCriteria searchCriteria
                    = new SearchCriteria("phone", SearchCriteriaOperator.JOIN_USER, userPhone);
            OrderSpecification filter = new OrderSpecification(searchCriteria);
            specification = specification.and(filter);
        }
        log.info("check start date: " + startDate);
        if (startDate != null & startDate.length()>1){
            SearchCriteria searchCriteria
                    = new SearchCriteria("createdAt", SearchCriteriaOperator.GREATER_THAN_OR_EQUALS,DateTimeHelper.convertStringToLocalDate(startDate));
            OrderSpecification filter = new OrderSpecification(searchCriteria);
            specification = specification.and(filter);
        }
        if (endDate != null & endDate.length()>1){
            SearchCriteria searchCriteria
                    = new SearchCriteria("createdAt", SearchCriteriaOperator.LESS_THAN_OR_EQUALS, DateTimeHelper.convertStringToLocalDate(endDate));
            OrderSpecification filter = new OrderSpecification(searchCriteria);
            specification = specification.and(filter);
        }
        Page<Order> result = this.orderService.findAll(page, size, specification);
        return ResponseEntity.ok().body(result);
    }
}