package edu.cuit.module.infom.web.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.sql.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.cuit.common.util.DateFormat;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.auchc.entity.Apply;
import edu.cuit.module.authc.service.ApplyService;
import edu.cuit.module.infom.service.BusinessmanagementService;

/**
 * 开放的 不用的登陆也可以访问
 * 前台opencms 认证查询
 *
 */

@Controller
@RequestMapping("authcQuery")
public class AuthedQueryController extends BaseController{
	@Autowired
	ApplyService applyService;
	@Autowired
	BusinessmanagementService businessmanagementService;
	
	@RequestMapping(value = "authedList", method = RequestMethod.GET)
	@ResponseBody
	public List<?> AuthedList(Integer pageNo, Integer itemsCount) {
		if(isEmpty(itemsCount)){
			itemsCount = new Integer(5);
		}
		if(isEmpty(pageNo)){
			pageNo = new Integer(1);
		}
		List<?> tmpList = applyService.getListForPage("select new map(apply.applyBh as id, apply.unityName as title, apply.applyTime as applyTime) from Apply as apply order by apply.applyTime DESC", pageNo-1, itemsCount);
		for(Object o : tmpList){
			Map<String, Object> map = (Map<String, Object>) o;
			String dateString = DateFormat.getStrTime((Date)map.get("applyTime"), 4);
			map.put("applyTime", dateString);
		}
		return tmpList;
	}
	
	@RequestMapping(value="authDetail")
	@ResponseBody
	public Map<String, String> authDetail(String applyId){
		Map<String, String> map = new HashMap<String, String>();
		Apply apply = applyService.get(applyId);
		if(!isEmpty(apply)){
			String hql = "select b.campanyNo from Businessmanagement as b where b.userName=?";
			List<?> bIdList = businessmanagementService.find(hql,apply.getApplyPerson());
			if(bIdList.size() > 0){
				map.put("id", bIdList.get(0).toString());
			}else{
				map.put("id", 0+"");
			}
			
			map.put("businessName", apply.getUnityName());
			map.put("addr", apply.getAddress());
			map.put("phone", apply.getContact());
			map.put("produceName", apply.getProductName());
		}
		return map;
	}
	
	/**
	 * 获取分页数量
	 * @param itemsCount
	 * @return
	 */
	@RequestMapping(value="getPageCount")
	@ResponseBody
	public long getPageCount(Integer itemsCount){
		List<?> list = applyService.find("select count(*) from Apply");
		long itemsCountAll = new Long(list.get(0).toString());
		long totalPages = (itemsCountAll-1)/itemsCount+1;
		return totalPages;
		
	}
	
	
	
}
