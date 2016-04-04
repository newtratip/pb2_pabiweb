package pb.repo.pcm.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pb.repo.pcm.constant.PcmOrdDtlConstant;
import pb.repo.pcm.model.PcmOrdDtlModel;

public class PcmOrdDtlUtil {
	
	public static String jsonSuccess(List<PcmOrdDtlModel> list) throws JSONException {
		
		JSONObject jsonObj = new JSONObject();
		
		for(PcmOrdDtlModel model : list) {
			jsonObj.put(model.getDescription(), model.getQuantity());
		}
		
		return jsonObj.toString();
	}
	
	public static JSONArray convertToJSONArray(List<PcmOrdDtlModel> inList) throws Exception {
		
		List<JSONObject> outList = new ArrayList<JSONObject>();
		
		JSONObject jsObj = null;
		for(PcmOrdDtlModel model : inList) {
			jsObj = new JSONObject();
			
			jsObj.put(PcmOrdDtlConstant.JFN_ID, model.getId());
			jsObj.put(PcmOrdDtlConstant.JFN_DESCRIPTION, model.getDescription());
			jsObj.put(PcmOrdDtlConstant.JFN_QUANTITY, String.valueOf(model.getQuantity()));
			jsObj.put(PcmOrdDtlConstant.JFN_UNIT, model.getUnit());
			jsObj.put(PcmOrdDtlConstant.JFN_PRICE, String.valueOf(model.getPrice()));
			jsObj.put(PcmOrdDtlConstant.JFN_PRICE_CNV, String.valueOf(model.getPriceCnv()));
			jsObj.put(PcmOrdDtlConstant.JFN_TOTAL, String.valueOf(model.getTotal()));
			jsObj.put(PcmOrdDtlConstant.JFN_CREATED_TIME, model.getCreatedTime());
			jsObj.put(PcmOrdDtlConstant.JFN_CREATED_BY, model.getCreatedBy());
			jsObj.put(PcmOrdDtlConstant.JFN_UPDATED_TIME, model.getUpdatedTime());
			jsObj.put(PcmOrdDtlConstant.JFN_UPDATED_BY, model.getUpdatedBy());
			jsObj.put(PcmOrdDtlConstant.JFN_ACTION, "ED");

			outList.add(jsObj);
		}
		
		return new JSONArray(outList);
	}
	
	public static List<PcmOrdDtlModel> convertJsonToList(String json, String masterId) throws Exception {
		List<PcmOrdDtlModel> list = new ArrayList<PcmOrdDtlModel>();
		
		if (json!=null && !json.equals("")) {
			JSONArray jsonArr = new JSONArray(json);
			for(int i=0; i<jsonArr.length(); i++) {
				JSONObject jsonObj = jsonArr.getJSONObject(i);
				
				PcmOrdDtlModel model = new PcmOrdDtlModel();
				model.setMasterId(masterId);
				model.setDescription(jsonObj.getString(PcmOrdDtlConstant.JFN_DESCRIPTION));
				model.setQuantity(Double.parseDouble(jsonObj.getString(PcmOrdDtlConstant.JFN_QUANTITY)));
				model.setUnit(jsonObj.getString(PcmOrdDtlConstant.JFN_UNIT));
				model.setPrice(Double.parseDouble(jsonObj.getString(PcmOrdDtlConstant.JFN_PRICE)));
				model.setPriceCnv(Double.parseDouble(jsonObj.getString(PcmOrdDtlConstant.JFN_PRICE_CNV)));
				model.setTotal(Double.parseDouble(jsonObj.getString(PcmOrdDtlConstant.JFN_TOTAL)));
				
				list.add(model);
			}
		}
		
		return list;
	}
}
