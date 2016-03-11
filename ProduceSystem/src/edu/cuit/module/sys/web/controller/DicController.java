package edu.cuit.module.sys.web.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam.Mode;
import javax.validation.Valid;

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
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;
import edu.cuit.module.sys.enums.JurCodeEnum;
import edu.cuit.module.sys.enums.SysEnum;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;

@Controller
@RequestMapping("sys/dic")
public class DicController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;

	@Autowired
	TbcuitmoonDictionaryService dicService;

	@ModelAttribute
	public void getDicById(@RequestParam(required = false) String dicId,
			Model model) {
		if (hasLength(dicId)) {
			TbcuitmoonDictionary dic = dicService.get(dicId);
			model.addAttribute("dic", dic);
		}
	}

	@RequestMapping("index")
	public String index(Model model) {
		checkPermissions(JurCodeEnum.LISTDIC.value());
		List<?> dicList = dicService.findByParentCode(0l);
		for (Object obj : dicList) {
			TbcuitmoonDictionary parentDic = (TbcuitmoonDictionary) obj;
			List<?> childDicList = dicService.findByParentCode(parentDic
					.getCuitMoonDictionaryCode());
			if (childDicList.size() == 0) {
				parentDic.setHasChild(false);
			} else {
				parentDic.setHasChild(true);
			}
		}
		model.addAttribute("dicList", dicList);
		return "sys/dic/dicIndex";
	}

	@RequestMapping("getChildList")
	@ResponseBody
	public List<?> getChildList(Long parentCode) {
		checkPermissions(JurCodeEnum.LISTDIC.value());
		List<?> dicList = null;
		if (!isEmpty(parentCode)) {
			dicList = dicService.findByParentCode(parentCode);
			for (Object obj : dicList) {
				TbcuitmoonDictionary parentDic = (TbcuitmoonDictionary) obj;
				List<?> childDicList = dicService.findByParentCode(parentDic
						.getCuitMoonDictionaryCode());
				if (childDicList.size() == 0) {
					parentDic.setHasChild(false);
				} else {
					parentDic.setHasChild(true);
				}
			}
		}
		return dicList;
	}

	@RequestMapping("dicList")
	public String areaList(
			@RequestParam(required = false) Integer requsetPageNo,
			@RequestParam(required = false) String condition, Long parentCode,
			Model model) {

		checkPermissions(JurCodeEnum.LISTDIC.value());

		if ((requsetPageNo == null) || (requsetPageNo <= 0)) {
			requsetPageNo = pageNo;
		}

		if (isEmpty(parentCode)) {
			parentCode = (long) SysEnum.DICTOPCODE.value();
		}
		if (hasLength(condition)) {
			condition = toUTF8(condition);
			model.addAttribute("condition", condition);
		}
		Page dicPage = dicService.getByParentId(parentCode, condition,
				requsetPageNo, pageCounSize);

		for (Object object : dicPage.getPageList()) {
			TbcuitmoonDictionary area = (TbcuitmoonDictionary) object;
			parentCode = area.getCuitMoonParentDictionaryCode();
			break;
		}

		model.addAttribute("page", dicPage);
		model.addAttribute("parentCode", parentCode);
		return "sys/dic/dicList";
	}

	@RequestMapping(value="edit", method=RequestMethod.GET)
	public String editGet(Model model) {
		checkPermissions(JurCodeEnum.EDITDIC.value());
		List<?> dicList = dicService.loadAll();
		model.addAttribute("dicList", dicList);
		return "sys/dic/dicEdit";
	}
	
	@RequestMapping(value="edit", method=RequestMethod.POST)
	public String editPost(@ModelAttribute("dic") @Valid TbcuitmoonDictionary dic, BindingResult result ,Model model) {
		checkPermissions(JurCodeEnum.EDITDIC.value());
		dicService.clear();
		if(result.hasErrors()){
			List<?> dicList = dicService.loadAll();
			model.addAttribute("dicList", dicList);
			return "sys/dic/dicEdit";
		}
		boolean isExist = dicService.hasThisCodeNotMe(dic.getCuitMoonDictionaryId(),dic.getCuitMoonDictionaryCode());
		if(isExist){
			List<?> dicList = dicService.loadAll();
			model.addAttribute("dicList", dicList);
			model.addAttribute("codeExist", "代码已经存在");
			return "sys/dic/dicEdit";
		}
		dicService.update(dic);
		return "sys/dic/dicEdit";
	}
	
	
	@RequestMapping("delete")
	public String delete(@ModelAttribute("dic") TbcuitmoonDictionary dic ,Long parentCode, Model model){
		model.addAttribute("parentCode", parentCode);
		checkPermissions(JurCodeEnum.DELETEDIC.value());
		dicService.delete(dic);
		return "redirect:dicList";
	}
	
	@RequestMapping(value="add", method=RequestMethod.GET)
	public String addGet(Model model) {
		checkPermissions(JurCodeEnum.ADDDIC.value());
		List<?> dicList = dicService.loadAll();
		model.addAttribute("dicList", dicList);
		model.addAttribute("dic", new TbcuitmoonDictionary());
		return "sys/dic/dicAdd";
	}
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	public String addPost(@Valid TbcuitmoonDictionary dic, BindingResult result ,Model model, RedirectAttributes redirectAttributes) {
		checkPermissions(JurCodeEnum.ADDDIC.value());
		if(result.hasErrors()){
			List<?> dicList = dicService.loadAll();
			model.addAttribute("dicList", dicList);
			model.addAttribute("dic", dic);
			return "sys/dic/dicAdd";
		}
		boolean isExist = dicService.hasThisCode(dic.getCuitMoonDictionaryCode());
		if(isExist){
			List<?> dicList = dicService.loadAll();
			model.addAttribute("dicList", dicList);
			model.addAttribute("dic", dic);
			model.addAttribute("codeExist", "代码已经存在");
			return "sys/dic/dicAdd";
		}
		dic.setCuitMoonDictionaryId(IdGenerator.genRamId().toUpperCase());
		dicService.save(dic);
		redirectAttributes.addAttribute("parentCode", dic.getCuitMoonParentDictionaryCode());
		return "redirect:dicList";
	}
}
