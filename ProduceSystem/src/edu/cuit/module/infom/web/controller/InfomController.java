package edu.cuit.module.infom.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.cuit.common.util.Page;
import edu.cuit.module.authc.service.ApplyService;
import edu.cuit.module.infom.entity.Businessmanagement;
import edu.cuit.module.infom.service.BusinessmanagementService;
import edu.cuit.module.infom.service.CheckcenternewsreportService;
import edu.cuit.module.infom.service.ExpertmanagementService;
import edu.cuit.module.infom.service.ExpertsgroupService;
import edu.cuit.module.infom.service.WeatherstationinfoService;
import edu.cuit.module.sys.entity.TbcuitmoonArea;
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;
import edu.cuit.module.sys.service.TbcuitmoonAreaService;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;

@Controller
public class InfomController {

	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;

	@Autowired
	BusinessmanagementService businessmanagementservice;
	@Autowired
	TbcuitmoonAreaService tbcuitmoonareaservice;
	@Autowired
	TbcuitmoonDictionaryService tbcuitmoondictionaryservice;
	@Autowired
	ExpertmanagementService  expertmanagementservice;
	@Autowired
	ExpertsgroupService expertsgroupservice;
	@Autowired
	CheckcenternewsreportService checkcenternewsreportservice;
	@Autowired 
	WeatherstationinfoService  weatherstationinfoservice;

	
	
	
	
	
	/**
	 * 
	 * @param requsetPageNo
	 * @param model
	 * @return
	 * 
	 * businessPersonPageListInformation()方法，获取商家列表的数据，并且返回到
	 * BusinessManagement页面
	 */
	@RequestMapping("/infom/BusinessManagement")
	public String businessPersonPageListInformation(
			@RequestParam(required = false) Integer requsetPageNo, Model model) {
		boolean panduan = false;//这个
		List<?> list;//获取商家列表的list
		Page page;
		List<?> list1;//商家列表地区字码转为文字的list
		List<?> list2;//商家列表企业类型字码转为文字的list
		if (requsetPageNo == null) {
			requsetPageNo = pageNo;
		}
		page = businessmanagementservice.getPage("from Businessmanagement",
				requsetPageNo - 1, 20);
		list = page.getPageList();
		
/**
 * for语句完成2个功能，分别是对商家地区，商家类型的数字代码通过字典数据库的对应查找出相对的文字
 */
		for (int i = 0; i < list.size(); i++) {

			String d = ((Businessmanagement) list.get(i)).getBusinessArea()
					.substring(12, 18);
			String w = ((Businessmanagement) list.get(i)).getCompanyType();

			list1 = tbcuitmoonareaservice
					.find("from TbcuitmoonArea where cuitMoonAreaCode like '%"
							+ d + "%'");
			((Businessmanagement) list.get(i))
					.setBusinessArea(((TbcuitmoonArea) list1.get(0))
							.getCuitMoonAreaName());

			list2 = tbcuitmoondictionaryservice
					.find("from TbcuitmoonDictionary where cuitMoonDictionaryCode like '%"
							+ StringUtils.trimWhitespace(w) + "%'");

			if (list2.size() != 0) {
				((Businessmanagement) list.get(i))
						.setCompanyType(((TbcuitmoonDictionary) list2.get(0))
								.getCuitMoonDictionaryName());

			}

		}
		panduan = true;
		model.addAttribute("applyPage", page);
		model.addAttribute("panduan_zhuxinxi", panduan);

		return "/infom/BusinessManagement";
	}

	
/**
 * 
 * @return
 * 
 * businessPersonPageListInformationsd()商家编辑，获取数据，并显示在编辑商家页面
 * 需要的信息
 * 1.用户名称   userName
 * 2.单位名称   campanyName
 * 3.单位性质   campanyType
 * 4.企业类型   companyType
 * 5.商家类型   businessType
 * 6.商家所在地区，包括省，市，县   businessArea
 * 7.法人代表   legalPerson
 * 8.法人代表码   legalPresentCode
 * 9.通讯地址   address
 * 10.业务联系人   cantactPerson
 * 11.联系电话   tel
 * 12.手机   mobilePhone
 * 13.传真   fax
 * 14.企业邮箱   mailBox
 * 15.注册时间   addTime
 * 16.注册商标   logo
 * 17.备注 remark
 * 
 * 
 * 
 */
	
	
	
	
	
	
	@RequestMapping(value = "/infom/BusinessManagementInformationEditor", method = RequestMethod.GET)
	public String businessPersonPageListInformationsd(Model model) {
		
		List list=new ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		model.addAttribute("a", list);
		return "/infom/BusinessManagementInformationEditor";
	}

