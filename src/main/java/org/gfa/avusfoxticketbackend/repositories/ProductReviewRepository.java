package org.gfa.avusfoxticketbackend.repositories;

import org.gfa.avusfoxticketbackend.models.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {}
