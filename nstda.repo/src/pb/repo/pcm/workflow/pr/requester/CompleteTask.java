package pb.repo.pcm.workflow.pr.requester;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.forms.FormException;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.repo.version.VersionModel;
import org.alfresco.repo.workflow.activiti.ActivitiScriptNode;
import org.alfresco.repo.workflow.activiti.ActivitiScriptNodeList;
import org.alfresco.service.cmr.coci.CheckOutCheckInService;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.TemplateService;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.cmr.version.VersionType;
import org.alfresco.service.cmr.workflow.WorkflowInstance;
import org.alfresco.service.cmr.workflow.WorkflowService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.common.util.CommonDateTimeUtil;
import pb.common.util.NodeUtil;
import pb.common.util.PersonUtil;
import pb.repo.admin.constant.MainCompleteNotificationConstant;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.model.MainCompleteNotificationModel;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.service.AdminCompleteNotificationService;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminViewerService;
import pb.repo.admin.service.AlfrescoService;
import pb.repo.pcm.constant.PcmReqConstant;
import pb.repo.pcm.constant.PcmWorkflowConstant;
import pb.repo.pcm.model.PcmReqModel;
import pb.repo.pcm.model.PcmWorkflowHistoryModel;
import pb.repo.pcm.model.PcmWorkflowModel;
import pb.repo.pcm.service.PcmReqService;
import pb.repo.pcm.service.PcmReqWorkflowService;
import pb.repo.pcm.service.PcmSignatureService;
import pb.repo.pcm.util.PcmReqUtil;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

@Component("pb.pcm.workflow.pr.requester.CompleteTask")
public class CompleteTask implements TaskListener {
	
	private static Logger log = Logger.getLogger(CompleteTask.class);
	
	private static final long serialVersionUID = 1L;
	
	Properties properties = new Properties();
	
	@Autowired
	FileFolderService fileFolderService;
	
	@Autowired
	ContentService contentService;
	
	@Autowired
	CheckOutCheckInService checkOutCheckInService;
	
	@Autowired
	NodeService nodeService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	AuthenticationUtil authenticationUtil;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@Autowired
	PcmReqService pcmReqService;
	
	@Autowired
	PcmReqWorkflowService pcmWorkflowService;

	@Autowired
	PcmSignatureService memoSignatureService;
	
	@Autowired
	AdminMasterService adminMasterService;
	
	@Autowired
	AuthorityService authorityService;
	
	@Autowired
	AlfrescoService alfrescoService;
	
	@Autowired
	AdminCompleteNotificationService completeNotificationService;
	
	@Autowired
	WorkflowService workflowService;
	
	@Autowired
	AdminViewerService viewerService;
	
	@Autowired
	TemplateService templateService;

	
	private static final String WF_PREFIX = PcmWorkflowConstant.MODEL_PREFIX;
	
	public void notify(final DelegateTask task)  {
		
		log.info("<- pr.requester.CompleteTask ->");
		
		AuthenticationUtil.runAs(new RunAsWork<String>() {
			public String doWork() throws Exception
			{
				log.info("  task.getTaskDefinitionKey():" + task.getTaskDefinitionKey());
				log.info("  task.id="+task.getId());
				log.info("  task.Description="+task.getDescription());
				log.info("  task.EventName="+task.getEventName());
				log.info("  task.Name="+task.getName());
				log.info("  task.Owner="+task.getOwner());
				
				try {
					Object id = ObjectUtils.defaultIfNull(task.getVariable(WF_PREFIX+"id"), "");
					log.info("  id :: " + id.toString());
					PcmReqModel model = pcmReqService.get(id.toString());
					Integer level = model.getWaitingLevel();
					ExecutionEntity executionEntity = ((ExecutionEntity)task.getExecution()).getProcessInstance();
					
					String curUser = authenticationService.getCurrentUserName();
					String taskKey = task.getTaskDefinitionKey();
					String action = task.getVariable(WF_PREFIX+"reSubmitOutcome")!=null ? task.getVariable(WF_PREFIX+"reSubmitOutcome").toString():null;
					
					executionEntity.setVariable(WF_PREFIX+"reSubmitOutcome", action);
					
					updateExecutionEntity(executionEntity, task, "requestedTime");
					updateExecutionEntity(executionEntity, task, "remark");
					
					updateExecutionEntity(executionEntity, task, "document");
					updateExecutionEntity(executionEntity, task, "attachDocument");
				}
				catch (Exception ex) {
					log.error(ex);
				}
				
				return null;
			}
		}, AuthenticationUtil.getAdminUserName()); // runAs()
	}
	
