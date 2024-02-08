package org.gfa.avusfoxticketbackend.repositories;

import java.util.List;
import java.util.Optional;

import org.gfa.avusfoxticketbackend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  boolean existsByName(String name);

  Optional<Product> findByName(String name);

  Optional<Product> findById(Long id);

  List<Product> findProductsByProductTypeId(Long id);
}
