
package com.hellogood.http.controller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;

import com.hellogood.http.vo.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.hellogood.enumeration.BaseDataType;
import com.hellogood.exception.BusinessException;
import com.hellogood.service.BaseDataService;
import com.hellogood.service.EmpRoleRelationService;
import com.hellogood.service.ErrLogService;
import com.hellogood.service.MessageService;
import com.hellogood.utils.ExcelExport;
import com.hellogood.utils.HttpsUtils;
import com.hellogood.utils.StaticFileUtil;

/**
 * 基础控制器 Created by kejian
 */
@Controller
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ErrLogService errLogService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private BaseDataService baseDataService;

    @Autowired
    private EmpRoleRelationService empRoleRelationService;

    // 状态
    public static final String STATUS = "status";
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_FAILED = "failed";
    public static final String STATUS_ERROR = "error";
    // 返回数据类型
    public static final String DATA = "data";
    public static final String DATA_LIST = "dataList";
    public static final String ERROR_MSG = "errorMsg";
    public static final String TOTAL = "total";

    @RequestMapping("/list/{path}-{fileName}")
    public String list(@PathVariable String path, @PathVariable String fileName) {
        return path + "/" + path + "-" + fileName;
    }

    @RequestMapping("/common/{fileName}")
    public String common(@PathVariable String fileName) {
        return "common/" + fileName;
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Map<String, Object> exceptionHandler(RuntimeException rex, HttpServletRequest request) {
        if (getCurrentEmployee(request) == null) {
            logger.error(rex.getLocalizedMessage());
        } else {
            logger.error("[操作人:" + getCurrentEmployee(request).getName() + "] " + rex.getLocalizedMessage());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (rex instanceof BusinessException) {
            map.put(STATUS, STATUS_FAILED);
            map.put(ERROR_MSG, rex.getMessage());
        } else {
            rex.printStackTrace();// 控制台输出异常信息
            map.put(STATUS, STATUS_ERROR);
            map.put(ERROR_MSG, "服务器繁忙，请稍后再试");
        }
        return map;
    }

    /**
     * 生成文件夹
     *
     * @param id
     * @param type
     * @return
     */
    public String generateFolderPath(Integer id, String type) {
        // 文件夹路径
        StringBuffer folderPath = new StringBuffer(StaticFileUtil.getProperty(
                "fileSystem", "storagePath"));
        String folderName = generateFolderName(id, type);
        folderPath.append(folderName).append("/");
        // 文件夹不存在时创建
        if (!new File(folderPath.toString()).exists()) {
            new File(folderPath.toString()).mkdir();
        }
        return folderPath.toString();
    }

    /**
     * 生成文件夹名称
     *
     * @param id
     * @param type
     * @return
     */
    public String generateFolderName(Integer id, String type) {
        // 最大文件夹数
        Integer fileMaxCount = Integer.valueOf(StaticFileUtil.getProperty(
                "fileSystem", "partyFileMaxCount"));
        // 生成文件夹名
        return type + id % fileMaxCount;
    }

    /**
     * 根据userId生成文件夹
     *
     * @param userId
     * @return
     */
    public String generateFolderPath(Integer userId) {
        // 文件夹路径
        StringBuffer folderPath = new StringBuffer(StaticFileUtil.getProperty(
                "fileSystem", "storagePath"));
        String folderName = generateFolderName(userId);
        folderPath.append(folderName).append("/");
        // 文件夹不存在时创建
        if (!new File(folderPath.toString()).exists()) {
            new File(folderPath.toString()).mkdir();
        }
        return folderPath.toString();
    }

    public String generateTmpFolderPath() {
        // 文件夹路径
        StringBuffer folderPath = new StringBuffer(StaticFileUtil.getProperty(
                "fileSystem", "storagePath"));
        folderPath.append("tmp/");
        if (!new File(folderPath.toString()).exists()) {
            new File(folderPath.toString()).mkdir();
        }
        return folderPath.toString();
    }

    /**
     * 按天生成文件夹名称
     *
     * @param typeName
     * @param day
     * @return
     * @author chenzeke on 2015.10.08
     */
    public String generateFolderName(String typeName, Integer day) {
        Integer fileMaxCount = Integer.valueOf(StaticFileUtil.getProperty("fileSystem", "partyFileMaxCount"));
        return typeName + day % fileMaxCount;
    }

    /**
     * 按天生成文件夹路径
     *
     * @param typeName
     * @param day
     * @return
     * @author chenzeke on 2015.10.08
     */
    public String generateFolderPath(String typeName, Integer day) {
        // 获取upload文件夹路径
        StringBuffer folderPath = new StringBuffer(StaticFileUtil.getProperty("fileSystem", "storagePath"));
        String folderName = generateFolderName(typeName, day);
        folderPath.append(folderName).append("/");
        // 文件夹不存在时创建
        if (!new File(folderPath.toString()).exists()) {
            new File(folderPath.toString()).mkdir();
        }
        return folderPath.toString();
    }

    public static void main(String[] args) {
        BaseController b = new BaseController();
        System.out.println(b.generateFolderPath(18, "partyDetail"));
    }

    /**
     * 根据userId生成文件夹名称
     *
     * @param userId
     * @return
     */
    public String generateFolderName(Integer userId) {
        // 最大文件夹数
        Integer fileMaxCount = Integer.valueOf(StaticFileUtil.getProperty(
                "fileSystem", "fileMaxCount"));
        // 生成文件夹名
        return String.valueOf(userId % fileMaxCount);
    }

    /**
     * 根据文件名生成文件路径
     *
     * @param fileName
     * @return
     */
    public String generateFilePath(String fileName) {
        // 存储路径
        StringBuffer filePath = new StringBuffer(StaticFileUtil.getProperty(
                "fileSystem", "storagePath"));
        // 文件名是否包含文件夹名称 前缀为文件夹名称
        if (fileName.indexOf("_") > 0) {
            String folderName = fileName.substring(0, fileName.indexOf("_"));
            filePath.append(folderName).append("/").append(fileName);
        } else {
            filePath.append(fileName);
        }
        return filePath.toString();
    }

    /**
     * 获取token
     *
     * @param request
     * @return
     */
    public String getRequestToken(HttpServletRequest request) {
        String serverUrl = StaticFileUtil.getProperty("systemConfig",
                "INTERFACE_SERVER");
        String url = serverUrl
                + StaticFileUtil.getProperty("systemConfig", "CREATE_TOKEN");
        Map<String, String> tokenRequestMap = new HashMap<>();
        tokenRequestMap.put("source",
                StaticFileUtil.getProperty("systemConfig", "ADMIN_SOURCE"));
        JSONObject jsonObject = new JSONObject(HttpsUtils.post(url,
                tokenRequestMap));
        String token = String.valueOf(jsonObject.get("token"));
        return token;
    }

    /**
     * 获取登陆用户信息
     *
     * @param request
     * @return
     */
    public EmployeeVO getCurrentEmployee(HttpServletRequest request) {
        // 获取登陆用户信息
        EmployeeVO employee = (EmployeeVO) request.getSession().getAttribute(
                "employee");
        return employee;
    }

    /**
     * 获取登陆用户ID
     *
     * @param request
     * @return
     */
    public Long getCurrentEmployeeId(HttpServletRequest request) {
        return getCurrentEmployee(request).getId();
    }

    /**
     * 消息通知
     * <p/>
     * noticeParams  KEYS
     * userId 用户ID
     * type   类型
     * content 类容
     *
     * @param params
     */
    public String notice(NoticeVO params) {
        logger.info("消息通知，请求参数..." + params);
        MessageVO vo = new MessageVO();
        if (params.getUserId() != null) vo.setUserId(params.getUserId());
        if (params.getType() != null) vo.setType(params.getType());
        if (StringUtils.isNotBlank(params.getContent())) vo.setContent(params.getContent());
        messageService.save(vo);
        return STATUS_SUCCESS;
    }

    /**
     * 刷新缓存
     */
    public void reflashehcache(HttpServletRequest request, String type) {
        logger.info("刷新缓存:"
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date()));
        String token = getRequestToken(request);
        Map<String, String> map = new HashMap<String, String>();
        map.put(StaticFileUtil.getProperty("systemConfig", "type"), type);
        map.put("token", token);
        String result = HttpsUtils
                .post(StaticFileUtil.getProperty("systemConfig",
                        "INTERFACE_SERVER")
                                + StaticFileUtil.getProperty("systemConfig", "ehcache"),
                        map);
        logger.info("刷新缓存 返回结果：" + result);
    }

    /**
     * 更新订单状态
     *
     * @param request
     * @param orderId
     * @return
     */
    public void getOrderPayStatus(HttpServletRequest request, Integer orderId) {
        logger.info("更新订单 "
                + orderId
                + " 支付状态:"
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date()));
        String token = getRequestToken(request);
        String queryPayStatus = StaticFileUtil.getProperty("systemConfig",
                "queryPayStatus");
        HttpsUtils.get(
                StaticFileUtil.getProperty("systemConfig", "INTERFACE_SERVER")
                        + StringUtils.replace(queryPayStatus, "{orderId}",
                        orderId.toString()), token);
    }

    /**
     * 更新订单状态
     *
     * @param request
     * @param rechargeId
     * @return
     */
    public void getRechargePayStatus(HttpServletRequest request, Integer rechargeId) {
        logger.info("更新易约币充值状态   id:"
                + rechargeId
                + " ;  时间:"
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date()));
        String token = getRequestToken(request);
        String queryPayStatus = StaticFileUtil.getProperty("systemConfig",
                "recharge_queryPayStatus");
        HttpsUtils.get(
                StaticFileUtil.getProperty("systemConfig", "INTERFACE_SERVER")
                        + StringUtils.replace(queryPayStatus, "{rechargeId}",
                        rechargeId.toString()), token);
    }

    /**
     * 重置token
     *
     * @param userId
     * @param request
     */
    public void resetToken(Integer userId, HttpServletRequest request) {
        logger.info("重置token:userId="
                + userId
                + "  时间："
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date()));
        String token = getRequestToken(request);
        String url = StaticFileUtil.getProperty("systemConfig", "RESET_TOKEN");
        HttpsUtils.get(
                StaticFileUtil.getProperty("systemConfig", "INTERFACE_SERVER")
                        + StringUtils.replace(url, "{userId}",
                        userId.toString()), token);
    }


    public String register(RegisterVO vo, HttpServletRequest request) {
        JSONObject json = new JSONObject(vo);
        String result = HttpsUtils.post(
                StaticFileUtil.getProperty("systemConfig", "INTERFACE_SERVER")
                        + StaticFileUtil.getProperty("systemConfig", "register"),
                json.toString(), getRequestToken(request));
        return result;
    }


    /**
     * 环信推送
     * @param targetCodes 用户账号
     * @param content 内容
     * @param needEncode 需要编码
     * @param request
     * @return
     */
    public String batchAddIMMessage(List<String> targetCodes,
                                    String content,
                                    boolean needEncode,
                                    HttpServletRequest request) {
        if (needEncode) content = Base64.encodeBase64String(content.getBytes());
        return batchAddIMMessage(targetCodes, content, request);
    }

    /**
     * 环信推送
     * @param targetCodes 用户账号
     * @param content 内容
     * @param request
     * @return
     */
    public String batchAddIMMessage(List<String> targetCodes,
                                    String content,
                                    HttpServletRequest request) {
        JSONObject json = new JSONObject();
        // 获取客服id
        String fromUserCode = "";
        json.accumulate("fromUserCode", fromUserCode);
        json.accumulate("userCodeList", targetCodes);// 单个发送
        json.accumulate("content", content);// 发送内容
        json.accumulate("type", "text");// 私信类型 text：文本
        String result = HttpsUtils.post(
                StaticFileUtil.getProperty("systemConfig", "INTERFACE_SERVER")
                        + StaticFileUtil.getProperty("systemConfig",
                        "batchAddIMMessage"), json.toString(),
                getRequestToken(request));
        return result;
    }

    /**
     * 虚拟点赞
     *
     * @param momentId
     * @param request
     * @return
     */
    public String virtualSayGood(int momentId, HttpServletRequest request) {
        String path = StaticFileUtil.getProperty("systemConfig", "INTERFACE_SERVER")
                + StaticFileUtil.getProperty("systemConfig", "virtualSayGood");
        path = StringUtils.replace(path, "{momentId}", momentId + "");
        HttpsUtils.get(path, getRequestToken(request));
        return STATUS_SUCCESS;
    }

    /**
     * 验证图片格式
     * @param imgName
     */
    public void validImgFormat(String imgName){
        logger.info("验证图片格式, 图片名称: " + imgName);
        if(StringUtils.isBlank(imgName)){
            throw new BusinessException("图片名不能为空");
        }
        //判断上传格式
        String prefix = imgName.substring(imgName.lastIndexOf(".") + 1);
        if (!("jpeg".equals(prefix.toLowerCase())
                || "jpg".equals(prefix.toLowerCase())
                || "gif".equals(prefix.toLowerCase())
                || "png".equals(prefix.toLowerCase()))) {
            logger.error("文件格式有误");
            throw new BusinessException("图片格式有误, 系统目前支持jpeg, jpg, gif, png");
        }
    }
    
    /**
     * 验证视频格式
     * @param videoName
     */
    public void validVideoFormat(String videoName){
        logger.info("验证视频格式, 视频名称: " + videoName);
        if(StringUtils.isBlank(videoName)){
            throw new BusinessException("视频名不能为空");
        }
        //判断上传格式
        String prefix = videoName.substring(videoName.lastIndexOf(".") + 1);
        if (!("mp4".equals(prefix.toLowerCase())
                || "rmvb".equals(prefix.toLowerCase())
                || "avi".equals(prefix.toLowerCase())
                || "mkv".equals(prefix.toLowerCase())
                || "mpg".equals(prefix.toLowerCase())
                || "flv".equals(prefix.toLowerCase())
                || "wmv".equals(prefix.toLowerCase()))) {
            logger.error("文件格式有误");
            throw new BusinessException("视频格式有误, 系统目前支持mp4,rmvb,avi,mkv,mpg,flv,wmv");
        }
    }
    
    /**
     * 验证图片格式
     * @param imgName
     */
    public void validBase64ImgFormat(String imgFile){
    	if(imgFile == null || imgFile.isEmpty() || !imgFile.contains(","))
			throw new BusinessException("头像上传数据有误");
		
		//data:image/png;base64,base64编码的png图片数据  
		String prefix = imgFile.split(",")[0].split("image/")[1].split(";")[0];
		
        logger.info("验证图片格式, 图片: " + imgFile);
       
        //判断上传格式
        if (!("jpeg".equals(prefix.toLowerCase())
        		|| "jpg".equals(prefix.toLowerCase())
                || "gif".equals(prefix.toLowerCase())
            	|| "png".equals(prefix.toLowerCase()))) {
            logger.error("文件格式有误");
            throw new BusinessException("图片格式有误, 系统目前支持jpeg, jpg, gif, png");
        }
    }

    /**
     * 字符串ids 转为 数组
     * @param ids
     * @return
     */
    Integer[] idsString2IntegerArray(String ids){
        String[] idStrArr = ids.split(",");
        Integer[] idArr = new Integer[idStrArr.length];
        int index = 0;
        for (String id : idStrArr) {
            idArr[index] = Integer.parseInt(id);
            index++;
        }
        return idArr;
    }

    /**
     * 字符串ids 转为 数组
     * @param ids
     * @return
     */
    Long[] ids2LongArray(String ids){
        String[] idStrArr = ids.split(",");
        Long[] idArr = new Long[idStrArr.length];
        int index = 0;
        for (String id : idStrArr) {
            idArr[index] = Long.parseLong(id);
            index++;
        }
        return idArr;
    }

    /**
     * 字符串ids 转为 数组
     * @param ids
     * @return
     */
    Integer[] ids2IntArray(String ids){
        String[] idStrArr = ids.split(",");
        Integer[] idArr = new Integer[idStrArr.length];
        int index = 0;
        for (String id : idStrArr) {
            idArr[index] = Integer.parseInt(id);
            index++;
        }
        return idArr;
    }


    protected boolean isSystemAdmin(HttpServletRequest request) {
        String roleNames = empRoleRelationService.getRoleCodesByEmpId(getCurrentEmployeeId(request));
        return StringUtils.contains(roleNames, RoleVO.SYSADMIN);
    }

    protected boolean isCustomerManager(HttpServletRequest request) {
        String roleNames = empRoleRelationService.getRoleCodesByEmpId(getCurrentEmployeeId(request));
        return StringUtils.contains(roleNames, RoleVO.CUSTOMER_MANAGER);
    }

    /**
     * 测试验收
     * @param request
     * @return
     */
    protected boolean isUat(HttpServletRequest request) {
        String roleNames = empRoleRelationService.getRoleCodesByEmpId(getCurrentEmployeeId(request));
        return StringUtils.contains(roleNames, RoleVO.UAT);
    }

    /**
     * 绘制截图
     *
     * @param suffix
     * @param sourcePath
     * @param targetPath
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     */
     protected void cutImage(String suffix, String sourcePath, String targetPath,
                          int startX, int startY, int endX, int endY) {
        try {
            Image img = null;
            ImageFilter cropFilter = null;
            File sourceImgFile = new File(sourcePath);
            BufferedImage bi = ImageIO.read(sourceImgFile);
            int srcWidth = bi.getWidth();
            int srcHeight = bi.getHeight();
            int destWidth = endX - startX;
            int destHeight = endY - startY;
            Image image = bi.getScaledInstance(srcWidth, srcHeight,
                    Image.SCALE_DEFAULT);
            if (srcHeight > 400) {
                cropFilter = new CropImageFilter(startX * srcHeight / 400,
                        startY * srcHeight / 400, destWidth * srcHeight / 400,
                        destHeight * srcHeight / 400);
            } else {
                cropFilter = new CropImageFilter(startX, startY, destWidth,
                        destHeight);
            }

            img = Toolkit.getDefaultToolkit().createImage(
                    new FilteredImageSource(image.getSource(), cropFilter));
            BufferedImage tag = new BufferedImage(1080, 1080,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(img, 0, 0, 1080, 1080, null);
            g.dispose();
            ImageIO.write(tag, suffix, new File(targetPath));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("上传失败, 系统繁忙! ");
        }
    }
     
     /**
      * 按参数规定长度和宽度绘制截图
      * @param suffix
      * @param sourcePath
      * @param targetPath
      * @param startX
      * @param startY
      * @param endX
      * @param endY
      */
      protected void cutImageByXY(String suffix, String sourcePath, String targetPath,
                           int startX, int startY, int endX, int endY) {
         try {
             File sourceImgFile = new File(sourcePath);
             BufferedImage bi = ImageIO.read(sourceImgFile);
             int srcWidth = bi.getWidth();//原始宽度
             int srcHeight = bi.getHeight();//原始高度
             double nowHeight = 300d;//现在高度
             double nowWidth = srcWidth * 300d / srcHeight; //现在宽度
             double rateWidth = 1.0 * srcWidth / nowWidth;//宽度缩小放大倍率
             double rateHeight = 1.0 * srcHeight / nowHeight;//高度缩小放大倍率
             int width = endX - startX;//裁剪图片宽度
             int height = endY - startY;//裁剪图片高度
             Iterator iterator = ImageIO.getImageReadersByFormatName(suffix);
             ImageReader reader = (ImageReader)iterator.next();   
             InputStream inputStream = new FileInputStream(sourcePath);    
             ImageInputStream iis = ImageIO.createImageInputStream(inputStream);     
             reader.setInput(iis, true);     
             ImageReadParam param = reader.getDefaultReadParam();     
             //(int)(startX * rateWidth): 原始图片开始裁剪X坐标位置，
             //(int)(startY * rateHeight)：原始图片开始裁剪Y坐标位置，
             //(int)(width * rateWidth)原始图片裁剪宽度，
             //(int)(height * rateHeight)原始图片裁剪高度
             Rectangle rectangle = new Rectangle((int)(startX * rateWidth), (int)(startY * rateHeight), (int)(width * rateWidth), (int)(height * rateHeight));/*指定截取范围*/      
             param.setSourceRegion(rectangle);     
             BufferedImage bi2 = reader.read(0,param);   
             ImageIO.write(bi2, suffix, new File(targetPath));  
         } catch (Exception e) {
             e.printStackTrace();
             throw new BusinessException("上传失败, 系统繁忙! ");
         }
     }
      
    /**
     * 临时图片上传(为截图做准备)
     * @param files
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/uploadTmpPhoto.do")
    public Map<String, Object> uploadFile(@RequestParam("file") CommonsMultipartFile files) {
        logger.info("开始上传...");
        Map<String, Object> map = new HashMap<>();
        // 获取文件名称
        String myFileName = files.getOriginalFilename();

        validImgFormat(myFileName);

        // 生成文件夹路径
        String folderPath = generateTmpFolderPath();
        // 生成文件夹名
        String fileName = "tmp_" + UUID.randomUUID() + "_" + myFileName;
        File localFile = new File(folderPath + fileName);
        try {
            files.transferTo(localFile);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        map.put("fileName", fileName);
        map.put("originalImgName", myFileName);

        return map;
    }

    public String getRemoteHost(javax.servlet.http.HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }

    /**
     * 生成表头
     */
    protected void createHeader(HSSFSheet sheet, String[] header){
        // 设置表头
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.setHeight((short) 500);
        for (int i = 0; i < header.length; i++) {
            HSSFCell cell = headerRow.createCell(i);// 创建一列
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(header[i]);
        }
    }

    /**
     * 根据list生成行
     * @param sheet
     * @param list
     * @throws IllegalAccessException
     */
    protected void createRows(HSSFSheet sheet, List<?> list) throws IllegalAccessException {

        if(list == null || list.isEmpty()) return;

        // 填充数据
        int rowIndex = 1;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Object obj : list) {
            HSSFRow row = sheet.createRow(rowIndex);// 创建一行
            row.setHeight((short) 500);
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(ExcelExport.class)) {
                    continue;
                }
                int sort = field.getAnnotation(ExcelExport.class).sort();
                HSSFCell cell = row.createCell(sort);// 创建一列
                //私有变量必须先设置Accessible为true
                field.setAccessible(true);
                //获取用get类方法。
                Object object = field.get(obj);
                if (object instanceof Integer) {
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(object != null ? ((Integer) object)
                            .intValue() : 0);
                    if (object != null && ((Integer) object).intValue() > 0) {
                        cell.setCellValue(((Integer) object).intValue());
                    } else {
                        cell.setCellValue("");
                    }
                } else if (object instanceof Date) {
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(dateFormat.format(object));
                } else {
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(object != null ? object
                            .toString() : "");
                }
            }
            rowIndex++;
        }
    }

    /**
     * 根据list生成行
     * @param sheet
     * @param list
     * @param annotationName @ExcelExport name(注解名称)
     * @throws IllegalAccessException
     */
    protected void createRows(HSSFSheet sheet, List<? extends BaseVO> list, String annotationName) throws IllegalAccessException {

        if(list == null || list.isEmpty()) return;

        // 填充数据
        int rowIndex = 1;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Object obj : list) {
            HSSFRow row = sheet.createRow(rowIndex);// 创建一行
            row.setHeight((short) 500);
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(ExcelExport.class)
                        || !StringUtils.equals(annotationName, field.getAnnotation(ExcelExport.class).name())) {
                    continue;
                }
                int sort = field.getAnnotation(ExcelExport.class).sort();
                HSSFCell cell = row.createCell(sort);// 创建一列
                //私有变量必须先设置Accessible为true
                field.setAccessible(true);
                //获取用get类方法。
                Object object = field.get(obj);
                if (object instanceof Integer) {
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(object != null ? ((Integer) object)
                            .intValue() : 0);
                    if (object != null && ((Integer) object).intValue() > 0) {
                        cell.setCellValue(((Integer) object).intValue());
                    } else {
                        cell.setCellValue("");
                    }
                } else if (object instanceof Date) {
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(dateFormat.format(object));
                } else {
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(object != null ? object
                            .toString() : "");
                }
            }
            rowIndex++;
        }
    }

    /**
     * 上传截图（共用）
     * @param selectUploadVO
     * @return
     */
    public void uploadSelectPhoto(SelectUploadVO selectUploadVO, Map<String, Object> map, HttpServletRequest request) {

        validImgFormat(selectUploadVO.getFileName());

        String filePath = selectUploadVO.getFileName().substring(
                selectUploadVO.getFileName().lastIndexOf("_") + 1, selectUploadVO.getFileName().length());
        String suffix = filePath.substring(filePath.lastIndexOf("."));

        String sourceFilePath = generateTmpFolderPath();
        StringBuffer sourcePath = new StringBuffer();
        sourcePath.append(sourceFilePath);
        sourcePath.append(selectUploadVO.getFileName());

        StringBuffer targetPath = new StringBuffer();
        int empId = getCurrentEmployeeId(request).intValue();
        targetPath.append(generateFolderPath(empId));
        String folderName = generateFolderName(empId);

        // 头像重命名规则 文件夹名+photo+UUID+后缀名
        String fileName = folderName
                + "_photo-"+ UUID.randomUUID()
                + filePath.substring(filePath.indexOf("."));
        targetPath.append(fileName);

        cutImageByXY(suffix.substring(1), sourcePath.toString(),
                targetPath.toString(), selectUploadVO.getStartX(),
                selectUploadVO.getStartY(), selectUploadVO.getEndX(),
                selectUploadVO.getEndY());

        File file=new File(sourcePath.toString());
        if (file.isFile() && file.exists()) {
            file.delete();
        }
        map.put("imgName", fileName);//返回物理存储图片名称
        map.put("originalImgName", selectUploadVO.getOriginalImgName());//返回上传图片名称
        map.put(STATUS, STATUS_SUCCESS);
    }
}
