package edu.cuit.module.infom.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.cuit.common.enums.DictionaryEnum;
import edu.cuit.common.util.Constant;
import edu.cuit.common.util.DateFormat;
import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.infom.entity.Businessmanagement;
import edu.cuit.module.infom.entity.Businessqualification;
import edu.cuit.module.infom.service.BusinessmanagementService;
import edu.cuit.module.infom.service.BusinessqualificationService;
import edu.cuit.module.sys.entity.TbcuitmoonArea;
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;
import edu.cuit.module.sys.entity.TbcuitmoonUser;
import edu.cuit.module.sys.entity.TbcuitmoonUserrole;
import edu.cuit.module.sys.enums.JurCodeEnum;
import edu.cuit.module.sys.service.TbcuitmoonAreaService;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;
import edu.cuit.module.sys.service.TbcuitmoonUserService;
import edu.cuit.module.sys.service.TbcuitmoonUserroleService;

import java.text.ParseException;
import java.text.SimpleDateFormat;  

@Controller
@RequestMapping("auth_center_info/business_info")
public class BusinessManagementController extends BaseController {
	private int pageNo;
	private int pageCountSize;
	@Value("#{settings['file.path']}")
	private String fileDirectory;
	
	@Autowired
	BusinessmanagementService businessManagementService;
	@Autowired
	TbcuitmoonAreaService areaService;
	@Autowired
	TbcuitmoonDictionaryService dictionaryService;
	@Autowired
	TbcuitmoonUserService userService;
	@Autowired
	TbcuitmoonUserroleService userRoleService;
	@Autowired
	BusinessqualificationService certifyService;

	public BusinessManagementController() {
		pageCountSize = 20; // 页面大小
		pageNo = 1;
	}

