package com.inventory.controllers;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inventory.beans.Distributors;
import com.inventory.beans.Notification;
import com.inventory.beans.Supplier;
import com.inventory.service.DBService;
import com.inventory.util.MessageEnum;

@Controller
public class DistributorController {

	@Autowired
	DBService service;

	@GetMapping(value = "/app/distributors")
	public String distributor() {
		return "/distributor";
	}
	@PostMapping(path="/app/addDistributor",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public Notification add(@RequestBody Distributors distributors) {
		if(distributors!=null && !isExist(distributors)) {
			distributors.isActive=true;
			service.save(distributors);
			return new Notification(MessageEnum.DISTRIBUTOR_ADDED.getMsg(),Notification.NotifyType.SUCCESS.type());
		}else {
			return new Notification(MessageEnum.DUPLICATE_DISTRIBUTOR.getMsg(),Notification.NotifyType.ERROR.type());
		}
	}
	
	@PostMapping(value = "/app/get/distributors",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Distributors> getAll(){
		Session s=service.dbFactory.getSession();
		CriteriaQuery<Distributors> cq=s.getCriteriaBuilder().createQuery(Distributors.class);
		cq.from(Distributors.class);
		return s.createQuery(cq).getResultList();
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/app/get/active/distributors")
	@ResponseBody
	public List<Supplier> getActiveDistributors() {
		Session session = service.dbFactory.getSession();
		return session.createNamedQuery("activeDist").getResultList();
	}

	
	@PostMapping(value="/app/update/distributor",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Notification update(@RequestBody Distributors distributor) {
		if(distributor!=null && isExist(distributor)) {
		service.update(distributor);
		return new Notification(MessageEnum.DISTRIBUTOR_UPDATED.getMsg(),Notification.NotifyType.SUCCESS.type());
		}else {
			return new Notification(MessageEnum.ERROR.getMsg(),Notification.NotifyType.ERROR.type());
		}
	}
	@PostMapping(value="/app/delete/distributor",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Notification delete(@RequestBody Distributors distributor) {
		if(distributor!=null || isExist(distributor)) {
		service.dbFactory.getSession().createNamedQuery("deleteDistributor").setParameter("distributorID", distributor.getDistributorID())
		.executeUpdate();
		return new Notification(MessageEnum.DISTRIBUTOR_DELETED.getMsg(),Notification.NotifyType.SUCCESS.type());
		}else {
			return new Notification(MessageEnum.ALREADY_REMOVED.getMsg(),Notification.NotifyType.ERROR.type());
		}
	}
	private boolean isExist(Distributors dist) {
		Session s=service.dbFactory.getSession();
		Query cq=s.createNamedQuery("checkDistExist");
		cq.setParameter("primaryKey", dist.getDistributorID());
		return cq.getResultList().size()>0;
	}
}

