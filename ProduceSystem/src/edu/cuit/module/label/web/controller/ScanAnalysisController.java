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
import edu.cuit.module.authc.service.ApplyService;
import edu.cuit.module.label.entity.Labelapplication;
import edu.cuit.module.label.entity.Labermanagement;
import edu.cuit.module.label.service.LabelapplicationService;
import edu.cuit.module.label.service.LabelscanrecordService;
import edu.cuit.module.label.service.LabermanagementService;
import edu.cuit.module.origin.service.ProductapplyService;
import edu.cuit.module.sys.entity.TbcuitmoonRole;
import edu.cuit.module.sys.entity.TbcuitmoonUser;

@Controller
@RequestMapping("ScanAnalysis")
public class ScanAnalysisController extends BaseController{
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCountSize;
	@Autowired
	ApplyService applyService;
	@Autowired
	LabelapplicationService labelApplyService;
	@Autowired
	ProductapplyService productapplyService;
	@Autowired
	LabelscanrecordService labelscanrecordService;
	@Autowired
	LabermanagementService labermanagementService;
	@Autowired
	LabelapplicationService labelapplicationService;
	
	//分页查询认证产品列表
	private Page getProductApplyInfoList(String queryString, String applyType, 
			String userName, String roleStr, Integer pageIndex) {
		Page page;		
		String sqlString = "";
		if (pageIndex == null){
			pageIndex = pageNo;
		}	
		//1表示气候品质认证 2表示农网认证0表示全部
		if(applyType != null && applyType.equals("2"))
		{
			if(roleStr.equals("商家"))
			{
				if(queryString == null)
				{
					sqlString = "from Productapply as p where p.orignStatus='100084' and p.applyPerson='"+userName+"'order by p.applyTime desc";
				}else{
					sqlString = "from Productapply as p where p.orignStatus='100084' and p.applyPerson='"+userName+"' and p.originName like '%" + queryString + "%'order by p.applyTime desc";
				}	
			}else{
				if(queryString == null)
				{
					sqlString = "from Productapply as p where p.orignStatus='100084' order by p.applyTime desc";
				}else{
					sqlString = "from Productapply as p where p.orignStatus='100084' and p.originName like '%" + queryString + "%' order by p.applyTime desc";
				}
			}
			page = productapplyService.getPage(sqlString, pageIndex-1, pageCountSize);
		}else{
			if(roleStr.equals("商家"))
			{
				if(queryString == null)
				{
					sqlString = "from Apply as a where a.handleResult='100084' and a.applyPerson='"+userName+"'order by a.applyTime desc";
				}else{
					sqlString = "from Apply as a where a.handleResult='100084' and a.applyPerson='"+userName+"' and a.productName like '%" + queryString + "%'order by a.applyTime desc";
				}			
			}else{
				if(queryString == null)
				{
					sqlString = "from Apply as a where a.handleResult='100084' order by a.applyTime desc";
				}else{
					sqlString = "from Apply as a where a.handleResult='100084' and a.productName like '%" + queryString + "%' order by a.applyTime desc";
				}
			}
			page = applyService.getPage(sqlString, pageIndex-1, pageCountSize);
		}		
		return page;
	}
	
