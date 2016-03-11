package edu.cuit.module.infom.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.cuit.common.enums.DictionaryEnum;
import edu.cuit.common.util.Constant;
import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.infom.entity.Businessmanagement;
import edu.cuit.module.infom.entity.Expertmanagement;
import edu.cuit.module.infom.entity.Expertsgroup;
import edu.cuit.module.infom.entity.Weatherstationinfo;
import edu.cuit.module.infom.service.ExpertmanagementService;
import edu.cuit.module.infom.service.ExpertsgroupService;
import edu.cuit.module.sys.entity.TbcuitmoonArea;
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;
import edu.cuit.module.sys.entity.TbcuitmoonUser;
import edu.cuit.module.sys.enums.JurCodeEnum;
import edu.cuit.module.sys.service.TbcuitmoonAreaService;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;

@Controller
@RequestMapping("auth_center_info/expertGroup_info")
public class ExpertGroupController extends BaseController {
	private int pageNo;
	private int pageCountSize;

	@Autowired
	ExpertsgroupService groupService;
	@Autowired
	ExpertmanagementService expertService;
	@Autowired
	TbcuitmoonDictionaryService dictionaryService;
	@Autowired
	TbcuitmoonAreaService areaService;

	public ExpertGroupController() {
		pageCountSize = 20; // 页面大小
		pageNo = 1;
	}

