package pb.repo.exp.wscript;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.common.exception.FolderNoPermissionException;
import pb.common.model.FileModel;
import pb.common.util.CommonDateTimeUtil;
import pb.common.util.CommonUtil;
import pb.common.util.FileUtil;
import pb.common.util.FolderUtil;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.constant.MainWkfConfigDocTypeConstant;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.service.AdminHrEmployeeService;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminModuleService;
import pb.repo.admin.service.AdminUserGroupService;
import pb.repo.admin.util.MainUtil;
import pb.repo.exp.constant.ExpUseConstant;
import pb.repo.exp.model.ExpUseAttendeeModel;
import pb.repo.exp.model.ExpUseDtlModel;
import pb.repo.exp.model.ExpUseModel;
import pb.repo.exp.service.ExpUseService;
import pb.repo.exp.service.ExpUseWorkflowService;
import pb.repo.exp.util.ExpUseAttendeeUtil;
import pb.repo.exp.util.ExpUseDtlUtil;
import pb.repo.exp.util.ExpUseUtil;

import com.github.dynamicextensionsalfresco.webscripts.annotations.HttpMethod;
import com.github.dynamicextensionsalfresco.webscripts.annotations.RequestParam;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript
public class ExpUseWebScript {
	
	private static Logger log = Logger.getLogger(ExpUseWebScript.class);
	
	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/exp/use";
	
	@Autowired
	private AuthenticationService authService;
	
	@Autowired
	private AdminUserGroupService userGroupService;
	
	@Autowired
	private ExpUseService expUseService;

	@Autowired
	private AdminMasterService masterService;
	
	@Autowired
	private AdminHrEmployeeService adminHrEmployeeService;
	
	@Autowired
	private ExpUseWorkflowService mainWorkflowService;
	
	@Autowired
	private AdminModuleService moduleService;
	
