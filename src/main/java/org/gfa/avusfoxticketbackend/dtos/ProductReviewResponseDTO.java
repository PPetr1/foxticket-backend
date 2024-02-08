package org.gfa.avusfoxticketbackend.dtos;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDTO;

public class ProductReviewResponseDTO extends ResponseDTO {
  private Long id;
  private String reviewContent;
  private Integer numberOfStars;
  private Long orderId;
  private String productName;
  private String username;

  public ProductReviewResponseDTO() {}

  public ProductReviewResponseDTO(
      Long id,
      String reviewContent,
      Integer numberOfStars,
      Long orderId,
      String productName,
      String username) {
    this.id = id;
    this.reviewContent = reviewContent;
    this.numberOfStars = numberOfStars;
    this.orderId = orderId;
    this.productName = productName;
    this.username = username;
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

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
