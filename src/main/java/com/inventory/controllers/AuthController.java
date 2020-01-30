package com.inventory.controllers;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
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
import com.inventory.util.HashUtil;

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
		if (authUser != null && !exist(authUser)) {
			String key=HashUtil.secureHash(authUser.password);
			authUser.password=key;
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
		AuthUser user = validateUser(authUser);
		if (user != null) {
			httpSession.setAttribute("user", user);
			httpSession.setMaxInactiveInterval(600);
			return new Notification("true", Notification.NotifyType.SUCCESS.type());
		} else {
			return new Notification("No User Exist with given info.", Notification.NotifyType.ERROR.type());
		}
	}
	private boolean exist(AuthUser authUser) {
		if(authUser==null) {
			return false;
		}
		Session dbSesssion = service.dbFactory.getSession();
		Criteria criteria=dbSesssion.createCriteria(AuthUser.class);
		criteria.add(Restrictions.eq("name", authUser.name));
		java.util.List<?> result= criteria.list();
		return result.size()>0;
	}
	private AuthUser validateUser(AuthUser authUser) {
		if(authUser==null) {
			return null;
		}
		Session dbSesssion = service.dbFactory.getSession();
		java.util.List<?> result= dbSesssion.createNamedQuery("validateUser").setParameter("phoneNumber", authUser.phoneNumber)
				.setParameter("password", HashUtil.secureHash(authUser.password)).getResultList();
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
