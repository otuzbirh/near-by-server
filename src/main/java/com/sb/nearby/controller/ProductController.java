package com.sb.nearby.controller;

import com.sb.nearby.dto.ProductDto;
import com.sb.nearby.model.Product;
import com.sb.nearby.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sb.nearby.util.Constants.BASE_URL;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(BASE_URL)
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;

    }


    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findAllProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(this.productService.getAllProducts(categoryId, productName, sortBy, sortDirection, page, size));
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<Product>> findNearbyProducts(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("maxDistanceInKm") Double maxDistanceInKm) {

        if (latitude == null || longitude == null || maxDistanceInKm == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Product> nearbyProducts = productService.findProductsNearby(latitude, longitude, maxDistanceInKm);
        return ResponseEntity.ok(nearbyProducts);
    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> findProductById(@PathVariable Long productId) {
        Product product = this.productService.getProductById(productId);
        return ResponseEntity.ok(product);

    }

    @PostMapping("/create-product")
    public ResponseEntity<Product> addProduct(@ModelAttribute ProductDto product) {
        return ResponseEntity.ok(this.productService.addProduct(product));
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean deleted = this.productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();


    }

    @PatchMapping("/product/{productId}")
    public ResponseEntity<Product> updateProduct(@ModelAttribute ProductDto updatedProductDto, @PathVariable Long productId) {
        Product updatedProduct = productService.updateProduct(productId, updatedProductDto);
        return ResponseEntity.ok(updatedProduct);
    }


}
