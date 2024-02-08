package org.gfa.avusfoxticketbackend.services.impl;

import org.gfa.avusfoxticketbackend.dtos.ProductReviewRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.ProductReviewResponseDTO;
import org.gfa.avusfoxticketbackend.models.ProductReview;
import org.gfa.avusfoxticketbackend.repositories.OrderRepository;
import org.gfa.avusfoxticketbackend.repositories.ProductRepository;
import org.gfa.avusfoxticketbackend.repositories.ProductReviewRepository;
import org.gfa.avusfoxticketbackend.repositories.UserRepository;
import org.gfa.avusfoxticketbackend.services.ProductReviewService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductReviewServiceImpl(ProductReviewRepository productReviewRepository,
                                    UserRepository userRepository,
                                    OrderRepository orderRepository,
                                    ProductRepository productRepository) {
        this.productReviewRepository = productReviewRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ProductReviewResponseDTO toProductReviewResponseDTO(ProductReview productReview) {
        return new ProductReviewResponseDTO(
                productReview.getId(),
                productReview.getReviewContent(),
                productReview.getNumberOfStars(),
                productReview.getOrder().getId(),
                productReview.getProduct().getName(),
                productReview.getUser().getName()
        );
    }

    @Override
    public ProductReviewResponseDTO saveNewProductReview(ProductReviewRequestDTO productReviewRequestDTO) {
        ProductReview productReview = new ProductReview(
                productReviewRequestDTO.getReviewContent(),
                productReviewRequestDTO.getNumberOfStars(),
                orderRepository.findById(productReviewRequestDTO.getOrderId()).orElseThrow(),
                productRepository.findByName(productReviewRequestDTO.getProductName()).orElseThrow(),
                userRepository.findByEmail(productReviewRequestDTO.getUsername()).orElseThrow()
        );
        productReviewRepository.save(productReview);
        return toProductReviewResponseDTO(productReview);
    }
}
