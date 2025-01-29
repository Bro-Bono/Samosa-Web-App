package com.brobono.samosawebapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.brobono.samosawebapp.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
