package edu.cuit.module.sys.web.controller;

import java.util.HashSet;
import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.cuit.common.util.IdGenerator;
import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.sys.entity.TbcuitmoonJurisdiction;
import edu.cuit.module.sys.entity.TbcuitmoonModule;
import edu.cuit.module.sys.enums.JurCodeEnum;
import edu.cuit.module.sys.service.TbcuitmoonJurisdictionService;
import edu.cuit.module.sys.service.TbcuitmoonModuleService;
import edu.cuit.module.sys.service.TbcuitmoonRolejurisdictionService;
/**
 * 权限管理
 * @author Jialong
 *
 */
@Controller
@RequestMapping("sys/jurisdiction")
public class JurisdictionController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;

	@Autowired
	TbcuitmoonJurisdictionService jurisdictionService;
	@Autowired
	TbcuitmoonModuleService moduleService;
	@Autowired
	TbcuitmoonRolejurisdictionService roleJurService;
	
	@ModelAttribute
	public void getJurisdictionById(@RequestParam(required=false) String jurisdictionId, Model model){
		if(hasLength(jurisdictionId)){
			model.addAttribute("jurisdiction", jurisdictionService.get(jurisdictionId));
		}else{
			model.addAttribute("jurisdiction", new TbcuitmoonJurisdiction());
		}
	}
	
	/**
	 * 权限首页
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model) {
		checkPermissions("JurisdictionList");
		List<?> topLevelModuleList = moduleService.getTopLevelModule();
		for (Object obj : topLevelModuleList) {
			TbcuitmoonModule topLevelModule = (TbcuitmoonModule) obj;
			List<?> childModuleList = moduleService
					.getByParentId(topLevelModule.getCuitMoonModuleId());
			topLevelModule.setChildModules(new HashSet(childModuleList));
		}
		model.addAttribute("moduleList", topLevelModuleList);
		return "sys/jurisdiction/jurisdictionIndex";
	}

	@RequestMapping("jurisdictionList")
	public String jurisdictionList(
			@RequestParam(required = false) Integer requsetPageNo,
			@RequestParam(required = false) String condition,
			String moduleId, Model model) {
		checkPermissions("JurisdictionList");
		if ((requsetPageNo == null) || (requsetPageNo <= 0)) {
			requsetPageNo = pageNo;
		}
		if (hasLength(moduleId)) {
			/* 通过条件查询 */
			if (hasLength(condition)) {
				condition = toUTF8(condition);
				model.addAttribute("condition", condition);
			}
			
			Page jurisdictionPage = jurisdictionService.listByModule(moduleId, condition,
					requsetPageNo, pageCounSize);
			model.addAttribute("page", jurisdictionPage);
			model.addAttribute("module", moduleService.get(moduleId));
		}
		return "sys/jurisdiction/jurisdictionList";
	}

	/**
	 * 跳转添加页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addJurisdictionGet(String moduleId, Model model) {
		checkPermissions(JurCodeEnum.ADDJURISDICTION.value());
		model.addAttribute("jurisdiction", new TbcuitmoonJurisdiction());
		model.addAttribute("module", moduleService.get(moduleId));
		return "sys/jurisdiction/addJurisdiction";
	}

	/**
	 * 处理添加 post 请求
	 * 
	 * @param jurisdiction
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addJurisdictionPost(@ModelAttribute("jurisdiction") @Valid TbcuitmoonJurisdiction jurisdiction,
			BindingResult result, Model model,RedirectAttributes redirectAttributes) {
		checkPermissions(JurCodeEnum.ADDJURISDICTION.value());
		if (result.hasErrors()) {
			return "sys/jurisdiction/addJurisdiction";
		}
		jurisdiction.setCuitMoonJurisdictionId(IdGenerator.genRamId().toUpperCase());
		jurisdictionService.save(jurisdiction);
		redirectAttributes.addAttribute("moduleId", jurisdiction.getCuitMoonModuleId());
		return "redirect:jurisdictionList";
	}
	
	/**
	 * 根据id删除权限
	 * @return
	 */
	@RequestMapping("delete")
	public String delete(@ModelAttribute("jurisdiction") TbcuitmoonJurisdiction jurisdiction, RedirectAttributes redirectAttributes){
		checkPermissions(JurCodeEnum.DELTEJURISDICTION.value());
		jurisdictionService.delete(jurisdiction);
		/*同时删除 中间表的记录*/
		roleJurService.deleteByJurId(jurisdiction.getCuitMoonJurisdictionId());
		redirectAttributes.addAttribute("moduleId", jurisdiction.getCuitMoonModuleId());
		return "redirect:jurisdictionList";
	}
	
	
	@RequestMapping(value = "edit" , method=RequestMethod.GET)
	public String editGet(@ModelAttribute("jurisdiction") TbcuitmoonJurisdiction jurisdiction, Model model){
		checkPermissions(JurCodeEnum.EDITJURISDICTION.value());
		model.addAttribute("module", moduleService.get(jurisdiction.getCuitMoonModuleId()));
		return "sys/jurisdiction/jurisdictionEdit";
	}
	
	@RequestMapping(value = "edit" , method=RequestMethod.POST)
	public String editPost(@ModelAttribute("jurisdiction") @Valid TbcuitmoonJurisdiction jurisdiction,BindingResult result, RedirectAttributes redirectAttributes){
		checkPermissions(JurCodeEnum.EDITJURISDICTION.value());
		if (result.hasErrors()) {
			return "sys/jurisdiction/jurisdictionEdit";
		}
		jurisdictionService.update(jurisdiction);
		redirectAttributes.addAttribute("moduleId", jurisdiction.getCuitMoonModuleId());
		return "redirect:jurisdictionList";
	}
}
