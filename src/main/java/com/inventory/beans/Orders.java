package com.inventory.beans;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="orders")
public class Orders {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
public long primaryKey;
public double orderCost;
@Column(name="payment_status")
public boolean isPaymentDone;
public double orderPrice;
public double orderProfit;
public double orderDeliveryCharge;
public String orderStatus;
public Date orderDate;
@ManyToOne
public Distributors distributor;


@OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
public Set<OrderDetail> orderDetailsArray;

public void addOrderDetail(Set<OrderDetail> orderDetailsArray) {
	Iterator<OrderDetail> itr=orderDetailsArray.iterator();
	while(itr.hasNext()) {
		OrderDetail od=itr.next();
		od.order=this;
		this.orderDetailsArray.add(od);
	}
}
}
