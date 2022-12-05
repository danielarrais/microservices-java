package com.danielarrais.inventoryservice.controller;

import com.danielarrais.inventoryservice.dto.InventoryResponse;
import com.danielarrais.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam("sku-codes") List<String> skuCodes) {
        return inventoryService.isInStock(skuCodes);
    }
}
