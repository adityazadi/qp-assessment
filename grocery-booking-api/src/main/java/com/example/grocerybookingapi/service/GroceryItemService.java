package com.example.grocerybookingapi.service;

import com.example.grocerybookingapi.entity.GroceryItem;
import com.example.grocerybookingapi.exception.NotFoundException;
import com.example.grocerybookingapi.repository.GroceryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroceryItemService {
    @Autowired
    private GroceryItemRepository groceryItemRepository;

    public GroceryItem addGroceryItem(GroceryItem groceryItem) {
        return groceryItemRepository.save(groceryItem);
    }

    public List<GroceryItem> getAllGroceryItems() {
        return groceryItemRepository.findAll();
    }

    public void removeGroceryItem(Long id) {
        groceryItemRepository.deleteById(id);
    }

    public GroceryItem updateGroceryItem(Long id, GroceryItem updatedItem) {
        GroceryItem existingItem = groceryItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grocery item not found"));

        existingItem.setName(updatedItem.getName());
        existingItem.setPrice(updatedItem.getPrice());

        return groceryItemRepository.save(existingItem);
    }

    public GroceryItem manageInventory(Long id, int quantity) {
        GroceryItem existingItem = groceryItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Grocery item not found"));

        existingItem.setInventory(existingItem.getInventory() + quantity);

        return groceryItemRepository.save(existingItem);
    }

    public List<GroceryItem> getAvailableGroceryItems() {
        return groceryItemRepository.findByInventoryGreaterThan(0);
    }

}