	public void _notify(final DelegateTask task)  {
		
		log.info("<- CompleteTask ->");
		
		AuthenticationUtil.runAs(new RunAsWork<String>() {
			public String doWork() throws Exception
			{
				log.info("  task.getTaskDefinitionKey():" + task.getTaskDefinitionKey());
				log.info("  task.id="+task.getId());
				log.info("  task.Description="+task.getDescription());
				log.info("  task.EventName="+task.getEventName());
				log.info("  task.Name="+task.getName());
				log.info("  task.Owner="+task.getOwner());
				
				try {
					
					Object memoId = ObjectUtils.defaultIfNull(task.getVariable(WF_PREFIX+"memoId"), "");
					log.info("  memoId :: " + memoId.toString());
					PcmReqModel memoModel = pcmReqService.get(memoId.toString());
					Integer level = memoModel.getWaitingLevel();
					ExecutionEntity executionEntity = ((ExecutionEntity)task.getExecution()).getProcessInstance();

					String curUser = authenticationService.getCurrentUserName();
					String taskKey = task.getTaskDefinitionKey();
					String action = task.getVariable(WF_PREFIX+"approveRejectOutcome")!=null ? task.getVariable(WF_PREFIX+"approveRejectOutcome").toString():null;
					Object completeOutcome = ObjectUtils.defaultIfNull(task.getVariable(WF_PREFIX+"completeOutcome"), "");
					String reSubmitOutcome = task.getVariable(WF_PREFIX+"reSubmitOutcome")!=null ? task.getVariable(WF_PREFIX+"reSubmitOutcome").toString():null;
					if (taskKey.equalsIgnoreCase("requesterTask")) action = reSubmitOutcome;
					executionEntity.setVariable(WF_PREFIX+"reSubmitOutcome", reSubmitOutcome);
					log.info("  reSubmitOutcome="+reSubmitOutcome);

					int lastLevel = getLastReviewerLevel(memoModel);
					
					log.info("  level="+level);
					if (action.equals("Approve") && task.getTaskDefinitionKey().equals("reviewer"+(level)+"Task") && (level != lastLevel)) {
						Object lst = task.getVariable(WF_PREFIX+"reviewer"+(level+1)+"List");
						log.info("  check approver list '"+task.getVariable(WF_PREFIX+"reviewer"+(level+1)+"List")+"'   "+lst.getClass().getName()+":"+((List)lst).isEmpty());
						
						boolean isEmpty = ((List)lst).isEmpty();
						if (!isEmpty) {
							if (((List)lst).size() == 1) {
								Object o = ((List)lst).get(0);
								if (o.equals("")) {
									isEmpty = true;
								}
							}
						}
						
						if (isEmpty) {
							log.info("    List is empty");
							MainMasterModel msgModel = adminMasterService.getSystemConfig(MainMasterConstant.SCC_MEMO_MSG_MISSING_NEXT_APP);
							throw new FormException("|BG_ERR|"+msgModel.getFlag1());
						}
					}
					
					if (action.equals("Approve")) { 
						int condNo = 1;
						MainMasterModel failCondModel = adminMasterService.getSystemConfig(MainMasterConstant.SCC_MEMO_WF_FAIL_COND+"_"+condNo);
						while (failCondModel != null) {
							
							String[] fs = failCondModel.getFlag1().split(",");
							
							if (fs.length>0) {
								String lvl = fs[0];
								String cond = fs[1];
								String msg = fs[2];
								log.info("fs[0]:"+lvl);
								log.info("fs[1]:"+cond);
								log.info("fs[2]:"+msg);
								
								if (Integer.parseInt(lvl) == level) {
									boolean matched = false;
								
									String[] cs = cond.split("\\|");
									for(int i=0; i<cs.length; i++) {
										log.info("cs["+i+"]:"+cs[i]);
										matched = true;
										
										String f = null;
										String v = null;
										
										int pos = cs[i].indexOf("!=");
										if (pos >= 0) {
											log.info("*** != ***");
											f = cs[i].substring(0,pos);
											v = cs[i].substring(pos+2);
										} else {
											pos= cs[i].indexOf("=");
											if (pos >= 0) {
												log.info("*** = ***");
												f = cs[i].substring(0,pos);
												v = cs[i].substring(pos+1);
											}
										}
										log.info("---"+f+":"+v);
										
										Object obj = ObjectUtils.defaultIfNull(task.getVariable(WF_PREFIX+f), "");
										if (v.startsWith("'")) {
											v = v.substring(1, v.length()-1);
										}
										
										pos = cs[i].indexOf("!=");
										if (pos >= 0) {
											matched = matched && (obj==null || !obj.equals(v));
										} else {
											pos= cs[i].indexOf("=");
											if (pos >= 0) {
												matched = matched && (obj!=null && obj.equals(v));
											}
										}
										
									}
								
									if (matched) {
										throw new FormException("|BG_ERR|"+msg);
									}
								}
							}
							
							condNo++;
							failCondModel = adminMasterService.getSystemConfig(MainMasterConstant.SCC_MEMO_WF_FAIL_COND+"_"+condNo);
						}
					
					}
					
//					List<Object> fields = new ArrayList<Object>();
//					for (int i=1; i<=PcmReqConstant.MAX_DUMMY_FIELD; i++) {
////						fields.add(ObjectUtils.defaultIfNull(task.getVariable(WF_PREFIX+"field"+i), ""));
//						updateExecutionEntity(executionEntity, task, "field"+i);
//					}
					
					updateExecutionEntity(executionEntity, task, "requestedTime");
					updateExecutionEntity(executionEntity, task, "remark");
					
					Object nextActionUserAssignee = updateExecutionEntity(executionEntity, task, "nextActionUserAssignee");
					Object nextActionGroupAssignee = updateExecutionEntity(executionEntity, task, "nextActionGroupAssignee");
					Object targetFolderNodeRef = ObjectUtils.defaultIfNull(executionEntity.getVariable(WF_PREFIX+"targetFolderNodeRef"), "");
					Date date = new Date();

					for(int i=1;i<=CommonConstant.MAX_APPROVER;i++) {
						
						if(taskKey.equalsIgnoreCase("reviewer"+i+"Task")) {
							executionEntity.setVariable(WF_PREFIX+"reviewer"+i+"Assignee", curUser);
							executionEntity.setVariable(WF_PREFIX+"reviewer"+i+"SignDate", date);
							
							executionEntity.setVariable(WF_PREFIX+"approveRejectOutcome", action);
							
							break;
						}
						
						updateExecutionEntity(executionEntity, task, "rewarning"+i);
						updateExecutionEntity(executionEntity, task, "hint"+i);
					}
					log.info("  action="+action);
					executionEntity.setVariable(WF_PREFIX+"workflowStatus", action);
					
					// Comment History
					String taskComment = "";
					Object tmpComment = task.getVariable("bpm_comment");
					if(tmpComment != null && !tmpComment.equals("")){
						taskComment = tmpComment.toString();
					}
					
					String defaultAction = action;
					
					log.info("  completeOutcome :: " + completeOutcome);
					if(completeOutcome.equals("Completed Workflow")) {
						memoModel.setId(memoId.toString());
						memoModel.setStatus(PcmReqConstant.ST_CLOSED_BY_ACT);
						memoModel.setWaitingLevel(null);
						action = "Completed Workflow";
//						createPaperHistory(memoId.toString(), targetFoelderNodeRef.toString(), executionEntity);
					} 
					else 
					if(reSubmitOutcome!=null && reSubmitOutcome.equalsIgnoreCase("Cancel")) {
						memoModel.setId(memoId.toString());
						memoModel.setStatus(PcmReqConstant.ST_CANCEL_BY_REQ);
						memoModel.setWaitingLevel(null);
						action = reSubmitOutcome.toString();
					}
					else
					if (action.equalsIgnoreCase("Reject")) {
						memoModel.setId(memoId.toString());
						memoModel.setWaitingLevel(memoModel.getWaitingLevel()-1);
					}
					else {
						memoModel.setWaitingLevel(memoModel.getWaitingLevel()!=null ? memoModel.getWaitingLevel()+1 : null);
					}
					
					// Keep TaskId to memwf:taskHistory.
					String totalTaskHistory = (String)executionEntity.getVariable(WF_PREFIX+"taskHistory");
					executionEntity.setVariable(WF_PREFIX+"taskHistory", totalTaskHistory + "|" + taskKey);
					log.info("  taskHistory:" + totalTaskHistory);

					log.info("  status : "+memoModel.getStatus()+", waitingLevel:"+memoModel.getWaitingLevel());
					update(memoModel, nextActionUserAssignee, nextActionGroupAssignee);
										
					PcmWorkflowModel memoWorkflowModel = new PcmWorkflowModel();
					memoWorkflowModel.setMasterId(memoId.toString());
					memoWorkflowModel = pcmWorkflowService.getLastWorkflow(memoWorkflowModel);
					action = saveWorkflowHistory(executionEntity, curUser, task.getName(), taskComment, action, task,  memoWorkflowModel.getId(), level);
					
					/// update reviewer from form control
					for(int lvl=1; lvl<=CommonConstant.MAX_APPROVER; lvl++) {
						updateExecutionEntity(executionEntity, task, "reviewer"+lvl+"Group");
						updateExecutionEntity(executionEntity, task, "reviewer"+lvl+"List");
					}
					
					/*
					 * update workflow table
					 */
			        memoWorkflowModel.setStatus(action);
			        memoWorkflowModel.setBy(curUser);			
			    	pcmWorkflowService.update(memoWorkflowModel);

			    	
			    	HashMap<String, Object> config = new HashMap<String, Object>();
			    	List<Map<String,Object>> listMaster = adminMasterService.listByType(MainMasterConstant.TYPE_SYSTEM_CONFIG, null, null, null, null);
			    	for(Map<String,Object> m : listMaster) {
			    		config.put((String)m.get(MainMasterConstant.TFN_CODE), m.get(MainMasterConstant.TFN_FLAG1));
			    	}
			    	
			    	/*
			    	 * In case Completed Workflow
			    	 */
					if(completeOutcome.equals("Completed Workflow")) {
						
						// Send Mail to requester
						log.info("-- Send Mail to requester --");
						String processId = task.getExecution().getProcessInstanceId();
						WorkflowInstance workflow = workflowService.getWorkflowById("activiti$"+processId);
						
						MainCompleteNotificationModel completeNotificationModel = new MainCompleteNotificationModel();
						completeNotificationModel.setReceiver(PersonUtil.getPerson(workflow.getInitiator(), personService).getUserName());
						completeNotificationModel.setTaskId(task.getId());
						completeNotificationModel.setTemplate(MainCompleteNotificationConstant.TEMPLATE_COMPLETE);
						completeNotificationService.save(completeNotificationModel);
						
						// Send Mail to related person
						log.info("-- Send Mail to related person --");
			    		MainMasterModel masterModel = adminMasterService.getSystemConfig(MainMasterConstant.SCC_MEMO_MAIL_RELATED);
			    		boolean mailRelated = masterModel != null && masterModel.getFlag1().equals(CommonConstant.V_ENABLE);
			    		log.info(MainMasterConstant.SCC_MEMO_MAIL_RELATED+" :: " + mailRelated);
			    		
		    			if(mailRelated){
							List<ActivitiScriptNode> relatedPerson = (List<ActivitiScriptNode>) task.getVariable(WF_PREFIX + "relatedUserAssignee");
	            			List<ActivitiScriptNode> relatedGroup = (List<ActivitiScriptNode>) task.getVariable(WF_PREFIX + "relatedGroupAssignee");
	            			
	            			List<NodeRef> userList = new ArrayList<NodeRef>();
	            			
	            			if(relatedPerson!=null){
	            				for (ActivitiScriptNode user : relatedPerson)
	                	    	{
	                	            log.info("Related User:" + user.getNodeRef().toString());
	                	            if(!userList.contains(user.getNodeRef())){
	                	            	userList.add(user.getNodeRef());
	                	            }
	                	    	}
	            			}
	            			
	            			String userGroup = null;
	            			
	            			if(relatedGroup!=null){
	            				for (ActivitiScriptNode group : relatedGroup)
	                	    	{
	                	            userGroup = (String) NodeUtil.getProperty(group.getNodeRef(), ContentModel.PROP_AUTHORITY_NAME, nodeService);
	                	            log.info("Related Group:" + userGroup);
	            					final Set<String> authorities = PersonUtil.getContainedAuthorities(userGroup, authorityService);
	                            	for (final String authority : authorities)
	                            	{
	                		            NodeRef person = PersonUtil.getPerson(authority, personService);
	                		            if(!userList.contains(person)){
	                		            	userList.add(person);
	                		            }
	                            	}
	                	    	}
	            			}
	            			
	        				for(NodeRef user : userList) {
	    						MainCompleteNotificationModel cnModel = new MainCompleteNotificationModel();
	    						cnModel.setReceiver(PersonUtil.getPerson(user, personService).getUserName());
	    						cnModel.setTaskId(task.getId());
	    						cnModel.setTemplate(MainCompleteNotificationConstant.TEMPLATE_RELATED);
	    						completeNotificationService.save(cnModel);
	        				}
		    			}
		    			
						JSONObject dataMap = PcmReqUtil.convertToJSONObject(memoModel,false);

			    		NodeRef folderNodeRef = new NodeRef(memoModel.getFolderRef());
			    		memoModel = pcmReqService.genDoc(memoModel, folderNodeRef);
			    		pcmReqService.update(memoModel);
			    		pcmWorkflowService.updateWorkflow(memoModel, task);
			    		updateExecutionEntity(executionEntity, task, "memoDocument");
					}
					else {
			    		pcmWorkflowService.updateWorkflow(memoModel, task);
					}
			    	
					doCalculateActualPercent(executionEntity, taskKey, defaultAction, task, config, completeOutcome);

				} catch (Exception ex) {
					log.error("", ex);
					throw ex;
				}
				
				return null;
			}
		}, AuthenticationUtil.getAdminUserName()); // runAs()
	}
	
