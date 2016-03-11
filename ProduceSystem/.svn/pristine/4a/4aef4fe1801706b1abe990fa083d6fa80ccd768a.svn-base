package edu.cuit.module.authc.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import edu.cuit.common.util.DateFormat;
import edu.cuit.common.util.Page;
import edu.cuit.common.web.controller.BaseController;
import edu.cuit.module.auchc.entity.Apply;
import edu.cuit.module.auchc.entity.Bearinginfo;
import edu.cuit.module.auchc.entity.ClimateQualitycertificationExperts;
import edu.cuit.module.auchc.entity.Expertsgrade;
import edu.cuit.module.auchc.entity.MemberGroup;
import edu.cuit.module.auchc.entity.QualityIdentification;
import edu.cuit.module.authc.service.ApplyService;
import edu.cuit.module.authc.service.BearinginfoService;
import edu.cuit.module.authc.service.ClimateQualitycertificationExpertsService;
import edu.cuit.module.authc.service.ExpertsgradeService;
import edu.cuit.module.authc.service.MemberGroupService;
import edu.cuit.module.authc.service.QualityIdentificationService;
import edu.cuit.module.infom.service.WeatherstationinfoService;
import edu.cuit.module.model.entity.Elementmodel;
import edu.cuit.module.model.entity.Growthelement;
import edu.cuit.module.model.entity.Growthperiod;
import edu.cuit.module.model.entity.Modelformulate;
import edu.cuit.module.model.entity.Productinfomation;
import edu.cuit.module.model.service.ElementmodelService;
import edu.cuit.module.model.service.GrowthelementService;
import edu.cuit.module.model.service.GrowthperiodService;
import edu.cuit.module.model.service.ModelformulateService;
import edu.cuit.module.model.service.ProductinfomationService;
import edu.cuit.module.sys.entity.TbcuitmoonUser;
import edu.cuit.module.sys.service.TbcuitmoonDictionaryService;

@Controller
@RequestMapping("expert_grade")
public class ExpertGradeController extends BaseController {
	@Value("#{settings['page.pageNo']}")
	private int pageNo;
	@Value("#{settings['page.pageCountSize']}")
	private int pageCounSize;
	@Autowired
	private ApplyService applyService;
	@Autowired
	private TbcuitmoonDictionaryService dicService;
	@Autowired
	private ClimateQualitycertificationExpertsService cExpertsService;
	@Autowired
	private MemberGroupService groupService;
	@Autowired
	BearinginfoService bearService;
	@Autowired
	QualityIdentificationService qulityService;
	@Autowired
	ElementmodelService elementService;
	@Autowired
	WeatherstationinfoService stationService;
	@Autowired
	ExpertsgradeService gradeService;
	@Autowired
	ModelformulateService mfService;
	@Autowired
	ProductinfomationService pfSevice;
	@Autowired
	GrowthperiodService gpService;
	@Autowired
	GrowthelementService geService;
	@Autowired
	ElementmodelService emService;

