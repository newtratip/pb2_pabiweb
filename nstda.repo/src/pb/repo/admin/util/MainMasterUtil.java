package pb.repo.admin.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;

import pb.common.model.ResultModel;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.model.MainMasterModel;

public class MainMasterUtil {
	
	public static void addAction(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			map.put("action", "ED");
		}
	}
	
	public static String jsonSuccess(List<MainMasterModel> list) throws Exception {
		
		Long total = list.size() > 0 ? list.get(0).getTotalRowCount() : 0;
		
		ResultModel resultModel = new ResultModel(true, list, total);
		
		return JSON.toJSONString(resultModel);
		
//		JSONObject jsonObj = new JSONObject();
//
//		jsonObj.put("success",  true);
//		jsonObj.put("total",  total);
//		jsonObj.put("data", convertToJSONArray(list));
//		
//		return jsonObj.toString();
	}
	
	public static Map<String, Object> convertToMap(List<MainMasterModel> inList) throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		for(MainMasterModel model : inList) {
			map.put(String.valueOf(model.getCode()), model.getName());
		}
		
		return map;
	}
	
	public static JSONArray convertToJSONArray(List<Map<String, Object>> inList) throws Exception {
		JSONArray jsArr = new JSONArray();
		
		JSONObject jsObj;
		
		for(Map<String, Object> model : inList) {
			jsObj = new JSONObject();
			
			jsObj.put(MainMasterConstant.JFN_ID, model.get(MainMasterConstant.TFN_ID));
			jsObj.put(MainMasterConstant.JFN_TYPE, model.get(MainMasterConstant.TFN_TYPE));
			jsObj.put(MainMasterConstant.JFN_CODE, model.get(MainMasterConstant.TFN_CODE));
			jsObj.put(MainMasterConstant.JFN_NAME, model.get(MainMasterConstant.TFN_NAME));
			jsObj.put(MainMasterConstant.JFN_FLAG1, model.get(MainMasterConstant.TFN_FLAG1));
			jsObj.put(MainMasterConstant.JFN_FLAG2, model.get(MainMasterConstant.TFN_FLAG2));
			jsObj.put(MainMasterConstant.JFN_FLAG3, model.get(MainMasterConstant.TFN_FLAG3));
			jsObj.put(MainMasterConstant.JFN_FLAG4, model.get(MainMasterConstant.TFN_FLAG4));
			jsObj.put(MainMasterConstant.JFN_FLAG5, model.get(MainMasterConstant.TFN_FLAG5));
			jsObj.put(MainMasterConstant.JFN_CREATED_TIME, model.get(MainMasterConstant.TFN_CREATED_TIME));
			jsObj.put(MainMasterConstant.JFN_CREATED_BY, model.get(MainMasterConstant.TFN_CREATED_BY));
			jsObj.put(MainMasterConstant.JFN_UPDATED_TIME, model.get(MainMasterConstant.TFN_UPDATED_TIME));
			jsObj.put(MainMasterConstant.JFN_UPDATED_BY, model.get(MainMasterConstant.TFN_UPDATED_BY));
			jsObj.put(MainMasterConstant.JFN_ACTIVE, model.get(MainMasterConstant.TFN_ACTIVE));
			jsObj.put(MainMasterConstant.JFN_ACTION, "ED");
			
			jsArr.put(jsObj);
		}
		
		return jsArr;
	}
}
