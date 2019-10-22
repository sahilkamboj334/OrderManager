package com.inventory.util;

public enum MessageEnum {
	
	ORDER_ADDED("Order added succesfully."),
	ORDER_UPDATED("Order updated succesfully."),
	ORDER_DELETED("Order deleted succesfully."),
	DISTRIBUTOR_ADDED("Distributor added succesfully."),
	DISTRIBUTOR_UPDATED("Distributor updated succesfully."),
	DISTRIBUTOR_DELETED("Distributor deleted succesfully."),
	PRODUCT_CAT_ADDED("Product Category added succesfully."),
	PRODUCT_CAT_UPDATED("Product Category updated succesfully."),
	PRODUCT_CAT_DELETED("Product Category deleted succesfully."),
	ERROR("Something went wrong!"),
	ALREADY_REMOVED("Data has been deleted already.| Not Found."),
	PRODUCT_UPDATED("Product updated succesfully."),
	MANUFACTURE_UPDATED("Manufacture updated succesfully."),
	MANUFACTURE_DELETED("Manufacture deleted succesfully."),
	MANUFACTURE_ADD("Manufacture added succesfully."),
	PRODUCT_ADDED("Product added succesfully."),
	DUPLICATE_PRODUCT("Product Already exist with this code."),
	DUPLICATE_DISTRIBUTOR("Distributor Already exists."),
	DUPLICATE_PRODUCT_CAT("Category Already exist with this name.");
	
	private MessageEnum(String msg) {
		this.setMsg(msg);
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	private String msg=null;
	
}
