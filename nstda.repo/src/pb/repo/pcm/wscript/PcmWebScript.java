package pb.repo.pcm.wscript;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.TemplateService;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.common.model.FileModel;
import pb.common.util.CommonUtil;
import pb.common.util.FileUtil;
import pb.common.util.FolderUtil;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminTestSystemService;
import pb.repo.admin.service.AdminUserGroupService;
import pb.repo.pcm.constant.PcmReqConstant;
import pb.repo.pcm.model.PcmReqCmtModel;
import pb.repo.pcm.model.PcmReqDtlModel;
import pb.repo.pcm.model.PcmReqModel;
import pb.repo.pcm.service.PcmReqService;
import pb.repo.pcm.service.PcmReqWorkflowService;
import pb.repo.pcm.util.PcmReqCmtUtil;
import pb.repo.pcm.util.PcmReqUtil;

import com.github.dynamicextensionsalfresco.webscripts.annotations.HttpMethod;
import com.github.dynamicextensionsalfresco.webscripts.annotations.RequestParam;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript
public class PcmWebScript {
	
	private static Logger log = Logger.getLogger(PcmWebScript.class);
	
	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/pcm";
	
	@Autowired
	private PcmReqService pcmReqService;
	
	@Autowired
	TemplateService templateService;

	@Autowired
	private PcmReqWorkflowService pcmWorkflowService;
	
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
	
  @Uri(URI_PREFIX+"/req/list")
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
			List<PcmReqModel> list = pcmReqService.list(params);
			MainMasterModel showDelBtnCfg = masterService.getSystemConfig(MainMasterConstant.SCC_PCM_REQ_DELETE_BUTTON);
			json = PcmReqUtil.jsonSuccess(list, showDelBtnCfg!=null && showDelBtnCfg.getFlag1()!=null && showDelBtnCfg.getFlag1().equals("1"));
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
			
		}
	  
  }
	
	
  /**
   * Handles the "list" request. Note the use of Spring MVC-style annotations to map the Web Script URI configuration
   * and request handling objects.
   * 
   * @param s : searchTerm
   * @param response
   * @throws Exception
   */
