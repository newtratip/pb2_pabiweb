package pb.repo.pcm.service;

import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.activiti.engine.delegate.DelegateTask;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.repo.workflow.WorkflowModel;
import org.alfresco.repo.workflow.activiti.ActivitiScriptNode;
import org.alfresco.repo.workflow.activiti.ActivitiScriptNodeList;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.TemplateProcessor;
import org.alfresco.service.cmr.repository.TemplateService;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.service.cmr.security.OwnableService;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.cmr.workflow.WorkflowDefinition;
import org.alfresco.service.cmr.workflow.WorkflowPath;
import org.alfresco.service.cmr.workflow.WorkflowService;
import org.alfresco.service.cmr.workflow.WorkflowTask;
import org.alfresco.service.cmr.workflow.WorkflowTaskQuery;
import org.alfresco.service.cmr.workflow.WorkflowTaskState;
import org.alfresco.service.namespace.QName;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.common.constant.CommonConstant;
import pb.common.util.CommonDateTimeUtil;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.constant.MainUserGroupConstant;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.model.MainUserGroupModel;
import pb.repo.admin.service.AdminEmployeeBossService;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AlfrescoService;
import pb.repo.admin.util.MainUserGroupUtil;
import pb.repo.admin.util.MainUtil;
import pb.repo.pcm.constant.PcmWorkflowConstant;
import pb.repo.pcm.dao.PcmOrdWorkflowDAO;
import pb.repo.pcm.dao.PcmOrdWorkflowHistoryDAO;
import pb.repo.pcm.model.PcmOrdModel;
import pb.repo.pcm.model.PcmOrdReviewerModel;
import pb.repo.pcm.model.PcmOrdWorkflowHistoryModel;
import pb.repo.pcm.model.PcmOrdWorkflowModel;
import pb.repo.pcm.util.PcmOrdUtil;
import pb.repo.pcm.util.PcmUtil;

@Service
public class PcmOrdWorkflowService {
	
	private static Logger log = Logger.getLogger(PcmOrdWorkflowService.class);
	
	@Autowired
	ServiceRegistry services;
	
	@Autowired
	AuthenticationService authService;

	@Autowired
	WorkflowService workflowService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	NodeService nodeService;
	
	@Autowired
	PermissionService permissionService;
	
	@Autowired
	AuthorityService authorityService;
	
	@Autowired
	FileFolderService fileFolderService;

	@Autowired
	ContentService contentService;
	
	@Autowired
	AdminMasterService adminMasterService;
	
	@Autowired
	PcmOrdService pcmOrdService;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	OwnableService ownableService;
	
	@Autowired
	TemplateService templateService;
	
	@Autowired
	AlfrescoService alfrescoService;
	
	@Autowired
	AdminEmployeeBossService employeeBossService;
	
	private static final String WF_URI = PcmWorkflowConstant.WF_URI;
	private static final String WF_PREFIX = PcmWorkflowConstant.MODEL_PREFIX;
	
