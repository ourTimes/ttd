package com.ttd.controller;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ttd.entity.User;
import com.ttd.service.impl.RegisterServiceImpl;
import com.ttd.web.model.CityModel;
import com.ttd.web.model.ProvinceModel;

@Controller
@RequestMapping(value={"/register"})
public class RegisterController {

	@Autowired
	private RegisterServiceImpl register;
	/**
	 * 请求注册页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/apply"}, method=RequestMethod.GET)
	public String apply(Model model){
		return "/register/apply";
	}
	
	/**
	 * 提交注册信息
	 */
	@SuppressWarnings("null")
	@RequestMapping(value={"/submit"}, method=RequestMethod.POST)
	public String submit(Model model, HttpServletResponse response,  HttpServletRequest request){
		try{
			User user = null;
			//Map<String, String[]> map = request.getParameterMap();
			String username = request.getParameter("username");
			String loginname = request.getParameter("loginName");
			String password = request.getParameter("password");
			String mobile = request.getParameter("mobile");
//			String 
			Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]{6,15}$");
			Pattern patternmob = Pattern.compile("^\\d{11}$");
			Matcher matcher = pattern.matcher(loginname.trim());
			if(matcher.find()){
				user.setPassword(loginname.trim());
			}
			matcher = pattern.matcher(password.trim());
			if(matcher.find()){
				user.setPassword(password.trim());
			}
			matcher = patternmob.matcher(mobile.trim());
			if(matcher.find()){
				user.setPassword(mobile.trim());
			}
			user.setUserName(username.trim());			
			//TODO
			register.add(user);
		}catch(Exception e){
//			logger.error("注册失败。"+ie.getMessage());
			model.addAttribute("error", e.getMessage());
			return "/register/apply";
		}
		return "/register/success";
	}
	
	/**
	 * 注册成功跳转
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"success"}, method=RequestMethod.GET)
	public String success(Model model){
		return "/register/success";
	}
	
	/**
	 * 获取省份列表
	 * @return
	 */
	@RequestMapping(value={"provinces"}, method=RequestMethod.GET)
	public @ResponseBody List<ProvinceModel> provinces(){
		return null;
	}
	
	/**
	 * 获取城市列表
	 * @return
	 */
	@RequestMapping(value={"cities"}, method=RequestMethod.GET)
	public @ResponseBody List<CityModel> cities(@RequestParam(required = false) final String provinceCode){
		return null;
	}
	
}
