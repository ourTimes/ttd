package com.ttd.enums;

public enum MsgTypeEnum {

	DRIVER("车源信息", 0),
	MERCHANT("货源信息", 1);
	
	private String name;
	private int index;
	
	MsgTypeEnum(String name, int index){
		this.name = name;
		this.index = index;
	}
	
	public String getName(){
		return this.name;
	}
	
	public static MsgTypeEnum getByIndex(int index){
		for(MsgTypeEnum item : MsgTypeEnum.values()){
			if(item.index == index)
				return item;
		}
		return null;
	}
		
}
