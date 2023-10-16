package com.example.grocerybookingapi.controller;


import com.example.grocerybookingapi.entity.GroceryItem;
import com.example.grocerybookingapi.model.GroceryItemRequest;
import com.example.grocerybookingapi.model.GroceryItemResponse;
import com.example.grocerybookingapi.service.GroceryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/grocery-items")
public class AdminController {
    @Autowired
    private GroceryItemService groceryItemService;

    @PostMapping
    public ResponseEntity<GroceryItemResponse> addGroceryItem(@RequestBody GroceryItemRequest request) {

        GroceryItem newItem = GroceryItem.builder()
                .name(request.getName())
                .price(request.getPrice())
                .inventory(request.getInventory())
                .build();

        newItem = groceryItemService.addGroceryItem(newItem);
        GroceryItemResponse response = new GroceryItemResponse(newItem.getId(), newItem.getName(), newItem.getPrice(), newItem.getInventory());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<GroceryItemResponse>> viewGroceryItems() {
        List<GroceryItem> items = groceryItemService.getAllGroceryItems();
        List<GroceryItemResponse> responses = items.stream()
                .map(item -> new GroceryItemResponse(item.getId(), item.getName(), item.getPrice(), item.getInventory()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeGroceryItem(@PathVariable Long id) {
        groceryItemService.removeGroceryItem(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroceryItemResponse> updateGroceryItem(@PathVariable Long id, @RequestBody GroceryItemRequest request) {
        GroceryItem groceryItem = GroceryItem.builder()
                .id(id)
                .name(request.getName())
                .price(request.getPrice())
                .build();
        GroceryItem updatedItem = groceryItemService.updateGroceryItem(id, groceryItem);
        GroceryItemResponse response = new GroceryItemResponse(updatedItem.getId(), updatedItem.getName(), updatedItem.getPrice(), updatedItem.getInventory());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/manage-inventory")
    public ResponseEntity<GroceryItemResponse> manageInventory(@PathVariable Long id, @RequestParam int quantity) {
        GroceryItem updatedItem = groceryItemService.manageInventory(id, quantity);
        GroceryItemResponse response = new GroceryItemResponse(updatedItem.getId(), updatedItem.getName(), updatedItem.getPrice(), updatedItem.getInventory());
        return ResponseEntity.ok(response);
    }
}
