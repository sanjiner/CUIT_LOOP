package edu.cuit.module.sys.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.From;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.sys.entity.TbcuitmoonModule;
import edu.cuit.module.sys.entity.TbcuitmoonRole;
import edu.cuit.module.sys.entity.TbcuitmoonRolemodule;
import edu.cuit.module.sys.entity.TbcuitmoonUser;
import edu.cuit.module.sys.service.TbcuitmoonModuleService;
import edu.cuit.module.sys.service.TbcuitmoonRolemoduleService;
import edu.cuit.module.sys.service.TbcuitmoonUserService;

@Controller
@RequestMapping("sys")
public class ManageController extends BaseController {
	@Autowired
	TbcuitmoonUserService userService;
	@Autowired
	TbcuitmoonModuleService moduleService;
	@Autowired
	TbcuitmoonRolemoduleService roleModuleService;

	/**
	 * 显示主页面
	 * 
	 * @return
	 */
	@RequestMapping("mainPage")
	public String getMainPage(HttpSession session, Model model) {
		List<?> moduleList = moduleService.findWithChildList();
		TbcuitmoonUser user = getLoginUser(session);
		List<String> roleIdList =new ArrayList<String>();
		for (TbcuitmoonRole role : user.getTbcuitmoonRoles()) {
			roleIdList.add(role.getCuitMoonRoleId());
		}
		for (int i=0; i<moduleList.size(); i++) {
			TbcuitmoonModule parentModule = (TbcuitmoonModule) moduleList.get(i);
			
			List<?> childModuleList = parentModule.getChildModulesList();
			for(int j=0;j<childModuleList.size(); j++){
				TbcuitmoonModule childModule = (TbcuitmoonModule) childModuleList.get(j);
				boolean hasThisModule = roleModuleService.hasThisModule(childModule.getCuitMoonModuleId(),roleIdList);
				if(!hasThisModule){
					childModuleList.remove(j);
					j--;
				}
			}
			if(parentModule.getChildModulesList().size()==0){
				moduleList.remove(i);
				i--;
			}
			
		}
		
		model.addAttribute("moduleList", moduleList);
		return "mainPage/mainPage";
	}
	
	/**
	 * 显示欢迎页面
	 * @return
	 */
	@RequestMapping("welcome")
	public String welcome(){
		return "mainPage/welcome";
	}


	/**
	 * 修改密码
	 */
	@RequestMapping(value = "changePassword", method = RequestMethod.GET)
	public String changePasswordGet() {
		return "sys/changepwd";
	}

	/**
	 * 修改密码 post
	 */
	@RequestMapping(value = "changePassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> changePasswordPost(String oldPassword,
			String newPassword, String reNewPassword, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		if (hasLength(oldPassword, newPassword, reNewPassword)) {

			if (newPassword.equals(reNewPassword)) {
				TbcuitmoonUser loginUser = getLoginUser(session);
				String md5NewPassword = new SimpleHash("md5", newPassword,
						null, 1).toHex().toUpperCase();
				String md5OldPassword = new SimpleHash("md5", oldPassword,
						null, 1).toHex().toUpperCase();
				boolean result = userService.changePassword(
						loginUser.getCuitMoonUserId(), md5OldPassword,
						md5NewPassword);
				if (result) {
					loginUser.setCuitMoonUserPassWord(newPassword);
					modifyLoginUser(session, loginUser);
					map.put("msg", "success");
				} else {
					map.put("msg", "fail");
				}
			} else {
				map.put("msg", "fail");
			}

		} else {
			map.put("msg", "fail");
		}
		return map;
	}

}
