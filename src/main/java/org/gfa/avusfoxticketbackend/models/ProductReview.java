package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product_reviews")
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reviewContent;
    private Integer numberOfStars;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ProductReview() {}

    public ProductReview(Long id, String reviewContent, Integer numberOfStars, Order order, Product product, User user) {
        this.id = id;
        this.reviewContent = reviewContent;
        this.numberOfStars = numberOfStars;
        this.order = order;
        this.product = product;
        this.user = user;
    }

    public ProductReview(String reviewContent, Integer numberOfStars, Order order, Product product, User user) {
        this.reviewContent = reviewContent;
        this.numberOfStars = numberOfStars;
        this.order = order;
        this.product = product;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Integer getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(Integer numberOfStars) {
        this.numberOfStars = numberOfStars;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductReview that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getReviewContent(), that.getReviewContent()) && Objects.equals(getNumberOfStars(), that.getNumberOfStars()) && Objects.equals(getOrder(), that.getOrder()) && Objects.equals(getProduct(), that.getProduct()) && Objects.equals(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getReviewContent(), getNumberOfStars(), getOrder(), getProduct(), getUser());
    }
}
