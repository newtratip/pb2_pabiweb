package pb.repo.pcm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import pb.common.constant.JsonConstant;
import pb.common.util.CommonUtil;
import pb.repo.pcm.model.PcmReqCmtModel;

import com.alibaba.fastjson.JSON;

public class PcmReqCmtUtil {
	
	private static Logger log = Logger.getLogger(PcmReqCmtUtil.class);
	
	public static String jsonSuccess(List<PcmReqCmtModel> list) {
		
		List<Map<String, Object>> cmbList = new ArrayList<Map<String, Object>>();
		
		for(PcmReqCmtModel model : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put(JsonConstant.COMBOBOX_ID, model.getId());
			map.put(JsonConstant.COMBOBOX_NAME, model.getObjType() + " - " + model.getMethod() + " - " + model.getCond1());
			map.put(JsonConstant.COMBOBOX_DATA, model);
			
			cmbList.add(map);
		}
		
		return CommonUtil.jsonSuccess(cmbList);
	}
	
}
