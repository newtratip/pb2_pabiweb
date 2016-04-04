package pb.repo.admin.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import pb.repo.admin.constant.MainCompleteNotificationConstant;
import pb.repo.admin.model.MainCompleteNotificationModel;

public class MainCompleteNotificationUtil {
	
	public static String jsonSuccess(List<MainCompleteNotificationModel> list) throws Exception {
		
		Long total = list.size() > 0 ? list.get(0).getTotalRowCount() : 0;

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("success",  true);
		jsonObj.put("total",  total);
		jsonObj.put("data", convertToJSONArray(list));
		
		return jsonObj.toString();
	}
	
	public static Map<String, Object> convertToMap(List<MainCompleteNotificationModel> inList) throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		for(MainCompleteNotificationModel model : inList) {
			map.put(String.valueOf(model.getId()), model.getReceiver());
		}
		
		return map;
	}
	
	public static JSONArray convertToJSONArray(List<MainCompleteNotificationModel> inList) throws Exception {
		JSONArray jsArr = new JSONArray();
		
		JSONObject jsObj;
		
		for(MainCompleteNotificationModel model : inList) {
			jsObj = new JSONObject();
			
			jsObj.put(MainCompleteNotificationConstant.JFN_ID, model.getId());
			jsObj.put(MainCompleteNotificationConstant.JFN_RECEIVER, model.getReceiver());
			jsObj.put(MainCompleteNotificationConstant.JFN_TASK_ID, model.getTaskId());
			jsObj.put(MainCompleteNotificationConstant.JFN_CREATED_TIME, model.getCreatedTime());
			jsObj.put(MainCompleteNotificationConstant.JFN_CREATED_BY, model.getCreatedBy());
			jsObj.put(MainCompleteNotificationConstant.JFN_ACTION, "ED");
			
			jsArr.put(jsObj);
		}
		
		return jsArr;
	}
}
