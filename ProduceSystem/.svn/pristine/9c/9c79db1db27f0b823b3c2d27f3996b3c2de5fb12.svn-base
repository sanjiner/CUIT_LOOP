package edu.cuit.module.authc.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import edu.cuit.common.util.DateFormat;
import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.auchc.entity.Apply;
import edu.cuit.module.auchc.entity.Cimatequalityapproveprocess;
import edu.cuit.module.authc.service.ApplyService;
import edu.cuit.module.authc.service.CimatequalityapproveprocessService;
import edu.cuit.module.infom.service.BusinessmanagementService;
import edu.cuit.module.infom.service.BusinessqualificationService;
import edu.cuit.module.model.entity.Productinfomation;
import edu.cuit.module.model.service.ProductinfomationService;
import edu.cuit.module.origin.entity.Certificate;
import edu.cuit.module.origin.service.CertificateService;
import edu.cuit.module.sys.entity.TbcuitmoonArea;
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;
import edu.cuit.module.sys.entity.TbcuitmoonRole;
import edu.cuit.module.sys.entity.TbcuitmoonUser;
import edu.cuit.module.sys.service.TbcuitmoonAreaService;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;
import edu.cuit.module.sys.service.TbcuitmoonUserService;

@Controller
@RequestMapping("exam")
public class ExamApplyController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;
	@Autowired
	private ApplyService applyService;
	@Autowired
	TbcuitmoonUserService tbUserSerivice;
	@Autowired
	BusinessmanagementService bmService;
	@Autowired
	BusinessqualificationService bqService;
	@Autowired
	TbcuitmoonAreaService tbAreaService;
	@Autowired
	TbcuitmoonDictionaryService tbDicService;
	@Autowired
	ProductinfomationService pfService;
	@Autowired
	CimatequalityapproveprocessService capService;
	@Autowired CertificateService certServoce;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
	public String showList(
			@RequestParam(required = false) Integer requsetPageNo, Model model,
			HttpSession session) {
		Page page;
		if (requsetPageNo == null) {
			requsetPageNo = pageNo;
		}
		TbcuitmoonUser user = getLoginUser(session);
		String role = user == null ? "" : ((TbcuitmoonRole) user
				.getTbcuitmoonRoles().toArray()[0]).getCuitMoonRoleName() + "";
		String area = user == null ? "" : user.getCuitMoonAreaId().trim();
		long IdentifyCode = 100021;
		String hql = "from Apply as T where T.produceBase like '%" + area
				+ "%'";
		Object[] role_array =  (Object[])user.getTbcuitmoonRoles().toArray();
		//TbcuitmoonRole tbRole = (TbcuitmoonRole)role_array[0];
		for (Object item : role_array) {
			TbcuitmoonRole tbRole = (TbcuitmoonRole)item;
			if(tbRole.getCuitMoonRoleName().equals("县级气象局管理员")){
				role = tbRole.getCuitMoonRoleName();
				break;
			}else if(tbRole.getCuitMoonRoleName().equals("市级气象局管理员")){
				role = tbRole.getCuitMoonRoleName();
				break;
			}
			else if(tbRole.getCuitMoonRoleName().equals("省级气象局管理员")){
				role = tbRole.getCuitMoonRoleName();
				break;
			}
		}
		if (role != null && !role.equals("超级管理员")) {
			List<TbcuitmoonDictionary> dic_list = (List<TbcuitmoonDictionary>) tbDicService
					.find("from TbcuitmoonDictionary as T where T.cuitMoonDictionaryName = ?",
							"待" + role + "审核");
			if (dic_list != null && dic_list.size() > 0) {
				IdentifyCode = dic_list.get(0).getCuitMoonDictionaryCode();
				hql += " and  declareStatus = " + IdentifyCode;
			}
		}
		hql += " order by applyTime desc";
		page = applyService.getPage(hql, requsetPageNo - 1, pageCounSize);
		model.addAttribute("applyPage", page);
		for (Apply apply : (List<Apply>) page.getPageList()) {
			apply.setHandleDescription(getHandleResult(apply.getHandleResult()));
			apply.setDesalareStatusDescript(getHandleResult(apply
					.getDeclareStatus()));
		}
		return "apply_exam/list";
	}

	public String getHandleResult(String code) {
		if (code == null) {
			return "未知状态";
		}
		code = code.trim();
		long l_code;
		try {
			l_code = Long.parseLong(code);
		} catch (Exception ex) {
			l_code = 100081;
		}
		String name = "";
		List<TbcuitmoonDictionary> list = (List<TbcuitmoonDictionary>) tbDicService
				.find("from TbcuitmoonDictionary as T where T.cuitMoonDictionaryCode = ? ",
						l_code);
		if (list.size() > 0) {
			name = list.get(0).getCuitMoonDictionaryName();
		} else {
			name = "未知状态";
		}
		return name;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/examInfo")
	public String examInfo(Model model, String applyBh,
			HttpServletRequest request, HttpSession session) {
		Apply entity = (Apply) applyService.find(
				"from Apply where applyBh = ?", applyBh).get(0);
		request.setAttribute("apply", entity);
		// 获取地址信息
		String address = entity.getProduceBase();
		String[] strs = address.split("\\|");
		address = "";
		for (String string : strs) {
			List<TbcuitmoonArea> list = (List<TbcuitmoonArea>) tbAreaService
					.find("from TbcuitmoonArea as T where T.cuitMoonAreaCode = ?",
							Long.parseLong(string));
			if (list.size() > 0) {
				address += list.get(0).getCuitMoonAreaName();
			}
		}
		String hql = "from Businessmanagement  as T where T.userName = ?";
		List<?> list = bmService.find(hql, entity.getApplyPerson());
		if (list.size() > 0) {
			// 用户基本信息
			model.addAttribute("bsm", list.get(0));
			// 荣誉资质
			if (list.size() > 0) {
				// 用户基本信息
				model.addAttribute("bsm", list.get(0));
				// 荣誉资质
				String cert_hql = "from Certificate where originId = ?";
				List<Certificate> certs = (List<Certificate>) certServoce.find(cert_hql,
						entity.getApplyBh());
				model.addAttribute("certs", certs);
			}
		}
		request.setAttribute("address", address);
		List<Productinfomation> p_list = (List<Productinfomation>) pfService
				.find("from Productinfomation where productType = ?",
						entity.getCommodityType());
		if (p_list.size() > 0) {
			model.addAttribute("type", p_list.get(0).getProductName());
		}
		TbcuitmoonUser user = getLoginUser(session);
		String role = user == null ? "" : ((TbcuitmoonRole) user
				.getTbcuitmoonRoles().toArray()[0]).getCuitMoonRoleName() + "";
		model.addAttribute("role", role);
		model.addAttribute("process", entity.getIsWithDraw());
		model.addAttribute("name", user.getCuitMoonUserRealName());
		model.addAttribute("handleResult", entity.getHandleResult());
		return "apply_exam/exam";
	}

	@RequestMapping("/examApply")
	@ResponseBody
	public Map<String, String> examApply(Model model, String applyBh,
			HttpSession session, HttpServletRequest request) {
		TbcuitmoonUser user = (TbcuitmoonUser) getLoginUser(session);
		Map<String, String> map = new HashMap<String, String>();
		Apply apply = applyService.get(applyBh);
		String IsPass = WebUtils.findParameterValue(request, "IsPass");
		String LoginRole ="";
		Object[] role_array =  (Object[])user.getTbcuitmoonRoles().toArray();
		//TbcuitmoonRole tbRole = (TbcuitmoonRole)role_array[0];
		for (Object item : role_array) {
			TbcuitmoonRole tbRole = (TbcuitmoonRole)item;
			if(tbRole.getCuitMoonRoleName().equals("县级气象局管理员")){
				LoginRole = tbRole.getCuitMoonRoleName();
				break;
			}else if(tbRole.getCuitMoonRoleName().equals("市级气象局管理员")){
				LoginRole = tbRole.getCuitMoonRoleName();
				break;
			}
			else if(tbRole.getCuitMoonRoleName().equals("省级气象局管理员")){
				LoginRole = tbRole.getCuitMoonRoleName();
				break;
			}
		}
		if (LoginRole != null) {
			if (IsPass.equals("Yes")) {
				// 通过
				apply.setDeclareStatus((getDicCodeByName("待" + LoginRole + "审核") + 1)
						+ "");
				apply.setIsWithDraw(apply.getDeclareStatus());
				apply.setHandleResult(getDicCodeByName("受理中") + "");
			} else if (IsPass.equals("Back")) {
				// 退回
				if (!LoginRole.equals("县级气象局管理员")) {
					apply.setDeclareStatus(getDicCodeByName("待" + LoginRole
							+ "审核")
							- 1 + "");
					//县级以上退回后，不用再县级退回
					//apply.setDeclareStatus("100020");
					apply.setIsWithDraw(apply.getDeclareStatus());
					apply.setHandleResult(getDicCodeByName("受理中") + "");
				} else {
					apply.setDeclareStatus("100020");
					apply.setIsWithDraw(apply.getDeclareStatus());
					apply.setHandleResult(getDicCodeByName("已退回，请仔细核对申请内容！") + "");
				}
			} else if (IsPass.equals("No")) {
				// 拒绝
				apply.setDeclareStatus(getDicCodeByName("受理失败，请重新申请！") + "");
				apply.setHandleResult(getDicCodeByName("受理失败，请重新申请！") + "");
				apply.setIsWithDraw("待" + LoginRole + "审核");
			}
			// 更新进度
			setExamAdvice(apply, request, LoginRole);
			try {
				// 更新申请表记录
				updateProcess(applyBh, apply.getDeclareStatus(),
						apply.getProductName());
				submitExam(LoginRole, apply);
				map.put("success", "true");
			} catch (Exception ex) {
				map.put("success", "false");
				return map;
			}
		} else {
			map.put("success", "false");
		}
		return map;
	}

	/*
	 * @RequestMapping("/deleteApply") private String deleteApply(String
	 * applyBh,String requsetPageNo){ applyService.deleteByKey(applyBh); return
	 * "redirect:list?requsetPageNo="+requsetPageNo; }
	 */

	@RequestMapping("/detailExam")
	private String detailApply(String applyBh, String requsetPageNo,
			Model model, HttpServletRequest request, HttpSession session) {
		model.addAttribute("requsetPageNo", requsetPageNo);
		examInfo(model, applyBh, request, session);
		return "/apply_exam/details";
	}

	@RequestMapping(value = "/searchApply")
	public String searchApply(Model model, String key) {
		Page page = null;
		page = applyService.getPage(
				"from Apply as T where T.productName like '%" + key + "%'",
				pageNo - 1, pageCounSize);
		model.addAttribute("applyPage", page);
		model.addAttribute("key", key);
		return "/apply_exam/list";
	}

	private void setExamAdvice(Apply apply, HttpServletRequest request,
			String LoginRole) {
		if ("县级气象局管理员".equals(LoginRole)) {
			apply.setRegionHeader(WebUtils
					.findParameterValue(request, "Region"));
			apply.setRegionAuditTime(new Date(System.currentTimeMillis()));
			apply.setRegionManageOpinion(WebUtils.findParameterValue(request,
					"RegionAdvice"));
		} else if ("市级气象局管理员".equals(LoginRole)) {
			apply.setCityAuditTime(new Date(System.currentTimeMillis()));
			apply.setCityHeader(WebUtils.findParameterValue(request, "City"));
			apply.setCityManageAudit(WebUtils.findParameterValue(request,
					"CityAdvice"));
		} else {
			apply.setProvinceAuditTime(new Date(System.currentTimeMillis()));
			apply.setProvinceHeader(WebUtils.findParameterValue(request,
					"Province"));
			apply.setProvinceManageOpinion(WebUtils.findParameterValue(request,
					"ProvinceAdvice"));
		}
	}

	private void updateProcess(String id, String status, String name) {
		Cimatequalityapproveprocess entity = null;
		String hql = "from Cimatequalityapproveprocess where applyBh = ?";
		List<?> list = capService.find(hql, id);
		if (list.size() > 0) {
			entity = (Cimatequalityapproveprocess) list.get(0);
			entity.setTime(DateFormat.getStrTime(
					new Date(System.currentTimeMillis()), 5));
			entity.setScheduleIdProcess(status);
			entity.setProjectName(name);
			capService.update(entity);
		} else {
			entity = new Cimatequalityapproveprocess(UUID.randomUUID()
					.toString().replace("-", ""));
			entity.setApplyBh(id);
			entity.setTime(DateFormat.getStrTime(
					new Date(System.currentTimeMillis()), 5));
			entity.setScheduleIdProcess(status);
			entity.setProjectName(name);
			capService.save(entity);
		}
	}

	private void submitExam(String role, Apply apply) {
		Date date = new Date(System.currentTimeMillis());
		if (role.equals("县级气象局管理员")) {
			apply.setRegionAuditTime(date);
		} else if (role.equals("市级气象管理员")) {
			apply.setCityAuditTime(date);
		} else if (role.equals("省级气象管理员")) {
			apply.setProvinceAuditTime(date);
		}
		applyService.update(apply);
	}

	@SuppressWarnings("unchecked")
	private long getDicCodeByName(String name) {
		// TbcuitmoonDictionary
		long code = 0L;
		String hql = "from TbcuitmoonDictionary where cuitMoonDictionaryName = ?";
		List<TbcuitmoonDictionary> list = (List<TbcuitmoonDictionary>) tbDicService
				.find(hql, name);
		if (list.size() > 0) {
			code = list.get(0).getCuitMoonDictionaryCode();
		}
		return code;
	}
}