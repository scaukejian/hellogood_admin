package com.hellogood.http.controller;

import com.hellogood.constant.Code;
import com.hellogood.domain.*;
import com.hellogood.exception.BusinessException;
import com.hellogood.http.vo.*;
import com.hellogood.service.*;
import com.hellogood.utils.DateUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kejian
 */
@RequestMapping("user")
@Controller
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	AreaService areaService;

	Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * 新增用户
	 * 
	 * @param userVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add.do")
	public Map<String, Object> add(UserVO userVO) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(DATA, userService.add(userVO));
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete/{id}.do")
	public Map<String, Object> delete(@PathVariable Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		userService.delete(id);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/***
	 * 修改用户信息
	 * 
	 * @param userVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update.do")
	public Map<String, Object> update(UserVO userVO, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		userService.updateSelective(userVO);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 获取用户详细信息
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get/{id}.do")
	public Map<String, Object> get(@PathVariable Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询头像路径
		String fPath = generateFolderPath(id);
		UserVO vo = userService.get(id);
		map.put(DATA, vo);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 获取用户简要信息
	 *
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSimpleInfo/{id}.do")
	public Map<String, Object> getSimpleInfo(@PathVariable Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 查询头像路径
		String fPath = generateFolderPath(id);
		UserVO vo = userService.getSimpleInfo(id);
		map.put(DATA, vo);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 查找用户列表
	 * 
	 * @param userVO
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pageJson.do")
	public Map<String, Object> pageJson(UserVO userVO, @RequestParam int page,
			@RequestParam int pageSize) {
		Map<String, Object> map = new HashMap<>();
		List<UserVO> userVOList = userService.pageJson(userVO, page, pageSize);
		map.put(DATA_LIST, userVOList);
		map.put(TOTAL, userService.getSearchTotal(userVO));
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 用户数据下载
	 * 
	 * @param fileName
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/download.do")
	public ResponseEntity<byte[]> download(
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

	@RequestMapping("/excelImport")
	@ResponseBody
	public Map<String, Object> userExcelImport( @RequestParam CommonsMultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userService.excelImport(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("excel导入失败");
		}
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 获取易约号
	 */
	@ResponseBody
	@RequestMapping("/getUser/{userCode}.do")
	public Map<String, Object> getUser(@PathVariable String userCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UserVO> vos = userService.getUserByUserCode(userCode);
		map.put(DATA_LIST, vos);
		return map;
	}

	@ResponseBody
	@RequestMapping("/getUserBaseInfo/{userId}.do")
	public Map<String, Object> getUserBaseInfo(@PathVariable Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(DATA, userService.getUserBaseInfo(userId));
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 上传图片
	 * @param userId
	 * @param certificationType
	 * @param files
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upload.do")
	public Map<String, Object> uploadFile(
			@RequestParam("userId") Integer userId,
			@RequestParam("certificationType") String certificationType,
			@RequestParam("file") CommonsMultipartFile files) {
		logger.info("开始上传...");
		Map<String, Object> map = new HashMap<String, Object>();
		long startTime = System.currentTimeMillis();
		if (files == null) {
			throw new BusinessException("上传失败: 文件不能为空");
		}
		// 获取文件名称
		String myFileName = files.getOriginalFilename();
		if (StringUtils.isBlank(myFileName)) {
			throw new BusinessException("上传失败: 文件名不能为空");
		}
		validImgFormat(myFileName);
		// 生成文件夹路径
		String folderPath = generateFolderPath(userId);
		// 生成文件夹名
		String folderName = generateFolderName(userId);
		// 头像重命名规则 文件夹名+header+UUID+后缀名
		String fileName = folderName
				+ "_"
				+ "certification-"
				+ UUID.randomUUID()
				+ files.getOriginalFilename().substring(
				files.getOriginalFilename().indexOf("."));
		File localFile = new File(folderPath + fileName);
		try {
			files.transferTo(localFile);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			throw new BusinessException("上传失败: 请稍后再试");
		}
		//保存到数据库
		logger.info("上传完毕耗时 ： " + (System.currentTimeMillis() - startTime));
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}
}
