package com.inventory.controllers;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inventory.beans.AuthUser;
import com.inventory.beans.Notification;
import com.inventory.service.DBService;

@Controller
public class AuthController {

	@Autowired
	HttpSession httpSession;
	@Autowired
	DBService service;

	@RequestMapping(value = { "/", "/home" })
	public String login() {
		if (loggedIn()) {
			return "index";
		}
		return "login";
	}

	@GetMapping("/signup-page")
	public String signUpPage() {
		return "signup";
	}

	@GetMapping("/signin-page")
	public String loginPage() {
		return "signin";
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/register")
	@ResponseBody
	public Notification register(@RequestBody AuthUser authUser) {
		if (authUser != null && exist(authUser) == null) {
			service.save(authUser);
			return new Notification("User registered successfully!", Notification.NotifyType.SUCCESS.type());
		} else {
			return new Notification("User Already Exist with this Detail!", Notification.NotifyType.ERROR.type());
		}
	}

	private boolean loggedIn() {
		if (httpSession != null && (AuthUser) httpSession.getAttribute("user") != null) {
			return true;
		} else {
			return false;
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/validate")
	@ResponseBody
	public Notification validateLogin(@RequestBody AuthUser authUser) {
		AuthUser user = exist(authUser);
		if (user != null) {
			httpSession.setAttribute("user", user);
			httpSession.setMaxInactiveInterval(600);
			return new Notification("true", Notification.NotifyType.SUCCESS.type()).setUser(user);
		} else {
			return new Notification("No User Exist with given info.", Notification.NotifyType.ERROR.type());
		}
	}

	private AuthUser exist(AuthUser authUser) {
		if(authUser==null) {
			return null;
		}
		Session dbSesssion = service.dbFactory.getSession();
		java.util.List<?> result= dbSesssion.createNamedQuery("validateUser").setParameter("phoneNumber", authUser.phoneNumber)
				.setParameter("password", authUser.password).getResultList();
		if (result.size() > 0)
			return (AuthUser)result.get(0);
		else
			return null;
	}

	@PostMapping(value = "/app/logout", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String logout() {
		httpSession.setAttribute("user", null);
		return "login";
	}

}
