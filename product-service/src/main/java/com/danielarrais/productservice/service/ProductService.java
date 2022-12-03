package com.danielarrais.productservice.service;

import com.danielarrais.productservice.dto.ProductRequest;
import com.danielarrais.productservice.dto.ProductResponse;
import com.danielarrais.productservice.model.Product;
import com.danielarrais.productservice.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
    }

    public List<ProductResponse> getAllProducts() {
        var products = productRepository.findAll();
        return products.stream().map(this::mapProductToResponse).toList();
    }

    public ProductResponse mapProductToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
