package pb.repo.pcm.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import pb.repo.pcm.constant.PcmReqCmtDtlConstant;
import pb.repo.pcm.constant.PcmReqCmtHdrConstant;
import pb.repo.pcm.model.PcmReqCmtDtlModel;
import pb.repo.pcm.model.PcmReqCmtHdrModel;

public class PcmReqCmtHdrUtil {
	
	private static Logger log = Logger.getLogger(PcmReqCmtHdrUtil.class);
	
//	public static String jsonSuccess(List<PcmReqCmtHdrModel> list) {
//		
//		List<Map<String, Object>> cmbList = new ArrayList<Map<String, Object>>();
//		
//		for(PcmReqCmtHdrModel model : list) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			
//			map.put(JsonConstant.COMBOBOX_ID, model.getId());
//			map.put(JsonConstant.COMBOBOX_NAME, model.getObjType() + " - " + model.getMethod() + " - " + model.getCond1());
//			map.put(JsonConstant.COMBOBOX_DATA, model);
//			
//			cmbList.add(map);
//		}
//		
//		return CommonUtil.jsonSuccess(cmbList);
//	}
	
	public static List<PcmReqCmtHdrModel> convertJsonToList(String json, String masterId) throws Exception {
		List<PcmReqCmtHdrModel> list = new ArrayList<PcmReqCmtHdrModel>();
		
		if (json!=null && !json.equals("")) {
			JSONArray jsonArr = new JSONArray(json);
			for(int i=0; i<jsonArr.length(); i++) {
				JSONObject jsonObj = jsonArr.getJSONObject(i);
				
				PcmReqCmtHdrModel model = new PcmReqCmtHdrModel();
				model.setPcmReqId(masterId);
				model.setCommittee(jsonObj.getString(PcmReqCmtHdrConstant.JFN_COMMITTEE));
				model.setCommitteeId(jsonObj.getInt(PcmReqCmtHdrConstant.JFN_COMMITTEE_ID));
				
				list.add(model);

				
				JSONArray dtlArr = jsonObj.getJSONArray(PcmReqCmtHdrConstant.JFN_COMMITTEES);
				for(int j=0; j<dtlArr.length(); j++) {
					JSONObject dtlObj = dtlArr.getJSONObject(j);
					
					PcmReqCmtDtlModel dtlModel = new PcmReqCmtDtlModel();
					dtlModel.setEmployeeCode(dtlObj.getString(PcmReqCmtDtlConstant.JFN_EMPLOYEE_CODE));
					dtlModel.setTitle(dtlObj.getString(PcmReqCmtDtlConstant.JFN_TITLE));
					dtlModel.setFirstName(dtlObj.getString(PcmReqCmtDtlConstant.JFN_FIRST_NAME));
					dtlModel.setLastName(dtlObj.getString(PcmReqCmtDtlConstant.JFN_LAST_NAME));
					dtlModel.setPosition(dtlObj.getString(PcmReqCmtDtlConstant.JFN_POSITION));
					
					model.getDtlList().add(dtlModel);
				}

			}
		}
		
		return list;
	}	
	
}
