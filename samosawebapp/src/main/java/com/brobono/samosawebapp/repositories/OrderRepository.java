package com.brobono.samosawebapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.brobono.samosawebapp.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	//runs the following query: SELECT COUNT(*) FROM user WHERE status = ?
	long countByStatus(String status); 
}
