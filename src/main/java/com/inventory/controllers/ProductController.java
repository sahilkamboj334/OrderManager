package com.inventory.controllers;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.inventory.beans.Notification;
import com.inventory.beans.Product;
import com.inventory.beans.Supplier;
import com.inventory.service.DBService;
import com.inventory.util.FileUtil;
import com.inventory.util.MessageEnum;

@Controller
public class ProductController {
	@Autowired
	DBService service;

	@RequestMapping(path = { "/app/home"})
	public ModelAndView homepage() {
		ModelAndView view = new ModelAndView();
		view.setViewName("index");
		return view;
	}

	@RequestMapping(path = { "/app/products" })
	public ModelAndView products() {
		ModelAndView view = new ModelAndView();
		view.setViewName("products");
		return view;
	}

	@RequestMapping(path = { "/app/manufacture" })
	public ModelAndView manufactures() {
		ModelAndView view = new ModelAndView();
		view.setViewName("manufactures");
		return view;
	}

	public String saveImage(String base64, String name) {
		return FileUtil.writeImageToLocal(base64, name);
	}

	@PostMapping(value = "/app/addManufacture", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Notification addManufcature(@RequestBody Supplier manufacture) {
		if (manufacture != null) {
			manufacture.isActive=true;
			service.save(manufacture);
			return new Notification(MessageEnum.MANUFACTURE_ADD.getMsg(), Notification.NotifyType.SUCCESS.type());
		} else {
			return new Notification(MessageEnum.ERROR.getMsg(), Notification.NotifyType.ERROR.type());
		}
	}

	private boolean IsExistManufacture(Supplier manufacture) {
		Session s = service.dbFactory.getSession();
		Query cq = s.createNamedQuery("checkSuppierExist");
		cq.setParameter("primaryKey", manufacture.primaryKey);
		return cq.getResultList().size() > 0;
	}
	
	//will return all supppliers with status active or not
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/app/get/manufactures")
	@ResponseBody
	public List<Supplier> getManufactures() {
		Session session = service.dbFactory.getSession();
		CriteriaQuery<Supplier> criteriaQuery = session.getCriteriaBuilder().createQuery(Supplier.class);
		criteriaQuery.from(Supplier.class);
		return session.createQuery(criteriaQuery).getResultList();
	}
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/app/get/active/manufactures")
	@ResponseBody
	public List<Supplier> getActiveManufactures() {
		Session session = service.dbFactory.getSession();
		return session.createNamedQuery("activeSupplier").setParameter("isActive", true).getResultList();
	}

	@PostMapping(value = "/app/update/manufacture", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Notification update(@RequestBody Supplier manufacture) {
		if (manufacture != null && IsExistManufacture(manufacture)) {
			service.update(manufacture);
			return new Notification(MessageEnum.MANUFACTURE_UPDATED.getMsg(), Notification.NotifyType.SUCCESS.type());
		} else {
			return new Notification(MessageEnum.ERROR.getMsg(), Notification.NotifyType.ERROR.type());
		}

	}

	@PostMapping(value = "/app/delete/manufacture", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Notification deleteManufacture(@RequestBody Supplier manufacture) {
		if (manufacture != null && IsExistManufacture(manufacture)) {
			Session session = service.dbFactory.getSession();
			try {
			session.beginTransaction();
			session.createNamedQuery("deleteSupplier").setParameter("primaryKey", manufacture.primaryKey).executeUpdate();
			session.getTransaction().commit();
			}catch(Exception e) {
				return  new Notification(e.getMessage(), Notification.NotifyType.ERROR.type());
			}
			return new Notification(MessageEnum.MANUFACTURE_DELETED.getMsg(), Notification.NotifyType.SUCCESS.type());
		} else {
			return new Notification(MessageEnum.ERROR.getMsg(), Notification.NotifyType.ERROR.type());
		}

	}

	@PostMapping(value = "/app/addProduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Notification addProduct(@RequestBody Product product) {
		if (product != null && !isProductExist(product)) {
			product.isActive=true;
			product.productImage = saveImage(product.productImage, product.productName + "_" + product.productCode);
			product.productAddedOnDate = new Date();
			service.save(product);
			return new Notification(MessageEnum.PRODUCT_ADDED.getMsg(), Notification.NotifyType.SUCCESS.type());
		} else {
			return new Notification(MessageEnum.DUPLICATE_PRODUCT.getMsg(), Notification.NotifyType.ERROR.type());
		}
	}

	private boolean isProductExist(Product product) {
		Session s = service.dbFactory.getSession();
		Query cq = s.createNamedQuery("checkProductExist");
		cq.setParameter("productCode", product.productCode);
		return cq.getResultList().size() > 0;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/app/get/products/{manufactureID}")
	@ResponseBody
	public List<Product> getAssociatedProducts(@PathVariable("manufactureID") long manufactureID) {
		Session session = service.dbFactory.getSession();
		Query query = session.createNamedQuery("product_for_manufacture");
		query.setParameter("primaryKey", manufactureID);
		return query.getResultList();

	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/app/get/products")
	@ResponseBody
	public List<Product> getProducts() {
		Session session = service.dbFactory.getSession();
		CriteriaQuery<Product> criteriaQuery = session.getCriteriaBuilder().createQuery(Product.class);
		criteriaQuery.from(Product.class);
		return session.createQuery(criteriaQuery).getResultList();
	}

	@PostMapping(value = "/app/update/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Notification update(@RequestBody Product pro) {
		if (isProductExist(pro)) {
			service.update(pro);
			return new Notification(MessageEnum.PRODUCT_UPDATED.getMsg(), Notification.NotifyType.SUCCESS.type());
		} else {
			return new Notification(MessageEnum.ERROR.getMsg(), Notification.NotifyType.ERROR.type());

		}
	}

	@PostMapping(value = "/app/update/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public void updateBulk(@RequestBody List<Product> pros) {
		pros.stream().forEach(service::update);
	}

}
