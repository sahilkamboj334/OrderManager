package com.inventory.beans;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "auth_user")
@NamedQuery(name = "validateUser", query = "select u from AuthUser u where u.phoneNumber=:phoneNumber and u.password=:password")
public class AuthUser {
	@Column(nullable = false)
	public String name;
	@Id
	@Column(nullable = false, length = 10)
	public long phoneNumber;
	public String emailID;
	@Column(nullable = false)
	public String password;

	@Override
	public String toString() {
		return "AuthUser [name=" + name + ", phoneNumber=" + phoneNumber + ", emailID=" + emailID + ", password="
				+ password + "]";
	}

	
}
