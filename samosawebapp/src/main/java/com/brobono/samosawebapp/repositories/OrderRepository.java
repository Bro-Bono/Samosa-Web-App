package com.brobono.samosawebapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.brobono.samosawebapp.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	//runs the following query: SELECT COUNT(*) FROM orders WHERE status = ?
	long countByStatus(String status);
	
	//runs the following query: SELECT * FROM orders WHERE status = ?;
	List<Order> findByStatus(String status); 
}
