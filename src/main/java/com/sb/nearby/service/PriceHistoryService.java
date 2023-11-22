package com.sb.nearby.service;

import com.sb.nearby.model.PriceHistory;
import com.sb.nearby.repository.PriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceHistoryService {

    private final PriceHistoryRepository priceHistoryRepository;

    public PriceHistoryService(PriceHistoryRepository priceHistoryRepository) {
        this.priceHistoryRepository = priceHistoryRepository;
    }

    public List<PriceHistory> getPriceHistoryForProduct(Long productId) {

        return priceHistoryRepository.findByProductId(productId);
    }

    public PriceHistory save(PriceHistory priceHistory) {

        return priceHistoryRepository.save(priceHistory);
    }
}
