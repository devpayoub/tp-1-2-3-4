package com.gestion_shiop.shop.repository;

import com.gestion_shiop.shop.models.SubCategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
	
    List<SubCategory> findByCategoryId(Long categoryId);

}
