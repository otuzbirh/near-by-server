package com.sb.nearby.service;

import com.sb.nearby.model.Category;
import com.sb.nearby.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories(){
        return this.categoryRepository.findAll();
    }

    public Category getCategoryById(Long categoryId) {
        Optional<Category> optionalCategory = this.categoryRepository.findById(categoryId);
        return optionalCategory.orElse(null);
    }

    public Category addCategory(Category category){
        return this.categoryRepository.save(category);
    }

    public Category updateCategory(Long categoryId, Category updatedCategory) {
        Optional<Category> optionalCategory = this.categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(updatedCategory.getName());
            return this.categoryRepository.save(category);
        } else {
            return null;
        }
    }

    public boolean deleteCategory(Long categoryId) {
        Optional<Category> optionalCategory = this.categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            this.categoryRepository.deleteById(categoryId);
            return true;
        } else {
            return false;
        }
    }
}
