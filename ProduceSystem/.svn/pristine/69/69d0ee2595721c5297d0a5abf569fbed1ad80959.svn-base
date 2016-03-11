package edu.cuit.module.origin.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.model.service.ProductinfomationService;
import edu.cuit.module.origin.entity.Certificate;
import edu.cuit.module.origin.entity.Productapply;
import edu.cuit.module.origin.service.CertificateService;
import edu.cuit.module.origin.service.ProductapplyService;
import edu.cuit.module.sys.entity.TbcuitmoonArea;
import edu.cuit.module.sys.service.TbcuitmoonAreaService;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;
import edu.cuit.module.sys.service.TbcuitmoonUserService;

@Controller
@RequestMapping("org_exam")
public class OrgExamController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;

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
	TbcuitmoonDictionaryService dicService;
	@Autowired
	CertificateService certSrevice;

	@RequestMapping("list")
	public String list(@RequestParam(required = false) Integer requsetPageNo,
			Model model) {
		Page page;
		if (requsetPageNo == null) {
			requsetPageNo = pageNo;
		}
		log.debug("pageNO {}", requsetPageNo);
		String hql = "from Productapply where orignStatus > 100021 order by applyTime desc";
		page = productapplyService.getPage(hql,requsetPageNo - 1,
				pageCounSize);
		model.addAttribute("productapplyPage", page);
		return "org_exam/list";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("audit")
	public String audit(String id, Model model) {
		Productapply apply = paService.get(id);
		model.addAttribute("apply", apply);
		model.addAttribute("username", getLoginUserName());
		String address = apply.getOriginAddress();
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
		String hql = "from Certificate where originId = ?";
		List<Certificate> certs = (List<Certificate>) certSrevice.find(hql,
				apply.getOriginId());
		model.addAttribute("certs", certs);
		
		model.addAttribute("address", address);
		return "org_exam/audit";
	}
	
	@ResponseBody
	@RequestMapping(value="audit",method=RequestMethod.POST)
	public Map<String, String> audit(String originId,HttpServletRequest request,HttpSession session){
		Productapply apply = paService.get(originId);
		String isPass = WebUtils.findParameterValue(request, "IsPass");
		String pserson = WebUtils.findParameterValue(request, "Person");
		String advice = WebUtils.findParameterValue(request, "Advice");
		
		if (isPass.equals("Yes")) {
			apply.setOrignStatus("100084");
		} else if (isPass.equals("No")) {
			apply.setOrignStatus("100083");
		} else if (isPass.equals("Back")) {
			apply.setOrignStatus("100082");
		}
		apply.setProvincesPerson(pserson);
		apply.setProvinceAdvice(advice);
		apply.setProvinceTime(new Date(System.currentTimeMillis()));
		
		paService.update(apply);
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", "true");
		return map;
	}
}
