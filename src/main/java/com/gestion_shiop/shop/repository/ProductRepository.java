package com.gestion_shiop.shop.repository;

import com.gestion_shiop.shop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}

