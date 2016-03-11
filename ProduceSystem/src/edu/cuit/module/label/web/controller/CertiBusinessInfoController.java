package edu.cuit.module.label.web.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.cuit.common.util.DateFormat;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.auchc.entity.Apply;
import edu.cuit.module.authc.service.ApplyService;
import edu.cuit.module.entm.entity.TbncpQiyeNews;
import edu.cuit.module.entm.entity.TbncpQiyeProduct;
import edu.cuit.module.entm.service.TbncpQiyeNewsService;
import edu.cuit.module.entm.service.TbncpQiyeProductService;
import edu.cuit.module.infom.entity.Businessmanagement;
import edu.cuit.module.infom.entity.Businessqualification;
import edu.cuit.module.infom.service.BusinessmanagementService;
import edu.cuit.module.infom.service.BusinessqualificationService;
import edu.cuit.module.origin.entity.Productapply;
import edu.cuit.module.origin.service.ProductapplyService;
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;

@Controller
@RequestMapping("labelscan")
public class CertiBusinessInfoController extends BaseController{
	
	@Autowired
	BusinessmanagementService businessmanagementService;
	@Autowired
	TbcuitmoonDictionaryService tbcuitmoonDictionaryService;
	@Autowired
	TbncpQiyeNewsService tbncpQiyeNewsService;
	@Autowired
	TbncpQiyeProductService tbncpQiyeProductService;
	@Autowired
	ApplyService applyService;
	@Autowired
	ProductapplyService productapplyService;
	@Autowired
	BusinessqualificationService businessqualificationService;
	
	@RequestMapping(value="CertiBusinessInfo",method = RequestMethod.GET)
	public String show(@RequestParam("id") String BusinessId, Model model){	
		//查询商家信息
		String campanyNo = ""; //商家编号
		String campanyName = ""; //企业名称
		String campanyType = ""; //企业类型
		String cantactPerson = ""; //联系人
		String tel = "";  //联系电话
		String email = "";  //邮箱
		String address = ""; //地址
		String businessLogo = "";  //商家logo
		Businessmanagement bmodel = new Businessmanagement();
		String hql = "from Businessmanagement as bmodel where bmodel.userName='" + BusinessId + "'";
		List<?> list = businessmanagementService.find(hql);
		if(list.size() > 0)
		{
			bmodel = (Businessmanagement)list.get(0);
			campanyNo = bmodel.getCampanyNo();
			campanyName = bmodel.getCampanyName();
			campanyType = analyzeCompanyType(bmodel.getCompanyType().trim());
			cantactPerson = bmodel.getCantactPerson();
			tel = bmodel.getTel();
			email = bmodel.getMailBox();
			address = bmodel.getAddress();
			businessLogo = bmodel.getLogo();
		}
		model.addAttribute("campanyName", campanyName);
		model.addAttribute("campanyType", campanyType);
		model.addAttribute("cantactPerson", cantactPerson);
		model.addAttribute("tel", tel);
		model.addAttribute("email", email);
		model.addAttribute("address", address);
		model.addAttribute("businessLogo", businessLogo);
		model.addAttribute("id", BusinessId);
		//获取企业新闻
		model = getCampanyNews(campanyNo,model);
		//获取企业产品信息
		model = getCampanyProductInfo(campanyNo,model);
		//获取已认证的产品
		model = getCertifiedProduct(BusinessId,model);
		//获取认证中的产品
		model = getCertifingProduct(BusinessId,model);
		//获取相关资质证书
		model = getCertiImages(campanyNo,model);
		System.out.println(campanyNo);
		return "labelscan/CertiBusinessInfo";
	}
	
	@RequestMapping(value="BusinessNews",method = RequestMethod.GET)
	public String showNews(@RequestParam("id") String newsId, Model model){	
		TbncpQiyeNews newsmodel = tbncpQiyeNewsService.get(newsId);
		String newsTitle = newsmodel.getNewstitle();
		Date date = newsmodel.getCretime();
		String newsTime = DateFormat.getStrTime(date, 4);
		String newsContent = newsmodel.getNewscontent();
		
		model.addAttribute("newsTitle", newsTitle);
		model.addAttribute("newsTime", newsTime);
		model.addAttribute("newsContent", newsContent);
		return "labelscan/BusinessNews";
	}
	
	@RequestMapping(value="BusinessProduct",method = RequestMethod.GET)
	public String showProduct(@RequestParam("id") String productId, Model model){	
		TbncpQiyeProduct productmodel = tbncpQiyeProductService.get(productId);
		String productName = productmodel.getProductname();
		Date date = productmodel.getCretime();
		String creTime = DateFormat.getStrTime(date, 4);
		String productImage = productmodel.getProductimg();
		String productContent = productmodel.getProductcontent();
		
		model.addAttribute("productName", productName);
		model.addAttribute("creTime", creTime);
		model.addAttribute("productImage", productImage);
		model.addAttribute("productContent", productContent);
		return "labelscan/BusinessProduct";
	}
	
	//解析企业类型
	private String analyzeCompanyType(String code){
		String name = "";
		String hql = "from TbcuitmoonDictionary as dmodel where dmodel.cuitMoonDictionaryCode='" + code + "'";
		name = ((TbcuitmoonDictionary)tbcuitmoonDictionaryService.find(hql).get(0)).getCuitMoonDictionaryName();
		return name;
	}
	
	//获取企业新闻
	private Model getCampanyNews(String id, Model model){
		String hql = "from TbncpQiyeNews as tqnmodel where tqnmodel.qiyenum='" + id + "'order by tqnmodel.cretime desc";
		List<?> list = tbncpQiyeNewsService.find(hql);
		model.addAttribute("newslist", list);
		return model;
	}
	
	//获取企业产品信息
	private Model getCampanyProductInfo(String id, Model model){
		String hql = "from TbncpQiyeProduct as tqpmodel where tqpmodel.qiyenum='" + id + "'order by tqpmodel.cretime desc";
		List<?> list = tbncpQiyeProductService.find(hql);
		model.addAttribute("productlist", list);
		return model;
	}
	
	//获取已认证的产品
	private Model getCertifiedProduct(String applyPerson, Model model){
		//查询气候品质认证
		String hql1 = "from Apply as a where a.applyPerson='"+applyPerson+"' and a.declareStatus between '100029' and '100030'";
		List<?> alist = applyService.find(hql1);		
		//查询农网认证
		String hql2 = "from Productapply as p where p.applyPerson='"+applyPerson+"' and p.orignStatus='100084'";
		List<?> plist = productapplyService.find(hql2);		
		model.addAttribute("alist1", alist);
		model.addAttribute("plist1", plist);
		return model;
	}
	
	//获取认证中的产品
	private Model getCertifingProduct(String applyPerson, Model model){
		//查询气候品质认证
		String hql1 = "from Apply as a where a.applyPerson='"+applyPerson+"' and a.declareStatus < '100029'";
		List<?> alist = applyService.find(hql1);		
		//查询农网认证
		String hql2 = "from Productapply as p where p.applyPerson='"+applyPerson+"' and p.orignStatus < '100084'";
		List<?> plist = productapplyService.find(hql2);		
		model.addAttribute("alist2", alist);
		model.addAttribute("plist2", plist);		
		return model;
	}
	
	//获取相关资质证书
	private Model getCertiImages(String campanyNo, Model model){
		String hql = "from Businessqualification as bq where bq.companyNo='"+campanyNo+"'";		
		List<?> list = businessqualificationService.find(hql);
		model.addAttribute("certiImages", list);
		return model;
	}
	
}