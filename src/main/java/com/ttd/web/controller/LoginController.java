package com.ttd.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ttd.web.model.LoginForm;

@Controller
public class LoginController {

	@RequestMapping(value="login")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response, LoginForm command){
		String username = command.getUsername();
		String password = command.getPassword();
		ModelAndView mv = new ModelAndView("/login/success", "command", "LOGIN SUCCESS, username=" + username + ", password=" + password);
		return mv;
	}
	
}
