package org.gfa.avusfoxticketbackend.services;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.gfa.avusfoxticketbackend.dtos.CartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.CartResponseDTO;
import org.gfa.avusfoxticketbackend.dtos.ModifyCartRequestDTO;
import org.gfa.avusfoxticketbackend.dtos.ModifyCartResponseDTO;
import org.gfa.avusfoxticketbackend.models.Cart;
import org.gfa.avusfoxticketbackend.models.CartProduct;
import org.gfa.avusfoxticketbackend.models.User;

public interface CartService {
  Optional<Cart> getCartById(Long id);

  Optional<Cart> getCartByUser(User user);

  void saveCart(Cart cart);

  void deleteCart(Cart cart);

  CartResponseDTO saveProductToCart(
          CartRequestDTO cartRequestDTO,
          String token);

  ModifyCartResponseDTO modifyProductInCart(
          ModifyCartRequestDTO requestDTO,
          String token);

  void addCartItemToCart(User user, CartProduct cartProduct);
}
