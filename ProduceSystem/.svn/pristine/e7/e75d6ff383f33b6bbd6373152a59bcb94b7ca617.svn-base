package edu.cuit.module.sys.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.dom4j.util.UserDataAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.Module;

import edu.cuit.common.util.DateFormat;
import edu.cuit.common.util.IdGenerator;
import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.sys.entity.TbcuitmoonArea;
import edu.cuit.module.sys.entity.TbcuitmoonJurisdiction;
import edu.cuit.module.sys.entity.TbcuitmoonUser;
import edu.cuit.module.sys.entity.TbcuitmoonUserrole;
import edu.cuit.module.sys.enums.JurCodeEnum;
import edu.cuit.module.sys.enums.SysEnum;
import edu.cuit.module.sys.service.TbcuitmoonAreaService;
import edu.cuit.module.sys.service.TbcuitmoonJurisdictionService;
import edu.cuit.module.sys.service.TbcuitmoonRoleService;
import edu.cuit.module.sys.service.TbcuitmoonUserService;
import edu.cuit.module.sys.service.TbcuitmoonUserroleService;

@Controller
@RequestMapping("sys/user")
public class UserController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;

	@Autowired
	TbcuitmoonUserService userService;
	@Autowired
	TbcuitmoonAreaService areaService;
	@Autowired
	TbcuitmoonJurisdictionService jurisdictionService;
	@Autowired
	TbcuitmoonRoleService roleService;
	@Autowired
	TbcuitmoonUserroleService userRoleService;

	@ModelAttribute
	public void getUserById(@RequestParam(required = false) String userId,
			Model model) {
		if (hasLength(userId)) {
			TbcuitmoonUser user = userService.get(userId);
			model.addAttribute("user", user);
		}
	}
	
	/**
	 * 用户 管理首页
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(Model model) {
		checkPermissions(JurCodeEnum.LISTUSER.value());
		/* 获取父级id为0的地区 */
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
		return "sys/user/userIndex";
	}
	
	/**
	 * 根据地区，搜索条件，分页获取用户列表
	 * @param requsetPageNo
	 * @param condition
	 * @param areaCode
	 * @param model
	 * @return
	 */
	@RequestMapping("userList")
	public String getUserListByAreaCode(
			@RequestParam(required = false) Integer requsetPageNo,
			@RequestParam(required = false) String conditionRealname,
			@RequestParam(required = false) String conditionUsername,
			String areaCode,
			Model model) {
		checkPermissions(JurCodeEnum.LISTUSER.value());
		if ((requsetPageNo == null) || (requsetPageNo <= 0)) {
			requsetPageNo = pageNo;
		}
		if (!hasLength(areaCode)) {
			areaCode = SysEnum.SICHUANCODE.value() + "";
		}
		Page page = null;

		if (hasLength(conditionRealname,conditionUsername)) {
			conditionRealname = toUTF8(conditionRealname);
			conditionUsername = toUTF8(conditionUsername);
			page = userService.findByConditions(conditionUsername,conditionRealname,
					requsetPageNo, pageCounSize);
			model.addAttribute("conditionRealname", conditionRealname);
			model.addAttribute("conditionUsername", conditionUsername);

		}else if(hasLength(conditionRealname) && !hasLength(conditionUsername)){
			conditionRealname = toUTF8(conditionRealname);
			String hql = "from TbcuitmoonUser as user where user.cuitMoonUserRealName like '%"+conditionRealname+"%'";
			page = userService.getPage(hql,requsetPageNo-1,pageCounSize);
			model.addAttribute("conditionRealname", conditionRealname);
		}else if(!hasLength(conditionRealname) && hasLength(conditionUsername)){
			conditionUsername = toUTF8(conditionUsername);
			String hql = "from TbcuitmoonUser as user where user.cuitMoonUserName like '%"+conditionUsername+"%'";
			page = userService.getPage(hql,requsetPageNo-1,pageCounSize);
			model.addAttribute("conditionUsername", conditionUsername);
		} else {
			page = userService.findByAreaCode(areaCode, requsetPageNo,
					pageCounSize);
		}

		model.addAttribute("page", page);
		model.addAttribute("areaCode", areaCode);

		return "sys/user/userList";
	}

	@RequestMapping("delete")
	public String delete(@RequestParam(required = false) String condition,
			String userId, String areaCode,
			RedirectAttributes redirectAttributes) {
		checkPermissions(JurCodeEnum.DELETEUSER.value());

		if (hasLength(userId)) {
			userService.deleteByKey(userId);
		}
		if (!hasLength(areaCode)) {
			areaCode = SysEnum.SICHUANCODE.value() + "";
		}
		if (hasLength(condition)) {
			condition = toUTF8(condition);
			redirectAttributes.addAttribute("condition", condition);

		}
		redirectAttributes.addFlashAttribute("areaCode", areaCode);
		return "redirect:userList";
	}
	
	/**
	 * 转到用户信息修改页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="edit", method=RequestMethod.GET)
	public String editUserGet(@ModelAttribute("user") TbcuitmoonUser user, Model model) {
		checkPermissions(JurCodeEnum.EDITUSER.value());
		List<?> areaList = areaService.getByParentCode(0);
		for(Object obj : areaList){
			TbcuitmoonArea areaPro = (TbcuitmoonArea)obj;
			List<?> areaCityList = areaService.getByParentCode(areaPro.getCuitMoonAreaCode());
			areaPro.setChildAreas(areaCityList);
			for(Object obj2 : areaCityList){
				TbcuitmoonArea areaCity = (TbcuitmoonArea)obj2;
				List<?> areaCountryList = areaService.getByParentCode(areaCity.getCuitMoonAreaCode());
				areaCity.setChildAreas(areaCountryList);
			}
		}
		List<?> roles = roleService.findByState(SysEnum.ROLESTATEABLE.value());

		model.addAttribute("roles", roles);
		model.addAttribute("areaList", areaList);
		return "sys/user/userEdit";
	}
	/**
	 * 更新用户信息
	 * @param user
	 * @param password
	 * @param reNewPassword
	 * @param roleIds
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateUserInfo(@ModelAttribute("user") TbcuitmoonUser user,
			@RequestParam(required = false) String password,
			@RequestParam(required = false) String reNewPassword, String roleIds,String userBirthday, RedirectAttributes reAttributes) {
		checkPermissions(JurCodeEnum.EDITUSER.value());
		if (hasLength(roleIds)) {
			String[] roleIdsArray = roleIds.split(",");
			/* 先删除已有的权限再保存 */
			userRoleService.deleteByUserId(user.getCuitMoonUserId());
			for (int i = 0; i < roleIdsArray.length; i++) {
				TbcuitmoonUserrole userRole = new TbcuitmoonUserrole(
						IdGenerator.genRamId(), user.getCuitMoonUserId(),
						roleIdsArray[i]);
				userRoleService.save(userRole);
			}
		}
		if (hasLength(password, reNewPassword)) {
			if (password.equals(reNewPassword)) {
				String md5Password = new SimpleHash("md5", password, null, 1)
						.toHex().toUpperCase();
				user.setCuitMoonUserPassWord(md5Password);
			}
		}
		if(hasLength(userBirthday)){
			user.setCuitMoonUserBirthday(DateFormat.strToDate(userBirthday));
		}
		userService.update(user);
		reAttributes.addAttribute("areaCode", user.getCuitMoonAreaId());
		return "redirect:userList";
	}
	
	/**
	 * 添加用户
	 * @param model
	 * @return
	 */
	@RequestMapping(value="add", method=RequestMethod.GET)
	public String addUserGet(Model model){
		checkPermissions(JurCodeEnum.ADDUSER.value());
		TbcuitmoonUser user = new TbcuitmoonUser();
		List<?> areaList = areaService.getByParentCode(0);
		for(Object obj : areaList){
			TbcuitmoonArea areaPro = (TbcuitmoonArea)obj;
			List<?> areaCityList = areaService.getByParentCode(areaPro.getCuitMoonAreaCode());
			areaPro.setChildAreas(areaCityList);
			for(Object obj2 : areaCityList){
				TbcuitmoonArea areaCity = (TbcuitmoonArea)obj2;
				List<?> areaCountryList = areaService.getByParentCode(areaCity.getCuitMoonAreaCode());
				areaCity.setChildAreas(areaCountryList);
			}
		}
		List<?> roles = roleService.findByState(SysEnum.ROLESTATEABLE.value());

		model.addAttribute("user",user);
		model.addAttribute("areaList", areaList);
		model.addAttribute("roles", roles);
		return "sys/user/userAdd";
	}
	@RequestMapping(value="add", method=RequestMethod.POST)
	public String addUserPost(TbcuitmoonUser user, String roleIds, RedirectAttributes reAttributes){
		user.setCuitMoonUserId(IdGenerator.genRamId().toUpperCase());
		String md5Password = new SimpleHash("md5", user.getCuitMoonUserPassWord(), null, 1).toHex().toUpperCase();
		user.setCuitMoonUserPassWord(md5Password);
		
		if(userService.hasUsername(user.getCuitMoonUserName())){
			reAttributes.addFlashAttribute("errorMsg", "用户名已经存在");
			return "redirect:add";
		}else{
			userService.save(user);
			if (hasLength(roleIds)) {
				String[] roleIdsArray = roleIds.split(",");
				/* 先删除已有的权限再保存 */
				userRoleService.deleteByUserId(user.getCuitMoonUserId());
				for (int i = 0; i < roleIdsArray.length; i++) {
					TbcuitmoonUserrole userRole = new TbcuitmoonUserrole(
							IdGenerator.genRamId(), user.getCuitMoonUserId(),
							roleIdsArray[i]);
					userRoleService.save(userRole);
				}
			}
		}
		
		
		reAttributes.addAttribute("areaCode", user.getCuitMoonAreaId().trim());
		return "redirect:userList";
	}
	
	@RequestMapping(value="validUsername", method=RequestMethod.GET)
	@ResponseBody
	public String validUsername(String username){
		String result;
		if(userService.hasUsername(username)){
			result = "true";
		}else{
			result = "false";
		}
		return result;
	}
	

}
