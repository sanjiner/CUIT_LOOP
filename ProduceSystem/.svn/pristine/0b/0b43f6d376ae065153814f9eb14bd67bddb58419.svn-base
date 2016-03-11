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
import edu.cuit.module.entm.entity.TbncpQiyeNews;
import edu.cuit.module.entm.service.TbncpQiyeNewsService;
import edu.cuit.module.infom.entity.Businessmanagement;
import edu.cuit.module.infom.service.BusinessmanagementService;
import edu.cuit.module.sys.entity.TbcuitmoonUser;
import edu.cuit.module.sys.service.TbcuitmoonUserService;

@Controller
@RequestMapping("enterprise/news")
public class EnterpriseNewsController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;

	@Autowired
	TbncpQiyeNewsService newsService;
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
		String hql = "from TbncpQiyeNews ";
		if (!getLoginUserName().equals("superadmin")) {
			hql += "where qiyenum = '" + compayNo + "'";
		}
		if (key != null) {
			if(!getLoginUserName().equals("superadmin"))
				hql += " and newstitle like '%" + key + "%'";
			else
				hql += " where newstitle like  '%" + key + "%'";
		}
		hql += " order by cretime desc ";
		page = newsService.getPage(hql, requsetPageNo - 1, pageCounSize);
		model.addAttribute("list", page);
		for (TbncpQiyeNews item : (List<TbncpQiyeNews>) page.getPageList()) {
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
		return "enterprise/news/list";
	}

	@RequestMapping("add")
	public String add() {
		return "enterprise/news/add";
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public Map<String, String> add(String newstitle, String newscontent,
			HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			TbncpQiyeNews news = new TbncpQiyeNews(UUID.randomUUID().toString()
					.replace("-", ""));
			news.setCretime(new Date(System.currentTimeMillis()));
			List<Businessmanagement> list = (List<Businessmanagement>) businessService
					.find("from Businessmanagement where userName = ?",
							getLoginUser(session).getCuitMoonUserName());
			news.setQiyenum(list.get(0).getCampanyNo());
			news.setNewstitle(newstitle);
			news.setNewscontent(newscontent);
			newsService.save(news);
			map.put("success", "true");
		} catch (Exception ex) {
			map.put("success", "false");
		}
		return map;
	}

	@RequestMapping("details")
	public String details(String newsId, Model model) {
		TbncpQiyeNews news = newsService.get(newsId);
		model.addAttribute("news", news);
		return "enterprise/news/details";
	}

	@RequestMapping("edit")
	public String edit(String newsId, Model model) {
		TbncpQiyeNews news = newsService.get(newsId);
		model.addAttribute("news", news);
		return "enterprise/news/edit";
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public Map<String, String> edit(String newstitle, String newscontent,
			String newsId, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			TbncpQiyeNews news = newsService.get(newsId);
			news.setCretime(new Date(System.currentTimeMillis()));
			List<Businessmanagement> list = (List<Businessmanagement>) businessService
					.find("from Businessmanagement where userName = ?",
							getLoginUser(session).getCuitMoonUserName());
			news.setQiyenum(list.get(0).getCampanyNo());
			news.setNewstitle(newstitle);
			news.setNewscontent(newscontent);
			newsService.update(news);
			map.put("success", "true");
		} catch (Exception ex) {
			map.put("success", "false");
		}
		return map;
	}

	@RequestMapping("delete")
	public String delete(String newsId, Model model, String requsetPageNo) {
		TbncpQiyeNews news = newsService.get(newsId);
		newsService.delete(news);
		return "redirect:list?requsetPageNo=" + requsetPageNo;
	}
}
