package com.inventory.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@GetMapping(value = "/app/dashboard")
	public String dashboard() {
		return "dashboard";
	}
	
	
}
