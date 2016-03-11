package edu.cuit.module.authc.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.cuit.common.util.Constant;
import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.auchc.entity.Apply;
import edu.cuit.module.authc.service.ApplyService;
import edu.cuit.module.infom.entity.Businessmanagement;
import edu.cuit.module.infom.entity.Businessqualification;
import edu.cuit.module.infom.service.BusinessmanagementService;
import edu.cuit.module.infom.service.BusinessqualificationService;
import edu.cuit.module.model.entity.Productinfomation;
import edu.cuit.module.model.service.ProductinfomationService;
import edu.cuit.module.origin.entity.Certificate;
import edu.cuit.module.origin.service.CertificateService;
import edu.cuit.module.sys.entity.TbcuitmoonArea;
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;
import edu.cuit.module.sys.entity.TbcuitmoonRole;
import edu.cuit.module.sys.entity.TbcuitmoonUser;
import edu.cuit.module.sys.service.TbcuitmoonAreaService;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;
import edu.cuit.module.sys.service.TbcuitmoonUserService;

@Controller
@RequestMapping("authc")
public class AuthcApplyController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;
	@Value("#{settings['image.showpath']}")
	public String imageShowPath;

	@Autowired
	private ApplyService applyService;
	@Autowired
	TbcuitmoonUserService TbcuitmoonUserService;
	@Autowired
	BusinessmanagementService bmService;
	@Autowired
	BusinessqualificationService bqService;
	@Autowired
	TbcuitmoonAreaService tbAreaService;
	@Autowired
	TbcuitmoonDictionaryService tbDicService;
	@Autowired
	ProductinfomationService pfService;
	@Autowired
	BusinessmanagementService bsService;
	@Autowired
	CertificateService certSrevice;

	private final int INIT_STATUS = 100020;

	/**
	 * 显示认证
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("showApply")
	public String apply(@RequestParam(required = false) Integer requsetPageNo,
			Model model, HttpSession session) {
		Page page;
		if (requsetPageNo == null) {
			requsetPageNo = pageNo;
		}
		log.debug("pageNO {}", requsetPageNo);
		/* hibernate 索引是从0开始的，所有要-1 */
		TbcuitmoonUser user = getLoginUser(session);
		String role = user == null ? "" : ((TbcuitmoonRole) user
				.getTbcuitmoonRoles().toArray()[0]).getCuitMoonRoleName() + "";
		String hql = "";
		if (user != null && role.equals("商家")) {
			hql = "from Apply where applyPerson = '"
					+ user.getCuitMoonUserName() + "'  order by applyTime desc";
		} else {
			hql = "from Apply as T where T.produceBase like '%"
					+ user.getCuitMoonAreaId() + "%' order by applyTime desc";
		}
		page = applyService.getPage(hql, requsetPageNo - 1, pageCounSize);
		model.addAttribute("applyPage", page);
		for (Apply apply : (List<Apply>) page.getPageList()) {
			apply.setHandleDescription(getHandleResult(apply.getHandleResult()));
		}
		return "authc/showApply";
	}

	@SuppressWarnings("unchecked")
	public String getHandleResult(String code) {
		long l_code;
		try {
			l_code = Long.parseLong(code);
		} catch (Exception ex) {
			l_code = 100081;
		}
		String name = "";
		List<TbcuitmoonDictionary> list = (List<TbcuitmoonDictionary>) tbDicService
				.find("from TbcuitmoonDictionary as T where T.cuitMoonDictionaryCode = ? ",
						l_code);
		if (list.size() > 0) {
			name = list.get(0).getCuitMoonDictionaryName();
		} else {
			name = "未知状态";
		}
		return name;
	}

	/**
	 * 认证须知
	 */
	@RequestMapping("applyNotice")
	public String applyNotice() {

		return "authc/applyNotice";

	}

	/** 添加认证页面 */
	@RequestMapping(value = "/addApply", method = RequestMethod.GET)
	public String addAplay(Model model, HttpSession session) {
		String hql = "from Businessmanagement  as T where T.userName = ?";
		List<?> list = bmService.find(
				hql,
				session.getAttribute(Constant.LOGINUSER) == null ? "cd_pz_jwt"
						: ((TbcuitmoonUser) session
								.getAttribute(Constant.LOGINUSER))
								.getCuitMoonUserName());
		if (list.size() > 0) {
			// 用户基本信息
			Businessmanagement bsm = (Businessmanagement) list.get(0);
			model.addAttribute("bsm", list.get(0));
			bsm.setCampanyType(tbDicService.getDicNameByCode(bsm
					.getCampanyType()));
			// 荣誉资质
			String sql = "from Businessqualification where companyNo=?";
			List<?> bqList = bqService.find(sql,
					((Businessmanagement) list.get(0)).getCampanyNo());
			model.addAttribute("bsq", bqList);
		}
		// 绑定省级地区
		String areHql = "from TbcuitmoonArea as T where T.cuitMoonParentAreaCode = ?";
		List<?> areaList = tbAreaService.find(areHql, 0l);
		model.addAttribute("pro", areaList);
		// 获取产品类型
		// 获取字典根级类别
		List<?> dicList = tbDicService
				.find("from TbcuitmoonDictionary as T where T.cuitMoonParentDictionaryCode = ?",
						10005L);
		model.addAttribute("dicType", dicList);
		model.addAttribute("pfType", pfService.loadAll());
		return "authc/addApply";
	}

	@RequestMapping(value = "/addApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addApply(Model model,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		// 第一步
		Apply apply = new Apply(UUID.randomUUID().toString().replace("-", ""));
		// location 地址，address通讯地址
		// System.out.print(WebUtils.findParameterValue(request,
		// "edit_contact_address"));
		apply.setAddress(request.getParameterMap().get("edit_contact_address")[0]);
		apply.setUnityName(request.getParameterMap().get("edit_company_name")[0]);
		apply.setRepersentative(request.getParameterMap().get("edit_repr_name")[0]);
		apply.setUnitProperty(request.getParameterMap().get(
				"edit_company_property")[0]);
		apply.setCounterMan(request.getParameterMap()
				.get("edit_contact_person")[0]);
		apply.setCountermanPhone(request.getParameterMap().get(
				"edit_contact_phone")[0]);
		apply.setCountermanFax(request.getParameterMap().get(
				"edit_contact_email")[0]);
		apply.setApplicationType(request.getParameterMap().get("chb_type")[0]);
		// 第二步
		apply.setProduceBase(request.getParameterMap().get("edit_product_area")[0]);
		apply.setLocation(request.getParameterMap().get("edit_product_address")[0]);
		apply.setProductionCharger(request.getParameterMap().get(
				"edit_product_resp")[0]);
		apply.setContact(request.getParameterMap().get("edit_product_phone")[0]);
		apply.setPhone(request.getParameterMap().get("edit_product_cell_phone")[0]);
		//apply.setFax(request.getParameterMap().get("edit_product_fox_mail")[0]);
		apply.setIsApplyOriginCode(request.getParameterMap().get("getLable")[0]);
		apply.setApplyTime(new Date(System.currentTimeMillis()));
		apply.setEmail(request.getParameterMap().get("edit_product_e_mail")[0]);
		// 第三步，保存新上传的资质证明

		// 第四步，产品信息
		apply.setCommodityType(request.getParameterMap().get("select_type")[0]);
		apply.setProductName(request.getParameterMap().get("edit_produce_name")[0]);
		apply.setProductBrand(request.getParameterMap().get(
				"edit_produce_brand")[0]);
		apply.setRemark(request.getParameterMap().get("edit_produce_quality")[0]);
		apply.setProdutOutput(request.getParameterMap().get(
				"edit_produce_production")[0]);
		apply.setOutputValue(request.getParameterMap().get(
				"edit_produce_output")[0]);
		apply.setScale(request.getParameterMap().get("edit_produce_scope")[0]);
		apply.setFormat(request.getParameterMap().get("edit_produce_spec")[0]);
		apply.setApplyOriginType(request.getParameterMap().get("labelType")[0]);
		apply.setInTotal(request.getParameterMap().get("edit_produce_cost")[0]);
		apply.setNumber(Integer.parseInt(request.getParameterMap().get(
				"edit_produce_label_count")[0]));
		apply.setControlImplement(request.getParameterMap().get(
				"edit_weather_control_mesure")[0]);
		apply.setProductDescription(request.getParameterMap().get(
				"edit_product_descipt")[0]);
		apply.setProductOverview(request.getParameterMap().get(
				"edit_place_sitiation")[0]);
		Map<String, String> map = new HashMap<String, String>();
		String companyNo = "";
		// String userName = getLoginUserName();
		String cerIds = request.getParameter("cerIds");
		List<Businessqualification> b_list = new ArrayList<Businessqualification>();

		String[] cerIdsArray = cerIds.split(",");
		for (String cerId : cerIdsArray) {
			if (hasLength(cerId)) {
				Businessqualification bg = bqService.get(cerId);
				if (!isEmpty(bg)) {
					b_list.add(bg);
				}
			}
		}

		for (int i = 0; i < b_list.size(); i++) {
			Businessqualification bc = b_list.get(i);
			Certificate cert = new Certificate();
			cert.setAwardDepart(bc.getPublishUnit());
			cert.setOriginId(apply.getApplyBh());
			cert.setPictureUrl(bc.getPicUrl());
			cert.setGetTime(bc.getAwardTime() + "");
			cert.setCertificateId(UUID.randomUUID().toString().replace("-", ""));
			cert.setCertificateName(bc.getName());
			cert.setCampanyNo(companyNo);
			certSrevice.save(cert);
		}
		String images = WebUtils.findParameterValue(request, "imags");
		if (images != null && images.length() > 1) {
			ObjectMapper maper = new ObjectMapper();
			try {
				JsonNode node = maper.readTree(images);
				for (int i = 0; i < node.size(); i++) {
					String name = node.get(i).get("name").asText();
					String publisher = node.get(i).get("publisher").asText();
					String url = node.get(i).get("url").asText();
					String date = node.get(i).get("time").asText();
					Certificate cert = new Certificate();
					cert.setAwardDepart(publisher);
					cert.setOriginId(apply.getApplyBh());
					cert.setPictureUrl(imageShowPath + "/" + url);
					cert.setGetTime(date);
					cert.setCertificateId(UUID.randomUUID().toString()
							.replace("-", ""));
					cert.setCertificateName(name);
					cert.setCampanyNo(companyNo);
					certSrevice.save(cert);
				}
			} catch (IOException e) {
				map.put("success", "false");
				return map;
			}
		}
		/*
		 * // 写入businessqutifacate表的资质到证书表 List<Businessqualification> bc_list =
		 * (List<Businessqualification>) bqService
		 * .find("from Businessqualification where companyNo = ?", companyNo);
		 * for (int i = 0; i < bc_list.size(); i++) { Businessqualification bc =
		 * bc_list.get(i); Certificate cert = new Certificate();
		 * cert.setAwardDepart(bc.getPublishUnit());
		 * cert.setOriginId(apply.getApplyBh());
		 * cert.setPictureUrl(bc.getPicUrl()); cert.setGetTime(bc.getAwardTime()
		 * + "");
		 * cert.setCertificateId(UUID.randomUUID().toString().replace("-", ""));
		 * cert.setCertificateName(bc.getName()); cert.setCampanyNo(companyNo);
		 * certSrevice.save(cert); }
		 */

		apply.setHandleResult(INIT_STATUS + "");
		apply.setDeclareStatus(INIT_STATUS + "");
		apply.setIsWithDraw(INIT_STATUS + "");
		apply.setApplyPerson(getLoginUser(session).getCuitMoonUserName());
		String id = applyService.save(apply).toString();
		if (id == null)
			map.put("success", "false");
		else
			map.put("success", "true");

		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "editApply")
	public String editApply(String applyBh, Model model, HttpSession session) {
		// 项目信息
		Apply apply = applyService.get(applyBh);
		model.addAttribute("apply", apply);
		String address = apply.getProduceBase();
		String[] strs = address.split("\\|");
		address = "";
		
		for (String string : strs) {
			List<TbcuitmoonArea> list = (List<TbcuitmoonArea>) tbAreaService
					.find("from TbcuitmoonArea as T where T.cuitMoonAreaCode = ?",
							Long.parseLong(string));
			if (list.size() > 0) {
				address += list.get(0).getCuitMoonAreaName()+",";
			}
		}
		String[] strs2 = address.split("\\,");
		String a = strs2[0];
		model.addAttribute("a", strs2[0]);
		model.addAttribute("b", strs2[1]);
		model.addAttribute("c", strs2[2]);
		
		
		// 资质证明
		List<Certificate> certs = (List<Certificate>) certSrevice.find(
				"from Certificate where originId = ?", applyBh);
		model.addAttribute("bsq", certs);

		// 绑定省级地区
		String areHql = "from TbcuitmoonArea as T where T.cuitMoonParentAreaCode = ?";
		List<?> areaList = tbAreaService.find(areHql, 0l);
		model.addAttribute("pro", areaList);
		// 获取产品类型
		// 获取字典根级类别
		List<?> dicList = tbDicService
				.find("from TbcuitmoonDictionary as T where T.cuitMoonParentDictionaryCode = ?",
						10005L);
		model.addAttribute("dicType", dicList);
		model.addAttribute("applyBh", applyBh);
		model.addAttribute("pfType", pfService.loadAll());

		return "authc/editApply";
	}

	@ResponseBody
	@RequestMapping(value = "editApply", method = RequestMethod.POST)
	public Map<String, String> editApply(String applyBh,
			HttpServletRequest request, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "false");
		Apply apply = applyService.get(applyBh);
		if (apply == null) {
			return map;
		}
		// location 地址，address通讯地址
		// System.out.print(WebUtils.findParameterValue(request,
		// "edit_contact_address"));
		apply.setAddress(request.getParameterMap().get("edit_contact_address")[0]);
		apply.setUnityName(request.getParameterMap().get("edit_company_name")[0]);
		apply.setRepersentative(request.getParameterMap().get("edit_repr_name")[0]);
		apply.setUnitProperty(request.getParameterMap().get(
				"edit_company_property")[0]);
		apply.setCounterMan(request.getParameterMap()
				.get("edit_contact_person")[0]);
		apply.setCountermanPhone(request.getParameterMap().get(
				"edit_contact_phone")[0]);
		apply.setCountermanFax(request.getParameterMap().get(
				"edit_contact_email")[0]);
		apply.setApplicationType(request.getParameterMap().get("chb_type")[0]);
		// 第二步
		apply.setProduceBase(request.getParameterMap().get("edit_product_area")[0]);
		apply.setLocation(request.getParameterMap().get("edit_product_address")[0]);
		apply.setProductionCharger(request.getParameterMap().get(
				"edit_product_resp")[0]);
		apply.setContact(request.getParameterMap().get("edit_product_phone")[0]);
		apply.setPhone(request.getParameterMap().get("edit_product_cell_phone")[0]);
		//apply.setFax(request.getParameterMap().get("edit_product_fox_mail")[0]);
		apply.setIsApplyOriginCode(request.getParameterMap().get("getLable")[0]);
		apply.setApplyTime(new Date(System.currentTimeMillis()));
		apply.setEmail(request.getParameterMap().get("edit_product_e_mail")[0]);
		// 第三步，保存新上传的资质证明

		// 第四步，产品信息
		apply.setCommodityType(request.getParameterMap().get("select_type")[0]);
		apply.setProductName(request.getParameterMap().get("edit_produce_name")[0]);
		apply.setProductBrand(request.getParameterMap().get(
				"edit_produce_brand")[0]);
		apply.setRemark(request.getParameterMap().get("edit_produce_quality")[0]);
		apply.setProdutOutput(request.getParameterMap().get(
				"edit_produce_production")[0]);
		apply.setOutputValue(request.getParameterMap().get(
				"edit_produce_output")[0]);
		apply.setScale(request.getParameterMap().get("edit_produce_scope")[0]);
		apply.setFormat(request.getParameterMap().get("edit_produce_spec")[0]);
		apply.setApplyOriginType(request.getParameterMap().get("labelType")[0]);
		apply.setInTotal(request.getParameterMap().get("edit_produce_cost")[0]);
		apply.setNumber(Integer.parseInt(request.getParameterMap().get(
				"edit_produce_label_count")[0]));
		apply.setControlImplement(request.getParameterMap().get(
				"edit_weather_control_mesure")[0]);
		apply.setProductDescription(request.getParameterMap().get(
				"edit_product_descipt")[0]);
		apply.setProductOverview(request.getParameterMap().get(
				"edit_place_sitiation")[0]);
		String companyNo = "";
		// String userName = getLoginUserName();

		/*for (int i = 0; i < b_list.size(); i++) {
			Businessqualification bc = b_list.get(i);
			Certificate cert = new Certificate();
			cert.setAwardDepart(bc.getPublishUnit());
			cert.setOriginId(apply.getApplyBh());
			cert.setPictureUrl(bc.getPicUrl());
			cert.setGetTime(bc.getAwardTime() + "");
			cert.setCertificateId(UUID.randomUUID().toString().replace("-", ""));
			cert.setCertificateName(bc.getName());
			cert.setCampanyNo(companyNo);
			certSrevice.save(cert);
		}*/
		String images = WebUtils.findParameterValue(request, "imags");
		if (images != null && images.length() > 1) {
			ObjectMapper maper = new ObjectMapper();
			try {
				JsonNode node = maper.readTree(images);
				for (int i = 0; i < node.size(); i++) {
					String name = node.get(i).get("name").asText();
					String publisher = node.get(i).get("publisher").asText();
					String url = node.get(i).get("url").asText();
					String date = node.get(i).get("time").asText();
					Certificate cert = new Certificate();
					cert.setAwardDepart(publisher);
					cert.setOriginId(apply.getApplyBh());
					cert.setPictureUrl(imageShowPath + "/" + url);
					cert.setGetTime(date);
					cert.setCertificateId(UUID.randomUUID().toString()
							.replace("-", ""));
					cert.setCertificateName(name);
					cert.setCampanyNo(companyNo);
					certSrevice.save(cert);
				}
			} catch (IOException e) {
				map.put("success", "false");
				return map;
			}
		}

		apply.setHandleResult(INIT_STATUS + "");
		apply.setDeclareStatus(INIT_STATUS + "");
		apply.setIsWithDraw(INIT_STATUS + "");
		apply.setApplyPerson(getLoginUser(session).getCuitMoonUserName());
		applyService.update(apply);
		map.put("success", "true");

		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "del_cert")
	public Map<String, String> String(String id, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		// 删除文件
		Certificate cert = certSrevice.get(id);
		String fileName = cert.getPictureUrl().substring(
				cert.getPictureUrl().lastIndexOf("/") + 1,
				cert.getPictureUrl().length());
		delFile(fileName, request, 1);
		map.put("success", "false");
		try {
			certSrevice.deleteByKey(id);
			map.put("success", "true");
		} catch (Exception ex) {
			ex.printStackTrace();
			return map;
		}
		return map;
	}

	@RequestMapping(value = "/updateCity")
	public String updateCity(Model model, Long parentCode) {
		String hql = "from TbcuitmoonArea as T where T.cuitMoonParentAreaCode = ?";
		List<?> list = tbAreaService.find(hql, parentCode);
		model.addAttribute("city", list);
		return "authc/addApply";
	}

	@RequestMapping(value = "/updateCountry")
	public String updateCountry(Model model, Long parentCode) {
		String hql = "from TbcuitmoonArea as T where T.cuitMoonParentAreaCode = ?";
		List<?> list = tbAreaService.find(hql, parentCode);
		model.addAttribute("country", list);
		return "authc/addApply";
	}

	@RequestMapping(value = "/deleteApply")
	public String deleteApply(Model model, String applyBh, String requsetPageNo) {
		applyService.deleteByKey(applyBh);
		return "redirect:/authc/showApply?requsetPageNo=" + requsetPageNo;
	}

	@RequestMapping(value = "/searchApply")
	public String searchApply(Model model, String key) {
		Page page = null;
		page = applyService.getPage(
				"from Apply as T where T.productName like '%" + key + "%'",
				pageNo - 1, pageCounSize);
		model.addAttribute("applyPage", page);
		model.addAttribute("key", key);
		return "/authc/showApply";
	}

	// 上报申请
	@RequestMapping(value = "/reportApply")
	public/* Map<String, String> */String reportApply(String applyBh,
			Integer requsetPageNo) {
		Apply entity = applyService.load(applyBh);
		entity.setHandleResult("100081");
		entity.setDeclareStatus("100021");
		entity.setIsWithDraw("100021");
		applyService.update(entity);
		return "redirect:/authc/showApply?requsetPageNo=" + requsetPageNo;
	}

	// 申请详情
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detailApply")
	public String detailApply(Model model, String applyBh,
			HttpServletRequest request, HttpSession session) {
		Apply entity = (Apply) applyService.find(
				"from Apply where applyBh = ?", applyBh).get(0);
		request.setAttribute("apply", entity);
		// 获取地址信息
		String address = entity.getProduceBase();
		String[] strs = address.split("\\|");
		address = "";
		for (String string : strs) {
			List<TbcuitmoonArea> list = (List<TbcuitmoonArea>) tbAreaService
					.find("from TbcuitmoonArea as T where T.cuitMoonAreaCode = ?",
							Long.parseLong(string));
			if (list.size() > 0) {
				address += list.get(0).getCuitMoonAreaName();
			}
		}
		String hql = "from Businessmanagement  as T where T.userName = ?";
		List<?> list = bmService.find(
				hql,
				session.getAttribute(Constant.LOGINUSER) == null ? ""
						: ((TbcuitmoonUser) session
								.getAttribute(Constant.LOGINUSER))
								.getCuitMoonUserName());
		if (list.size() > 0) {
			// 用户基本信息
			model.addAttribute("bsm", list.get(0));
			// 荣誉资质
			String cert_hql = "from Certificate where originId = ?";
			List<Certificate> certs = (List<Certificate>) certSrevice.find(
					cert_hql, entity.getApplyBh());
			model.addAttribute("certs", certs);
		}
		
		//Test
		List<Certificate> certs = (List<Certificate>) certSrevice.find(
				"from Certificate where originId = ?", applyBh);
		model.addAttribute("certs", certs);
		
		request.setAttribute("address", address);
		List<Productinfomation> p_list = (List<Productinfomation>) pfService
				.find("from Productinfomation where productNumber = ?",
						entity.getCommodityType());
		if (p_list.size() > 0) {
			model.addAttribute("type", p_list.get(0).getProductName());
		}

		return "/authc/detailApply";
	}
}
