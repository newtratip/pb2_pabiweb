package pb.repo.exp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import pb.common.constant.CommonConstant;
import pb.common.constant.JsonConstant;
import pb.common.util.CommonDateTimeUtil;
import pb.common.util.CommonUtil;
import pb.repo.exp.constant.ExpBrwConstant;
import pb.repo.exp.model.ExpBrwVoyagerModel;
import pb.repo.exp.model.ExpBrwModel;

public class ExpBrwUtil {
	
	private static Logger log = Logger.getLogger(ExpBrwUtil.class);
	
	public static JSONObject convertToJSONObject(ExpBrwModel model, Boolean showDelBtn) throws Exception {
		if (model == null) {
			return null;
		}
		
		JSONObject jsObj = new JSONObject();
		
		jsObj.put(ExpBrwConstant.JFN_ID, model.getId());
		
		jsObj.put(ExpBrwConstant.JFN_REQ_BY, model.getReqBy());
		jsObj.put(ExpBrwConstant.JFN_REQ_SECTION_ID, model.getReqSectionId());
		
		jsObj.put(ExpBrwConstant.JFN_OBJECTIVE_TYPE, model.getObjectiveType());
		jsObj.put(ExpBrwConstant.JFN_OBJECTIVE_TYPE_NAME, model.getObjectiveTypeName());
		jsObj.put(ExpBrwConstant.JFN_OBJECTIVE, model.getObjective());
		jsObj.put(ExpBrwConstant.JFN_REASON, model.getReason());
		
		jsObj.put(ExpBrwConstant.JFN_BUDGET_CC, model.getBudgetCc());
		jsObj.put(ExpBrwConstant.JFN_BUDGET_CC_NAME, model.getBudgetCcName());
		jsObj.put(ExpBrwConstant.JFN_BUDGET_CC_TYPE, model.getBudgetCcType());
		jsObj.put(ExpBrwConstant.JFN_BUDGET_CC_TYPE_NAME, model.getBudgetCcTypeName());
		
		jsObj.put(ExpBrwConstant.JFN_COST_CONTROL_ID, model.getCostControlId());
		jsObj.put(ExpBrwConstant.JFN_COST_CONTROL, model.getCostControl());
		jsObj.put(ExpBrwConstant.JFN_COST_CONTROL_TYPE_ID, model.getCostControlTypeId());
		jsObj.put(ExpBrwConstant.JFN_COST_CONTROL_FROM, CommonDateTimeUtil.convertToSenchaFieldDateTime(model.getCostControlFrom()));
		jsObj.put(ExpBrwConstant.JFN_COST_CONTROL_TO, CommonDateTimeUtil.convertToSenchaFieldDateTime(model.getCostControlTo()));
		
		jsObj.put(ExpBrwConstant.JFN_BANK_TYPE, model.getBankType());
		jsObj.put(ExpBrwConstant.JFN_BANK, model.getBank());
		
		jsObj.put(ExpBrwConstant.JFN_TOTAL_TYPE, model.getTotalType());
		
		jsObj.put(ExpBrwConstant.JFN_TOTAL, model.getTotal());
		jsObj.put(ExpBrwConstant.JFN_WORKFLOW_INS_ID, model.getWorkflowInsId());
		jsObj.put(ExpBrwConstant.JFN_DOC_REF, model.getDocRef());
		jsObj.put(ExpBrwConstant.JFN_FOLDER_REF, model.getFolderRef());
		jsObj.put(ExpBrwConstant.JFN_WAITING_LEVEL, String.valueOf(model.getWaitingLevel()));
		jsObj.put(ExpBrwConstant.JFN_STATUS, model.getStatus());
		jsObj.put(ExpBrwConstant.JFN_WF_STATUS, model.getWfStatus());
		jsObj.put(ExpBrwConstant.JFN_OVER_DUE, model.getOverDue());
		jsObj.put(ExpBrwConstant.JFN_CREATED_TIME_SHOW, CommonDateTimeUtil.convertToGridDateTime(model.getCreatedTime()));
		jsObj.put(ExpBrwConstant.JFN_CREATED_TIME, CommonDateTimeUtil.convertToSenchaFieldDateTime(model.getCreatedTime()));
		jsObj.put(ExpBrwConstant.JFN_CREATED_BY, model.getCreatedBy());
		jsObj.put(ExpBrwConstant.JFN_UPDATED_TIME, CommonDateTimeUtil.convertToGridDate(model.getUpdatedTime()));
		jsObj.put(ExpBrwConstant.JFN_UPDATED_BY, model.getUpdatedBy());
		jsObj.put(ExpBrwConstant.JFN_ACTION, getAction(model, showDelBtn));
		
		return jsObj;
	}
	
