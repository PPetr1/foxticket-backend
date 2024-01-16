package org.gfa.avusfoxticketbackend.services.impl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import org.gfa.avusfoxticketbackend.dtos.ApiProductsDTO;
import org.gfa.avusfoxticketbackend.exception.ApiRequestException;
import org.gfa.avusfoxticketbackend.dtos.RequestProductDTO;
import org.gfa.avusfoxticketbackend.dtos.ResponseProductDTO;
import org.gfa.avusfoxticketbackend.enums.Type;

import org.gfa.avusfoxticketbackend.models.Product;
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
  public ProductServiceImpl(
      ProductRepository productRepository, ExceptionService exceptionService) {
    this.productRepository = productRepository;
    this.exceptionService = exceptionService;
  }

  @Override
  public ResponseProductDTO toResponseProductDto(Product product) {
    return new ResponseProductDTO(
        product.getId(),
        product.getName(),
        product.getPrice(),
        String.valueOf(product.getDuration()),
        product.getDescription(),
        product.getType().toString());
  }

  @Override
  public ApiProductsDTO getApiProductsDto() {
    return new ApiProductsDTO(
        new ArrayList<>(
            productRepository.findAll().stream()
                .map(this::toResponseProductDto)
                .collect(Collectors.toList())));
  }

  @Override
  public ProductDTO updateProduct(RequestProductDTO requestProductDTO, Long productId) {

    exceptionService.checkForRequestProductDTOError(requestProductDTO, productId);

    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(
                () ->
                    new ApiRequestException(
                        ("/api/products/" + productId), "Product with provided id doesn't exist."));

    if (!productRepository.existsByName(requestProductDTO.getName())) {

      product.setName(requestProductDTO.getName());
      product.setPrice(requestProductDTO.getPrice());
      product.setDuration(requestProductDTO.getDuration());
      product.setDescription(requestProductDTO.getDescription());
      product.setType(Type.valueOf(requestProductDTO.getType()));
      productRepository.save(product);

      return toProductDto(product);
    } else {
      throw new ApiRequestException(("/api/products/" + productId), "Product name already exists!");
    }
  public Optional<Product> getProductById(Long id) {
    return productRepository.findById(id);
  }

  @Override
  public void saveProduct(Product product) {
    productRepository.save(product);

  public ResponseProductDTO createNewProductAndReturn(RequestProductDTO requestProductDTO) {
    exceptionService.checkForRequestProductDTOError(requestProductDTO);
    Product newProduct = requestProductDTOToProductConvert(requestProductDTO);
    productRepository.save(newProduct);
    return productToResponseProductDTOConvert(newProduct);
  }

  @Override
  public Product requestProductDTOToProductConvert(RequestProductDTO requestProductDTO) {
    return new Product(
        requestProductDTO.getName(),
        requestProductDTO.getPrice(),
        requestProductDTO.getDuration(),
        requestProductDTO.getDescription(),
        Type.valueOf(requestProductDTO.getType()));
  }

  @Override
  public ResponseProductDTO productToResponseProductDTOConvert(Product product) {
    return new ResponseProductDTO(
        product.getId(),
        product.getName(),
        product.getPrice(),
        String.valueOf(product.getDuration()),
        product.getDescription(),
        product.getType().name());
  }
}
