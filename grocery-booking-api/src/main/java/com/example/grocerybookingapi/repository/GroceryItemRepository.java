package com.example.grocerybookingapi.repository;

import com.example.grocerybookingapi.entity.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {
    List<GroceryItem> findByInventoryGreaterThan(int inventory);
}