	private Page getExpertGroupInfoList(String queryString, Integer pageIndex, HttpSession session) {
		if (pageIndex == null)
			pageIndex = pageNo;
		TbcuitmoonUser user_model = (TbcuitmoonUser)session.getAttribute(Constant.LOGINUSER);
		String areaString = user_model.getCuitMoonAreaId().trim();
		String sqlString = "from Expertsgroup where Region = '"+ areaString +"'"
				+ (queryString == null ? "" : " and expertsName like '%"
						+ queryString + "%'");
		Page page = groupService.getPage(sqlString, pageIndex - 1,
				pageCountSize);
		List<?> infoList = page.getPageList();
		String areaID;
		List<?> tmpList;
		for (int i = 0; i < infoList.size(); i++) {
			areaID = ((Expertsgroup) infoList.get(i))
					.getRegion();
			// ///根据areaID获取区域的中文名称
			tmpList = areaService
					.find("from TbcuitmoonArea where cuitMoonAreaCode = '"
							+ areaID + "'");
			if (tmpList.size() > 0) {
				((Expertsgroup) infoList.get(i))
						.setRegion(((TbcuitmoonArea) tmpList.get(0))
								.getCuitMoonAreaName());
			}
		}
		return page;
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String List(@RequestParam(required = false) Integer requestPageNo,
			@RequestParam(required = false) String queryString, Model model, HttpSession session)
			throws UnsupportedEncodingException {
		if (queryString != null)
			queryString = toUTF8(queryString != null ? queryString : "");
		Page page = getExpertGroupInfoList(queryString, requestPageNo,session);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		return "auth_center_info/expertGroup_info/ExpertGroupList";
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String List(String queryString,
			@RequestParam(required = false) Integer requestPageNo, Model model, HttpSession session)
			throws UnsupportedEncodingException {
		Page page = getExpertGroupInfoList(queryString, requestPageNo, session);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		return "auth_center_info/expertGroup_info/ExpertGroupList";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String Add(Model model, HttpSession session) {
		checkPermissions(JurCodeEnum.ADDGROUP.value());
		List<?> tmpList;
		//专家层次
		TbcuitmoonUser user_model = (TbcuitmoonUser)session.getAttribute(Constant.LOGINUSER);
		String areaString = user_model.getCuitMoonAreaId().trim();
		if(areaString.equals("1012")){
			model.addAttribute("expertLevel", "省级专家");
			model.addAttribute("ExpertLevelID", DictionaryEnum.PROVINCALEXPERTID.toString());
			tmpList = expertService.find("from Expertmanagement where ExpertLevel = '省级专家' and BelongToMeteorology = '"+ areaString +"'");
			model.addAttribute("expertList", tmpList);
			// 获取一级省的信息
			tmpList = areaService.find("from TbcuitmoonArea where CuitMoon_ParentAreaCode = '0'");
			model.addAttribute("provinceList", tmpList);
			
		}
		else{
			model.addAttribute("expertLevel", "基层专家");
			model.addAttribute("ExpertLevelID", DictionaryEnum.PRIMARYEXPERTID.toString());
			tmpList = expertService.find("from Expertmanagement where ExpertLevel = '基层专家' and BelongToMeteorology = '"+ areaString +"'");
			model.addAttribute("expertList", tmpList);
			model.addAttribute("expertLevelArea", areaString);
			tmpList = areaService.find("from TbcuitmoonArea where CuitMoon_AreaCode = '"+ areaString +"'");
			if(tmpList.size() > 0)
				model.addAttribute("AreaName", ((TbcuitmoonArea)tmpList.get(0)).getCuitMoonAreaName());
			model.addAttribute("AreaCode", areaString);
		}
		return "auth_center_info/expertGroup_info/ExpertGroupAdd";
	}

	@RequestMapping(value = "experts", method = RequestMethod.GET)
	@ResponseBody
	// 根据专家层次获取专家
	public Map<String, List<?>> getExperts(String code) {
		String query = "";
		if(code.equals(DictionaryEnum.PRIMARYEXPERTID.toString()))
			query = "基层专家";
		else if(code.equals(DictionaryEnum.PROVINCALEXPERTID.toString()))
			query = "省级专家";
		List<?> expertsList = expertService
				.find("from Expertmanagement where ExpertLevel = '"
						+ query + "'");
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		map.put("list", expertsList);
		return map;
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String Add(String expertsLevel, String expertsName,
			String expertsPerson, String region) {
		Expertsgroup model = new Expertsgroup();
		String primaryKey = UUID.randomUUID().toString().replace("-", "");
		model.setExpertsId(primaryKey); // 主键
		model.setExpertsLevel(expertsLevel);
		model.setExpertsName(expertsName);
		model.setExpertsPerson(expertsPerson);
		model.setRegion(region);
		groupService.save(model);
		return "T";
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String Edit(@RequestParam String id, Model model) {
		checkPermissions(JurCodeEnum.EDITGROUP.value());
		// 专家组数据
		Expertsgroup group = groupService.get(id);
		model.addAttribute("primaryKey", group.getExpertsId());
//		model.addAttribute("expertsLevel", group.getExpertsLevel());
		model.addAttribute("expertsName", group.getExpertsName());
		model.addAttribute("expertsPerson", group.getExpertsPerson());
		model.addAttribute("region", group.getRegion());
		
		//默认基层专家
		List<?> tmpList;
		String areaString = group.getRegion();
		if(areaString.equals("1012")){
			model.addAttribute("expertLevel", "省级专家");
			model.addAttribute("ExpertLevelID", DictionaryEnum.PROVINCALEXPERTID.toString());
			tmpList = expertService.find("from Expertmanagement where ExpertLevel = '省级专家' and BelongToMeteorology = '"+ areaString +"'");
			model.addAttribute("expertList", tmpList);
			// 获取一级省的信息
			tmpList = areaService.find("from TbcuitmoonArea where CuitMoon_ParentAreaCode = '0'");
			model.addAttribute("provinceList", tmpList);
			model.addAttribute("province", areaString);
		}
		else{
			model.addAttribute("expertLevel", "基层专家");
			model.addAttribute("ExpertLevelID", DictionaryEnum.PRIMARYEXPERTID.toString());
			tmpList = expertService.find("from Expertmanagement where ExpertLevel = '基层专家' and BelongToMeteorology = '"+ areaString +"'");
			model.addAttribute("expertList", tmpList);
			model.addAttribute("expertLevelArea", areaString);
			tmpList = areaService.find("from TbcuitmoonArea where CuitMoon_AreaCode = '"+ areaString +"'");
			if(tmpList.size() > 0)
				model.addAttribute("AreaName", ((TbcuitmoonArea)tmpList.get(0)).getCuitMoonAreaName());
			model.addAttribute("AreaCode", areaString);
		}
		return "auth_center_info/expertGroup_info/ExpertGroupEdit";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody 
	public String Edit(String primaryKey,String expertsLevel, String expertsName,
			String expertsPerson, String region) {
		Expertsgroup model = groupService.get(primaryKey);
		model.setExpertsLevel(expertsLevel);
		model.setExpertsName(expertsName);
		model.setExpertsPerson(expertsPerson);
		model.setRegion(region);
		groupService.update(model);
		return "T";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	@ResponseBody //删除
	public String delete(String primaryKey) {
		checkPermissions(JurCodeEnum.DELTEGROUP.value());
		groupService.deleteByKey(primaryKey);
		return "T";
	}
	
	@RequestMapping(value = "dictionary", method = RequestMethod.GET)
	@ResponseBody
	// 根据区域的，获取其子级
	public Map<String, List<?>> getSubListByAreaCode(String code) {
		List<?> areaList = areaService
				.find("from TbcuitmoonArea where CuitMoon_ParentAreaCode = '"
						+ code + "'");
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		map.put("list", areaList);
		return map;
	}
}
