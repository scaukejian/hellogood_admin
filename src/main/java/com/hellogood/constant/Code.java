package com.hellogood.constant;

/**
 * 公共代码类
 * @author kejian
 */
public final class Code {

	public static final Integer STATUS_VALID = 1; //有效状态
	public static final Integer STATUS_INVALID = 0;//无效状态/初始化

	// 员工状态 0禁用 1可用  2删除
	public final static Byte EMPLOYEE_STATUS_INVALID = 0;
	public final static Byte EMPLOYEE_STATUS_VALID = 1;
	public final static Byte EMPLOYEE_STATUS_DEL = 2;

	// 刷新缓存类型 hotel,job,area,base,meal
	public final static String REFLASH_EHCACHE_BASE = "base";

}
