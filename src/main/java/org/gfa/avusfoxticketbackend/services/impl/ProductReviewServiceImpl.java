package org.gfa.avusfoxticketbackend.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.gfa.avusfoxticketbackend.dtos.ProductReviewRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.ProductReviewResponseDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.*;
import org.gfa.avusfoxticketbackend.repositories.OrderRepository;
import org.gfa.avusfoxticketbackend.repositories.ProductRepository;
import org.gfa.avusfoxticketbackend.repositories.ProductReviewRepository;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.gfa.avusfoxticketbackend.services.ExceptionService;
import org.gfa.avusfoxticketbackend.services.ProductReviewService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

  private final ProductReviewRepository productReviewRepository;
  private final UserRepository userRepository;
  private final UserService userService;
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final ExceptionService exceptionService;

  @Autowired
  public ProductReviewServiceImpl(
      ProductReviewRepository productReviewRepository,
      UserRepository userRepository,
      UserService userService,
      OrderRepository orderRepository,
      ProductRepository productRepository,
      ExceptionService exceptionService) {
    this.productReviewRepository = productReviewRepository;
    this.userRepository = userRepository;
    this.userService = userService;
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
    this.exceptionService = exceptionService;
  }

  @Override
  public ProductReviewResponseDTO toProductReviewResponseDTO(ProductReview productReview) {
    return new ProductReviewResponseDTO(
        productReview.getId(),
        productReview.getReviewContent(),
        productReview.getNumberOfStars(),
        productReview.getOrder().getId(),
        productReview.getProduct().getName(),
        productReview.getUser().getName());
  }

  @Override
  public ProductReviewResponseDTO saveNewProductReview(
      ProductReviewRequestDTO productReviewRequestDTO, String token) {
    exceptionService.checkProductReviewRequestDTOErrors(productReviewRequestDTO);

    Optional<User> currentUserOptional = userService.extractUserFromToken(token);
    Optional<Product> currentProductOptional =
        productRepository.findByName(productReviewRequestDTO.getProductName());
    Optional<Order> currentOrderOptional =
        orderRepository.findById(productReviewRequestDTO.getOrderId());

    if (currentUserOptional.isPresent()
        && currentProductOptional.isPresent()
        && currentOrderOptional.isPresent()) {
      User currentUser = currentUserOptional.get();
      Product currentProduct = currentProductOptional.get();
      Order currentOrder = currentOrderOptional.get();
      ProductReview productReview =
          new ProductReview(
              productReviewRequestDTO.getReviewContent(),
              productReviewRequestDTO.getNumberOfStars(),
              currentOrder,
              currentProduct,
              currentUser);
      productReviewRepository.save(productReview);
      return toProductReviewResponseDTO(productReview);
    } else {
      throw new ApiRequestException("/api/review", "Unknown Error");
    }
  }

  @Override
  public List<ProductReviewResponseDTO> getReviewsByUser(String token) {
    Optional<User> currentUserOptional = userService.extractUserFromToken(token);
    if (currentUserOptional.isPresent()) {
      return currentUserOptional.get().getReviews().stream()
          .map(this::toProductReviewResponseDTO)
          .collect(Collectors.toList());
    } else {
      throw new ApiRequestException("/api/get-user-reviews", "User not found.");
    }
  }

  @Override
  public List<ProductReviewResponseDTO> getProducReviews(Long productId) {
    if (productId == null) {
      exceptionService.throwProductIdRequired();
    }
    Optional<Product> currentProductOptional = productRepository.findById(productId);
    if (currentProductOptional.isPresent()) {
      return currentProductOptional.get().getReviewList().stream()
          .map(this::toProductReviewResponseDTO)
          .collect(Collectors.toList());
    } else {
      throw new ApiRequestException("/api/get-user-reviews", "Product not found.");
    }
  }
}
