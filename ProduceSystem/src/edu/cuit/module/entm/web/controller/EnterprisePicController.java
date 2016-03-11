package edu.cuit.module.entm.web.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.loader.custom.Return;
import org.json.JSONObject;
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

import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.authc.service.ApplyService;
import edu.cuit.module.authc.service.ApprovedatamanagerService;
import edu.cuit.module.authc.service.BearinginfoService;
import edu.cuit.module.authc.service.PrictureinfomationService;
import edu.cuit.module.authc.service.QualityIdentificationService;
import edu.cuit.module.authc.service.ReportinfomationService;
import edu.cuit.module.entm.entity.Producepic;
import edu.cuit.module.entm.service.ProducepicService;
import edu.cuit.module.infom.entity.Businessmanagement;
import edu.cuit.module.infom.service.BusinessmanagementService;
import edu.cuit.module.infom.service.BusinessqualificationService;
import edu.cuit.module.label.service.LabelapplicationService;
import edu.cuit.module.model.service.ElementmodelService;
import edu.cuit.module.model.service.ProductinfomationService;
import edu.cuit.module.origin.entity.Certificate;
import edu.cuit.module.origin.entity.Productapply;
import edu.cuit.module.origin.service.CertificateService;
import edu.cuit.module.origin.service.ProductapplyService;
import edu.cuit.module.sys.entity.TbcuitmoonRole;
import edu.cuit.module.sys.entity.TbcuitmoonUser;
import edu.cuit.module.sys.service.TbcuitmoonAreaService;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;
import edu.cuit.module.sys.service.TbcuitmoonUserService;

@Controller
@RequestMapping("enterprise/pic")
public class EnterprisePicController extends BaseController{
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCountSize;
	@Value("#{settings['image.showpath']}")
	public String imageShowPath;
	@Value("#{settings['image.path']}")
	public String imgeRootPath;
	@Value("#{settings['file.path']}")
	public String fileRootPath;
	@Autowired
	ApplyService applyService;
	@Autowired
	LabelapplicationService labelApplyService;
	@Autowired
	TbcuitmoonDictionaryService dictionaryService;
	@Autowired
	ProductapplyService productapplyService;
	@Autowired
	TbcuitmoonAreaService tbcuitmoonAreaService;
	@Autowired
	TbcuitmoonDictionaryService tbcuitmoonDictionaryService;
	@Autowired
	CertificateService certificateService;
	@Autowired 
	QualityIdentificationService qualityIdentificationService;
	@Autowired
	ApprovedatamanagerService approvedatamanagerService;
	@Autowired 
	PrictureinfomationService prictureinfomationService;
	@Autowired
	BusinessmanagementService businessmanagementService;
	@Autowired
	ReportinfomationService reportinfomationService;
	@Autowired
	QualityIdentificationService qaService;
	@Autowired
	ApprovedatamanagerService dataService;
	@Autowired
	BearinginfoService bearService;
	@Autowired
	ElementmodelService elmentService;
	@Autowired
	ProducepicService producepicService;
	private final int INIT_STATUS = 100020;
	
	//分页查询认证产品列表
		private Page getProductApplyInfoList(String queryString, String applyType, 
				String userName, String roleStr, Integer pageIndex) {
			Page page;		
			String sqlString = "";
			if (pageIndex == null){
				pageIndex = pageNo;
			}	
			//1表示气候品质认证 2表示农网认证
			if(applyType != null && applyType.equals("2"))
			{
				if(roleStr.equals("商家"))
				{
					if(queryString == null)
					{
						sqlString = "from Productapply as p where p.orignStatus='100084' and p.applyPerson='"+userName+"'order by p.applyTime desc";
					}else{
						sqlString = "from Productapply as p where p.orignStatus='100084' and p.applyPerson='"+userName+"' and p.originName like '%" + queryString + "%'order by p.applyTime desc";
					}	
				}else{
					if(queryString == null)
					{
						sqlString = "from Productapply as p where p.orignStatus='100084' order by p.applyTime desc";
					}else{
						sqlString = "from Productapply as p where p.orignStatus='100084' and p.originName like '%" + queryString + "%' order by p.applyTime desc";
					}
				}
				page = productapplyService.getPage(sqlString, pageIndex-1, pageCountSize);
			}else{
				if(roleStr.equals("商家"))
				{
					if(queryString == null)
					{
						sqlString = "from Apply as a where a.handleResult='100084' and a.applyPerson='"+userName+"'order by a.applyTime desc";
					}else{
						sqlString = "from Apply as a where a.handleResult='100084' and a.applyPerson='"+userName+"' and a.productName like '%" + queryString + "%'order by a.applyTime desc";
					}			
				}else{
					if(queryString == null)
					{
						sqlString = "from Apply as a where a.handleResult='100084' order by a.applyTime desc";
					}else{
						sqlString = "from Apply as a where a.handleResult='100084' and a.productName like '%" + queryString + "%' order by a.applyTime desc";
					}
				}
				page = applyService.getPage(sqlString, pageIndex-1, pageCountSize);
			}	
			
			return page;
		}
		//查询该商家是否有认证申请
		private String checkApplylist(String applyPerson){
			String flag = "";
			String hql ="from Apply as a where a.handleResult='100084' and a.applyPerson='"+applyPerson+"'";
			List<?> list = applyService.find(hql);
			if(list.size()>0)
			{
				flag = "1";
			}else{
				flag = "2";
			}
			return flag;
		}
	