    @Uri(URI_PREFIX+"/list")
    public void handleList(@RequestParam(required=false) final String s
		  	  , @RequestParam(required=false) final String fields
			  , @RequestParam(required=false) final Integer start
			  , @RequestParam(required=false) final Integer limit
			  , @RequestParam(required=false) final String lang
			  , final WebScriptResponse response)  throws Exception {

	  	/*
	  	 * Prepare Criteria
	  	 */
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (s != null && !s.equals("")) {
    		String[] terms = s.split(" ");
        	
    		params.put("terms", terms);
		}
		params.put("start", start);
		params.put("limit", limit);
		
		String curUser = authService.getCurrentUserName();
		params.put("loginE", curUser);
//			params.put("loginL", "%,"+curUser+",%");
		params.put("loginL", curUser);
		
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
//					roleList.add("%,"+roles[i]+",%");
				roleList.add(roles[i]);
			}
		}
		params.put("roleList",  roleList);
		
		
		if (fields != null) {
			JSONObject jsObj = new JSONObject(fields);
			
			putOneParam(params, jsObj, ExpUseConstant.JFN_STATUS);
			putOneParam(params, jsObj, ExpUseConstant.JFN_PAY_TYPE);
		}		
		
		params.put("orderBy", "ORDER_FIELD, updated_time DESC");		
	  
		params.put("lang", lang!=null && lang.startsWith("th") ? "_th" : "");
		
		MainMasterModel monitorUserModel = masterService.getSystemConfig(MainMasterConstant.SCC_MAIN_MONITOR_USER);
		if (monitorUserModel!=null && monitorUserModel.getFlag1()!=null) {
			String mu = ","+monitorUserModel.getFlag1()+",";
			if (mu.indexOf(","+curUser+",") >= 0) {
				params.put("monitorUser", "1");
			}
		}
		
		/*
		 * Search
		 */
		String json = null;
		
		try {
			List<Map<String,Object>> list = expUseService.list(params);
			json = CommonUtil.jsonSuccess(list);
			
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
			MainMasterModel rptModel = masterService.getSystemConfig(MainMasterConstant.SCC_EXP_USE_RPT_TAB);
			
			JSONObject tasks = new JSONObject();
			tasks.put("expUseRptTab", rptModel.getFlag1()!=null && rptModel.getFlag1().equals("1"));
			
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
	
	@Uri(URI_PREFIX+"/gridfield/list")
	public void handleGridFieldList(final WebScriptResponse response)  throws Exception {

		String json = null;
		
		try {
			JSONArray jsArr = expUseService.listGridField();
			json = CommonUtil.jsonSuccess(jsArr);
			
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
			JSONArray jsArr = expUseService.listCriteria();
			json = CommonUtil.jsonSuccess(jsArr);
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
			
		}
	    
	}
	
	@Uri(URI_PREFIX+"/userDtl")
	public void handleInitForm(@RequestParam(required=false) final String r,
			  					 @RequestParam(required=false) final String c,
			  					@RequestParam(required=false) final String lang,
			  					final WebScriptResponse response)
	      throws Exception {
			
		String json = null;

		try {
		  String langSuffix = lang!=null && lang.startsWith("th") ? "_th" : "";
			
		  List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

		  Map<String, Object> map = new HashMap<String, Object>();
		  
		  String reqUser = (r!=null) ? r : authService.getCurrentUserName();
		  
		  Map<String,Object> dtl = adminHrEmployeeService.getWithDtl(reqUser);
		  
		  map.put(ExpUseConstant.JFN_REQ_BY, reqUser);
		  
		  String ename = dtl.get("title"+langSuffix) + " " + dtl.get("first_name"+langSuffix) + " " + dtl.get("last_name"+langSuffix);
		  
		  String mphone = StringUtils.defaultIfEmpty((String)dtl.get("mobile_phone"),"");
		  String wphone = StringUtils.defaultIfEmpty((String)dtl.get("work_phone"),"");
		  String comma = (!mphone.equals("") && !wphone.equals("")) ? "," : "";
		  
		  map.put(ExpUseConstant.JFN_REQ_BY_NAME, ename);
		  map.put(ExpUseConstant.JFN_REQ_TEL_NO, wphone+comma+mphone);
		  map.put(ExpUseConstant.JFN_REQ_BY_DEPT, dtl.get("position"+langSuffix));
		  
		  map.put(ExpUseConstant.JFN_REQ_BU, dtl.get("org_desc"+langSuffix));
		  
		  map.put(ExpUseConstant.JFN_REQ_SECTION_ID, dtl.get("section_id"));
		  map.put(ExpUseConstant.JFN_REQ_SECTION_NAME, dtl.get("section_desc"+langSuffix));
		  
		  String createdUser = (c!=null) ? c : authService.getCurrentUserName();
		  if (!createdUser.equals(reqUser)) {
			  dtl = adminHrEmployeeService.getWithDtl(createdUser);
			  ename = dtl.get("title"+langSuffix) + " " + dtl.get("first_name"+langSuffix) + " " + dtl.get("last_name"+langSuffix);
			  
			  mphone = StringUtils.defaultIfEmpty((String)dtl.get("mobile_phone"),"");
			  wphone = StringUtils.defaultIfEmpty((String)dtl.get("work_phone"),"");
			  comma = (!mphone.equals("") && !wphone.equals("")) ? "," : "";
		  }
		  
		  map.put(ExpUseConstant.JFN_CREATED_BY_SHOW, ename);
		  
		  map.put(ExpUseConstant.JFN_TEL_NO, wphone+comma+mphone);
		  
		  list.add(map);

		  json = CommonUtil.jsonSuccess(list);

		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
		}
		
	}
	
	@Uri(URI_PREFIX+"/attendee/list")
	public void handleAttendeeList(@RequestParam final String id,
								  @RequestParam final String type,
								  @RequestParam final String lang,
								  final WebScriptResponse response)
	      throws Exception {
			
		String json = null;
		 
		try {
			List<Map<String, Object>> list = expUseService.listAttendeeByMasterIdAndType(id, type, lang);
			ExpUseAttendeeUtil.addAction(list);
			
			json = CommonUtil.jsonSuccess(list);
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
		}
		
	}
	
	@Uri(URI_PREFIX+"/item/list")
	public void handleItemList(@RequestParam final String id, final WebScriptResponse response)
	      throws Exception {
			
		String json = null;
		 
		try {
			List<ExpUseDtlModel> list = expUseService.listDtlByMasterId(id);
			ExpUseDtlUtil.addAction(list);
			
			json = CommonUtil.jsonSuccess(list);
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
	
	@Uri(URI_PREFIX+"/get")
	public void handleGet(@RequestParam final String id,
						  @RequestParam final String lang,
						  final WebScriptResponse response)
	      throws Exception {
			
		String json = null;

		try {
		  ExpUseModel model = expUseService.get(id, lang);

		  List<ExpUseModel> list = new ArrayList<ExpUseModel>();
		  list.add(model);

		  json = ExpUseUtil.jsonSuccess(list, (List<ExpUseAttendeeModel>)null);

		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
		}
		
	}
	
	@Uri(method=HttpMethod.POST, value=URI_PREFIX+"/copy")
	public void handleCopy(@RequestParam final String id,
						   @RequestParam(required=false) final String lang,
						   final WebScriptResponse response)
	      throws Exception {
			
		String json = null;
	
		try {
		  String newId = expUseService.copy(id);
	
		  json = CommonUtil.jsonSuccess(newId);
	
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
		}
		
	}
	
	@Uri(method=HttpMethod.POST, value=URI_PREFIX+"/save")
	public void handleSave(@RequestParam(required=false) final String id
							,@RequestParam(required=false) final String reqBy
			  				,@RequestParam(required=false) final String objective
			  				,@RequestParam(required=false) final String reason
			  				,@RequestParam(required=false) final String note
							,@RequestParam(required=false) final String budgetCc
							,@RequestParam(required=false) final String budgetCcType
							,@RequestParam(required=false) final String fundId
							,@RequestParam(required=false) final String costControlTypeId
							,@RequestParam(required=false) final String costControlId
							,@RequestParam(required=false) final String costControl
							,@RequestParam(required=false) final String costControlFrom
							,@RequestParam(required=false) final String costControlTo
							,@RequestParam(required=false) final String bankType
							,@RequestParam(required=false) final String bank
							,@RequestParam(required=false) final String payType
							,@RequestParam(required=false) final String payDtl1
							,@RequestParam(required=false) final String payDtl2
							,@RequestParam(required=false) final String payDtl3
							,@RequestParam(required=false) final String total
			  				,@RequestParam(required=false) final String attendees
			  				,@RequestParam(required=false) final String items
			  				,@RequestParam(required=false) final String files
			  				,final WebScriptResponse response) throws Exception {
		
		String json = null;
		
		try {
			ExpUseModel model = null;
			
			if (CommonUtil.isValidId(id)) {
				model = expUseService.get(id, null);
			}
			
			if (model==null) {
				model = new ExpUseModel();
			}
			
			model.setReqBy(reqBy);
			
			model.setObjective(objective);
			model.setReason(reason);
			model.setNote(note);
			
			if (budgetCc != null && !budgetCc.equals("")) {
				model.setBudgetCc(Integer.parseInt(budgetCc));
			}
			model.setBudgetCcType(budgetCcType);
			if (fundId != null && !fundId.equals("")) {
				model.setFundId(Integer.parseInt(fundId));
			}
			
			model.setCostControlTypeId((costControlTypeId != null && !costControlTypeId.equals("")) ? Integer.parseInt(costControlTypeId) : null);
			model.setCostControlId((costControlId != null && !costControlId.equals("")) ? Integer.parseInt(costControlId) : null);
			model.setCostControl(costControl);
			if (costControlFrom!=null) {
				model.setCostControlFrom(CommonDateTimeUtil.convertSenchaStringToTimestamp(costControlFrom));
			}
			if (costControlTo!=null) {
				model.setCostControlTo(CommonDateTimeUtil.convertSenchaStringToTimestamp(costControlTo));
			}
			
			model.setBankType(bankType);
			if (bank != null && !bank.equals("")) {
				model.setBank(Integer.parseInt(bank));
			}
			
			model.setPayType(payType);
			model.setPayDtl1(payDtl1);
			model.setPayDtl2(payDtl2);
			model.setPayDtl3(payDtl3);
			
			model.setTotal(Double.parseDouble(total!=null && !total.equals("") ? total : "0"));
			model.setStatus(ExpUseConstant.ST_DRAFT);
			
			log.info("model="+model);
			model = expUseService.save(model, attendees, items, files, false);
			
			JSONObject jsObj = new JSONObject();
			jsObj.put("id", model.getId());
			jsObj.put("status", model.getStatus());
			
			json = CommonUtil.jsonSuccess(jsObj);
		} catch (FolderNoPermissionException ex) {
			json = CommonUtil.jsonFail(ex.getMessage());
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
			  				,@RequestParam(required=false) final String objective
			  				,@RequestParam(required=false) final String reason
			  				,@RequestParam(required=false) final String note
							,@RequestParam(required=false) final String budgetCc
							,@RequestParam(required=false) final String budgetCcType
							,@RequestParam(required=false) final String fundId
							,@RequestParam(required=false) final String costControlTypeId
							,@RequestParam(required=false) final String costControlId
							,@RequestParam(required=false) final String costControl
							,@RequestParam(required=false) final String costControlFrom
							,@RequestParam(required=false) final String costControlTo
							,@RequestParam(required=false) final String bankType
							,@RequestParam(required=false) final String bank
							,@RequestParam(required=false) final String payType
							,@RequestParam(required=false) final String payDtl1
							,@RequestParam(required=false) final String payDtl2
							,@RequestParam(required=false) final String payDtl3
							,@RequestParam(required=false) final String total
			  				,@RequestParam(required=false) final String attendees
			  				,@RequestParam(required=false) final String items
			  				,@RequestParam(required=false) final String files
							,@RequestParam(required=false) final String status
							,@RequestParam(required=false) String lang
			  				,final WebScriptResponse response) throws Exception {
		
		String json = null;
		
		try {
			ExpUseModel model = null;
			
			if (CommonUtil.isValidId(id)) {
				model = expUseService.get(id, null);
			}
			
			if (model==null) {
				model = new ExpUseModel();
			}
			
			if (model.getId() == null) {
				model.setStatus(ExpUseConstant.ST_DRAFT);
			}
			
			model.setReqBy(reqBy);
			
			model.setObjective(objective);
			model.setReason(reason);
			model.setNote(note);
			
			if (budgetCc != null && !budgetCc.equals("")) {
				model.setBudgetCc(Integer.parseInt(budgetCc));
			}
			model.setBudgetCcType(budgetCcType);
			if (fundId != null && !fundId.equals("")) {
				model.setFundId(Integer.parseInt(fundId));
			}
			
			model.setCostControlTypeId((costControlTypeId != null && !costControlTypeId.equals("")) ? Integer.parseInt(costControlTypeId) : null);
			model.setCostControlId((costControlId != null && !costControlId.equals("")) ? Integer.parseInt(costControlId) : null);
			model.setCostControl(costControl);
			if (costControlFrom!=null) {
				model.setCostControlFrom(CommonDateTimeUtil.convertSenchaStringToTimestamp(costControlFrom));
			}
			if (costControlTo!=null) {
				model.setCostControlTo(CommonDateTimeUtil.convertSenchaStringToTimestamp(costControlTo));
			}
			
			model.setBankType(bankType);
			if (bank != null && !bank.equals("")) {
				model.setBank(Integer.parseInt(bank));
			}
			
			model.setPayType(payType);
			model.setPayDtl1(payDtl1);
			model.setPayDtl2(payDtl2);
			model.setPayDtl3(payDtl3);
			
			model.setTotal(Double.parseDouble(total));
			model.setStatus(status);
			
			model.setRequestedTime(CommonDateTimeUtil.now());
			
			if (model.getId() == null || (status!=null && status.equals(ExpUseConstant.ST_DRAFT))) {
				model.setStatus(ExpUseConstant.ST_WAITING);
				model.setWaitingLevel(1);
			}
			
			log.info("model="+model);
			JSONObject validateResult = expUseService.validateAssignee(model);
			if (!(Boolean)validateResult.get("valid")) {
				json = CommonUtil.jsonFail(validateResult);
			}
			else {
				JSONObject validateWfPath = expUseService.validateWfPath(model);
				if (!(Boolean)validateWfPath.get("valid")) {
					json = CommonUtil.jsonFail(validateWfPath);
				}
				else {
					MainMasterModel chkBudgetModel = masterService.getSystemConfig(MainMasterConstant.SCC_MAIN_INF_CHECK_BUDGET);
					
					Boolean checkBudget = chkBudgetModel.getFlag1().equals(CommonConstant.V_ENABLE);
					Boolean budgetOk = false;
					String budgetResult = null;
					if (checkBudget) {
						Map<String, Object> budget = moduleService.getTotalPreBudget(budgetCcType, model.getBudgetCc(), model.getFundId(), null, null);
						budgetOk = Double.parseDouble(((String)budget.get("balance")).replaceAll(",", "")) >= model.getTotal();
						if (!budgetOk) {
							String errMsg = MainUtil.getMessageWithOutCode("ERR_WF_BUDGET_NOT_ENOUGH", new Locale(lang));
							budgetResult = errMsg;
						}
						
//						Double budgetBalance = interfaceService.getBudget(model.getBudgetCcType(), model.getBudgetCc(), model.getFundId(), model.getCreatedBy());
//						log.info("Budget Balance:"+budgetBalance);
//						budgetOk = (Boolean)budgetResult.get("budget_ok");
					}
					
					if (checkBudget && !budgetOk) {
						JSONObject data = new JSONObject();
						data.put("valid", false);
						data.put("msg", budgetResult);
						json = CommonUtil.jsonFail(data);
					} else {
						model = expUseService.save(model, attendees, items, files, true);
						
						mainWorkflowService.setModuleService(expUseService);
						mainWorkflowService.startWorkflow(model, MainWkfConfigDocTypeConstant.DT_EX);
						
						JSONObject jsObj = new JSONObject();
						jsObj.put("id", model.getId());
						jsObj.put("status", model.getStatus());
						
						json = CommonUtil.jsonSuccess(jsObj);
					}
				}
			}
			
		} catch (FolderNoPermissionException ex) {
			json = CommonUtil.jsonFail(ex.getMessage());
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
			
		  List<FileModel> files = expUseService.listFile(id, false);
			
		  json = FileUtil.jsonSuccess(files);
			
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
			expUseService.delete(id);
			
			json = CommonUtil.jsonSuccess();
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
		} finally {
			CommonUtil.responseWrite(response, json);
		}
	}
	
    @Uri(URI_PREFIX+"/po/list")
    public void handlePoList(@RequestParam(required=false) final String u
			  , final WebScriptResponse response)  throws Exception {

	  	/*
	  	 * Prepare Criteria
	  	 */
		Map<String, Object> params = new HashMap<String, Object>();
		
		/*
		 * Search
		 */
		String json = null;
		
		try {
			List<Map<String, Object>> list = expUseService.listPO(params);
			json = CommonUtil.jsonSuccess(list);
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
		}
	  
    }
    
    @Uri(URI_PREFIX+"/asset/list")
    public void handleAssetList(@RequestParam(required=false) final String u
			  , final WebScriptResponse response)  throws Exception {

	  	/*
	  	 * Prepare Criteria
	  	 */
		Map<String, Object> params = new HashMap<String, Object>();
		
		/*
		 * Search
		 */
		String json = null;
		
		try {
			List<Map<String, Object>> list = expUseService.listAsset(params);
			json = CommonUtil.jsonSuccess(list);
			
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
	public void handlePreviewGen(@RequestParam(required=false) final String id
								,@RequestParam(required=false) final String reqBy
								,@RequestParam(required=false) final String objective
								,@RequestParam(required=false) final String reason
				  				,@RequestParam(required=false) final String note
								,@RequestParam(required=false) final String budgetCc
								,@RequestParam(required=false) final String budgetCcType
								,@RequestParam(required=false) final String fundId
								,@RequestParam(required=false) final String costControlTypeId
								,@RequestParam(required=false) final String costControlId
								,@RequestParam(required=false) final String costControl
								,@RequestParam(required=false) final String costControlFrom
								,@RequestParam(required=false) final String costControlTo
								,@RequestParam(required=false) final String bankType
								,@RequestParam(required=false) final String bank
								,@RequestParam(required=false) final String payType
								,@RequestParam(required=false) final String payDtl1
								,@RequestParam(required=false) final String payDtl2
								,@RequestParam(required=false) final String payDtl3
								,@RequestParam(required=false) final String total
								,@RequestParam(required=false) final String attendees
								,@RequestParam(required=false) final String items
								,@RequestParam(required=false) final String files
								,@RequestParam(required=false) final String status
								,final WebScriptResponse response) throws Exception {
		
		String json = null;
		
		try {
			ExpUseModel model = null;
			
			if (CommonUtil.isValidId(id)) {
				model = expUseService.get(id,null);
			}
			
			if (model==null) {
				model = new ExpUseModel();
			}
			
			if (model.getId() == null) {
				model.setStatus(ExpUseConstant.ST_DRAFT);
			}
			
			model.setReqBy(reqBy);
			
			model.setObjective(objective);
			model.setReason(reason);
			model.setNote(note);
			
			if (budgetCc != null && !budgetCc.equals("")) {
				model.setBudgetCc(Integer.parseInt(budgetCc));
			}
			model.setBudgetCcType(budgetCcType);
			if (fundId != null && !fundId.equals("")) {
				model.setFundId(Integer.parseInt(fundId));
			}
			
			model.setCostControlTypeId((costControlTypeId != null && !costControlTypeId.equals("")) ? Integer.parseInt(costControlTypeId) : null);
			model.setCostControlId((costControlId != null && !costControlId.equals("")) ? Integer.parseInt(costControlId) : null);
			model.setCostControl(costControl);
			if (costControlFrom!=null) {
				model.setCostControlFrom(CommonDateTimeUtil.convertSenchaStringToTimestamp(costControlFrom));
			}
			if (costControlTo!=null) {
				model.setCostControlTo(CommonDateTimeUtil.convertSenchaStringToTimestamp(costControlTo));
			}
			
			model.setBankType(bankType);
			if (bank != null && !bank.equals("")) {
				model.setBank(Integer.parseInt(bank));
			}
			
			model.setPayType(payType);
			model.setPayDtl1(payDtl1);
			model.setPayDtl2(payDtl2);
			model.setPayDtl3(payDtl3);
			
			model.setTotal(Double.parseDouble(total));
			model.setStatus(status);
			
			if (model.getId() == null || (status!=null && status.equals(ExpUseConstant.ST_DRAFT))) {
				model.setStatus(ExpUseConstant.ST_WAITING);
				model.setWaitingLevel(1);
			}
			
			JSONObject validateResult = expUseService.validateAssignee(model);
			if (!(Boolean)validateResult.get("valid")) {
				json = CommonUtil.jsonFail(validateResult);
			}
			else {		
				model.setAttendeeList(ExpUseAttendeeUtil.convertJsonToList(attendees, model.getId()));
				model.setDtlList(ExpUseDtlUtil.convertJsonToList(items, model.getId()));
				String fileName = expUseService.doGenDoc(ExpUseConstant.JR_EX, model);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(fileName, "");
				
				json = CommonUtil.jsonSuccess(map);
			}
		} catch (FolderNoPermissionException ex) {
			json = CommonUtil.jsonFail(ex.getMessage());
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
		} finally {
			CommonUtil.responseWrite(response, json);
		}
		  
	}
	
	@Uri(method=HttpMethod.GET, value=URI_PREFIX+"/paymentDoc")
	public void handlePaymentDoc(@RequestParam final String id
			  					,final WebScriptResponse response) throws Exception {
		  
		  String tmpDir = FolderUtil.getTmpDir();
		  String fullName = tmpDir+File.separator+id+".xls";
		  Path path = Paths.get(fullName);
		  byte[] data = Files.readAllBytes(path);
			
		  response.setContentType("application/vnd.ms-excel");
		  response.addHeader("Content-Disposition", "attachment; filename=paymentDoc.xls");
		  java.io.OutputStream out = response.getOutputStream();
		    
		  out.write(data);
		  out.flush();
		  out.close();
		  
		  File file = new File(fullName);
		  file.delete();
	}
	
	@Uri(method=HttpMethod.POST, value=URI_PREFIX+"/paymentDoc")
	public void handlePaymentDocGen(@RequestParam(required=false) final String id
								,@RequestParam(required=false) final String reqBy
								,@RequestParam(required=false) final String objective
								,@RequestParam(required=false) final String budgetCc
								,@RequestParam(required=false) final String budgetCcType
								,@RequestParam(required=false) final String fundId
								,@RequestParam(required=false) final String costControlTypeId
								,@RequestParam(required=false) final String costControlId
								,@RequestParam(required=false) final String costControl
								,@RequestParam(required=false) final String costControlFrom
								,@RequestParam(required=false) final String costControlTo
								,@RequestParam(required=false) final String bankType
								,@RequestParam(required=false) final String bank
								,@RequestParam(required=false) final String payType
								,@RequestParam(required=false) final String payDtl1
								,@RequestParam(required=false) final String payDtl2
								,@RequestParam(required=false) final String payDtl3
								,@RequestParam(required=false) final String total
								,@RequestParam(required=false) final String attendees
								,@RequestParam(required=false) final String items
								,@RequestParam(required=false) final String files
								,@RequestParam(required=false) final String status
								,final WebScriptResponse response) throws Exception {
		
		String json = null;
		
		try {
			ExpUseModel model = null;
			
			if (CommonUtil.isValidId(id)) {
				model = expUseService.get(id,null);
			}
			
			if (model==null) {
				model = new ExpUseModel();
			}
			
			if (model.getId() == null) {
				model.setStatus(ExpUseConstant.ST_DRAFT);
			}
			
			model.setReqBy(reqBy);
			
			model.setObjective(objective);
			if (budgetCc != null && !budgetCc.equals("")) {
				model.setBudgetCc(Integer.parseInt(budgetCc));
			}
			model.setBudgetCcType(budgetCcType);
			if (fundId != null && !fundId.equals("")) {
				model.setFundId(Integer.parseInt(fundId));
			}
			
			model.setCostControlTypeId((costControlTypeId != null && !costControlTypeId.equals("")) ? Integer.parseInt(costControlTypeId) : null);
			model.setCostControlId((costControlId != null && !costControlId.equals("")) ? Integer.parseInt(costControlId) : null);
			model.setCostControl(costControl);
			if (costControlFrom!=null) {
				model.setCostControlFrom(CommonDateTimeUtil.convertSenchaStringToTimestamp(costControlFrom));
			}
			if (costControlTo!=null) {
				model.setCostControlTo(CommonDateTimeUtil.convertSenchaStringToTimestamp(costControlTo));
			}
			
			model.setBankType(bankType);
			if (bank != null && !bank.equals("")) {
				model.setBank(Integer.parseInt(bank));
			}
			
			model.setPayType(payType);
			model.setPayDtl1(payDtl1);
			model.setPayDtl2(payDtl2);
			model.setPayDtl3(payDtl3);
			
			model.setTotal(Double.parseDouble(total));
			model.setStatus(status);
			
			if (model.getId() == null || (status!=null && status.equals(ExpUseConstant.ST_DRAFT))) {
				model.setStatus(ExpUseConstant.ST_WAITING);
				model.setWaitingLevel(1);
			}
			
			JSONObject validateResult = expUseService.validateAssignee(model);
			if (!(Boolean)validateResult.get("valid")) {
				json = CommonUtil.jsonFail(validateResult);
			}
			else {		
				model.setAttendeeList(ExpUseAttendeeUtil.convertJsonToList(attendees, model.getId()));
				model.setDtlList(ExpUseDtlUtil.convertJsonToList(items, model.getId()));
				String fileName = expUseService.doGenPaymentDoc(ExpUseConstant.JR_EX_PAYMENT_DOC, model);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(fileName, "");
				
				json = CommonUtil.jsonSuccess(map);
			}
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
							,@RequestParam(required=false) final String reqBy
			  				,@RequestParam(required=false) final String objective
			  				,@RequestParam(required=false) final String reason
			  				,@RequestParam(required=false) final String note
							,@RequestParam(required=false) final String budgetCc
							,@RequestParam(required=false) final String budgetCcType
							,@RequestParam(required=false) final String fundId
							,@RequestParam(required=false) final String costControlTypeId
							,@RequestParam(required=false) final String costControlId
							,@RequestParam(required=false) final String costControl
							,@RequestParam(required=false) final String costControlFrom
							,@RequestParam(required=false) final String costControlTo
							,@RequestParam(required=false) final String bankType
							,@RequestParam(required=false) final String bank
							,@RequestParam(required=false) final String payType
							,@RequestParam(required=false) final String payDtl1
							,@RequestParam(required=false) final String payDtl2
							,@RequestParam(required=false) final String payDtl3
							,@RequestParam(required=false) final String total
			  				,@RequestParam(required=false) final String attendees
			  				,@RequestParam(required=false) final String items
			  				,@RequestParam(required=false) final String files
							,@RequestParam(required=false) final String status
			  				,final WebScriptResponse response) throws Exception {
		
		String json = null;
		
		try {
			ExpUseModel model = null;
			
			if (CommonUtil.isValidId(id)) {
				model = expUseService.get(id, null);
			}
			
			if (model==null) {
				model = new ExpUseModel();
			}
			model.setWaitingLevel(0);
			
			model.setReqBy(reqBy);
			
			model.setObjective(objective);
			model.setReason(reason);
			model.setNote(note);
			
			if (budgetCc != null && !budgetCc.equals("")) {
				model.setBudgetCc(Integer.parseInt(budgetCc));
			}
			model.setBudgetCcType(budgetCcType);
			if (fundId != null && !fundId.equals("")) {
				model.setFundId(Integer.parseInt(fundId));
			}
			
			model.setCostControlTypeId((costControlTypeId != null && !costControlTypeId.equals("")) ? Integer.parseInt(costControlTypeId) : null);
			model.setCostControlId((costControlId != null && !costControlId.equals("")) ? Integer.parseInt(costControlId) : null);
			model.setCostControl(costControl);
			if (costControlFrom!=null) {
				model.setCostControlFrom(CommonDateTimeUtil.convertSenchaStringToTimestamp(costControlFrom));
			}
			if (costControlTo!=null) {
				model.setCostControlTo(CommonDateTimeUtil.convertSenchaStringToTimestamp(costControlTo));
			}
			
			model.setBankType(bankType);
			if (bank != null && !bank.equals("")) {
				model.setBank(Integer.parseInt(bank));
			}
			
			model.setPayType(payType);
			model.setPayDtl1(payDtl1);
			model.setPayDtl2(payDtl2);
			model.setPayDtl3(payDtl3);
			
			model.setTotal(Double.parseDouble(total));
			model.setStatus(status);
			
			if (model.getId() == null || (status!=null && status.equals(ExpUseConstant.ST_DRAFT))) {
				model.setStatus(ExpUseConstant.ST_WAITING);
				model.setWaitingLevel(1);
			}
			
			log.info("model="+model);
			JSONObject validateResult = expUseService.validateAssignee(model);
			if (!(Boolean)validateResult.get("valid")) {
				json = CommonUtil.jsonFail(validateResult);
			}
			else {
				JSONObject validateWfPath = expUseService.validateWfPath(model);
				if (!(Boolean)validateWfPath.get("valid")) {
					json = CommonUtil.jsonFail(validateWfPath);
				}
				else {
					model = expUseService.save(model, attendees, items, files, true);
					
					mainWorkflowService.setModuleService(expUseService);
					mainWorkflowService.updateWorkflow(model, null, null, MainWkfConfigDocTypeConstant.DT_EX);
					
					JSONObject jsObj = new JSONObject();
					jsObj.put("id", model.getId());
					jsObj.put("status", model.getStatus());
					
					json = CommonUtil.jsonSuccess(jsObj);
				}
			}
			
		} catch (FolderNoPermissionException ex) {
			json = CommonUtil.jsonFail(ex.getMessage());
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
		} finally {
			CommonUtil.responseWrite(response, json);
		}
		  
	}
	
}