	public String startWorkflow(PcmOrdModel pcmOrdModel) throws Exception {
		
		log.info("<- Start Workflow -> : PCM Req "+pcmOrdModel.getId());
		
		String instanceId = null;
		
		try {
			
			PcmOrdWorkflowHistoryModel workflowHistoryModel = new PcmOrdWorkflowHistoryModel();
			workflowHistoryModel.setLevel(0);
			
	//        NodeRef reqPerson = personService.getPerson(pcmOrdModel.getCreatedBy());
	//        String reqName = (String)nodeService.getProperty(reqPerson, ContentModel.PROP_FIRSTNAME);
	        
	        String stateTask = "ขออนุมัติ";
			String wfName = getWorkflowName(pcmOrdModel);
			log.info("  WF Name:"+wfName);
			WorkflowDefinition workflow = workflowService.getDefinitionByName("activiti$"+wfName);
			log.info("  workflow:"+workflow);
		
	     	NodeRef workflowPackage = workflowService.createPackage(null);
	     	
	     	// Set to WorkFlow
	        Map<QName, Serializable> parameters = new HashMap<QName, Serializable>();
	        parameters.put(WorkflowModel.ASSOC_PACKAGE, workflowPackage);
	        
	        final NodeRef folderNodeRef = new NodeRef(pcmOrdModel.getFolderRef());
	        final String createdBy = pcmOrdModel.getCreatedBy();
	        		
	        AuthenticationUtil.runAs(new RunAsWork<String>()
		    {
				public String doWork() throws Exception
				{
			        permissionService.deletePermissions(folderNodeRef);
					permissionService.setInheritParentPermissions(folderNodeRef, false);
					ownableService.setOwner(folderNodeRef, "admin");
					permissionService.setPermission(folderNodeRef, createdBy, "SiteCollaborator", true);
					
			    	return null;
				}
		    }, AuthenticationUtil.getAdminUserName());
	        
	        setRequesterPermission(folderNodeRef, pcmOrdModel);
			
	        setReviewer(parameters, pcmOrdModel, folderNodeRef);
	        
	        PcmOrdReviewerModel paramModel = new PcmOrdReviewerModel();
	        paramModel.setMasterId(pcmOrdModel.getId());
	        paramModel.setLevel(1);
	        PcmOrdReviewerModel pcmOrdReviewerModel = pcmOrdService.getReviewer(paramModel);
	        if (pcmOrdReviewerModel != null) {
	        	parameters.put(PcmWorkflowConstant.PROP_NEXT_REVIEWERS, MainUserGroupUtil.codes2logins(pcmOrdReviewerModel.getReviewerUser()));
	        } else {
	        	log.error("ERR:No Reviewer Level 1 of "+pcmOrdModel.getId());
	        }
	        
	        log.info("Doc Ref : " + pcmOrdModel.getDocRef());
	        List<NodeRef> docList = new ArrayList<NodeRef>();
	        docList.add(new NodeRef(pcmOrdModel.getDocRef()));
	
	        List<NodeRef> attachDocList = new ArrayList<NodeRef>();
	        if( pcmOrdModel.getListAttachDoc()!=null) {
	        	for(String nodeRef : pcmOrdModel.getListAttachDoc()) {
	        		attachDocList.add(new NodeRef(nodeRef));
	            }
	        }
	        
	        parameters.put(PcmWorkflowConstant.PROP_ID, pcmOrdModel.getId());
	        parameters.put(PcmWorkflowConstant.PROP_TARGET_FOLDER_NODE_REF, pcmOrdModel.getFolderRef());

	        parameters.put(PcmWorkflowConstant.PROP_DOCUMENT, (Serializable)docList);
	        parameters.put(PcmWorkflowConstant.PROP_ATTACH_DOCUMENT, (Serializable)attachDocList);
	        parameters.put(PcmWorkflowConstant.PROP_COMMENT_HISTORY, "");
	        
	        parameters.put(PcmWorkflowConstant.PROP_REMARK, pcmOrdModel.getRemark());
	        
	        parameters.put(PcmWorkflowConstant.PROP_TASK_HISTORY, "");
	        
	        parameters.put(PcmWorkflowConstant.PROP_REQUESTED_TIME, pcmOrdModel.getRequestedTime());
	        
	        parameters.put(WorkflowModel.PROP_WORKFLOW_DESCRIPTION, getWorkflowDescription(pcmOrdModel));
	        
	        WorkflowPath workflowPath = workflowService.startWorkflow(workflow.getId(), parameters);
	        log.info("Start Workflow Successfully");
	        
	        instanceId = workflowPath.getInstance().getId();
	        WorkflowTask startTask = workflowService.getStartTask(instanceId);
	        workflowService.endTask(startTask.getId(), null);
	        
	        pcmOrdModel.setWorkflowInsId(instanceId);
	        pcmOrdModel.setWaitingLevel(1);
	    	pcmOrdService.update(pcmOrdModel);
	        
	        Long wfKey = Long.valueOf(this.getKey());
	        PcmOrdWorkflowModel workflowModel = new PcmOrdWorkflowModel();
	        workflowModel.setId(wfKey);
	        workflowModel.setMasterId(pcmOrdModel.getId());
			workflowModel.setType("M");
			workflowModel.setWorkflowInsId(instanceId);
			workflowModel.setTaskId(startTask.getId());
			workflowModel.setStatus(stateTask);
			workflowModel.setBy(pcmOrdModel.getCreatedBy());
			workflowModel.setCreatedBy(pcmOrdModel.getCreatedBy());
	        
	        addWorkflow(workflowModel);
	        
	        workflowHistoryModel.setMasterId(wfKey);
	        addWorkflowHistory(workflowHistoryModel);
	        
	        log.info("Update Database Successfully");
        
		} catch (Exception ex) {
			log.error("", ex);
			throw ex;
		}

		return instanceId;
	}
	
