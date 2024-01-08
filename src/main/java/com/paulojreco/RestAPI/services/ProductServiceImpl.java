package com.paulojreco.RestAPI.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paulojreco.RestAPI.models.Product;
import com.paulojreco.RestAPI.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public Product create(Product product) {
    return this.productRepository.save(product);
  }

  @Override
  public void delete(Long id) {
    Optional<Product> product = this.productRepository.findById(id);
    if (product.isPresent()) {
      this.productRepository.delete(product.get());
    }   
  }

  @Override
  public Optional<Product> find(Long id) {
    return this.productRepository.findById(id);
  }

  @Override
  public List<Product> findAll() {
    return this.productRepository.findAll();
  }

  @Override
  public Product update(Long id, Product product) {
    Optional<Product> productExists = this.productRepository.findById(id);
    if (productExists.isPresent()) {
      product.setId(productExists.get().getId());
      return this.productRepository.save(product);
    }
    return null;
  }
  
}
