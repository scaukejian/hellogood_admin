package com.hellogood.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Row;

import com.hellogood.exception.BusinessException;

/**
 * Created by kejian on 2017/9/28.
 */
public class ExcelImport {
    /**
     *
     * @param row
     * @param index
     * @param cellNames
     * @return
     */
    public static String getValue(Row row, int index, String[] cellNames) {
        String value = null;
        try{
            if(row.getCell(index).getCellType() == HSSFCell.CELL_TYPE_BOOLEAN){
                value = String.valueOf(row.getCell(index).getBooleanCellValue());
            }else if(row.getCell(index).getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                if(HSSFDateUtil.isCellDateFormatted(row.getCell(index))){
                    double tempDouble = row.getCell(index).getNumericCellValue();
                    value = new SimpleDateFormat("yyyy-MM-dd").format(HSSFDateUtil.getJavaDate(tempDouble));
                }else{
                    value = String.valueOf(row.getCell(index).getNumericCellValue());
                }
            }else{
                value = row.getCell(index).getStringCellValue();
            }
        } catch(Exception e){
            e.printStackTrace();
            throw new BusinessException("导入EXCEL中"+cellNames[index]+"格式异常！");
        }
//        if (StringUtils.isBlank(value)) {
//            throw new BusinessException("导入EXCEL中"+cellNames[index]+"不能为空!");
//        }
        return value == null ? value : value.trim();
    }

    /**
     * 格式化时间
     * @param timeDateUtil
     * @param pattern
     * @return
     */
    public static Date parseDate(String timeDateUtil, String pattern){
        Date date = null;
        DateFormat format = new SimpleDateFormat(pattern);
//		if(time.length() != pattern.getName().length()){
//			throw new BusinessLogicVerifyException("导入EXCEL中时间格式错误, 只能为 "+pattern.getName()+ "!");
//		}
        try {
            date = format.parse(timeDateUtil);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BusinessException("导入EXCEL中时间格式错误, 只能为 "+pattern);
        }
        return date;
    }
}
