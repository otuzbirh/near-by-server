package com.sb.nearby.controller;

import com.sb.nearby.model.Category;
import com.sb.nearby.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sb.nearby.util.Constants.BASE_URL;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(BASE_URL)
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(this.categoryService.getAllCategories());
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
        Category category = this.categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    @PostMapping("/create-category")
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        return ResponseEntity.ok(this.categoryService.addCategory(category));
    }

    @PatchMapping("/category/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody Category updatedCategory) {
        Category category = this.categoryService.updateCategory(categoryId, updatedCategory);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        boolean deleted = this.categoryService.deleteCategory(categoryId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
