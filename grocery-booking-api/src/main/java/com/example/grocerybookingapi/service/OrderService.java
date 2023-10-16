package com.example.grocerybookingapi.service;

import com.example.grocerybookingapi.entity.GroceryItem;
import com.example.grocerybookingapi.entity.Order;
import com.example.grocerybookingapi.exception.NotFoundException;
import com.example.grocerybookingapi.repository.GroceryItemRepository;
import com.example.grocerybookingapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private GroceryItemRepository groceryItemRepository;
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(List<Long> itemIds) {
        List<GroceryItem> selectedItems = groceryItemRepository.findAllById(itemIds);

        for (GroceryItem item : selectedItems) {
            if (item.getInventory() <= 0) {
                throw new NotFoundException("Selected item is not available: " + item.getName());
            }
        }

        double totalAmount = selectedItems.stream()
                .mapToDouble(GroceryItem::getPrice)
                .sum();

        Order order = new Order();
        order.setItems(selectedItems);
        order.setTotalAmount(totalAmount);
        order.setOrderDate(LocalDateTime.now());

        orderRepository.save(order);

        for (GroceryItem item : selectedItems) {
            int currentInventory = item.getInventory();
            item.setInventory(currentInventory - 1);
            groceryItemRepository.save(item);
        }

        return order;
    }
}