	@RequestMapping("list")
	public String list(@RequestParam(required = false) Integer requsetPageNo,
			Model model, HttpSession session, String key)
			throws UnsupportedEncodingException {
		Page page;
		if (requsetPageNo == null) {
			requsetPageNo = pageNo;
		}
		String hql = "from Apply where productScoreStatus = '110012' or productScoreStatus = '110013' order by applyTime desc";
		if (key != null && key != "") {
			key = new String(key.getBytes("ISO-8859-1"), "UTF8");
			hql += " and productName like '%" + key + "%'";
		}
		page = applyService.getPage(hql, pageNo - 1, pageCounSize);
		model.addAttribute("applyPage", page);

		/*
		 * while (page.getPageList().iterator().hasNext()) { Apply apply =
		 * (Apply) page.getPageList().iterator().next(); List<?> cExperts =
		 * cExpertsService
		 * .find("from ClimateQualitycertificationExperts where applyBh = ?",
		 * apply.getApplyBh()); if (cExperts.size() > 0) { List<MemberGroup>
		 * members = (List<MemberGroup>) groupService
		 * .find("from MemberGroup where expertsNum = ?"
		 * ,((ClimateQualitycertificationExperts
		 * )cExperts.get(0)).getExpertsNum()); boolean flag = false; for
		 * (MemberGroup member : members) { TbcuitmoonUser user =
		 * getLoginUser(session); if (user != null && member.getMember().equals(
		 * user.getCuitMoonUserId())) { flag = true; break; } } if (!flag) {
		 * page.getPageList().remove(apply); page.setAllSize(page.getAllSize() -
		 * 1); } } else { page.getPageList().remove(apply);
		 * page.setAllSize(page.getAllSize() - 1); } }
		 */
		for (Apply apply : (List<Apply>) page.getPageList()) {
			apply.setHandleDescription(dicService.getDicNameByCode(apply
					.getProductScoreStatus().trim()));
		}
		return "expert_grade/list";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("grade")
	private String grade(String applyBh, Model model, String mId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		QualityIdentification quality = null;
		String qualityId = "";
		List<QualityIdentification> q_list = (List<QualityIdentification>) qulityService
				.find("from QualityIdentification where applyBh = ?", applyBh);
		if (q_list.size() > 0) {
			quality = q_list.get(0);
			qualityId = quality.getQualityIdentificationNum();
		}

		// 获取模型内预设的最佳生长条件
		List<Growthperiod> g_list = null;
		Apply apply = applyService.get(applyBh);
		String type = apply.getCommodityType();
		String typeId = "";
		List<Productinfomation> p_list = (List<Productinfomation>) pfSevice
				.find("from Productinfomation where productNumber = ?", type);
		if (p_list.size() > 0) {
			typeId = p_list.get(0).getProductNumber();
		}
		// 该类别对应的模型
		List<Modelformulate> m_list = (List<Modelformulate>) mfService.find(
				"from Modelformulate where productNumber = ?", typeId);
		model.addAttribute("Modules", m_list);
		if (mId == null) {
			if (m_list.size() > 0) {
				Modelformulate m = m_list.get(0);
				// 获取对应类别下的所有的生育期信息
				g_list = (List<Growthperiod>) gpService.find(
						"from Growthperiod where produceApproModelId = ?",
						m.getProductApproModelId());
				model.addAttribute("g_list", g_list);
			}
		} else {
			g_list = (List<Growthperiod>) gpService.find(
					"from Growthperiod where produceApproModelId = ?", mId);
			model.addAttribute("g_list", g_list);
			model.addAttribute("mId", mId);
		}
		// 获取生育期信息
		List<Bearinginfo> b_list = (List<Bearinginfo>) bearService.find(
				"from Bearinginfo where qualityIdentificationNum = ?",
				quality.getQualityIdentificationNum());
		for (int i = 0; i < b_list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", b_list.get(i).getCropGrowthPeriod());
			map.put("start", b_list.get(i).getStartCollectionTime());
			map.put("end", b_list.get(i).getEndCollectionTime());
			map.put("id", b_list.get(i).getBearingInfoId());
			String[] elements = b_list.get(i).getMeteIndicators().split("\\|");
			Growthperiod grp = null;
			if (g_list != null) {
				for (Growthperiod grow : g_list) {
					if (grow.getGrowthName().equals(
							b_list.get(i).getCropGrowthPeriod())) {
						// 如果在预设的生育期信息列表中查找到了跟当前生育期名称相同的项
						grp = grow;
						break;
					}
				}
			}
			List<Map<String, String>> models = new ArrayList<Map<String, String>>();
			for (String str : elements) {
				if (!str.equals("")) {
					/*
					 * <c:forEach items="${g_list}" var = "grow"><!--
					 * 循环生育期，比较生育期名称 --> <script> </script> <c:if
					 * test="${grow.growthName eq item.name }"> <script>
					 * </script> <c:forEach items="${grow.growthElemList}" var =
					 * "input"><!-- 循环要素值，比较要素名称 --> <script> </script> <c:if
					 * test="${input.growthName eq element.name }">
					 * ${input.elementValue } </c:if> </c:forEach> </c:if>
					 * </c:forEach>
					 */
					final Map<String, String> item = new HashMap<String, String>();

					Elementmodel element = elementService.get(str);
					if (grp != null) {
						// 获取预设生育期下的所有要素
						List<Growthelement> ele_list = (List<Growthelement>) geService
								.find("from Growthelement where growthId = ?",
										grp.getGrowthId());
						// 循环预设要素信息中的数据，判断当前要素名称是否存在与预设要素中名称相等的项
						for (Growthelement ge : ele_list) {
							Elementmodel em = emService.get(ge
									.getElementNumber());
							if (em.getElementName().equals(
									element.getElementName())) {
								item.put("preInstall", ge.getElementValue());
							}
						}
					}
					item.put("name", element.getElementName());
					item.put("id", str);
					models.add(item);
					if (b_list.get(i).getStationId() != null && element != null) {
						String urls = "{%22con%22:%22"
								+ element.getRemark()
								+ ","
								+ stationService.get(
										b_list.get(i).getStationId().trim())
										.getWeatherStationId()
								+ ","
								+ DateFormat.getStrTime(b_list.get(i)
										.getStartCollectionTime(), 4)
								+ ","
								+ DateFormat.getStrTime(b_list.get(i)
										.getEndCollectionTime(), 4)
								+ "%22,%22type%22:%22vw_ele_days%22}";
						final String url = "http://d.scnjw.gov.cn/MeteoInterface/MeteoInterface?user=csd&psd=123789456&route="
								+ urls;
						System.out.println(url);
						item.put("average",
								getURLContent(url) + " " + element.getUnit());
					}
				}
			}
			map.put("elements", models);
			list.add(map);
		}
		model.addAttribute("qualityId", qualityId);
		model.addAttribute("applyBh", applyBh);
		model.addAttribute("list", list);
		return "expert_grade/grade";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("details")
	private String details(String applyBh, Model model) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		QualityIdentification quality = null;
		List<QualityIdentification> q_list = (List<QualityIdentification>) qulityService
				.find("from QualityIdentification where applyBh = ?", applyBh);
		if (q_list.size() > 0) {
			quality = q_list.get(0);
		}
		// 获取生育期信息
		List<Bearinginfo> b_list = (List<Bearinginfo>) bearService.find(
				"from Bearinginfo where qualityIdentificationNum = ?",
				quality.getQualityIdentificationNum());
		for (int i = 0; i < b_list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", b_list.get(i).getCropGrowthPeriod());
			map.put("start", b_list.get(i).getStartCollectionTime());
			map.put("end", b_list.get(i).getEndCollectionTime());
			map.put("id", b_list.get(i).getBearingInfoId());
			list.add(map);
			List<Expertsgrade> grades = (List<Expertsgrade>) gradeService.find(
					"from Expertsgrade where bearingInfoId = ?", b_list.get(i)
							.getBearingInfoId());
			map.put("grades", grades);
		}
		model.addAttribute("applyBh", applyBh);
		model.addAttribute("list", list);
		return "expert_grade/details";
	}

	@ResponseBody
	@RequestMapping(value = "grade", method = RequestMethod.POST)
	private Map<String, String> grade(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String applyId = WebUtils.findParameterValue(request, "applyBh");
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(WebUtils.findParameterValue(
					request, "json"));
			for (int i = 0; i < node.size(); i++) {
				String infoId = node.get(i).get("infoId").asText();
				String name = node.get(i).get("name").asText();
				String time = node.get(i).get("time").asText();
				for (int j = 0; j < node.get(i).get("scores").size(); j++) {
					String element = node.get(i).get("scores").get(j)
							.get("element").asText();
					String optimum = node.get(i).get("scores").get(j)
							.get("optimum").asText();
					String real = node.get(i).get("scores").get(j).get("real")
							.asText();
					String level = node.get(i).get("scores").get(j)
							.get("level").asText();
					float score = (float) node.get(i).get("scores").get(j)
							.get("score").asDouble();
					Expertsgrade grade = new Expertsgrade(UUID.randomUUID()
							.toString().replace("-", ""));
					grade.setBearingInfoId(infoId);
					grade.setCropGrowthPeriod(name);
					grade.setElement(element);
					grade.setExpertsgradecol(score + "");
					grade.setOptimalRange(optimum);
					grade.setRealCondition(real);
					grade.setGradeLevel(level);
					grade.setGradeValue(score);
					grade.setTimeRange(time);
					gradeService.save(grade);
				}
			}
			Apply apply = applyService.get(applyId);
			apply.setProductScoreStatus("110013");
			applyService.update(apply);
			map.put("success", "true");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", "false");
			return map;
		}
		return map;
	}

	public String getURLContent(String urlStr) {
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
		float sum = 0;
		float average = 0f;
		DecimalFormat df = new DecimalFormat("#0.00");
		if (result.length() > 0) {
			result = result
					.substring(result.indexOf("</script>") + 9, result.length())
					.replaceAll("\n", "").trim();
			String[] sets = result.split(",");// 得到所有的元素
			for (int i = 0; i < sets.length; i++) {
				String[] tmp = sets[i].split("\\|");
				if (!tmp[2].equals("NoData"))
					sum += Float.parseFloat(tmp[2]);
			}
			average = sum / sets.length;
		}
		return df.format(average);
	}
}
