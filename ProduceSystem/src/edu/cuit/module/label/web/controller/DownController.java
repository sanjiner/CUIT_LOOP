package edu.cuit.module.label.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import edu.cuit.module.label.entity.Labelproviderecord;
import edu.cuit.module.label.service.LabelproviderecordService;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;

@Controller
@RequestMapping("/label/manage/receive")
public class DownController extends BaseController{
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCountSize;
	@Autowired
	LabelproviderecordService labelproviderecordService;
	@Autowired
	TbcuitmoonDictionaryService dictionaryService;

	//分页查询标签申请列表
	private Page getLabelProvideInfoList(String queryString, String applyBh, Integer pageIndex){
		Page page;
		String sqlString = "";
		if (pageIndex == null){
			pageIndex = pageNo;
		}
		if(queryString == null || queryString.equals(""))
		{			
			sqlString = "from Labelproviderecord as lmodel where lmodel.originId='"+applyBh+"'order by lmodel.receiveTime desc";
		}else{
			sqlString = "from Labelproviderecord as lmodel where lmodel.originId='"+applyBh+"'and lmodel.receivePerson like '%"+queryString+"%'order by lmodel.receiveTime desc";
		}
		page = labelproviderecordService.getPage(sqlString, pageIndex-1, pageCountSize);
		return page;
	}	
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String applyLook(@RequestParam(required = false) Integer requestPageNo, 
			@RequestParam(required = false) String queryString, 
			@RequestParam("key") String pid, Model model) {
		if (queryString != null)
			queryString = toUTF8(queryString != null ? queryString : "");
		Page page = getLabelProvideInfoList(queryString, pid, requestPageNo);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		model.addAttribute("pid", pid);
		return "label/manage/down/List";
	}
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String applyLook_post(@RequestParam(required = false) Integer requestPageNo, 
			@RequestParam(required = false) String queryString, 
			@RequestParam("key") String pid, Model model) {
		Page page = getLabelProvideInfoList(queryString, pid, requestPageNo);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		model.addAttribute("pid", pid);
		return "label/manage/down/List";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam("Id") String Id, Model model){
		Labelproviderecord lpr = labelproviderecordService.get(Id);
		String originId = lpr.getOriginId();
		String receivePerson = lpr.getReceivePerson();
		Integer num = lpr.getReceiveAmount();
		String receiveNum = num.toString();
		String receiveTime = DateFormat.getStrTime(lpr.getReceiveTime(), 1);
		model.addAttribute("receiveId", Id);
		model.addAttribute("originId", originId);
		model.addAttribute("receivePerson", receivePerson);
		model.addAttribute("receiveNum", receiveNum);
		model.addAttribute("receiveTime", receiveTime);
		return "label/manage/down/Edit";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit_post(String receiveId, String originId,
			String receivePerson, String receiveNum, String receiveTime){
		try {
			Labelproviderecord lpr = labelproviderecordService.get(receiveId);
			Integer receiveAmount = Integer.parseInt(receiveNum);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = null;
			date = format.parse(receiveTime);
			lpr.setReceivePerson(receivePerson);
			lpr.setReceiveAmount(receiveAmount);
			lpr.setReceiveTime(date);
			labelproviderecordService.update(lpr);
		} catch (Exception e) {
			System.out.println(e);
		}		
		return "redirect:/label/manage/receive/list?key="+originId;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String del(@RequestParam("key") String pid, @RequestParam("Id") String Id){
		try {
			labelproviderecordService.deleteByKey(Id);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/label/manage/receive/list?key="+pid;
	}
	
}
