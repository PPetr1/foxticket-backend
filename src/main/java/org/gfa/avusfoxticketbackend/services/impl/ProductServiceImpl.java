package org.gfa.avusfoxticketbackend.services.impl;

import java.util.ArrayList;
import java.util.stream.Collectors;
import org.gfa.avusfoxticketbackend.dtos.ApiProductsDTO;
import org.gfa.avusfoxticketbackend.dtos.ProductDTO;
import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.models.Product;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.repositories.ProductRepository;
import org.gfa.avusfoxticketbackend.services.ExceptionService;
import org.gfa.avusfoxticketbackend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ExceptionService exceptionService;

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository, ExceptionService exceptionService) {
    this.productRepository = productRepository;
    this.exceptionService = exceptionService;
  }

  @Override
  public ProductDTO toProductDto(Product product) {
    return new ProductDTO(
        product.getId(),
        product.getName(),
        product.getPrice(),
        product.getDuration(),
        product.getDescription(),
        product.getType());
  }

  @Override
  public ApiProductsDTO getApiProductsDto() {
    return new ApiProductsDTO(
        new ArrayList<>(
            productRepository.findAll().stream()
                .map(this::toProductDto)
                .collect(Collectors.toList())));
  }

  @Override
  public ProductDTO updateProduct(RequestProductDTO requestProductDTO, Long productId) {

    exceptionService.checkUpdateProductRequestFields(requestProductDTO, productId);

    Product product =
            productRepository
                    .findById(productId)
                    .orElseThrow(
                            () ->
                                    new ApiRequestException(
                                            "/api/products/{productId}", "Product with provided productId doesn't exist."));

    product.setName(requestProductDTO.getName());
    product.setPrice(requestProductDTO.getPrice());
    product.setDuration(Integer.parseInt(requestProductDTO.getDuration()));
    product.setDescription(requestProductDTO.getDescription());
    productRepository.save(product);

    return toProductDto(product);
  }
}
