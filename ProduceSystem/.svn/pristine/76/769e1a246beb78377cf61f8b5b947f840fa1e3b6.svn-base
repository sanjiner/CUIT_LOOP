package edu.cuit.module.authc.web.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.auchc.entity.Apply;
import edu.cuit.module.auchc.entity.IdentificationRecord;
import edu.cuit.module.auchc.entity.QualityIdentification;
import edu.cuit.module.auchc.entity.Reportinfomation;
import edu.cuit.module.authc.service.ApplyService;
import edu.cuit.module.authc.service.IdentificationRecordService;
import edu.cuit.module.authc.service.QualityIdentificationService;
import edu.cuit.module.authc.service.ReportinfomationService;
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;
import edu.cuit.module.sys.entity.TbcuitmoonRole;
import edu.cuit.module.sys.entity.TbcuitmoonUser;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;

@Controller
@RequestMapping("/pro_check")
public class ProCheckController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;
	@Autowired
	private ApplyService applyService;
	@Autowired
	TbcuitmoonDictionaryService tbDicService;
	@Autowired
	QualityIdentificationService qiService;
	@Autowired
	ReportinfomationService reportService;
	@Autowired
	IdentificationRecordService recordService;

	@SuppressWarnings("unchecked")
	@RequestMapping("/list")
	private String list(@RequestParam(required = false) Integer requsetPageNo,
			Model model,HttpSession session) {
		Page page;
		if (requsetPageNo == null) {
			requsetPageNo = pageNo;
		}
		TbcuitmoonUser user = getLoginUser(session);
		String role = user == null ? "" : ((TbcuitmoonRole) user
				.getTbcuitmoonRoles().toArray()[0]).getCuitMoonRoleName() + "";
		String hql = "";
		if (user != null && role.equals("商家")) {
			hql = "from Apply where applyPerson = '"
					+ user.getCuitMoonUserName()+"'  order by applyTime desc";
		} else {
			hql = "from Apply as T where T.produceBase like '%"
					+ user.getCuitMoonAreaId() + "%' order by applyTime desc";
		}
		page = applyService.getPage(hql, requsetPageNo - 1, pageCounSize);
		model.addAttribute("applyPage", page);
		for (Apply apply : (List<Apply>) page.getPageList()) {
			apply.setHandleDescription(getHandleResult(apply.getHandleResult()));
		}
		return "pro_check/list";
	}

	@SuppressWarnings("unchecked")
	public String getHandleResult(String code) {
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

	@RequestMapping("progress")
	private String progress(String applyBh, Model model) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		model.addAttribute("status", list);
		// 1.提交申请
		Apply apply = applyService.get(applyBh);
		Map<String, Object> step_1 = new HashMap<String, Object>();
		step_1.put("name", "提交申请");
		step_1.put("time", apply.getApplyTime());
		step_1.put("state", "true");

		list.add(step_1);
		if (apply.getHandleResult().equals("100083")) {
			// 受理失败
			Map<String, Object> step_2_1 = new HashMap<String, Object>();
			step_2_1.put("name", "受理失败");
			step_2_1.put("time", apply.getRegionAuditTime());
			step_2_1.put("state", "false");
			list.add(step_2_1);
			return "pro_check/progress";
		}
		long state = 100021;
		try{
			state = Long.parseLong(apply.getDeclareStatus().trim());
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
			step_2.put("time", apply.getRegionAuditTime());
			step_2.put("state", "false");
			list.add(step_2);
			return "pro_check/progress";
		}
		
		step_2.put("name", "审核中");
		step_2.put("time", apply.getRegionAuditTime());
		if (state >= 100021 && state < 100024) {
			// 审核过程中
			step_2.put("state", "false");
			list.add(step_2);
			return "pro_check/progress";
		}
		step_2.put("name", "审核通过");
		step_2.put("time", apply.getProvinceAuditTime());
		step_2.put("state", "true");
		list.add(step_2);

		// 3.建立实施方案
		Map<String, Object> step_3 = new HashMap<String, Object>();
		if (state == 100024 || qiService.find("from QualityIdentification where applyBh = ?", applyBh).size() == 0) {
			// 待建方案
			step_3.put("name", "待建立实施方案");
			step_3.put("time", apply.getProvinceAuditTime());
			step_3.put("state", "false");
			list.add(step_3);
			return "pro_check/progress";
		}
		
		QualityIdentification quality = (QualityIdentification)qiService.find("from QualityIdentification where applyBh = ?", applyBh).get(0);
		step_3.put("name", "建立实施方案");
		step_3.put("time", apply.getProvinceAuditTime());
		step_3.put("state", "true");
		list.add(step_3);
		Map<String, Object> step_4 = new HashMap<String, Object>();
		if (state >= 10025 && state <= 100027) {
			step_4.put("name", "待审核实施方案");
			step_4.put("time", quality.getBasicAuditTime());
			step_4.put("state", "false");
			list.add(step_4);
			return "pro_check/progress"; 
		}
		step_4.put("name", "实施方案审核通过");
		step_4.put("time", quality.getProvincialAuditTime());
		step_4.put("state", "true");
		list.add(step_4);
		
		// 4.品质档案
		Map<String, Object> step_5 = new HashMap<String, Object>();
		if (state == 100028) {
			step_5.put("name", "待生成品质档案");
			step_5.put("time", quality.getProvincialAuditTime());
			step_5.put("state", "false");
			list.add(step_5);
			return "pro_check/progress"; 
		}
		Reportinfomation report =(Reportinfomation)reportService.find("from Reportinfomation where applyBh = ?",applyBh).get(0);
		step_5.put("name", "生成品质档案");
		step_5.put("time", report.getReportAddTime());
		step_5.put("state", "true");
		list.add(step_5);
		// 5.项目归档
		Map<String, Object> step_6 = new HashMap<String, Object>();
		if (state == 100029) {
			step_6.put("name", "待归档");
			step_6.put("time", quality.getProvincialAuditTime());
			step_6.put("state", "false");
			list.add(step_6);
			return "pro_check/progress"; 
		}
		IdentificationRecord record = (IdentificationRecord) recordService.find("from  IdentificationRecord where applyBh = ?", applyBh);
		step_6.put("name", "归档成功");
		step_6.put("time", record.getFilingTime());
		step_6.put("state", "true");
		list.add(step_6);

		return "pro_check/progress";
	}
}