	private String getWorkflowName(PcmOrdModel pcmOrdModel) throws Exception {
		String wfName = null;
		
		/*
		 * find Workflow Name
		 */
		log.info("workflowId:"+pcmOrdModel.getWorkflowId());
		List<MainMasterModel> masterList = adminMasterService.list(
										MainMasterConstant.TYPE_WORKFLOW, 
										pcmOrdModel.getWorkflowId()
									);
		MainMasterModel masterModel = masterList.get(0);
		
		if (masterModel == null) {
			throw new Exception("Not Found Workflow to start!");
		}
		else {
			wfName = masterModel.getFlag1();
		}
		
		return wfName;
	}
	
	private void setRequesterPermission(final NodeRef folderNodeRef, PcmOrdModel pcmOrdModel) {
		
		// add permission to requester
        AuthenticationUtil.runAs(new RunAsWork<String>()
	    {
			public String doWork() throws Exception
			{
				permissionService.setPermission(folderNodeRef, authService.getCurrentUserName(), "SiteCollaborator", true);
		    	return null;
			}
	    }, AuthenticationUtil.getAdminUserName());
	}
	
    private void setRelatedAssignee(Map<QName, Serializable> parameters, String relatedUserGroups, final NodeRef folderNodeRef) throws Exception {
       	List<NodeRef> userList = new ArrayList<NodeRef>();
    	List<NodeRef> groupList = new ArrayList<NodeRef>();
    	
    	List<MainUserGroupModel> ugList = MainUserGroupUtil.convertJsonToUserGroupList(relatedUserGroups);
		for(MainUserGroupModel model : ugList){
			
			String type = model.getType();	
			String id = null;

			if (type.equals(MainUserGroupConstant.TYPE_USER)) {
				id = model.getId();
				NodeRef personNodeRef = personService.getPerson(id);
	            if(!userList.contains(personNodeRef)){
	            	userList.add(personNodeRef);
	            }
	            
			}
			else
			if (type.equals(MainUserGroupConstant.TYPE_GROUP)) {
				id = "GROUP_" + model.getId();
				NodeRef groupNodeRef = authorityService.getAuthorityNodeRef(id);
	            if(!groupList.contains(groupNodeRef)){
	            	groupList.add(groupNodeRef);
	            }
			}
	            
			final String fid = id;
	        AuthenticationUtil.runAs(new RunAsWork<String>()
    	    {
    			public String doWork() throws Exception
    			{
    		        permissionService.setPermission(folderNodeRef, fid, "SiteConsumer", true);
    		    	return "A";
    			}
    	    }, AuthenticationUtil.getAdminUserName());
		}
		
    }
    
