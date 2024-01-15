package org.gfa.avusfoxticketbackend.controllers;

import org.gfa.avusfoxticketbackend.dtos.ApiProductsDTO;
import org.gfa.avusfoxticketbackend.dtos.PatchResponseUserDTO;
import org.gfa.avusfoxticketbackend.dtos.RequestUserDTO;
import org.gfa.avusfoxticketbackend.logging.LogHandlerInterceptor;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

  private final ProductService productService;
  private final UserService userService;

  @Autowired
  public AdminController(ProductService productService, UserService userService) {
    this.productService = productService;
    this.userService = userService;
  }

  @GetMapping("/products")
  public ResponseEntity<ApiProductsDTO> getProducts() {
    return ResponseEntity.status(200).body(productService.getApiProductsDto());
  }

  @PatchMapping({"/users/{id}", "/users/"})
  public ResponseEntity<PatchResponseUserDTO> patchUser(
      @RequestBody(required = false) RequestUserDTO requestUserDTO,
      @PathVariable(required = false) Long id) {
    LogHandlerInterceptor.object = requestUserDTO;
    return ResponseEntity.status(200).body(userService.patchUser(requestUserDTO, id));
  }
}
