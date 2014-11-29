package com.ttd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value={"/login"})
public class LoginController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String login(){
		return "/login/login";
	}	

	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public void doLogin(){
		
	}
	
}
