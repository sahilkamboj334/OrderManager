package com.inventory.beans;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "supplier")
@NamedQueries({
@NamedQuery(name="checkSuppierExist" ,query = "select s from Supplier s where s.primaryKey=:primaryKey"),
@NamedQuery(name="activeSupplier",query="select s from Supplier s where s.isActive=:isActive"),
@NamedQuery(name="deleteSupplier",query="update Supplier s set s.isActive=false where s.primaryKey=:primaryKey")
})
public class Supplier {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long primaryKey;
	public String name;
	public String address;
	public String phoneNumber;
	public boolean isActive;
	@OneToMany(cascade = CascadeType.REFRESH,mappedBy = "supplier")
	@JsonIgnoreProperties("supplier")
	public Set<Product> productsArray;
	@PreRemove
	public void removeSafe() {
		productsArray.forEach(p->p.supplier=null);
	}
}

