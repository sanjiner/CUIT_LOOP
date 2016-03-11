package edu.cuit.module.sys.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import javassist.expr.NewArray;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.cuit.common.util.IdGenerator;
import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.sys.entity.TbcuitmoonArea;
import edu.cuit.module.sys.enums.JurCodeEnum;
import edu.cuit.module.sys.enums.SysEnum;
import edu.cuit.module.sys.service.TbcuitmoonAreaService;

@Controller
@RequestMapping("sys/area")
public class AreaController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;

	@Autowired
	TbcuitmoonAreaService areaService;

	@RequestMapping("getAreaJson")
	@ResponseBody
	public List<?> getAreaJson(Long parentCode) {
		List<?> areas = null;
		if (!isEmpty(parentCode)) {
			areas = areaService.getByParentCode(parentCode);
		}
		return areas;
	}
	
	@ModelAttribute
	private void getByAreaId(@RequestParam(required=false) String areaId, Model model){
		if(hasLength(areaId)){
			model.addAttribute("area", areaService.get(areaId));
		}else{
			model.addAttribute("area",  new TbcuitmoonArea());
		}
	}

	@RequestMapping("index")
	public String index(Model model) {
		checkPermissions(JurCodeEnum.LISTAREA.value());
		List<?> topLevelAreaList = areaService.getByParentCode(0);
		for (Object obj : topLevelAreaList) {
			TbcuitmoonArea topArea = (TbcuitmoonArea) obj;
			List<?> twoLevelAreaList = areaService.getByParentCode(topArea
					.getCuitMoonAreaCode());
			topArea.setChildAreas(twoLevelAreaList);
			for (Object obj2 : twoLevelAreaList) {
				TbcuitmoonArea twoArea = (TbcuitmoonArea) obj2;
				List<?> threeLevelAreaList = areaService
						.getByParentCode(twoArea.getCuitMoonAreaCode());
				twoArea.setChildAreas(threeLevelAreaList);
			}
		}
		model.addAttribute("areaList", topLevelAreaList);
		return "sys/area/areaIndex";
	}

	@RequestMapping("areaList")
	public String areaList(
			@RequestParam(required = false) Integer requsetPageNo,
			@RequestParam(required = false) String condition,
			@RequestParam(required = false) String parentCode, Model model) {
		checkPermissions(JurCodeEnum.LISTAREA.value());

		if ((requsetPageNo == null) || (requsetPageNo <= 0)) {
			requsetPageNo = pageNo;
		}

		if (!hasLength(parentCode)) {
			parentCode = SysEnum.SICHUANCODE.value() + "";
		}
		if (hasLength(condition)) {
			condition = toUTF8(condition);
			model.addAttribute("condition", condition);
		}
		Page areaPage = areaService.getByParentId(parentCode, condition,
				requsetPageNo, pageCounSize);

		for (Object object : areaPage.getPageList()) {
			TbcuitmoonArea area = (TbcuitmoonArea) object;
			parentCode = area.getCuitMoonParentAreaCode() + "";
			break;
		}

		model.addAttribute("page", areaPage);
		model.addAttribute("parentCode", parentCode);
		return "sys/area/areaList";
	}

	@RequestMapping("delete")
	public String delete(@RequestParam(required = false) String condition,
			String areaId, String parentCode, RedirectAttributes reAttributes) {
		areaService.deleteByKey(areaId);
		reAttributes.addAttribute("parentCode", parentCode);
		if (hasLength(condition)) {
			condition = toUTF8(condition);
			reAttributes.addAttribute("condition", condition);
		}

		return "redirect:areaList";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addAreaGet(Model model) {
		checkPermissions(JurCodeEnum.ADDAREA.value());
		List<?> topAreaList = areaService.getByParentCode(new Long(0));
		for (int i = 0; i < topAreaList.size(); i++) {
			TbcuitmoonArea topArea = (TbcuitmoonArea) topAreaList.get(i);
			topArea.setChildAreas(areaService.getByParentCode(topArea
					.getCuitMoonAreaCode()));
		}
		model.addAttribute("area", new TbcuitmoonArea());
		model.addAttribute("topAreaList", topAreaList);
		return "sys/area/areaAdd";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addAreaPost(TbcuitmoonArea area, Model model,
			RedirectAttributes reAttributes) {
		checkPermissions(JurCodeEnum.ADDAREA.value());
		if (!hasLength(area.getCuitMoonAreaName())) {

			reAttributes.addFlashAttribute("nameIsNull", "名称不能为空");
			return "redirect:add";
		}
		if (isEmpty(area.getCuitMoonAreaCode())) {

			reAttributes.addFlashAttribute("nameIsNull", "名称不能为空");
			return "redirect:add";
		}

		boolean isExist = areaService.isExistByCode(area.getCuitMoonAreaCode());
		if (isExist) {
			reAttributes.addFlashAttribute("codeExist", "代码已经存在");
			return "redirect:add";
		}

		area.setCuitMoonAreaId(IdGenerator.genRamId().toUpperCase());
		reAttributes.addAttribute("parentCode", area.getCuitMoonParentAreaCode());
		areaService.save(area);
		return "redirect:areaList";
	}

	@RequestMapping(value="edit", method=RequestMethod.GET)
	public String editAreaGet(Model model){
		checkPermissions(JurCodeEnum.EDITAREA.value());
		List<?> topAreaList = areaService.getByParentCode(new Long(0));
		for (int i = 0; i < topAreaList.size(); i++) {
			TbcuitmoonArea topArea = (TbcuitmoonArea) topAreaList.get(i);
			topArea.setChildAreas(areaService.getByParentCode(topArea
					.getCuitMoonAreaCode()));
		}
		model.addAttribute("topAreaList", topAreaList);
		return "sys/area/areaEdit";
	}
	
	@RequestMapping(value="edit", method=RequestMethod.POST)
	public String editAreaPost(@ModelAttribute("area")TbcuitmoonArea area, Model model,
			RedirectAttributes reAttributes){
		checkPermissions(JurCodeEnum.EDITAREA.value());
		if (!hasLength(area.getCuitMoonAreaName())) {

			reAttributes.addFlashAttribute("nameIsNull", "名称不能为空");
			return "redirect:add";
		}
		if (isEmpty(area.getCuitMoonAreaCode())) {

			reAttributes.addFlashAttribute("nameIsNull", "名称不能为空");
			return "redirect:add";
		}

		boolean isExist = areaService.isExistByCodeNotMySelf(area.getCuitMoonAreaId(), area.getCuitMoonAreaCode());
		if (isExist) {
			reAttributes.addFlashAttribute("codeExist", "代码已经存在");
			reAttributes.addAttribute("areaId", area.getCuitMoonAreaId());
			return "redirect:edit";
		}

		reAttributes.addAttribute("parentCode", area.getCuitMoonParentAreaCode());
		areaService.update(area);
		return "redirect:areaList";
	}
}