	@RequestMapping("list")
	public String PCList(@RequestParam(required = false) Integer requestPageNo, 
			@RequestParam(required = false) String queryString, 
			@RequestParam(required = false) String applyType, Model model, HttpSession session)
	{
		if (queryString != null)
			queryString = toUTF8(queryString != null ? queryString : "");
		if(applyType == null)
			applyType = "1";
		TbcuitmoonUser user = getLoginUser(session);
		String userName = user.getCuitMoonUserName();		
		String roleStr = user == null ? "" : ((TbcuitmoonRole) user
				.getTbcuitmoonRoles().toArray()[0]).getCuitMoonRoleName() + "";
		if(roleStr.equals("商家"))
		{
			applyType = checkApplylist(userName);
		}		
		Page page = getProductApplyInfoList(queryString,applyType,userName,roleStr,requestPageNo);
		boolean isPaging = page.getPageList().size() > 0 ? true : false; // 是否分页
		model.addAttribute("DataList", page);
		model.addAttribute("isPaging", isPaging);
		model.addAttribute("queryString", queryString);
		model.addAttribute("applyType", applyType);
		return "enterprise/pic/list";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="ManagePic" ,method = RequestMethod.GET)
	public String PCManage(@RequestParam("key") String ApplyBh,@RequestParam(required=false) String type,Model model )
	{
		List<Producepic> list=(List<Producepic>) producepicService.find("from Producepic as p where p.applyBh=?", ApplyBh);
		model.addAttribute("piclist", list);
		model.addAttribute("AppleBh", ApplyBh);
		return "enterprise/pic/ManagePic";
		
	}
	
	@RequestMapping(value = "file", method = RequestMethod.POST)
	@ResponseBody
	public String file( HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
		String fileDirectory = request.getServletContext().getRealPath(imgeRootPath);
		return uploadFile(request, fileDirectory);
	}
	
	@ResponseBody
	@RequestMapping("image_del")
	private Map<String, String> image_del(String fileName,
			HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String imageSavePath = request.getServletContext()
				.getRealPath(imgeRootPath);
		File file = new File(imageSavePath + File.separator + fileName);
		if (file.delete()) {
			map.put("success", "true");
		} else {
			map.put("success", "false");
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "del_cert")
	public Map<String, String> String(String id, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		// 删除文件
		Producepic producepic = producepicService.get(id);
		String fileName = producepic.getPicsrc().substring(
				producepic.getPicsrc().lastIndexOf("/") + 1,
				producepic.getPicsrc().length());
		String aString="";
		delFile(fileName, request, 1);
		map.put("success", "false");
		try {
			producepicService.deleteByKey(id);
			map.put("success", "true");
		} catch (Exception ex) {
			ex.printStackTrace();
			return map;
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String  originAedit_post(HttpServletRequest request,
			String applyNum,String applyReason,String imgName,String applyBh,Model model) {
		String pid=null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("sucess", "true");
//		String images = WebUtils.findParameterValue(request, "imags");
		//if(images != null && images.length() > 1){
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
		String dateString=dateFormat.format(now);
					Producepic producepic=new Producepic();
					producepic.setId(UUID.randomUUID().toString().replace("-", ""));
					producepic.setPicName(applyNum);
					producepic.setTime(dateString);
					producepic.setPicsrc(imageShowPath + "/" + imgName);
					producepic.setApplyBh(applyBh);
					pid =producepicService.save(producepic).toString();
					List<Producepic> list=(List<Producepic>) producepicService.find("from Producepic as p where p.applyBh=?", applyBh);
					model.addAttribute("piclist", list);
					model.addAttribute("AppleBh", applyBh);
					return "enterprise/pic/ManagePic";
		
	}
	
	
}
