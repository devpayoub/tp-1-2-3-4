package com.gestion_shiop.shop.repository;

import com.gestion_shiop.shop.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {}
