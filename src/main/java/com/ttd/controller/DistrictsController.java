package com.ttd.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ttd.entity.Districts;

@Controller
@RequestMapping(value={"/districts"})
public class DistrictsController {


	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public List<Districts> get(@RequestParam Integer districtLevel, @RequestParam Integer parentId){
		
		return null;
	}
	
}