	private Object updateExecutionEntity(ExecutionEntity executionEntity, DelegateTask task, String varName) {
		Object obj = ObjectUtils.defaultIfNull(task.getVariable(WF_PREFIX+varName), "");
		
		executionEntity.setVariable(WF_PREFIX+varName, obj);
		
		return obj;
	}
	
	private void update(PcmReqModel memoModel, Object au, Object ag) throws Exception {
		
		pcmReqService.updateStatus(memoModel);
	}
	
	private String getNamesFromForm(Object formObj, QName qname, String field) {
		String names = null;
		
		List<String> finalList = new ArrayList<String>();
		ActivitiScriptNodeList list = (ActivitiScriptNodeList)formObj;
		for(int i=0; i<list.size(); i++) {
			ActivitiScriptNode o = (ActivitiScriptNode)list.get(i);
			String name = (String)nodeService.getProperty(o.getNodeRef(), qname);
			name = name.replace("GROUP_",  "");
			finalList.add(name);
		}
		
		log.info(field+":"+finalList.toString());
		if (finalList.size()>0) {
			names = finalList.toString().replace("[",",").replace("]", ",").replace(" ", "");
		} else {
			names = ",";
		}
		
		return names;
	}
	
	private int getLastReviewerLevel(PcmReqModel memoModel) {
		int lastLevel = 0;
		
		return lastLevel;
	}	
	
