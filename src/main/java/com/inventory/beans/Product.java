package com.inventory.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Product")
@NamedQueries({
		@NamedQuery(name = "product_for_manufacture", query = "select p from Product p where p.supplier.primaryKey=:primaryKey and p.isActive=true"),
		@NamedQuery(name = "checkProductExist", query = "select p from Product p where p.productCode=:productCode") })
public class Product {

	public String productName;
	@ManyToOne
	@JsonIgnoreProperties("products")
	public ProductCategory productCategory;
	@Id
	public String productCode;
	public double productCost;
	public double productSellingPrice;
	public String productImage;
	public Date productAddedOnDate;
	public String productColor;
	public boolean isActive;

	@Override
	public String toString() {
		return "Product [productName=" + productName + ", productCategory=" + productCategory + ", productCode="
				+ productCode + ", productCost=" + productCost + ", productSellingPrice=" + productSellingPrice
				+ ", productImage=" + productImage + ", productAddedOnDate=" + productAddedOnDate + "productColor="
				+ productColor + ", manufactures=" + supplier + "]";
	}

	@ManyToOne
	@JsonIgnoreProperties("productsArray")
	public Supplier supplier;

}
