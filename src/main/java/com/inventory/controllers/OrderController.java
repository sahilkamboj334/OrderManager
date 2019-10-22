package com.inventory.controllers;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inventory.beans.Notification;
import com.inventory.beans.Orders;
import com.inventory.service.DBService;
import com.inventory.util.MessageEnum;

@Controller
public class OrderController {

	@Autowired
	DBService dbService;
	@GetMapping("/app/orders")
	public String orderSummaryPage() {
		return "/orders";
	}
	
	@GetMapping("/app/orderPage")
	public String orderAddPage() {
		return "/orderDetail";
	}
	@GetMapping("/app/orderDetail/update")
	public String orderDetailUpdate() {
		return "/orderDetailUpdate";
	}
	
	@PostMapping(value = "/app/orders/add",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Notification addOrder(@RequestBody Orders order) {
		if(order!=null && order.orderPrice>0) {
			order.addOrderDetail(order.orderDetailsArray);
			dbService.save(order);
			return new Notification(MessageEnum.ORDER_ADDED.getMsg(),Notification.NotifyType.SUCCESS.type());
		}else {
			return new Notification(MessageEnum.ERROR.getMsg(),Notification.NotifyType.ERROR.type());
		}
	}
	@PostMapping(value = "/app/get/orders",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Orders> getOrders() {
		Session session=dbService.dbFactory.getSession();
		CriteriaQuery<Orders> criteriaQuery=session.getCriteriaBuilder().createQuery(Orders.class);
		criteriaQuery.from(Orders.class);
		return session.createQuery(criteriaQuery).getResultList();
	}
	@PostMapping(value="/app/order/update",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Notification update(@RequestBody Orders orders) {
		if(orders!=null && orders.primaryKey>0) {
			dbService.update(orders);
			return new Notification(MessageEnum.ORDER_UPDATED.getMsg(),Notification.NotifyType.SUCCESS.type());
		}else {
			return new Notification(MessageEnum.ERROR.getMsg(),Notification.NotifyType.ERROR.type());

		}
	}
	@PostMapping(value="/app/order/delete",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Notification delete(@RequestBody Orders order) {
		if(order!=null && order.primaryKey>0) {
			dbService.delete(order);
			return new Notification(MessageEnum.ORDER_DELETED.getMsg(),Notification.NotifyType.SUCCESS.type());
		}else {
			return new Notification(MessageEnum.ERROR.getMsg(),Notification.NotifyType.ERROR.type());

		}
	}
}
