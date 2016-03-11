package edu.cuit.module.authc.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.cuit.common.util.DateFormat;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.auchc.entity.Bearinginfo;
import edu.cuit.module.auchc.entity.Climatopeinfo;
import edu.cuit.module.authc.service.BearinginfoService;
import edu.cuit.module.authc.service.ClimatopeinfoService;
import edu.cuit.module.infom.entity.Weatherstationinfo;
import edu.cuit.module.infom.service.WeatherstationinfoService;
import edu.cuit.module.model.entity.Elementmodel;
import edu.cuit.module.model.service.ElementmodelService;

@Controller
@RequestMapping("climate")
public class ClimateController extends BaseController{
	
	@Autowired
	BearinginfoService bearinginfoService;
	@Autowired
	ClimatopeinfoService climatopeinfoService;
	@Autowired
	WeatherstationinfoService weatherstationinfoService;
	@Autowired
	ElementmodelService elementmodelService;
	 
	@RequestMapping(value="add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addInfo(HttpServletRequest request,HttpServletResponse response){
		String qId = request.getParameter("qid");
		String flag = "";
		try {
			delClimateInfo(qId);
			List<String> list = getBearInfo(qId);
			addclimatopeInfo(qId,list);
			flag = "T";
		} catch (Exception e) {
			flag = "F";
		}		
		Map<String,Object> map = new HashMap<String,Object>();          
        map.put("success", flag); 
        return map; 
	}
	
	//删除已存在的生育期信息
	private void delClimateInfo(String qId){
		try {
			String hql = "from Climatopeinfo as cmodel where cmodel.qualityIdentificationNum='"+qId+"'";		
			List<?> list = climatopeinfoService.find(hql);
			if(list.size()>0)
			{
				String delHql = "delete from Climatopeinfo as cmodel where cmodel.qualityIdentificationNum='"+qId+"'";
				climatopeinfoService.bulkUpdate(delHql);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}	
	}
	
	//查询生育期信息
	private List<String> getBearInfo(String qId){
		List<String> list = new ArrayList<String>();
		String startTime = "";  //开始采集时间
		String endTime = "";    //结束采集时间
		String stationId = "";  //气象站号
		String elements = "";   //气象要素
		String hql = "from Bearinginfo as bmodel where bmodel.qualityIdentificationNum='"+qId+"' order by bmodel.startCollectionTime";
		List<?> blist = bearinginfoService.find(hql);
		if(blist.size()>0)
		{
			Date date1 = ((Bearinginfo)(blist.get(0))).getStartCollectionTime(); 
			startTime = DateFormat.getStrTime(date1, 4);
			Date date2 = ((Bearinginfo)(blist.get(blist.size()-1))).getEndCollectionTime();
			endTime = DateFormat.getStrTime(date2, 4);
			stationId = ((Bearinginfo)(blist.get(0))).getStationId();
			for(int i=0; i<blist.size();i++)
			{
				elements += ((Bearinginfo)(blist.get(i))).getMeteIndicators(); 
			}
			elements = delElement(elements);
		}
		list.add(startTime);
		list.add(endTime);
		list.add(stationId);
		list.add(elements);
		return list;
	}
	
	//去掉重复的要素
	private String delElement(String str){
		String s = "";
		String[] Info = str.split("\\|"); 
		for(int i=0; i<Info.length;i++)
		{
			for(int j=i+1; j<Info.length;j++)
			{
				if(Info[i].equals(Info[j]))
				{
					Info[j] = "no";
				}
			}
			if(Info[i] != "no")
			{
				s = s + Info[i] + "|";
			}
		}
		return s;
	}
	
	//添加气候环境信息
	private void addclimatopeInfo(String qId, List<String> list){
		if(list.size()==4)
		{
			String startTime = list.get(0);
			String endTime = list.get(1);
			String stationId = list.get(2);
			stationId = getStationId(stationId);
			String elements = list.get(3);
			String[] eInfo = elements.split("\\|");
			for(int i=0; i<eInfo.length;i++)
			{
				Elementmodel emodel = elementmodelService.get(eInfo[i]);
				String ename = emodel.getElementName();
				String symbol = emodel.getRemark();
				String unit = emodel.getUnit();
				Map<String,Object> map = getElementData(symbol,stationId,startTime,endTime);
				String eValue = map.get("value").toString();
				String eTime = map.get("time").toString();	
				String climatopeInfoNo = UUID.randomUUID().toString().replace("-", "");
				String years = startTime + "至" +endTime;
				
				Climatopeinfo cmodel = new Climatopeinfo();
				cmodel.setClimatopeInfoNo(climatopeInfoNo);
				cmodel.setMeteorologicalfactor(ename);
				cmodel.setSymbol(symbol);
				cmodel.setUnit(unit);
				cmodel.setAvgValues(eValue);
				cmodel.setMonths(eTime);
				cmodel.setYears(years);
				cmodel.setQualityIdentificationNum(qId);
				climatopeinfoService.save(cmodel);
			}
		}
	}
	
	//解析气象站
	private String getStationId(String id){
		String name = "";
		Weatherstationinfo wmodel = weatherstationinfoService.get(id);
		name = wmodel.getWeatherStationId();
		return name;
	} 	
	
	//通过接口获取气象要素信息
	private Map<String,Object> getElementData(String element, String stationId, String startTime, String endTime){
		Map<String, Object> map = new HashMap<String, Object>();
		String eValue = "";
		String eTime = "";
		String urls = "{%22con%22:%22" + element + ","
				+ stationId + "," + startTime
				+ "," + endTime + "%22,%22type%22:%22vw_ele_days%22}";
		String url = "http://d.scnjw.gov.cn/MeteoInterface/MeteoInterface?user=csd&psd=123789456&route="
				+ urls;
		String result = getURLData(url);
		if (result.length() > 0) {
			result = result
					.substring(result.indexOf("</script>") + 9, result.length())
					.replaceAll("\n", "").trim();
			String[] sets = result.split("\\,");// 得到所有的元素
			for (int i = sets.length-1; i >=0; i--) {
				String[] tmp = sets[i].split("\\|");
				if(i==0)
				{
					if(tmp[2].equals("NoData"))
					{
						eValue = eValue + "0";
					}else{
						eValue = eValue + tmp[2];
					}								
					eTime = eTime + "'" + tmp[1] + "'";
				}else{
					if(tmp[2].equals("NoData"))
					{
						eValue = eValue + "0" + ",";
					}else{
						eValue = eValue + tmp[2] + ",";
					}	
					eTime = eTime + "'" + tmp[1] + "'" + ",";
				}				
			}
		}
		map.put("value", eValue);
		map.put("time", eTime);
		return map;
	}
	
	//加载URL获取数据
	private String getURLData(String urlStr){
		URL url = null;
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer();
		try {
			url = new URL(urlStr);
			in = new BufferedReader(new InputStreamReader(url.openStream(),
					"UTF-8"));
			String str = null;
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
		} catch (Exception ex) {

		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
		}
		String result = sb.toString();
		return result;
	}	

}
