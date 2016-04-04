package pb.common.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import pb.common.constant.FileConstant;
import pb.common.constant.JsonConstant;
import pb.common.model.FileModel;

public class FileUtil {
	
	public static JSONObject convertToJSONObject(FileModel model) throws Exception {
		if (model == null) {
			return null;
		}
		JSONObject jsObj = new JSONObject();
		
		jsObj.put(FileConstant.JFN_NAME, model.getName());
		jsObj.put(FileConstant.JFN_PATH, model.getPath());
		jsObj.put(FileConstant.JFN_NODE_REF, model.getNodeRef());
		jsObj.put(FileConstant.JFN_ACTION, "ED");
		
		return jsObj;
	}
	
	public static JSONArray convertToJSONArray(List<FileModel> inList) throws Exception {
		
		List<JSONObject> outList = new ArrayList<JSONObject>();
		
		JSONObject jsObj = null;
		for(FileModel model : inList) {
			jsObj = convertToJSONObject(model);
			
			outList.add(jsObj);
		}
		
		return new JSONArray(outList);
	}
	
	
	public static String jsonSuccess(List<FileModel> list) throws Exception {
		
		JSONObject jsObj = new JSONObject();

		jsObj.put(JsonConstant.SUCCESS,  true);
		jsObj.put(JsonConstant.TOTAL,  list.size());
		jsObj.put(JsonConstant.DATA, convertToJSONArray(list));
		
		return jsObj.toString();
	}
	
}
