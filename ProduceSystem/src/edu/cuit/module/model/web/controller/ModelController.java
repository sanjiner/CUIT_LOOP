package edu.cuit.module.model.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
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

import edu.cuit.common.util.IdGenerator;
import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.model.dao.GrowthelementDao;
import edu.cuit.module.model.entity.Approvemodelmanage;
import edu.cuit.module.model.entity.Elementinfomation;
import edu.cuit.module.model.entity.Elementmodel;
import edu.cuit.module.model.entity.Growthelement;
import edu.cuit.module.model.entity.Growthperiod;
import edu.cuit.module.model.entity.Modelformulate;
import edu.cuit.module.model.entity.Productinfomation;
import edu.cuit.module.model.service.ElementmodelService;
import edu.cuit.module.model.service.GrowthelementService;
import edu.cuit.module.model.service.GrowthperiodService;
import edu.cuit.module.model.service.ModelformulateService;
import edu.cuit.module.model.service.ProductinfomationService;
import edu.cuit.module.model.service.ElementinfomationService;
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;

@Controller
@RequestMapping("/model/model")
public class ModelController extends BaseController{
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCountSize;
	@Autowired
	ModelformulateService modelService;
	@Autowired
	ProductinfomationService productinfomationService;
	@Autowired
	ElementmodelService elementmodelService;
	@Autowired
	TbcuitmoonDictionaryService dictionaryService;
	@Autowired
	ElementinfomationService elementinfomationService;
	@Autowired
	private GrowthperiodService growthperiodService;
	@Autowired
	private GrowthelementService growthelementService;
	
