package com.sb.nearby.controller;

import com.sb.nearby.model.PriceHistory;
import com.sb.nearby.service.PriceHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.sb.nearby.util.Constants.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class PriceHistoryController {

    private final PriceHistoryService priceHistoryService;

    public PriceHistoryController(PriceHistoryService priceHistoryService) {
        this.priceHistoryService = priceHistoryService;
    }

    @GetMapping("/price-history/{productId}")
    public ResponseEntity<List<PriceHistory>> getPriceHistoryForProduct(@PathVariable Long productId) {
        List<PriceHistory> priceHistoryList = priceHistoryService.getPriceHistoryForProduct(productId);
        return ResponseEntity.ok(priceHistoryList);
    }
}
