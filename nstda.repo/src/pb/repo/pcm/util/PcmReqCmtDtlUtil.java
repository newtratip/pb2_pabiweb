package pb.repo.pcm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pb.repo.pcm.constant.PcmReqCmtDtlConstant;
import pb.repo.pcm.model.PcmReqCmtDtlModel;

public class PcmReqCmtDtlUtil {
	
	public static void addAction(List<Map<String, Object>> list) {
		if (list!=null) {
			for (Map<String, Object> map : list) {
				map.put("action", "ED");
			}
		}
	}
	
	public static String jsonSuccess(List<PcmReqCmtDtlModel> list) throws JSONException {
		
		JSONObject jsonObj = new JSONObject();
		
		for(PcmReqCmtDtlModel model : list) {
			jsonObj.put(model.getId(), model.getFirstName()+" "+model.getLastName());
		}
		
		return jsonObj.toString();
	}
	
	public static JSONArray convertToJSONArray(List<PcmReqCmtDtlModel> inList) throws Exception {
		
		List<JSONObject> outList = new ArrayList<JSONObject>();
		
		JSONObject jsObj = null;
		for(PcmReqCmtDtlModel model : inList) {
			jsObj = new JSONObject();
			
			jsObj.put(PcmReqCmtDtlConstant.JFN_ID, model.getId());
			jsObj.put(PcmReqCmtDtlConstant.JFN_MASTER_ID, model.getMasterId());
			jsObj.put(PcmReqCmtDtlConstant.JFN_FIRST_NAME, String.valueOf(model.getFirstName()));
			jsObj.put(PcmReqCmtDtlConstant.JFN_LAST_NAME, model.getLastName());
			jsObj.put(PcmReqCmtDtlConstant.JFN_POSITION, String.valueOf(model.getPosition()));
			jsObj.put(PcmReqCmtDtlConstant.JFN_CREATED_TIME, model.getCreatedTime());
			jsObj.put(PcmReqCmtDtlConstant.JFN_CREATED_BY, model.getCreatedBy());
			jsObj.put(PcmReqCmtDtlConstant.JFN_UPDATED_TIME, model.getUpdatedTime());
			jsObj.put(PcmReqCmtDtlConstant.JFN_UPDATED_BY, model.getUpdatedBy());
			jsObj.put(PcmReqCmtDtlConstant.JFN_ACTION, "ED");

			outList.add(jsObj);
		}
		
		return new JSONArray(outList);
	}

}
