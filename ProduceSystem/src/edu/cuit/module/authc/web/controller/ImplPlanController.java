package edu.cuit.module.authc.web.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cuit.common.util.DateFormat;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.auchc.entity.Apply;
import edu.cuit.module.auchc.entity.Approvedatamanager;
import edu.cuit.module.auchc.entity.Bearinginfo;
import edu.cuit.module.auchc.entity.ClimateQualitycertificationExperts;
import edu.cuit.module.auchc.entity.MemberGroup;
import edu.cuit.module.auchc.entity.QualityIdentification;
import edu.cuit.module.authc.service.ApplyService;
import edu.cuit.module.authc.service.ApprovedatamanagerService;
import edu.cuit.module.authc.service.BearinginfoService;
import edu.cuit.module.authc.service.ClimateQualitycertificationExpertsService;
import edu.cuit.module.authc.service.MemberGroupService;
import edu.cuit.module.authc.service.QualityIdentificationService;
import edu.cuit.module.infom.entity.Expertmanagement;
import edu.cuit.module.infom.entity.Weatherstationinfo;
import edu.cuit.module.infom.service.ExpertmanagementService;
import edu.cuit.module.infom.service.WeatherstationinfoService;
import edu.cuit.module.model.entity.Elementmodel;
import edu.cuit.module.model.service.ElementmodelService;
import edu.cuit.module.sys.entity.TbcuitmoonRole;
import edu.cuit.module.sys.entity.TbcuitmoonUser;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;

