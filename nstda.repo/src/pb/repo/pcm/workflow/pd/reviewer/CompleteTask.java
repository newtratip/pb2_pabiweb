package pb.repo.pcm.workflow.pd.reviewer;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.forms.FormException;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.repo.workflow.activiti.ActivitiScriptNode;
import org.alfresco.repo.workflow.activiti.ActivitiScriptNodeList;
import org.alfresco.service.cmr.coci.CheckOutCheckInService;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.TemplateService;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.PersonService;
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
import pb.repo.admin.util.MainUserGroupUtil;
import pb.repo.pcm.constant.PcmOrdConstant;
import pb.repo.pcm.constant.PcmWorkflowConstant;
import pb.repo.pcm.model.PcmOrdModel;
import pb.repo.pcm.model.PcmOrdReviewerModel;
import pb.repo.pcm.model.PcmOrdWorkflowHistoryModel;
import pb.repo.pcm.model.PcmOrdWorkflowModel;
import pb.repo.pcm.service.PcmOrdService;
import pb.repo.pcm.service.PcmOrdWorkflowService;
import pb.repo.pcm.service.PcmSignatureService;
import pb.repo.pcm.util.PcmOrdUtil;

@Component("pb.pcm.workflow.pd.reviewer.CompleteTask")
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
	PcmOrdService pcmOrdService;
	
	@Autowired
	PcmOrdWorkflowService pcmOrdWorkflowService;

	@Autowired
	PcmSignatureService signatureService;
	
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
		
		log.info("<- pd.reviewer.CompleteTask ->");
		
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
					PcmOrdModel model = pcmOrdService.get(id.toString());
					Integer level = model.getWaitingLevel();
					ExecutionEntity executionEntity = ((ExecutionEntity)task.getExecution()).getProcessInstance();
					
					String curUser = authenticationService.getCurrentUserName();
					String taskKey = task.getTaskDefinitionKey();
					String action = task.getVariable(WF_PREFIX+"reviewOutcome")!=null ? task.getVariable(WF_PREFIX+"reviewOutcome").toString():null;
					
					log.info("action:"+action);
					
					if (action.equalsIgnoreCase("Reject")) {
						model.setWaitingLevel(0);
					}
					else
					if (action.equalsIgnoreCase("Approve")) {
						model.setWaitingLevel(model.getWaitingLevel()!=null ? model.getWaitingLevel()+1 : null);
						
						PcmOrdReviewerModel paramModel = new PcmOrdReviewerModel();
						paramModel.setMasterId(id.toString());
						paramModel.setLevel(model.getWaitingLevel());
						PcmOrdReviewerModel pcmReqReviewerModel = pcmOrdService.getReviewer(paramModel);
						if (pcmReqReviewerModel != null) {
							executionEntity.setVariable(WF_PREFIX+"nextReviewers", MainUserGroupUtil.codes2logins(pcmReqReviewerModel.getReviewerUser()));
						} else {
							executionEntity.setVariable(WF_PREFIX+"nextReviewers", "");
						}
					}

					executionEntity.setVariable(WF_PREFIX+"reviewOutcome", action);

					updateExecutionEntity(executionEntity, task, "requestedTime");
					updateExecutionEntity(executionEntity, task, "remark");
					
					updateExecutionEntity(executionEntity, task, "document");
					updateExecutionEntity(executionEntity, task, "attachDocument");
					
					
					// Keep TaskId to pcmwf:taskHistory.
					String taskHistory = (String)executionEntity.getVariable(WF_PREFIX+"taskHistory");
					String finalTaskHistory = taskHistory + "|" + taskKey + (taskKey.equals("reviewerTask") ? (" "+level) : "");
					executionEntity.setVariable(WF_PREFIX+"taskHistory", finalTaskHistory);
					log.info("  taskHistory:" + finalTaskHistory);

					log.info("  status : "+model.getStatus()+", waitingLevel:"+model.getWaitingLevel());
					update(model, null, null);
										
					// Comment History
					String taskComment = "";
					Object tmpComment = task.getVariable("bpm_comment");
					if(tmpComment != null && !tmpComment.equals("")){
						taskComment = tmpComment.toString();
					}
					
					PcmOrdWorkflowModel workflowModel = new PcmOrdWorkflowModel();
					workflowModel.setMasterId(id.toString());
					workflowModel = pcmOrdWorkflowService.getLastWorkflow(workflowModel);
					action = saveWorkflowHistory(executionEntity, curUser, task.getName(), taskComment, action, task,  workflowModel.getId(), level);
				}
				catch (Exception ex) {
					log.error(ex);
				}
				
				return null;
			}
		}, AuthenticationUtil.getAdminUserName()); // runAs()
	}
	
	public void _notify(final DelegateTask task)  {
		
		log.info("<- wf02.reviewer.CompleteTask ->");
		
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
					PcmOrdModel model = pcmOrdService.get(id.toString());
					Integer level = model.getWaitingLevel();
					ExecutionEntity executionEntity = ((ExecutionEntity)task.getExecution()).getProcessInstance();

					String curUser = authenticationService.getCurrentUserName();
					String taskKey = task.getTaskDefinitionKey();
					String action = task.getVariable(WF_PREFIX+"approveRejectOutcome")!=null ? task.getVariable(WF_PREFIX+"approveRejectOutcome").toString():null;
					Object completeOutcome = ObjectUtils.defaultIfNull(task.getVariable(WF_PREFIX+"completeOutcome"), "");
					String reSubmitOutcome = task.getVariable(WF_PREFIX+"reSubmitOutcome")!=null ? task.getVariable(WF_PREFIX+"reSubmitOutcome").toString():null;
					if (taskKey.equalsIgnoreCase("requesterTask")) action = reSubmitOutcome;
					executionEntity.setVariable(WF_PREFIX+"reSubmitOutcome", reSubmitOutcome);
					log.info("  reSubmitOutcome="+reSubmitOutcome);

					int lastLevel = getLastReviewerLevel(model);
					Boolean isLastReviewer = isLastReviewerAndNoCloseUser(level, model, action);
					
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
//					for (int i=1; i<=PcmOrdConstant.MAX_DUMMY_FIELD; i++) {
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
					if(completeOutcome.equals("Completed Workflow") || isLastReviewer) {
						model.setId(id.toString());
						model.setStatus(PcmOrdConstant.ST_CLOSED);
						model.setWaitingLevel(null);
						action = "Completed Workflow";
					} 
					else 
					if(reSubmitOutcome!=null && reSubmitOutcome.equalsIgnoreCase("Cancel")) {
						model.setId(id.toString());
						model.setStatus(PcmOrdConstant.ST_CANCEL);
						model.setWaitingLevel(null);
						action = reSubmitOutcome.toString();
					}
					else
					if (action.equalsIgnoreCase("Reject")) {
						model.setId(id.toString());
						model.setWaitingLevel(model.getWaitingLevel()-1);
					}
					else {
						model.setWaitingLevel(model.getWaitingLevel()!=null ? model.getWaitingLevel()+1 : null);
					}
					
					// Keep TaskId to pcmwf:taskHistory.
					String totalTaskHistory = (String)executionEntity.getVariable(WF_PREFIX+"taskHistory");
					executionEntity.setVariable(WF_PREFIX+"taskHistory", totalTaskHistory + "|" + taskKey);
					log.info("  taskHistory:" + totalTaskHistory);

					log.info("  status : "+model.getStatus()+", waitingLevel:"+model.getWaitingLevel());
					update(model, nextActionUserAssignee, nextActionGroupAssignee);
										
					PcmOrdWorkflowModel workflowModel = new PcmOrdWorkflowModel();
					workflowModel.setMasterId(id.toString());
					workflowModel = pcmOrdWorkflowService.getLastWorkflow(workflowModel);
					action = saveWorkflowHistory(executionEntity, curUser, task.getName(), taskComment, action, task,  workflowModel.getId(), level);
					
					/// update reviewer from form control
					for(int lvl=1; lvl<=CommonConstant.MAX_APPROVER; lvl++) {
						updateExecutionEntity(executionEntity, task, "reviewer"+lvl+"Group");
						updateExecutionEntity(executionEntity, task, "reviewer"+lvl+"List");
					}
					
					/*
					 * update workflow table
					 */
			        workflowModel.setStatus(action);
			        workflowModel.setBy(curUser);			
			    	pcmOrdWorkflowService.update(workflowModel);

			    	
			    	HashMap<String, Object> config = new HashMap<String, Object>();
			    	List<Map<String,Object>> listMaster = adminMasterService.listByType(MainMasterConstant.TYPE_SYSTEM_CONFIG, null, null, null, null);
			    	for(Map<String,Object> m : listMaster) {
			    		config.put((String)m.get(MainMasterConstant.TFN_CODE), m.get(MainMasterConstant.TFN_FLAG1));
			    	}
			    	
			    	/*
			    	 * In case Completed Workflow
			    	 */
					if(completeOutcome.equals("Completed Workflow") || isLastReviewer) {
						
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
		    			
						JSONObject dataMap = PcmOrdUtil.convertToJSONObject(model,false);

			    		NodeRef folderNodeRef = new NodeRef(model.getFolderRef());
			    		model = pcmOrdService.genDoc(model, folderNodeRef);
			    		pcmOrdService.update(model);
			    		pcmOrdWorkflowService.updateWorkflow(model, task);
			    		updateExecutionEntity(executionEntity, task, "document");
					}
					else {
			    		pcmOrdWorkflowService.updateWorkflow(model, task);
					}
			    	
					doCalculateActualPercent(executionEntity, taskKey, defaultAction, task, config, completeOutcome, isLastReviewer);

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
	
	private void update(PcmOrdModel model, Object au, Object ag) throws Exception {
		
		pcmOrdService.updateStatus(model);
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
	
	private int getLastReviewerLevel(PcmOrdModel model) {
		int lastLevel = 0;
		
		return lastLevel;
	}	
	
	private Boolean isLastReviewerAndNoCloseUser(Integer level, PcmOrdModel model, String action) {
		List<MainMasterModel> wfList = adminMasterService.list(MainMasterConstant.TYPE_WORKFLOW, model.getWorkflowId());
		if (!wfList.isEmpty()) {
			MainMasterModel wfModel = wfList.get(0);
			
			if (wfModel.getFlag2()!=null && wfModel.getFlag2().equals("1")) {
				
				int lastLevel = getLastReviewerLevel(model);
				
				log.info("level=="+level);
				log.info("last level=="+lastLevel);
				
				if (level == lastLevel && action.equals("Approve")) {
					return true;
				}
			}
		}
		
		return false;
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
		}else if(action.equalsIgnoreCase("Consult")){
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
		
		PcmOrdWorkflowHistoryModel workflowHistoryModel = new PcmOrdWorkflowHistoryModel();
		workflowHistoryModel.setTime(now);
		workflowHistoryModel.setUser_(user);
		workflowHistoryModel.setAction(action);
		workflowHistoryModel.setTask(stateTask);
		workflowHistoryModel.setComment(taskComment.equalsIgnoreCase("")?null:taskComment);
		workflowHistoryModel.setMasterId(workflowId);
		workflowHistoryModel.setLevel(level);
		pcmOrdWorkflowService.addWorkflowHistory(workflowHistoryModel);
		

		return action;
	}

	public void doCalculateActualPercent(ExecutionEntity executionEntity, String taskKey, String action, DelegateTask task
			,HashMap<String, Object> config, Object completeOutcome, Boolean isLastReviewer) throws Exception{
		
		log.info("DoCalulateActualPercent And Signature");
	
		Double actual = (Double) executionEntity.getVariable(WF_PREFIX+"actualPercentComplete");
		
		Object reviewer =  "";
		Object percent =  "";
		// java.text.DecimalFormat dfm = new java.text.DecimalFormat("0.00");
		if (!isLastReviewer && completeOutcome.equals("Completed Workflow")) {
			NodeRef docRef = null;
			for(int i=1; i<=CommonConstant.MAX_APPROVER; i++){
				docRef = signatureService.addSignature(task, config, WF_PREFIX, i);
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
							NodeRef docRef = signatureService.addSignature(task, config, WF_PREFIX, i);
							viewerService.prepareForViewer(docRef);
							
							MainMasterModel docSignModel = adminMasterService.getSystemConfig(MainMasterConstant.SCC_MEMO_DOC_SIGN);
							if (docSignModel != null && docSignModel.getFlag1().equals(CommonConstant.V_ENABLE)) {
								MainMasterModel docSignMethodModel = adminMasterService.getSystemConfig(MainMasterConstant.SCC_MEMO_DOC_SIGN_METHOD);
								if (docSignMethodModel != null && docSignMethodModel.getFlag1().equals("1")) { // method 1
									List<NodeRef> attachDocList = signatureService.addDocSignature(task, config, WF_PREFIX, i);
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
	
}
