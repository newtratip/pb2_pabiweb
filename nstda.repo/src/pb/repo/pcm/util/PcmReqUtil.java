package pb.repo.pcm.util;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import pb.common.constant.CommonConstant;
import pb.common.constant.JsonConstant;
import pb.common.util.CommonDateTimeUtil;
import pb.common.util.CommonUtil;
import pb.repo.common.mybatis.DbConnectionFactory;
import pb.repo.pcm.constant.PcmReqConstant;
import pb.repo.pcm.dao.PcmFunctionDAO;
import pb.repo.pcm.dao.PcmReqDAO;
import pb.repo.pcm.dao.PcmReqDtlDAO;
import pb.repo.pcm.dao.PcmReqReviewerDAO;
import pb.repo.pcm.dao.PcmReqWorkflowDAO;
import pb.repo.pcm.dao.PcmReqWorkflowHistoryDAO;
import pb.repo.pcm.model.PcmReqDtlModel;
import pb.repo.pcm.model.PcmReqModel;
import pb.repo.pcm.model.PcmReqReviewerModel;
import pb.repo.pcm.model.PcmWorkflowHistoryModel;
import pb.repo.pcm.model.PcmWorkflowModel;

public class PcmReqUtil {
	
	private static Logger log = Logger.getLogger(PcmReqUtil.class);
	
	public static JSONObject convertToJSONObject(PcmReqModel model, Boolean showDelBtn) throws Exception {
		if (model == null) {
			return null;
		}
		
		JSONObject jsObj = new JSONObject();
		
		jsObj.put(PcmReqConstant.JFN_ID, model.getId());
		
		jsObj.put(PcmReqConstant.JFN_REQ_BY, model.getReqBy());
		jsObj.put(PcmReqConstant.JFN_REQ_BU, model.getReqBu());
		jsObj.put(PcmReqConstant.JFN_REQ_OU, model.getReqOu());
		
		jsObj.put(PcmReqConstant.JFN_OBJECTIVE_TYPE, model.getObjectiveType());
		jsObj.put(PcmReqConstant.JFN_OBJECTIVE, model.getObjective());
		jsObj.put(PcmReqConstant.JFN_REASON, model.getReason());
		
		jsObj.put(PcmReqConstant.JFN_CURRENCY, model.getCurrency());
		jsObj.put(PcmReqConstant.JFN_CURRENCY_RATE, model.getCurrencyRate());
		
		jsObj.put(PcmReqConstant.JFN_BUDGET_CC, model.getBudgetCc());
		jsObj.put(PcmReqConstant.JFN_STOCK_OU, model.getStockOu());
		
		jsObj.put(PcmReqConstant.JFN_PROTOTYPE, model.getPrototype());
		jsObj.put(PcmReqConstant.JFN_EVENT, model.getEvent());
		
		jsObj.put(PcmReqConstant.JFN_PCM_OU, model.getPcmOu());
		jsObj.put(PcmReqConstant.JFN_LOCATION, model.getLocation());
		
		jsObj.put(PcmReqConstant.JFN_ACROSS_BUDGET, model.getAcrossBudget());
		jsObj.put(PcmReqConstant.JFN_REF_ID, model.getRefId());
		
		jsObj.put(PcmReqConstant.JFN_METHOD, model.getMethod());
		jsObj.put(PcmReqConstant.JFN_METHOD_COND2_RULE, model.getMethodCond2Rule());
		jsObj.put(PcmReqConstant.JFN_METHOD_COND2, model.getMethodCond2());
		jsObj.put(PcmReqConstant.JFN_METHOD_COND2_DTL, model.getMethodCond2Dtl());
		
		jsObj.put(PcmReqConstant.JFN_TOTAL, String.valueOf(model.getTotal()));
		jsObj.put(PcmReqConstant.JFN_WORKFLOW_INS_ID, model.getWorkflowInsId());
		jsObj.put(PcmReqConstant.JFN_DOC_REF, model.getDocRef());
		jsObj.put(PcmReqConstant.JFN_FOLDER_REF, model.getFolderRef());
		jsObj.put(PcmReqConstant.JFN_WAITING_LEVEL, String.valueOf(model.getWaitingLevel()));
		jsObj.put(PcmReqConstant.JFN_STATUS, model.getStatus());
		jsObj.put(PcmReqConstant.JFN_WF_STATUS, model.getWfStatus());
		jsObj.put(PcmReqConstant.JFN_OVER_DUE, model.getOverDue());
		jsObj.put(PcmReqConstant.JFN_REQUESTED_TIME_SHOW, CommonDateTimeUtil.convertToGridDate(model.getCreatedTime()));
		jsObj.put(PcmReqConstant.JFN_CREATED_TIME, model.getCreatedTime());
		jsObj.put(PcmReqConstant.JFN_CREATED_BY, model.getCreatedBy());
		jsObj.put(PcmReqConstant.JFN_UPDATED_TIME, CommonDateTimeUtil.convertToGridDate(model.getUpdatedTime()));
		jsObj.put(PcmReqConstant.JFN_UPDATED_BY, model.getUpdatedBy());
		jsObj.put(PcmReqConstant.JFN_ACTION, getAction(model, showDelBtn));
		
		return jsObj;
	}
	
