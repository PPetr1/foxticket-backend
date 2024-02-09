package org.gfa.avusfoxticketbackend.controllers;

import java.util.List;
import org.gfa.avusfoxticketbackend.config.services.JwtService;
import org.gfa.avusfoxticketbackend.dtos.*;
import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.CartResponseDTO;
import org.gfa.avusfoxticketbackend.logging.LogHandlerInterceptor;
import org.gfa.avusfoxticketbackend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecuredController {

  private final UserService userService;
  private final OrderService orderService;
  private final CartService cartService;
  private final JwtService jwtService;
  private final ProductReviewService productReviewService;

  @Autowired
  public SecuredController(
      UserService userService,
      OrderService orderService,
      CartService cartService,
      JwtService jwtService,
      ProductReviewService productReviewService) {
    this.userService = userService;
    this.orderService = orderService;
    this.cartService = cartService;
    this.jwtService = jwtService;
    this.productReviewService = productReviewService;
  }

  @PostMapping("/cart")
  public ResponseEntity<CartResponseDTO> addToCart(
      @RequestBody(required = false) CartRequestDTO requestDTO,
      @RequestHeader(HttpHeaders.AUTHORIZATION) String requestHeader) {
    LogHandlerInterceptor.object = List.of(requestDTO, requestHeader);
    return ResponseEntity.status(200)
        .body(
            cartService.saveProductToCart(
                requestDTO, jwtService.extractBearerToken(requestHeader)));
  }

  @PatchMapping("/cart")
  public ResponseEntity<ModifyCartResponseDTO> modifyCart(
      @RequestBody(required = false) ModifyCartRequestDTO requestDTO,
      @RequestHeader(HttpHeaders.AUTHORIZATION) String requestHeader) {
    LogHandlerInterceptor.object = List.of(requestDTO, requestHeader);
    return ResponseEntity.status(200)
        .body(
            cartService.modifyProductInCart(
                requestDTO, jwtService.extractBearerToken(requestHeader)));
  }

  @DeleteMapping("/cart")
  public ResponseEntity<ResponseStatusMessageDTO> deleteCart(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
    LogHandlerInterceptor.object = token;
    token = jwtService.extractBearerToken(token);
    userService.checkUserVerification(token);
    return ResponseEntity.status(200).body(cartService.deleteCart(token));
  }

  @PostMapping("/orders")
  public ResponseEntity<ResponseOrderDTO> order(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String requestHeader) {
    LogHandlerInterceptor.object = requestHeader;
    userService.checkUserVerification(jwtService.extractBearerToken(requestHeader));
    return ResponseEntity.status(200)
        .body(orderService.saveOrdersFromCart(jwtService.extractBearerToken(requestHeader)));
  }

  @GetMapping("/orders")
  public ResponseEntity<ResponseOrderSummaryDTO> getAllOrders(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String requestHeader) {
    LogHandlerInterceptor.object = requestHeader;
    return ResponseEntity.status(200)
        .body(orderService.getOrderSummaryDTO(jwtService.extractBearerToken(requestHeader)));
  }

  @PostMapping("/review")
  public ResponseEntity<ProductReviewResponseDTO> review(
      @RequestBody(required = false) ProductReviewRequestDTO productReviewRequestDTO,
      @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
    LogHandlerInterceptor.object = List.of(productReviewRequestDTO, token);
    return ResponseEntity.status(200)
        .body(
            productReviewService.saveNewProductReview(
                productReviewRequestDTO, jwtService.extractBearerToken(token)));
  }

  @GetMapping("/get-user-reviews")
  public ResponseEntity<List<ProductReviewResponseDTO>> getUserReviews(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
    LogHandlerInterceptor.object = token;
    return ResponseEntity.status(200)
        .body(productReviewService.getReviewsByUser(jwtService.extractBearerToken(token)));
  }

  @GetMapping("/get-product-reviews/{productId}")
  public ResponseEntity<List<ProductReviewResponseDTO>> getProductReviews(
      @PathVariable(required = false) Long productId) {
    LogHandlerInterceptor.object = productId;
    return ResponseEntity.status(200).body(productReviewService.getProducReviews(productId));
  }
}
