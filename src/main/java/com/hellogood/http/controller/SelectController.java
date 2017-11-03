package com.hellogood.http.controller;

import com.hellogood.domain.Folder;
import com.hellogood.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 下拉框初始化 Created by kejian
 */
@RequestMapping("/select")
@Controller
public class SelectController extends BaseController {

	@Autowired
	private FolderService folderService;

	@ResponseBody
	@RequestMapping("/getSystemFolderList.do")
	public Map<String, Object> selectBaseDataTypeList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(DATA_LIST, folderService.getSystemFolderListByRedis(false));
		map.put(STATUS, STATUS_SUCCESS);
		return map;
	}

}
