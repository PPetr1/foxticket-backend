package org.gfa.avusfoxticketbackend.models;

import jakarta.persistence.*;
import org.gfa.avusfoxticketbackend.enums.Type;

import java.util.Objects;

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

  @Enumerated(EnumType.STRING)
  private Type type;

  public Product() {}

  public Product(String name, Double price, Integer duration, String description, Type type) {
    this.name = name;
    this.price = price;
    this.duration = duration;
    this.description = description;
    this.type = type;
  }

  public Product(
      Long id, String name, Double price, Integer duration, String description, Type type) {
    this.name = name;
    this.id = id;
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

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Product product = (Product) obj;
    return Objects.equals(name, product.name) &&
            Objects.equals(price, product.price) &&
            Objects.equals(duration, product.duration) &&
            Objects.equals(description, product.description) &&
            type == product.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, price, duration, description, type);
  }
}
