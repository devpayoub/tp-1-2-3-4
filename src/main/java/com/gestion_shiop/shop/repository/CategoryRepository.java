package com.gestion_shiop.shop.repository;

import com.gestion_shiop.shop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c")
    List<Category> findAllCategoriesWithoutSubcategories();
}
