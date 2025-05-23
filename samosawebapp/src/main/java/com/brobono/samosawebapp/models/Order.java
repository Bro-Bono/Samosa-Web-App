package com.brobono.samosawebapp.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="`order`") //Escaped with backticks, don't know why but you're supposed to if using JPA/Hibernate
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // tells Hibernate to let MySQL handle the ID generation
	private Long id;
	
	@NotBlank(message = "Name is required")
    @Size(min = 2, message = "Name must be at least 2 characters")
	@Column(name="customer_name")
	private String customerName;
	
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
	@Column(name="customer_email")
	private String customerEmail;
	
    @NotBlank(message = "Order details are required")
    @Size(min = 4, message = "Details must be at least 4 characters")
	@Column(name="order_details")
	private String orderDetails;
	
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'NEW'")
    private String status = "NEW"; // Default value when customer creates an order
    

    @Column(name = "ordered_at", nullable = false)
    private LocalDateTime orderedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(String orderDetails) {
		this.orderDetails = orderDetails;
	}
	
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
	public LocalDateTime getOrderedAt() {
		return orderedAt;
	}

	public void setOrderedAt(LocalDateTime orderedAt) {
		this.orderedAt = orderedAt;
	}
    
}
