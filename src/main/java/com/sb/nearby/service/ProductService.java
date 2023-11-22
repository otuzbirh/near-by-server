package com.sb.nearby.service;

import com.sb.nearby.exception.ResourceNotFoundException;
import com.sb.nearby.model.PriceHistory;
import com.sb.nearby.dto.ProductDto;
import com.sb.nearby.model.Product;
import com.sb.nearby.repository.ProductRepository;
import com.sb.nearby.repository.PriceHistoryRepository;
import com.sb.nearby.util.ProductUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;
import java.util.ArrayList;

import static com.sb.nearby.util.Constants.Product.*;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PriceHistoryRepository priceHistoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);


    public ProductService(ProductRepository productRepository, PriceHistoryRepository priceHistoryRepository, PriceHistoryService priceHistoryService) {
        this.productRepository = productRepository;
        this.priceHistoryRepository = priceHistoryRepository;
    }

    public Page<Product> getAllProducts(Long categoryId, String productName, String sortBy, String sortDirection, int page, int size) {
        this.logger.info("Get all products");
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        if ("desc".equalsIgnoreCase(sortDirection)) {
            sort = Sort.by(Sort.Direction.DESC, sortBy);
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        try {
            if (productName != null && categoryId != null) {
                return productRepository.findByNameAndCategory_Id(productName, categoryId, pageable);
            } else if (productName != null) {
                return productRepository.findByName(productName, pageable);
            } else if (categoryId != null) {
                return productRepository.findByCategoryId(categoryId, pageable);
            } else {
                return productRepository.findAll(pageable);
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    public List<Product> findProductsNearby(double latitude, double longitude, double maxDistanceInKm) {
        List<Product> allProducts = productRepository.findAll();
        List<Product> nearbyProducts = new ArrayList<>();

        for (Product product : allProducts) {
            if (product.getLatitude() != null && product.getLongitude() != null) {
                double distance = calculateDistance(latitude, longitude, product.getLatitude(), product.getLongitude());

                if (distance <= maxDistanceInKm) {
                    nearbyProducts.add(product);
                }
            }
        }

        return nearbyProducts;
    }


    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    public Product addProduct(ProductDto productDto) {
        try {
            Product product = new Product();
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            if (productDto.getCategory() != null) {
                product.setCategory(productDto.getCategory());
            }
            product.setLatitude(productDto.getLatitude());
            product.setLongitude(productDto.getLongitude());
            product.setViews(0);
            product.setImage(new ProductUtil().saveFile(productDto.getImage()));
            return this.productRepository.save(product);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    public Product getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            System.out.println(product);
            product.setViews( product.getViews() + 1 );

            productRepository.save( product );

            return product;
        } else {
            return null;
        }
//        return optionalProduct.orElse(null);
    }

    public boolean deleteProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(productId);
            return true;
        } else {
            return false;
        }
    }

    public Product updateProduct(Long productId, ProductDto updatedProductDto) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            try {

                Product existingProduct = optionalProduct.get();

                if (updatedProductDto.getPrice() != null && existingProduct.getPrice() != updatedProductDto.getPrice()) {
                    PriceHistory priceHistory = new PriceHistory();
                    priceHistory.setProduct(existingProduct);
                    priceHistory.setPrice(existingProduct.getPrice());
                    priceHistory.setTimestamp(new Timestamp(System.currentTimeMillis()));
                    priceHistoryRepository.save(priceHistory);
                    existingProduct.setPrice(updatedProductDto.getPrice());
                }

                if (updatedProductDto.getName() != null) {
                    existingProduct.setName(updatedProductDto.getName());
                }
                if (updatedProductDto.getDescription() != null) {
                    existingProduct.setDescription(updatedProductDto.getDescription());
                }
                if (updatedProductDto.getCategory() != null) {
                    existingProduct.setCategory(updatedProductDto.getCategory());
                }
                if (updatedProductDto.getLatitude() != null) {
                    existingProduct.setLatitude(updatedProductDto.getLatitude());
                }
                if (updatedProductDto.getLongitude() != null) {
                    existingProduct.setLongitude(updatedProductDto.getLongitude());
                }
                if (updatedProductDto.getImage() != null) {
                    existingProduct.setImage(new ProductUtil().saveFile(updatedProductDto.getImage()));
                }

                return productRepository.save(existingProduct);

            } catch (Exception e) {
                throw new ResourceNotFoundException(e.getMessage());
            }
        }

        throw new ResourceNotFoundException("There is no product with id:" + productId);

    }



}
