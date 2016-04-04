package pb.repo.pcm.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pb.repo.pcm.constant.PcmReqDtlConstant;
import pb.repo.pcm.model.PcmReqDtlModel;

public class PcmReqDtlUtil {
	
	public static String jsonSuccess(List<PcmReqDtlModel> list) throws JSONException {
		
		JSONObject jsonObj = new JSONObject();
		
		for(PcmReqDtlModel model : list) {
			jsonObj.put(model.getDescription(), model.getQuantity());
		}
		
		return jsonObj.toString();
	}
	
	public static JSONArray convertToJSONArray(List<PcmReqDtlModel> inList) throws Exception {
		
		List<JSONObject> outList = new ArrayList<JSONObject>();
		
		JSONObject jsObj = null;
		for(PcmReqDtlModel model : inList) {
			jsObj = new JSONObject();
			
			jsObj.put(PcmReqDtlConstant.JFN_ID, model.getId());
			jsObj.put(PcmReqDtlConstant.JFN_DESCRIPTION, model.getDescription());
			jsObj.put(PcmReqDtlConstant.JFN_QUANTITY, String.valueOf(model.getQuantity()));
			jsObj.put(PcmReqDtlConstant.JFN_UNIT, model.getUnit());
			jsObj.put(PcmReqDtlConstant.JFN_PRICE, String.valueOf(model.getPrice()));
			jsObj.put(PcmReqDtlConstant.JFN_PRICE_CNV, String.valueOf(model.getPriceCnv()));
			jsObj.put(PcmReqDtlConstant.JFN_TOTAL, String.valueOf(model.getTotal()));
			jsObj.put(PcmReqDtlConstant.JFN_CREATED_TIME, model.getCreatedTime());
			jsObj.put(PcmReqDtlConstant.JFN_CREATED_BY, model.getCreatedBy());
			jsObj.put(PcmReqDtlConstant.JFN_UPDATED_TIME, model.getUpdatedTime());
			jsObj.put(PcmReqDtlConstant.JFN_UPDATED_BY, model.getUpdatedBy());
			jsObj.put(PcmReqDtlConstant.JFN_ACTION, "ED");

			outList.add(jsObj);
		}
		
		return new JSONArray(outList);
	}
	
	public static List<PcmReqDtlModel> convertJsonToList(String json, String masterId) throws Exception {
		List<PcmReqDtlModel> list = new ArrayList<PcmReqDtlModel>();
		
		if (json!=null && !json.equals("")) {
			JSONArray jsonArr = new JSONArray(json);
			for(int i=0; i<jsonArr.length(); i++) {
				JSONObject jsonObj = jsonArr.getJSONObject(i);
				
				PcmReqDtlModel model = new PcmReqDtlModel();
				model.setMasterId(masterId);
				model.setDescription(jsonObj.getString(PcmReqDtlConstant.JFN_DESCRIPTION));
				model.setQuantity(Double.parseDouble(jsonObj.getString(PcmReqDtlConstant.JFN_QUANTITY)));
				model.setUnit(jsonObj.getString(PcmReqDtlConstant.JFN_UNIT));
				model.setPrice(Double.parseDouble(jsonObj.getString(PcmReqDtlConstant.JFN_PRICE)));
				model.setPriceCnv(Double.parseDouble(jsonObj.getString(PcmReqDtlConstant.JFN_PRICE_CNV)));
				model.setTotal(Double.parseDouble(jsonObj.getString(PcmReqDtlConstant.JFN_TOTAL)));
				
				list.add(model);
			}
		}
		
		return list;
	}
}
