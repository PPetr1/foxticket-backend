package org.gfa.avusfoxticketbackend.repositories;

import org.gfa.avusfoxticketbackend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  Optional<Order> findById(Long id);
}
