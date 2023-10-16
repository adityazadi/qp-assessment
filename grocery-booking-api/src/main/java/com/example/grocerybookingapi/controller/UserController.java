package com.example.grocerybookingapi.controller;

import com.example.grocerybookingapi.entity.GroceryItem;
import com.example.grocerybookingapi.entity.Order;
import com.example.grocerybookingapi.model.GroceryItemResponse;
import com.example.grocerybookingapi.model.GroceryItemUserResponse;
import com.example.grocerybookingapi.model.OrderItemResponse;
import com.example.grocerybookingapi.model.OrderResponse;
import com.example.grocerybookingapi.service.GroceryItemService;
import com.example.grocerybookingapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/grocery-items")
public class UserController {
    @Autowired
    private GroceryItemService groceryItemService;
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<GroceryItemUserResponse>> viewAvailableGroceryItems() {
        List<GroceryItem> availableItems = groceryItemService.getAvailableGroceryItems();
        List<GroceryItemUserResponse> responses = availableItems.stream()
                .map(item -> new GroceryItemUserResponse(item.getName(), item.getPrice()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/book")
    public ResponseEntity<OrderResponse> bookGroceryItems(@RequestBody List<Long> itemIds) {
        Order order = orderService.createOrder(itemIds);
        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .items(order.getItems().stream().map(i -> OrderItemResponse.builder()
                        .name(i.getName())
                        .price(i.getPrice())
                        .build()).collect(Collectors.toList()))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }
}
