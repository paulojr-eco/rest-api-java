package com.paulojreco.RestAPI.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paulojreco.RestAPI.models.Product;
import com.paulojreco.RestAPI.services.ProductService;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/products")
public class ProductResource {

  @Autowired
  private ProductService productService;

  public ProductResource(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  @ResponseBody
  public ResponseEntity<?> findAll() {
    List<Product> list = this.productService.findAll();
    return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  @ResponseBody
  public ResponseEntity<?> find(@PathVariable("id") Long id) {                                                        
    Optional<Product> product = this.productService.find(id);
    return new ResponseEntity<Optional<Product>>(product, HttpStatus.OK);
  }

  @PostMapping
  @ResponseBody
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<?> create(@Valid @RequestBody Product product, Errors errors) {
    if (!errors.hasErrors()) {
      Product productCreated = this.productService.create(product);
      return new ResponseEntity<Product>(productCreated, HttpStatus.CREATED);
    }

    return ResponseEntity.badRequest().body(
        errors.getAllErrors()
            .stream()
            .map(msg -> msg.getDefaultMessage())
            .collect(Collectors.joining(",")));
  }

  @PutMapping("/{id}")
  @ResponseBody
  public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody Product product, Errors errors) {
    if (!errors.hasErrors()) {
      return ResponseEntity.ok(this.productService.update(id, product));
    }

    return ResponseEntity.badRequest().body(
        errors.getAllErrors()
            .stream()
            .map(msg -> msg.getDefaultMessage())
            .collect(Collectors.joining(",")));
  }

  @DeleteMapping("/{id}")
  @ResponseBody
  public void delete(@PathVariable("id") Long id, HttpServletResponse response) {
    this.productService.delete(id);
    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
  }

}
