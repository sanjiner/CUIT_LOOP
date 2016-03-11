package edu.cuit.module.sys.service.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.common.util.Page;
import edu.cuit.module.sys.dao.impl.TbcuitmoonModuleDaoImpl;
import edu.cuit.module.sys.entity.TbcuitmoonModule;
import edu.cuit.module.sys.service.TbcuitmoonModuleService;

@Service
public class TbcuitmoonModuleServiceImpl extends
		BaseServiceImpl<TbcuitmoonModule, String> implements TbcuitmoonModuleService {

	private TbcuitmoonModuleDaoImpl tbcuitmoonModuleDao;

	@Autowired
	private void setTbcuitmoonModuleDao(TbcuitmoonModuleDaoImpl tbcuitmoonModuleDao) {
		this.tbcuitmoonModuleDao = tbcuitmoonModuleDao;
		setBaseDao(tbcuitmoonModuleDao);
	}

	@Override
	public Page listChildModuleByOrderNum(String parentId,String search, Integer requsetPageNo, int pageCounSize) {
		String hql;
		if(StringUtils.hasLength(search)){
			hql = "from TbcuitmoonModule as m where m.cuitMoonParentModuleId='"+ parentId +"' and m.cuitMoonModuleName like '%"+search+"%' order by m.cuitMoonModuleOrderNum";
		}else{
			hql = "from TbcuitmoonModule as m where m.cuitMoonParentModuleId='"+ parentId +"' order by m.cuitMoonModuleOrderNum";
		}
		
		return getPage(hql, requsetPageNo-1, pageCounSize);
	}


	@Override
	public List<?> getTopLevelModule() {
		String hql = "from TbcuitmoonModule as m where m.cuitMoonParentModuleId='0'";
		List<?> list = find(hql) ;
		return list;
	}

	@Override
	public List<?> getByParentId(String parentId) {
		String hql = "from TbcuitmoonModule as m where m.cuitMoonParentModuleId=? order by m.cuitMoonModuleOrderNum";
		
		return find(hql, parentId);
	}

	@Override
	public List<?> findWithChild() {
		String hql = "from TbcuitmoonModule as m where m.cuitMoonParentModuleId=? and m.cuitMoonModuleStatus=1 order by m.cuitMoonModuleOrderNum";
		List<?> parentModuleList = find(hql, new String("0"));
		for(Object obj : parentModuleList){
			TbcuitmoonModule parentModule = (TbcuitmoonModule)obj;
			String parentId = parentModule.getCuitMoonModuleId();
			List<?> childModuList = find(hql, parentId);
			parentModule.setChildModules(new HashSet(childModuList));
		}
		return parentModuleList;
	}
	
	@Override
	public List<?> findWithChildList() {
		String hql = "from TbcuitmoonModule as m where m.cuitMoonParentModuleId=? and m.cuitMoonModuleStatus=1 order by m.cuitMoonModuleOrderNum";
		List<?> parentModuleList = find(hql, new String("0"));
		for(Object obj : parentModuleList){
			TbcuitmoonModule parentModule = (TbcuitmoonModule)obj;
			String parentId = parentModule.getCuitMoonModuleId();
			List<?> childModuList = find(hql, parentId);
			parentModule.setChildModulesList((List<TbcuitmoonModule>)childModuList);
		}
		return parentModuleList;
	}

	@Override
	public List<?> findWithChildListWithoutState() {
		String hql = "from TbcuitmoonModule as m where m.cuitMoonParentModuleId=? order by m.cuitMoonModuleOrderNum";
		List<?> parentModuleList = find(hql, new String("0"));
		for(Object obj : parentModuleList){
			TbcuitmoonModule parentModule = (TbcuitmoonModule)obj;
			String parentId = parentModule.getCuitMoonModuleId();
			List<?> childModuList = find(hql, parentId);
			parentModule.setChildModulesList((List<TbcuitmoonModule>)childModuList);
		}
		return parentModuleList;
	}

	@Override
	public boolean sort(TbcuitmoonModule module, String type) {
		boolean result = false;
		if(module != null){
			String hql = null;
			String hql2 = null;
			if(type.equals("asc")){
				hql2 = "select max(module.cuitMoonModuleOrderNum) from TbcuitmoonModule as module where module.cuitMoonParentModuleId='"+module.getCuitMoonParentModuleId()+"' and module.cuitMoonModuleOrderNum<"+module.getCuitMoonModuleOrderNum();
			}else if(type.equals("desc")){
				hql2 = "select min(module.cuitMoonModuleOrderNum) from TbcuitmoonModule as module where module.cuitMoonParentModuleId='"+module.getCuitMoonParentModuleId()+"' and module.cuitMoonModuleOrderNum>"+module.getCuitMoonModuleOrderNum();
			}
			if(hql2 != null){
				List<?> list2 = find(hql2);
				if(list2.get(0) != null){
					int orderNum =  Integer.valueOf(list2.get(0).toString());
					hql = "from TbcuitmoonModule as m where m.cuitMoonParentModuleId='"+module.getCuitMoonParentModuleId()+"' and m.cuitMoonModuleOrderNum="+orderNum;
				}
			}
			if(hql != null){
				List<?> list = this.getListForPage(hql, 0, 1);
				TbcuitmoonModule module2 = (TbcuitmoonModule)list.get(0);
				if(module2 != null){
					int temp = module.getCuitMoonModuleOrderNum();
					module.setCuitMoonModuleOrderNum(module2.getCuitMoonModuleOrderNum());
					module2.setCuitMoonModuleOrderNum(temp);
					
					update(module);
					update(module);
					flush();
					clear();
					result = true;
				}
			}
		}
		return result;
	}

	@Override
	public int getMaxOrderNum(String cuitMoonParentModuleId) {
		int orderNum = -1;
		if((cuitMoonParentModuleId != null) && cuitMoonParentModuleId.length()>0 ){
			String hql = "select max(module.cuitMoonModuleOrderNum) from TbcuitmoonModule as module where module.cuitMoonParentModuleId=?";
			List<?> list = find(hql, cuitMoonParentModuleId);
			if(list.get(0) != null){
				orderNum = Integer.valueOf(list.get(0).toString());
			}
		}
		return orderNum;
	}
	

}
