package edu.cuit.module.sys.web.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.UnauthorizedException;
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

import edu.cuit.common.exception.CustomException;
import edu.cuit.common.util.IdGenerator;
import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.sys.entity.TbcuitmoonModule;
import edu.cuit.module.sys.entity.TbcuitmoonRole;
import edu.cuit.module.sys.entity.TbcuitmoonRolejurisdiction;
import edu.cuit.module.sys.entity.TbcuitmoonRolemodule;
import edu.cuit.module.sys.entity.TbcuitmoonUserrole;
import edu.cuit.module.sys.enums.JurCodeEnum;
import edu.cuit.module.sys.service.TbcuitmoonJurisdictionService;
import edu.cuit.module.sys.service.TbcuitmoonModuleService;
import edu.cuit.module.sys.service.TbcuitmoonRoleService;
import edu.cuit.module.sys.service.TbcuitmoonRolejurisdictionService;
import edu.cuit.module.sys.service.TbcuitmoonRolemoduleService;

@Controller
@RequestMapping("sys/role")
public class RoleController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;

	@Autowired
	TbcuitmoonRoleService roleService;
	@Autowired
	TbcuitmoonModuleService moduleService;
	@Autowired
	TbcuitmoonJurisdictionService jurisdinctionService;
	@Autowired
	TbcuitmoonRolejurisdictionService roleJurService;
	@Autowired
	TbcuitmoonRolemoduleService roleModuleService;

	@ModelAttribute
	public void getRoleById(@RequestParam(required = false) String roleId,
			Model model) {
		if (hasLength(roleId)) {
			TbcuitmoonRole role = roleService.get(roleId);
			model.addAttribute("role", role);

		} else {
			model.addAttribute("role", new TbcuitmoonModule());
		}
	}

	@RequestMapping("index")
	public String index(Model model) {
		checkPermissions(JurCodeEnum.LISTROLE.value());
		List<?> roleList = roleService.findByOrderNum("0");
		for (Object object : roleList) {
			TbcuitmoonRole role = (TbcuitmoonRole) object;
			role.setChildRoles((List<TbcuitmoonRole>) roleService
					.findByOrderNum(role.getCuitMoonRoleId()));
		}
		model.addAttribute("roleList", roleList);
		return "sys/role/roleIndex";
	}

	/**
	 * 显示角色分页
	 * 
	 * @param requsetPageNo
	 * @param model
	 * @return
	 */
	@RequestMapping("roleList")
	public String roleList(
			@RequestParam(required = false) Integer requsetPageNo,
			@RequestParam(required = false) String condition,
			String parentRoleId, Model model) {
		checkPermissions(JurCodeEnum.LISTROLE.value());
		if ((requsetPageNo == null) || (requsetPageNo <= 0)) {
			requsetPageNo = pageNo;
		}

		/* 通过条件查询 */
		if (hasLength(condition)) {
			condition = toUTF8(condition);
			model.addAttribute("condition", condition);
		}

		Page rolesPage = roleService.listByOrderNum(parentRoleId, condition,
				requsetPageNo, pageCounSize);
		TbcuitmoonRole parentRole = null;
		if (parentRoleId.equals("0")) {
			parentRole = new TbcuitmoonRole();
			parentRole.setCuitMoonRoleId("0");
		} else {
			parentRole = roleService.get(parentRoleId);
		}

		model.addAttribute("page", rolesPage);
		model.addAttribute("parentRole", parentRole);
		return "sys/role/rolesList";
	}

	/**
	 * 显示指定id的角色
	 * 
	 * @param roleId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String editGet(@ModelAttribute("role") TbcuitmoonRole role,
			Model model) {
		List<?> moduleList = moduleService.findWithChildList();
		String parentId = role.getCuitMoonParentRoleId();
		if (!parentId.equals("0")) {
			TbcuitmoonRole parentRole = roleService.get(parentId);
			if(!isEmpty(parentRole)){
				role.setCuitMoonParentRoleName(roleService.get(parentId)
						.getCuitMoonRoleName());
			}
			
		}
		List<?> moduleIds = roleModuleService.roleHasModuleIdList(role.getCuitMoonRoleId());
		model.addAttribute("moduleIds", moduleIds);
		model.addAttribute("moduleList", moduleList);
		return "sys/role/roleEdit";
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String editPost(@ModelAttribute("role") @Valid TbcuitmoonRole role,
			BindingResult result, String jurisdictionIds, String moduleIds, Model model,
			RedirectAttributes redirectAttributes) {
		checkPermissions(JurCodeEnum.EDITROLE.value());
		if (result.hasErrors()) {
			return "sys/role/roleEdit";
		}
		if (hasLength(jurisdictionIds)) {
			roleJurService.deleteByRoleId(role.getCuitMoonRoleId());
			String[] jurIdArray = jurisdictionIds.split(",");
			for (int i = 0; i < jurIdArray.length; i++) {
				TbcuitmoonRolejurisdiction roleJur = new TbcuitmoonRolejurisdiction(
						IdGenerator.genRamId().toUpperCase(),
						role.getCuitMoonRoleId(), jurIdArray[i]);
				roleJurService.save(roleJur);
				if (i % 10 == 0) {
					roleJurService.flush();
					roleJurService.clear();
				}
			}
		}
		if (hasLength(moduleIds)) {
			String[] moduleIdArray = moduleIds.split(",");
			roleModuleService.bulkUpdate("delete from TbcuitmoonRolemodule as roleModule where roleModule.cuitMoonRoleId=?",role.getCuitMoonRoleId());
			for (int i = 0; i < moduleIdArray.length; i++) {
				TbcuitmoonRolemodule roleModule = new TbcuitmoonRolemodule(
						IdGenerator.genRamId().toUpperCase(),
						role.getCuitMoonRoleId(), moduleIdArray[i]);
				roleModuleService.save(roleModule);
				if (i % 10 == 0) {
					roleModuleService.flush();
					roleModuleService.clear();
				}
			}
		}
		roleService.update(role);

		redirectAttributes.addAttribute("parentRoleId",
				role.getCuitMoonParentRoleId());
		return "redirect:roleList";
	}

	@RequestMapping("delete")
	public String deleteRole(String roleId, RedirectAttributes reAttributes) {
		TbcuitmoonRole role = roleService.get(roleId);
		if(!isEmpty(role)){
			reAttributes.addAttribute("parentRoleId",
					role.getCuitMoonParentRoleId());
			roleService.delete(role);
		}
		return "redirect:roleList";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addRoleGet(Model model, String parentRoleId) {
		List<?> moduleList = moduleService.findWithChildList();
		model.addAttribute("moduleList", moduleList);
		TbcuitmoonRole role = new TbcuitmoonRole();
		role.setCuitMoonParentRoleId(parentRoleId);
		TbcuitmoonRole parentRole = roleService.get(parentRoleId);
		if(!isEmpty(parentRole)){
			role.setCuitMoonParentRoleName(parentRole.getCuitMoonRoleName());
		}
		model.addAttribute("role", role);
		return "sys/role/roleAdd";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addRolePost(TbcuitmoonRole role,String jurisdictionIds, String moduleIds, Model model,
			RedirectAttributes reAttributes) {
		role.setCuitMoonRoleId(IdGenerator.genRamId().toUpperCase());
		role.setCuitMoonRoleOrderNum(1);
		role.setCuitMoonRoleLevel(0);
		
		if (hasLength(jurisdictionIds)) {
			String[] jurIdArray = jurisdictionIds.split(",");
			for (int i = 0; i < jurIdArray.length; i++) {
				TbcuitmoonRolejurisdiction roleJur = new TbcuitmoonRolejurisdiction(
						IdGenerator.genRamId().toUpperCase(),
						role.getCuitMoonRoleId(), jurIdArray[i]);
				roleJurService.save(roleJur);
				if (i % 10 == 0) {
					roleJurService.flush();
					roleJurService.clear();
				}
			}
		}
		
		if (hasLength(moduleIds)) {
			String[] moduleIdArray = moduleIds.split(",");
			for (int i = 0; i < moduleIdArray.length; i++) {
				TbcuitmoonRolemodule roleModule = new TbcuitmoonRolemodule(
						IdGenerator.genRamId().toUpperCase(),
						role.getCuitMoonRoleId(), moduleIdArray[i]);
				roleModuleService.save(roleModule);
				if (i % 10 == 0) {
					roleModuleService.flush();
					roleModuleService.clear();
				}
			}
		}
		
		roleService.save(role);
		reAttributes.addAttribute("parentRoleId",
				role.getCuitMoonParentRoleId());

		return "redirect:roleList";
	}

}
