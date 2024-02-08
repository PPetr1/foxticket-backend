package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String status;
  private String expiry;

  @OneToMany(mappedBy = "order")
  private List<OrderProduct> orderProducts;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "order")
  private List<ProductReview> reviewsWithThisOrder;

  public Order() {}

  public Order(User user) {
    this.user = user;
    this.orderProducts = new ArrayList<>();
  }

  public Order(String status, String expiry, List<OrderProduct> orderProducts, User user) {
    this.status = status;
    this.expiry = expiry;
    this.orderProducts = orderProducts;
    this.user = user;
  }

  public Order(Long id, String status, String expiry, List<OrderProduct> orderProducts, User user, List<ProductReview> reviewsWithThisOrder) {
    this.id = id;
    this.status = status;
    this.expiry = expiry;
    this.orderProducts = orderProducts;
    this.user = user;
    this.reviewsWithThisOrder = reviewsWithThisOrder;
  }

  public List<OrderProduct> getOrderProducts() {
    return orderProducts;
  }

  public void setOrderProducts(List<OrderProduct> orderProducts) {
    this.orderProducts = orderProducts;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getExpiry() {
    return expiry;
  }

  public void setExpiry(String expiry) {
    this.expiry = expiry;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public double getOrderPrice() {
    double output = 0;
    for (OrderProduct o : this.getOrderProducts()) {
      output += o.getTotalPrice();
    }
    return output;
  }

  public List<ProductReview> getReviewsWithThisOrder() {
    return reviewsWithThisOrder;
  }

  public void setReviewsWithThisOrder(List<ProductReview> reviewsWithThisOrder) {
    this.reviewsWithThisOrder = reviewsWithThisOrder;
  }
}
