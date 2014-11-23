package com.ttd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 分级地址
 * @author hanxiaodong
 *
 */
@Entity
@Table(name = "districts")
public class Districts {

	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "parent_id")
	private Integer parentId;
	
	@Column(name = "level")
	private Integer level;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "name")
	private String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
}