//  @Uri(URI_PREFIX+"/list")
  public void _handleList(@RequestParam(required=false) final String s
		  , @RequestParam(required=false) final String fields
		  , @RequestParam(required=false) final Integer start
		  , @RequestParam(required=false) final Integer limit
		  , final WebScriptResponse response)  throws Exception {
    
	    /*
	     * Prepare Search Params
	     */
		Map<String, Object> params = new HashMap<String, Object>();
		
		String searchTerm = null;
		
		if (s != null && !s.equals("")) {
			searchTerm = "%" + s + "%";
		}
		params.put("searchTerm", searchTerm);
		params.put("start", start);
		params.put("limit", limit);
		
		if (fields != null) {
			JSONObject jsObj = new JSONObject(fields);
			
			putOneParam(params, jsObj, "status");
		}
		
		String curUser = authService.getCurrentUserName();
		params.put("loginE", curUser);
		params.put("loginL", "%,"+curUser+",%");
		
		String userRoles = userGroupService.getAuthoritiesForUser(curUser)
						.replace("GROUP_", "")
						.replace("','", ",")
						;
		if (userRoles.startsWith("'")) {
			userRoles = userRoles.substring(1);
		}
		if (userRoles.endsWith("'")) {
			userRoles = userRoles.substring(0, userRoles.length()-1);
		}
		
		String[] roles = userRoles.split(",");
		List<String> roleList = new ArrayList<String>();
		for(int i=0; i<roles.length; i++) {
			if (!roles[i].startsWith("site_")) {
				roleList.add("%,"+roles[i]+",%");
			}
		}
		params.put("roleList",  roleList);
		
		/*
		 * Lookup Fields
		 */
		List<MainMasterModel> criList = masterService.listSystemConfig(MainMasterConstant.SCC_PCM_REQ_CRITERIA);

		StringBuffer lookup = new StringBuffer();
		StringBuffer lookupS = new StringBuffer();
		int lookupIndex = 1;
		
		for(MainMasterModel model : criList) {
			
			if (model.getFlag3().equals("mainMaster") || model.getFlag3().equals("memo/master")) {
				String cond = model.getFlag4();
				String[] conds = cond.split(",");
				String flag2 = model.getFlag2();
				String[] field = flag2.split(",");
				
				String alias = "L"+lookupIndex++;
				
				lookup.append("LEFT JOIN ");
				lookup.append("(SELECT "+MainMasterConstant.TFN_CODE+","+MainMasterConstant.TFN_NAME+" FROM "+MainMasterConstant.TABLE_NAME+" WHERE "+conds[0]+") "+alias+" ");
				lookup.append("ON MEMO."+field[0]+" = "+alias+"."+MainMasterConstant.TFN_CODE+" ");
			}
		}
		
		List<MainMasterModel> colList = masterService.listSystemConfig(MainMasterConstant.SCC_MEMO_GRID_FIELD);
		
		for(MainMasterModel model : colList) {
			
			String flag2 = model.getFlag2();
			if (flag2 != null && !flag2.trim().equals("")) {
				String[] field = flag2.split(",");
				
				if (field[0].equalsIgnoreCase(PcmReqConstant.TFN_CREATED_TIME) 
				 || field[0].equalsIgnoreCase(PcmReqConstant.TFN_UPDATED_TIME))
				{
					lookupS.append("OR to_char("+field[0]+", 'DD/MM/YY') LIKE '"+searchTerm+"' ");
				} else {
					lookupS.append("OR "+field[0]+" LIKE '"+searchTerm+"' ");
				}
			}
		}
		
		params.put("lookup", lookup.toString());
		params.put("lookupS", lookupS.toString());
		
		MainMasterModel searchGridOrderByModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_SEARCH_GRID_ORDER_BY);
		if (searchGridOrderByModel!=null && searchGridOrderByModel.getFlag1()!=null) {
			params.put("orderBy", searchGridOrderByModel.getFlag1());
		} else {
			params.put("orderBy", "ORDER_FIELD, updated_time DESC");
		}
		
		MainMasterModel reqShowGrpModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_REQ_SHOW_GROUP);
		if (reqShowGrpModel!=null && reqShowGrpModel.getFlag1()!=null && reqShowGrpModel.getFlag1().equals("1")) {
			params.put("reqShowGrp", reqShowGrpModel.getFlag1());
		}
		
		MainMasterModel monitorUserModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_MONITOR_USER);
		if (monitorUserModel!=null && monitorUserModel.getFlag1()!=null) {
			String mu = ","+monitorUserModel.getFlag1()+",";
			if (mu.indexOf(","+curUser+",") >= 0) {
				params.put("monitorUser", "1");
			}
		}
		
		for(Entry<String, Object> e : params.entrySet()) {
			log.info("params : "+e.getKey()+":"+e.getValue());
		}

		/*
		 * Search
		 */
		String json = null;
		
		try {
			List<PcmReqModel> list = pcmReqService.list(params);
			MainMasterModel showDelBtnCfg = masterService.getSystemConfig(MainMasterConstant.SCC_PCM_REQ_DELETE_BUTTON);
			json = PcmReqUtil.jsonSuccess(list, showDelBtnCfg.getFlag1().equals("1"));
			
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
  
  @Uri(method=HttpMethod.POST, value=URI_PREFIX+"/save")
  public void handleSave(@RequestParam(required=false) final String id
						,@RequestParam(required=false) final String reqBy
						,@RequestParam(required=false) final String reqBu
		  				,@RequestParam(required=false) final String reqOu
		  				,@RequestParam(required=false) final String objectiveType
		  				,@RequestParam(required=false) final String objective
		  				,@RequestParam(required=false) final String reason
		  				,@RequestParam(required=false) final String currency
		  				,@RequestParam(required=false) final String currencyRate
						,@RequestParam(required=false) final String budgetCc
		  				,@RequestParam(required=false) final String stockOu
						,@RequestParam(required=false) final String prototype
		  				,@RequestParam(required=false) final String event
						,@RequestParam(required=false) final String pcmOu
		  				,@RequestParam(required=false) final String location
						,@RequestParam(required=false) final String acrossBudget
		  				,@RequestParam(required=false) final String refId
		  				,@RequestParam(required=false) final String method
		  				,@RequestParam(required=false) final String methodCond2Rule
		  				,@RequestParam(required=false) final String methodCond2
		  				,@RequestParam(required=false) final String methodCond2Dtl
		  				,@RequestParam(required=false) final String total
		  				,@RequestParam(required=false) final String status
		  				,@RequestParam(required=false) final String dtls
		  				,@RequestParam(required=false) final String files
		  				,final WebScriptResponse response) throws Exception {
	
	String json = null;
	
	try {
		PcmReqModel model = null;
		
		if (CommonUtil.isValidId(id)) {
			model = pcmReqService.get(id);
		}
		
		if (model==null) {
			model = new PcmReqModel();
		}
		
		if (model.getId() == null) {
			model.setStatus(PcmReqConstant.ST_DRAFT);
		}
		
		model.setReqBy(reqBy);
		model.setReqBu(reqBu);
		model.setReqOu(reqOu);
		model.setObjectiveType(objectiveType);
		model.setObjective(objective);
		model.setReason(reason);
		model.setCurrency(currency);
		model.setCurrencyRate(Double.parseDouble(currencyRate));
		model.setBudgetCc(budgetCc);
		model.setStockOu(stockOu);
		model.setPrototype(prototype);
		model.setEvent(event);
		model.setPcmOu(pcmOu);
		model.setLocation(location);
		model.setAcrossBudget(Double.parseDouble(acrossBudget));
		model.setRefId(refId);
		model.setMethod(method);
		model.setMethodCond2Rule(methodCond2Rule);
		model.setMethodCond2(methodCond2Dtl);
		model.setMethodCond2Dtl(methodCond2Dtl);
		model.setTotal(Double.parseDouble(total));
		model.setStatus(status);		
		
		model = pcmReqService.save(model, dtls, files, false);
		
		JSONObject jsObj = new JSONObject();
		jsObj.put("id", model.getId());
		jsObj.put("status", model.getStatus());
		
		json = CommonUtil.jsonSuccess(jsObj);
	} catch (Exception ex) {
		log.error("", ex);
		json = CommonUtil.jsonFail(ex.toString());
		throw ex;
	} finally {
		CommonUtil.responseWrite(response, json);
	}
	  
  }
  
  @Uri(method=HttpMethod.POST, value=URI_PREFIX+"/send")
  public void handleSendToReview(@RequestParam(required=false) final String id
								,@RequestParam(required=false) final String reqBy
								,@RequestParam(required=false) final String reqBu
								,@RequestParam(required=false) final String reqOu
								,@RequestParam(required=false) final String objectiveType
								,@RequestParam(required=false) final String objective
								,@RequestParam(required=false) final String reason
								,@RequestParam(required=false) final String currency
								,@RequestParam(required=false) final String currencyRate
								,@RequestParam(required=false) final String budgetCc
								,@RequestParam(required=false) final String stockOu
								,@RequestParam(required=false) final String prototype
								,@RequestParam(required=false) final String event
								,@RequestParam(required=false) final String pcmOu
								,@RequestParam(required=false) final String location
								,@RequestParam(required=false) final String acrossBudget
								,@RequestParam(required=false) final String refId
								,@RequestParam(required=false) final String method
								,@RequestParam(required=false) final String methodCond2Rule
								,@RequestParam(required=false) final String methodCond2
								,@RequestParam(required=false) final String methodCond2Dtl
								,@RequestParam(required=false) final String total
								,@RequestParam(required=false) final String status
								,@RequestParam(required=false) final String dtls
								,@RequestParam(required=false) final String files
		  				,final WebScriptResponse response) throws Exception {

	String json = null;
	
	try {
		PcmReqModel model = null;
		
		if (CommonUtil.isValidId(id)) {
			model = pcmReqService.get(id);
		}
		
		if (model==null) {
			model = new PcmReqModel();
		}
		model.setWaitingLevel(0);
		
		model.setReqBy(reqBy);
		model.setReqBu(reqBu);
		model.setReqOu(reqOu);
		model.setObjectiveType(objectiveType);
		model.setObjective(objective);
		model.setReason(reason);
		model.setCurrency(currency);
		model.setCurrencyRate(Double.parseDouble(currencyRate));
		model.setBudgetCc(budgetCc);
		model.setStockOu(stockOu);
		model.setPrototype(prototype);
		model.setEvent(event);
		model.setPcmOu(pcmOu);
		model.setLocation(location);
		model.setAcrossBudget(Double.parseDouble(acrossBudget));
		model.setRefId(refId);
		model.setMethod(method);
		model.setMethodCond2Rule(methodCond2Rule);
		model.setMethodCond2(methodCond2Dtl);
		model.setMethodCond2Dtl(methodCond2Dtl);
		model.setTotal(Double.parseDouble(total));
		model.setStatus(status);	
		
		if (model.getId() == null || (status!=null && status.equals(PcmReqConstant.ST_DRAFT))) {
			model.setStatus(PcmReqConstant.ST_WAITING);
			model.setWaitingLevel(1);
		}
		
		JSONObject validateResult = pcmReqService.validateAssignee(model);
		if (!(Boolean)validateResult.get("valid")) {
			json = CommonUtil.jsonFail(validateResult);
		}
		else {
			model = pcmReqService.save(model, dtls, files, true);
			
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


  @Uri(method=HttpMethod.POST, value=URI_PREFIX+"/delete")
  public void handleDelete(@RequestParam final String id, final WebScriptResponse response) throws Exception {
	String json = null;
	
	try {
		pcmReqService.delete(id);
		
		json = CommonUtil.jsonSuccess();
	} catch (Exception ex) {
		log.error("", ex);
		json = CommonUtil.jsonFail(ex.toString());
		throw ex;
	} finally {
		CommonUtil.responseWrite(response, json);
	}
  }

  @Uri(URI_PREFIX+"/req/get")
  public void handleGet(@RequestParam final String id, final WebScriptResponse response)
      throws Exception {
		
	String json = null;

	try {
	  PcmReqModel model = pcmReqService.get(id);

	  List<PcmReqModel> list = new ArrayList<PcmReqModel>();
	  list.add(model);

	  List<PcmReqDtlModel> dtlList = pcmReqService.listDtlByMasterId(id);  

	  json = PcmReqUtil.jsonSuccess(list, dtlList);

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
		
	  List<FileModel> files = pcmReqService.listFile(id);
		
	  json = FileUtil.jsonSuccess(files);
		
	} catch (Exception ex) {
		log.error("", ex);
		json = CommonUtil.jsonFail(ex.toString());
		throw ex;
		
	} finally {
		CommonUtil.responseWrite(response, json);
	}
	
  }
  
  @Uri(URI_PREFIX+"/req/dtl/list")
  public void handleDetailList(@RequestParam final String id, final WebScriptResponse response)
      throws Exception {
		
	String json = null;
	 
	try {
		
//	  json = "{\"success\":true,\"data\":[{\"id\":1,\"type\":\"ครุภัณฑ์\",\"desc\":\"จัดพิมพ์สื่อการเรียนรู้\",\"amt\":14000,\"unit\":\"SET\",\"price\":\"16.05\",\"priceBaht\":\"16.05\",\"total\":\"224,700\",\"action\":\"ED\"}"
//			  +"]}";
	  json = "{\"success\":true,\"data\":["
			  +"]}";
	  
	  
	} catch (Exception ex) {
		log.error("", ex);
		json = CommonUtil.jsonFail(ex.toString());
		throw ex;
		
	} finally {
		CommonUtil.responseWrite(response, json);
	}
	
  }  
  
  @Uri(method=HttpMethod.GET, value=URI_PREFIX+"/preview")
  public void handlePreview(@RequestParam final String id
		  					,final WebScriptResponse response) throws Exception {
	  
	  String tmpDir = FolderUtil.getTmpDir();
	  String fullName = tmpDir+File.separator+id+".pdf";
	  Path path = Paths.get(fullName);
	  byte[] data = Files.readAllBytes(path);
		
	  response.setContentType("application/pdf");
	  java.io.OutputStream out = response.getOutputStream();
	    
	  out.write(data);
	  out.flush();
	  out.close();
	  
	  File file = new File(fullName);
	  file.delete();
  }
  
  @Uri(method=HttpMethod.POST, value=URI_PREFIX+"/preview")
  public void handlePreviewGen(final WebScriptRequest request, final WebScriptResponse response) throws Exception {
	
	String json = null;
	
	try {
		String fileName = pcmReqService.doGenDoc("pr");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(fileName, "");
		
		json = CommonUtil.jsonSuccess(map);
	} catch (Exception ex) {
		log.error("", ex);
		json = CommonUtil.jsonFail(ex.toString());
		throw ex;
	} finally {
		CommonUtil.responseWrite(response, json);
	}
	  
  }
  
  @Uri(URI_PREFIX+"/req/criteria/list")
  public void handleCriteriaList(final WebScriptResponse response)  throws Exception {

		String json = null;
		
		try {
			JSONArray jsArr = pcmReqService.listCriteria();
			json = CommonUtil.jsonSuccess(jsArr);
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
			
		}
    
  }
  
  
  @Uri(URI_PREFIX+"/req/gridfield/list")
  public void handleGridFieldList(final WebScriptResponse response)  throws Exception {

		String json = null;
		
		try {
			JSONArray jsArr = pcmReqService.listGridField();
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
		PcmReqModel model = null;
		
		if (CommonUtil.isValidId(id)) {
			model = pcmReqService.get(id);
		}
		
		if (model==null) {
			model = new PcmReqModel();
		}
		
		if (model.getId() == null || (status!=null && status.equals(PcmReqConstant.ST_DRAFT))) {
			model.setStatus(PcmReqConstant.ST_WAITING);
		}
		
		JSONObject validateResult = pcmReqService.validateAssignee(model);
		if (!(Boolean)validateResult.get("valid")) {
			json = CommonUtil.jsonFail(validateResult);
		}
		else {
			model = pcmReqService.save(model, dtls, files, true);
			pcmWorkflowService.updateWorkflow(model, aug, rug);
			
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
			MainMasterModel rptModel = masterService.getSystemConfig(MainMasterConstant.SCC_PCM_REQ_RPT_TAB);
			
			JSONObject tasks = new JSONObject();
			tasks.put("pcmReqRptTab", rptModel!=null && rptModel.getFlag1()!=null && rptModel.getFlag1().equals("1"));
			
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
	
	  @Uri(URI_PREFIX+"/req/cmt/list")
	  public void handleCmtList(@RequestParam final String objType,
			  					final WebScriptResponse response)  throws Exception {
	
			String json = null;
			
			try {
				List<PcmReqCmtModel> list = pcmReqService.listCmt(objType);
				
				json = PcmReqCmtUtil.jsonSuccess(list);
			} catch (Exception ex) {
				log.error("", ex);
				json = CommonUtil.jsonFail(ex.toString());
				throw ex;
				
			} finally {
				CommonUtil.responseWrite(response, json);
			}
	    
	  }	
	  
	public void genRpt(byte[] file, String fileName, WebScriptResponse response, JasperPrint jasperPrint) throws IOException, JRException {
		
		file = JasperExportManager.exportReportToPdf(jasperPrint);
			
		InputStream is = new ByteArrayInputStream(file);
		response.setContentType("application/pdf");
		
		OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int length;
        while ((length = is.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }
      
        is.close();
        out.flush();	
        out.close();
	}	  
  
}
