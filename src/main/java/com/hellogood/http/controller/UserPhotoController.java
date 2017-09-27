package com.hellogood.http.controller;

import com.hellogood.constant.Code;
import com.hellogood.domain.UserPhoto;
import com.hellogood.exception.BusinessException;
import com.hellogood.http.vo.SelectUploadVO;
import com.hellogood.http.vo.UserPhotoVO;
import com.hellogood.service.UserPhotoService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 用户相册Controller
 */
@Controller
@RequestMapping(value = "/photo")
public class UserPhotoController extends BaseController {
	
	Logger logger = LoggerFactory.getLogger(UserPhotoController.class);

	@Autowired
	private UserPhotoService userPhotoService;

	/**
	 * 相册上传
	 * 
	 * @param userId
	 * @param files
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/upload.do")
	public Map<String, Object> uploadFile(@RequestParam("userId") int userId,
										  @RequestParam("file1") CommonsMultipartFile files) {
		logger.info("开始上传...");
		Map<String, Object> map = new HashMap<>();

		long startTime = System.currentTimeMillis();

		// 获取文件名称
		String myFileName = files.getOriginalFilename();
		//校验文件类型
		validImgFormat(files.getOriginalFilename());
		// 生成文件夹路径
		String folderPath = generateFolderPath(userId);
		// 生成文件夹名
		String folderName = generateFolderName(userId);
		// 头像重命名规则 文件夹名+header+UUID+后缀名
		String fileName = folderName
				+ "_"
				+ "header-"
				+ UUID.randomUUID()
				+ files.getOriginalFilename().substring(
				files.getOriginalFilename().indexOf("."));
		File localFile = new File(folderPath + fileName);
		try {
			files.transferTo(localFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		UserPhoto userPhoto = new UserPhoto();
		userPhoto.setHeadFlag(0);
		userPhoto.setImgName(fileName);
		userPhoto.setOriginalImgName(myFileName);
		userPhoto.setUserId(userId);
		userPhoto.setUpdateTime(new Date());
		userPhoto.setThumbnailImgName(fileName);
		userPhotoService.insert(userPhoto);
		map.put(DATA, userPhoto);
		logger.info("上传完毕耗时 ： " + (System.currentTimeMillis() - startTime));
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 头像上传
	 * @author 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadWhenRegister.do")
	public Map<String, Object> uploadWhenRegister(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String file = (String)request.getParameter("base64Image");
		long startTime = System.currentTimeMillis();
		Integer userId = Integer.valueOf(request.getParameter("userId"));
		String token = request.getParameter("token");

		validBase64ImgFormat(file);
		
        String fileName = "xxx.jpg";
		// 生成文件夹路径
		String folderPath = generateFolderPath(userId);
		String folderName = generateFolderName(userId);
		// 文件重命名 生成文件夹名+UUID+后缀名
		String uploadFileName = folderName + "_"  + UUID.randomUUID() + fileName.substring(fileName.indexOf("."));
        File uploadFile = new File(folderPath + uploadFileName);
        
		//上传代码
        OutputStream out = null;
		Base64 base64 = new Base64();
        try {
            // Base64解码
        	file = file.split(",")[1];
            byte[] bytes = base64.decode(file);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            out = new FileOutputStream(uploadFile);
            out.write(bytes);
            out.flush();
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
	    	try {
	    		if(out != null) out.close();
			} catch (IOException e) {
				logger.error("关闭流出现问题");
	        	map.put(STATUS, STATUS_ERROR);
				return map;
			}
	    }
        
        //新增一条记录
        UserPhoto userPhoto = new UserPhoto();
		userPhoto.setHeadFlag(1);
		userPhoto.setImgName(uploadFileName);
		userPhoto.setOriginalImgName(fileName);
		userPhoto.setUserId(userId);
		userPhoto.setUpdateTime(new Date());
		userPhoto.setThumbnailImgName(uploadFileName);
		userPhotoService.insert(userPhoto);
		
		logger.info("上传完毕耗时 ： " + (System.currentTimeMillis() - startTime));
		map.put("id", userPhoto.getId());
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}
	
	/**
	 * 删除照片
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deletePhoto.do")
	public Map<String, Object> deletePhoto(@RequestParam("id") int id,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserPhoto userPhoto = userPhotoService.getUserPhoto(id);
		userPhotoService.delete(id);
		UserPhotoVO userPVO =new UserPhotoVO();
		userPVO.setId(id);
		map.put(DATA, userPVO);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 后台用户相册截图预览
	 * @param fileName
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectPhotoDownload.do")
	public ResponseEntity<byte[]> selectPhotoDownload(
			@RequestParam("fileName") String fileName,
			HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		// 文件路径
		String responeName = generateFilePath(fileName);
		byte[] bytes = null;
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		try {
			headers.setContentDispositionFormData("attachment", new String(
					fileName.getBytes("gb2312"), "ISO8859-1"));
			File file = new File(responeName);
			if (!file.exists()) {
				file.delete();
				throw new BusinessException("下载失败: " + responeName + "文件不存在!");
			}
			bytes = FileUtils.readFileToByteArray(file);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new BusinessException("下载失败...");
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("下载失败...");
		}
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}
	
	/**
	 * 截图
	 * @param selectUploadVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("uploadSelectPhoto.do")
	public Map<String, Object> uploadSelectPhoto(SelectUploadVO selectUploadVO, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();

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
		//上传裁剪图片
		cutImageByXY(suffix.substring(1), sourcePath.toString(),
				targetPath.toString(), selectUploadVO.getStartX(),
				selectUploadVO.getStartY(), selectUploadVO.getEndX(),
				selectUploadVO.getEndY());
		File file=new File(sourcePath.toString());
		if (file.isFile() && file.exists()) {  
	        file.delete(); 
		}
		//保存头像到数据库
		UserPhoto userPhoto = userPhotoService.save(fileName, filePath, selectUploadVO.getUserId());
		map.put(DATA, userPhoto);
		return map;
	}
}
