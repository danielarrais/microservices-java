package com.danielarrais.inventoryservice;

import com.danielarrais.inventoryservice.model.Inventory;
import com.danielarrais.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadInitialData(InventoryRepository inventoryRepository) {
        return args -> {
            Inventory inventory1 = Inventory.builder()
                    .skuCode("galaxy_20")
                    .quantity(10)
                    .build();

            Inventory inventory2 = Inventory.builder()
                    .skuCode("galaxy_22")
                    .quantity(0)
                    .build();

            inventoryRepository.save(inventory1);
            inventoryRepository.save(inventory2);
        };
    }

}
