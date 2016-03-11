package edu.cuit.module.entm.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.entm.entity.TbncpQiyeProduct;
import edu.cuit.module.entm.service.TbncpQiyeProductService;
import edu.cuit.module.infom.entity.Businessmanagement;
import edu.cuit.module.infom.service.BusinessmanagementService;
import edu.cuit.module.sys.entity.TbcuitmoonUser;
import edu.cuit.module.sys.service.TbcuitmoonUserService;

@RequestMapping("enterprise/produce")
@Controller
public class EnterpriseProduceController extends BaseController{
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;
	@Value("#{settings['image.showpath']}")
	public String imageShowPath;
	

	@Autowired
	TbncpQiyeProductService produceService;
	@Autowired
	BusinessmanagementService businessService;
	@Autowired
	TbcuitmoonUserService userService;

	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public String list(@RequestParam(required = false) Integer requsetPageNo,
			Model model, String key) {
		Page page;
		if (requsetPageNo == null) {
			requsetPageNo = pageNo;
		}
		String compayNo = ""; // 当前登录用户所属商家ID;
		List<Businessmanagement> b_list = (List<Businessmanagement>) businessService
				.find("from Businessmanagement where userName = ?",
						getLoginUserName());
		compayNo = b_list.size() > 0 ? b_list.get(0).getCampanyNo() : "";
		String hql = "from TbncpQiyeProduct ";
		if (!getLoginUserName().equals("superadmin")) {
			hql += "where qiyenum = '" + compayNo + "'";
		}
		if (key != null) {
			if(!getLoginUserName().equals("superadmin"))
				hql += " and productname like '%" + key + "%'";
			else
				hql += " where productname like  '%" + key + "%'";
		}
		hql += " order by cretime desc ";
		page = produceService.getPage(hql, requsetPageNo - 1, pageCounSize);
		model.addAttribute("list", page);
		for (TbncpQiyeProduct item : (List<TbncpQiyeProduct>) page.getPageList()) {
			// 获取用户名
			String companyNo = item.getQiyenum();// 商家编号
			Businessmanagement company = businessService.get(companyNo);
			String username = company.getUserName();
			List<TbcuitmoonUser> list = (List<TbcuitmoonUser>) userService
					.find("from TbcuitmoonUser where cuitMoonUserName = ?",
							username);
			if (list.size() > 0) {
				item.setQiyenum(list.get(0).getCuitMoonUserRealName());
			} else {
				item.setQiyenum("未知用户");
			}
		}
		return "enterprise/produce/list";
	}

	@RequestMapping("add")
	public String add() {
		return "enterprise/produce/add";
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public Map<String, String> add(String productname, String productcontent,String productimg,
			HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			TbncpQiyeProduct produce = new TbncpQiyeProduct(UUID.randomUUID().toString()
					.replace("-", ""));
			produce.setCretime(new Date(System.currentTimeMillis()));
			List<Businessmanagement> list = (List<Businessmanagement>) businessService
					.find("from Businessmanagement where userName = ?",
							getLoginUser(session).getCuitMoonUserName());
			produce.setQiyenum(list.get(0).getCampanyNo());
			produce.setProductcontent(productcontent);
			//produce.setProductimg(imageShowPath + "/" + productimg);
			produce.setProductimg(productimg);
			produce.setProductname(productname);
			produceService.save(produce);
			map.put("success", "true");
		} catch (Exception ex) {
			map.put("success", "false");
		}
		//TODO
		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("details")
	public String details(String productId, Model model) {
		TbncpQiyeProduct produce = produceService.get(productId);
		model.addAttribute("produce", produce);
		String companyNo = produce.getQiyenum();// 商家编号
		Businessmanagement company = businessService.get(companyNo);
		String username = company.getUserName();
		List<TbcuitmoonUser> list = (List<TbcuitmoonUser>) userService
				.find("from TbcuitmoonUser where cuitMoonUserName = ?",
						username);
		if (list.size() > 0) {
			produce.setQiyenum(list.get(0).getCuitMoonUserRealName());
		} else {
			produce.setQiyenum("未知用户");
		}
		return "enterprise/produce/details";
	}

	@RequestMapping("edit")
	public String edit(String productId, Model model) {
		TbncpQiyeProduct produce = produceService.get(productId);
		model.addAttribute("produce", produce);
		return "enterprise/produce/edit";
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public Map<String, String> edit(String productname, String productcontent,String productimg,
			String id, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			TbncpQiyeProduct produce = produceService.get(id);
			produce.setCretime(new Date(System.currentTimeMillis()));
			List<Businessmanagement> list = (List<Businessmanagement>) businessService
					.find("from Businessmanagement where userName = ?",
							getLoginUser(session).getCuitMoonUserName());
			produce.setQiyenum(list.get(0).getCampanyNo());
			produce.setProductcontent(productcontent);
			produce.setProductname(productname);
//			produce.setProductimg(imageShowPath + "/" + productimg);
			produce.setProductimg(productimg);
			produceService.update(produce);
			map.put("success", "true");
		} catch (Exception ex) {
			map.put("success", "false");
		}
		return map;
	}

	@RequestMapping("delete")
	public String delete(String productId, Model model, String requsetPageNo) {
		TbncpQiyeProduct news = produceService.get(productId);
		produceService.delete(news);
		return "redirect:list?requsetPageNo=" + requsetPageNo;
	}
}
