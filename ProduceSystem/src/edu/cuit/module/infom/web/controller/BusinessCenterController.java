package edu.cuit.module.infom.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.cuit.common.enums.DictionaryEnum;
import edu.cuit.module.auchc.entity.Apply;
import edu.cuit.module.authc.service.ApplyService;
import edu.cuit.module.entm.entity.TbncpQiyeNews;
import edu.cuit.module.entm.entity.TbncpQiyeProduct;
import edu.cuit.module.entm.service.TbncpQiyeNewsService;
import edu.cuit.module.entm.service.TbncpQiyeProductService;
import edu.cuit.module.infom.entity.Businessmanagement;
import edu.cuit.module.infom.service.BusinessmanagementService;

@Controller
@RequestMapping("bussiness_center")
public class BusinessCenterController {
	@Autowired
	BusinessmanagementService businessService;
	@Autowired
	TbncpQiyeNewsService newsService;
	@Autowired
	TbncpQiyeProductService productService;
	@Autowired
	ApplyService applyService;
	@Value("#{settings['image.showpath']}")
	public String imageShowPath;
	
	@RequestMapping(value = "center", method = RequestMethod.GET)
	public String AuthedList(@RequestParam String id, Model model,HttpServletRequest request) {
		////得到商家的实体
		Businessmanagement business_model = businessService.get(id);
		model.addAttribute("IsLogo", (business_model.getLogo() == null || ("".equals(business_model.getLogo())) ? "NO" : "YES")); //是否有Logo
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path;
		String imgPath =basePath + "/"+business_model.getLogo();
		business_model.setLogo(imgPath);
		model.addAttribute("BusinessModel", business_model);
		
		////获取商家新闻
		List<?> tmpList = newsService.find("from TbncpQiyeNews where qiyenum = '"+id+"' order by cretime DESC");
		tmpList = tmpList.subList(0, tmpList.size() > 5 ? 5 : tmpList.size());
//		for(int i = 0; i < tmpList.size();i++)
//		{
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
//			tmpTime = sdf.format(((TbncpQiyeNews)tmpList.get(i)).getCretime());
//			((TbncpQiyeNews)tmpList.get(i)).setCretime();
//			tmpTime
//		}
		model.addAttribute("NewsList", tmpList);
		////获取产品信息
		tmpList = productService.find("from TbncpQiyeProduct where qiyenum = '"+id+"' order by cretime DESC");
		tmpList = tmpList.subList(0, tmpList.size() > 5 ? 5 : tmpList.size());
		model.addAttribute("ProductList", tmpList);
		///获取认证了的产品
		tmpList = applyService.find("from Apply where HandleResult = '"+ DictionaryEnum.AUTHED.value() +"' order by ApplyTime DESC");
		tmpList = tmpList.subList(0, tmpList.size() > 5 ? 5 : tmpList.size());
		model.addAttribute("AuthedList", tmpList);
		///获取正在认证中的商家
		tmpList = applyService.find("from Apply where HandleResult = '"+ DictionaryEnum.AUTHING.value() +"' order by ApplyTime DESC");
		tmpList = tmpList.subList(0, tmpList.size() > 5 ? 5 : tmpList.size());
		model.addAttribute("AuthingList", tmpList);
		return "bussiness_center/BusinessCenter";
	}
	
	@RequestMapping(value = "news", method = RequestMethod.GET)
	public String NewsDetails(@RequestParam String id,@RequestParam String newsID, Model model,HttpServletRequest request) {
		////得到商家的实体
		Businessmanagement business_model = businessService.get(id);
		model.addAttribute("IsLogo", (business_model.getLogo() == null || ("".equals(business_model.getLogo())) ? "NO" : "YES")); //是否有Logo
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path;
		String imgPath =basePath + "/"+business_model.getLogo();
		business_model.setLogo(imgPath);
		model.addAttribute("BusinessModel", business_model);
		////获取新闻详情
		TbncpQiyeNews news_model = newsService.get(newsID);
		model.addAttribute("NewsModel", news_model);
		return "bussiness_center/NewsDetails";
	}
	
