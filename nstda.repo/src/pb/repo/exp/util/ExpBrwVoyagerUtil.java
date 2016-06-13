package pb.repo.exp.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pb.repo.exp.constant.ExpBrwVoyagerConstant;
import pb.repo.exp.model.ExpBrwVoyagerModel;

public class ExpBrwVoyagerUtil {
	
	public static String jsonSuccess(List<ExpBrwVoyagerModel> list) throws JSONException {
		
		JSONObject jsonObj = new JSONObject();
		
		for(ExpBrwVoyagerModel model : list) {
			jsonObj.put(model.getName(), model.getName());
		}
		
		return jsonObj.toString();
	}
	
	public static JSONArray convertToJSONArray(List<ExpBrwVoyagerModel> inList) throws Exception {
		
		List<JSONObject> outList = new ArrayList<JSONObject>();
		
		JSONObject jsObj = null;
		for(ExpBrwVoyagerModel model : inList) {
			jsObj = new JSONObject();
			
			jsObj.put(ExpBrwVoyagerConstant.JFN_ID, model.getId());
			jsObj.put(ExpBrwVoyagerConstant.JFN_MASTER_ID, model.getMasterId());
			jsObj.put(ExpBrwVoyagerConstant.JFN_TYPE, model.getType());
			jsObj.put(ExpBrwVoyagerConstant.JFN_CODE, model.getCode());
			jsObj.put(ExpBrwVoyagerConstant.JFN_NAME, model.getName());
			jsObj.put(ExpBrwVoyagerConstant.JFN_UNIT_TYPE, model.getUnitType());
			jsObj.put(ExpBrwVoyagerConstant.JFN_POSITION, model.getPosition());
			jsObj.put(ExpBrwVoyagerConstant.JFN_CREATED_TIME, model.getCreatedTime());
			jsObj.put(ExpBrwVoyagerConstant.JFN_CREATED_BY, model.getCreatedBy());
			jsObj.put(ExpBrwVoyagerConstant.JFN_UPDATED_TIME, model.getUpdatedTime());
			jsObj.put(ExpBrwVoyagerConstant.JFN_UPDATED_BY, model.getUpdatedBy());
			jsObj.put(ExpBrwVoyagerConstant.JFN_ACTION, "ED");

			outList.add(jsObj);
		}
		
		return new JSONArray(outList);
	}
	
	public static List<ExpBrwVoyagerModel> convertJsonToList(String json, String masterId) throws Exception {
		List<ExpBrwVoyagerModel> list = new ArrayList<ExpBrwVoyagerModel>();
		
		if (json!=null && !json.equals("")) {
			JSONArray jsonArr = new JSONArray(json);
			for(int i=0; i<jsonArr.length(); i++) {
				JSONObject jsonObj = jsonArr.getJSONObject(i);
				
				ExpBrwVoyagerModel model = new ExpBrwVoyagerModel();
				model.setMasterId(masterId);
				model.setType(jsonObj.getString(ExpBrwVoyagerConstant.JFN_TYPE));
				model.setCode(jsonObj.getString(ExpBrwVoyagerConstant.JFN_CODE));
				model.setName(jsonObj.getString(ExpBrwVoyagerConstant.JFN_NAME));
				model.setUnitType(jsonObj.getString(ExpBrwVoyagerConstant.JFN_UNIT_TYPE));
				model.setPosition(jsonObj.getString(ExpBrwVoyagerConstant.JFN_POSITION));
				
				list.add(model);
			}
		}
		
		return list;
	}
	
	public static void addAction(List<ExpBrwVoyagerModel> list) {
		for (ExpBrwVoyagerModel model : list) {
			model.setAction("ED");
		}
	}	
}