	String saveWorkflowHistory(DelegateExecution execution, String user, String stateTask, String taskComment, String action, DelegateTask task, Long workflowId, Integer level) throws Exception {
		
		String commentHistoryAttr = WF_PREFIX+"commentHistory";
		
		String allComment = execution.getVariable(commentHistoryAttr)!= null?
					 execution.getVariable(commentHistoryAttr).toString():"";
		
		if(!allComment.equals("")){
			allComment += ",";
		}
		
		if(action.equalsIgnoreCase("Approve")){
			action = "อนุมัติ";
		}else if(action.equalsIgnoreCase("Reject")){
			action = "ไม่อนุมัติ-นำกลับไปแก้ไข";
		}else if(action.equalsIgnoreCase("NeedConsult")){
			action = "ขอคำปรึกษา";
		}else if(action.equalsIgnoreCase("Comment")){
			action = "ให้ความเห็น";
		}else if(action.equalsIgnoreCase("Completed Workflow")){
			action = "ปิดงาน";
		}else if(action.equalsIgnoreCase("Cancel")){
			action = "ยกเลิก";
		}else if(action.equalsIgnoreCase("Resubmit")){
			action = "ขออนุมัติ";
		}
		Timestamp now = CommonDateTimeUtil.now();
		SimpleDateFormat formatter = new SimpleDateFormat("EEE d MMM yyyy HH:mm", Locale.ENGLISH);
		String timeAction = formatter.format(now);
		
		// Build JSON string
		String currentComment = "";
		
		currentComment +="{";
		currentComment += "\"time\":\""+timeAction+"\",";
		currentComment += "\"user\":\""+user+"\",";
		currentComment += "\"action\":\""+action+"\",";
		currentComment += "\"task\":\""+stateTask+"\",";
		currentComment += "\"comment\":\""+org.json.simple.JSONObject.escape(taskComment)+"\" ";
		currentComment += "}";
		
		execution.setVariable(commentHistoryAttr, allComment + currentComment);
		task.setVariable(commentHistoryAttr, allComment + currentComment);
		
		PcmWorkflowHistoryModel memoWorkflowHistoryModel = new PcmWorkflowHistoryModel();
		memoWorkflowHistoryModel.setTime(now);
		memoWorkflowHistoryModel.setUser_(user);
		memoWorkflowHistoryModel.setAction(action);
		memoWorkflowHistoryModel.setTask(stateTask);
		memoWorkflowHistoryModel.setComment(taskComment.equalsIgnoreCase("")?null:taskComment);
		memoWorkflowHistoryModel.setMasterId(workflowId);
		memoWorkflowHistoryModel.setLevel(level);
		pcmWorkflowService.addWorkflowHistory(memoWorkflowHistoryModel);
		

		return action;
	}

