package edu.cuit.module.sys.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javassist.expr.NewArray;

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

import ch.qos.logback.core.joran.conditional.IfAction;
import edu.cuit.common.util.IdGenerator;
import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.sys.entity.TbcuitmoonModule;
import edu.cuit.module.sys.enums.IdEnum;
import edu.cuit.module.sys.enums.JurCodeEnum;
import edu.cuit.module.sys.service.TbcuitmoonModuleService;
/**
 * 模块管理
 * @author Jialong
 *
 */
@Controller
@RequestMapping("sys/module")
public class ModuleController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;

	@Autowired
	TbcuitmoonModuleService tbcuitmoonModuleService;

	/**
	 * 显示模块分页
	 * 
	 * @param requsetPageNo
	 * @param model
	 * @return
	 */
	@ModelAttribute
	public void getModuleById(
			@RequestParam(value = "cuitMoonModuleId", required = false) String moduleId,
			Model model) {
		if (hasLength(moduleId)) {
			TbcuitmoonModule module = tbcuitmoonModuleService.get(moduleId);
			String parentId = module.getCuitMoonParentModuleId();
			/* 0 表示该模块为顶级模块 */
			if (!parentId.equals("0")) {
				String parentName = tbcuitmoonModuleService.get(parentId)
						.getCuitMoonModuleName();
				module.setCuitMoonParentModuleName(parentName);
			}
			model.addAttribute("module", tbcuitmoonModuleService.get(moduleId));

		} else {
			model.addAttribute("module", new TbcuitmoonModule());
		}
	}

	/**
	 * 显示模块主页， 父级模块
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String Index(Model model) {
		List<?> moduleList = tbcuitmoonModuleService
				.findWithChildListWithoutState();
		model.addAttribute("moduleList", moduleList);
		return "sys/module/moduleIndex";
	}

	/**
	 * 
	 * @param requsetPageNo
	 * @param condition
	 *            搜索条件
	 * @param parentModuelId
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("modulesList")
	public String moduleList(
			@RequestParam(required = false) Integer requsetPageNo,
			@RequestParam(required = false) String condition,
			String parentModuelId, Model model) {
		checkPermissions(JurCodeEnum.LISTMODULE.value());
		if ((requsetPageNo == null) || (requsetPageNo <= 0)) {
			requsetPageNo = pageNo;
		}
		if (hasLength(parentModuelId)) {
			/* 通过条件查询 */
			if (hasLength(condition)) {
				condition = toUTF8(condition);
				model.addAttribute("condition", condition);
			}

			Page modulePage = tbcuitmoonModuleService
					.listChildModuleByOrderNum(parentModuelId, condition,
							requsetPageNo, pageCounSize);
			TbcuitmoonModule parentModule = tbcuitmoonModuleService
					.get(parentModuelId);
			model.addAttribute("modulePage", modulePage);
			model.addAttribute("parentModule", parentModule);
		}

		return "sys/module/modulesList";
	}

	/**
	 * 显示指定模块
	 * 
	 * @param moduleId
	 * @return
	 */
	@RequestMapping("show")
	public String show(@ModelAttribute("module") TbcuitmoonModule module,
			Model model) {
		checkPermissions(JurCodeEnum.LISTMODULE.value());
		return "sys/module/moduleDetail";
	}

	/**
	 * 修改模块属性
	 * 
	 * @param module
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "editModule", method = RequestMethod.POST)
	public String editModuleInfo(
			@ModelAttribute("module") @Valid TbcuitmoonModule module,
			BindingResult result, Model model, RedirectAttributes reAttributes) {
		checkPermissions(JurCodeEnum.EDITMODULE.value());
		if (result.hasErrors()) {
			return "sys/module/moduleDetail";
		}
		tbcuitmoonModuleService.update(module);
		reAttributes.addAttribute("parentModuelId", module.getCuitMoonParentModuleId());
		return "redirect:modulesList";
	}

	@RequestMapping("delete")
	public String deleteMoule(String cuitMoonModuleId,RedirectAttributes reAttributes) {
		checkPermissions(JurCodeEnum.DELETEMODULE.value());
		TbcuitmoonModule module = null;
		if (hasLength(cuitMoonModuleId)) {
			module = tbcuitmoonModuleService.get(cuitMoonModuleId);
			tbcuitmoonModuleService.delete(module);
		}
		if(isEmpty(module)){
			reAttributes.addAttribute("parentModuelId", IdEnum.ROOTMODULEID.value());
		}else{
			reAttributes.addAttribute("parentModuelId", module.getCuitMoonParentModuleId());
			
		}
		
		return "redirect:modulesList";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addModuleGet(String parentModuelId,Model model) {
		
		checkPermissions(JurCodeEnum.ADDMODULE.value());
		
		TbcuitmoonModule module = new TbcuitmoonModule();
		if(hasLength(parentModuelId)){
			TbcuitmoonModule parentModule = tbcuitmoonModuleService.get(parentModuelId);
			if(isEmpty(parentModule)){
				module.setCuitMoonParentModuleId("0");
			}else{
				module.setCuitMoonParentModuleId(parentModule.getCuitMoonModuleId());
				module.setCuitMoonParentModuleName(parentModule.getCuitMoonModuleName());
			}
		}else{
			module.setCuitMoonParentModuleId("0");
		}
		model.addAttribute("module", module);
		return "sys/module/moduleAdd";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addModulePost(
			@ModelAttribute("module") @Valid TbcuitmoonModule module,
			BindingResult result, String parentId, RedirectAttributes reAttributes) {
		checkPermissions(JurCodeEnum.ADDMODULE.value());
		if (result.hasErrors()) {
			return "sys/module/moduleAdd";
		}
		module.setCuitMoonModuleId(IdGenerator.genRamId().toUpperCase());
		/*设置module ordernum*/
		int orderNum = tbcuitmoonModuleService.getMaxOrderNum(module.getCuitMoonParentModuleId());
		if(orderNum != -1){
			module.setCuitMoonModuleOrderNum(orderNum+1);
		}
		
		tbcuitmoonModuleService.save(module);
		reAttributes.addAttribute("parentModuelId", module.getCuitMoonParentModuleId());
		return "redirect:modulesList";
	}
	
	/**
	 * 模块排序
	 * @param type 排序方式  ,值asc, desc
	 * @param moduleId 要改顺序的模块id
	 * @return
	 */
	@RequestMapping("sort")
	public String sort(String type,String moduleId, RedirectAttributes redirectAttributes){
		if(hasLength(type,moduleId)){
			TbcuitmoonModule module = tbcuitmoonModuleService.get(moduleId);
			boolean result = tbcuitmoonModuleService.sort(module,type);
			if(result){
				redirectAttributes.addAttribute("parentModuelId", module.getCuitMoonParentModuleId());
			}else{
				redirectAttributes.addAttribute("parentModuelId", "0");
			}
		}else{
			redirectAttributes.addAttribute("parentModuelId", "0");
		}
		return "redirect:modulesList";
	}
}
