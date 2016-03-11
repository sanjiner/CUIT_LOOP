package edu.cuit.module.label.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.cuit.common.util.DateFormat;
import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.auchc.entity.Apply;
import edu.cuit.module.auchc.entity.Approvedatamanager;
import edu.cuit.module.auchc.entity.Bearinginfo;
import edu.cuit.module.auchc.entity.Climatopeinfo;
import edu.cuit.module.auchc.entity.Expertsgrade;
import edu.cuit.module.auchc.entity.Prictureinfomation;
import edu.cuit.module.auchc.entity.QualityIdentification;
import edu.cuit.module.auchc.entity.Reportinfomation;
import edu.cuit.module.authc.service.ApplyService;
import edu.cuit.module.authc.service.ApprovedatamanagerService;
import edu.cuit.module.authc.service.BearinginfoService;
import edu.cuit.module.authc.service.ClimatopeinfoService;
import edu.cuit.module.authc.service.ExpertsgradeService;
import edu.cuit.module.authc.service.PrictureinfomationService;
import edu.cuit.module.authc.service.QualityIdentificationService;
import edu.cuit.module.authc.service.ReportinfomationService;
import edu.cuit.module.authc.service.ReportinfomationotherService;
import edu.cuit.module.infom.entity.Businessmanagement;
import edu.cuit.module.infom.service.BusinessmanagementService;
import edu.cuit.module.infom.service.WeatherstationinfoService;
import edu.cuit.module.label.service.LabelapplicationService;
import edu.cuit.module.label.service.LabelscanrecordService;
import edu.cuit.module.label.service.LabermanagementService;
import edu.cuit.module.origin.entity.Productapply;
import edu.cuit.module.origin.service.CertificateService;
import edu.cuit.module.origin.service.ProductapplyService;
import edu.cuit.module.sys.entity.TbcuitmoonArea;
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;
import edu.cuit.module.sys.service.TbcuitmoonAreaService;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;

@Controller
@RequestMapping("/label/manage")
public class ManageLabelController extends BaseController{
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCountSize;
	@Autowired
	ApplyService applyService;
	@Autowired
	ProductapplyService productapplyService;
	@Autowired
	LabelapplicationService labelApplyService;
	@Autowired
	TbcuitmoonDictionaryService dictionaryService;
	
	
	@Autowired
	LabermanagementService labermanagementService;
	@Autowired
	LabelscanrecordService labelscanrecordService;
	@Autowired
	LabelapplicationService labelapplicationService;
	@Autowired
	TbcuitmoonAreaService tbcuitmoonAreaService;
	@Autowired
	BusinessmanagementService businessmanagementService;
	@Autowired
	CertificateService certificateService;
	@Autowired 
	QualityIdentificationService qualityIdentificationService;
	@Autowired
	ApprovedatamanagerService approvedatamanagerService;
	@Autowired 
	PrictureinfomationService prictureinfomationService;
	@Autowired
	ReportinfomationService reportinfomationService;
	@Autowired
	ReportinfomationotherService reportinfomationotherService;
	@Autowired
	TbcuitmoonDictionaryService tbcuitmoonDictionaryService;
	@Autowired
	WeatherstationinfoService weatherstationinfoService;
	@Autowired
	ClimatopeinfoService climatopeinfoService;
	@Autowired
	BearinginfoService bearinginfoService;
	@Autowired
	ExpertsgradeService expertsgradeService;
	//分页查询认证产品列表
	private Page getProductApplyInfoList(String queryString, 
			String applyType, Integer pageIndex) {
		Page page;		
		String sqlString = "";
		if (pageIndex == null){
			pageIndex = pageNo;
		}	
		//1表示气候品质认证 2表示农网认证
		if(applyType != null && applyType.equals("2"))
		{
			if(queryString == null)
			{
				sqlString = "from Productapply as p where p.orignStatus='100084' order by p.applyTime desc";
			}else{
				sqlString = "from Productapply as p where p.orignStatus='100084' and p.originName like '%" + queryString + "%' order by p.applyTime desc";
			}
			page = productapplyService.getPage(sqlString, pageIndex-1, pageCountSize);
		}else{
			if(queryString == null)
			{
				sqlString = "from Apply as a where a.handleResult='100084' order by a.applyTime desc";
			}else{
				sqlString = "from Apply as a where a.handleResult='100084' and a.productName like '%" + queryString + "%' order by a.applyTime desc";
			}
			page = applyService.getPage(sqlString, pageIndex-1, pageCountSize);
		}		
		return page;
	}	
	
	@RequestMapping(value = "applylist", method = RequestMethod.GET)
	public String manageList(@RequestParam(required = false) Integer requestPageNo, 
			@RequestParam(required = false) String queryString, 
			@RequestParam(required = false) String applyType, Model model, HttpSession session) {
		if (queryString != null)
			queryString = toUTF8(queryString != null ? queryString : "");
		if(applyType == null)
			applyType = "1";
		
		Page page = getProductApplyInfoList(queryString,applyType,requestPageNo);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		model.addAttribute("applyType", applyType);
		
		return "label/manage/ApplyList";
	}

	@RequestMapping(value = "applylist", method = RequestMethod.POST)
	public String applyList_post(@RequestParam(required = false) Integer requestPageNo, 
			@RequestParam(required = false) String queryString, 
			@RequestParam(required = false) String applyType, Model model, HttpSession session){
		if(applyType == null)
			applyType = "1";

		Page page = getProductApplyInfoList(queryString,applyType,requestPageNo);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		model.addAttribute("applyType", applyType);
		return "label/manage/ApplyList";
	}

}
