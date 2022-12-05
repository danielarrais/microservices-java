package com.danielarrais.inventoryservice.service;

import com.danielarrais.inventoryservice.dto.InventoryResponse;
import com.danielarrais.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes) {
        return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
                .map(inventory -> InventoryResponse.builder()
                        .inStock(inventory.getQuantity() > 0)
                        .skuCode(inventory.getSkuCode())
                        .build())
                .toList();
    }
}