    private void setReviewer(Map<QName, Serializable> parameters, PcmOrdModel pcmOrdModel, final NodeRef folderNodeRef) {
    	
         try {
        	 
//        	Map<String, String> bossMap = employeeBossService.getBossMap(MainEmployeeConstant.BM_PR, pcmOrdModel.getCostType(), pcmOrdModel.getReqOu(), pcmOrdModel.getCreatedBy(), pcmOrdModel.getTotal());

/* for DEMO { */
        	Map<String, String> bossMap = new LinkedHashMap<String, String>();
        	bossMap.put("L01", "001509");
        	bossMap.put("L02", "000511");
/* for DEMO } */
        	
    		log.info("setReviewer : start");
        	for(Entry e : bossMap.entrySet()) {
        		log.info("setReviewer : "+e.getKey()+":"+e.getValue());
        	}
        	
        	final List<String> permissionGroup = new ArrayList<String>();
        	final List<String> permissionUser = new ArrayList<String>();
        	//List<Double> percents = new ArrayList<Double>();
        	List<String> userCollectionList = null; 
        	
        	pcmOrdService.deleteReviewerByMasterId(pcmOrdModel.getId());
        	PcmOrdReviewerModel pcmOrdReviewerModel = null;
        	
        	Map<Integer,List<String>> lvl = new HashMap<Integer, List<String>>();
//        	for(MainApprovalMatrixDtlModel dtl : listDtl) {
        	int level = 1;
        	for(Entry e : bossMap.entrySet()) {
        		pcmOrdReviewerModel = new PcmOrdReviewerModel();
        		userCollectionList = new ArrayList<String>();
        		String reviewerUser = (String)e.getValue();
        		String reviewerGroup = "";
        		
        		pcmOrdReviewerModel.setReviewerGroup(reviewerGroup);
        		pcmOrdReviewerModel.setReviewerUser(reviewerUser);
        		pcmOrdReviewerModel.setMasterId(pcmOrdModel.getId());
        		pcmOrdReviewerModel.setLevel(level);
        		pcmOrdReviewerModel.setPercent(0.0);
        		pcmOrdReviewerModel.setRewarning(0);
        		pcmOrdReviewerModel.setCreatedBy(pcmOrdModel.getCreatedBy());
        		pcmOrdService.addReviewer(pcmOrdReviewerModel);
        		
        		log.info("   user : "+reviewerUser);
        		
        		if(reviewerGroup!=null && !reviewerGroup.equalsIgnoreCase("")) {
        			
        			reviewerGroup = reviewerGroup.substring(1, reviewerGroup.length()-1);
        			
        			
        			for(String appGroup : reviewerGroup.split(",")) {
        				
        				if(!permissionGroup.contains(appGroup)) {
            				permissionGroup.add(appGroup);
            			}
        				
        				Set<String> authorities = authorityService.getContainedAuthorities(AuthorityType.USER, "GROUP_"+appGroup, false);
        				for(String u : authorities) {
        					if(!userCollectionList.contains(u)) {
        						userCollectionList.add(u);
        					}
	
        				}
        				
        			}

        		}
        		
        		if(reviewerUser!=null && !reviewerUser.equalsIgnoreCase("")) {
        			
        			//reviewerUser = reviewerUser.substring(1, reviewerUser.length()-1);
 
        			for(String appUser : reviewerUser.split(",")) {
               			if(!permissionUser.contains(appUser)) {
            				permissionUser.add(appUser);
            			}
               			if(!userCollectionList.contains(appUser)) {
               				userCollectionList.add(appUser);
               			}
        				
        			}
        			
        		}
        		log.info("user collection:"+userCollectionList.toString());
//        		Double percent = dtl.getPercent();
        		
        		lvl.put(level, userCollectionList);
        		//parameters.put(QName.createQName(wfUri + "reviewer"+i+"Group"), appGroup);
//        		parameters.put(QName.createQName(WF_URI + "reviewer"+dtl.getLevel()+"RequirePercentComplete"), percent);
        		//parameters.put(QName.createQName(WF_URI + "reviewer"+dtl.getLevel()+"List"), (Serializable) userCollectionList);
//        		parameters.put(QName.createQName(WF_URI + "rewarning"+dtl.getLevel()), dtl.getRewarning());
//        		parameters.put(QName.createQName(WF_URI + "hint"+dtl.getLevel()), dtl.getHint());
        		level++;
        	}
        	
//        	for(int i=1; i<=CommonConstant.MAX_APPROVER;i++) {
//        		log.info("  reviewer"+i);
//        		if(lvl.containsKey(i)) {
//        			log.info(i);
//        			parameters.put(QName.createQName(WF_URI + "reviewer"+i+"Group"), "true");
////            		parameters.put(QName.createQName(WF_URI + "reviewer"+i+"List"), (Serializable) lvl.get(i));
//           		}else {
//        			log.info(WF_URI + "reviewer"+i+"List");
//        			List<String> emptyCollectionList = new ArrayList<String>(); 
//        			parameters.put(QName.createQName(WF_URI + "reviewer"+i+"Group"), "");
////            		parameters.put(QName.createQName(WF_URI + "reviewer"+i+"List"), (Serializable) emptyCollectionList);
//        		}
//        		
//        	}
        	
	        AuthenticationUtil.runAs(new RunAsWork<String>()
    	    {
    			public String doWork() throws Exception
    			{
    	        	for(String g : permissionGroup) {
    	        		
    	        		if(!permissionService.getPermissions(folderNodeRef).contains("GROUP_"+g)) {
    	        			permissionService.setPermission(folderNodeRef, "GROUP_"+g, "SiteCollaborator", true);
    	        		}
    	        			
    	        	}
    	        	for(String u : permissionUser) {
    	        		String uu = MainUserGroupUtil.code2login(u);
    	        		if(!permissionService.getPermissions(folderNodeRef).contains(uu)) {
    	        			permissionService.setPermission(folderNodeRef, uu, "SiteCollaborator", true);
    	        		}

    	        	}
    	        	return "A";
    			}
    	    }, AuthenticationUtil.getAdminUserName());
        	
		} catch (Exception ex) {
			log.error("", ex);
		}

    }

