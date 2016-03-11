package edu.cuit.module.label.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.label.entity.Labermanagement;
import edu.cuit.module.label.service.LabelscanrecordService;
import edu.cuit.module.label.service.LabermanagementService;

@Controller
@RequestMapping("/label/apply/label")
public class LabelMController extends BaseController{
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCountSize;
	@Autowired
	LabermanagementService labermanagementService;
	@Autowired
	LabelscanrecordService labelscanrecordService;
	
	//分页查询标签申请列表
	private Page getLabelInfoList(String queryString, String applyBh, Integer pageIndex){
		Page page;
		String sqlString = "";
		if (pageIndex == null){
			pageIndex = pageNo;
		}
		if(queryString == null || queryString.equals(""))
		{			
			sqlString = "from Labermanagement as lmodel where lmodel.applyBh='"+applyBh+"'order by lmodel.scanNum desc";
		}else{
			sqlString = "from Labermanagement as lmodel where lmodel.applyBh='"+applyBh+"'and lmodel.labelQueryId like '%"+queryString+"%'order by lmodel.scanNum desc";
		}
		page = labermanagementService.getPage(sqlString, pageIndex-1, pageCountSize);
		return page;
	}	
	
	//分页查询标签扫描记录
	private Page getLabelScanInfolist(String queryString, String labelId, Integer pageIndex)
	{
		Page page;
		String sqlString = "";
		if (pageIndex == null){
			pageIndex = pageNo;
		}
		if(queryString == null || queryString.equals(""))
		{			
			sqlString = "from Labelscanrecord as lmodel where lmodel.labelId='"+labelId+"'order by lmodel.scanTime";
		}else{
			sqlString = "from Labelscanrecord as lmodel where lmodel.labelId='"+labelId+"'and lmodel.scanPlace like '%"+queryString+"%'order by lmodel.scanTime";
		}
		page = labelscanrecordService.getPage(sqlString, pageIndex-1, pageCountSize);
		return page;
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String applyLook(@RequestParam(required = false) Integer requestPageNo, 
			@RequestParam(required = false) String queryString, 
			@RequestParam("key") String pid, 
			@RequestParam("productId") String proId, Model model) {
		if (queryString != null)
			queryString = toUTF8(queryString != null ? queryString : "");
		Page page = getLabelInfoList(queryString, pid, requestPageNo);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		model.addAttribute("pid", pid);
		model.addAttribute("productId", proId);
		return "label/apply/label/List";
	}
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String applyLook_post(@RequestParam(required = false) Integer requestPageNo, 
			@RequestParam(required = false) String queryString, 
			@RequestParam("key") String pid,
			@RequestParam("productId") String proId, Model model) {
		Page page = getLabelInfoList(queryString, pid, requestPageNo);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		model.addAttribute("pid", pid);
		model.addAttribute("productId", proId);
		return "label/apply/label/List";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam("Id") String Id, Model model){
		Labermanagement lm = labermanagementService.get(Id);
		String productId = lm.getApplyOriginCode();
		String applyId = lm.getApplyBh();
		Integer num = lm.getScanNum();
		String scanNum = num.toString();
		String abnormalType = lm.getAbnormalType();
		model.addAttribute("labelId", Id);
		model.addAttribute("applyId", applyId);
		model.addAttribute("productId", productId);
		model.addAttribute("scanNum", scanNum);
		model.addAttribute("abnormalType", abnormalType);
		return "label/apply/label/Edit";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit_post(String labelId, String productId, String applyId,
			String scanNum, String labelStatus, String abnormal, String abnormalType){
		try {
			Labermanagement lm = labermanagementService.get(labelId);
			Integer num = Integer.parseInt(scanNum);
			lm.setScanNum(num);
			lm.setLabelStatus(labelStatus);
			lm.setAbnormal(abnormal);
			lm.setAbnormalType(abnormalType);
			labermanagementService.update(lm);
		} catch (Exception e) {
			System.out.println(e);
		}		
		return "redirect:/label/apply/label/list?key="+applyId+"&productId="+productId;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String del(@RequestParam("key") String pid, 
					  @RequestParam("Id") String Id,
					  @RequestParam("productId") String proId){
		try {
			labermanagementService.deleteByKey(Id);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/label/apply/label/list?key="+pid+"&productId="+proId;
	}
	
	@RequestMapping(value = "scanlist", method = RequestMethod.GET)
	public String scanlist(@RequestParam(required = false) Integer requestPageNo, 
			@RequestParam(required = false) String queryString, 
			@RequestParam("key") String pid, @RequestParam("Id") String labelId,
			@RequestParam("productId") String productId, Model model) {
		if (queryString != null)
			queryString = toUTF8(queryString != null ? queryString : "");
		Page page = getLabelScanInfolist(queryString, labelId, requestPageNo);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		model.addAttribute("labelId", labelId);
		model.addAttribute("applyId", pid);
		model.addAttribute("productId", productId);
		return "label/apply/label/ScanList";
	}
	
	@RequestMapping(value = "scanlist", method = RequestMethod.POST)
	public String sanlist_post(@RequestParam(required = false) Integer requestPageNo, 
			@RequestParam(required = false) String queryString, 
			@RequestParam("key") String pid, @RequestParam("Id") String labelId,
			@RequestParam("productId") String productId, Model model) {
		Page page = getLabelScanInfolist(queryString, labelId, requestPageNo);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		model.addAttribute("labelId", labelId);
		model.addAttribute("applyId", pid);
		model.addAttribute("productId", productId);
		return "label/apply/label/ScanList";
	}
	
}
