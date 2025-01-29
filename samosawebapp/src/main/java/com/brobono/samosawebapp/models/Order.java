package com.brobono.samosawebapp.models;

import jakarta.persistence.*;

@Entity
@Table(name="`order`") //Escaped with backticks, don't know why but you're supposed to if using JPA/Hibernate
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // tells Hibernate to let MySQL handle the ID generation
	private Long id;
	
	@Column(name="customer_name")
	private String customerName;
	
	@Column(name="customer_email")
	private String customerEmail;
	
	@Column(name="order_details")
	private String orderDetails;
	
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'NEW'")
    private String status = "NEW"; // Default value when customer creates an order

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
}
