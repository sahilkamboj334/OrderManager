package com.inventory.controllers;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.inventory.beans.AuthUser;
import com.inventory.beans.Notification;
import com.inventory.beans.ProductCategory;
import com.inventory.service.DBService;
import com.inventory.util.MessageEnum;

@Controller
public class CategoryController {

	@Autowired
	DBService service;

	@RequestMapping(path = { "/app/productcat" })
	public ModelAndView productCat() {
		ModelAndView view = new ModelAndView();
		view.setViewName("productcat");
		return view;
	}

	@PostMapping(value = "/app/addCategory", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Notification addCategory(@RequestBody(required = true) ProductCategory productCategory) {
		if (productCategory != null && !isExist(productCategory)) {
			service.save(productCategory);
			return new Notification(MessageEnum.PRODUCT_CAT_ADDED.getMsg(), Notification.NotifyType.SUCCESS.type());
		} else {
			return new Notification(MessageEnum.DUPLICATE_PRODUCT_CAT.getMsg(), Notification.NotifyType.ERROR.type());
		}

	}

	@PostMapping(value = "/app/update/category", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Notification update(@RequestBody ProductCategory category) {
		if (category != null && isExist(category)) {
			service.update(category);
			return new Notification(MessageEnum.PRODUCT_CAT_UPDATED.getMsg(), Notification.NotifyType.SUCCESS.type());
		} else {
			return new Notification(MessageEnum.ERROR.getMsg(), Notification.NotifyType.ERROR.type());
		}
	}

	@PostMapping(value = "/app/delete/category", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Notification delete(@RequestBody ProductCategory category) {
		if (category != null || isExist(category)) {
			System.out.println(category.toString());
			service.delete(category);
			return new Notification(MessageEnum.PRODUCT_CAT_DELETED.getMsg(), Notification.NotifyType.SUCCESS.type());
		} else {
			return new Notification(MessageEnum.ALREADY_REMOVED.getMsg(), Notification.NotifyType.SUCCESS.type());
		}
	}

	private boolean isExist(ProductCategory category) {
		Session s = service.dbFactory.getSession();
		Query cq = s.createNamedQuery("checkExist");
		cq.setParameter("catCode", category.catCode);
		return cq.getResultList().size() > 0;
	}

	@PostMapping(value = "/app/categories", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<ProductCategory> getCategories() {
		Session s = service.dbFactory.getSession();

		CriteriaQuery<ProductCategory> cq = s.getCriteriaBuilder().createQuery(ProductCategory.class);
		cq.from(ProductCategory.class);
		return s.createQuery(cq).getResultList();

	}
}
