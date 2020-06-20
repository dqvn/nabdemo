package com.nab.cart.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Product sold by the Online store
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String brand;
	private BigDecimal price;
	private String productColor;
	private String productSize;
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getProductColor() {
		return productColor;
	}

	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}

	public String getProductSize() {
		return productSize;
	}

	public void setProductSize(String productSize) {
		this.productSize = productSize;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return String.format("Product [id=%s, name=%s, brand=%s, price=%s, productColor=%s, productSize=%s, status=%s]",
				id, name, brand, price, productColor, productSize, status);
	}

}