	private Page getBusinessInfoList(String queryString, Integer pageIndex, HttpSession session) {
		List<?> businessInfoList;// 存放商家信息的list
		TbcuitmoonUser user_model = (TbcuitmoonUser)session.getAttribute(Constant.LOGINUSER);///当前登录的用户
		List<?> userroleList = userRoleService.find(" from TbcuitmoonUserrole where CuitMoon_UserID = '"+user_model.getCuitMoonUserId()+"'");
		boolean isShopRole = false;
		if(userroleList.size() > 0)
		{
			isShopRole = DictionaryEnum.SHOPROLEID.toString().equals(((TbcuitmoonUserrole)userroleList.get(0)).getCuitMoonRoleId()) ? true : false;
		}
		if (pageIndex == null)
			pageIndex = pageNo;
		String sqlString = "";
		if(isShopRole)
		{
			sqlString = "from Businessmanagement where UserName = '"+user_model.getCuitMoonUserName()+"' order by addTime DESC";
		}
		else
		{
			sqlString = "from Businessmanagement"
					+ (queryString == null ? "" : " where campanyName like '%"
							+ queryString + "%'") + " order by addTime DESC";
		}
		Page page = businessManagementService.getPage(sqlString, pageIndex - 1,
				pageCountSize);
		businessInfoList = page.getPageList();
		String areaID, companyType;
		String[] areaArray;
		List<?> tmpList;
		for (int i = 0; i < businessInfoList.size(); i++) {
			areaID = ((Businessmanagement) businessInfoList.get(i))
					.getBusinessArea();
			companyType = ((Businessmanagement) businessInfoList.get(i))
					.getCompanyType();
			areaArray = areaID.split("\\|");
			areaID = areaArray[areaArray.length - 1];
			// ///根据areaID获取区域的中文名称
			tmpList = areaService
					.find("from TbcuitmoonArea where cuitMoonAreaCode like '%"
							+ areaID + "%'");
			if (tmpList.size() > 0) {
				((Businessmanagement) businessInfoList.get(i))
						.setBusinessArea(((TbcuitmoonArea) tmpList.get(0))
								.getCuitMoonAreaName());
			}
			// ///根据字典的code查出公司的类型
			tmpList = dictionaryService
					.find("from TbcuitmoonDictionary where cuitMoonDictionaryCode like '%"
							+ StringUtils.trimWhitespace(companyType) + "%'");
			if (tmpList.size() > 0) {
				((Businessmanagement) businessInfoList.get(i))
						.setCompanyType(((TbcuitmoonDictionary) tmpList.get(0))
								.getCuitMoonDictionaryName());
			}
		}
		return page;
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String businessInfoList(
			@RequestParam(required = false) Integer requestPageNo,
			@RequestParam(required = false) String queryString, Model model, HttpSession session) throws UnsupportedEncodingException {
		if(queryString != null)
			queryString = toUTF8(queryString != null ? queryString : "");
		Page page = getBusinessInfoList(queryString, requestPageNo, session);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("applyPage", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		return "auth_center_info/business_info/BusinessInfoList";
	}

	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String businessInfoList(String queryString,
			@RequestParam(required = false) Integer requestPageNo, Model model, HttpSession session)
			throws UnsupportedEncodingException {
		Page page = getBusinessInfoList(queryString, requestPageNo, session);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("applyPage", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		return "auth_center_info/business_info/BusinessInfoList";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String Add(Model model) {
		checkPermissions(JurCodeEnum.ADDBUSINESS.value());
		List<?> tmpList;
		// 单位性质
		tmpList = dictionaryService
				.find("from TbcuitmoonDictionary where CuitMoon_ParentDictionaryCode ='"
						+ DictionaryEnum.COMPANYTYPE.value() + "'");
		model.addAttribute("companyTypeList", tmpList);
		// 企业类型
		tmpList = dictionaryService
				.find("from TbcuitmoonDictionary where CuitMoon_ParentDictionaryCode ='"
						+ DictionaryEnum.ENTERPRISETYPE.value() + "'");
		model.addAttribute("enterpriseTypeList", tmpList);
		// 商家类型
		tmpList = dictionaryService
				.find("from TbcuitmoonDictionary where CuitMoon_ParentDictionaryCode ='"
						+ DictionaryEnum.SHOPTYPE.value() + "'");
		model.addAttribute("shopTypeList", tmpList);
		// 获取一级省的信息
		tmpList = areaService
				.find("from TbcuitmoonArea where CuitMoon_ParentAreaCode = '0'");
		model.addAttribute("provinceList", tmpList);
		return "auth_center_info/business_info/BusinessInfoAdd";
	}
	

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody //添加
	public String Add(String userName, String companyType, String area, String companyName, String legalPerson, String address
			, String cantactPerson, String enterpriseType, String shopType, String legalPresentCode, String tel, 
			String mobilePhone, String fax, String mailBox, String logo, String certificateName, String awardDepart, String certifyImage
			,String getTime, String password, String json, String remarks) throws ParseException {
		
		List<?> userList = userService.find(" from TbcuitmoonUser where CuitMoon_UserName = '"+userName+"'");
		if(userList.size() > 0)
		{
			return "E";
		}
		else
		{
			////保存商家信息
			Businessmanagement model = new Businessmanagement();
			String primaryKey = UUID.randomUUID().toString().replace("-", "");
			model.setCampanyNo(primaryKey);
			model.setCampanyType(companyType);
			model.setUserName(userName);
			model.setBusinessArea(area);		
			model.setCampanyName(companyName);
			model.setLegalPerson(legalPerson);
			model.setAddress(address);
			model.setCantactPerson(cantactPerson);
			model.setCompanyType(enterpriseType);
			model.setBusinessType(shopType);
			model.setLegalPresentCode(legalPresentCode);
			model.setTel(tel);
			model.setMobilePhone(mobilePhone);
			model.setFax(fax);
			model.setMailBox(mailBox);
			model.setAddTime(new Date());
			model.setRemark(remarks);
			if(logo.length() > 0)
				model.setLogo(imageShowPath+ "/"+ logo);
			businessManagementService.save(model);
			
			////保存证书信息
//			Certificate model_certificate = new Certificate();
//			String certificate_primaryKey = UUID.randomUUID().toString().replace("-", "");
//			model_certificate.setCertificateId(certificate_primaryKey);
//			model_certificate.setCertificateName(certificateName);
//			model_certificate.setAwardDepart(awardDepart);
//			model_certificate.setPictureUrl(certifyImage);
//			model_certificate.setGetTime(getTime);
//			model_certificate.setCampanyNo(primaryKey);
//			certificateService.save(model_certificate);
			
			////添加到用户表
			TbcuitmoonUser user_model = new TbcuitmoonUser();
			user_model.setCuitMoonUserId(primaryKey);
			user_model.setCuitMoonUserName(userName);
			String md5Password = new SimpleHash("md5", password, null, 1).toHex().toUpperCase();
			user_model.setCuitMoonUserPassWord(md5Password);
			user_model.setCuitMoonAreaId("0");
			user_model.setCuitMoonDepartmentId("0");
			user_model.setCuitMoonUserStatus((long)1);
			user_model.setCuitMoonUserRemarks(companyName);
			userService.save(user_model);
			///添加到用户权限表
			TbcuitmoonUserrole userRole_model = new TbcuitmoonUserrole();
			String roleID = DictionaryEnum.SHOPROLEID.toString();
			userRole_model.setCuitMoonRoleId(roleID);
			userRole_model.setCuitMoonUserId(primaryKey);
			primaryKey = UUID.randomUUID().toString().replace("-", "");
			userRole_model.setCuitMoonUserRoleId(primaryKey);
			userRoleService.save(userRole_model);
			
			JSONObject  dataJson = new JSONObject(json);
			JSONArray dataArray = dataJson.getJSONArray("DataArray");
			for(int i = 0; i < dataArray.length(); i++)
			{
				JSONObject info=dataArray.getJSONObject(i);
				////添加商家的资质证书
				Businessqualification certify_model = new Businessqualification();
				primaryKey = UUID.randomUUID().toString().replace("-", "");
				certify_model.setBusinessId(primaryKey);
				certify_model.setCompanyNo(model.getCampanyNo());
				certify_model.setAwardTime(info.getString("getTime"));
				certify_model.setPublishUnit(info.getString("awardDepart"));
				certify_model.setName(info.getString("certificateName"));
				if(info.getString("certifyImage").length() > 0)
					certify_model.setPicUrl(imageShowPath+ "/"+ info.getString("certifyImage"));
				certifyService.save(certify_model);
			}
			return "T";
		}
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String businessInfoAdd(@RequestParam String id, Model model,HttpServletRequest request) {
		checkPermissions(JurCodeEnum.EDITBUSINESS.value());
		model.addAttribute("primaryKey", id);
		Businessmanagement model_business = businessManagementService.get(id);
		model.addAttribute("companyType", model_business.getCampanyType());
		model.addAttribute("userName", model_business.getUserName());		
		model.addAttribute("companyName", model_business.getCampanyName());
		model.addAttribute("legalPerson", model_business.getLegalPerson());
		model.addAttribute("address", model_business.getAddress());
		model.addAttribute("cantactPerson", model_business.getCantactPerson());
		model.addAttribute("enterpriseType", model_business.getCompanyType());
		model.addAttribute("shopType", model_business.getBusinessType());
		model.addAttribute("legalPresentCode", model_business.getLegalPresentCode());
		model.addAttribute("tel", model_business.getTel());
		model.addAttribute("mobilePhone", model_business.getMobilePhone());
		model.addAttribute("fax", model_business.getFax());
		model.addAttribute("mailBox", model_business.getMailBox());
		model.addAttribute("remarks", model_business.getRemark());
		model.addAttribute("logoName", model_business.getLogo());
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path;
		String imgPath =basePath + "/"+model_business.getLogo();
		model_business.setLogo(imgPath);
		model.addAttribute("businessmanagement", model_business);
		model.addAttribute("logo", model_business.getLogo());
		
		List<?> tmpList;
		// 单位性质
		tmpList = dictionaryService
				.find("from TbcuitmoonDictionary where CuitMoon_ParentDictionaryCode ='"
						+ DictionaryEnum.COMPANYTYPE.value() + "'");
		model.addAttribute("companyTypeList", tmpList);
		// 企业类型
		tmpList = dictionaryService
				.find("from TbcuitmoonDictionary where CuitMoon_ParentDictionaryCode ='"
						+ DictionaryEnum.ENTERPRISETYPE.value() + "'");
		model.addAttribute("enterpriseTypeList", tmpList);
		// 商家类型
		tmpList = dictionaryService
				.find("from TbcuitmoonDictionary where CuitMoon_ParentDictionaryCode ='"
						+ DictionaryEnum.SHOPTYPE.value() + "'");
		model.addAttribute("shopTypeList", tmpList);
		// 获取一级省的信息
		tmpList = areaService
				.find("from TbcuitmoonArea where CuitMoon_ParentAreaCode = '0'");
		model.addAttribute("provinceList", tmpList);
		String[] areaCodes = model_business.getBusinessArea().split("\\|");
		if(areaCodes.length > 0){
			model.addAttribute("province", areaCodes[0]);
			//获取被选中的市级数据
			tmpList = areaService
					.find("from TbcuitmoonArea where CuitMoon_ParentAreaCode = '"+areaCodes[0]+"'");
			model.addAttribute("cityList", tmpList);
		}
		if(areaCodes.length > 1){
			model.addAttribute("city", areaCodes[1]);
			tmpList = areaService
					.find("from TbcuitmoonArea where CuitMoon_ParentAreaCode = '"+areaCodes[1]+"'");
			model.addAttribute("countyList", tmpList);
		}
		if(areaCodes.length > 2)
			model.addAttribute("county", areaCodes[2]);
		
		////获取证书信息
		List<?> certifyList = certifyService.find(" from Businessqualification where CompanyNo = '"+ model_business.getCampanyNo()+"'");
		model.addAttribute("certificateList", certifyList);
//		if(certificateList.size() > 0)
//		{
//			model.addAttribute("certificateName", ((Certificate)certificateList.get(0)).getCertificateName());
//			model.addAttribute("awardDepart", ((Certificate)certificateList.get(0)).getAwardDepart());
//			model.addAttribute("certifyImage", ((Certificate)certificateList.get(0)).getPictureUrl());
//			model.addAttribute("getTime", ((Certificate)certificateList.get(0)).getGetTime());
//			certificateService.save((Certificate)certificateList.get(0));
//		}
		return "auth_center_info/business_info/BusinessInfoEdit";
	}

	@RequestMapping(value = "dictionary", method = RequestMethod.GET)
	@ResponseBody
	// 根据区域的，获取其子级
	public Map<String, List<?>> getSubListByAreaCode(String code) {
		List<?> areaList = areaService
				.find("from TbcuitmoonArea where CuitMoon_ParentAreaCode = '"
						+ code + "'");
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		map.put("list", areaList);
		return map;
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody //更新
	public String update(String id, String userName, String companyType, String area, String companyName, String legalPerson, String address
			, String cantactPerson, String enterpriseType, String shopType, String legalPresentCode, String tel, String mobilePhone, String fax, String mailBox
			, String logo, String certificateName, String awardDepart, String certifyImage,String getTime, String json,String remarks) throws JSONException, ParseException {
		Businessmanagement model = businessManagementService.get(id);
		model.setCampanyType(companyType);
		model.setUserName(userName);
		model.setBusinessArea(area);
		model.setCampanyName(companyName);
		model.setLegalPerson(legalPerson);
		model.setAddress(address);
		model.setCantactPerson(cantactPerson);
		model.setCompanyType(enterpriseType);
		model.setBusinessType(shopType);
		model.setLegalPresentCode(legalPresentCode);
		model.setTel(tel);
		model.setMobilePhone(mobilePhone);
		model.setFax(fax);
		model.setMailBox(mailBox);
		model.setRemark(remarks);
		model.setAddTime(new Date());
		if(logo.length() > 0)
			model.setLogo(imageShowPath+ "/"+ logo);
		businessManagementService.update(model);
		
		JSONObject  dataJson = new JSONObject(json);
		JSONArray dataArray = dataJson.getJSONArray("DataArray");
		String primaryKey = "";
		for(int i = 0; i < dataArray.length(); i++)
		{
			JSONObject info=dataArray.getJSONObject(i);
			////添加商家的资质证书
			Businessqualification certify_model = new Businessqualification();
			primaryKey = UUID.randomUUID().toString().replace("-", "");
			certify_model.setBusinessId(primaryKey);
			certify_model.setCompanyNo(model.getCampanyNo());
			certify_model.setAwardTime(info.getString("getTime"));
			certify_model.setPublishUnit(info.getString("awardDepart"));
			certify_model.setName(info.getString("certificateName"));
			if(info.getString("certifyImage").length() > 0)
				certify_model.setPicUrl(imageShowPath+ "/"+ info.getString("certifyImage"));
			certifyService.save(certify_model);
		}
		
		////保存证书信息
//		List<?> certificateList = certificateService.find(" from Certificate where CampanyNo = '"+ model.getCampanyNo() +"'");
//		if(certificateList.size() > 0)
//		{
//			((Certificate)certificateList.get(0)).setCertificateName(certificateName);
//			((Certificate)certificateList.get(0)).setAwardDepart(awardDepart);
//			((Certificate)certificateList.get(0)).setPictureUrl(certifyImage);
//			((Certificate)certificateList.get(0)).setGetTime(getTime);
//			certificateService.update((Certificate)certificateList.get(0));
//		}
		return "T";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	@ResponseBody //删除
	public String delete(String primaryKey) {
		checkPermissions(JurCodeEnum.DELTEBUSINESS.value());
		
		Businessmanagement model = businessManagementService.get(primaryKey);
		List<?> userList = userService.find(" from TbcuitmoonUser where CuitMoon_UserName = '"+model.getUserName()+"'");
		for(int i = 0; i < userList.size(); i++)
		{
			userService.deleteByKey(((TbcuitmoonUser)userList.get(i)).getCuitMoonUserId());
		}
		List<?> certifyList = certifyService.find(" from Businessqualification where CompanyNo = '"+model.getCampanyNo()+"'");
		for(int i = 0; i < certifyList.size(); i++)
		{
			certifyService.deleteByKey(((Businessqualification)certifyList.get(i)).getBusinessId());
		}
		businessManagementService.deleteByKey(primaryKey);
		return "T";
	}
	
	@RequestMapping(value = "details", method = RequestMethod.GET)
	public String details(String id, Model model,HttpServletRequest request) {
		Businessmanagement model_business = businessManagementService.get(id);
		model.addAttribute("companyType", getNameByDictionaryCode(model_business.getCampanyType()));
		model.addAttribute("userName", model_business.getUserName());		
		model.addAttribute("companyName", model_business.getCampanyName());
		model.addAttribute("legalPerson", model_business.getLegalPerson());
		model.addAttribute("address", model_business.getAddress());
		model.addAttribute("cantactPerson", model_business.getCantactPerson());
		model.addAttribute("enterpriseType", getNameByDictionaryCode(model_business.getCompanyType()));
		model.addAttribute("shopType", getNameByDictionaryCode(model_business.getBusinessType()));
		model.addAttribute("legalPresentCode", model_business.getLegalPresentCode());
		model.addAttribute("tel", model_business.getTel());
		model.addAttribute("mobilePhone", model_business.getMobilePhone());
		model.addAttribute("fax", model_business.getFax());
		model.addAttribute("mailBox", model_business.getMailBox());		
		model.addAttribute("businessmanagement", model_business);
		model.addAttribute("remarks", model_business.getRemark());
	////获取证书信息
			List<?> certifyList = certifyService.find(" from Businessqualification where CompanyNo = '"+ model_business.getCampanyNo()+"'");
			model.addAttribute("certificateList", certifyList);
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path;
		String imgPath =basePath + "/"+model_business.getLogo();
		model_business.setLogo(imgPath);
		model.addAttribute("logo", model_business.getLogo());
		String[] areaCodes = model_business.getBusinessArea().split("\\|");
		if(areaCodes.length > 0){
			model.addAttribute("province", getNameByAreaCode(areaCodes[0]));
		}
		if(areaCodes.length > 1){
			model.addAttribute("city", getNameByAreaCode(areaCodes[1]));
		}
		if(areaCodes.length > 2)
			model.addAttribute("county", getNameByAreaCode(areaCodes[2]));
		return "auth_center_info/business_info/BusinessInfoDetails";
	}
	
	private String getNameByDictionaryCode(String code){
		List<?> tmpList = dictionaryService
				.find("from TbcuitmoonDictionary where cuitMoonDictionaryCode like '%"
						+ StringUtils.trimWhitespace(code) + "%'");
		if (tmpList.size() > 0) {
			return ((TbcuitmoonDictionary) tmpList.get(0))
			.getCuitMoonDictionaryName();
		}
		else
			return "";
	}
	
	private String getNameByAreaCode(String code){
		List<?> tmpList = areaService
				.find("from TbcuitmoonArea where cuitMoonAreaCode like '%"
						+ StringUtils.trimWhitespace(code) + "%'");
		if (tmpList.size() > 0) {
			return ((TbcuitmoonArea) tmpList.get(0))
			.getCuitMoonAreaName();
		}
		else
			return "";
	}
	
	
	@RequestMapping(value = "deleteCertify", method = RequestMethod.GET)
	@ResponseBody //删除证书
	public String deleteCertify(String id) {
		certifyService.deleteByKey(id);
		return "T";
	}
	
	@RequestMapping(value = "file", method = RequestMethod.POST)
	@ResponseBody
	public String file( HttpServletRequest request, HttpServletResponse response) throws IOException {
		return uploadFile(request, fileDirectory);
	}
}