	public void doCalculateActualPercent(ExecutionEntity executionEntity, String taskKey, String action, DelegateTask task
			,HashMap<String, Object> config, Object completeOutcome) throws Exception{
		
		log.info("DoCalulateActualPercent And Signature");
	
		Double actual = (Double) executionEntity.getVariable(WF_PREFIX+"actualPercentComplete");
		
		Object reviewer =  "";
		Object percent =  "";
		// java.text.DecimalFormat dfm = new java.text.DecimalFormat("0.00");
		if (completeOutcome.equals("Completed Workflow")) {
			NodeRef docRef = null;
			for(int i=1; i<=CommonConstant.MAX_APPROVER; i++){
				docRef = memoSignatureService.addSignature(task, config, WF_PREFIX, i);
			}
			if (docRef != null) {
				viewerService.prepareForViewer(docRef);
			}
		}
		else {
			for(int i=1; i<=CommonConstant.MAX_APPROVER; i++){
				
				//log.info(taskKey.equals("reviewer"+i+"Task"));
				
				if(taskKey.equalsIgnoreCase("reviewer"+i+"Task")){
						reviewer = ObjectUtils.defaultIfNull(executionEntity.getVariable(WF_PREFIX+"reviewer"+i+"List"), "");
						percent  = ObjectUtils.defaultIfNull(executionEntity.getVariable(WF_PREFIX+"reviewer"+i+"RequirePercentComplete"), "");
						actual = calculateActualPercent(executionEntity, taskKey, action, reviewer, actual);
						
						log.info("percent ::  " + percent);
						log.info("action ::  " + action);
						
						if(action.equalsIgnoreCase("Approve")){
							NodeRef docRef = memoSignatureService.addSignature(task, config, WF_PREFIX, i);
							viewerService.prepareForViewer(docRef);
							
							MainMasterModel docSignModel = adminMasterService.getSystemConfig(MainMasterConstant.SCC_MEMO_DOC_SIGN);
							if (docSignModel != null && docSignModel.getFlag1().equals(CommonConstant.V_ENABLE)) {
								MainMasterModel docSignMethodModel = adminMasterService.getSystemConfig(MainMasterConstant.SCC_MEMO_DOC_SIGN_METHOD);
								if (docSignMethodModel != null && docSignMethodModel.getFlag1().equals("1")) { // method 1
									List<NodeRef> attachDocList = memoSignatureService.addDocSignature(task, config, WF_PREFIX, i);
									for(NodeRef attachDocRef : attachDocList) {
										viewerService.prepareForViewer(attachDocRef);
									}
								}
							}
						}
						/*if(action.equalsIgnoreCase("Approve") && !precent.equals("")){
							if(actual >= Double.valueOf(precent.toString())){
								//Signature
								EwfSignatureService ewfSignatureService = new EwfSignatureService();
								ewfSignatureService.signature(task, config, "reviewer"+String.valueOf(i+1)+"List", "memwf_", action, fileFolderService, contentService, checkoutCheckInService, nodeService, personService);
							}
						}*/
						
						break;
		
				}
			}
		}
	}
	
