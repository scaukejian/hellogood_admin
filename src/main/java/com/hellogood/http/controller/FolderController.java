package com.hellogood.http.controller;

import com.github.pagehelper.PageInfo;
import com.hellogood.http.vo.FolderVO;
import com.hellogood.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件夹Controller
 * Created by kejian
 */
@RequestMapping("folder")
@Controller
public class FolderController extends BaseController {

	@Autowired
	private FolderService folderService;

	/**
	 * 查找文件夹列表
	 * @param folderVO
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pageJson.do")
	public Map<String, Object> pageJson(FolderVO folderVO, @RequestParam int page,
										@RequestParam int pageSize) {
		Map<String, Object> map = new HashMap<>();
		PageInfo pageInfo = folderService.pageQuery(folderVO, page, pageSize);
		map.put(DATA_LIST, pageInfo.getList());
		map.put(TOTAL, pageInfo.getTotal());
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 新增文件夹
	 * @param folderVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add.do")
	public Map<String, Object> add(FolderVO folderVO) {
		Map<String, Object> map = new HashMap<String, Object>();
		folderService.add(folderVO);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 删除文件夹
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete/{ids}.do")
	public Map<String, Object> delete(@PathVariable String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		folderService.delete(ids);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 修改文件夹信息
	 * @param folderVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update.do")
	public Map<String, Object> update(FolderVO folderVO) {
		Map<String, Object> map = new HashMap<>();
		folderService.update(folderVO);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

	/**
	 * 获取文件夹详细信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get/{id}.do")
	public Map<String, Object> get(@PathVariable Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		FolderVO vo = folderService.get(id);
		map.put(DATA, vo);
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

}
