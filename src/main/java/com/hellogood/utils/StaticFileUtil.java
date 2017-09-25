package com.hellogood.utils;

import java.util.ResourceBundle;

/**
 * 静态文件工具类
 * @author kejian
 *
 */
public class StaticFileUtil {
	
	/**
	 * 读取propertites文件
	 * @param configName
	 * @param propKey
	 * @return
	 */
	public static String getProperty(String configName, String propKey) {
	    return ResourceBundle.getBundle(configName).getString(propKey);
	}
}
