package pb.repo.admin.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import pb.common.model.ComboBoxModel;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.model.MainCurrencyModel;
import pb.repo.admin.model.MainMasterModel;

public class MainCurrencyUtil {
	
	public static List<ComboBoxModel> convertToComboBoxList(List<MainCurrencyModel> list) {
		
		List<ComboBoxModel> cmbList = new ArrayList<ComboBoxModel>();
	
		for(MainCurrencyModel model : list) {
			
			ComboBoxModel cmbModel = new ComboBoxModel(model.getName(), model.getName());
			
			cmbList.add(cmbModel);
		}
		
		return cmbList;
	}
	
	public static Map<String, Object> convertToMap(List<MainMasterModel> inList) throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		for(MainMasterModel model : inList) {
			map.put(String.valueOf(model.getCode()), model.getName());
		}
		
		return map;
	}
	
	public static JSONArray convertToJSONArray(List<MainMasterModel> inList) throws Exception {
		JSONArray jsArr = new JSONArray();
		
		JSONObject jsObj;
		
		for(MainMasterModel model : inList) {
			jsObj = new JSONObject();
			
			jsObj.put(MainMasterConstant.JFN_ID, model.getId());
			jsObj.put(MainMasterConstant.JFN_TYPE, model.getType());
			jsObj.put(MainMasterConstant.JFN_CODE, model.getCode());
			jsObj.put(MainMasterConstant.JFN_NAME, model.getName());
			jsObj.put(MainMasterConstant.JFN_FLAG1, model.getFlag1());
			jsObj.put(MainMasterConstant.JFN_FLAG2, model.getFlag2());
			jsObj.put(MainMasterConstant.JFN_FLAG3, model.getFlag3());
			jsObj.put(MainMasterConstant.JFN_FLAG4, model.getFlag4());
			jsObj.put(MainMasterConstant.JFN_FLAG5, model.getFlag5());
			jsObj.put(MainMasterConstant.JFN_CREATED_TIME, model.getCreatedTime());
			jsObj.put(MainMasterConstant.JFN_CREATED_BY, model.getCreatedBy());
			jsObj.put(MainMasterConstant.JFN_UPDATED_TIME, model.getUpdatedTime());
			jsObj.put(MainMasterConstant.JFN_UPDATED_BY, model.getUpdatedBy());
			jsObj.put(MainMasterConstant.JFN_ACTIVE, model.getActive());
			jsObj.put(MainMasterConstant.JFN_ACTION, "ED");
			
			jsArr.put(jsObj);
		}
		
		return jsArr;
	}
}
