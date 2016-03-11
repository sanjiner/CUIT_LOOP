package edu.cuit.module.authc.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import edu.cuit.module.auchc.entity.Bearinginfo;
import edu.cuit.module.auchc.entity.ClimateQualitycertificationExperts;
import edu.cuit.module.auchc.entity.IdentificationRecord;
import edu.cuit.module.auchc.entity.MemberGroup;
import edu.cuit.module.auchc.entity.QualityIdentification;
import edu.cuit.module.auchc.entity.Reportinfomation;
import edu.cuit.module.authc.service.ApplyService;
import edu.cuit.module.authc.service.BearinginfoService;
import edu.cuit.module.authc.service.ClimateQualitycertificationExpertsService;
import edu.cuit.module.authc.service.ExpertsgradeService;
import edu.cuit.module.authc.service.IdentificationRecordService;
import edu.cuit.module.authc.service.MemberGroupService;
import edu.cuit.module.authc.service.QualityIdentificationService;
import edu.cuit.module.authc.service.ReportinfomationService;
import edu.cuit.module.infom.entity.Expertmanagement;
import edu.cuit.module.infom.entity.Weatherstationinfo;
import edu.cuit.module.infom.service.ExpertmanagementService;
import edu.cuit.module.infom.service.WeatherstationinfoService;
import edu.cuit.module.model.service.ElementmodelService;
import edu.cuit.module.sys.entity.TbcuitmoonArea;
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;
import edu.cuit.module.sys.service.TbcuitmoonAreaService;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;

@Controller
@RequestMapping("file_recode")
public class FileRecodeController extends BaseController{
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCountSize;
	
	@Autowired
	IdentificationRecordService identificationRecordService;
	@Autowired
	ApplyService applyService;
	@Autowired
	TbcuitmoonAreaService tbcuitmoonAreaService;
	@Autowired
	TbcuitmoonDictionaryService tbcuitmoonDictionaryService;
	@Autowired
	QualityIdentificationService qualityIdentificationService;
	@Autowired
	WeatherstationinfoService weatherstationinfoService;
	@Autowired 
	BearinginfoService bearinginfoService;
	@Autowired
	ElementmodelService elementmodelService;
	@Autowired
	ClimateQualitycertificationExpertsService climateQualitycertificationExpertsService;
	@Autowired
	MemberGroupService memberGroupService;
	@Autowired
	ExpertmanagementService expertmanagementService;
	@Autowired 
	ExpertsgradeService expertsgradeService;
	@Autowired
	ReportinfomationService reportinfomationService;
	
