package com.hellogood.utils;

import java.lang.reflect.Field;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hellogood.exception.BusinessException;

public class DateUtil {

	public static Date stringToDate(String strDate) {
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  ParsePosition pos = new ParsePosition(0);
		  Date strtodate = formatter.parse(strDate, pos);
		  return strtodate;
	}

	
	
	public static String stringDate(String strDate) {
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  ParsePosition pos = new ParsePosition(0);
		  Date strtodate = formatter.parse(strDate, pos);
		  return formatter.format(strtodate);
	}
	
	public static String dateToString(Date date) {
		  if (date==null) {
			return "";
		  }
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String dateString = formatter.format(date);
		  return dateString;
	}
	
	
	public static long getTime() {
		  return new Date().getTime();
	}
	
	/**

     * 把毫秒转化成日期

     * @param millSec(日期格式，例如：MM/ dd/yyyy HH:mm:ss)

     * @param millSec(毫秒数)

     * @return

     */

	public static Date longToDate(Long millSec){
		return new Date(millSec);
    }
	
	/**
	 * object to map
	 * 日期类型格式化
	 * @param o
	 * @return
	 */
	public static Map<String, Object> object2MapDateFormat(Object o){
		if(o == null)
			return null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Field[] declaredFields  = o.getClass().getDeclaredFields();
			for (Field field : declaredFields) {
				field.setAccessible(true);  
				//过滤内容为空的  
	            if (field.get(o) == null) {  
	                continue;  
	            }  
	            //日期格式统一处理
	            if(field.getType() == Date.class){
	            	Date temp = (Date) field.get(o);
	            	resultMap.put(field.getName(), temp.getTime());
	                continue;
	            }
	            resultMap.put(field.getName(), field.get(o));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("convert object failed");
		}
		return resultMap;
	}
	
	/**
	 * objects to maps
	 * 日期类型格式化
	 * @param list
	 * @return
	 */
	
	public static List<Map<String, Object>> list2MapDateFormat(List<?> list) {
		if(list == null)
			return null;
		List<Map<String, Object>> resultList = null;
		try {
			resultList = new ArrayList<Map<String, Object>>();
			for (Object o : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				Field[] declaredFields = o.getClass().getDeclaredFields();
				for (Field field : declaredFields) {
					field.setAccessible(true);
					// 过滤内容为空的
					if (field.get(o) == null) {
						continue;
					}
					//日期格式化
					if (field.getType() == Date.class) {
						Date temp = (Date) field.get(o);
						map.put(field.getName(), temp.getTime());
						continue;
					}
					map.put(field.getName(), field.get(o));
				}
				resultList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("convert object failed");
		}
		return resultList;
	}
	
	/**获取生日*/
	public static Date getBirthDay(int age){
		 Calendar cal = Calendar.getInstance();
		 int now = cal.get(Calendar.YEAR);
		 cal.set(Calendar.YEAR, now-age);
		 Date date = cal.getTime();
		return date;
	}
	
	/** 计算年龄 */
	public static Integer getAge(Date birthDay){
	        Calendar cal = Calendar.getInstance();
	        if(birthDay == null)
	        	return 0;
	        if (cal.before(birthDay)) {
	        	return 0;
	        }

	        int yearNow = cal.get(Calendar.YEAR);
	        int monthNow = cal.get(Calendar.MONTH)+1;
	        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
	       
	        cal.setTime(birthDay);
	        int yearBirth = cal.get(Calendar.YEAR);
	        int monthBirth = cal.get(Calendar.MONTH)+1;
	        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

	        int age = yearNow - yearBirth;

	        if (monthNow <= monthBirth) {
	            if (monthNow == monthBirth) {
	                if (dayOfMonthNow < dayOfMonthBirth) {
	                    age--;
	                }
	            } else {
	                age--;
	            }
	        }

	        return age;
	    } 

	/**
	 * 指定格式输出时间字符串
	 * 
	 * @param date 日期
	 * @param format 输出日期格式
	 * @return 时间字符串
	 */
	public static String dateToString(Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(date);
		return dateString;
	}
	
	public static String dateToYMD(Date date) {
		  if (date==null) {
			return "";
		  }
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		  String dateString = formatter.format(date);
		  return dateString;
	}
	
	public static boolean isToday(Date date) {
		Calendar currentDate = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		currentDate.setTime(date);
		today.setTime(new Date());
		int currentDateYear = currentDate.get(Calendar.YEAR);
		int currentDateMonth = currentDate.get(Calendar.MONTH) + 1;
		int currentDateDay = currentDate.get(Calendar.DAY_OF_MONTH);
		int todayYear = today.get(Calendar.YEAR);
		int todayMonth = today.get(Calendar.MONTH) + 1;
		int todayDay = today.get(Calendar.DAY_OF_MONTH);
		if (currentDateYear == todayYear && currentDateMonth == todayMonth
				&& currentDateDay == todayDay) {
			return true;
		}
		return false;
	}

	/**
	 * 获取周几
	 * @return
     */
	public static int getDayOfWeek(){
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		//周日
		if(dayOfWeek == 0) dayOfWeek = 7;
		return dayOfWeek;
	}

	public static void main(String[] args) {
//		System.out.println(isToday(new Date()));
		System.out.println(getBirthDay(30));
	}
}
