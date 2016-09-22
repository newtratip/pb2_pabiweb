package pb.repo.exp.wscript;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.common.util.CommonUtil;
import pb.repo.exp.service.ExpBrwService;
import pb.repo.exp.service.ExpBrwWorkflowService;

import com.github.dynamicextensionsalfresco.webscripts.annotations.RequestParam;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript
public class ExpWorkflowWebScript {
	
	private static Logger log = Logger.getLogger(ExpWorkflowWebScript.class);
	
	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/exp/wf";
	
	@Autowired
	ExpBrwWorkflowService workflowService;
	
	@Autowired
	ExpBrwService expBrwService;
	
	/*
	 * id = av id
	 */
	@Uri(URI_PREFIX + "/assignee/list")
	public void handleAssigneeList(@RequestParam final String id,
								   @RequestParam(required=false) final String lang
								, final WebScriptResponse response)
								throws Exception {

		String json = null;

		try {
			workflowService.setModuleService(expBrwService);
			JSONArray jsArr = workflowService.listAssignee(id, lang);
			
			json = CommonUtil.jsonSuccess(jsArr);
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
		} finally {
			CommonUtil.responseWrite(response, json);
		}
	}
	
	/*
	 * id = av id
	 */
	@Uri(URI_PREFIX + "/task/list")
	public void handleTaskList(@RequestParam final String id
								, final WebScriptResponse response)
								throws Exception {

		String json = null;

		try {
			workflowService.setModuleService(expBrwService);
			JSONArray jsArr = workflowService.listTask(id);
			json = CommonUtil.jsonSuccess(jsArr);

		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;

		} finally {
			CommonUtil.responseWrite(response, json);

		}
	}
	
	/*
	 * id = av id
	 */
	@Uri(URI_PREFIX + "/dtl/list")
	public void handleDetailList(@RequestParam final String id,
								 @RequestParam(required=false) final String lang
								, final WebScriptResponse response)
								throws Exception {

		String json = null;

		try {
			workflowService.setModuleService(expBrwService);
			JSONArray jsArr = workflowService.listDetail(id, lang);
			json = CommonUtil.jsonSuccess(jsArr);

		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;

		} finally {
			CommonUtil.responseWrite(response, json);

		}
	}
	
}
