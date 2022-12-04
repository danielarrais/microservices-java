package com.danielarrais.productservice.controller;

import com.danielarrais.productservice.ProductServiceApplication;
import com.danielarrais.productservice.dto.ProductRequest;
import com.danielarrais.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ProductServiceApplication.class})
@AutoConfigureMockMvc
@Testcontainers
public class ProductControllerTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    static void setMongoDBProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        mongoDBContainer.start();
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    public void createProduct_success() throws Exception {
        var productRequest = objectMapper.writeValueAsString(getProductRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequest)
        ).andExpect(MockMvcResultMatchers.status().isCreated());

        assertEquals(productRepository.count(), 1);
    }

    @Test
    public void getAllProducts_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("Galaxy S20+")
                .description("Top de linha Samsung 2020")
                .price(BigDecimal.valueOf(3000))
                .build();
    }
}
