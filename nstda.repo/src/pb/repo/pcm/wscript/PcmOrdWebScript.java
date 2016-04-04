package pb.repo.pcm.wscript;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.TemplateService;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.common.model.FileModel;
import pb.common.util.CommonDateTimeUtil;
import pb.common.util.CommonUtil;
import pb.common.util.FileUtil;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminTestSystemService;
import pb.repo.admin.service.AdminUserGroupService;
import pb.repo.pcm.constant.PcmOrdConstant;
import pb.repo.pcm.model.PcmOrdDtlModel;
import pb.repo.pcm.model.PcmOrdModel;
import pb.repo.pcm.service.PcmOrdService;
import pb.repo.pcm.service.PcmOrdWorkflowService;
import pb.repo.pcm.util.PcmOrdUtil;

import com.github.dynamicextensionsalfresco.webscripts.annotations.HttpMethod;
import com.github.dynamicextensionsalfresco.webscripts.annotations.RequestParam;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript
public class PcmOrdWebScript {
	
	private static Logger log = Logger.getLogger(PcmOrdWebScript.class);
	
	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/pcm/ord";
	
	@Autowired
	private PcmOrdService pcmOrdService;
	
	@Autowired
	TemplateService templateService;

	@Autowired
	private PcmOrdWorkflowService pcmWorkflowService;
	
	@Autowired
	private AdminMasterService masterService;
	
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private AuthenticationService authService;
	
	@Autowired
	private AdminUserGroupService userGroupService;
	
	@Autowired
	private AdminTestSystemService adminTestSystemService;
	
  @Uri(URI_PREFIX+"/list")
  public void handleList(@RequestParam(required=false) final String s
			  , @RequestParam(required=false) final Integer start
			  , @RequestParam(required=false) final Integer limit
			  , final WebScriptResponse response)  throws Exception {

	  	/*
	  	 * Prepare Criteria
	  	 */
		Map<String, Object> params = new HashMap<String, Object>();
		
		String searchTerm = null;
		
		if (s != null && !s.equals("")) {
			searchTerm = "%" + s + "%";
		}
		params.put("searchTerm", searchTerm);
		params.put("start", start);
		params.put("limit", limit);
		
		params.put("orderBy", "ORDER_FIELD, updated_time DESC");		
	  
		/*
		 * Search
		 */
		String json = null;
		
		try {
			List<PcmOrdModel> list = pcmOrdService.list(params);
			MainMasterModel showDelBtnCfg = masterService.getSystemConfig(MainMasterConstant.SCC_PCM_ORD_DELETE_BUTTON);
			json = PcmOrdUtil.jsonSuccess(list, showDelBtnCfg!=null && showDelBtnCfg.getFlag1()!=null && showDelBtnCfg.getFlag1().equals("1"));
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
			
		}
	  
  }
  
  private void putOneParam(Map<String, Object> params, JSONObject jsObj, String fieldName) {
	  try {
		String field = jsObj.getString(fieldName);  
		if (field!=null && !field.equals("") && !field.equals("null")) {
			params.put(fieldName,  field);
		}
	  } catch (Exception ex) {
		  // do nothing
	  }
  }
  