	private Page getModelFormulateInfoList(String queryString, 
			String parentCode, Integer pageIndex) {
		if (pageIndex == null)
			pageIndex = pageNo;
		String sqlString = "";
		if(queryString == null){
			if(parentCode.equals("0"))
			{
				sqlString = "from Modelformulate as mf where mf.modelType like '%10006%'";			
			}else{
				sqlString = "from Modelformulate as mf where mf.modelType='"+parentCode+"'";
			}
		}else{
			if(parentCode.equals("0"))
			{
				sqlString = "from Modelformulate as mf where mf.modelName like '%" + queryString + "%'";
			}else{
				sqlString = "from Modelformulate as mf where mf.modelName like '%" + queryString + "%' and mf.modelType='"+parentCode+"'";
			}
		}
		Page page = modelService.getPage(sqlString, pageIndex - 1,pageCountSize);
		return page;
	}
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String modelIndex(Model model){
		String hql = "from TbcuitmoonDictionary as td where td.cuitMoonParentDictionaryCode='10006'";	
		List<?> list = dictionaryService.find(hql);
		model.addAttribute("modelTypelist", list);
		return "model/model/Index";
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String modelList(
			@RequestParam(required = false) Integer requestPageNo,
			@RequestParam(required = false) String queryString,
			@RequestParam(required = false) String parentId,
			Model model) {
		if (queryString != null)
			queryString = toUTF8(queryString != null ? queryString : "");

		Page page = getModelFormulateInfoList(queryString, parentId, requestPageNo);
		List<?> list = page.getPageList();
		for(int i=0;i<list.size();i++)
		{
			String modelType = ((Modelformulate)(list.get(i))).getModelType();
			modelType = analyzeCode(modelType);
			((Modelformulate)(list.get(i))).setModelType(modelType);
		}
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		model.addAttribute("parentId", parentId);
		model.addAttribute("modelList", page);
		return "model/model/List";
	}

	@RequestMapping("delete")
	// @ResponseBody
	public String deleteModel(String productApproModelId, String requsetPageNo) {
		Map<String, String> map = new HashMap<String, String>();
		String massage = "提交成功";
		String state = "success";
		try {
			modelService.deleteByKey(productApproModelId);
			List<?> gperList = growthperiodService.find("from Growthperiod as m where m.produceApproModelId=?", productApproModelId);
			for(Object obj : gperList){
				Growthperiod gp = (Growthperiod)obj;
				growthelementService.bulkUpdate("delete from Growthelement as g where g.growthId=?",gp.getGrowthId());
			}
			growthperiodService.bulkUpdate("delete from Growthperiod as m where m.produceApproModelId=?", productApproModelId);
		} catch (Exception e) {
			massage = e.getMessage();
			state = "fail";
		}
		map.put("state", state);
		map.put("result", massage);
		// return map;
		return "redirect:list?requsetPageNo=" + requsetPageNo;
	}

	@RequestMapping("search")
	public String searchModel(@RequestParam(required = false) String keyword,
			Model model) {
		Page dataPage = modelService.listByOrderNum("and modelName like'%"
				+ keyword + "%'", 0, pageCountSize);
		model.addAttribute("modelList", dataPage);

		return "model/model/List";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String modelAdd(Model dModel, Model pModel, Model eModel,
			String elementNumber) {
		List<?> dictionaryList = dictionaryService
				.find("from TbcuitmoonDictionary where cuitMoonParentDictionaryCode = '10006'");
		// List<?> dictionaryList =
		// dictionaryService.dictionaryList("10006");//根据要素类别来查
		dModel.addAttribute("dictionaryList", dictionaryList);

		List<?> dicList = dictionaryService
				.find("from TbcuitmoonDictionary where cuitMoonParentDictionaryCode = '10005'");
		dModel.addAttribute("dicType", dicList);
		
		List<?> productList = productinfomationService
				.getListAll(" order by addTime desc");// 根据要素类别来查
		pModel.addAttribute("productList", productList);

		List<?> elementList = elementmodelService
				.getListAll(" order by addTime desc");// 根据要素类别来查
		eModel.addAttribute("elementList", elementList);

		return "model/model/Add";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> doModelAdd(String txtName, String selType,
			String selProduct, String txtDecription, String growthPeriodValue,
			@ModelAttribute("module") @Valid Modelformulate module) {
		Map<String, String> map = new HashMap<String, String>();
		String massage = "提交成功";
		String state = "success";

		try {
			module.setProductApproModelId(UUID.randomUUID().toString()
					.replaceAll("-", ""));
			module.setModelName(txtName);
			module.setModelType(selType);
			module.setProductNumber(selProduct);
			module.setModelDescription(txtDecription);
			
			/*存生育期*/
			if(hasLength(growthPeriodValue)){
				JSONArray jsonArray = new JSONArray(growthPeriodValue);
				for(int i = 0; i<jsonArray.length(); i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String[] growthPeroidNames = JSONObject.getNames(jsonObject);
					for(String name : growthPeroidNames){
						Growthperiod growthperiod = new Growthperiod();
						growthperiod.setGrowthName(name);
						growthperiod.setGrowthId(IdGenerator.genRamId().toUpperCase());
						growthperiod.setProduceApproModelId(module.getProductApproModelId());
						JSONObject jsonObject2 = jsonObject.getJSONObject(name);
						String[] growthElemNames = JSONObject.getNames(jsonObject2);
						for(String elemName : growthElemNames){
							Growthelement growthelement = new Growthelement();
							growthelement.setGrowthElemId(IdGenerator.genRamId().toUpperCase());
							growthelement.setGrowthId(growthperiod.getGrowthId());
							growthelement.setElementValue(jsonObject2.getString(elemName));
							growthelement.setElementNumber(elemName);
							growthelementService.save(growthelement);
						}
						growthperiodService.save(growthperiod);
					}
					
				}
			}
			modelService.save(module);
			/*//String[] elementList = txtElements.split("\\|");
			Elementinfomation eModule = new Elementinfomation();
			for (int i = 0; i < elementList.length; i++) {
				eModule = new Elementinfomation();
				eModule.setProductApproModelId(module.getProductApproModelId());
				if ((elementList[i] != null) && (!elementList[i].isEmpty())) {
					eModule.setCertificationNumber(UUID.randomUUID().toString()
							.replaceAll("-", ""));
					eModule.setElementNumber(elementList[0]);
					elementinfomationService.save(eModule);
				}
			}*/

		} catch (Exception e) {
			massage = "失败，请稍后再试";
			state = "fail";
		}

		map.put("state", state);
		map.put("result", massage);
		return map;
	}

	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String modelDetail(Model model,
			String productApproModelId) {
		List<?> modeldetail = modelService.getDetail(productApproModelId);// 根据要素类别来查
		List<?> tmpList;
		String modelType = ((Modelformulate) modeldetail.get(0)).getModelType();
		tmpList = dictionaryService
				.find("from TbcuitmoonDictionary where cuitMoonDictionaryCode ='"
						+ StringUtils.trimWhitespace(modelType) + "'");
		if (tmpList.size() > 0) {
			((Modelformulate) modeldetail.get(0))
					.setModelType(((TbcuitmoonDictionary) tmpList.get(0))
							.getCuitMoonDictionaryName());
		}
		String productID = ((Modelformulate) modeldetail.get(0))
				.getProductNumber();
		tmpList = productinfomationService
				.find("from Productinfomation where productNumber ='"
						+ StringUtils.trimWhitespace(productID) + "'");
		if (tmpList.size() > 0) {
			((Modelformulate) modeldetail.get(0))
					.setProductNumber(((Productinfomation) tmpList.get(0))
							.getProductName());
		}
		model.addAttribute("modelDetail", modeldetail);
		/*List<?> elementinfoList = elementinfomationService
				.find("from Elementinfomation where productApproModelId='"
						+ productApproModelId + "' ");// 根据要素类别来查
*/	/*///	String strElementinfo = "";
		for (int i = 0; i < elementinfoList.size(); i++) {
			List<?> elementdetail = elementmodelService
					.getDetail(((Elementinfomation) elementinfoList.get(i))
							.getElementNumber());// 根据要素类别来查
			if (elementdetail.size() > 0) {
				strElementinfo += ((Elementmodel) elementdetail.get(0)).getElementName();
				if (i < elementinfoList.size() - 1) {
					strElementinfo += "、";
				}
			}
		}
		if(strElementinfo.isEmpty()){
			strElementinfo="无";
		}*/
		/*eiModel.addAttribute("strElementinfo", strElementinfo);*/
		List<?> elementList = elementmodelService
				.getListAll(" order by addTime desc");// 根据要素类别来查
		model.addAttribute("elementList", elementList);
		List<?> growthPeriodList = growthperiodService.find("from Growthperiod as m where m.produceApproModelId=?", productApproModelId);
		
		for(Object obj : growthPeriodList){
			Growthperiod  gp = (Growthperiod)obj;
			List<?> elemList = growthelementService.find("from Growthelement as g where g.growthId=?",gp.getGrowthId());
			gp.setGrowthElemList(elemList);
		}
		model.addAttribute("growthPeriodList", growthPeriodList);
		return "model/model/Detail";
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String modelEdit(Model model, String productApproModelId) {

		List<?> modelDetail = modelService.getDetail(productApproModelId);// 根据要素类别来查
		model.addAttribute("modelDetail", modelDetail);

		List<?> dictionaryList = dictionaryService
				.find("from TbcuitmoonDictionary where cuitMoonParentDictionaryCode = '10006'");
		model.addAttribute("dictionaryList", dictionaryList);

		List<?> productList = productinfomationService
				.getListAll(" order by addTime desc");// 根据要素类别来查
		model.addAttribute("productList", productList);

		List<?> elementList = elementmodelService
				.getListAll(" order by addTime desc");// 根据要素类别来查
		model.addAttribute("elementList", elementList);
		
		List<?> growthPeriodList = growthperiodService.find("from Growthperiod as m where m.produceApproModelId=?", productApproModelId);
		
		for(Object obj : growthPeriodList){
			Growthperiod  gp = (Growthperiod)obj;
			List<?> elemList = growthelementService.find("from Growthelement as g where g.growthId=?",gp.getGrowthId());
			gp.setGrowthElemList(elemList);
		}
		/*
		
		List<?> elementinfoList = elementinfomationService
				.find("from Elementinfomation where productApproModelId='"
						+ productApproModelId + "' ");// 根据要素类别来查
		String strElementinfo = "";
		for (int i = 0; i < elementinfoList.size(); i++) {
			strElementinfo += ((Elementinfomation) elementinfoList.get(i))
					.getElementNumber() + "|";
		}*/
		model.addAttribute("growthPeriodList", growthPeriodList);

		return "model/model/Edit";
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> doModelEdit(String txtModel, String txtName,String growthPeriodValue,
			String selType, String selProduct, String txtDecription,
			String txtElements,
			@ModelAttribute("module") @Valid Modelformulate module) {
		Map<String, String> map = new HashMap<String, String>();
		String massage = "提交成功";
		String state = "success";

		try {
			module.setProductApproModelId(txtModel);
			module.setModelName(txtName);
			module.setModelType(selType);
			module.setProductNumber(selProduct);
			module.setModelDescription(txtDecription);
			modelService.update(module);
			List<?> gperList = growthperiodService.find("from Growthperiod as m where m.produceApproModelId=?", txtModel);
			for(Object obj : gperList){
				Growthperiod gp = (Growthperiod)obj;
				growthelementService.bulkUpdate("delete from Growthelement as g where g.growthId=?",gp.getGrowthId());
			}
			growthperiodService.bulkUpdate("delete from Growthperiod as m where m.produceApproModelId=?", txtModel);
			if(hasLength(growthPeriodValue)){
				JSONArray jsonArray = new JSONArray(growthPeriodValue);
				for(int i = 0; i<jsonArray.length(); i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String[] growthPeroidNames = JSONObject.getNames(jsonObject);
					for(String name : growthPeroidNames){
						Growthperiod growthperiod = new Growthperiod();
						growthperiod.setGrowthName(name);
						growthperiod.setGrowthId(IdGenerator.genRamId().toUpperCase());
						growthperiod.setProduceApproModelId(module.getProductApproModelId());
						JSONObject jsonObject2 = jsonObject.getJSONObject(name);
						String[] growthElemNames = JSONObject.getNames(jsonObject2);
						
						for(String elemName : growthElemNames){
							Growthelement growthelement = new Growthelement();
							growthelement.setGrowthElemId(IdGenerator.genRamId().toUpperCase());
							growthelement.setGrowthId(growthperiod.getGrowthId());
							growthelement.setElementValue(jsonObject2.getString(elemName));
							growthelement.setElementNumber(elemName);
							growthelementService.save(growthelement);
						}
						growthperiodService.save(growthperiod);
					}
					
				}
			}
			
			
			
			/*String[] elementList = txtElements.split("\\|");
			Elementinfomation eModule = new Elementinfomation();
			for (int i = 0; i < elementList.length; i++) {
				eModule = new Elementinfomation();
				eModule.setProductApproModelId(module.getProductApproModelId());
				if ((elementList[i] != null) && (!elementList[i].isEmpty())) {
					eModule.setCertificationNumber(UUID.randomUUID().toString()
							.replaceAll("-", ""));
					eModule.setElementNumber(elementList[i]);
					elementinfomationService.save(eModule);
				}
			}*/
		} catch (Exception e) {
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
		String hql = "from TbcuitmoonDictionary as dmodel where dmodel.cuitMoonDictionaryCode='" + code + "'";
		name = ((TbcuitmoonDictionary)dictionaryService.find(hql).get(0)).getCuitMoonDictionaryName();
		return name;
	}	

}
