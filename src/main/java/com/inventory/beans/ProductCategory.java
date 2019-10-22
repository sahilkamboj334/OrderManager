package com.inventory.beans;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="pcategory")
@NamedQuery(name = "checkExist",query = "select c from ProductCategory c where c.catCode=:catCode")
@JsonIgnoreProperties({"user"})
public class ProductCategory {
 
	
	public String catName;

	@OneToMany(mappedBy = "productCategory",cascade = CascadeType.PERSIST)
	@JsonIgnoreProperties("productCategory")
	public Set<Product> products;
	@PreRemove
	public void removeSafe() {
		products.forEach(p->p.productCategory=null);
	}
	
	@Id
	public String catCode;
	
	
}
