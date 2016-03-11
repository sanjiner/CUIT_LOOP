package edu.cuit.module.label.web.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.cuit.common.util.DateFormat;
import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.auchc.entity.Apply;
import edu.cuit.module.authc.service.ApplyService;
import edu.cuit.module.infom.entity.Businessmanagement;
import edu.cuit.module.infom.service.BusinessmanagementService;
import edu.cuit.module.label.service.LabelapplicationService;
import edu.cuit.module.label.service.LabelproviderecordService;
import edu.cuit.module.label.service.LabermanagementService;
import edu.cuit.module.label.entity.Labelapplication;
import edu.cuit.module.label.entity.Labelproviderecord;
import edu.cuit.module.label.entity.Labermanagement;
import edu.cuit.module.origin.entity.Productapply;
import edu.cuit.module.origin.service.ProductapplyService;
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;
import edu.cuit.module.sys.entity.TbcuitmoonUser;
import edu.cuit.module.sys.service.TbcuitmoonAreaService;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;

@Controller
@RequestMapping("/label/manage/application")
public class ApplicationController extends BaseController{
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCountSize;
	@Autowired
	ApplyService applyService;
	@Autowired
	LabelapplicationService labelApplyService;
	@Autowired
	LabermanagementService labermanagementService;
	@Autowired
	LabelproviderecordService labelproviderecordService;
	@Autowired
	TbcuitmoonDictionaryService tbcuitmoonDictionaryService;
	@Autowired
	TbcuitmoonAreaService tbcuitmoonAreaService;
	@Autowired
	ProductapplyService productapplyService;
	@Autowired
	BusinessmanagementService businessmanagementService;