  @Uri(method=HttpMethod.POST, value=URI_PREFIX+"/send")
  public void handleSendToReview(@RequestParam(required=false) final String id
						,@RequestParam(required=false) final String reqType
		  				,@RequestParam(required=false) final String remark
		  				,@RequestParam(required=false) final String costType
		  				,@RequestParam(required=false) final String reqOu
		  				,@RequestParam(required=false) final String total
		  				,@RequestParam(required=false) final String requested_time
		  				,@RequestParam(required=false) final String status
		  				,@RequestParam(required=false) final String dtls
		  				,@RequestParam(required=false) final String files
		  				,final WebScriptResponse response) throws Exception {

	String json = null;
	
	try {
		PcmOrdModel model = null;
		
		if (CommonUtil.isValidId(id)) {
			model = pcmOrdService.get(id);
		}
		
		if (model==null) {
			model = new PcmOrdModel();
		}
		model.setRemark(remark);
		model.setWaitingLevel(0);
		model.setReqType(reqType);
		model.setCostType(costType);
		model.setReqOu(reqOu);
		model.setTotal(Double.parseDouble(total));
		
		if (requested_time!=null) {
			model.setRequestedTime(CommonDateTimeUtil.convertSenchaStringToTimestamp(requested_time));
		}

		if (model.getId() == null || (status!=null && status.equals(PcmOrdConstant.ST_DRAFT))) {
			model.setStatus(PcmOrdConstant.ST_WAITING);
			model.setWaitingLevel(1);
		}
		
		JSONObject validateResult = pcmOrdService.validateAssignee(model);
		if (!(Boolean)validateResult.get("valid")) {
			json = CommonUtil.jsonFail(validateResult);
		}
		else {
			model = pcmOrdService.save(model, dtls, files, true);
			
			model.setWorkflowId("2");
			pcmWorkflowService.startWorkflow(model);
			
			JSONObject jsObj = new JSONObject();
			jsObj.put("id", model.getId());
			json = CommonUtil.jsonSuccess(jsObj);
		}
		
	} catch (Exception ex) {
		log.error("", ex);
		json = CommonUtil.jsonFail(ex.toString());
		throw ex;
	} finally {
		CommonUtil.responseWrite(response, json);
	}

	  
  }


  @Uri(URI_PREFIX+"/get")
  public void handleGet(@RequestParam final String id, final WebScriptResponse response)
      throws Exception {
		
	String json = null;
	 
	try {
	  PcmOrdModel model = pcmOrdService.get(id);
	  
	  List<PcmOrdModel> list = new ArrayList<PcmOrdModel>();
	  list.add(model);
	  
	  List<PcmOrdDtlModel> dtlList = pcmOrdService.listDtlByMasterId(id);	  
		
	  json = PcmOrdUtil.jsonSuccess(list, dtlList);
		
	} catch (Exception ex) {
		log.error("", ex);
		json = CommonUtil.jsonFail(ex.toString());
		throw ex;
		
	} finally {
		CommonUtil.responseWrite(response, json);
	}
	
  }
  
  @Uri(URI_PREFIX+"/file/list")
  public void handleFileList(@RequestParam final String id, final WebScriptResponse response)
      throws Exception {
		
	String json = null;
	 
	try {
		
	  List<FileModel> files = pcmOrdService.listFile(id);
		
	  json = FileUtil.jsonSuccess(files);
		
	} catch (Exception ex) {
		log.error("", ex);
		json = CommonUtil.jsonFail(ex.toString());
		throw ex;
		
	} finally {
		CommonUtil.responseWrite(response, json);
	}
	
  }
  
  @Uri(URI_PREFIX+"/dtl/list")
  public void handleDetailList(@RequestParam final String id, final WebScriptResponse response)
      throws Exception {
		
	String json = null;
	 
	try {
		
	  json = "{\"success\":true,\"data\":[{\"id\":1,\"desc\":\"Computer\",\"amt\":13,\"unit\":\"SET\",\"price\":\"21,300\",\"priceBaht\":\"21,300\",\"total\":\"276,900\",\"action\":\"ED\"}"
			  +",{\"id\":2,\"desc\":\"Tablet\",\"amt\":13,\"unit\":\"EACH\",\"price\":\"11,500\",\"priceBaht\":\"11,500\",\"total\":\"149,500\",\"action\":\"ED\"}"
			  +"]}";
	  
	  
	} catch (Exception ex) {
		log.error("", ex);
		json = CommonUtil.jsonFail(ex.toString());
		throw ex;
		
	} finally {
		CommonUtil.responseWrite(response, json);
	}
	
  }  
  
