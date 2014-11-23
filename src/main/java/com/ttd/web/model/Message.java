package com.ttd.web.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Message implements Serializable{
		private static final long serialVersionUID = 1L;
		
		private String title;
		private String desc;
		private String fromAddr;
		private String toAddr;
		private BigDecimal quote;
		
		@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd" )
		private Date sendTime;
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public String getFromAddr() {
			return fromAddr;
		}
		public void setFromAddr(String fromAddr) {
			this.fromAddr = fromAddr;
		}
		public String getToAddr() {
			return toAddr;
		}
		public void setToAddr(String toAddr) {
			this.toAddr = toAddr;
		}
		public BigDecimal getQuote() {
			return quote;
		}
		public void setQuote(BigDecimal quote) {
			this.quote = quote;
		}
		public Date getSendTime() {
			return sendTime;
		}
		public void setSendTime(Date sendTime) {
			this.sendTime = sendTime;
		}
	}
