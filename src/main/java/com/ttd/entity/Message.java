package com.ttd.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 消息对象
 * @author hanxiaodong
 *
 */
@Entity
@Table(name = "message")
public class Message implements Serializable {

	private static final long serialVersionUID = 4218661933898916695L;
	
	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "msg_type")
	private Integer msgType;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "desc")
	private String desc;
	
	@Column(name = "from_province")
	private Integer fromProvince;
	
	@Column(name = "from_city")
	private Integer fromCity;
	
	@Column(name = "from_addr")
	private String fromAddr;
	
	@Column(name = "to_province")
	private Integer toProvince;
	
	@Column(name = "to_city")
	private Integer toCity;
	
	@Column(name = "to_addr")
	private String toAddr;
	
	@Column(name = "quote")
	private BigDecimal quote;

	@Column(name = "send_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date sendTime;
	
	@Column(name = "arrive_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date arriveTime;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Integer getFromProvince() {
		return fromProvince;
	}

	public void setFromProvince(Integer fromProvince) {
		this.fromProvince = fromProvince;
	}

	public Integer getFromCity() {
		return fromCity;
	}

	public void setFromCity(Integer fromCity) {
		this.fromCity = fromCity;
	}

	public Integer getToProvince() {
		return toProvince;
	}

	public void setToProvince(Integer toProvince) {
		this.toProvince = toProvince;
	}

	public Integer getToCity() {
		return toCity;
	}

	public void setToCity(Integer toCity) {
		this.toCity = toCity;
	}
}