	public Double calculateActualPercent(ExecutionEntity executionEntity, String taskKey, String action, Object reviewer, Double actual)throws Exception{
		
		log.info("reviewer :: " + reviewer);
		log.info("action :: " + action);
		
		Double actComplete = 100.0;
		
		if(!reviewer.equals("")){
			
			if(action.equalsIgnoreCase("Approve")){
				if (actual==null) actual = 0.0;
				List<String>  reviewList = (List<String>) reviewer;
				
				log.info("reviewList.size() :: "  + reviewList.size());
						
				BigDecimal act = BigDecimal.valueOf(actual+=(100/reviewList.size()));
				//act.add(BigDecimal.valueOf(actual));
				actual = act.setScale(0, BigDecimal.ROUND_UP).doubleValue();
				
				log.info("actual : " + act);
				
				actComplete = actual;
			}
			
			executionEntity.setVariable(WF_PREFIX+"actualPercentComplete", actComplete);
		}
		
		return actComplete;
	}
	
	public void createPaperHistory(final String memoRefId, final String memoFolder, final ExecutionEntity executionEntity) {
		log.info("create Paper");
		
		final String basePath = System.getProperty("catalina.base");
		final String filepath = basePath+"/temp/"+memoRefId+new Date().getTime()+".pdf";
		try {
			
		 AuthenticationUtil.runAs(new RunAsWork<String>() {
			 public String doWork() throws Exception
			 	{
						String rptName = basePath+"/webapps/alfresco/report/memo";
						
						Map<String,Object> params = new HashMap<String, Object>();
						params.put("masterId", memoRefId);
						List<PcmWorkflowHistoryModel> list = pcmWorkflowService.listHistory(params);
						//log.info("list :: " + list);
						List<Map<String, Object>> listHistory = new ArrayList<Map<String,Object>>();
						Map<String, Object> dataMap =null;
						
						for(PcmWorkflowHistoryModel model : list) {
							dataMap = new HashMap<String, Object>();
							dataMap.put("time", CommonDateTimeUtil.convertToGridDateTime(model.getTime()));
							dataMap.put("by", model.getUser_());
							dataMap.put("status", model.getAction());
							dataMap.put("task", model.getTask());
							dataMap.put("comment", model.getComment());
							listHistory.add(dataMap);
						}
						Map<String, Object> map = new HashMap<>();
						List<Map<String, Object>> listData = new ArrayList<Map<String,Object>>();
						
						map.put("data", listHistory);

						listData.add(map);
						
						Collection<Map<String, ?>> collection = new ArrayList<Map<String,?>>(listData);
						JRMapCollectionDataSource data = new JRMapCollectionDataSource(collection);
						
						
						JasperCompileManager.compileReportToFile(rptName+".jrxml");
						File reportFile = new File(rptName+".jasper");
						if (!reportFile.exists())
							throw new JRRuntimeException("File WebappReport.jasper not found. The report design must be compiled first.");
						
						
						JasperReport jasperReport = (JasperReport)JRLoader.loadObjectFromFile(reportFile.getPath());
						JasperPrint jasperPrint = 
								JasperFillManager.fillReport(
									jasperReport, 
									null, 
									data
									);	
						
						byte[] file = JasperExportManager.exportReportToPdf(jasperPrint);
						InputStream inputStream = new ByteArrayInputStream(file);
						/*NodeRef existDoc = fileFolderService.searchSimple(new NodeRef(memoFolder), memoRefId+"_ใบแนบท้าย.pdf");
						log.info("existDoc  " + existDoc);
				
						FileInfo paper = fileFolderService.create(new NodeRef(memoFolder), memoRefId+"_ใบแนบท้าย.pdf", ContentModel.PROP_CONTENT);
						
						ContentWriter contentWriter = null;
						contentWriter = contentService.getWriter(paper.getNodeRef(), ContentModel.PROP_CONTENT, true);
					    contentWriter.setMimetype("application/pdf");
						
						contentWriter.putContent(inputStream);*/
					
						ActivitiScriptNodeList mDoc = (ActivitiScriptNodeList)executionEntity.getVariable(WF_PREFIX+"memoDocument");
						ContentReader contentReader = contentService.getReader(mDoc.get(0).getNodeRef(), ContentModel.PROP_CONTENT);
					    InputStream is = contentReader.getContentInputStream();
					    PdfReader memoDocument = new PdfReader(is);
					    PdfReader memoDocument2 = new PdfReader(inputStream);
					    
						Document document = new Document();
						
						
						PdfCopy copy = new PdfCopy(document, new FileOutputStream(filepath));
						document.open();
				        copy.setMergeFields();
				        
				        copy.addDocument(memoDocument);
				        copy.addDocument(memoDocument2);
				        document.close();
				        
				        memoDocument.close();
				        memoDocument2.close();
				       
				        InputStream fileNewVersion = new FileInputStream(filepath);
				        
				        if (contentReader.isClosed()) {

				        	NodeRef oldDocRef = mDoc.get(0).getNodeRef(); 
					    	log.info("  is checked out:"+oldDocRef.toString());
						    if (checkOutCheckInService.isCheckedOut(oldDocRef)) {
						    	log.info("    true");
						    	final NodeRef wNodeRef = alfrescoService.getWorkingCopyNodeRef(oldDocRef.toString());
						    	log.info("    cancel check out:"+wNodeRef);
								AuthenticationUtil.runAs(new RunAsWork<String>()
								{
									public String doWork() throws Exception
									{
								    	checkOutCheckInService.cancelCheckout(wNodeRef);
										return null;
									}
								}, AuthenticationUtil.getAdminUserName());
						    }
						    else {
						    	log.info("    false");
						    }
				        	
				        	NodeRef workingCopy = checkOutCheckInService.checkout(mDoc.get(0).getNodeRef());
				        	ContentWriter contentWriter = null;
							contentWriter = contentService.getWriter(workingCopy, ContentModel.PROP_CONTENT, true);
						    contentWriter.setMimetype("application/pdf");
						    contentWriter.putContent(fileNewVersion);
						    Map<String, Serializable> versionProperties = new HashMap<String, Serializable>(1);
						    versionProperties.put(VersionModel.PROP_VERSION_TYPE, VersionType.MINOR);
						    
						    log.info("check in");
						    checkOutCheckInService.checkin(workingCopy, versionProperties);
						   
				        }
				        
				        
						/*if(existDoc != null){
							
							 // Find Target NodeRef
							log.info("check out");
							NodeRef workingCopy = checkoutCheckInService.checkout(existDoc);
							
							ContentWriter contentWriter = null;
							contentWriter = contentService.getWriter(workingCopy, ContentModel.PROP_CONTENT, true);
						    contentWriter.setMimetype("application/pdf");
						    InputStream inputStream = new ByteArrayInputStream(file);
						    contentWriter.putContent(inputStream);
						    nodeService.setProperty(workingCopy, ContentModel.PROP_CONTENT, contentWriter.getContentData());
						    
						    Map<String, Serializable> versionProperties = new HashMap<String, Serializable>(1);
						    versionProperties.put(VersionModel.PROP_VERSION_TYPE, VersionType.MINOR);
						   
						    log.info("check in");
						    checkoutCheckInService.checkin(workingCopy, versionProperties);
						    
							
						}*/
				        
				        is.close();
				        inputStream.close();
				        fileNewVersion.close();
					return null;
				}
			}, AuthenticationUtil.getAdminUserName()); // runAs()
		}catch(Exception ex){
			log.error("", ex);
		}finally {
			File f = new File(filepath);
			f.delete();
		}
		
	}
}
