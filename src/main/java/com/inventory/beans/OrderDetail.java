package com.inventory.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="orderdetail")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long primaryKey;
	@OneToOne
	@JoinColumn(name = "product_pk")
	public Product product;
	
	public int qty;
	public double cost;
	public double price;
	public double profit;
	
	
	@ManyToOne
	@JoinColumn(name="order_pk",nullable = false)
	@JsonIgnoreProperties("orderDetailsArray")
	public Orders order;
	
}
