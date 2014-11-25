package com.ttd.controller;

import java.util.ArrayList;
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
	public List<Districts> get(@RequestParam(required = true) Integer parentId,
			@RequestParam(required = true) Integer level){
		//按parentId查询districts
		//页面加载时，默认parentId为0，即查询所有的省份
		//页面上选择省份后，会动态的加载该省对应的所有市，此时本方法的参数是省的id
		List<Districts> districts = new ArrayList<Districts>(10);
		String fix = null;
		if(level==1)fix="省";
		else if(level==2)fix="市";
		else if(level==3)fix="县";
		for(int i=0 ; i<10 ; i++){
			Districts d = new Districts();
			d.setCode("SB" + i);
			d.setId(i+1);
			d.setName("SB" + i + fix);
			districts.add(d);
		}
		return districts;
	}
	
}
