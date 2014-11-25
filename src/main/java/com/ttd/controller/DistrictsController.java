package com.ttd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ttd.dao.impl.DistrictsDaoImpl;
import com.ttd.entity.Districts;

@Controller
@RequestMapping(value={"/districts"})
public class DistrictsController {

	@Autowired
	private DistrictsDaoImpl quer;
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public List<Districts> get(@RequestParam(required = true) Integer parentId){
		//按parentId查询districts
		//页面加载时，默认parentId为0，即查询所有的省份
		//页面上选择省份后，会动态的加载该省对应的所有市，此时本方法的参数是省的id
        return quer.getBy(parentId);
		//return null;
	}
	
}
