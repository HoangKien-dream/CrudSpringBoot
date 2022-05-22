package com.example.crudangular.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
 @EmbeddedId
 private OrderDetailKey id;
 @ManyToOne
 @MapsId("productId")
 @JoinColumn(name = "product_id")
 private Product product;
 @Column(name = "product_id", insertable = false, updatable = false)
 private int productId;
 @JsonIgnore
 @ManyToOne
 @MapsId("orderId")
 @JoinColumn(name = "order_id")
 private Order order;
 @Column(name = "order_id", insertable = false, updatable = false)
 private int orderId;
 private int quantity;
 private int unitPrice;
}
