package edu.cuit.module.model.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.model.entity.Modelformulate;
import edu.cuit.module.model.entity.Productinfomation;
import edu.cuit.module.model.service.ProductinfomationService;
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;

@Controller
@RequestMapping("/model/product")
public class ProductController extends BaseController{
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCountSize;
	@Autowired
	ProductinfomationService productinfomationService;
	@Autowired
	TbcuitmoonDictionaryService dictionaryService;
	@Autowired
	TbcuitmoonDictionaryService tbDicService;
	@Autowired
	ProductinfomationService pfService;
	
	private Page getProductInfoList(String queryString, 
			String parentCode, Integer pageIndex) {
		if (pageIndex == null)
			pageIndex = pageNo;
		String sqlString = "";
		if(queryString == null){
			if(parentCode.equals("0"))
			{
				sqlString = "from Productinfomation as pf where pf.productType like '%10005%'";				
			}else{
				sqlString = "from Productinfomation as pf where pf.productType='"+parentCode+"'";
			}
		}else{
			if(parentCode.equals("0"))
			{
				sqlString = "from Productinfomation as pf where pf.productName like '%" + queryString + "%' and pf.productType like '%10005%'";
			}else{
				sqlString = "from Productinfomation as pf where pf.productName like '%" + queryString + "%' and pf.productType='"+parentCode+"'";
			}
		}
		Page page = productinfomationService.getPage(sqlString, pageIndex - 1,pageCountSize);
		return page;
	}
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String modelIndex(Model model){
		String hql = "from TbcuitmoonDictionary as td where td.cuitMoonParentDictionaryCode='10005'";	
		List<?> list = dictionaryService.find(hql);
		model.addAttribute("productTypelist", list);
		return "model/product/Index";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String productList(
			@RequestParam(required = false) Integer requestPageNo,
			@RequestParam(required = false) String queryString,
			@RequestParam(required = false) String parentId,
			Model model){
		if (queryString != null)
			queryString = toUTF8(queryString != null ? queryString : "");
		Page page = getProductInfoList(queryString, parentId, requestPageNo);
		List<?> list = page.getPageList();
		for(int i=0;i<list.size();i++)
		{			
			String productType = ((Productinfomation)(list.get(i))).getProductType();
			productType = analyzeCode(productType);
			((Productinfomation)(list.get(i))).setProductType(productType);
		}
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		model.addAttribute("parentId", parentId);
		model.addAttribute("productList", page);
		return "model/product/List";
	}
	
	@RequestMapping("delete")
	//@ResponseBody
	public String deleteProduct(String productNumber,String requsetPageNo) {
		Map<String, String> map = new HashMap<String, String>();
		String massage = "提交成功";
		String state = "success";
		try{
			productinfomationService.deleteByKey(productNumber);
		}
		catch (Exception e) {
			massage = e.getMessage();
			state = "fail";
		}
		map.put("state", state);
		map.put("result", massage);
		//return map;
		return "redirect:list?requsetPageNo="+requsetPageNo;
	}

	@RequestMapping("search")
	public String searchProduct(@RequestParam(required=false) String keyword ,Model model){
		Page dataPage = productinfomationService.listByOrderNum("and productType='100041' and productName like'%"+keyword+"%'",0, pageCountSize);
		model.addAttribute("productList", dataPage);
		
		return "model/product/List";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String elementAdd(Model model,String productNumber){
		List<?> dictionaryList = dictionaryService.find("from TbcuitmoonDictionary where cuitMoonParentDictionaryCode = '10005'");
		model.addAttribute("dictionaryList", dictionaryList);
		List<?> dicList = tbDicService
				.find("from TbcuitmoonDictionary as T where T.cuitMoonParentDictionaryCode = ?",
						10005L);
		model.addAttribute("dicType", dicList);
		model.addAttribute("pfType", pfService.loadAll());
		return "model/product/Add";
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> doProductAdd(String txtName, String selType, String txtCode, String txtDecription,@ModelAttribute("module") @Valid Productinfomation module){
		Map<String, String> map = new HashMap<String, String>();
		String massage = "提交成功";
		String state = "success";
		
		try{
			module.setProductNumber(UUID.randomUUID().toString().replaceAll("-", ""));
			module.setProductName(txtName);
			module.setProductType(selType);
			module.setRemark(txtCode);
			module.setProductDescription(txtDecription);
			module.setAddTime(new Date());
			productinfomationService.save(module);
			
		}
		catch (Exception e) {
			massage = e.getMessage();
			state = "fail";
		}
		
		map.put("state", state);
		map.put("result", massage);
		return map;
	}
	
	@RequestMapping(value="detail",method=RequestMethod.GET)
	public String productDetail(Model model,String productNumber){
		List<?> productdetail = productinfomationService.getDetail(productNumber);//根据要素类别来查
		
		model.addAttribute("productDetail", productdetail);
		return "model/product/Detail";
	}
	
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String productEdit(Model model,Model dModel,String productNumber){
		List<?> productdetail = productinfomationService.getDetail(productNumber);//根据要素类别来查
		model.addAttribute("productDetail", productdetail);
		
		List<?> dictionaryList = dictionaryService.find("from TbcuitmoonDictionary where cuitMoonParentDictionaryCode = '10005'");
		dModel.addAttribute("dictionaryList", dictionaryList);
		List<?> dicList = tbDicService
				.find("from TbcuitmoonDictionary as T where T.cuitMoonParentDictionaryCode = ?",
						10005L);
		model.addAttribute("dicType", dicList);
		model.addAttribute("pfType", pfService.loadAll());
		return "model/product/Edit";
	}
	
	@RequestMapping(value="edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> doProductEdit(String txtProduct,String txtName, String selType, String txtCode, String txtDecription,@ModelAttribute("module") @Valid Productinfomation module){
		Map<String, String> map = new HashMap<String, String>();
		String massage = "提交成功";
		String state = "success";
		
		try{
			module.setProductNumber(txtProduct);
			module.setProductName(txtName);
			module.setProductType(selType);
			module.setRemark(txtCode);
			module.setProductDescription(txtDecription);
			module.setAddTime(new Date());
			productinfomationService.update(module);
		}
		catch (Exception e) {
			massage = e.getMessage();
			state = "fail";
		}
		
		map.put("state", state);
		map.put("result", massage);
		return map;
	}
	

	//解析字典
	private String analyzeCode(String code){
		String name = "";
		code = code.trim();
		String hql = "from TbcuitmoonDictionary as dmodel where dmodel.cuitMoonDictionaryCode='" + code + "'";
		List<?> list = dictionaryService.find(hql);
		if(list.size()>0)
		{
			name = ((TbcuitmoonDictionary)(list.get(0))).getCuitMoonDictionaryName();
		}else{
			name = code;
		}	
		return name;
	}	
	
}