	public static Map<String, Object> convertToMap(ExpBrwModel model, Boolean showDelBtn) {
		if (model == null) {
			return null;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put(ExpBrwConstant.JFN_ID, model.getId());
		
		map.put(ExpBrwConstant.JFN_REQ_BY, model.getReqBy());
		map.put(ExpBrwConstant.JFN_REQ_SECTION_ID, model.getReqSectionId());
		
		map.put(ExpBrwConstant.JFN_OBJECTIVE_TYPE, model.getObjectiveType());
		map.put(ExpBrwConstant.JFN_OBJECTIVE, model.getObjective());
		map.put(ExpBrwConstant.JFN_REASON, model.getReason());
		
		map.put(ExpBrwConstant.JFN_BUDGET_CC, model.getBudgetCc());
		
		map.put(ExpBrwConstant.JFN_COST_CONTROL, model.getCostControl());
		map.put(ExpBrwConstant.JFN_COST_CONTROL_ID, model.getCostControlId());
		map.put(ExpBrwConstant.JFN_COST_CONTROL_TYPE_ID, model.getCostControlTypeId());
		map.put(ExpBrwConstant.JFN_COST_CONTROL_FROM, model.getCostControlFrom());
		map.put(ExpBrwConstant.JFN_COST_CONTROL_TO, model.getCostControlTo());
		
		map.put(ExpBrwConstant.JFN_BANK_TYPE, model.getBankType());
		map.put(ExpBrwConstant.JFN_BANK, model.getBank());
		
		map.put(ExpBrwConstant.JFN_TOTAL_TYPE, model.getTotalType());
		
		map.put(ExpBrwConstant.JFN_TOTAL, model.getTotal());
		map.put(ExpBrwConstant.JFN_WORKFLOW_INS_ID, model.getWorkflowInsId());
		map.put(ExpBrwConstant.JFN_DOC_REF, model.getDocRef());
		map.put(ExpBrwConstant.JFN_FOLDER_REF, model.getFolderRef());
		map.put(ExpBrwConstant.JFN_WAITING_LEVEL, String.valueOf(model.getWaitingLevel()));
		map.put(ExpBrwConstant.JFN_STATUS, model.getStatus());
		map.put(ExpBrwConstant.JFN_WF_STATUS, model.getWfStatus());
		map.put(ExpBrwConstant.JFN_OVER_DUE, model.getOverDue());
		map.put(ExpBrwConstant.JFN_CREATED_TIME_SHOW, CommonDateTimeUtil.convertToGridDateTime(model.getCreatedTime()));
		map.put(ExpBrwConstant.JFN_CREATED_TIME, model.getCreatedTime());
		map.put(ExpBrwConstant.JFN_CREATED_BY, model.getCreatedBy());
		map.put(ExpBrwConstant.JFN_UPDATED_TIME, CommonDateTimeUtil.convertToGridDate(model.getUpdatedTime()));
		map.put(ExpBrwConstant.JFN_UPDATED_BY, model.getUpdatedBy());
		map.put(ExpBrwConstant.JFN_ACTION, getAction(model, showDelBtn));
		
		return map;
	}	
	
	private static String getAction(ExpBrwModel model, Boolean showDelBtn) {
		StringBuffer action = new StringBuffer();
		
		action.append(ExpBrwConstant.ACTION_COPY);
		if (model.getStatus().equals(ExpBrwConstant.ST_DRAFT)) {
			action.append(ExpBrwConstant.ACTION_EDIT);
			action.append(ExpBrwConstant.ACTION_DELETE);
		}
		else {
			action.append(ExpBrwConstant.ACTION_SHOW_HISTORY);
			if (model.getStatus().equals(ExpBrwConstant.ST_WAITING)) {
				action.append(ExpBrwConstant.ACTION_SHOW_DIAGRAM);
			}
		}
		
		if (model.getFolderRef() != null && !model.getFolderRef().trim().equals("")) {
			action.append(ExpBrwConstant.ACTION_GOTO_FOLDER);
		}
		
		if (model.getDocRef() != null && !model.getDocRef().trim().equals("")) {
			action.append(ExpBrwConstant.ACTION_SHOW_DETAIL);
		}
		
		return action.toString();
	}
	
	public static JSONArray convertToJSONArray(List<ExpBrwModel> inList, Boolean showDelBtn) throws Exception {
		JSONArray jsArr = new JSONArray();
		
		for(ExpBrwModel model : inList) {
			jsArr.put(convertToJSONObject(model, showDelBtn));
		}
		
		return jsArr;
	}
	
	public static String jsonSuccess(List<ExpBrwModel> list, Boolean showDelBtn) throws Exception {
		
		Long total = list.size() > 0 ? list.get(0).getTotalRowCount() : 0;

		JSONObject jsonObj = new JSONObject();

		jsonObj.put(JsonConstant.SUCCESS,  true);
		jsonObj.put(JsonConstant.TOTAL,  total);
		jsonObj.put(JsonConstant.DATA, convertToJSONArray(list, showDelBtn));
		
		return jsonObj.toString();
	}
	
	public static String jsonSuccess(List<ExpBrwModel> list, List<ExpBrwVoyagerModel> dtlList) throws Exception {
		
		Long total = list.size() > 0 ? list.get(0).getTotalRowCount() : 0;

		JSONObject jsonObj = new JSONObject();

		jsonObj.put(JsonConstant.SUCCESS,  true);
		jsonObj.put(JsonConstant.TOTAL,  total);
		jsonObj.put(JsonConstant.DATA, convertToJSONArray(list, false));
		
//		JSONObject jsonObjDtl = new JSONObject();
//		for(ExpBrwDtlModel model : dtlList) {
//			jsonObjDtl.put(model.getName(), model.getName());
//		}
//		jsonObj.put("dtls", jsonObjDtl);
		
		return jsonObj.toString();
	}

	public static String getMessage(String key, Locale locale) {
		return CommonUtil.getMessage(CommonConstant.MODULE_EXP, key, locale);
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
