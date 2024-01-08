package com.paulojreco.RestAPI.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "Name cannot be empty")
  @NotBlank(message = "Name cannot be blank")
  @Size(min = 4, max = 255, message = "Name must be between 4 and 255 characters")
  private String name;

  @NotNull(message = "Qtd cannot be empty")
  @Min(value = 0, message = "Qtd must be greater than or equal to 0")
  private Integer qtd;

  private Date createdAt;

  public Product() {
  }

  public Product(String name, Integer qtd) {
    this.name = name;
    this.qtd = qtd;
  }

  @PrePersist
  public void onPrePresist() {
    if (this.createdAt == null) {
      this.createdAt = new Date();
    }
  }

  @Override
  public String toString() {
    return "{\n id: " + this.id + ",\n name: " + this.name + ",\n qtd: " + this.qtd + ",\n createdAt: " + this.createdAt
        + "\n}";
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

  public Integer getQtd() {
    return qtd;
  }

  public void setQtd(Integer qtd) {
    this.qtd = qtd;
  }

  public Date getDateCreated() {
    return createdAt;
  }

  public void setDateCreated(Date createdAt) {
    this.createdAt = createdAt;
  }

}
