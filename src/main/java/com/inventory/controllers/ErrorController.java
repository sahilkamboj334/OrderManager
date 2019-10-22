package com.inventory.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
	@RequestMapping(value = "/error")
	public ModelAndView handleError(HttpServletRequest httpServletRequest) {
		System.out.println("In handle error");
		Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if ((Integer) status == 404) {
			if (httpServletRequest.getSession(false) != null
					&& httpServletRequest.getSession(false).getAttribute("user") != null) {
				return new ModelAndView("/index");
			} else {
				return new ModelAndView("/login");
			}
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg",
				status == null ? "Something went wrong !!!" : "Error-" + Integer.valueOf(status.toString()));
		mv.setViewName("/errorPage");
		return mv;
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
