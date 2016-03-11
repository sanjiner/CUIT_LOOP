package edu.cuit.module.sys.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.sys.service.TbcuitmoonLogService;

@Controller
@RequestMapping("sys/log")
public class LogController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;

	@Autowired
	private TbcuitmoonLogService tbcuitmoonLogService;

	@RequestMapping("logList")
	public String showList(@RequestParam(required=false) Integer requsetPageNo, Model model) {
		if((requsetPageNo == null) || (requsetPageNo <= 0)){
			requsetPageNo = pageNo;
		}
		Page logPage = tbcuitmoonLogService
				.findByTimeDesc(requsetPageNo, pageCounSize);
		model.addAttribute("page", logPage);
		return "sys/log/logList";
	}

	@RequestMapping("delete")
	public String deleteLog(String cuitMoonLogId) {
		if (hasLength(cuitMoonLogId))
			tbcuitmoonLogService.deleteByKey(cuitMoonLogId);
		return "redirect:logList";
	}
}
