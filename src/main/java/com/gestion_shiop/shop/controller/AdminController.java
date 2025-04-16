package com.gestion_shiop.shop.controller;

import com.gestion_shiop.shop.models.*;
import com.gestion_shiop.shop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth/admin")
public class AdminController {
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private SubCategoryRepository subCategoryRepo;
    @Autowired
    private ProductRepository productRepo;

    @PostMapping("/categories")
    public Category addCategory(@RequestBody Category category) {
        return categoryRepo.save(category);
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryRepo.findAllCategoriesWithoutSubcategories();
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryRepo.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        return categoryRepo.findById(id)
                .map(category -> {
                    category.setName(updatedCategory.getName());
                    return ResponseEntity.ok(categoryRepo.save(category));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/subcategories")
    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepo.findAll();
    }

    @GetMapping("/subcategories/{id}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable Long id) {
        return subCategoryRepo.findById(id)
                .map(subCategory -> ResponseEntity.ok(subCategory))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<SubCategory> addSubCategoryToCategory(@RequestBody Map<String, Object> body) {
        Long categoryId = Long.parseLong(body.get("categoryId").toString()); // Category ID from request body
        String name = body.get("name").toString(); // Subcategory name from request body

        // Find the category by its ID
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Create a new subcategory
        SubCategory subCategory = new SubCategory();
        subCategory.setName(name);
        subCategory.setCategory(category); // Set the category to link the subcategory

        // Save the subcategory and return the response
        SubCategory savedSubCategory = subCategoryRepo.save(subCategory);
        return ResponseEntity.ok(savedSubCategory);
    }


    @PutMapping("/subcategories/{id}")
    public ResponseEntity<SubCategory> updateSubCategory(@PathVariable Long id, @RequestBody SubCategory updatedSubCategory) {
        return subCategoryRepo.findById(id)
                .map(sub -> {
                    sub.setName(updatedSubCategory.getName());
                    sub.setCategory(updatedSubCategory.getCategory()); // Update the category if needed
                    return ResponseEntity.ok(subCategoryRepo.save(sub));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (!categoryRepo.existsById(id)) return ResponseEntity.notFound().build();
        categoryRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/subcategories")
    public SubCategory addSubCategory(@RequestBody Map<String, Object> body) {
        Long categoryId = Long.parseLong(body.get("categoryId").toString());
        String name = body.get("name").toString();

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        SubCategory sub = new SubCategory();
        sub.setName(name);
        sub.setCategory(category);
        return subCategoryRepo.save(sub);
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Map<String, Object> body) {
        Long subCategoryId = Long.parseLong(body.get("subCategoryId").toString());
        String name = body.get("name").toString();
        String description = body.get("description").toString();
        double price = Double.parseDouble(body.get("price").toString());

        SubCategory sub = subCategoryRepo.findById(subCategoryId)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setSubCategory(sub);
        return productRepo.save(product);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepo.findById(id)
                .map(product -> ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updated) {
        return productRepo.findById(id)
                .map(prod -> {
                    prod.setName(updated.getName());
                    prod.setDescription(updated.getDescription());
                    prod.setPrice(updated.getPrice());
                    prod.setSubCategory(updated.getSubCategory());
                    return ResponseEntity.ok(productRepo.save(prod));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productRepo.existsById(id)) return ResponseEntity.notFound().build();
        productRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
