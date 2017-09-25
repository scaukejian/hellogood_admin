package com.hellogood.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

/**
 * Created by kejian
 */
public class UtilDateConverter implements Converter {
    public Object convert(Class type, Object value) {
        if (value == null) {
            return value;
        }
        if (value instanceof Date) {//instanceof判断是否属于此类型
            return value;
        }
        Date date = null;
        if (value instanceof String) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = sdf.parse(value.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public void test(){
    }
}
