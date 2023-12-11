package org.gfa.avusfoxticketbackend.dtos;

import org.gfa.avusfoxticketbackend.dtos.abstractdtos.ResponseDto;

import java.util.ArrayList;
import java.util.List;

public class ApiProductsDto extends ResponseDto {

    private List<ProductDto> products;

    public ApiProductsDto() {
        this.products = new ArrayList<>();
    }

    public ApiProductsDto(List<ProductDto> products) {
        this.products = products;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}