  @Uri(URI_PREFIX+"/criteria/list")
  public void handleCriteriaList(final WebScriptResponse response)  throws Exception {

		String json = null;
		
		try {
			JSONArray jsArr = pcmOrdService.listCriteria();
			json = CommonUtil.jsonSuccess(jsArr);
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
			
		}
    
  }
  
  
  @Uri(URI_PREFIX+"/gridfield/list")
  public void handleGridFieldList(final WebScriptResponse response)  throws Exception {

		String json = null;
		
		try {
			JSONArray jsArr = pcmOrdService.listGridField();
			json = CommonUtil.jsonSuccess(jsArr);
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
			
		}
    
  }
  
  @Uri(method=HttpMethod.POST, value=URI_PREFIX+"/finish")
  public void handleFinish(@RequestParam(required=false) final String id
		  				,@RequestParam(required=false) final String field1
		  				,@RequestParam(required=false) final String field2
		  				,@RequestParam(required=false) final String field3
		  				,@RequestParam(required=false) final String field4
		  				,@RequestParam(required=false) final String field5
		  				,@RequestParam(required=false) final String field6
		  				,@RequestParam(required=false) final String field7
		  				,@RequestParam(required=false) final String field8
		  				,@RequestParam(required=false) final String field9
		  				,@RequestParam(required=false) final String field10
		  				,@RequestParam(required=false) final String remark
		  				,@RequestParam(required=false) final String requested_time
		  				,@RequestParam(required=false) final String status
		  				,@RequestParam final String content1
		  				,@RequestParam final Long hId
		  				,@RequestParam final Long format_id
		  				,@RequestParam final String workflow_id
		  				,@RequestParam final Long approval_matrix_id
		  				,@RequestParam final String dtls
		  				,@RequestParam(required=false) final String aug
		  				,@RequestParam(required=false) final String rug
		  				,@RequestParam(required=false) final String files
		  				,final WebScriptResponse response) throws Exception {
	
	String json = null;
	
	try {
		PcmOrdModel model = null;
		
		if (CommonUtil.isValidId(id)) {
			model = pcmOrdService.get(id);
		}
		
		if (model==null) {
			model = new PcmOrdModel();
		}
		model.setWorkflowId(workflow_id);
		
		Boolean amDirty = (approval_matrix_id != model.getApprovalMatrixId());
		
		model.setApprovalMatrixId(approval_matrix_id);
		model.setRemark(remark);
		
		
		
		if (requested_time!=null) {
			model.setRequestedTime(CommonDateTimeUtil.convertSenchaStringToTimestamp(requested_time));
		}

		if (model.getId() == null || (status!=null && status.equals(PcmOrdConstant.ST_DRAFT))) {
			model.setStatus(PcmOrdConstant.ST_WAITING);
		}
		
		JSONObject validateResult = pcmOrdService.validateAssignee(model);
		if (!(Boolean)validateResult.get("valid")) {
			json = CommonUtil.jsonFail(validateResult);
		}
		else {
			model = pcmOrdService.save(model, dtls, files, true);
			pcmWorkflowService.updateWorkflow(model, aug, rug, amDirty);
			
			JSONObject jsObj = new JSONObject();
			jsObj.put("id", model.getId());
			json = CommonUtil.jsonSuccess(jsObj);
		}
		
	} catch (Exception ex) {
		log.error("", ex);
		json = CommonUtil.jsonFail(ex.toString());
		throw ex;
	} finally {
		CommonUtil.responseWrite(response, json);
	}
	  
  }
  
	@Uri(URI_PREFIX + "/userTask")
	public void handleUserTask(final WebScriptResponse response)
								throws Exception {

		String json = null;

		try {
			MainMasterModel rptModel = masterService.getSystemConfig(MainMasterConstant.SCC_PCM_ORD_RPT_TAB);
			
			JSONObject tasks = new JSONObject();
			tasks.put("pcmOrdRptTab", rptModel!=null && rptModel.getFlag1()!=null && rptModel.getFlag1().equals("1"));
			
			JSONObject jsObj = new JSONObject();
			jsObj.put("tasks", tasks);
			
			json = CommonUtil.jsonSuccess(jsObj);
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
		} finally {
			CommonUtil.responseWrite(response, json);
		}
	} 	
  
}