	//分页查询标签申请列表
	private Page getLabelApplyInfoList(String queryString, String applyBh, Integer pageIndex){
		Page page;
		String sqlString = "";
		if (pageIndex == null){
			pageIndex = pageNo;
		}
		if(queryString == null || queryString.equals(""))
		{
			sqlString = "from Labelapplication as lmodel where lmodel.applyBh='"+applyBh+"'order by lmodel.applyTime desc";
		}else{
			sqlString = "from Labelapplication as lmodel where lmodel.applyBh='"+applyBh+"'and lmodel.piCi like '%"+queryString+"%'order by lmodel.applyTime desc";
		}
		page = labelApplyService.getPage(sqlString, pageIndex-1, pageCountSize);
		return page;
	}	
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String applyLook(@RequestParam(required = false) Integer requestPageNo, 
			@RequestParam(required = false) String queryString, 
			@RequestParam("key") String pid, Model model) {
		if (queryString != null)
			queryString = toUTF8(queryString != null ? queryString : "");
		Page page = getLabelApplyInfoList(queryString, pid, requestPageNo);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		for(int i=0;i<page.getPageList().size();i++)
		{
			String code = ((Labelapplication)(page.getPageList().get(i))).getLabelMakeStatus();
			code = analyzeCode(code);
			((Labelapplication)(page.getPageList().get(i))).setLabelMakeStatus(code);
		}
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		model.addAttribute("pid", pid);
		return "label/manage/application/List";
	}
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String applyLook_post(@RequestParam(required = false) Integer requestPageNo, 
			@RequestParam(required = false) String queryString, 
			@RequestParam("key") String pid, Model model) {
		
		Page page = getLabelApplyInfoList(queryString, pid, requestPageNo);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		for(int i=0;i<page.getPageList().size();i++)
		{
			String code = ((Labelapplication)(page.getPageList().get(i))).getLabelMakeStatus();
			code = analyzeCode(code);
			((Labelapplication)(page.getPageList().get(i))).setLabelMakeStatus(code);
		}
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		model.addAttribute("pid", pid);
		return "label/manage/application/List";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam("Id") String Id, Model model){
		Labelapplication lamodel = labelApplyService.get(Id);
		String labelApplicationId = lamodel.getLabelApplicationId();
		String applyBh = lamodel.getApplyBh();
		String applyPerson = lamodel.getApplyPerson();
		String applyType = lamodel.getApplyType();
		String applyNum = lamodel.getConfirmor();
		String applyTime = DateFormat.getStrTime(lamodel.getApplyTime(), 1);
		String pici = lamodel.getPiCi();
		String makeStatus = analyzeCode(lamodel.getLabelMakeStatus());
		String applyReason = lamodel.getApplyReason();
		String makeStatus2 = "制作完成";
		String format = lamodel.getFormat();
		if(makeStatus.equals("制作完成"))
		{
			makeStatus2 = "未制作";
		}
		
		model.addAttribute("labelApplicationId",labelApplicationId);
		model.addAttribute("applyBh",applyBh);
		model.addAttribute("applyPerson",applyPerson);
		model.addAttribute("applyType",applyType);
		model.addAttribute("applyNum",applyNum);
		model.addAttribute("applyTime",applyTime);
		model.addAttribute("pici",pici);
		model.addAttribute("makeStatus", makeStatus);
		model.addAttribute("makeStatus2", makeStatus2);
		model.addAttribute("applyReason",applyReason);
		model.addAttribute("format",format);
		return "label/manage/application/Edit";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit_post(String labelApplicationId, String applyBh,
			String applyPerson, String labelType, String applyNum,
			String pici, String applyTime, String makeStatus,
			String applyReason, String format){
		try {
			Labelapplication lamodel = labelApplyService.get(labelApplicationId);
			lamodel.setApplyPerson(applyPerson);
			lamodel.setApplyType(labelType);
			lamodel.setConfirmor(applyNum);
			lamodel.setInTotal(applyNum);
			lamodel.setLabelMakeStatus(makeStatus);
			lamodel.setPiCi(pici);
			lamodel.setApplyTime(DateFormat.strToDate(applyTime));
			lamodel.setApplyReason(applyReason);
			lamodel.setFormat(format);
			labelApplyService.update(lamodel);
		} catch (Exception e) {
			System.out.println(e);
		}		
		return "redirect:/label/manage/application/list?key="+applyBh;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String del(@RequestParam("key") String pid, @RequestParam("Id") String Id){
		try {
			labelApplyService.deleteByKey(Id);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/label/manage/application/list?key="+pid;
	}
	
	@RequestMapping(value = "make", method = RequestMethod.GET)		
	public String make(@RequestParam("key") String Id, Model model){
		Labelapplication lamodel = labelApplyService.get(Id);
		String labelApplicationId = lamodel.getLabelApplicationId();
		String applyBh = lamodel.getApplyBh();
		String applyPerson = lamodel.getApplyPerson();
		String applyType = lamodel.getApplyType();
		String applyNum = lamodel.getConfirmor();
		String applyTime = DateFormat.getStrTime(lamodel.getApplyTime(), 1);
		String pici = lamodel.getPiCi();
		String makeStatus = analyzeCode(lamodel.getLabelMakeStatus());
		String applyReason = lamodel.getApplyReason();
		//删除已制作的标签
		delOldLabel(Id);
		model.addAttribute("labelApplicationId",labelApplicationId);
		model.addAttribute("applyBh",applyBh);
		model.addAttribute("applyPerson",applyPerson);
		model.addAttribute("applyType",applyType);
		model.addAttribute("applyNum",applyNum);
		model.addAttribute("applyTime",applyTime);
		model.addAttribute("pici",pici);
		model.addAttribute("makeStatus", makeStatus);
		model.addAttribute("applyReason",applyReason);
		return "label/manage/application/MakeLabel";
	}
	
	@RequestMapping(value="makelabel",method = RequestMethod.GET)
	public void updateScanInfo(HttpServletRequest request,HttpServletResponse response){
		String applyBh = request.getParameter("applyBh");
		String labelApplicationId = request.getParameter("labelApplicationId");		
		try {
			Labelapplication lamodel = labelApplyService.get(labelApplicationId);
			String labelType = lamodel.getApplyType().trim(); //标签类型
			String applyOriginType = "1000161"; 
			String originCode = "";  //溯源查询码
			if(labelType.equals("农网认证"))
			{
				String Buser = getBusinessUserName(applyBh, "2");
				originCode = getAreaCode(Buser);
				applyOriginType = "1000163";
			}else{
				String Buser = getBusinessUserName(applyBh, "1");
				originCode = getAreaCode(Buser);
				applyOriginType = "1000161";
			}
			Integer num = Integer.parseInt(lamodel.getConfirmor()); //标签申请数量		
			//生成标签
			makeLabel(labelApplicationId,applyBh,labelType,applyOriginType,originCode,num,request,response);
			//更新标签申请表
			lamodel.setLabelMakeStatus("1000191");
			lamodel.setLabelPayment("1000181");
			lamodel.setApplyStatus("处理完成");
			labelApplyService.update(lamodel);
		} catch (Exception e) {
			System.out.println(e);
		}			         
	}	  

	@RequestMapping(value="downExcel",method=RequestMethod.GET)  
	public ModelAndView viewExcel(HttpServletRequest request,  
            HttpServletResponse response, HttpSession session) {  
		String pid = request.getParameter("key");
		List<?> list = getLabelInfolist(pid);
		Map<String,Object> map = new HashMap<String,Object>();   
        map.put("list", list);
        map.put("filename", pid);
        addReceiveInfo(pid,list.size(),session);
        return new ModelAndView(new ViewExcel(), map);  
    }  
	
	@RequestMapping(value = "down", method = RequestMethod.GET)		
	public String down(@RequestParam("key") String Id, Model model){
		Labelapplication lamodel = labelApplyService.get(Id);
		String labelApplicationId = lamodel.getLabelApplicationId();
		String applyBh = lamodel.getApplyBh();
		String applyPerson = lamodel.getApplyPerson();
		String applyType = lamodel.getApplyType();
		String applyNum = lamodel.getConfirmor();
		String applyTime = DateFormat.getStrTime(lamodel.getApplyTime(), 1);
		String pici = lamodel.getPiCi();
		String makeStatus = analyzeCode(lamodel.getLabelMakeStatus());
		String applyReason = lamodel.getApplyReason();
		
		model.addAttribute("labelApplicationId",labelApplicationId);
		model.addAttribute("applyBh",applyBh);
		model.addAttribute("applyPerson",applyPerson);
		model.addAttribute("applyType",applyType);
		model.addAttribute("applyNum",applyNum);
		model.addAttribute("applyTime",applyTime);
		model.addAttribute("pici",pici);
		model.addAttribute("makeStatus", makeStatus);
		model.addAttribute("applyReason",applyReason);
		return "label/manage/application/DownLabel";
	}
	
	//解析字典编码
	private String analyzeCode(String code){
		String name = "";
		String hql = "from TbcuitmoonDictionary as dmodel where dmodel.cuitMoonDictionaryCode='" + code + "'";
		name = ((TbcuitmoonDictionary)tbcuitmoonDictionaryService.find(hql).get(0)).getCuitMoonDictionaryName();
		return name;
	}
	
    //添加领用记录
	private void addReceiveInfo(String pid, int num, HttpSession session){
		try {
			Labelapplication la = labelApplyService.get(pid);
			String originId = la.getApplyBh();
			String receiveId = UUID.randomUUID().toString().replace("-", "");
			Date receiveTime = new Date();
			TbcuitmoonUser user = getLoginUser(session);
			String userName = user.getCuitMoonUserName();
			Labelproviderecord lp = new Labelproviderecord();
			lp.setReceiveId(receiveId);
			lp.setApplyBh(pid);
			lp.setOriginId(originId);
			lp.setReceiveAmount(num);
			lp.setReceivePerson(userName);
			lp.setReceiveTime(receiveTime);
			labelproviderecordService.save(lp);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//根据标签申请编号查询标签
	private List<?> getLabelInfolist(String pid){
		String hql = "from Labermanagement as la where la.applyBh='"+pid+"'";
		List<?> list = labermanagementService.find(hql);		
		return list;
	}
	
	//删除已制作的标签
	private void delOldLabel(String labelApplicationId){
		String hql = "from Labermanagement as la where la.applyBh='"+labelApplicationId+"'";
		List<?> list = labermanagementService.find(hql);
		if(list.size()>0)
		{
			String delHql = "delete from Labermanagement as la where la.applyBh='"+labelApplicationId+"'";
			labermanagementService.bulkUpdate(delHql);
		}
	}
	
	//生成标签
	private void makeLabel(String labelApplicationId, String applyOriginCode, 
			String labelKinds, String applyOriginType, String originCode, Integer num,
			HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String startTime = DateFormat.getStrTime(new Date(), 1);
		ProgressShow ps = new ProgressShow();
		ps.doGet(request, response);
		ps.BeginTrans(request, response);
		double m = num / 100.0;
		for(int i=0; i<num;i++)
		{			
			String labelId =  UUID.randomUUID().toString().replace("-", "");
			Date makeTime = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String timeStr =format.format(makeTime);
			String remark = originCode + timeStr;
			String labelQueryId = getMD5(remark);
			
			Labermanagement lmmodel = new Labermanagement();
			lmmodel.setLabelId(labelId);
			lmmodel.setLabelQueryId(labelQueryId);
			lmmodel.setLabelKinds(labelKinds);
			lmmodel.setScanNum(0);
			lmmodel.setMakeTime(makeTime);
			lmmodel.setLabelStatus("有效");
			lmmodel.setReark(remark);
			lmmodel.setApplyOriginType(applyOriginType);
			lmmodel.setApplyOriginCode(applyOriginCode);
			lmmodel.setApplyBh(labelApplicationId);
			lmmodel.setAbnormal("否");
			labermanagementService.save(lmmodel);
			
			int k = ((int)(i/m)) + 1;
			String s = String.valueOf(k);
			ps.SetPorgressBar(request, response, s);
		}	
		ps.EndTrans(request,response);
		String endTime = DateFormat.getStrTime(new Date(), 1);
		String timeSpan = getTimeSpan(startTime,endTime);
		ps.SetTimeInfo(request, response, timeSpan);
		ps.back(request, response, applyOriginCode);
	}
	
	//计算标签生成时间
	private String getTimeSpan(String startTime, String endTime){
		String timeSpan = "";
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin = null;
		Date end = null;
		try {
			begin = dfs.parse(startTime);
			end = dfs.parse(endTime);
			long between = (end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒   
			long day = between/(24*3600);   
			long hour = (between/3600)%24;   
			long minute = (between/60)%60;   
			long second = between%60; 
			if(day>0)
				timeSpan +=String.valueOf(day) + "天";
			if(hour>0)
				timeSpan +=String.valueOf(hour) + "小时";
			if(minute>0)
				timeSpan +=String.valueOf(minute) + "分钟";
			if(second>0)
				timeSpan +=String.valueOf(second) + "秒";
		} catch (ParseException e1) {
			e1.printStackTrace();
		}		
		return timeSpan;
	}
	
	//将溯源查询码MD5成16位
	private String getMD5(String str){
		String code = "";
		try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            code = buf.toString().substring(8, 24);
        } catch (Exception e) {
            System.out.println(e);
        }
		return code;
	}
	
	//查询认证申请商家用户名
	private String getBusinessUserName(String id, String type){
		String username = "";
		if(type.equals("1"))
		{
			Apply amodel = applyService.get(id);
			username = amodel.getApplyPerson();
		}else{
			Productapply pmodel = productapplyService.get(id);
			username = pmodel.getApplyPerson();
		}			
		return username;
	}
	
	//查询区域码和法人代表码
	private String getAreaCode(String id){
		String code = "";
		String hql = "from Businessmanagement as bmodel where bmodel.userName='"+id+"'";
		List<?> list = businessmanagementService.find(hql);
		if(list.size()>0)
		{
			code = ((Businessmanagement)(list.get(0))).getBusinessArea().substring(12, 18);
			code += ((Businessmanagement)(list.get(0))).getLegalPresentCode();
		}
		return code;
	}
	
}
