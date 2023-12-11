package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;
import org.gfa.avusfoxticketbackend.dtos.ProductDto;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Integer duration;
    private String description;
    private String type;

    public Product() {
    }

    public Product(String name, Double price, Integer duration, String description, String type) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.description = description;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ProductDto toProductDto() {
        return new ProductDto(
                this.id,
                this.name,
                this.price,
                this.duration,
                this.description,
                this.type
        );
    }
}