package com.example.grocerybookingapi.repository;

import com.example.grocerybookingapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
