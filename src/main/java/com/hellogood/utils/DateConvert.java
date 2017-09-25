package com.hellogood.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

/**
 * Created by kejian on 2017/9/21.
 */

public class DateConvert implements Converter {
    @Override
    public Object convert(Class type, Object value) {
            String p = (String)value;
            if(p== null || p.trim().length()==0){
                return null;
            }
            try{
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return df.parse(p.trim());
            }
            catch(Exception e){
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    return df.parse(p.trim());
                } catch (ParseException ex) {
                    return null;
                }
            }

        }
}
