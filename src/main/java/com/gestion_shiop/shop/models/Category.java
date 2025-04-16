package com.gestion_shiop.shop.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; 
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    private List<SubCategory> subCategories;

    public void setName(String name) {
        if (!name.equalsIgnoreCase("goods") && !name.equalsIgnoreCase("service")) {
            throw new IllegalArgumentException("Category must be 'Goods' or 'Service'");
        }
        this.name = name.toLowerCase();
    }

    public String getName() { return name; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public List<SubCategory> getSubCategories() { return subCategories; }
    public void setSubCategories(List<SubCategory> subCategories) { this.subCategories = subCategories; }
}
