package edu.cuit.module.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import edu.cuit.common.service.impl.BaseServiceImpl;
import edu.cuit.common.util.Page;
import edu.cuit.module.sys.dao.impl.TbcuitmoonDictionaryDaoImpl;
import edu.cuit.module.sys.entity.TbcuitmoonDictionary;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;

@Service
public class TbcuitmoonDictionaryServiceImpl extends
		BaseServiceImpl<TbcuitmoonDictionary, String> implements
		TbcuitmoonDictionaryService {
	private TbcuitmoonDictionaryDaoImpl tbcuitmoonDictionaryDao;

	@Autowired
	private void setTbcuitmoonDictionaryDao(
			TbcuitmoonDictionaryDaoImpl tbcuitmoonDictionaryDao) {
		this.tbcuitmoonDictionaryDao = tbcuitmoonDictionaryDao;
		setBaseDao(tbcuitmoonDictionaryDao);
	}

	@Override
	public List<?> dictionaryList(String cuitMoon_ParentDictionaryCode) {
		String hql = "from TbcuitmoonDictionary as t where t.cuitMoonParentDictionaryCode=?";
		List<?> list = tbcuitmoonDictionaryDao.find(hql,
				cuitMoon_ParentDictionaryCode);
		return list;
	}

	@Override
	public String getDicNameByCode(long code) {
		String name = "";
		String hql = "from TbcuitmoonDictionary as t where t.cuitMoonDictionaryCode=?";
		List<?> list = tbcuitmoonDictionaryDao.find(hql, code);
		if (list.size() > 0) {
			name = ((TbcuitmoonDictionary) list.get(0))
					.getCuitMoonDictionaryName();
		}
		return name;
	}

	@Override
	public String getDicNameByCode(String code) {
		String name = "未知";
		if (code != null) {
			try{
			name = getDicNameByCode(Long.parseLong(code.trim()));
			}catch(Exception ex){
				return name;
			}
		}
		return name;
	}

	@Override
	public String getCodeByName(String name) {
		String code = "";
		String hql = "from TbcuitmoonDictionary as t where t.cuitMoonDictionaryName=?";
		List<?> list = tbcuitmoonDictionaryDao.find(hql, name);
		if (list.size() > 0) {
			code = ((TbcuitmoonDictionary) list.get(0))
					.getCuitMoonDictionaryCode()+"";
		}
		return code;
	}

	@Override
	public String getRemarkByCode(long code) {
		String name = "";
		String hql = "from TbcuitmoonDictionary as t where t.cuitMoonDictionaryCode=?";
		List<?> list = tbcuitmoonDictionaryDao.find(hql, code);
		if (list.size() > 0) {
			name = ((TbcuitmoonDictionary) list.get(0))
					.getCuitMoonDictionaryRemarks();
		}
		return name;
	}

	@Override
	public List<?> findByParentCode(long code) {
		String hql = "from TbcuitmoonDictionary as dic where dic.cuitMoonParentDictionaryCode=? order by dic.cuitMoonDictionaryOrderNum";
		List<?> list = tbcuitmoonDictionaryDao.find(hql, code);
		return list;
	}

	@Override
	public Page getByParentId(Long parentCode, String condition,
			Integer pageNo, int pageCounSize) {
		String hql= "";
		if(StringUtils.hasLength(condition)){
			hql= "from TbcuitmoonDictionary as dic where dic.cuitMoonDictionaryName='"+condition+"' order by dic.cuitMoonDictionaryOrderNum";
		}else{
			hql= "from TbcuitmoonDictionary as dic where dic.cuitMoonParentDictionaryCode='"+parentCode+"' order by dic.cuitMoonDictionaryOrderNum";
		}
		
		return getPage(hql, pageNo-1, pageCounSize);
		
	}

	@Override
	public boolean hasThisCode(Long dicCode) {
		String hql = "from TbcuitmoonDictionary as dic where dic.cuitMoonDictionaryCode=?";
		List<?> areaList = find(hql, dicCode);
		if(areaList.size() == 0){
			return false;
		}
		return true;
	}

	@Override
	public boolean hasThisCodeNotMe(String dicId,Long dicCode) {
		String hql = "from TbcuitmoonDictionary as dic where dic.cuitMoonDictionaryId<>? and dic.cuitMoonDictionaryCode=?";
		List<?> areaList = find(hql,dicId, dicCode);
		if(areaList.size() == 0){
			return false;
		}
		return true;
	}
}
