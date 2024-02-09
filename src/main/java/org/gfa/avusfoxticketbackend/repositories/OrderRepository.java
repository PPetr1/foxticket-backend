package org.gfa.avusfoxticketbackend.repositories;

import java.util.Optional;
import org.gfa.avusfoxticketbackend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  Optional<Order> findById(Long id);
}
