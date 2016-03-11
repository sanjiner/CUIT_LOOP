package edu.cuit.module.origin.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.infom.service.BusinessmanagementService;
import edu.cuit.module.infom.service.BusinessqualificationService;
import edu.cuit.module.model.service.ProductinfomationService;
import edu.cuit.module.origin.entity.Productapply;
import edu.cuit.module.origin.service.CertificateService;
import edu.cuit.module.origin.service.ProductapplyService;
import edu.cuit.module.sys.entity.TbcuitmoonRole;
import edu.cuit.module.sys.entity.TbcuitmoonUser;
import edu.cuit.module.sys.service.TbcuitmoonAreaService;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;
import edu.cuit.module.sys.service.TbcuitmoonUserService;

@Controller
@RequestMapping("org_pro")
public class OrgProCheckController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;
	
	
	@Autowired
	ProductapplyService productapplyService;
	@Autowired
	TbcuitmoonUserService TbcuitmoonUserService;
	@Autowired
	TbcuitmoonAreaService tbAreaService;
	@Autowired
	TbcuitmoonDictionaryService tbDicService;
	@Autowired
	ProductinfomationService pfService;
	@Autowired
	ProductapplyService paService;
	@Autowired
	BusinessqualificationService bsqService;
	@Autowired
	BusinessmanagementService bsService;
	@Autowired
	CertificateService certSrevice;
	
	@RequestMapping("list")
	public String list(Integer requsetPageNo, Model model,
			HttpSession session){
		Page page;
		if (requsetPageNo == null) {
			requsetPageNo = pageNo;
		}
		TbcuitmoonUser user = getLoginUser(session);
		String role = user == null ? "" : ((TbcuitmoonRole) user
				.getTbcuitmoonRoles().toArray()[0]).getCuitMoonRoleName() + "";
		String hql = "";

		if (user != null && role.equals("商家")) {
			hql = "from Productapply where applyPerson = '"
					+ user.getCuitMoonUserName() + "'  order by applyTime desc";
		} else {
			hql = "from Productapply as T where T.originAddress like '%"
					+ user.getCuitMoonAreaId() + "%' order by applyTime desc";
		}
		log.debug("pageNO {}", requsetPageNo);
		page = productapplyService
				.getPage(hql, requsetPageNo - 1, pageCounSize);
		for (Productapply apply : (List<Productapply>) page.getPageList()) {
			apply.setOrignStatus(tbDicService.getDicNameByCode(apply.getOrignStatus()));
		}
		model.addAttribute("applyPage", page);
		return "pro_check/org_list";
	}

	@RequestMapping("progress")
	private String progress(String id, Model model) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		model.addAttribute("status", list);
		// 1.提交申请
		Productapply apply = paService.get(id);
		Map<String, Object> step_1 = new HashMap<String, Object>();
		step_1.put("name", "填写申请");
		step_1.put("time", apply.getApplyTime());
		step_1.put("state", "true");

		list.add(step_1);
		if (apply.getOrignStatus().equals("100083")) {
			// 受理失败
			Map<String, Object> step_2_1 = new HashMap<String, Object>();
			step_2_1.put("name", "受理失败");
			step_2_1.put("time", apply.getProvinceTime());
			step_2_1.put("state", "false");
			list.add(step_2_1);
			return "pro_check/progress";
		}
		if (apply.getOrignStatus().equals("100082")) {
			// 受理失败
			Map<String, Object> step_2_1 = new HashMap<String, Object>();
			step_2_1.put("name", "已退回");
			step_2_1.put("time", apply.getProvinceTime());
			step_2_1.put("state", "false");
			list.add(step_2_1);
			return "pro_check/progress";
		}
		long state = 100021;
		try{
			state = Long.parseLong(apply.getOrignStatus().trim());
		}catch(Exception ex){
			Map<String, Object> step_er = new HashMap<String, Object>();
			step_er.put("name", "错误的状态");
			step_er.put("time", new Date(System.currentTimeMillis()));
			step_er.put("state", "false");
			list.add(step_er);
			return "pro_check/progress";
		}
		Map<String, Object> step_2 = new HashMap<String, Object>();
		
		if (state == 100020) {
			step_2.put("name", "未上报");
			step_2.put("time", apply.getOrignStatus());
			step_2.put("state", "false");
			list.add(step_2);
			return "pro_check/progress";
		}
		
		step_2.put("name", "审核中");
		step_2.put("time", apply.getOrignStatus());
		if (state >= 100021 && state < 100024) {
			// 审核过程中
			step_2.put("state", "false");
			list.add(step_2);
			return "pro_check/progress";
		}
		step_2.put("name", "审核通过");
		step_2.put("time", apply.getProvinceTime());
		step_2.put("state", "false");
		list.add(step_2);
		return "pro_check/progress";
	}
	
}