@Controller
@RequestMapping("impl_plan")
public class ImplPlanController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;
	@Autowired
	ApplyService applyService;
	@Autowired
	QualityIdentificationService qiService;
	@Autowired
	TbcuitmoonDictionaryService dicService;
	@Autowired
	WeatherstationinfoService weatherService;
	@Autowired
	ElementmodelService elementService;
	@Autowired
	BearinginfoService bearService;
	@Autowired
	ExpertmanagementService experstService;
	@Autowired
	ApprovedatamanagerService approveService;
	@Autowired
	MemberGroupService groupService;
	@Autowired
	ClimateQualitycertificationExpertsService climateService;

	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	public String list(@RequestParam(required = false) Integer requsetPageNo,
			Model model, HttpSession session) {
		TbcuitmoonUser user = getLoginUser(session);
		String memberId = user == null ? "" : user.getCuitMoonUserId();
		String name = user.getCuitMoonUserName();
		List<Expertmanagement> experts = (List<Expertmanagement>) experstService
				.find("from Expertmanagement where username = ?", name);
		if (experts.size() > 0) {
			memberId = experts.get(0).getExpertNo();
		}

		String hql = "select applyBh,productName,applyTime,isWithDraw,HandleResult,HandleDescription,declareStatus from Apply "
				+ " where ApplyBh in(select ApplyBh from"
				+ " climate_qualitycertification_experts where Experts_Num in(select"
				+ " Experts_Num from Member_Group where Member_Group.Member ="
				+ "'" + memberId + "'))  and";

		if (user != null && user.getCuitMoonUserName().equals("superadmin")) {
			hql = "select applyBh,productName,applyTime,isWithDraw,HandleResult,HandleDescription,declareStatus from Apply where";
		}
		hql += " Apply.ApplyBh in(select Apply.ApplyBh from Apply "
				+ " where apply.declareStatus >100023 and apply.declareStatus < 100030 and apply.declareStatus != 100082)";
		List<Map<String, String>> applyList = applyService.select(hql);
		for (Map<String, String> item : applyList) {
			String role = user == null ? "" : ((TbcuitmoonRole) user
					.getTbcuitmoonRoles().toArray()[0]).getCuitMoonRoleName() + "";
			if(role.equals("基层专家组")){
				if(item.get("declareStatus").equals("100027"))
					item.put("declareStatus", "100028");
			}
			item.put("handleDescription",
					dicService.getDicNameByCode(item.get("isWithDraw")));
			String sql = "from QualityIdentification where applyBh = ?";
			List<QualityIdentification> list = (List<QualityIdentification>) qiService
					.find(sql, item.get("applyBh"));
			if (list.size() > 0) {
				item.put("establishedPlan", "是");
				item.put("num", list.get(0).getQualityIdentificationNum());
				item.put("handleDescription",
						dicService.getDicNameByCode(list.get(0).getState()));
			} else {
				item.put("establishedPlan", "否");
			}
		}
		model.addAttribute("applyList", applyList);
		return "impl_plan/list";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/search_plan")
	public String search(@RequestParam(required = false) Integer requsetPageNo,
			Model model, HttpSession session, String key) {
		TbcuitmoonUser user = getLoginUser(session);
		String memberId = user == null ? "" : user.getCuitMoonUserId();
		String name = user.getCuitMoonUserName();
		List<Expertmanagement> experts = (List<Expertmanagement>) experstService
				.find("from Expertmanagement where username = ?", name);
		if (experts.size() > 0) {
			memberId = experts.get(0).getExpertNo();
		}
		String hql = "select applyBh,productName,applyTime,isWithDraw,HandleResult,HandleDescription,declareStatus from Apply "
				+ " where ApplyBh in(select ApplyBh from"
				+ " climate_qualitycertification_experts where Experts_Num in(select"
				+ "Experts_Num from Member_Group where Member_Group.Member ="
				+ "'" + memberId + "'))";
		if (user != null && user.getCuitMoonUserName().equals("superadmin")) {
			hql = "select applyBh,productName,applyTime,isWithDraw,HandleResult,HandleDescription from Apply ";
		}
		hql += " and Apply.ApplyBh in(select Apply.ApplyBh from Apply "
				+ "where apply.declareStatus >100023 and apply.declareStatus < 100030 and apply.declareStatus != 100082)";
		if (key != null && key != "") {
			hql += " and apply.ProductName like '%" + key + "%'";
		}
		hql += ")";
		List<Map<String, String>> applyList = applyService.select(hql);
		for (Map<String, String> item : applyList) {
			item.put("handleDescription",
					dicService.getDicNameByCode(item.get("isWithDraw")));
			String sql = "from QualityIdentification where applyBh = ?";
			List<QualityIdentification> list = (List<QualityIdentification>) qiService
					.find(sql, item.get("applyBh"));
			if (list.size() > 0) {
				item.put("establishedPlan", "是");
				item.put("num", list.get(0).getQualityIdentificationNum());
				item.put("handleDescription",
						dicService.getDicNameByCode(list.get(0).getState()));
			} else {
				item.put("establishedPlan", "否");
			}
		}
		model.addAttribute("applyList", applyList);
		return "impl_plan/list";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/audit")
	public String audit(String applyBh, String num, Model model,
			HttpServletRequest request, HttpSession session) {
		QualityIdentification entity = qiService.get(num);
		Apply apply = applyService.get(applyBh);
		if (entity != null) {
		}
		TbcuitmoonUser user = getLoginUser(session);
		if (user != null) {
			model.addAttribute("role", ((TbcuitmoonRole) user
					.getTbcuitmoonRoles().toArray()[0]).getCuitMoonRoleName());
			model.addAttribute("name", user.getCuitMoonUserRealName());
		}
		// 判断该专家是否是组长
		List<ClimateQualitycertificationExperts> climateExperts = (List<ClimateQualitycertificationExperts>) climateService
				.find("from ClimateQualitycertificationExperts where applyBh = ? and expertsLevel = ?",
						applyBh,user.getCuitMoonAreaId());
		String memberId = user == null ? "" : user.getCuitMoonUserId();
		String name = user.getCuitMoonUserName();
		List<Expertmanagement> experts = (List<Expertmanagement>) experstService
				.find("from Expertmanagement where username = ?", name);
		if (experts.size() > 0) {
			memberId = experts.get(0).getExpertNo();
		}
		String expertsNum = climateExperts == null
				|| climateExperts.size() <= 0 ? null : climateExperts.get(0)
				.getExpertsNum();
		if (expertsNum == null) {
			model.addAttribute("role", "无权限");
		} else {
			String hql = "from MemberGroup as m where m.expertsNum = ? and m.member = ?";
			List<MemberGroup> group = (List<MemberGroup>) groupService
					.find(hql, expertsNum,memberId);
			if (group == null || group.size() <= 0) {
				model.addAttribute("role", "无权限");
			} else if (!dicService.getCodeByName("是").toString().equals(
					group.get(0).getIsGrouper())) {
				model.addAttribute("role", "无权限");
			}
		}

		// 初始化认证信息
		// 获取生育期、气象站、气象指标等信息
		List<Bearinginfo> bearList = (List<Bearinginfo>) bearService.find(
				"from Bearinginfo where qualityIdentificationNum = ?", num);
		for (Bearinginfo info : bearList) {
			String elements = info.getMeteIndicators();
			info.setStationId(weatherService.get(info.getStationId()).getName());
			if (elements.length() > 0) {
				String[] strs = elements.split("\\|");
				String names = "";
				for (String str : strs) {
					Elementmodel element = elementService.get(str);
					if (element != null) {
						names += element.getElementName() + ",";
					}
				}
				info.setMeteIndicators(names);
			}
		}

		model.addAttribute("apply", apply);
		model.addAttribute("bearList", bearList);
		model.addAttribute("applyBh", applyBh);
		model.addAttribute("num", num);
		model.addAttribute("entity", entity);
		return "impl_plan/audit";
	}

	@ResponseBody
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	public Map<String, String> audit(HttpServletRequest request,
			HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		String applyBh = WebUtils.findParameterValue(request, "applyBh");
		Apply apply = applyService.get(applyBh);
		QualityIdentification quality = qiService.get(WebUtils
				.findParameterValue(request, "num"));
		String role = getLoginUser(session) == null ? ""
				: ((TbcuitmoonRole) getLoginUser(session).getTbcuitmoonRoles()
						.toArray()[0]).getCuitMoonRoleName();
		boolean isPass = false;
		if (WebUtils.findParameterValue(request, "IsPass") != null
				&& WebUtils.findParameterValue(request, "IsPass") != "") {
			if (WebUtils.findParameterValue(request, "IsPass").equals("Pass")) {
				isPass = true;
			} else {
				isPass = false;
			}
		}
		if (!role.equals("")) {
			if (role.equals("省级专家组") || role.equals("超级管理员")) {
				quality.setProvincialPanelAuditOpinion(WebUtils
						.findParameterValue(request, "AdvanceAdvice"));
				quality.setProvincialPanelSign(WebUtils.findParameterValue(
						request, "Advance"));
				quality.setProvincialAuditTime(new Date(System
						.currentTimeMillis()));
				if (isPass) {
					quality.setState(dicService.getCodeByName("受理成功"));
					updateApplyStatus(quality.getState(), applyBh);
					Approvedatamanager approve = new Approvedatamanager();
					approve.setQualityIdentificationNum(quality
							.getQualityIdentificationNum());
					approve.setRemark(apply.getProduceBase());
					approve.setApproveDataCode(UUID.randomUUID().toString()
							.replace("-", ""));
					approveService.save(approve);
				} else {
					quality.setState(dicService.getCodeByName("受理失败"));
					updateApplyStatus(quality.getState(), applyBh);
				}
			} else if (role.equals("基层专家组") || role.equals("超级管理员")) {
				quality.setBasicPanelSign(WebUtils.findParameterValue(request,
						"Base"));
				quality.setBasicPanelAuditOpinion(WebUtils.findParameterValue(
						request, "BaseAdvice"));
				quality.setBasicAuditTime(new Date(System.currentTimeMillis()));
				if (isPass) {
					quality.setState("100027");
				} else {
					quality.setState(dicService.getCodeByName("受理失败"));
					updateApplyStatus(quality.getState(), applyBh);
				}
				apply.setDeclareStatus("100027");
				apply.setIsWithDraw("100027");
				applyService.update(apply);
			}
			map.put("success", "true");
		} else {
			map.put("success", "false");
		}
		return map;
	}

	@RequestMapping("details")
	public String details(String applyBh, String num, Model model,
			HttpServletRequest request, HttpSession session) {
		audit(applyBh, num, model, request, session);
		return "impl_plan/details";
	}

	protected void updateApplyStatus(String value, String applyBh) {
		Apply apply = applyService.get(applyBh);
		if ("100084".equals(value)) {
			apply.setDeclareStatus(dicService.getCodeByName("待生成品质报告"));
			apply.setProductScoreStatus(dicService.getCodeByName("待评分"));
			apply.setHandleResult("100084");
		} else// 受理失败
		{
			apply.setDeclareStatus("100083");
			apply.setHandleResult("100083");
		}

		try {
			applyService.update(apply);
			// /ProduceSystem.BLL.ApplyManager.Instance.UpdateStatus(Model);
		} catch (Exception ex) {
			// Response.Write(ex.Message);
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/addPlan")
	public String add(String applyBh, Model model, HttpServletRequest request,
			HttpSession session) {
		// 申请基本信息
		Apply apply = applyService.get(applyBh);
		model.addAttribute("apply", apply);
		// 气象站信息
		List<Weatherstationinfo> stations = null;
		TbcuitmoonUser user = getLoginUser(session);
		String hql = "from Weatherstationinfo";
		if (user != null && !user.getCuitMoonAreaId().trim().equals("1012")) {
			hql += " where belongTo = ?";
			stations = (List<Weatherstationinfo>) weatherService.find(hql, user
					.getCuitMoonAreaId().trim());
		} else {
			stations = (List<Weatherstationinfo>) weatherService.find(hql);
		}
		model.addAttribute("stations", stations);
		model.addAttribute("applyBh", applyBh);
		return "impl_plan/add";
	}

	@ResponseBody
	@RequestMapping(value = "addPlan", method = RequestMethod.POST)
	public Map<String, Object> add(HttpServletRequest request) {
		// 先创建方案，然后添加生育期信息
		QualityIdentification qualied = new QualityIdentification(UUID
				.randomUUID().toString().replace("-", ""));
		String worm = WebUtils.findParameterValue(request, "worm");
		String weather = WebUtils.findParameterValue(request, "weather");
		String summary = WebUtils.findParameterValue(request, "summary");
		String type = WebUtils.findParameterValue(request, "type");
		String elements = WebUtils.findParameterValue(request, "elements");
		String applyBh = WebUtils.findParameterValue(request, "applyId");
		Apply apply = applyService.get(applyBh);
		apply.setDeclareStatus(dicService.getCodeByName("待实施方案审核"));
		apply.setHandleResult(dicService.getCodeByName("受理中"));
		qualied.setState("100026");
		qualied.setApplyFrequency(type);
		qualied.setOriginSituation(summary);
		qualied.setApplyBh(applyBh);
		qualied.setMeteorologicalDisaster(weather);
		qualied.setClimateStationName(WebUtils.findParameterValue(request,
				"staName"));
		qualied.setDiseasesInsectPests(worm);
		qualied.setProduceBase(apply.getProduceBase());
		qualied.setIdentificationer(apply.getCounterMan());
		qualied.setPhone(apply.getContact());
		qualied.setAddress(apply.getAddress());
		qiService.saveOrUpdate(qualied);
		applyService.update(apply);

		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(elements);
			for (int i = 0; i < node.size(); i++) {
				JsonNode child = node.get(i);
				String station = child.path("station").asText();
				String items = child.path("elements").asText();
				String start = child.path("start").asText();
				String end = child.path("end").asText();
				String reason = child.path("reason").asText();
				String stage = child.path("stage").asText();
				Bearinginfo info = new Bearinginfo(UUID.randomUUID().toString()
						.replace("-", ""));
				info.setCropGrowthPeriod(stage);
				info.setStartCollectionTime(DateFormat.strToDate(start));
				info.setEndCollectionTime(DateFormat.strToDate(end));
				info.setMeteIndicators(items);
				info.setMeteIndicatorsReason(reason);
				info.setQualityIdentificationNum(qualied
						.getQualityIdentificationNum());
				info.setStationId(station);
				bearService.save(info);
			}
		} catch (Exception ex) {
			map.put("success", "false");
			return map;
		}
		map.put("success", "true");
		return map;
	}

	@ResponseBody
	@RequestMapping("/getElements")
	public Map<String, Object> getElements(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = WebUtils.findParameterValue(request, "stationId");
		if (id == null || id == "") {
			map.put("success", "false");
			map.put("msg", "气象站id为空");
			return map;
		}
		Weatherstationinfo info = weatherService.get(id);
		if (info == null) {
			map.put("success", "false");
			map.put("msg", "气象站不存在");
			return map;
		}
		String elements = info.getMeasuringelements();
		String[] strs = elements.split("\\|");
		List<Elementmodel> list = new ArrayList<Elementmodel>();
		for (String str : strs) {
			if (str.equals("")) {
				continue;
			}
			Elementmodel entity = elementService.get(str);
			if (entity != null) {
				list.add(entity);
			}
		}
		map.put("success", "true");
		map.put("msg", list);
		return map;
	}
}
