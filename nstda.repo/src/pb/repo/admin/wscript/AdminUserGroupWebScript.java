package pb.repo.admin.wscript;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.common.util.CommonUtil;
import pb.repo.admin.constant.MainUserGroupConstant;
import pb.repo.admin.model.MainUserGroupModel;
import pb.repo.admin.service.AdminUserGroupService;
import pb.repo.admin.util.MainUserGroupUtil;

import com.github.dynamicextensionsalfresco.webscripts.annotations.RequestParam;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript
public class AdminUserGroupWebScript {
	
	private static Logger log = Logger.getLogger(AdminUserGroupWebScript.class);
	
	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/admin/main/userGroup";

	@Autowired
	AdminUserGroupService userGroupService;
	
	@Uri(URI_PREFIX+"/list")
	public void handleList(@RequestParam(required=false) String s,
						   @RequestParam(required=false) String t,
						   @RequestParam(required=false) String notIn
			, final WebScriptResponse response)  throws Exception {
		
		t = (t != null) ? t : "B";
		
		String json = null;

		try {
			List<MainUserGroupModel> list = userGroupService.listUserGroup(s, t, notIn);
			json = MainUserGroupUtil.jsonSuccess(list);
		} catch (Exception ex) {
			log.error("", ex);
			try {
				json = CommonUtil.jsonFail(ex.toString());
			} catch (JSONException e) {
				log.error("", e);
			}
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
		}
		
	}
	
	@Uri(URI_PREFIX+"/listDetail")
	public void handleListDetail(@RequestParam(required=false) String u,
						   @RequestParam(required=false) String g
			, final WebScriptResponse response)  throws Exception {
		
		String json = null;

		try {
			List<MainUserGroupModel> list = userGroupService.listUserGroupDetail(u, MainUserGroupConstant.TYPE_USER);
			list.addAll(userGroupService.listUserGroupDetail(g, MainUserGroupConstant.TYPE_GROUP));
			json = MainUserGroupUtil.jsonSuccess(list);
		} catch (Exception ex) {
			log.error("", ex);
			try {
				json = CommonUtil.jsonFail(ex.toString());
			} catch (JSONException e) {
				log.error("", e);
			}
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
		}
		
	}

}
