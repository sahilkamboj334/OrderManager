package com.inventory.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Distributors")
@NamedQueries({
	@NamedQuery(name = "checkDistExist",query = "select d from Distributors d where d.distributorID=:primaryKey"),
	@NamedQuery(name="deleteDistributor",query="update Distributors d set d.isActive=false where d.distributorID=:distributorID"),
	@NamedQuery(name="activeDist",query="select d from Distributors d where d.isActive=true")
})
public class Distributors {
	@Id
	private String distributorID;
	private String distributorName;
	private String location;
	private boolean isDelivered;
	public boolean isActive;
	private Date deliveredOn;
	private long totalDelivered;

	public String getDistributorID() {
		return distributorID;
	}

	public void setDistributorID(String distributorID) {
		this.distributorID = distributorID;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isDelivered() {
		return isDelivered;
	}

	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	public Date getDeliveredOn() {
		return deliveredOn;
	}

	public void setDeliveredOn(Date deliveredOn) {
		this.deliveredOn = deliveredOn;
	}

	public long getTotalDelivered() {
		return totalDelivered;
	}

	public void setTotalDelivered(long totalDelivered) {
		this.totalDelivered = totalDelivered;
	}

}