	@RequestMapping(value = "product", method = RequestMethod.GET)
	public String ProductDetails(@RequestParam String id,@RequestParam String productID, Model model,HttpServletRequest request) {
		////得到商家的实体
		Businessmanagement business_model = businessService.get(id);
		model.addAttribute("IsLogo", (business_model.getLogo() == null || ("".equals(business_model.getLogo())) ? "NO" : "YES")); //是否有Logo
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path;
		String imgPath =basePath + "/"+business_model.getLogo();
		business_model.setLogo(imgPath);
		model.addAttribute("BusinessModel", business_model);
		////获取新闻详情
		TbncpQiyeProduct product_model = productService.get(productID);
		model.addAttribute("ProductModel", product_model);
		return "bussiness_center/ProductDetails";
	}
	
	@RequestMapping(value = "apply", method = RequestMethod.GET)
	public String ApplyDetails(@RequestParam String id,@RequestParam String applyID, Model model,HttpServletRequest request) {
		////得到商家的实体
		Businessmanagement business_model = businessService.get(id);
		model.addAttribute("IsLogo", (business_model.getLogo() == null || ("".equals(business_model.getLogo())) ? "NO" : "YES")); //是否有Logo
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path;
		String imgPath =basePath + "/"+business_model.getLogo();
		business_model.setLogo(imgPath);
		model.addAttribute("BusinessModel", business_model);
		////获取新闻详情
		Apply apply_model = applyService.get(applyID);
		model.addAttribute("ApplyModel", apply_model);
		return "bussiness_center/ApplyDetails";
	}
	
	@RequestMapping(value = "newsList", method = RequestMethod.GET)
	public String newsList(@RequestParam String id, Model model,HttpServletRequest request) {
		////得到商家的实体
		Businessmanagement business_model = businessService.get(id);
		model.addAttribute("IsLogo", (business_model.getLogo() == null || ("".equals(business_model.getLogo())) ? "NO" : "YES")); //是否有Logo
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path;
		String imgPath =basePath + "/"+business_model.getLogo();
		business_model.setLogo(imgPath);
		model.addAttribute("BusinessModel", business_model);
		////获取商家新闻
			List<?> tmpList = newsService.find("from TbncpQiyeNews where qiyenum = '"+id+"' order by cretime DESC");
			model.addAttribute("NewsList", tmpList);
		return "bussiness_center/NewsList";
	}
	
	@RequestMapping(value = "productList", method = RequestMethod.GET)
	public String productList(@RequestParam String id, Model model,HttpServletRequest request) {
		////得到商家的实体
		Businessmanagement business_model = businessService.get(id);
		model.addAttribute("IsLogo", (business_model.getLogo() == null || ("".equals(business_model.getLogo())) ? "NO" : "YES")); //是否有Logo
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path;
		String imgPath =basePath + "/"+business_model.getLogo();
		business_model.setLogo(imgPath);
		model.addAttribute("BusinessModel", business_model);
		////获取产品信息
		List<?> tmpList = productService.find("from TbncpQiyeProduct where qiyenum = '"+id+"' order by cretime DESC");
		model.addAttribute("ProductList", tmpList);
		return "bussiness_center/ProductList";
	}
	
	@RequestMapping(value = "applyList", method = RequestMethod.GET)
	public String applyList(@RequestParam String id,@RequestParam String code, Model model,HttpServletRequest request) {
		////得到商家的实体
		Businessmanagement business_model = businessService.get(id);
		model.addAttribute("IsLogo", (business_model.getLogo() == null || ("".equals(business_model.getLogo())) ? "NO" : "YES")); //是否有Logo
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path;
		String imgPath =basePath + "/"+business_model.getLogo();
		business_model.setLogo(imgPath);
		model.addAttribute("BusinessModel", business_model);

		List<?> tmpList;
		if(code.equals("AUTHED"))
			 tmpList = applyService.find("from Apply where HandleResult = '"+ DictionaryEnum.AUTHED.value() +"'");
		else
			tmpList = applyService.find("from Apply where HandleResult = '"+ DictionaryEnum.AUTHING.value() +"'");
		model.addAttribute("AuthedList", tmpList);
		return "bussiness_center/ApplyList";
	}
}