	//查询该商家是否有认证申请
	private String checkApplylist(String applyPerson){
		String flag = "";
		String hql ="from Apply as a where a.handleResult='100084' and a.applyPerson='"+applyPerson+"'";
		List<?> list = applyService.find(hql);
		if(list.size()>0)
		{
			flag = "1";
		}else{
			flag = "2";
		}
		return flag;
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String applyList(@RequestParam(required = false) Integer requestPageNo, 
			@RequestParam(required = false) String queryString, 
			@RequestParam(required = false) String applyType, Model model, HttpSession session) {
		if (queryString != null)
			queryString = toUTF8(queryString != null ? queryString : "");
		if(applyType == null)
			applyType = "1";
		TbcuitmoonUser user = getLoginUser(session);
		String userName = user.getCuitMoonUserName();		
		String roleStr = user == null ? "" : ((TbcuitmoonRole) user
				.getTbcuitmoonRoles().toArray()[0]).getCuitMoonRoleName() + "";
		if(roleStr.equals("商家"))
		{
			applyType = checkApplylist(userName);
		}		
		Page page = getProductApplyInfoList(queryString,applyType,userName,roleStr,requestPageNo);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		model.addAttribute("applyType", applyType);
		
		return "labelscan/ScanAnalysisList";
	}
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String applyList_post(@RequestParam(required = false) Integer requestPageNo, 
			@RequestParam(required = false) String queryString, 
			@RequestParam(required = false) String applyType, Model model, HttpSession session){
		if(applyType == null)
			applyType = "1";
		TbcuitmoonUser user = getLoginUser(session);
		String userName = user.getCuitMoonUserName();
		String roleStr = user == null ? "" : ((TbcuitmoonRole) user
				.getTbcuitmoonRoles().toArray()[0]).getCuitMoonRoleName() + "";
		if(roleStr.equals("商家"))
		{
			applyType = checkApplylist(userName);
		}
		Page page = getProductApplyInfoList(queryString,applyType,userName,roleStr,requestPageNo);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		model.addAttribute("applyType", applyType);
		return "labelscan/ScanAnalysisList";
	}
	
	@RequestMapping(value="show", method = RequestMethod.GET)
	public String analysisShow(@RequestParam("originID") String originID, String name, String time, Model model){
		model = monthsScanInfo(originID,model);
		model = provinceScanInfo(originID,model);
		model = numScanInfo(originID, model);
		model = piciScanInfo(originID, model);
		String[] a= time.split(" ");
		model.addAttribute("name", name);
		model.addAttribute("time", a[0]);
		return "labelscan/ScanAnalysisShow";
	}
	
	//计算每个月的扫描量
	private Model monthsScanInfo(String originID, Model model){
		List<String> months = new ArrayList<String>();
		List<String> value = new ArrayList<String>();
		Date date = new Date();
		String year = DateFormat.getStrTime(date, 4).substring(0, 4);
		for(int i=0;i<12;i++)
		{
			String monthStr = year + "-" + "0" + String.valueOf(i+1);
			if(i>9)
			{
				monthStr = year + "-" + String.valueOf(i+1);
			}
			String month = "'" + String.valueOf(i+1) + "月" + "'";
			String hql = "from Labelscanrecord as lsr where lsr.originID='"+originID+"' and lsr.scanTime like '%"+monthStr+"%'";
			List<?> list = labelscanrecordService.find(hql);
			value.add(String.valueOf(list.size()));
			months.add(month);
		}
		model.addAttribute("monYlist", value);
		model.addAttribute("monXlist", months);
		model.addAttribute("montitle", "'每个月的扫描量分析'");
		model.addAttribute("monsubtitle", "'"+year+"'");
		model.addAttribute("monYname", "'扫描次数'");
		model.addAttribute("monXname", "'扫描量'");
		return model;
	}
	
	//计算每个省的扫描量
	private Model provinceScanInfo(String originID, Model model){
		String[] provinceInfo = {"安徽省","北京市","重庆市","福建省","甘肃省","广东省",
								 "贵州省","海南省","河北省","河南省","黑龙江省","湖北省",
								 "湖南省","吉林省","江苏省","辽宁省","山东省","山西省",
								 "陕西省","上海市","四川省","天津市","云南省","浙江省","其他"};
		Date date = new Date();
		String year = DateFormat.getStrTime(date, 4).substring(0, 4);
		List<String> xlist = new ArrayList<String>();
		List<String> ylist = new ArrayList<String>();
		int n=0,m=0;
		for(int i=0;i<provinceInfo.length;i++)
		{
			String xStr = "'"+provinceInfo[i]+"'";
			if(provinceInfo[i].equals("其他"))
			{
				String hql = "from Labelscanrecord as lsr where lsr.originID='"+originID+"'";
				List<?> list = labelscanrecordService.find(hql);
				m = list.size() - n;
				ylist.add(String.valueOf(m));
			}else{
				String hql = "from Labelscanrecord as lsr where lsr.originID='"+originID+"' and lsr.province='"+provinceInfo[i]+"'";
				List<?> list = labelscanrecordService.find(hql);
				n = n + list.size();
				ylist.add(String.valueOf(list.size()));
			}
			xlist.add(xStr);
		}
		model.addAttribute("proYlist", ylist);
		model.addAttribute("proXlist", xlist);
		model.addAttribute("protitle", "'各省的扫描量分析'");
		model.addAttribute("prosubtitle", "'"+year+"'");
		model.addAttribute("proYname", "'扫描次数'");
		model.addAttribute("proXname", "'扫描量'");
		return model;
	}

	//计算扫描次数的扫描量
	private Model numScanInfo(String originID, Model model){
		String[] numInfo = {"0次","1次","2次","3次","4次","5次",
				 			"6次","7次","8次","9次","10次","10次以上"};
		Date date = new Date();
		String year = DateFormat.getStrTime(date, 4).substring(0, 4);
		List<String> xlist = new ArrayList<String>();
		List<String> ylist = new ArrayList<String>();
		int n=0,m=0;
		for(int i=0;i<numInfo.length;i++)
		{
			String xStr = "'"+numInfo[i]+"'";
			if(numInfo[i].equals("10次以上"))
			{
				String hql = "from Labermanagement as lm where lm.applyOriginCode='"+originID+"'";	
				List<?> list = labermanagementService.find(hql);
				m = list.size() - n;
				ylist.add(String.valueOf(m));
			}else{
				String hql = "from Labermanagement as lm where lm.applyOriginCode='"+originID+"' and lm.scanNum='"+i+"'";
				List<?> list = labermanagementService.find(hql);
				n = n + list.size();
				ylist.add(String.valueOf(list.size()));
			}
			xlist.add(xStr);
		}
		model.addAttribute("numYlist", ylist);
		model.addAttribute("numXlist", xlist);
		model.addAttribute("numtitle", "'标签扫描次数分析'");
		model.addAttribute("numsubtitle", "'"+year+"'");
		model.addAttribute("numYname", "'标签个数'");
		model.addAttribute("numXname", "'标签量'");
		return model;
	}
	
	//计算扫描批次的扫描量
	private Model piciScanInfo(String originID, Model model){
		String[] piciInfo = {"0","0","0","0","0","0",
	 			"0","0","0","0","0","0","0"};
		Date date = new Date();
		String year = DateFormat.getStrTime(date, 4).substring(0, 4);
		List<String> xlist = new ArrayList<String>();
		List<String> ylist = new ArrayList<String>();
		//查询申请批次
		String hql1 = "from Labelapplication as la where la.applyBh='"+originID+"'";
		List<?> lalist = labelapplicationService.find(hql1);
		for(int i=0;i<lalist.size();i++)
		{
			Labelapplication lamodel = (Labelapplication)(lalist.get(i));
			piciInfo[i] = (lamodel.getLabelApplicationId());
		}
		for(int i=0;i<piciInfo.length;i++)
		{
			if(piciInfo[i].equals("0"))
			{
				xlist.add("' '");
			}else{
				xlist.add("'"+"第"+(i+1)+"批"+"'");
			}
			String hql2 = "from Labelscanrecord as lsr where lsr.labelApplyBh='"+piciInfo[i]+"'";
			List<?> lslist = labelscanrecordService.find(hql2);
			ylist.add(String.valueOf(lslist.size()));						
		}
		model.addAttribute("piciYlist", ylist);
		model.addAttribute("piciXlist", xlist);
		model.addAttribute("picititle", "'标签批次扫描分析'");
		model.addAttribute("picisubtitle", "'"+year+"'");
		model.addAttribute("piciYname", "'扫描次数'");
		model.addAttribute("piciXname", "'扫描量'");
		return model;
	}
	
}