	@RequestMapping(value = "/infom/BusinessManagementInformationEditor", method = RequestMethod.POST)
	public String businessPersonPageListInformationsd_post(String campanyName,
			String campanyType, String companyType, String businessType,
			String businessArea_shengji, String businessArea_shiji,
			String legalPerson, String legalPresentCode, String address,
			String cantactPerson, String tel, String mobilePhone, String fax,
			String mailBox

	) {

		return "/infom/BusinessManagementInformationEditor";
	}

	
	/**
	 * 
	 * @param find
	 * @param model
	 * @param requsetPageNo
	 * @return
	 * 
	 * businessPersonPageListInformationsd_post()这个方法是对商家单位名称查找出相关的数据并
	 * 返回到BusinessManagement页面
	 * 
	 * 
	 */
	
	
	
	
	
	
	
	@RequestMapping(value = "/infom/BusinessManagement", method = RequestMethod.POST)
	public String businessPersonPageListInformationsd_post(String find,
			Model model, @RequestParam(required = false) Integer requsetPageNo) {
		boolean panduan = false;
		Page page;
		List<?> list;//获取查询出的商家列表的list
		List<?> list1;//获取查询出的商家列表地区字码转为文字的list
		List<?> list2;//获取查询出的商家列表企业类型字码转为文字的list

		if (requsetPageNo == null) {
			requsetPageNo = pageNo;
		}
		page = businessmanagementservice.getPage(
				"from Businessmanagement where campanyName like '%" + find
						+ "%'", requsetPageNo - 1, 20);
		list = page.getPageList();
		for (int i = 0; i < list.size(); i++) {

			String d = ((Businessmanagement) list.get(i)).getBusinessArea()
					.substring(12, 18);
			String w = ((Businessmanagement) list.get(i)).getCompanyType();

			list1 = tbcuitmoonareaservice
					.find("from TbcuitmoonArea where cuitMoonAreaCode like '%"
							+ d + "%'");
			((Businessmanagement) list.get(i))
					.setBusinessArea(((TbcuitmoonArea) list1.get(0))
							.getCuitMoonAreaName());

			list2 = tbcuitmoondictionaryservice
					.find("from TbcuitmoonDictionary where cuitMoonDictionaryCode like '%"
							+ StringUtils.trimWhitespace(w) + "%'");

			if (list2.size() != 0) {
				((Businessmanagement) list.get(i))
						.setCompanyType(((TbcuitmoonDictionary) list2.get(0))
								.getCuitMoonDictionaryName());

			}

		}
		if (list.size() != 0) {
			panduan = true;
		}
		
		model.addAttribute("applyPage", page);
		model.addAttribute("panduan_chaxunxinxi", panduan);

		return "/infom/BusinessManagement";
	}
/**
 * 
 * @return
 * 获取商家详细信息方法
 * 
 */
	@RequestMapping(value = "/infom/BusinessManagementXiangQing", method = RequestMethod.GET)
	public String BusinessManagementXiangQing(String campanyNo,Model model) {
	Businessmanagement bsm=	businessmanagementservice.get(campanyNo);
	//单位性质
	String campanyType=bsm.getCampanyType();
	//企业类型
	String companyType=bsm.getCompanyType();
	//商家类型
	String businessType=bsm.getBusinessType();
	//商家所在地区
	String businessArea=bsm.getBusinessArea();
	String sheng=businessArea.substring(0, 4);
	String shi=businessArea.substring(5, 11);
	String xian=businessArea.substring(12, 18);
	System.out.println(sheng);
	System.out.println(shi);
	System.out.println(xian);
	
	
	String tempBusinessArea=
			((TbcuitmoonArea)  tbcuitmoonareaservice
					.find("from TbcuitmoonArea where cuitMoonAreaCode = "
							+sheng).get(0))
							.getCuitMoonAreaName()+	
							((TbcuitmoonArea)  tbcuitmoonareaservice
					.find("from TbcuitmoonArea where cuitMoonAreaCode = "
							+ shi ).get(0))
							.getCuitMoonAreaName()+
							((TbcuitmoonArea)  tbcuitmoonareaservice
					.find("from TbcuitmoonArea where cuitMoonAreaCode = "
							+ xian ).get(0))
							.getCuitMoonAreaName();
							
		
			System.out.println(((TbcuitmoonArea)  tbcuitmoonareaservice
					.find("from TbcuitmoonArea where cuitMoonAreaCode = "
							+sheng).get(0))
							.getCuitMoonAreaName());
			System.out.println(((TbcuitmoonArea)  tbcuitmoonareaservice
					.find("from TbcuitmoonArea where cuitMoonAreaCode = "
							+shi).get(0))
							.getCuitMoonAreaName());
			System.out.println(((TbcuitmoonArea)  tbcuitmoonareaservice
					.find("from TbcuitmoonArea where cuitMoonAreaCode = "
							+xian).get(0))
							.getCuitMoonAreaName());
//			System.out.println(((TbcuitmoonArea)  tbcuitmoonareaservice
//					.find("from TbcuitmoonArea where cuitMoonAreaCode like '%"
//							+ "1012" + "%'").get(0))
//							.getCuitMoonAreaName());
//			System.out.println(((TbcuitmoonArea)  tbcuitmoonareaservice
//					.find("from TbcuitmoonArea where cuitMoonAreaCode like '%"
//							+ shi + "%'").get(0))
//							.getCuitMoonAreaName());
//			System.out.println(((TbcuitmoonArea)  tbcuitmoonareaservice
//					.find("from TbcuitmoonArea where cuitMoonAreaCode like '%"
//							+ xian + "%'").get(0))
//							.getCuitMoonAreaName());
			
			
			
			
			
			
			
			
			
	bsm.setBusinessArea(tempBusinessArea);

	
	
	
//	
//	String w = ((Businessmanagement) list.get(i)).getCompanyType();
//	
//	
//	
//	
//	String d = ((Businessmanagement) list.get(i)).getBusinessArea()
//			.substring(12, 18);
//	
//	
//	
//	
//	
//	
//	list1 = tbcuitmoonareaservice
//			.find("from TbcuitmoonArea where cuitMoonAreaCode like '%"
//					+ d + "%'");
//	
//	
//	
//	
//	
//	list2 = tbcuitmoondictionaryservice
//			.find("from TbcuitmoonDictionary where cuitMoonDictionaryCode like '%"
//					+ StringUtils.trimWhitespace(w) + "%'");
//	
//	
	
	
	model.addAttribute("bsm", bsm);
	
		return "/infom/BusinessManagementXiangQing";
	}
	
