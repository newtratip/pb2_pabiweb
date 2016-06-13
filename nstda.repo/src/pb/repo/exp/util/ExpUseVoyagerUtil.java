package pb.repo.exp.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pb.repo.exp.constant.ExpUseVoyagerConstant;
import pb.repo.exp.model.ExpUseVoyagerModel;

public class ExpUseVoyagerUtil {
	
	public static String jsonSuccess(List<ExpUseVoyagerModel> list) throws JSONException {
		
		JSONObject jsonObj = new JSONObject();
		
		for(ExpUseVoyagerModel model : list) {
			jsonObj.put(model.getName(), model.getName());
		}
		
		return jsonObj.toString();
	}
	
	public static JSONArray convertToJSONArray(List<ExpUseVoyagerModel> inList) throws Exception {
		
		List<JSONObject> outList = new ArrayList<JSONObject>();
		
		JSONObject jsObj = null;
		for(ExpUseVoyagerModel model : inList) {
			jsObj = new JSONObject();
			
			jsObj.put(ExpUseVoyagerConstant.JFN_ID, model.getId());
			jsObj.put(ExpUseVoyagerConstant.JFN_MASTER_ID, model.getMasterId());
			jsObj.put(ExpUseVoyagerConstant.JFN_TYPE, model.getType());
			jsObj.put(ExpUseVoyagerConstant.JFN_CODE, model.getCode());
			jsObj.put(ExpUseVoyagerConstant.JFN_NAME, model.getName());
			jsObj.put(ExpUseVoyagerConstant.JFN_UNIT_TYPE, model.getUnitType());
			jsObj.put(ExpUseVoyagerConstant.JFN_POSITION, model.getPosition());
			jsObj.put(ExpUseVoyagerConstant.JFN_CREATED_TIME, model.getCreatedTime());
			jsObj.put(ExpUseVoyagerConstant.JFN_CREATED_BY, model.getCreatedBy());
			jsObj.put(ExpUseVoyagerConstant.JFN_UPDATED_TIME, model.getUpdatedTime());
			jsObj.put(ExpUseVoyagerConstant.JFN_UPDATED_BY, model.getUpdatedBy());
			jsObj.put(ExpUseVoyagerConstant.JFN_ACTION, "ED");

			outList.add(jsObj);
		}
		
		return new JSONArray(outList);
	}
	
	public static List<ExpUseVoyagerModel> convertJsonToList(String json, String masterId) throws Exception {
		List<ExpUseVoyagerModel> list = new ArrayList<ExpUseVoyagerModel>();
		
		if (json!=null && !json.equals("")) {
			JSONArray jsonArr = new JSONArray(json);
			for(int i=0; i<jsonArr.length(); i++) {
				JSONObject jsonObj = jsonArr.getJSONObject(i);
				
				ExpUseVoyagerModel model = new ExpUseVoyagerModel();
				model.setMasterId(masterId);
				model.setType(jsonObj.getString(ExpUseVoyagerConstant.JFN_TYPE));
				model.setCode(jsonObj.getString(ExpUseVoyagerConstant.JFN_CODE));
				model.setName(jsonObj.getString(ExpUseVoyagerConstant.JFN_NAME));
				model.setUnitType(jsonObj.getString(ExpUseVoyagerConstant.JFN_UNIT_TYPE));
				model.setPosition(jsonObj.getString(ExpUseVoyagerConstant.JFN_POSITION));
				
				list.add(model);
			}
		}
		
		return list;
	}
	
	public static void addAction(List<ExpUseVoyagerModel> list) {
		for (ExpUseVoyagerModel model : list) {
			model.setAction("ED");
		}
	}	
}
