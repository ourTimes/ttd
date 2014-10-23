/*
 * Class: Constant
 * Description:静态常量类，用于存储静态常量
 * Version: 1.0
 * Author: Vinda
 * Creation date: 2013-6-27
 * (C) Copyright IBM Corporation 2013. All rights reserved.
 */
package com.ttd.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vinda
 * 
 */
public abstract class Constant {
	/**
	 * 默认返回消息对象的名称
	 */
	public static final String PARAM_RESPO_MESSAGE = "message";
	/**
	 * 主数据代码
	 */
	public static final String METADATA_TYPE_ORG_TYPE_CUSTOMER = "CUSTOMER";
	public static final String METADATA_TYPE_ORG_TYPE_CARRIER = "CARRIER";
	public static final String METADATA_TYPE_SALES_ORDERS_STATUS_READY = "READY_TO_ADD";
	public static final String METADATA_TYPE_SALES_ORDERS_STATUS_CLOSED= "CLOSED";
	public static final String METADATE_TYPE_DELIVERY_ORDERS_STATUS_CANCELED="CANCELED";

	/**
	 * 主数据类型代码
	 */
	public static final String METADATA_TYPE_DEFAULT = "DEFAULT";
	public static final String METADATA_TYPE_COUNTRY = "COUNTRY";
	public static final String METADATA_TYPE_STATE = "STATE";
	public static final String METADATA_TYPE_CITY = "CITY";
	public static final String METADATA_TYPE_USER_TYPE = "USER_TYPE";
	public static final String METADATA_TYPE_LOCALE_CODE = "LOCALE_CODE";
	public static final String METADATA_TYPE_ORG_TYPE = "ORG_TYPE";
	public static final String METADATA_TYPE_SHIPPING_TYPE = "SHIPPING_TYPE";
	public static final String METADATA_TYPE_SHIPPING_CONDITION = "SHIPPING_CONDITION";
	public static final String METADATA_TYPE_PRICING_TYPE = "PRICING_TYPE";
	public static final String METADATA_TYPE_CURRENCY = "CURRENCY";
	public static final String METADATA_TYPE_SALES_ORDERS_STATUS = "SALES_ORDERS.STATUS";
	public static final String METADATA_TYPE_SHIPPING_PLANS_STATUS = "SHIPPING_PLANS.STATUS";
	public static final String METADATA_TYPE_DELIVERY_ORDERS_STATUS = "DELIVERY_ORDERS.STATUS";

	public static final String[] METADATA_TYPE_CODE = { METADATA_TYPE_DEFAULT,
			METADATA_TYPE_COUNTRY, METADATA_TYPE_STATE, METADATA_TYPE_CITY,
			METADATA_TYPE_USER_TYPE, METADATA_TYPE_LOCALE_CODE,
			METADATA_TYPE_ORG_TYPE, METADATA_TYPE_SHIPPING_TYPE,
			METADATA_TYPE_PRICING_TYPE, METADATA_TYPE_CURRENCY,
			METADATA_TYPE_SALES_ORDERS_STATUS,
			METADATA_TYPE_SHIPPING_PLANS_STATUS,
			METADATA_TYPE_DELIVERY_ORDERS_STATUS };

	// 流程节点信息
	public static final String WORKFLOW_UPLOAD_SHIPPING_ORDER = "海安客服上传发货单";
	public static final String WORKFLOW_CARRIER_PRINT = "承运商打印提货单";
	public static final String WORKFLOW_LAND_AUDIT = "陆运专员审核提货单";
	public static final String WORKFLOW_CARRIER_AUDIT = "承运商落实提货单";
	public static final String WORKFLOW_CUSTOMER_RECEIVER_ORDER = "客服专员录入回单";

	public static final String STATUS_METADATA_CODE_PENDING_GENERATE = "待落实";
	public static final String STATUS_METADATA_CODE_PENDING_NOT_PASS = "审核未通过";
	public static final String STATUS_METADATA_CODE_PENDING_PRINT = "待打印";
	/**
	 * 主数据的描述
	 */
	public static final String[] METADATA_TYPE_DESC = { "全部", "国家", "省", "城市",
			"用户类型", "地区语言", "组织类型", "运输方式", "定价方式", "币别", "销售订单状态", "运输计划状态",
			"提货单状态"

	};

	public static final String[] NOTIFY_CONFIG_METHOD = { "", "禁用", "短信", "邮件",
			"短信+邮件" };

	public static final String SHIP_ORDER_TASK_NAME = "uploadSalesOrder";
	public static final String Receive_ORDER_TASK_NAME = "receiveOrder";
	
	public static final String CARRIER_PRINT_TASK_NAME = "carrierPrint";
	public static final String CARRIER_AUDIT_TASK_NAME = "carrierAudit";
	public static final String LAND_AUDIT_TASK_NAME = "landAudit";
	public static final String LAND_AUDIT_PASS_NAME = "landPass";

	public static final String CARRIER_FIRST_CONFIRM_NAME = "carrierFirstConfirm";
	public static final String ROLE_CARRIER = "承运商";
	public static final String ROLE_LT_COMMISSIONER = "陆运专员";
	public static final String ROLE_LT_MANAGER = "物流部经理";
	
	public static final String ROLE_HAIAN_CUSTOMER = "海安客服";
	public static final Map<String, String> metadataMap = new HashMap<String, String>();

	static {
		metadataMap.put("COUNTRY", "国家");
		metadataMap.put("STATE", "省");
		metadataMap.put("CITY", "城市");
		metadataMap.put("USER_TYPE", "用户类型");
		metadataMap.put("LOCALE_CODE", "地区语言");
		metadataMap.put("ORG_TYPE", "组织类型");
		metadataMap.put("SHIPPING_TYPE", "运输方式");
		metadataMap.put("CURRENCY", "币别");
		metadataMap.put("SALES_ORDERS.STATUS", "销售订单状态");
		metadataMap.put("SHIPPING_PLANS.STATUS", "发货计划状态");
		metadataMap.put("DELIVERY_ORDERS.STATUS", "提货单状态");
		metadataMap.put("NOTIFY_TYPE", "提醒方式");
		metadataMap.put("CONTENT_TYPE", "提醒内容类型");
		metadataMap.put("COMPLAINT_TYPE", "投诉类型");
		metadataMap.put("SHIPPING_CONDITION", "运货条件");
	}

	public static final String[] HEADER = { "日期", "购货单位", "送达方", "发货数量",
			"提货通知单号", "承运商", "车牌号", "装车时间", "送达时间", "客户卸货时间", "卸货完毕时间", "等候时间",
			"卸货时间", "实发数量", "实收数量", "货损", "千分比", "车次", "货损超标", "卸货整点时间" };
}