	/**
	 * 
	 * 专家组管理
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	@RequestMapping(value = "/infom/ExpertManagement", method = RequestMethod.GET)
	public String ExpertManagement(@RequestParam(required = false) Integer requsetPageNo, Model model) {
		
		
		boolean panduan = false;//这个
		List<?> list;//获取专家列表的list
		Page page;
		List<?> list1;//商家列表地区字码转为文字的list
		List<?> list2;//商家列表企业类型字码转为文字的list
		if (requsetPageNo == null) {
			requsetPageNo = pageNo;
		}
//		page = businessmanagementservice.getPage("from Businessmanagement",
//				requsetPageNo - 1, 20);
		
		page=expertmanagementservice.getPage("from Expertmanagement", requsetPageNo - 1, 20);
		list = page.getPageList();
		
/**
 * for语句完成2个功能，分别是对商家地区，商家类型的数字代码通过字典数据库的对应查找出相对的文字
 */
//		for (int i = 0; i < list.size(); i++) {
//
//			String d = ((Businessmanagement) list.get(i)).getBusinessArea()
//					.substring(12, 18);
//			String w = ((Businessmanagement) list.get(i)).getCompanyType();
//
//			list1 = tbcuitmoonareaservice
//					.find("from TbcuitmoonArea where cuitMoonAreaCode like '%"
//							+ d + "%'");
//			((Businessmanagement) list.get(i))
//					.setBusinessArea(((TbcuitmoonArea) list1.get(0))
//							.getCuitMoonAreaName());
//
//			list2 = tbcuitmoondictionaryservice
//					.find("from TbcuitmoonDictionary where cuitMoonDictionaryCode like '%"
//							+ StringUtils.trimWhitespace(w) + "%'");
//
//			if (list2.size() != 0) {
//				((Businessmanagement) list.get(i))
//						.setCompanyType(((TbcuitmoonDictionary) list2.get(0))
//								.getCuitMoonDictionaryName());
//
//			}
//
//		}
		panduan = true;
		model.addAttribute("applyPage", page);
		model.addAttribute("panduan_zhuxinxi", panduan);	
		return "/infom/ExpertManagement";
	}
	
	@RequestMapping(value = "/infom/ExpertManagement", method = RequestMethod.POST)
	public String ExpertManagement_find(@RequestParam(required = false) Integer requsetPageNo, Model model) {
		
		
		boolean panduan = false;//这个
		List<?> list;//获取专家列表的list
		Page page;
		List<?> list1;//商家列表地区字码转为文字的list
		List<?> list2;//商家列表企业类型字码转为文字的list
		if (requsetPageNo == null) {
			requsetPageNo = pageNo;
		}
//		page = businessmanagementservice.getPage("from Businessmanagement",
//				requsetPageNo - 1, 20);
		
		page=expertmanagementservice.getPage("from Expertmanagement", requsetPageNo - 1, 20);
		list = page.getPageList();
		
		/**
		 * for语句完成2个功能，分别是对商家地区，商家类型的数字代码通过字典数据库的对应查找出相对的文字
		 */
//		for (int i = 0; i < list.size(); i++) {
//
//			String d = ((Businessmanagement) list.get(i)).getBusinessArea()
//					.substring(12, 18);
//			String w = ((Businessmanagement) list.get(i)).getCompanyType();
//
//			list1 = tbcuitmoonareaservice
//					.find("from TbcuitmoonArea where cuitMoonAreaCode like '%"
//							+ d + "%'");
//			((Businessmanagement) list.get(i))
//					.setBusinessArea(((TbcuitmoonArea) list1.get(0))
//							.getCuitMoonAreaName());
//
//			list2 = tbcuitmoondictionaryservice
//					.find("from TbcuitmoonDictionary where cuitMoonDictionaryCode like '%"
//							+ StringUtils.trimWhitespace(w) + "%'");
//
//			if (list2.size() != 0) {
//				((Businessmanagement) list.get(i))
//						.setCompanyType(((TbcuitmoonDictionary) list2.get(0))
//								.getCuitMoonDictionaryName());
//
//			}
//
//		}
		panduan = true;
		model.addAttribute("applyPage", page);
		model.addAttribute("panduan_zhuxinxi", panduan);	
		return "/infom/ExpertManagement";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * 
	 * 专家组设定
	 * 
	 * 
	 * 
	 */

	
	@RequestMapping(value = "/infom/ExpertsGroup", method = RequestMethod.GET)
	public String ExpertsGroup(@RequestParam(required = false) Integer requsetPageNo, Model model) {
		boolean panduan = false;//这个
		List<?> list;//获取专家列表的list
		Page page;
		List<?> list1;//商家列表地区字码转为文字的list
		List<?> list2;//商家列表企业类型字码转为文字的list
		if (requsetPageNo == null) {
			requsetPageNo = pageNo;
		}
//		page = businessmanagementservice.getPage("from Businessmanagement",
//				requsetPageNo - 1, 20);
		
		page=expertsgroupservice.getPage("from Expertsgroup", requsetPageNo - 1, 20);
		list = page.getPageList();
		
/**
 * for语句完成2个功能，分别是对商家地区，商家类型的数字代码通过字典数据库的对应查找出相对的文字
 */
//		for (int i = 0; i < list.size(); i++) {
//
//			String d = ((Businessmanagement) list.get(i)).getBusinessArea()
//					.substring(12, 18);
//			String w = ((Businessmanagement) list.get(i)).getCompanyType();
//
//			list1 = tbcuitmoonareaservice
//					.find("from TbcuitmoonArea where cuitMoonAreaCode like '%"
//							+ d + "%'");
//			((Businessmanagement) list.get(i))
//					.setBusinessArea(((TbcuitmoonArea) list1.get(0))
//							.getCuitMoonAreaName());
//
//			list2 = tbcuitmoondictionaryservice
//					.find("from TbcuitmoonDictionary where cuitMoonDictionaryCode like '%"
//							+ StringUtils.trimWhitespace(w) + "%'");
//
//			if (list2.size() != 0) {
//				((Businessmanagement) list.get(i))
//						.setCompanyType(((TbcuitmoonDictionary) list2.get(0))
//								.getCuitMoonDictionaryName());
//
//			}
//
//		}
		panduan = true;
		model.addAttribute("applyPage", page);
		model.addAttribute("panduan_zhuxinxi", panduan);
		return "/infom/ExpertsGroup";
	}
	@RequestMapping(value = "/infom/ExpertsGroup", method = RequestMethod.POST)
	public String ExpertsGroup_find() {
		
		return "/infom/ExpertsGroup";
	}
	
	
	/**
	 * 
	 * 
	 * 新闻管理
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	@RequestMapping(value = "/infom/CheckCenterNewsReport", method = RequestMethod.GET)
	public String CheckCenterNewsReport(@RequestParam(required = false) Integer requsetPageNo, Model model) {
		
		
		boolean panduan = false;//这个
		List<?> list;//获取专家列表的list
		Page page;
		List<?> list1;//商家列表地区字码转为文字的list
		List<?> list2;//商家列表企业类型字码转为文字的list
		if (requsetPageNo == null) {
			requsetPageNo = pageNo;
		}
//		page = businessmanagementservice.getPage("from Businessmanagement",
//				requsetPageNo - 1, 20);
		
		page=checkcenternewsreportservice.getPage("from Checkcenternewsreport", requsetPageNo - 1, 20);
		list = page.getPageList();
		
/**
 * for语句完成2个功能，分别是对商家地区，商家类型的数字代码通过字典数据库的对应查找出相对的文字
 */
//		for (int i = 0; i < list.size(); i++) {
//
//			String d = ((Businessmanagement) list.get(i)).getBusinessArea()
//					.substring(12, 18);
//			String w = ((Businessmanagement) list.get(i)).getCompanyType();
//
//			list1 = tbcuitmoonareaservice
//					.find("from TbcuitmoonArea where cuitMoonAreaCode like '%"
//							+ d + "%'");
//			((Businessmanagement) list.get(i))
//					.setBusinessArea(((TbcuitmoonArea) list1.get(0))
//							.getCuitMoonAreaName());
//
//			list2 = tbcuitmoondictionaryservice
//					.find("from TbcuitmoonDictionary where cuitMoonDictionaryCode like '%"
//							+ StringUtils.trimWhitespace(w) + "%'");
//
//			if (list2.size() != 0) {
//				((Businessmanagement) list.get(i))
//						.setCompanyType(((TbcuitmoonDictionary) list2.get(0))
//								.getCuitMoonDictionaryName());
//
//			}
//
//		}
		panduan = true;
		model.addAttribute("applyPage", page);
		model.addAttribute("panduan_zhuxinxi", panduan);
		
		return "/infom/CheckCenterNewsReport";
	}
	@RequestMapping(value = "/infom/CheckCenterNewsReport", method = RequestMethod.POST)
	public String CheckCenterNewsReport_find() {
		
		return "/infom/CheckCenterNewsReport";
	}
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 气象站信息管理
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	

	@RequestMapping(value = "/infom/WeatherStationInfo", method = RequestMethod.GET)
	public String WeatherStationInfo(@RequestParam(required = false) Integer requsetPageNo, Model model) {
		boolean panduan = false;//这个
		List<?> list;//获取专家列表的list
		Page page;
		List<?> list1;//商家列表地区字码转为文字的list
		List<?> list2;//商家列表企业类型字码转为文字的list
		if (requsetPageNo == null) {
			requsetPageNo = pageNo;
		}
//		page = businessmanagementservice.getPage("from Businessmanagement",
//				requsetPageNo - 1, 20);
		
		page=weatherstationinfoservice.getPage("from Weatherstationinfo", requsetPageNo - 1, 20);
		list = page.getPageList();
		System.out.println("王超");
		
/**
 * for语句完成2个功能，分别是对商家地区，商家类型的数字代码通过字典数据库的对应查找出相对的文字
 */
//		for (int i = 0; i < list.size(); i++) {
//
//			String d = ((Businessmanagement) list.get(i)).getBusinessArea()
//					.substring(12, 18);
//			String w = ((Businessmanagement) list.get(i)).getCompanyType();
//
//			list1 = tbcuitmoonareaservice
//					.find("from TbcuitmoonArea where cuitMoonAreaCode like '%"
//							+ d + "%'");
//			((Businessmanagement) list.get(i))
//					.setBusinessArea(((TbcuitmoonArea) list1.get(0))
//							.getCuitMoonAreaName());
//
//			list2 = tbcuitmoondictionaryservice
//					.find("from TbcuitmoonDictionary where cuitMoonDictionaryCode like '%"
//							+ StringUtils.trimWhitespace(w) + "%'");
//
//			if (list2.size() != 0) {
//				((Businessmanagement) list.get(i))
//						.setCompanyType(((TbcuitmoonDictionary) list2.get(0))
//								.getCuitMoonDictionaryName());
//
//			}
//
//		}
		panduan = true;
		model.addAttribute("applyPage", page);
		model.addAttribute("panduan_zhuxinxi", panduan);
		return "/infom/WeatherStationInfo";
	}
	
	@RequestMapping(value = "/infom/WeatherStationInfo", method = RequestMethod.POST)
	public String WeatherStationInfo_find() {
		
		return "/infom/WeatherStationInfo";
	}
}
