package edu.cuit.module.model.web.controller;

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
import edu.cuit.module.model.entity.Approvemodelmanage;
import edu.cuit.module.model.service.ApprovemodelmanageService;
import edu.cuit.module.model.service.ElementmodelService;
import edu.cuit.module.model.service.ModelformulateService;
import edu.cuit.module.model.service.ProductinfomationService;
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;

@Controller
@RequestMapping("/model/grade")
public class ModelGradeControll extends BaseController {

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
	ApprovemodelmanageService gradeService;

	@RequestMapping(value = "modellist", method = RequestMethod.GET)
	public String modList(
			@RequestParam(required = false) Integer requsetPageNo, Model model) {
		if ((requsetPageNo == null) || (requsetPageNo <= 0)) {
			requsetPageNo = pageNo;
		}
		Page dataPage = modelService.listByOrderNum("", requsetPageNo,
				pageCountSize);
		List<?> modlist = dataPage.getPageList();
		String modelType;
		List<?> tmpList;
		for (int i = 0; i < modlist.size(); i++) {
			modelType = ((Modelformulate) modlist.get(i)).getModelType();
			tmpList = dictionaryService .find("from TbcuitmoonDictionary where cuitMoonDictionaryCode ='" + StringUtils.trimWhitespace(modelType) + "'");
			if (tmpList.size() > 0) {
				((Modelformulate) modlist.get(i))
						.setModelType(((TbcuitmoonDictionary) tmpList.get(0))
								.getCuitMoonDictionaryName());
			}
		}
		model.addAttribute("modelList", dataPage);
		return "model/grade/ModelList";
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String gradeList(
			@RequestParam(required = false) String productApproModelId,
			Model model) {
		List<?> modlist = gradeService.find("from Approvemodelmanage where productApproModelId='" + StringUtils.trimWhitespace(productApproModelId) + "'", null);
		String approveLevelName;
		List<?> tmpList;
		List<?> modeldetail = modelService.getDetail(productApproModelId);// 根据要素类别来查
		for (int i = 0; i < modlist.size(); i++) {
			approveLevelName = ((Approvemodelmanage) modlist.get(i))
					.getApproveLevelName();
			tmpList = dictionaryService
					.find("from TbcuitmoonDictionary where cuitMoonDictionaryCode ='"
							+ StringUtils.trimWhitespace(approveLevelName)
							+ "'");
			if (tmpList.size() > 0) {
				((Approvemodelmanage) modlist.get(i))
						.setApproveLevelName(((TbcuitmoonDictionary) tmpList
								.get(0)).getCuitMoonDictionaryName());
			}
			((Approvemodelmanage) modlist.get(i))
					.setRemark(((Modelformulate) modeldetail.get(0))
							.getModelName());
		}
		model.addAttribute("gradelList", modlist);
		return "model/grade/List";
	}

	@RequestMapping("delete")
	public String deleteGrade(String approveLevelId, String productApproModelId) {
		Map<String, String> map = new HashMap<String, String>();
		String massage = "提交成功";
		String state = "success";
		try {
			gradeService.deleteByKey(approveLevelId);
		} catch (Exception e) {
			massage = e.getMessage();
			state = "fail";
		}
		map.put("state", state);
		map.put("result", massage);
		// return map;
		return "redirect:list?productApproModelId=" + productApproModelId;
	}

	@RequestMapping("search")
	public String searchGrade(@RequestParam(required = false) String keyword,
			Model model) {
		Page dataPage = modelService.listByOrderNum("and modelName like'%"
				+ keyword + "%'", 0, pageCountSize);
		model.addAttribute("modelList", dataPage);

		return "model/grade/List";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String gradeAdd(Model dModel, Model mModel,
			String productApproModelId) {
		List<?> dictionaryList = dictionaryService
				.find("from TbcuitmoonDictionary where cuitMoonParentDictionaryCode = '10007'");
		dModel.addAttribute("dictionaryList", dictionaryList);

		List<?> modeldetail = modelService.getDetail(productApproModelId);// 根据要素类别来查
		mModel.addAttribute("modeldetail", modeldetail);

		return "model/grade/Add";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> doGradeAdd(String txtModel, String selLevel,
			String txtDecription,
			@ModelAttribute("module") @Valid Approvemodelmanage module) {
		Map<String, String> map = new HashMap<String, String>();
		String massage = "提交成功";
		String state = "success";

		try {
			module.setApproveLevelId(UUID.randomUUID().toString()
					.replaceAll("-", ""));
			module.setProductApproModelId(txtModel);
			module.setApproveLevelName(selLevel);
			module.setApproveLevelDescription(txtDecription);
			gradeService.save(module);

		} catch (Exception e) {
			massage = e.getMessage();
			state = "fail";
		}

		map.put("state", state);
		map.put("result", massage);
		return map;
	}

	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String gradeDetail(Model model, String productApproModelId) {
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
		return "model/grade/Detail";
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String gradeEdit(Model model, String approveLevelId,
			String productApproModelId) {

		List<?> gradeDetail = gradeService.find("from Approvemodelmanage where approveLevelId = '" + approveLevelId + "'");// 根据要素类别来查
		List<?> tmpList;
		String modelGrade = ((Approvemodelmanage) gradeDetail.get(0)).getApproveLevelName();
		tmpList = dictionaryService.find("from TbcuitmoonDictionary where cuitMoonDictionaryCode ='" + StringUtils.trimWhitespace(modelGrade) + "'");
		if (tmpList.size() > 0) {
			((Approvemodelmanage) gradeDetail.get(0)).setApproveLevelName(((TbcuitmoonDictionary) tmpList.get(0)).getCuitMoonDictionaryName());
		}
		List<?> modeldetail = modelService.getDetail(productApproModelId);// 根据要素类别来查
		if (modeldetail.size() > 0) {
			((Approvemodelmanage) gradeDetail.get(0)).setRemark(((Modelformulate) modeldetail.get(0)).getModelName());
		}
		model.addAttribute("gradeDetail", gradeDetail);

		return "model/grade/Edit";
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> doGradeEdit(String txtGrade, String txtDecription) {
		Map<String, String> map = new HashMap<String, String>();
		String massage = "提交成功";
		String state = "success";
	    List<?> gradeDetail = gradeService.find("from Approvemodelmanage where approveLevelId = '" + txtGrade + "'");// 根据要素类别来查
	    Approvemodelmanage module =  null;
	    if(gradeDetail.size() > 0){
	    	module = (Approvemodelmanage)gradeDetail.get(0);
	    }
		try {
				module.setApproveLevelDescription(txtDecription);
				gradeService.update(module);
		} catch (Exception e) {
			massage = e.getMessage();
			state = "fail";
		}

		map.put("state", state);
		map.put("result", massage);
		return map;
	}

}
