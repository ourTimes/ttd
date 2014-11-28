package com.ttd.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ttd.entity.Message;


@Controller
@RequestMapping(value={"/message"})
public class MessageController {
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(){
		return "/message/show";
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public List<Message> query(){
		List<Message> msgs = new ArrayList<Message>(10);
		Date now = new Date();
		for(int i=0 ; i<10 ; i++){
			Message msg = new Message();
			msg.setDesc("desc"+i);
			msg.setFromAddr("fromAddr"+i);
			msg.setQuote(new BigDecimal(100*i));
			msg.setTitle("title"+i);
			msg.setToAddr("toAddr"+i);
			msg.setSendTime(now);
			msgs.add(msg);
		}
		return msgs;
	}
	
}
