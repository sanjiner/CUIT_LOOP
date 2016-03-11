package edu.cuit.module.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.common.util.Page;
import edu.cuit.module.sys.dao.TbcuitmoonAreaDao;
import edu.cuit.module.sys.entity.TbcuitmoonArea;
import edu.cuit.module.sys.service.TbcuitmoonAreaService;

@Service
public class TbcuitmoonAreaServiceImpl extends BaseServiceImpl<TbcuitmoonArea, String> implements TbcuitmoonAreaService {
	private TbcuitmoonAreaDao tbcuitmoonAreaDao;
	
	@Autowired
	private void setTbcuitmoonArea(TbcuitmoonAreaDao tbcuitmoonAreaDao){
		this.tbcuitmoonAreaDao = tbcuitmoonAreaDao;
		setBaseDao(tbcuitmoonAreaDao);
		
	}

	@Override
	public List<?> getByParentCode(long parentCode) {
		String hql = "from TbcuitmoonArea as area where area.cuitMoonParentAreaCode=? order by area.cuitMoonAreaOrderNum";
		List<?> list = find(hql, parentCode);
		return list;
	}

	@Override
	public Page getByParentId(String parentCode, String condition, int pageNo, int pageCountSize) {
		String hql= "";
		if(StringUtils.hasLength(condition)){
			hql= "from TbcuitmoonArea as area where area.cuitMoonAreaName='"+condition+"' order by area.cuitMoonAreaOrderNum";
		}else{
			hql= "from TbcuitmoonArea as area where area.cuitMoonParentAreaCode='"+parentCode+"' order by area.cuitMoonAreaOrderNum";
		}
		
		return getPage(hql, pageNo-1, pageCountSize);
	}

	@Override
	public boolean isExistByCode(long cuitMoonAreaCode) {
		String hql = "from TbcuitmoonArea as area where area.cuitMoonAreaCode=?";
		List<?> areaList = find(hql, cuitMoonAreaCode);
		if(areaList.size() == 0){
			return false;
		}
		return true;
	}
	
	public String getNameByCode(long code){
		String name = "";
		List<TbcuitmoonArea> list = (List<TbcuitmoonArea>)find("from TbcuitmoonArea where cuitMoonAreaCode = ?", code);
		if (list.size() > 0) {
			name = list.get(0).getCuitMoonAreaName();
		}
		return name;
	}
	public String getNameByCode(String code){
		long code_l = 0L;
		try {
			code_l = Long.parseLong(code);
		} catch (Exception e) {
		}
		String name = "";
		name = getNameByCode(code_l);
		return name;
	}
	public long getCodeByName(String name){
		long code = 0l;
		List<TbcuitmoonArea> list = (List<TbcuitmoonArea>)find("from TbcuitmoonArea where cuitMoonAreaName = ?", name);
		if (list.size() > 0) {
			code = list.get(0).getCuitMoonAreaCode();
		}
		return code;
	}

	@Override
	public boolean isExistByCodeNotMySelf(String areaId, long cuitMoonAreaCode) {
		String hql = "from TbcuitmoonArea as area where area.cuitMoonAreaId<>? and area.cuitMoonAreaCode=?";
		List<?> areaList = find(hql,areaId, cuitMoonAreaCode);
		if(areaList.size() == 0){
			return false;
		}
		return true;
	}

	@Override
	public TbcuitmoonArea getByCode(long cuitMoonAreaId) {
		List<?> list = find("from TbcuitmoonArea where cuitMoonAreaCode=?", cuitMoonAreaId);
		TbcuitmoonArea area = null;
		if (list.size() > 0) {
			area = (TbcuitmoonArea)list.get(0);
		}
		return area;
	}
}
