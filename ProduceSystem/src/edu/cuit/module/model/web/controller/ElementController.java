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
import edu.cuit.module.model.entity.Elementmodel;
import edu.cuit.module.model.service.ElementmodelService;
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;

@Controller
@RequestMapping("/model/element")
public class ElementController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCountSize;
	@Autowired
	ElementmodelService elementmodelService;
	@Autowired
	TbcuitmoonDictionaryService dictionaryService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String elementList(@RequestParam(required=false) Integer requsetPageNo ,Model model){
		if((requsetPageNo == null) || (requsetPageNo <= 0)){
			requsetPageNo = pageNo;
		}
		Page dataPage = elementmodelService.listByOrderNum("and elementType='100041'",requsetPageNo, pageCountSize);
		List<?> elelist = dataPage.getPageList();
		String elementType;
		List<?> tmpList;
		for (int i = 0; i < elelist.size(); i++) {
			elementType = ((Elementmodel) elelist.get(i)) .getElementType();
			tmpList = dictionaryService .find("from TbcuitmoonDictionary where cuitMoonDictionaryCode ='" + StringUtils.trimWhitespace(elementType) + "'");
			if (tmpList.size() > 0) {
				((Elementmodel) elelist.get(i)).setElementType(((TbcuitmoonDictionary) tmpList.get(0)).getCuitMoonDictionaryName());
			}
		}
		model.addAttribute("elementList", dataPage);
		return "model/element/List";
	}
	
	@RequestMapping("delete")
	//@ResponseBody
	public String deleteEelement(String elementNumber,String requsetPageNo) {
		Map<String, String> map = new HashMap<String, String>();
		String massage = "提交成功";
		String state = "success";
		try{
			elementmodelService.deleteByKey(elementNumber);
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
	public String searchEelement(@RequestParam(required=false) String keyword ,Model model){
		Page dataPage = elementmodelService.listByOrderNum("and elementType='100041' and elementName like'%"+keyword+"%'",0, pageCountSize);
		model.addAttribute("elementList", dataPage);
		
		return "model/element/List";
	}
	
	@RequestMapping(value="add",method=RequestMethod.GET)
	public String elementAdd(Model model,String elementNumber){
		List<?> dictionaryList = dictionaryService.find("from TbcuitmoonDictionary where cuitMoonParentDictionaryCode = '10004'");
		//List<?> dictionaryList = dictionaryService.dictionaryList("10006");//根据要素类别来查
		model.addAttribute("dictionaryList", dictionaryList);
		return "model/element/Add";
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> doEelementAdd(String txtName, String selType, String txtUnit, String txtIndicators,String txtDecription,@ModelAttribute("module") @Valid Elementmodel module){
		Map<String, String> map = new HashMap<String, String>();
		String massage = "提交成功";
		String state = "success";
		
		try{
			module.setElementNumber(UUID.randomUUID().toString().replaceAll("-", ""));
			module.setElementName(txtName);
			module.setElementType(selType);
			module.setUnit(txtUnit);
			module.setRemark(txtIndicators);
			module.setElementDecription(txtDecription);
			//module.setAddTime(new Date());
			elementmodelService.save(module);
			
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
	public String elementDetail(Model model,String elementNumber){
		List<?> elementdetail = elementmodelService.getDetail(elementNumber);//根据要素类别来查
		List<?> tmpList;
		String elementType = ((Elementmodel) elementdetail.get(0)) .getElementType();
		tmpList = dictionaryService .find("from TbcuitmoonDictionary where cuitMoonDictionaryCode ='" + StringUtils.trimWhitespace(elementType) + "'");
		if (tmpList.size() > 0) {
			((Elementmodel) elementdetail.get(0)).setElementType(((TbcuitmoonDictionary) tmpList.get(0)).getCuitMoonDictionaryName());
		}
		model.addAttribute("elementDetail", elementdetail);
		return "model/element/Detail";
	}
	
	@RequestMapping(value="edit",method=RequestMethod.GET)
	public String elementEdit(Model model,Model dModel,String elementNumber){
		List<?> elementdetail = elementmodelService.getDetail(elementNumber);//根据要素类别来查
		model.addAttribute("elementDetail", elementdetail);
		List<?> dictionaryList = dictionaryService.find("from TbcuitmoonDictionary where cuitMoonParentDictionaryCode = '10004'");
		//List<?> dictionaryList = dictionaryService.dictionaryList("10006");//根据要素类别来查
		dModel.addAttribute("dictionaryList", dictionaryList);
		return "model/element/Edit";
	}
	
	@RequestMapping(value="edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> doEelementEdit(String txtElement,String txtName, String selType, String txtUnit, String txtIndicators,String txtDecription,@ModelAttribute("module") @Valid Elementmodel module){
		Map<String, String> map = new HashMap<String, String>();
		String massage = "提交成功";
		String state = "success";
		
		try{
			module.setElementNumber(txtElement);
			module.setElementName(txtName);
			module.setElementType(selType);
			module.setUnit(txtUnit);
			module.setRemark(txtIndicators);
			module.setElementDecription(txtDecription);
			module.setAddTime(new Date());
			elementmodelService.update(module);
			
		}
		catch (Exception e) {
			massage = e.getMessage();
			state = "fail";
		}
		
		map.put("state", state);
		map.put("result", massage);
		return map;
	}
	
}
