package edu.cuit.module.authc.web.controller;

import java.util.ArrayList;
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

import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.auchc.entity.ClimateQualitycertificationExperts;
import edu.cuit.module.auchc.entity.MemberGroup;
import edu.cuit.module.authc.service.ApplyService;
import edu.cuit.module.authc.service.ClimateQualitycertificationExpertsService;
import edu.cuit.module.authc.service.MemberGroupService;
import edu.cuit.module.infom.entity.Expertmanagement;
import edu.cuit.module.infom.entity.Expertsgroup;
import edu.cuit.module.infom.service.ExpertmanagementService;
import edu.cuit.module.infom.service.ExpertsgroupService;
import edu.cuit.module.sys.entity.TbcuitmoonUser;

@Controller
@RequestMapping("expert_assign")
public class ExpertAssignmentController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;
	@Autowired
	private ApplyService applyService;
	@Autowired
	private ClimateQualitycertificationExpertsService cqeService;
	@Autowired
	private ApplyService aService;
	@Autowired
	private ExpertsgroupService exgService;
	@Autowired
	private MemberGroupService mgService;
	@Autowired
	private ExpertmanagementService emService;

	/**
	 * 显示认证
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public String apply(@RequestParam(required = false) Integer requsetPageNo,
			String applyBh, Model model, HttpSession session) {
		bindLeft(session, model);
		if (applyBh != null) {
			String hql = "from ClimateQualitycertificationExperts where applyBh='"
					+ applyBh + "'";
			String hql2 = "select applyBh,productName,applyTime,isWithDraw,HandleResult,HandleDescription,declareStatus from Apply where applyBh='"
					+ applyBh + "'";
			List<Map<String, String>> abc = aService.select(hql2);
			String aTime = abc.get(0).get("applyTime");
			Page page;
			if (requsetPageNo == null) {
				requsetPageNo = pageNo;
			}
			page = cqeService.getPage(hql, requsetPageNo - 1, pageCounSize);
			for (ClimateQualitycertificationExperts item : (List<ClimateQualitycertificationExperts>) page
					.getPageList()) {
				item.setExpertsLevel(exgService.getGroupLevel(item
						.getExpertsLevel()));
			}
			model.addAttribute("aTime", aTime);
			model.addAttribute("expertPage", page);
		}
		model.addAttribute("applyBh", applyBh);
		return "expert_assignment/list";
	}

	@RequestMapping("/add")
	public String add(Model model, HttpServletRequest request,
			HttpSession session, String applyBh) {
		model.addAttribute("applyBh", applyBh);
		bindExpertGroup(session, request, model);
		return "expert_assignment/add";
	}

	@ResponseBody
	@RequestMapping("/getMembers")
	public Map<String, Object> getGroupMember(String expertsId) {
		// 放组员的结果集
		List<Expertmanagement> list = new ArrayList<Expertmanagement>();
		// 拆分获取到的experts里面的personid，获取expert
		Expertsgroup group = exgService.get(expertsId);
		String persons = group.getExpertsPerson();
		String[] sts = persons.split("\\|");
		for (String str : sts) {
			if (str != "") {
				Expertmanagement expert = emService.get(str);
				if (expert != null)
					list.add(expert);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("members", list);
		return map;
	}

	@SuppressWarnings("unchecked")
	private void bindExpertGroup(HttpSession session,
			HttpServletRequest request, Model model) {
		TbcuitmoonUser user = getLoginUser(session);
		String areaCode = user == null ? "" : user.getCuitMoonAreaId();
		String hql = "from Expertsgroup where region = ?";
		if (user != null && user.getCuitMoonUserName().equals("superadmin")) {
			areaCode = "1012";
		}
		List<Expertsgroup> list = (List<Expertsgroup>) exgService.find(hql,
				areaCode);
		
			model.addAttribute("experts", list);
		
		
	}

	@RequestMapping("/delete")
	public String delete(String expertsNum, String applyBh) {
		if (expertsNum != null) {
			ClimateQualitycertificationExperts entity = cqeService
					.get(expertsNum);
			if (entity != null) {
				cqeService.delete(entity);
				// 这里还应该删除对应的组员信息
			}
		}
		return "redirect:list?applyBh=" + applyBh;
	}

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Map<String, String> add(HttpServletRequest request,
			HttpSession session, String applyBh) {
		Map<String, String> map = new HashMap<String, String>();
		TbcuitmoonUser user = getLoginUser(session);
		if (user == null || applyBh == null) {
			map.put("success", "false");
			return map;
		}
		// 添加组，选组长
		ClimateQualitycertificationExperts entity = new ClimateQualitycertificationExperts(
				UUID.randomUUID().toString().replace("-", ""));
		entity.setApplyBh(applyBh);
		entity.setExpertsLevel(user.getCuitMoonAreaId());
		entity.setExpertsClass(WebUtils
				.findParameterValue(request, "groupName"));
		entity.setRemark(WebUtils.findParameterValue(request, "groupRemarks"));
		String members = WebUtils.findParameterValue(request, "members");
		String[] member = members.split(",");
		String grouper = WebUtils.findParameterValue(request, "grouper");
		for (String str : member) {
			if (!str.equals("")) {
				// 组员
				MemberGroup group = new MemberGroup();
				group.setMember(str);
				group.setMemberNum(UUID.randomUUID().toString()
						.replace("-", ""));
				group.setExpertsNum(entity.getExpertsNum());
				if (str.equals(grouper)) {
					group.setIsGrouper("100011");
				} else {
					group.setIsGrouper("100012");
				}
				mgService.save(group);
			}
		}
		cqeService.save(entity);
		map.put("success", "true");
		return map;
	}

	@RequestMapping("/edit")
	private String edit(String expertsNum, String applyBh, Model model,
			HttpServletRequest request, HttpSession session) {
		if (expertsNum != null && applyBh != null) {
			ClimateQualitycertificationExperts entity = cqeService
					.get(expertsNum);
			List<Expertsgroup> list = (List<Expertsgroup>) exgService.find(
					"from Expertsgroup where expertsName = ? and  region = ?",
					new String[] {entity.getExpertsClass(),getLoginUser(session).getCuitMoonAreaId().trim()});
			if (list.size() > 0) {
				model.addAttribute("expertsId", list.get(0).getExpertsId());
			}
			model.addAttribute("entity", entity);
			model.addAttribute("applyBh",applyBh);
		}
		bindExpertGroup(session, request, model);
		//获取专家组成员
		String hql = "from MemberGroup where expertsNum = ? ";
		List<MemberGroup> group = (List<MemberGroup>)mgService.find(hql, expertsNum);
		String members = "";
		for (MemberGroup member : group) {
			members += member.getMember()+",";
			if (member.getIsGrouper() != null && member.getIsGrouper().equals("100011")) {
				model.addAttribute("grouper", member.getMember());
			}
		}
		model.addAttribute("memberStr", members);
		
		return "expert_assignment/edit";
	}
	
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Map<String, String> update(HttpServletRequest request,
			HttpSession session, String applyBh) {
		Map<String, String> map = new HashMap<String, String>();
		TbcuitmoonUser user = getLoginUser(session);
		if (user == null || applyBh == null) {
			map.put("success", "false");
			return map;
		}
		// 添加组，选组长
		ClimateQualitycertificationExperts entity = cqeService.get(WebUtils.findParameterValue(request, "expertsNum"));
		entity.setApplyBh(applyBh);
		entity.setExpertsLevel(user.getCuitMoonAreaId());
		entity.setExpertsClass(WebUtils
				.findParameterValue(request, "groupName"));
		entity.setRemark(WebUtils.findParameterValue(request, "groupRemarks"));
		String members = WebUtils.findParameterValue(request, "members");
		String[] member = members.split(",");
		String grouper = WebUtils.findParameterValue(request, "grouper");
		//清空原有组员
		List<MemberGroup> list = (List<MemberGroup>)mgService.find("from MemberGroup where expertsNum = ?",WebUtils.findParameterValue(request, "expertsNum"));
		for (MemberGroup memberGroup : list) {
			mgService.delete(memberGroup);
		}
		for (String str : member) {
			if (!str.equals("")) {
				// 组员
				MemberGroup group = new MemberGroup();
				group.setMember(str);
				group.setMemberNum(UUID.randomUUID().toString()
						.replace("-", ""));
				group.setExpertsNum(entity.getExpertsNum());
				if (str.equals(grouper)) {
					group.setIsGrouper("100011");
				} else {
					group.setIsGrouper("100012");
				}
				mgService.save(group);
			}
		}
		cqeService.update(entity);
		map.put("success", "true");
		return map;
	}

	private void bindLeft(HttpSession session, Model model) {
		TbcuitmoonUser user = getLoginUser(session);
		String areaCode = user == null ? "" : user.getCuitMoonAreaId().trim();
		if (user != null && user.getCuitMoonUserName().equals("superadmin")) {
			areaCode = "";
		}
		String hql = "from Apply where DeclareStatus > 100023 and DeclareStatus<100028 and ProduceBase like"
				+ " '%" + areaCode + "%'";
		List<?> list = applyService.find(hql, null);
		model.addAttribute("left", list);
	}
}