	private static String getAction(PcmReqModel model, Boolean showDelBtn) {
		StringBuffer action = new StringBuffer();
		
		if (model.getStatus().equals(PcmReqConstant.ST_DRAFT)) {
			action.append(PcmReqConstant.ACTION_EDIT);
			action.append(PcmReqConstant.ACTION_DELETE);
		}
		else {
			action.append(PcmReqConstant.ACTION_SHOW_HISTORY);
			if (model.getStatus().equals(PcmReqConstant.ST_WAITING)) {
				action.append(PcmReqConstant.ACTION_SHOW_DIAGRAM);
			}
		}
		
		if (model.getFolderRef() != null && !model.getFolderRef().trim().equals("")) {
			action.append(PcmReqConstant.ACTION_GOTO_FOLDER);
		}
		
		if (model.getDocRef() != null && !model.getDocRef().trim().equals("")) {
			action.append(PcmReqConstant.ACTION_SHOW_DETAIL);
		}
		
		return action.toString();
	}
	
	public static JSONArray convertToJSONArray(List<PcmReqModel> inList, Boolean showDelBtn) throws Exception {
		JSONArray jsArr = new JSONArray();
		
		for(PcmReqModel model : inList) {
			jsArr.put(convertToJSONObject(model, showDelBtn));
		}
		
		return jsArr;
	}
	
	public static String jsonSuccess(List<PcmReqModel> list, Boolean showDelBtn) throws Exception {
		
		Long total = list.size() > 0 ? list.get(0).getTotalRowCount() : 0;

		JSONObject jsonObj = new JSONObject();

		jsonObj.put(JsonConstant.SUCCESS,  true);
		jsonObj.put(JsonConstant.TOTAL,  total);
		jsonObj.put(JsonConstant.DATA, convertToJSONArray(list, showDelBtn));
		
		return jsonObj.toString();
	}
	
	public static String jsonSuccess(List<PcmReqModel> list, List<PcmReqDtlModel> dtlList) throws Exception {
		
		Long total = list.size() > 0 ? list.get(0).getTotalRowCount() : 0;

		JSONObject jsonObj = new JSONObject();

		jsonObj.put(JsonConstant.SUCCESS,  true);
		jsonObj.put(JsonConstant.TOTAL,  total);
		jsonObj.put(JsonConstant.DATA, convertToJSONArray(list, false));
		
		JSONObject jsonObjDtl = new JSONObject();
		for(PcmReqDtlModel model : dtlList) {
			jsonObjDtl.put(model.getDescription(), model.getQuantity());
		}
		jsonObj.put("dtls", jsonObjDtl);
		
		return jsonObj.toString();
	}

	public static String getMessage(String key, Locale locale) {
		return CommonUtil.getMessage(CommonConstant.MODULE_PCM, key, locale);
	}
	
	public static String jsonReportSuccess(List<Map<String,Object>> list) throws Exception {

		Map<String,Object> obj = new HashMap<String,Object>();
	
		obj.put(JsonConstant.SUCCESS,  true);
		obj.put(JsonConstant.TOTAL,  0);
		obj.put(JsonConstant.DATA, list);
		
		return com.alibaba.fastjson.JSONObject.toJSONString(obj);
	}
	
	public static Map<String, Object> dateParameter(String dateFrom, String dateTo, Map<String, Object> params) throws Exception {
		StringBuffer criteria = new StringBuffer();
		if ((dateFrom==null || dateFrom.equalsIgnoreCase("")) && (dateTo==null || dateTo.equalsIgnoreCase(""))) {
			criteria.append("ทั้งหมด");
			params.put("s", "00/00/0000");
			params.put("f", "99/99/9999");
		} else {
			criteria.append("วันที่ : ");
			if(dateFrom==null || dateFrom.equalsIgnoreCase("")) {
				criteria.append("		");
				params.put("s", "00/00/0000");
			}else {
				criteria.append(dateFrom);
				params.put("s", dateFrom);
			}
			criteria.append(" ถึง : ");
	
			if(dateTo==null || dateTo.equalsIgnoreCase("")) {
				criteria.append("		");
				params.put("f", "99/99/9999");
			}else {
				criteria.append(dateTo);
				params.put("f", dateTo);
			}
		}
		params.put("criteria", criteria);
		return params;
	}
	
	public static StringBuffer appenCriteria(StringBuffer sb, String value, String label) throws Exception {
		//log.info("value : " + value);
		if(value!=null && !value.equalsIgnoreCase("")) {
			if(sb.length()>0) sb.append("		");
			sb.append(label + value);
		}
		//log.info("params : " + sb.toString());
		return sb;
		
	}

}
