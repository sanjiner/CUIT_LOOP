package edu.cuit.module.origin.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
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
import edu.cuit.module.infom.entity.Businessmanagement;
import edu.cuit.module.infom.entity.Businessqualification;
import edu.cuit.module.infom.service.BusinessmanagementService;
import edu.cuit.module.infom.service.BusinessqualificationService;
import edu.cuit.module.model.service.ProductinfomationService;
import edu.cuit.module.origin.entity.Certificate;
import edu.cuit.module.origin.entity.Productapply;
import edu.cuit.module.origin.service.CertificateService;
import edu.cuit.module.origin.service.ProductapplyService;
import edu.cuit.module.sys.entity.TbcuitmoonArea;
import edu.cuit.module.sys.entity.TbcuitmoonRole;
import edu.cuit.module.sys.entity.TbcuitmoonUser;
import edu.cuit.module.sys.service.TbcuitmoonAreaService;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;
import edu.cuit.module.sys.service.TbcuitmoonUserService;

@Controller
@RequestMapping("origin")
public class originApplyController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;
	@Value("#{settings['image.showpath']}")
	public String imageShowPath;

	@Autowired
	ProductapplyService productapplyService;
	@Autowired
	TbcuitmoonUserService TbcuitmoonUserService;
	@Autowired
	TbcuitmoonAreaService tbAreaService;
	@Autowired
	TbcuitmoonDictionaryService tbDicService;
	@Autowired
	ProductinfomationService pfService;
	@Autowired
	ProductapplyService paService;
	@Autowired
	BusinessqualificationService bsqService;
	@Autowired
	BusinessmanagementService bsService;
	@Autowired
	CertificateService certSrevice;
	@Autowired
	BusinessmanagementService bmService;

	private final int INIT_STATUS = 100020;

	@RequestMapping("OAlist")
	public String applylist(
			@RequestParam(required = false) Integer requsetPageNo, Model model,
			HttpSession session) {
		/*
		 * Subject subject = SecurityUtils.getSubject(); subject.checkRoles("");
		 */
		Page page;
		if (requsetPageNo == null) {
			requsetPageNo = pageNo;
		}
		TbcuitmoonUser user = getLoginUser(session);
		String role = user == null ? "" : ((TbcuitmoonRole) user
				.getTbcuitmoonRoles().toArray()[0]).getCuitMoonRoleName() + "";
		String hql = "";

		if (user != null && role.equals("商家")) {
			hql = "from Productapply where applyPerson = '"
					+ user.getCuitMoonUserName() + "'  order by applyTime desc";
		} else {
			hql = "from Productapply as T where T.originAddress like '%"
					+ user.getCuitMoonAreaId() + "%' order by applyTime desc";
		}
		log.debug("pageNO {}", requsetPageNo);
		page = productapplyService
				.getPage(hql, requsetPageNo - 1, pageCounSize);
		model.addAttribute("productapplyPage", page);
		return "origin/OAlist";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "OAadd", method = RequestMethod.GET)
	public String originAadd(Model model,HttpSession session) {
		String areHql = "from TbcuitmoonArea as T where T.cuitMoonParentAreaCode = ?";
		List<?> areaList = tbAreaService.find(areHql, 0l);
		model.addAttribute("pro", areaList);
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
			bsm.setCampanyType(tbDicService.getDicNameByCode(bsm.getCampanyType()));
		}
		// 获取产品类型
		// 获取字典根级类别
		List<?> dicList = tbDicService
				.find("from TbcuitmoonDictionary as T where T.cuitMoonParentDictionaryCode = ?",
						10005L);
		model.addAttribute("dicType", dicList);
		model.addAttribute("pfType", pfService.loadAll());

		// 获取商家资质
		String companyNo = "";
		String userName = getLoginUserName();
		List<Businessmanagement> b_list = (List<Businessmanagement>) bsService
				.find("from Businessmanagement where userName = ?", userName);
		if (b_list.size() > 0) {
			companyNo = b_list.get(0).getCampanyNo();
			List<Businessqualification> bsqList = (List<Businessqualification>) bsqService
					.find("from Businessqualification where companyNo = ?",
							companyNo);
			model.addAttribute("bsqList", bsqList);
		}
		model.addAttribute("UserRealName", getLoginUser(session).getCuitMoonUserRealName());

		return "origin/OAadd";
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public Map<String, String> add(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sucess", "true");
		String companyName = WebUtils.findParameterValue(request,
				"text_company_name");
		String productName = WebUtils.findParameterValue(request,
				"text_product_name");
		String address = WebUtils.findParameterValue(request,
				"text_product_brand");
		String productBrand = WebUtils.findParameterValue(request,
				"text_product_address");
		String outputValue = WebUtils.findParameterValue(request,
				"text_output_value");
		String outputNum = WebUtils.findParameterValue(request,
				"text_output_num");
		String contactPhone = WebUtils.findParameterValue(request,
				"text_contact_phone");
		String labelNum = WebUtils
				.findParameterValue(request, "text_label_num");
		String productBase = WebUtils.findParameterValue(request,
				"text_prodcut_base");
		String desc = WebUtils.findParameterValue(request, "text_product_desc");
		Productapply apply = new Productapply();
		apply.setApplyCompany(companyName);
		apply.setApplyPerson(getLoginUserName());
		apply.setProductBrand(productBrand);
		apply.setProductValue(outputValue);
		apply.setProductNum(outputNum);
		apply.setApplyTime(new Date(System.currentTimeMillis()));
		apply.setConstract(contactPhone);
		apply.setPersonAdress(address);
		apply.setOriginAddress(productBase);
		apply.setOriginName(productName);
		apply.setLabelNum(Integer.parseInt(labelNum));
		apply.setOriginDescription(desc);
		apply.setHandleResult(INIT_STATUS + "");
		apply.setIsWithDraw(INIT_STATUS + "");
		apply.setOrignStatus(INIT_STATUS + "");
		apply.setOriginId(UUID.randomUUID().toString().replace("-", ""));
		String companyNo = "";
		String userName = getLoginUserName();
		List<Businessmanagement> b_list = (List<Businessmanagement>) bsService
				.find("from Businessmanagement where userName = ?", userName);
		if (b_list.size() > 0) {
			companyNo = b_list.get(0).getCampanyNo();
		}
		// 保存资质证明
		String images = WebUtils.findParameterValue(request, "imags");
		if(images != null && images.length() > 1){
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
				cert.setOriginId(apply.getOriginId());
				cert.setPictureUrl(imageShowPath + "/" + url);
				cert.setGetTime(date);
				cert.setCertificateId(UUID.randomUUID().toString()
						.replace("-", ""));
				cert.setCertificateName(name);
				cert.setCampanyNo(companyNo);
				certSrevice.save(cert);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return map;
		}
		}

		// 写入businessqutifacate表的资质到证书表
		List<Businessqualification> bc_list = (List<Businessqualification>) bsqService
				.find("from Businessqualification where companyNo = ?",
						companyNo);
		for (int i = 0; i < bc_list.size(); i++) {
			Businessqualification bc = bc_list.get(i);
			Certificate cert = new Certificate();
			cert.setAwardDepart(bc.getPublishUnit());
			cert.setOriginId(apply.getOriginId());
			cert.setPictureUrl(bc.getPicUrl());
			cert.setGetTime(bc.getAwardTime() + "");
			cert.setCertificateId(UUID.randomUUID().toString().replace("-", ""));
			cert.setCertificateName(bc.getName());
			cert.setCampanyNo(companyNo);
			certSrevice.save(cert);
		}

		String id = paService.save(apply).toString();
		if (id == null) {
			map.put("success", "false");
		} else {
			map.put("success", "true");
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "OAedit", method = RequestMethod.GET)
	public String originAedit(@RequestParam("id") String id, Model model) {
		Productapply pamodel = productapplyService.getPAmodel(id);
		String address = pamodel.getOriginAddress();
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
		model.addAttribute("address", address);
		model.addAttribute("pamodel", pamodel);
		model.addAttribute("id", id);

		String areHql = "from TbcuitmoonArea as T where T.cuitMoonParentAreaCode = ?";
		List<?> areaList = tbAreaService.find(areHql, 0l);
		model.addAttribute("pro", areaList);

		String hql = "from Certificate where originId = ?";
		List<Certificate> certs = (List<Certificate>) certSrevice.find(hql,
				pamodel.getOriginId());
		model.addAttribute("certs", certs);
		return "origin/OAedit";
	}

	@RequestMapping(value = "submit")
	public String submit(String id, String requsetPageNo) {
		Productapply apply = productapplyService.getPAmodel(id);
		apply.setOrignStatus("100023");
		productapplyService.update(apply);
		return "redirect:OAlist?requsetPageNo=" + requsetPageNo;
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public Map<String, String> originAedit_post(HttpServletRequest request,
			String id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sucess", "true");
		String companyName = WebUtils.findParameterValue(request,
				"text_company_name");
		String productName = WebUtils.findParameterValue(request,
				"text_product_name");
		String address = WebUtils.findParameterValue(request,
				"text_product_brand");
		String productBrand = WebUtils.findParameterValue(request,
				"text_product_address");
		String outputValue = WebUtils.findParameterValue(request,
				"text_output_value");
		String outputNum = WebUtils.findParameterValue(request,
				"text_output_num");
		String contactPhone = WebUtils.findParameterValue(request,
				"text_contact_phone");
		String labelNum = WebUtils
				.findParameterValue(request, "text_label_num");
		String productBase = WebUtils.findParameterValue(request,
				"text_prodcut_base");
		String desc = WebUtils.findParameterValue(request, "text_product_desc");
		Productapply apply = paService.get(id);
		apply.setApplyCompany(companyName);
		apply.setApplyPerson(getLoginUserName());
		apply.setProductBrand(productBrand);
		apply.setProductValue(outputValue);
		apply.setProductNum(outputNum);
		apply.setApplyTime(new Date(System.currentTimeMillis()));
		apply.setConstract(contactPhone);
		apply.setPersonAdress(address);
		apply.setOriginAddress(productBase);
		apply.setOriginName(productName);
		apply.setLabelNum(Integer.parseInt(labelNum));
		apply.setOriginDescription(desc);
		apply.setHandleResult(INIT_STATUS + "");
		apply.setIsWithDraw(INIT_STATUS + "");
		apply.setOrignStatus(INIT_STATUS + "");
		String companyNo = "";
		String userName = getLoginUserName();
		List<Businessmanagement> b_list = (List<Businessmanagement>) bsService
				.find("from Businessmanagement where userName = ?", userName);
		if (b_list.size() > 0) {
			companyNo = b_list.get(0).getCampanyNo();
		}
		// 保存资质证明
		String images = WebUtils.findParameterValue(request, "imags");
		if(images != null && images.length() > 1){
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
				cert.setOriginId(apply.getOriginId());
				cert.setPictureUrl(imageShowPath + "/" + url);
				cert.setGetTime(date);
				cert.setCertificateId(UUID.randomUUID().toString()
						.replace("-", ""));
				cert.setCertificateName(name);
				cert.setCampanyNo(companyNo);
				certSrevice.save(cert);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return map;
		}
		}
		paService.update(apply);
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "OAdetail", method = RequestMethod.GET)
	public String originAdetail(@RequestParam("id") String id, Model model) {
		Productapply pamodel = productapplyService.getPAmodel(id);
		String address = pamodel.getOriginAddress();
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
		model.addAttribute("address", address);
		model.addAttribute("pamodel", pamodel);

		String hql = "from Certificate where originId = ?";
		List<Certificate> certs = (List<Certificate>) certSrevice.find(hql,
				pamodel.getOriginId());
		model.addAttribute("certs", certs);
		return "origin/OAdetail";
	}
	
}
