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
import pb.repo.pcm.constant.PcmOrdConstant;
import pb.repo.pcm.dao.PcmFunctionDAO;
import pb.repo.pcm.dao.PcmOrdDAO;
import pb.repo.pcm.dao.PcmOrdDtlDAO;
import pb.repo.pcm.dao.PcmOrdReviewerDAO;
import pb.repo.pcm.dao.PcmOrdWorkflowDAO;
import pb.repo.pcm.dao.PcmOrdWorkflowHistoryDAO;
import pb.repo.pcm.model.PcmOrdDtlModel;
import pb.repo.pcm.model.PcmOrdModel;
import pb.repo.pcm.model.PcmOrdReviewerModel;
import pb.repo.pcm.model.PcmWorkflowHistoryModel;
import pb.repo.pcm.model.PcmWorkflowModel;

public class PcmOrdUtil {
	
	private static Logger log = Logger.getLogger(PcmOrdUtil.class);
	
	public static JSONObject convertToJSONObject(PcmOrdModel model, Boolean showDelBtn) throws Exception {
		if (model == null) {
			return null;
		}
		
		JSONObject jsObj = new JSONObject();
		
		jsObj.put(PcmOrdConstant.JFN_ID, model.getId());
		jsObj.put(PcmOrdConstant.JFN_REQ_TYPE, String.valueOf(model.getReqType()));
		jsObj.put(PcmOrdConstant.JFN_REF_ID, model.getRefId());
		
		jsObj.put(PcmOrdConstant.JFN_PUR_TYPE, model.getPurType());
		
		jsObj.put(PcmOrdConstant.JFN_DOMESTIC, model.getDomestic());
		jsObj.put(PcmOrdConstant.JFN_CURRENCY, model.getCurrency());
		jsObj.put(PcmOrdConstant.JFN_CURRENCY_RATE, model.getCurrencyRate());
		jsObj.put(PcmOrdConstant.JFN_OBJECTIVE, model.getObjective());
		jsObj.put(PcmOrdConstant.JFN_REASON, model.getReason());
		
		jsObj.put(PcmOrdConstant.JFN_REQ_OU, model.getReqOu());
		jsObj.put(PcmOrdConstant.JFN_STOCK_OU, model.getStockOu());
		jsObj.put(PcmOrdConstant.JFN_NEED_DATE, model.getNeedDate());
		jsObj.put(PcmOrdConstant.JFN_TOTAL, String.valueOf(model.getTotal()));
		jsObj.put(PcmOrdConstant.JFN_REMARK, model.getRemark());
		jsObj.put(PcmOrdConstant.JFN_WORKFLOW_ID, model.getWorkflowId());
		jsObj.put(PcmOrdConstant.JFN_WORKFLOW_INS_ID, model.getWorkflowInsId());
		jsObj.put(PcmOrdConstant.JFN_APPROVAL_MATRIX_ID, model.getApprovalMatrixId());
		jsObj.put(PcmOrdConstant.JFN_DOC_REF, model.getDocRef());
		jsObj.put(PcmOrdConstant.JFN_FOLDER_REF, model.getFolderRef());
		jsObj.put(PcmOrdConstant.JFN_WAITING_LEVEL, String.valueOf(model.getWaitingLevel()));
		jsObj.put(PcmOrdConstant.JFN_STATUS, model.getStatus());
		jsObj.put(PcmOrdConstant.JFN_WF_STATUS, model.getWfStatus());
		jsObj.put(PcmOrdConstant.JFN_OVER_DUE, model.getOverDue());
		jsObj.put(PcmOrdConstant.JFN_REQUESTED_TIME, model.getRequestedTime());
		jsObj.put(PcmOrdConstant.JFN_REQUESTED_TIME_SHOW, CommonDateTimeUtil.convertToGridDate(model.getRequestedTime()));
		jsObj.put(PcmOrdConstant.JFN_CREATED_TIME, model.getCreatedTime());
		jsObj.put(PcmOrdConstant.JFN_CREATED_BY, model.getCreatedBy());
		jsObj.put(PcmOrdConstant.JFN_UPDATED_TIME, CommonDateTimeUtil.convertToGridDate(model.getUpdatedTime()));
		jsObj.put(PcmOrdConstant.JFN_UPDATED_BY, model.getUpdatedBy());
		jsObj.put(PcmOrdConstant.JFN_ACTION, getAction(model, showDelBtn));
		
		return jsObj;
	}
	
	private static String getAction(PcmOrdModel model, Boolean showDelBtn) {
		StringBuffer action = new StringBuffer();
		
		if (model.getStatus().equals(PcmOrdConstant.ST_DRAFT)) {
			action.append(PcmOrdConstant.ACTION_EDIT);
			action.append(PcmOrdConstant.ACTION_DELETE);
		}
		else {
			action.append(PcmOrdConstant.ACTION_SHOW_HISTORY);
			if (model.getStatus().equals(PcmOrdConstant.ST_WAITING)) {
				action.append(PcmOrdConstant.ACTION_SHOW_DIAGRAM);
			}
			else 
			if (model.getStatus().equals(PcmOrdConstant.ST_CLOSED)
				|| model.getStatus().equals(PcmOrdConstant.ST_CANCEL)) {
				if (showDelBtn) {
					action.append(PcmOrdConstant.ACTION_DELETE);
				}
			}
		}
		
		if (model.getFolderRef() != null && !model.getFolderRef().trim().equals("")) {
			action.append(PcmOrdConstant.ACTION_GOTO_FOLDER);
		}
		
		if (model.getDocRef() != null && !model.getDocRef().trim().equals("")) {
			action.append(PcmOrdConstant.ACTION_SHOW_DETAIL);
		}
		
		return action.toString();
	}
	
	public static JSONArray convertToJSONArray(List<PcmOrdModel> inList, Boolean showDelBtn) throws Exception {
		JSONArray jsArr = new JSONArray();
		
		for(PcmOrdModel model : inList) {
			jsArr.put(convertToJSONObject(model, showDelBtn));
		}
		
		return jsArr;
	}
	
	public static String jsonSuccess(List<PcmOrdModel> list, Boolean showDelBtn) throws Exception {
		
		Long total = list.size() > 0 ? list.get(0).getTotalRowCount() : 0;

		JSONObject jsonObj = new JSONObject();

		jsonObj.put(JsonConstant.SUCCESS,  true);
		jsonObj.put(JsonConstant.TOTAL,  total);
		jsonObj.put(JsonConstant.DATA, convertToJSONArray(list, showDelBtn));
		
		return jsonObj.toString();
	}
	
	public static String jsonSuccess(List<PcmOrdModel> list, List<PcmOrdDtlModel> dtlList) throws Exception {
		
		Long total = list.size() > 0 ? list.get(0).getTotalRowCount() : 0;

		JSONObject jsonObj = new JSONObject();

		jsonObj.put(JsonConstant.SUCCESS,  true);
		jsonObj.put(JsonConstant.TOTAL,  total);
		jsonObj.put(JsonConstant.DATA, convertToJSONArray(list, false));
		
		JSONObject jsonObjDtl = new JSONObject();
		for(PcmOrdDtlModel model : dtlList) {
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