	public Long getKey() {
		
		SqlSession session = PcmUtil.openSession(dataSource);
		Long key = null;
		try {
			
            PcmOrdWorkflowDAO dao = session.getMapper(PcmOrdWorkflowDAO.class);
            
            key = dao.getKey();
            
            session.commit();
            
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
		return key;
		
	}
	public void addWorkflow(PcmOrdWorkflowModel workflowModel) {
		
		SqlSession session = PcmUtil.openSession(dataSource);
		
		try {
			
            PcmOrdWorkflowDAO dao = session.getMapper(PcmOrdWorkflowDAO.class);
            
            dao.add(workflowModel);
            
            session.commit();
            
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
		
	}
	
	public void update(PcmOrdWorkflowModel workflowModel) {
		
		SqlSession session = PcmUtil.openSession(dataSource);
		
		try {
			
            PcmOrdWorkflowDAO dao = session.getMapper(PcmOrdWorkflowDAO.class);
            
            workflowModel.setByTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            
            dao.update(workflowModel);
            
            session.commit();
            
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
		
	}
	
	public PcmOrdWorkflowModel getLastWorkflow(PcmOrdWorkflowModel workflowModel) {
		
		SqlSession session = PcmUtil.openSession(dataSource);
		PcmOrdWorkflowModel model = new PcmOrdWorkflowModel();
		try {
		
            PcmOrdWorkflowDAO dao = session.getMapper(PcmOrdWorkflowDAO.class);
            
            model = dao.getLastWorkflow(workflowModel);
           
            session.commit();
            
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
		return model;
	}
	
	public void addWorkflowHistory(PcmOrdWorkflowHistoryModel workflowHistoryModel) {
		
		SqlSession session = PcmUtil.openSession(dataSource);
		
		try {
			
            PcmOrdWorkflowHistoryDAO dao = session.getMapper(PcmOrdWorkflowHistoryDAO.class);
            
            dao.add(workflowHistoryModel);
            
            session.commit();
            
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
		
	}
	
	public void updateMemoWorkflowHistory(PcmOrdWorkflowHistoryModel workflowHistoryModel) {
		
		SqlSession session = PcmUtil.openSession(dataSource);
		
		try {
			
            PcmOrdWorkflowHistoryDAO dao = session.getMapper(PcmOrdWorkflowHistoryDAO.class);
            
            dao.update(workflowHistoryModel);
            
            session.commit();
            
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
		
	}
	
	
	public List<PcmOrdWorkflowHistoryModel> listHistory(Map<String,Object> params) {
		
		SqlSession session = PcmUtil.openSession(dataSource);
		List<PcmOrdWorkflowHistoryModel> list = new ArrayList<PcmOrdWorkflowHistoryModel>();
		
		try {
			
            PcmOrdWorkflowHistoryDAO dao = session.getMapper(PcmOrdWorkflowHistoryDAO.class);
            
            list =  dao.listHistory(params);
            
            session.commit();
            
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
		
		return list;
		
	}
	

	public String updateWorkflow(PcmOrdModel pcmOrdModel, String actionUserGroups, String relatedUserGroups, Boolean amDirty) throws Exception {
		
		WorkflowTaskQuery query = new WorkflowTaskQuery();

		query.setTaskState(WorkflowTaskState.IN_PROGRESS);
		query.setProcessId(pcmOrdModel.getWorkflowInsId());

		List<WorkflowTask> tasks = workflowService.queryTasks(query, true);

		if (tasks.size() > 0) {
		     Map<QName, Serializable> params = new HashMap<QName, Serializable>();
		     
	         NodeRef folderNodeRef = new NodeRef(pcmOrdModel.getFolderRef());
	        
	         setRelatedAssignee(params, relatedUserGroups, folderNodeRef);
			
	         if (amDirty) {
	        	 log.info("approvalMatrixId is changed");
	        	 setReviewer(params, pcmOrdModel, folderNodeRef);
	         }
		 
	         params.put(PcmWorkflowConstant.PROP_REMARK, pcmOrdModel.getRemark());
	         params.put(PcmWorkflowConstant.PROP_REQUESTED_TIME, pcmOrdModel.getRequestedTime());
	
	         List<NodeRef> docList = new ArrayList<NodeRef>();
	         docList.add(new NodeRef(pcmOrdModel.getDocRef()));
	
	         List<NodeRef> attachDocList = new ArrayList<NodeRef>();
	         if( pcmOrdModel.getListAttachDoc()!=null) {
	         	for(String nodeRef : pcmOrdModel.getListAttachDoc()) {
	         		attachDocList.add(new NodeRef(nodeRef));
	             }
	         }
	         
//	         params.put(MemoWorkflowConstant.PROP_MEMO_DOCUMENT, (Serializable)docList);
	         params.put(QName.createQName(PcmWorkflowConstant.MODEL_URI , "memoDocument"), (Serializable)docList);
	         
	         params.put(WorkflowModel.PROP_DESCRIPTION, getWorkflowDescription(pcmOrdModel));
	         
			 for (WorkflowTask task : tasks)
			 {
			     String taskId = task.getId();
				 log.info("update task.id="+taskId);
				 
			     workflowService.updateTask(taskId, params, null, null);  
			 }

		}
		
		return null;
	}
	
	/*
	 * Backup 19/01/2016
	 * For Paknampo
	 */
	public String _updateWorkflow(PcmOrdModel pcmOrdModel, DelegateTask task) throws Exception {
		
		WorkflowTaskQuery query = new WorkflowTaskQuery();

		query.setTaskState(WorkflowTaskState.IN_PROGRESS);
		query.setProcessId(pcmOrdModel.getWorkflowInsId());

		List<WorkflowTask> tasks = workflowService.queryTasks(query, true);

		if (tasks.size() > 0) {
		     Map<QName, Serializable> params = new HashMap<QName, Serializable>();
		     
	         NodeRef folderNodeRef = new NodeRef(pcmOrdModel.getFolderRef());
	        
	         setReviewer(params, pcmOrdModel, folderNodeRef);
	         
//	         for(Entry<QName, Serializable> e : params.entrySet()) {
//	        	 log.info("params:"+e.getKey().toString());
//	         }
//		 
	         params.put(PcmWorkflowConstant.PROP_REMARK, pcmOrdModel.getRemark());
	         params.put(PcmWorkflowConstant.PROP_REQUESTED_TIME, pcmOrdModel.getRequestedTime());
	
	         List<NodeRef> docList = new ArrayList<NodeRef>();
	         docList.add(new NodeRef(pcmOrdModel.getDocRef()));
	
	         List<NodeRef> attachDocList = new ArrayList<NodeRef>();
	         if( pcmOrdModel.getListAttachDoc()!=null) {
	         	for(String nodeRef : pcmOrdModel.getListAttachDoc()) {
	         		attachDocList.add(new NodeRef(nodeRef));
	             }
	         }
	         
	         String desc = getWorkflowDescription(pcmOrdModel);
	         params.put(WorkflowModel.PROP_DESCRIPTION, desc);
	         
			 for (WorkflowTask t : tasks)
			 {
			     String taskId = t.getId();
				 log.info("update task.id="+taskId);
				 
			     workflowService.updateTask(taskId, params, null, null);   
			 }
			 
			 // update delegate task
			 task.getExecution().setVariable(WorkflowModel.PROP_DESCRIPTION.getLocalName(), desc);
			 
        	 for(int i=1; i<=CommonConstant.MAX_APPROVER;i++) {
				 updateExecutionVariable(params, task, "reviewer"+i+"Group");
				 updateExecutionVariable(params, task, "reviewer"+i+"List");
				 
				 updateExecutionVariable(params, task, "reviewer"+i+"RequirePercentComplete");
        	 }			
		}
		
		return null;
	}
	
	/*
	 * For NBTC
	 */
	public String updateWorkflow(PcmOrdModel pcmOrdModel, DelegateTask task) throws Exception {

	     Map<QName, Serializable> params = new HashMap<QName, Serializable>();
         params.put(PcmWorkflowConstant.PROP_REMARK, pcmOrdModel.getRemark());
         params.put(PcmWorkflowConstant.PROP_REQUESTED_TIME, pcmOrdModel.getRequestedTime());

         List<NodeRef> docList = new ArrayList<NodeRef>();
         docList.add(new NodeRef(pcmOrdModel.getDocRef()));

         List<NodeRef> attachDocList = new ArrayList<NodeRef>();
         if( pcmOrdModel.getListAttachDoc()!=null) {
         	for(String nodeRef : pcmOrdModel.getListAttachDoc()) {
         		attachDocList.add(new NodeRef(nodeRef));
             }
         }
         
         String desc = getWorkflowDescription(pcmOrdModel);
         params.put(WorkflowModel.PROP_DESCRIPTION, desc);

	     String taskId = task.getId();
	     workflowService.updateTask("activiti$"+taskId, params, null, null);   

		 // update delegate task
		 task.getExecution().setVariable("bpm_"+WorkflowModel.PROP_DESCRIPTION.getLocalName(), desc);
		 task.getExecution().setVariable("bpm_"+WorkflowModel.PROP_WORKFLOW_DESCRIPTION.getLocalName(), desc);
		 
		 ActivitiScriptNodeList l = new ActivitiScriptNodeList();
		 ActivitiScriptNode n = new ActivitiScriptNode(new NodeRef(pcmOrdModel.getDocRef()), services);
		 l.add(n);
		 task.getExecution().setVariable(WF_PREFIX + "document", l);
		 
//		 for (int i=1; i<=PcmOrdConstant.MAX_DUMMY_FIELD; i++) {
//			 updateExecutionVariable(params, task, "field"+i);
//		 }
		 

//    	 for(int i=1; i<=CommonConstant.MAX_APPROVER;i++) {
//			 updateExecutionVariable(params, task, "reviewer"+i+"Group");
//			 updateExecutionVariable(params, task, "reviewer"+i+"List");
//			 
//			 updateExecutionVariable(params, task, "reviewer"+i+"RequirePercentComplete");
//    	 }
		 
		return null;
	}
	
	private String getWorkflowDescription(PcmOrdModel pcmOrdModel) throws Exception {
		
		MainMasterModel descFormatModel = adminMasterService.getSystemConfig(MainMasterConstant.SCC_PCM_ORD_WF_DESC_FORMAT);
		String descFormat = descFormatModel.getFlag1();
		
		JSONObject memoMap = PcmOrdUtil.convertToJSONObject(pcmOrdModel,false);
		
		Writer w = null;
		TemplateProcessor pc = templateService.getTemplateProcessor("freemarker");
		w = new StringWriter();
		pc.processString(descFormat, memoMap, w);
		
		return w.toString();
	}

	public JSONArray listAssignee(String id) throws Exception {
		JSONArray jsArr = new JSONArray();

		int index = 0;
		List<Map<String, Object>> list = pcmOrdService.listWorkflowPath(id);
		for (Map<String, Object> map : list) {
//			jsArr.put(MainApprovalMatrixUtil.createAssigneeGridModel(index++, (String)map.get("LEVEL"), (String)map.get("U") , (String)map.get("G"), (Boolean)map.get("IRA")));
		}

		return jsArr;
	}
	
	public JSONArray listTask(String id) throws Exception {
		JSONArray jsArr = new JSONArray();

		int index = 0;
		try {
			PcmOrdModel pcmOrdModel = pcmOrdService.get(id);
			 
			final String workflowInsId = pcmOrdModel.getWorkflowInsId();
			
			log.info("workflowInsId:" + workflowInsId);
			
			final List<WorkflowTask> tasks = AuthenticationUtil.runAs(new RunAsWork<List<WorkflowTask>>()
				    {
						public List<WorkflowTask> doWork() throws Exception
						{
							WorkflowTaskQuery workflowTaskQuery = new WorkflowTaskQuery();
					    	workflowTaskQuery.setActive(true);
					    	workflowTaskQuery.setTaskState(WorkflowTaskState.IN_PROGRESS);
					    	workflowTaskQuery.setProcessId(workflowInsId);
					    	
							return workflowService.queryTasks(workflowTaskQuery);
						}
				    }, AuthenticationUtil.getAdminUserName());
			
			String taskJson = "";
			for(WorkflowTask task : tasks) {
				log.info("task:"+task.getId());
				if (taskJson.length() > 0) {
					taskJson += ",";
				}
				
				QName q1 = null;
				QName q1_1 = null;
				QName q2 = null;
				for(QName key : task.getProperties().keySet()) {
//					log.info("    "+key.toString()+":"+task.getProperties().get(key));
					
					if (key.toString().contains("}owner")) {
						q1 = key;
					}
					else
					if (key.toString().contains("}nextActionGroupAssignee")) {
						q1_1 = key;
					}
					else
					if (key.toString().contains("}status")) {
						q2 = key;
					}
				}
				
				String assignedTo = "";
				if(task.getProperties().get(q1) == null) {
					List<NodeRef> list = (List<NodeRef>)task.getProperties().get(q1_1);
					for(NodeRef nodeRef : list) {
						if (assignedTo.length() > 0) {
							assignedTo += ",";
						}
						Map<QName, Serializable> props = nodeService.getProperties(nodeRef);
						for(QName key : props.keySet()) {
//							log.info("  prop : "+key.toString()+":"+props.get(key));
							if (key.toString().contains("}authorityDisplayName")) {
								assignedTo += props.get(key);
								break;
							}
						}
					}
				}
				else {
					assignedTo = task.getProperties().get(q1).toString();
				}
				
				jsArr.put(createTaskGridModel(index++, task.getTitle(), assignedTo, (String)task.getProperties().get(q2)));
			}
			
		 } catch (Exception ex) {
			log.error("", ex);
		 }

		return jsArr;
	}
	
	private JSONObject createTaskGridModel(int id, String type, String assignedTo, String status)  throws Exception {
		JSONObject jsObj = new JSONObject();
		
		jsObj.put("id", id);
		jsObj.put("type", type);
		jsObj.put("assignedTo", MainUtil.trimComma(assignedTo));
		jsObj.put("status", status);
		
		return jsObj;
	}
	
	public JSONArray listDetail(String id) throws Exception {
		JSONArray jsArr = new JSONArray();

		int index = 0;
		List<PcmOrdWorkflowModel> list = listWorkflowById(id);
		
		List<Long> workflowIds = new ArrayList<Long>();
		for(PcmOrdWorkflowModel wfModel : list) {
			workflowIds.add(wfModel.getId());
		}
		  
		List<PcmOrdWorkflowHistoryModel> hList = listWorkflowHistory(workflowIds);

		if (hList.isEmpty()) {
			for (PcmOrdWorkflowModel wfModel : list) {
				hList = listWorkflowHistory(wfModel.getTaskId());
				break;
			}
			Collections.reverse(hList);
		}

		for (PcmOrdWorkflowHistoryModel model : hList) {
			jsArr.put(createDetailGridModel(index++, model.getTime(), model.getUser_(), model.getAction(), model.getTask(), StringUtils.defaultIfBlank(model.getComment(),"").replace("\\n", "<br>").replace("'", "\\'")));
		}

		return jsArr;
	}
	
	private JSONObject createDetailGridModel(int id, Timestamp time, String by, String status, String task, String comment)  throws Exception {
		JSONObject jsObj = new JSONObject();
		
		jsObj.put("id", id);
		jsObj.put("time", CommonDateTimeUtil.convertToGridDateTime(time));
		jsObj.put("by", by);
		jsObj.put("status", status);
		jsObj.put("task", task);
		jsObj.put("comment", comment);
		
		return jsObj;
	}
	
	public List<PcmOrdWorkflowModel> listWorkflowById(String id) {
		List<PcmOrdWorkflowModel> list = null;
		
		SqlSession session = PcmUtil.openSession(dataSource);
		
		try {
			
            PcmOrdWorkflowDAO dao = session.getMapper(PcmOrdWorkflowDAO.class);
            
    		list = dao.listByMasterId(id);
            
            session.commit();
            
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
		
		return list;	
	}
	
	public List<PcmOrdWorkflowHistoryModel> listWorkflowHistory(List<Long> workflowIds) {
		List<PcmOrdWorkflowHistoryModel> list = null;
		
		SqlSession session = PcmUtil.openSession(dataSource);
		
		try {
			
            PcmOrdWorkflowHistoryDAO dao = session.getMapper(PcmOrdWorkflowHistoryDAO.class);
            
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("ids", workflowIds);
    		
    		list = dao.listByMasterId(params);
            
            session.commit();
            
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
		
		return list;
	}
	
	public List<PcmOrdWorkflowHistoryModel> listWorkflowHistory(String taskId) {

		List<PcmOrdWorkflowHistoryModel> list = new ArrayList<PcmOrdWorkflowHistoryModel>();
		try {
			WorkflowTask task = workflowService.getTaskById(taskId);
			String historyStr = (String)task.getProperties().get("memwf_commentHistory");
			
			JSONArray history = new JSONArray("["+historyStr.replaceAll("/", "")+"]");
			 
			log.info("history.length="+history.length());
			
			for(int i=0; i < history.length(); i++) {
				 JSONObject h = history.getJSONObject(i);
				 
				 list.add(new PcmOrdWorkflowHistoryModel(h));
			}
		} catch (Exception ex) {
			log.error("", ex);
		}
		
		return list;
	}
	
	private Object updateExecutionVariable(Map<QName, Serializable> params, DelegateTask task, String varName) {
		Object obj = ObjectUtils.defaultIfNull(params.get(QName.createQName(WF_URI + varName)), "");
		
		task.getExecution().setVariable(WF_PREFIX+varName, obj);
		
		return obj;
	}	
}
