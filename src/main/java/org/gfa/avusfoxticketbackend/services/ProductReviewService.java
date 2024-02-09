package org.gfa.avusfoxticketbackend.services;

import java.util.List;
import org.gfa.avusfoxticketbackend.dtos.ProductReviewRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.ProductReviewResponseDTO;
import org.gfa.avusfoxticketbackend.models.ProductReview;

public interface ProductReviewService {
  ProductReviewResponseDTO toProductReviewResponseDTO(ProductReview productReview);

  ProductReviewResponseDTO saveNewProductReview(
      ProductReviewRequestDTO productReviewRequestDTO, String token);

  List<ProductReviewResponseDTO> getReviewsByUser(String token);

  List<ProductReviewResponseDTO> getProducReviews(Long productId);
}