	private Page getIdentificationRecordInfoList(String queryString, Integer pageIndex) {
		if (pageIndex == null)
			pageIndex = pageNo;
		String sqlString = "";
		if(queryString == null){
			sqlString = "from IdentificationRecord order by filingTime desc";
		}else{
			sqlString = "from IdentificationRecord where projectName like '%" + queryString + "%' order by filingTime desc";
		}
		Page page = identificationRecordService.getPage(sqlString, pageIndex - 1,
				pageCountSize);
		return page;
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String FRlist(@RequestParam(required = false) Integer requestPageNo,
			@RequestParam(required = false) String queryString, Model model)
			throws UnsupportedEncodingException {
		if (queryString != null)
			queryString = toUTF8(queryString != null ? queryString : "");
		
		//查询待归档的项目
		List<?> alist = getProductlist("100029");
		Page page = getIdentificationRecordInfoList(queryString, requestPageNo);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("productlist", alist);
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		return "file_recode/FRlist";
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String FRlist(String queryString, @RequestParam(required = false) Integer requestPageNo, Model model)
			throws UnsupportedEncodingException {
		//查询待归档的项目
		List<?> alist = getProductlist("100029");
		model.addAttribute("productlist", alist);
		Page page = getIdentificationRecordInfoList(queryString, requestPageNo);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("productlist", alist);
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		return "file_recode/FRlist";
	}
	
	//查询待归档的项目
	private List<?> getProductlist(String code){
		String hql = "from Apply as amodel where amodel.declareStatus='"+code+"'";
		List<?> alist = applyService.find(hql);
		return alist;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String FRdel(@RequestParam("key") String Id){
		try {
			IdentificationRecord IRecode = identificationRecordService.get(Id);
			String applyBh = IRecode.getApplyBh();
			identificationRecordService.deleteByKey(Id);			
			//更新申请表的申报状态为待归档
			Apply apply = applyService.get(applyBh);
			apply.setDeclareStatus("100029");
			applyService.update(apply);
		} catch (Exception e) {
			System.out.println(e);
		}		
		return "redirect:/file_recode/list";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String FRedit(@RequestParam("key") String Id, Model model){
		IdentificationRecord IRecode = identificationRecordService.get(Id);
		String proName = IRecode.getProjectName();
		String proNo = IRecode.getFileCode();
		String person = IRecode.getArchivePeople();
		String time = IRecode.getFilingTime();
		String remark = IRecode.getRemark();
		model.addAttribute("primaryKey", Id);
		model.addAttribute("proName", proName);
		model.addAttribute("proNo", proNo);
		model.addAttribute("person", person);
		model.addAttribute("protime", time);
		model.addAttribute("remark", remark);
		return "file_recode/FRedit";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String FRedit_post(String primaryKey, String proName, String proNo, String Person, 
			String protime, String remark){
		try {
			IdentificationRecord IRecode = identificationRecordService.get(primaryKey);
			IRecode.setProjectName(proName);
			IRecode.setFileCode(proNo);
			IRecode.setArchivePeople(Person);
			IRecode.setFilingTime(protime);
			IRecode.setRemark(remark);
			identificationRecordService.update(IRecode);
		} catch (Exception e) {
			System.out.println(e);
		}		
		return "redirect:/file_recode/list";				
	}
	
	@RequestMapping(value="add", method = RequestMethod.GET)
	public String FRadd(@RequestParam("pid") String pid, Model model){
		
		model.addAttribute("applyBh", pid);
		model = getApplyInfo(pid,model);
		model = getQualityIdentificationInfo(pid,model);
		model = getExpertsInfo(pid,model);
		model = getExpertsgradeInfo(pid,model);
		model = getReportInfo(pid,model);		
		return "file_recode/FRadd";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String FRadd_post(String applyBh, String proName, String proNo, String Person, 
			String protime, String remark){
		try {
			//添加归档记录
			String Id = UUID.randomUUID().toString().replace("-", "");
			IdentificationRecord IRecode = new IdentificationRecord();
			IRecode.setRecordNum(Id);
			IRecode.setApplyBh(applyBh);
			IRecode.setProjectName(proName);
			IRecode.setFileCode(proNo);
			IRecode.setArchivePeople(Person);
			IRecode.setFilingTime(protime);
			IRecode.setRemark(remark);
			identificationRecordService.save(IRecode);
			//更新申请表的申报状态为归档完成
			Apply apply = applyService.get(applyBh);
			apply.setDeclareStatus("100030");
			applyService.update(apply);
		} catch (Exception e) {
			System.out.println(e);
		}		
		return "redirect:/file_recode/list";				
	}
	
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String FRdetail(@RequestParam("key") String Id, Model model){	
		IdentificationRecord IRecode = identificationRecordService.get(Id);
		String proName = IRecode.getProjectName();
		String proNo = IRecode.getFileCode();
		String person = IRecode.getArchivePeople();
		String time = IRecode.getFilingTime();
		String remark = IRecode.getRemark();
		String pid = IRecode.getApplyBh();
		
		model.addAttribute("primaryKey", Id);
		model.addAttribute("proName", proName);
		model.addAttribute("proNo", proNo);
		model.addAttribute("person", person);
		model.addAttribute("protime", time);
		model.addAttribute("remark", remark);
			
		model = getApplyInfo(pid,model);
		model = getQualityIdentificationInfo(pid,model);
		model = getExpertsInfo(pid,model);
		model = getExpertsgradeInfo(pid,model);
		model = getReportInfo(pid,model);
		return "file_recode/FRdetail";
	}
	
	//查询产品申请信息
	private Model getApplyInfo(String Id, Model model){
		Apply apply = applyService.get(Id);
		String productName = apply.getProductName();
		String productBand = apply.getProductBrand();
		String productBase = analyzeProductBase(apply.getProduceBase());
		String scale = apply.getScale();
		String produtOutput = apply.getProdutOutput();
		String outputValue = apply.getOutputValue();
		String baozhiqi = apply.getRemark();
		String productDescription = apply.getProductDescription();
		String unityName  = apply.getUnityName();
		String applyTime = DateFormat.getStrTime(apply.getApplyTime(), 1);
		String applicationType = analyzeCode(apply.getApplicationType());
		String counterMan = apply.getCounterMan();
		String contact = apply.getContact();
		String fax = apply.getFax();
		String phone = apply.getPhone();
		String email = apply.getEmail();
		String address = apply.getAddress();
		String regionHeader = apply.getRegionHeader();
		String regionAuditTime = DateFormat.getStrTime(apply.getRegionAuditTime(),1);
		String regionManageOpinion = apply.getRegionManageOpinion();
		String cityHeader = apply.getCityHeader();
		String cityAuditTime =  DateFormat.getStrTime(apply.getCityAuditTime(),1);
		String cityManageAudit = apply.getCityManageAudit();
		String provinceHeader = apply.getProvinceHeader();
		String provinceAuditTime = DateFormat.getStrTime(apply.getProvinceAuditTime(),1);
		String provinceManageOpinion = apply.getProvinceManageOpinion();
		
		model.addAttribute("productName", productName);
		model.addAttribute("productBand", productBand);
		model.addAttribute("productBase", productBase);
		model.addAttribute("scale", scale);
		model.addAttribute("produtOutput", produtOutput);
		model.addAttribute("outputValue", outputValue);
		model.addAttribute("baozhiqi", baozhiqi);
		model.addAttribute("productDescription", productDescription);
		model.addAttribute("unityName", unityName);
		model.addAttribute("applyTime", applyTime);
		model.addAttribute("applicationType", applicationType);
		model.addAttribute("counterMan", counterMan);
		model.addAttribute("contact", contact);
		model.addAttribute("fax", fax);
		model.addAttribute("phone", phone);
		model.addAttribute("email", email);
		model.addAttribute("address", address);
		model.addAttribute("regionHeader", regionHeader);
		model.addAttribute("regionAuditTime", regionAuditTime);
		model.addAttribute("regionManageOpinion", regionManageOpinion);
		model.addAttribute("cityHeader", cityHeader);
		model.addAttribute("cityAuditTime", cityAuditTime);
		model.addAttribute("cityManageAudit", cityManageAudit);
		model.addAttribute("provinceHeader", provinceHeader);
		model.addAttribute("provinceAuditTime", provinceAuditTime);
		model.addAttribute("provinceManageOpinion", provinceManageOpinion);
		return model;
	}
	
	//解析产地
	private String analyzeProductBase(String productBase){
		String[] Info = productBase.split("\\|");
		String shengstr = Info[0];
		String shistr = Info[1];
		String xianstr = Info[2];
		String hql1 = "from TbcuitmoonArea as tbArea where tbArea.cuitMoonAreaCode='" + shengstr + "'";
		String hql2 = "from TbcuitmoonArea as tbArea where tbArea.cuitMoonAreaCode='" + shistr + "'";
		String hql3 = "from TbcuitmoonArea as tbArea where tbArea.cuitMoonAreaCode='" + xianstr + "'";
		String sheng = ((TbcuitmoonArea) tbcuitmoonAreaService.find(hql1).get(0)).getCuitMoonAreaName();
		String shi = ((TbcuitmoonArea) tbcuitmoonAreaService.find(hql2).get(0)).getCuitMoonAreaName();
		String xian = ((TbcuitmoonArea) tbcuitmoonAreaService.find(hql3).get(0)).getCuitMoonAreaName();
		
		productBase = sheng + shi + xian;
		return productBase;
	}	
	
	//解析字典编码
	private String analyzeCode(String code){
		String name = "";
		String hql = "from TbcuitmoonDictionary as dmodel where dmodel.cuitMoonDictionaryCode='" + code + "'";
		name = ((TbcuitmoonDictionary)tbcuitmoonDictionaryService.find(hql).get(0)).getCuitMoonDictionaryName();
		return name;
	} 
	
	//查询实施方案信息
	private Model getQualityIdentificationInfo(String id, Model model){
		String hql = "from QualityIdentification as qimodel where qimodel.applyBh='" + id + "'";
		List<?> qilist = qualityIdentificationService.find(hql);
		if(qilist.size()>0)
		{
			//实施方案编号
			String qualityIdentificationNum = ((QualityIdentification)qilist.get(0)).getQualityIdentificationNum();
			String applyFrequency = ((QualityIdentification)qilist.get(0)).getApplyFrequency();
			applyFrequency = analyzeCode(applyFrequency);
			String originSituation = ((QualityIdentification)qilist.get(0)).getOriginSituation();
			String meteorologicalDisaster = ((QualityIdentification)qilist.get(0)).getMeteorologicalDisaster();
			String diseasesInsectPests = ((QualityIdentification)qilist.get(0)).getDiseasesInsectPests();
			String basicPanelSign = ((QualityIdentification)qilist.get(0)).getBasicPanelSign();
			Date btime = ((QualityIdentification)qilist.get(0)).getBasicAuditTime();
			String basicAuditTime = "";
			if(btime != null)
			{
				basicAuditTime = DateFormat.getStrTime(btime,1);
			}			
			String basicPanelAuditOpinion = ((QualityIdentification)qilist.get(0)).getBasicPanelAuditOpinion();
			String provincialPanelSign = ((QualityIdentification)qilist.get(0)).getProvincialPanelSign();
			Date ptime = ((QualityIdentification)qilist.get(0)).getProvincialAuditTime();
			String provincialAuditTime = "";
			if(ptime != null)
			{
				provincialAuditTime = DateFormat.getStrTime(ptime,1);
			}
			String provincialPanelAuditOpinion = ((QualityIdentification)qilist.get(0)).getProvincialPanelAuditOpinion();
			String hqlstr = "from Bearinginfo as bmodel where bmodel.qualityIdentificationNum='"+qualityIdentificationNum+"'";
			List<?> blist = bearinginfoService.find(hqlstr);
			blist = getBearlistInfo(blist);
			model.addAttribute("bearlist", blist);
			model.addAttribute("applyFrequency", applyFrequency);
			model.addAttribute("originSituation", originSituation);
			model.addAttribute("meteorologicalDisaster", meteorologicalDisaster);
			model.addAttribute("diseasesInsectPests", diseasesInsectPests);
			model.addAttribute("basicPanelSign", basicPanelSign);
			model.addAttribute("basicAuditTime", basicAuditTime);
			model.addAttribute("basicPanelAuditOpinion", basicPanelAuditOpinion);
			model.addAttribute("provincialPanelSign", provincialPanelSign);
			model.addAttribute("provincialAuditTime", provincialAuditTime);
			model.addAttribute("provincialPanelAuditOpinion", provincialPanelAuditOpinion);
		}		
		return model;
	}
	
	//解析生育期信息
	private List<?> getBearlistInfo(List<?> blist){
		for(int i=0; i<blist.size(); i++)
		{
			String Estr = analyzeElementName(((Bearinginfo)blist.get(i)).getMeteIndicators());
			((Bearinginfo)blist.get(i)).setMeteIndicators(Estr);
			String stationName = getStationName(((Bearinginfo)blist.get(i)).getStationId());
			((Bearinginfo)blist.get(i)).setStationId(stationName);
		}
		return blist;
	}
	
	//解析气象要素名称
	private String analyzeElementName(String Estr){
		String[] Info = Estr.split("\\|");
		String name = "";
		for(int i=0; i<Info.length; i++)
		{
			String str = elementmodelService.get(Info[i]).getElementName();
			name =  str + " " +name;
		}
		return name;
	}
	
	//解析气象站名
	private String getStationName(String id){
		String name = "";
		Weatherstationinfo wmodel = weatherstationinfoService.get(id);
		name = wmodel.getName();
		return name;
	} 
	
	//查询专家组及专家成员信息
	private Model getExpertsInfo(String id, Model model){
		String bgroupName = "";
		String bleader = "";
		String bpeople = "";
		String pgroupName = "";
		String pleader = "";
		String ppeople = "";
		String hqlstr1 = "from ClimateQualitycertificationExperts as cmodel where cmodel.applyBh='"+id+"'";
		List<?> clist = climateQualitycertificationExpertsService.find(hqlstr1);
		for(int i=0; i<clist.size(); i++)
		{
			String expertsNum = ((ClimateQualitycertificationExperts)clist.get(i)).getExpertsNum();
			String expertsLevel = ((ClimateQualitycertificationExperts)clist.get(i)).getExpertsLevel().toString().trim();
			if(expertsLevel.equals("1012"))
			{
				pgroupName = ((ClimateQualitycertificationExperts)clist.get(i)).getExpertsClass().toString().trim();
				String hqlstr2 = "from MemberGroup as mmodel where mmodel.expertsNum='"+expertsNum+"'";
				List<?> mlist = memberGroupService.find(hqlstr2);
				for(int j=0;j<mlist.size();j++)
				{
					String IsGrouper = ((MemberGroup)mlist.get(j)).getIsGrouper().toString().trim();
					String member = ((MemberGroup)mlist.get(j)).getMember();
					if(IsGrouper.equals("100011"))
					{
						pleader = analyzeExpertName(member);
						ppeople = ppeople + " "+ analyzeExpertName(member);
					}else{
						ppeople = ppeople + " "+ analyzeExpertName(member);
					}
				}
			}else{
				bgroupName = ((ClimateQualitycertificationExperts)clist.get(i)).getExpertsClass().toString().trim();
				String hqlstr2 = "from MemberGroup as mmodel where mmodel.expertsNum='"+expertsNum+"'";
				List<?> mlist = memberGroupService.find(hqlstr2);
				for(int j=0;j<mlist.size();j++)
				{
					String IsGrouper = ((MemberGroup)mlist.get(j)).getIsGrouper().toString().trim();
					String member = ((MemberGroup)mlist.get(j)).getMember();
					if(IsGrouper.equals("100011"))
					{
						bleader = analyzeExpertName(member);
						bpeople = bpeople + " "+ analyzeExpertName(member);
					}else{
						bpeople = bpeople + " "+ analyzeExpertName(member);
					}
				}
			}
		}
		model.addAttribute("bgroupName", bgroupName);
		model.addAttribute("bleader", bleader);
		model.addAttribute("bpeople", bpeople);
		model.addAttribute("pgroupName", pgroupName);
		model.addAttribute("pleader", pleader);
		model.addAttribute("ppeople", ppeople);
		return model;
	}
	
	//解析专家名称
	private String analyzeExpertName(String id){
		String name = "";
		Expertmanagement emodel = expertmanagementService.get(id);
		name = emodel.getExpertname();
		return name;
	}
	
	//查询专家打分信息
	private Model getExpertsgradeInfo(String applyBh, Model model){
		// 评分信息
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		QualityIdentification quality = null;
		List<?> q_list = qualityIdentificationService.find("from QualityIdentification where applyBh = ?", applyBh);
		if (q_list.size() > 0) {
			quality = (QualityIdentification)q_list.get(0);
		}
		// 获取生育期信息
		List<?> b_list = bearinginfoService.find(
				"from Bearinginfo where qualityIdentificationNum = ?",
				quality == null ? "" : quality.getQualityIdentificationNum());
		for (int i = 0; i < b_list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", ((Bearinginfo)b_list.get(i)).getCropGrowthPeriod());
			map.put("start", ((Bearinginfo)b_list.get(i)).getStartCollectionTime());
			map.put("end", ((Bearinginfo)b_list.get(i)).getEndCollectionTime());
			map.put("id", ((Bearinginfo)b_list.get(i)).getBearingInfoId());
			list.add(map);
			List<?> grades = expertsgradeService.find(
					"from Expertsgrade where bearingInfoId = ?", ((Bearinginfo)b_list.get(i))
							.getBearingInfoId());
			map.put("grades", grades);
		}
		model.addAttribute("Egradeslist", list);						
		return model;
	}
		
	//查询品质档案信息
	private Model getReportInfo(String id, Model model){
		//查询认证报告		
		String hql = "from Reportinfomation as rmodel where rmodel.applyBh='" + id + "'";
		Reportinfomation reportInfo = ((Reportinfomation)reportinfomationService.find(hql).get(0));
		String report_pName = reportInfo.getProductInfomantion();
		String report_pArea = reportInfo.getAreaInfomation();
		String report_level = analyzeApproveLevel(reportInfo.getApprovelevel());
		Date date = reportInfo.getReportAddTime();
		String report_time =  DateFormat.getStrTime(date, 4);
		String report_agency = reportInfo.getApproveAgency();
		String report_conclusion = reportInfo.getApproveConclusion();
		String report_attachmentUrl = reportInfo.getAttachmentUrl();
		String report_certificateAttachment = reportInfo.getCertificateAttachment();
		String report_certificateImage = reportInfo.getCertificateImage();
		String report_ifOther = reportInfo.getIfOther();
		model.addAttribute("report_pName", report_pName);
		model.addAttribute("report_pArea", report_pArea);
		model.addAttribute("report_level", report_level);
		model.addAttribute("report_time", report_time);
		model.addAttribute("report_agency", report_agency);
		model.addAttribute("report_conclusion", report_conclusion);
		model.addAttribute("report_attachmentUrl", report_attachmentUrl);
		model.addAttribute("report_certificateImage", report_certificateImage);
		model.addAttribute("report_certificateAttachment", report_certificateAttachment);
		model.addAttribute("report_ifOther", report_ifOther);
		return model;
	}
	
	//解析认证等级
	private String analyzeApproveLevel(String code){
		String name = "";
		String hql = "from TbcuitmoonDictionary as dmodel where dmodel.cuitMoonDictionaryCode='" + code + "'";
		name = ((TbcuitmoonDictionary)tbcuitmoonDictionaryService.find(hql).get(0)).getCuitMoonDictionaryName();
		return name;
	}	
			
}
