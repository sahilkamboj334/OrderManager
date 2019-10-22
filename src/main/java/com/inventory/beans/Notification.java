package com.inventory.beans;

import java.util.Date;

public class Notification {

	private String message;
	private String type;
	private Date date;
	public long authUserPk;
	
	public Notification setUser(AuthUser authUser) {
		this.authUserPk=authUser.phoneNumber;
		return this;
	}
	public long getAuthUserPk(){
		return this.authUserPk;
	}
	public Notification(String message,String type) {
		this.message=message;
		this.type=type;
		this.date=new Date();
	}
	public String getMessage() {
		return message;
	}
	public String getType() {
		return type;
	}
	public Date getTimestamp() {
		return date;
	}
	public static enum NotifyType{
		WARNING("warning"),SUCCESS("success"),INFO("info"),ERROR("error");
		private String type;
		private NotifyType(String type) {
			this.type=type;
		}
		public String type() {
			return type;
		}
	}
}
