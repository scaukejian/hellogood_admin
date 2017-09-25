package com.hellogood.utils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.*;

/**
 * Created by kejian on 2017/9/21.
 */
public class BeaUtils extends BeanUtils{

    static {
        ConvertUtils.register(new LongConverter(null), Long.class);
        ConvertUtils.register(new ShortConverter(null), Short.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
        //注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
        ConvertUtils.register(new SqlDateConverter(), java.util.Date.class);
        //ConvertUtils.register(new SqlTimestampConverter(), java.sql.Timestamp.class);
        //注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空
        ConvertUtils.register(new UtilDateConverter(), java.util.Date.class);
    }
    public static void copyProperties(Object target, Object source) throws
            InvocationTargetException, IllegalAccessException {
        //支持对日期copy
        org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
    }
}
