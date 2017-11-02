package com.hellogood.http.controller;

import com.github.pagehelper.PageInfo;
import com.hellogood.exception.BusinessException;
import com.hellogood.http.vo.PictureVO;
import com.hellogood.http.vo.SelectUploadVO;
import com.hellogood.service.PictureService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片Controller
 * Created by kejian
 */
@RequestMapping("picture")
@Controller
public class PictureController extends BaseController {

	@Autowired
	private PictureService pictureService;

	/**
	 * 查找图片列表
	 * @param pictureVO
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pageJson.do")
	public Map<String, Object> pageJson(PictureVO pictureVO, @RequestParam int page,
										@RequestParam int pageSize) {
		Map<String, Object> map = new HashMap<>();
		PageInfo pageInfo = pictureService.pageQuery(pictureVO, page, pageSize);
		map.put(DATA_LIST, pageInfo.getList());
		map.put(TOTAL, pageInfo.getTotal());
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 新增图片
	 * @param pictureVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add.do")
	public Map<String, Object> add(PictureVO pictureVO) {
		Map<String, Object> map = new HashMap<String, Object>();
		pictureService.add(pictureVO);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 删除图片
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete/{ids}.do")
	public Map<String, Object> delete(@PathVariable String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		pictureService.delete(ids);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 修改图片信息
	 * @param pictureVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update.do")
	public Map<String, Object> update(PictureVO pictureVO) {
		Map<String, Object> map = new HashMap<>();
		pictureService.update(pictureVO);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 获取图片详细信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get/{id}.do")
	public Map<String, Object> get(@PathVariable Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		PictureVO vo = pictureService.get(id);
		map.put(DATA, vo);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 上传图片
	 * @param selectUploadVO
	 * @param pictureId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("uploadPhoto-{pictureId}.do")
	public Map<String, Object> uploadPhoto(SelectUploadVO selectUploadVO, HttpServletRequest request, @PathVariable Integer pictureId){
		Map<String, Object> result = new HashMap<>();
		uploadSelectPhoto(selectUploadVO, result, request);
		Integer picId = 0;
		//保存图片并返回活动id
		if (pictureId != null && pictureId != 0)
			picId = pictureService.saveDatum(pictureId, (String)result.get("imgName"), (String)result.get("originalImgName"));
		result.put("pictureId", picId);
		return result;
	}

	/**
	 * 修改图片
	 * @param selectUploadVO
	 * @param pictureId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updatePhoto-{pictureId}.do")
	public Map<String, Object> updatePhoto(SelectUploadVO selectUploadVO, HttpServletRequest request, @PathVariable Integer pictureId){
		Map<String, Object> result = new HashMap<>();
		uploadSelectPhoto(selectUploadVO, result, request);
		//更新图片记录
		Integer picId = 0;
		if (pictureId != null && pictureId != 0)
			picId = pictureService.saveDatum(pictureId,(String)result.get("imgName"), (String)result.get("originalImgName"));
		result.put("pictureId", picId);
		return result;
	}

	/**
	 * 删除图片
	 * @param pictureId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deletePhoto-{pictureId}.do")
	public Map<String, Object> deletePhoto(@PathVariable Integer pictureId){
		Map<String, Object> result = new HashMap<>();
		//更新图片记录
		Integer picId = 0;
		if (pictureId != null && pictureId != 0)
			picId = pictureService.saveDatum(pictureId, null, null);
		result.put("pictureId", picId);
		return result;
	}
}
