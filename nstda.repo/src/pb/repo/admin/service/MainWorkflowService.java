package pb.repo.admin.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.repo.workflow.WorkflowModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
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
import pb.repo.admin.constant.MainUserGroupConstant;
import pb.repo.admin.constant.MainWorkflowConstant;
import pb.repo.admin.dao.MainWorkflowDAO;
import pb.repo.admin.dao.MainWorkflowHistoryDAO;
import pb.repo.admin.dao.MainWorkflowNextActorDAO;
import pb.repo.admin.dao.MainWorkflowReviewerDAO;
import pb.repo.admin.model.MainHrEmployeeModel;
import pb.repo.admin.model.MainUserGroupModel;
import pb.repo.admin.model.MainWorkflowHistoryModel;
import pb.repo.admin.model.MainWorkflowModel;
import pb.repo.admin.model.MainWorkflowNextActorModel;
import pb.repo.admin.model.MainWorkflowReviewerModel;
import pb.repo.admin.model.SubModuleModel;
import pb.repo.admin.util.MainUserGroupUtil;
import pb.repo.admin.util.MainUtil;
import pb.repo.admin.util.MainWorkflowUtil;
import pb.repo.common.mybatis.DbConnectionFactory;

@Service
public class MainWorkflowService {
	
	private static Logger log = Logger.getLogger(MainWorkflowService.class);
	
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
	DataSource dataSource;
	
	@Autowired
	OwnableService ownableService;
	
	@Autowired
	TemplateService templateService;
	
	@Autowired
	AlfrescoService alfrescoService;
	
	@Autowired
	AdminHrEmployeeService adminHrEmployeeService;
	
	@Autowired
	AdminWkfConfigService adminWkfConfigService;
	
	SubModuleService moduleService;
	
	private String MODEL_URI;
	private String WF_URI;
	private String WF_PREFIX;
	
	public void setModuleService(SubModuleService moduleService) {
		this.moduleService = moduleService;
		
		MODEL_URI = moduleService.getModelUri();
		WF_URI = moduleService.getWfUri();
		WF_PREFIX = moduleService.getModelPrefix();
	}
	
	public String startWorkflow(SubModuleModel model, String docType) throws Exception {
		
		//Map<String, Object> map = moduleService.convertToMap(model);
		
		log.info("<- Start Workflow -> "+model.getId());
		
		String instanceId = null;
		
		try {
			String action = moduleService.getActionCaption(MainWorkflowConstant.TA_START);
	        String stateTask = MainWorkflowConstant.TN_REQUESTER_CAPTION;
			String wfName = moduleService.getWorkflowName();
			log.info("  WF Name:"+wfName);
			WorkflowDefinition workflow = workflowService.getDefinitionByName("activiti$"+wfName);
			log.info("  workflow:"+workflow);
		
	     	NodeRef workflowPackage = workflowService.createPackage(null);
	     	
	     	// Set to WorkFlow
	        Map<QName, Serializable> parameters = new HashMap<QName, Serializable>();
	        parameters.put(WorkflowModel.ASSOC_PACKAGE, workflowPackage);
	        
	        final NodeRef folderNodeRef = new NodeRef(model.getFolderRef());
	        final String createdBy = model.getCreatedBy();
	        		
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
	        
	        setRequesterPermission(folderNodeRef, model);
	        
	        setRelatedUserPermission(folderNodeRef, model);
			
	        setReviewer(parameters, model, folderNodeRef, docType);
	        
	        setNextActor(model, folderNodeRef);
	        
	        setSpecialUserPermission(folderNodeRef, model);
	        
	        MainWorkflowReviewerModel paramModel = new MainWorkflowReviewerModel();
	        paramModel.setMasterId(model.getId());
	        paramModel.setLevel(1);
	        MainWorkflowReviewerModel reviewerModel = getReviewer(paramModel);
	        if (reviewerModel != null) {
	        	parameters.put(moduleService.getPropNextReviewers(), MainUserGroupUtil.codes2logins(reviewerModel.getReviewerUser()));
	        } else {
	        	log.error("ERR:No Reviewer Level 1 of "+model.getId());
	        }
	        
	        log.info("Doc Ref : " + model.getDocRef());
	        List<NodeRef> docList = new ArrayList<NodeRef>();
	        docList.add(new NodeRef(model.getDocRef()));
	
	        List<NodeRef> attachDocList = new ArrayList<NodeRef>();
	        if(model.getListAttachDoc() !=null) {
	        	for(String nodeRef : model.getListAttachDoc()) {
	        		attachDocList.add(new NodeRef(nodeRef));
	            }
	        }
	        
	        moduleService.setWorkflowParameters(parameters, model, docList, attachDocList);
	        
	        parameters.put(WorkflowModel.PROP_WORKFLOW_DESCRIPTION, moduleService.getWorkflowDescription(model));
	        
	        WorkflowPath workflowPath = workflowService.startWorkflow(workflow.getId(), parameters);
	        log.info("Start Workflow Successfully");
	        
	        instanceId = workflowPath.getInstance().getId();
	        WorkflowTask startTask = workflowService.getStartTask(instanceId);
	        
	        model.setWorkflowInsId(instanceId);
	        model.setWaitingLevel(1);
	    	moduleService.update(model);
	        
	        Long wfKey = Long.valueOf(this.getKey());
	        MainWorkflowModel workflowModel = new MainWorkflowModel();
	        workflowModel.setId(wfKey);
	        workflowModel.setMasterId(model.getId());
			workflowModel.setType(moduleService.getSubModuleType());
			workflowModel.setWorkflowInsId(instanceId);
			workflowModel.setTaskId(startTask.getId());
			workflowModel.setExecutionId("");
			workflowModel.setStatus(action);
			workflowModel.setBy(model.getCreatedBy());
			workflowModel.setCreatedBy(model.getCreatedBy());
			workflowModel.setAssignee(reviewerModel.getReviewerUser());
	        
	        addWorkflow(workflowModel);
	        
	        MainWorkflowHistoryModel workflowHistoryModel = moduleService.getReqByWorkflowHistory(model); // Extra History
	        if (workflowHistoryModel != null) {
		        workflowHistoryModel.setMasterId(wfKey);
		        addWorkflowHistory(workflowHistoryModel);
	        }
	        
			workflowHistoryModel = new MainWorkflowHistoryModel();
	        workflowHistoryModel.setMasterId(wfKey);
			workflowHistoryModel.setLevel(0);
			workflowHistoryModel.setAction(action);
			workflowHistoryModel.setBy(model.getCreatedBy());
			workflowHistoryModel.setTask(stateTask);
			workflowHistoryModel.setComment(moduleService.getFirstComment(model));
			workflowHistoryModel.setStatus(moduleService.getFirstStatus());
	        addWorkflowHistory(workflowHistoryModel);
	        
	        
	        workflowHistoryModel = moduleService.getAppByWorkflowHistory(model); // Extra History
	        if (workflowHistoryModel != null) {
		        workflowHistoryModel.setMasterId(wfKey);
		        addWorkflowHistory(workflowHistoryModel);
	        }
	        
	        log.info("Update Database Successfully");
	        
	        workflowService.endTask(startTask.getId(), null);
	        log.info("End Start Task : "+startTask.getId());
        
		} catch (Exception ex) {
			log.error("", ex);
			throw ex;
		}

		return instanceId;
	}
	
	public void setFolderPermission(final NodeRef folderNodeRef, final String user) {
		
		// add permission to current user
        AuthenticationUtil.runAs(new RunAsWork<String>()
	    {
			public String doWork() throws Exception
			{
				permissionService.setPermission(folderNodeRef, user, "SiteCollaborator", true);
		    	return null;
			}
	    }, AuthenticationUtil.getAdminUserName());
	}	
	
	private void setRequesterPermission(final NodeRef folderNodeRef, Object model) {
		
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
	
	private void setRelatedUserPermission(final NodeRef folderNodeRef, Object model) {
		
		final List<String> list = moduleService.listRelatedUser((SubModuleModel)model); 
		if (list!=null) {
			// add permission to related user
	        AuthenticationUtil.runAs(new RunAsWork<String>()
		    {
				public String doWork() throws Exception
				{
					for(String u : list) {
						permissionService.setPermission(folderNodeRef, u, "SiteCollaborator", true);
					}
			    	return null;
				}
		    }, AuthenticationUtil.getAdminUserName());
		}
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

    private void setNextActor(final SubModuleModel model, final NodeRef folderNodeRef) throws Exception {
    	    		
	        AuthenticationUtil.runAs(new RunAsWork<String>()
    	    {
    			public String doWork() throws Exception
    			{
    				deleteNextActorByMasterId(model.getId());
    				    				
    		    	List<MainWorkflowNextActorModel> list = moduleService.listNextActor(model);
    		    	for(MainWorkflowNextActorModel actorModel : list) {
    		    		addNextActor(actorModel);

    		    		String u = MainUserGroupUtil.code2login(actorModel.getActorUser());
		        		if(u!=null && !u.equals(CommonConstant.DUMMY_EMPLOYEE_CODE) && !permissionService.getPermissions(folderNodeRef).contains(u)) {
		        			permissionService.setPermission(folderNodeRef, u, "SiteCollaborator", true);
		        		}
    		    	}

    	        	return "A";
    			}
    	    }, AuthenticationUtil.getAdminUserName());    		
    }
    
    private void setReviewer(Map<QName, Serializable> parameters, SubModuleModel model, final NodeRef folderNodeRef, String docType) throws Exception {
    	
    	Map<String, String> bossMap = moduleService.getBossMap(docType, model);

//        	Map<String, String> bossMap = new LinkedHashMap<String, String>();
//        	bossMap.put("L01", "001509");
//        	bossMap.put("L02", "000511");
//        	bossMap.put("L03", "000090");
    	
		log.info("setReviewer :");
    	for(Entry e : bossMap.entrySet()) {
    		log.info("  - "+e.getKey()+":"+e.getValue());
    	}
    	
    	final List<String> permissionGroup = new ArrayList<String>();
    	final List<String> permissionUser = new ArrayList<String>();
    	//List<Double> percents = new ArrayList<Double>();
    	List<String> userCollectionList = null; 
    	
    	deleteReviewerByMasterId(model.getId());
    	MainWorkflowReviewerModel reviewerModel = null;
    	
    	Map<Integer,List<String>> lvl = new HashMap<Integer, List<String>>();
//        	for(MainApprovalMatrixDtlModel dtl : listDtl) {
    	int level = 1;
    	for(Entry e : bossMap.entrySet()) {
    		reviewerModel = new MainWorkflowReviewerModel();
    		userCollectionList = new ArrayList<String>();
    		String reviewerUser = (String)e.getValue();
    		String reviewerGroup = "";
    		
    		reviewerModel.setReviewerGroup(reviewerGroup);
    		reviewerModel.setReviewerUser(reviewerUser);
    		reviewerModel.setMasterId(model.getId());
    		reviewerModel.setLevel(level);
    		reviewerModel.setPercent(0.0);
    		reviewerModel.setRewarning(0);
    		reviewerModel.setCreatedBy(model.getCreatedBy());
    		addReviewer(reviewerModel);
    		
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
    		
    		lvl.put(level, userCollectionList);

    		level++;
    	}
    	
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
    	
    }

	public Long getKey() {
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();;
		Long key = null;
		try {
			
            MainWorkflowDAO dao = session.getMapper(MainWorkflowDAO.class);
            
            key = dao.getKey();
            
        } finally {
        	session.close();
        }
		return key;
		
	}
	
	public void addWorkflow(MainWorkflowModel mainWorkflowModel) {
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
		
		try {
			
            MainWorkflowDAO dao = session.getMapper(MainWorkflowDAO.class);
            
            dao.add(mainWorkflowModel);
            
            session.commit();
            
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
		
	}
	
	public void update(MainWorkflowModel workflowModel) {
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
		
		try {
			
            MainWorkflowDAO dao = session.getMapper(MainWorkflowDAO.class);
            
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
	
	public MainWorkflowModel getLastWorkflow(MainWorkflowModel mainWorkflowModel) {
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
		MainWorkflowModel model = new MainWorkflowModel();
		try {
		
            MainWorkflowDAO dao = session.getMapper(MainWorkflowDAO.class);
            
            model = dao.getLastWorkflow(mainWorkflowModel);
           
        } catch (Exception ex) {
			log.error("", ex);
        } finally {
        	session.close();
        }
		return model;
	}
	
	public void addWorkflowHistory(MainWorkflowHistoryModel mainWorkflowHistoryModel) {
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
		
		try {
			
            MainWorkflowHistoryDAO dao = session.getMapper(MainWorkflowHistoryDAO.class);
            
            mainWorkflowHistoryModel.setTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            
            dao.add(mainWorkflowHistoryModel);
            
            session.commit();
            
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
	}
	
	public void updateWorkflowHistory(MainWorkflowHistoryModel workflowHistoryModel) {
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
		
		try {
			
            MainWorkflowHistoryDAO dao = session.getMapper(MainWorkflowHistoryDAO.class);
            
            dao.update(workflowHistoryModel);
            
            session.commit();
            
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
		
	}
	
	
	public List<MainWorkflowHistoryModel> listHistory(Map<String,Object> params) {
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
		List<MainWorkflowHistoryModel> list = new ArrayList<MainWorkflowHistoryModel>();
		
		try {
			
            MainWorkflowHistoryDAO dao = session.getMapper(MainWorkflowHistoryDAO.class);
            
            list =  dao.listHistory(params);
            
        } finally {
        	session.close();
        }
		
		return list;
		
	}
	

	public String updateWorkflow(SubModuleModel model, String actionUserGroups, String relatedUserGroups, String docType) throws Exception {
		
		WorkflowTaskQuery query = new WorkflowTaskQuery();

		query.setTaskState(WorkflowTaskState.IN_PROGRESS);
		query.setProcessId(model.getWorkflowInsId());

		List<WorkflowTask> tasks = workflowService.queryTasks(query, true);
		final NodeRef folderNodeRef = new NodeRef(model.getFolderRef());

		if (tasks.size() > 0) {
		     Map<QName, Serializable> params = new HashMap<QName, Serializable>();
		     
//	         NodeRef folderNodeRef = new NodeRef(model.getFolderRef());
	        
	         //setRelatedAssignee(params, relatedUserGroups, folderNodeRef);
		     
	         setReviewer(params, model, folderNodeRef, docType);
	        
	         setNextActor(model, folderNodeRef);
	        
	         setSpecialUserPermission(folderNodeRef, model);
			
	         List<NodeRef> docList = new ArrayList<NodeRef>();
	         docList.add(new NodeRef(model.getDocRef()));
	
	         List<NodeRef> attachDocList = new ArrayList<NodeRef>();
	         if(model.getListAttachDoc()!=null) {
	         	for(String nodeRef : model.getListAttachDoc()) {
	         		attachDocList.add(new NodeRef(nodeRef));
	             }
	         }
	         
	         params.put(QName.createQName(MODEL_URI , "document"), (Serializable)docList);
	         
	         params.put(WorkflowModel.PROP_DESCRIPTION, moduleService.getWorkflowDescription(model));
	         
	         moduleService.setWorkflowParameters(params, model, docList, attachDocList);
	         
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
	public String _updateWorkflow(SubModuleModel model, DelegateTask task) throws Exception {
		
		WorkflowTaskQuery query = new WorkflowTaskQuery();

		query.setTaskState(WorkflowTaskState.IN_PROGRESS);
		query.setProcessId(model.getWorkflowInsId());

		List<WorkflowTask> tasks = workflowService.queryTasks(query, true);

		if (tasks.size() > 0) {
		     Map<QName, Serializable> params = new HashMap<QName, Serializable>();
		     
	         NodeRef folderNodeRef = new NodeRef(model.getFolderRef());
	        
	         setReviewer(params, model, folderNodeRef, null);
	         
//	         for(Entry<QName, Serializable> e : params.entrySet()) {
//	        	 log.info("params:"+e.getKey().toString());
//	         }
//		 
	         List<NodeRef> docList = new ArrayList<NodeRef>();
	         docList.add(new NodeRef(model.getDocRef()));
	
	         List<NodeRef> attachDocList = new ArrayList<NodeRef>();
	         if( model.getListAttachDoc()!=null) {
	         	for(String nodeRef : model.getListAttachDoc()) {
	         		attachDocList.add(new NodeRef(nodeRef));
	             }
	         }
	         
	         String desc = moduleService.getWorkflowDescription(model);
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
	public String updateWorkflow(SubModuleModel model, DelegateTask task) throws Exception {
	     Map<QName, Serializable> params = new HashMap<QName, Serializable>();

//         List<NodeRef> docList = new ArrayList<NodeRef>();
//         docList.add(new NodeRef(model.getDocRef()));
//
//         List<NodeRef> attachDocList = new ArrayList<NodeRef>();
//         if( model.getListAttachDoc()!=null) {
//         	for(String nodeRef : model.getListAttachDoc()) {
//         		attachDocList.add(new NodeRef(nodeRef));
//             }
//         }
         
         String desc = moduleService.getWorkflowDescription(model);
         params.put(WorkflowModel.PROP_DESCRIPTION, desc);

	     String taskId = task.getId();
	     workflowService.updateTask("activiti$"+taskId, params, null, null);   

		 // update delegate task
		 task.getExecution().setVariable("bpm_"+WorkflowModel.PROP_DESCRIPTION.getLocalName(), desc);
		 task.getExecution().setVariable("bpm_"+WorkflowModel.PROP_WORKFLOW_DESCRIPTION.getLocalName(), desc);
		 
//		 ActivitiScriptNodeList l = new ActivitiScriptNodeList();
//			log.info("pass 17.1 "+model.getDocRef()+","+services);
//		 ActivitiScriptNode n = new ActivitiScriptNode(new NodeRef(model.getDocRef()), services);
//			log.info("pass 17.2 "+n);
//		 l.add(n);
//			log.info("pass 17.3 "+WF_PREFIX);
//		 task.getExecution().setVariable(WF_PREFIX + "document", l);
//			log.info("pass 18");
		 
//		 for (int i=1; i<=PcmReqConstant.MAX_DUMMY_FIELD; i++) {
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
	
	public List<Map<String, Object>> listWorkflowPath(String id) {
		List<Map<String, Object>> list = null;
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            
            MainWorkflowReviewerDAO dao = session.getMapper(MainWorkflowReviewerDAO.class);

    		list = dao.listWorkflowPath(id);
            
        } catch (Exception ex) {
			log.error("", ex);
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	public JSONArray listAssignee(String id, String lang) throws Exception {
		JSONArray jsArr = new JSONArray();

		MainWorkflowModel mainWorkflowModel = new MainWorkflowModel();
		mainWorkflowModel.setMasterId(id);
		mainWorkflowModel = getLastWorkflow(mainWorkflowModel);
		
		int index = 0;
		List<Map<String, Object>> list = moduleService.listWorkflowPath(id, lang);
		for (Map<String, Object> map : list) {
			jsArr.put(MainWorkflowUtil.createAssigneeGridModel(index++, (String)map.get("LEVEL"), (String)map.get("U") , (String)map.get("G"), (Boolean)map.get("IRA")));
		}

		return jsArr;
	}
	
	public JSONArray listTask(String id) throws Exception {
		JSONArray jsArr = new JSONArray();

		int index = 0;
		try {
			SubModuleModel model = (SubModuleModel)moduleService.get(id);
			 
			final String workflowInsId = model.getWorkflowInsId();
			
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
				
				MainHrEmployeeModel empModel = adminHrEmployeeService.get(assignedTo);
				
				jsArr.put(createTaskGridModel(index++, task.getTitle(), assignedTo + " - " + empModel.getFirstName(), (String)task.getProperties().get(q2)));
			}
			
			if (jsArr.length()==0) {
				jsArr.put(createTaskGridModel(index++, moduleService.getNextActionInfo(model), "", ""));
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
	
	public JSONArray listDetail(String id, String lang) throws Exception {
		JSONArray jsArr = new JSONArray();

		int index = 0;
		List<MainWorkflowModel> list = listWorkflowById(id);
		
		List<Long> workflowIds = new ArrayList<Long>();
		for(MainWorkflowModel wfModel : list) {
			workflowIds.add(wfModel.getId());
		}
		  
		List<MainWorkflowHistoryModel> hList = listWorkflowHistory(workflowIds);
		
//
//		if (hList.isEmpty()) {
//			for (MainWorkflowModel wfModel : list) {
//				hList = listWorkflowHistory(wfModel.getTaskId());
//				break;
//			}
//			Collections.reverse(hList);
//		}
		lang = (lang!=null && lang.startsWith("th") ? "_th" : "");
		
		for (MainWorkflowHistoryModel model : hList) {
			Map<String, Object> empModel = adminHrEmployeeService.getWithDtl(model.getBy());

			jsArr.put(createDetailGridModel(index++, model.getTime(), empModel!=null ? (String)empModel.get("first_name"+lang) : model.getBy(), model.getAction(), model.getTask(), StringUtils.defaultIfBlank(model.getComment(),"").replace("\\n", "<br>").replace("'", "\\'")));
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
	
	public List<MainWorkflowModel> listWorkflowById(String id) {
		List<MainWorkflowModel> list = null;
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
		
		try {
			
            MainWorkflowDAO dao = session.getMapper(MainWorkflowDAO.class);
            
    		list = dao.listByMasterId(id);
            
        } finally {
        	session.close();
        }
		
		return list;	
	}
	
	public List<MainWorkflowHistoryModel> listWorkflowHistory(List<Long> workflowIds) {
		List<MainWorkflowHistoryModel> list = null;
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
		
		try {
			
            MainWorkflowHistoryDAO dao = session.getMapper(MainWorkflowHistoryDAO.class);
            
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("ids", workflowIds);
    		
    		list = dao.listByMasterId(params);
            
        } finally {
        	session.close();
        }
		
		return list;
	}
	
//	public List<MainWorkflowHistoryModel> listWorkflowHistory(String taskId) {
//
//		List<MainWorkflowHistoryModel> list = new ArrayList<MainWorkflowHistoryModel>();
//		try {
//			WorkflowTask task = workflowService.getTaskById(taskId);
//			String historyStr = (String)task.getProperties().get("pcmwf_commentHistory");
//			
//			JSONArray history = new JSONArray("["+historyStr.replaceAll("/", "")+"]");
//			 
//			log.info("history.length="+history.length());
//			
//			for(int i=0; i < history.length(); i++) {
//				 JSONObject h = history.getJSONObject(i);
//				 
//				 list.add(new MainWorkflowHistoryModel(h));
//			}
//		} catch (Exception ex) {
//			log.error("", ex);
//		}
//		
//		return list;
//	}
	
	private Object updateExecutionVariable(Map<QName, Serializable> params, DelegateTask task, String varName) {
		Object obj = ObjectUtils.defaultIfNull(params.get(QName.createQName(WF_URI + varName)), "");
		
		task.getExecution().setVariable(WF_PREFIX+varName, obj);
		
		return obj;
	}
	
	public void addReviewer(MainWorkflowReviewerModel reviewerModel) throws Exception {
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
           
            MainWorkflowReviewerDAO dao = session.getMapper(MainWorkflowReviewerDAO.class);

    		dao.add(reviewerModel);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
	}
	
	public void addNextActor(MainWorkflowNextActorModel actorModel) throws Exception {
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
           
            MainWorkflowNextActorDAO dao = session.getMapper(MainWorkflowNextActorDAO.class);

    		dao.add(actorModel);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
	}
	
	public MainWorkflowReviewerModel getReviewer(MainWorkflowReviewerModel paramModel) throws Exception {
		
		MainWorkflowReviewerModel model = null;
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
           
            MainWorkflowReviewerDAO dao = session.getMapper(MainWorkflowReviewerDAO.class);

    		List<MainWorkflowReviewerModel> list = dao.listByLevel(paramModel);
    		if (list.size()>0) {
    			model = list.get(0);
    		}
        } finally {
        	session.close();
        }
        
        return model;
	}		
	
	public void deleteReviewerByMasterId(String id) throws Exception {
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainWorkflowReviewerDAO dao = session.getMapper(MainWorkflowReviewerDAO.class);
            dao.deleteByMasterId(id);
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
	}
	
	public void deleteNextActorByMasterId(String id) throws Exception {
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainWorkflowNextActorDAO dao = session.getMapper(MainWorkflowNextActorDAO.class);
            dao.deleteByMasterId(id);
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
	}
	
	public Integer getLastReviewerLevel(String id) throws Exception {
		Integer lastLevel = null;
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainWorkflowReviewerDAO dao = session.getMapper(MainWorkflowReviewerDAO.class);
            lastLevel = dao.getLastLevel(id);
        } catch (Exception ex) {
			log.error("", ex);
        } finally {
        	session.close();
        }
        
        return lastLevel;
	}
	
	public String saveWorkflowHistory(DelegateExecution execution, String user, String stateTask, String taskComment, String action, DelegateTask task, String id, Integer level, String status) throws Exception {
		
		Timestamp now = CommonDateTimeUtil.now();
		action = moduleService.getActionCaption(action);
//		if (execution!=null) {
//		
//			String commentHistoryAttr = WF_PREFIX+"commentHistory";
//	
//			String allComment = execution.getVariable(commentHistoryAttr)!= null?
//						 execution.getVariable(commentHistoryAttr).toString():"";
//			
//			if(!allComment.equals("")){
//				allComment += ",";
//			}
//			
//			SimpleDateFormat formatter = new SimpleDateFormat("EEE d MMM yyyy HH:mm", Locale.ENGLISH);
//			String timeAction = formatter.format(now);
//			
//			// Build JSON string
//			String currentComment = "";
//			
//			currentComment +="{";
//			currentComment += "\"time\":\""+timeAction+"\",";
//			currentComment += "\"user\":\""+user+"\",";
//			currentComment += "\"action\":\""+action+"\",";
//			currentComment += "\"task\":\""+stateTask+"\",";
//			currentComment += "\"comment\":\""+org.json.simple.JSONObject.escape(taskComment)+"\" ";
//			currentComment += "}";
//			
//			execution.setVariable(commentHistoryAttr, allComment + currentComment);
//			if (task!=null) {
//				task.setVariable(commentHistoryAttr, allComment + currentComment);
//			}
//		}
		
		MainWorkflowModel workflowModel = new MainWorkflowModel();
		workflowModel.setMasterId(id.toString());
		workflowModel = getLastWorkflow(workflowModel);
		if (workflowModel!=null) {
			MainWorkflowHistoryModel workflowHistoryModel = new MainWorkflowHistoryModel();
			workflowHistoryModel.setTime(now);
			workflowHistoryModel.setBy(user);
			workflowHistoryModel.setAction(action);
			workflowHistoryModel.setTask(stateTask);
			workflowHistoryModel.setComment((taskComment!=null && !taskComment.equalsIgnoreCase(""))?taskComment:null);
			workflowHistoryModel.setMasterId(workflowModel.getId());
			workflowHistoryModel.setLevel(level);
			workflowHistoryModel.setStatus(status);
			addWorkflowHistory(workflowHistoryModel);
			
			workflowModel.setStatus(action);
			workflowModel.setBy(user);
			workflowModel.setByTime(workflowHistoryModel.getTime());
			update(workflowModel);	
		} else {
			log.info("Not Found WorkflowModel");
		}
		log.info("saveHistory finish");

		return action;
	}	

	public Object updateExecutionEntity(ExecutionEntity executionEntity, DelegateTask task, String varName) throws Exception {
		Object obj = ObjectUtils.defaultIfNull(task.getVariable(WF_PREFIX+varName), "");
		
		executionEntity.setVariable(WF_PREFIX+varName, obj);
		
		return obj;
	}
	
	public String getWorkflowStatus(String WF_PREFIX, ExecutionEntity executionEntity, String taskKey, Integer level) {
		/*
		 * Status
		 */
		String varName = WF_PREFIX+"workflowStatus";
		String workflowStatus = (String)executionEntity.getVariable(varName);

		log.info("  workflowStatus:" + workflowStatus);
		
		if(workflowStatus == null || !(workflowStatus.toUpperCase().equals("REJECT") || workflowStatus.toUpperCase().equals("CONSULT"))){
			workflowStatus = "WAPPR";
			
			//Check current task is in taskHistory?
			log.info("  Check Task History");
			String taskHistory = (String)executionEntity.getVariable(WF_PREFIX+"taskHistory");
			log.info("  taskHistory:" + taskHistory);
			String finalTaskKey = MainWorkflowUtil.getFinalTaskKey(taskKey, level);
			if(taskHistory.contains("|" + finalTaskKey)){
				log.info("  Found Task in History -> RESUBMIT");
				workflowStatus = "RESUBMIT";
			}
		}
		
//		for(String n : task.getVariableNames()) {
//			Object obj = task.getVariable(n);
//			log.info(n+":"+obj+":+"+((obj!=null) ? obj.getClass().getName() : ""));
//		}
		
		return workflowStatus.toUpperCase();
	}
	
	
	private void setSpecialUserPermission(final NodeRef folderNodeRef, final Object model) {
		
		// add permission to requester
        AuthenticationUtil.runAs(new RunAsWork<String>()
	    {
			public String doWork() throws Exception
			{
				List<String> list = moduleService.getSpecialUserForAddPermission((SubModuleModel)model);
				if (list!=null) {
					for(String s : list) {
						permissionService.setPermission(folderNodeRef, s, "SiteCollaborator", true);
					}
				}
		    	return null;
			}
	    }, AuthenticationUtil.getAdminUserName());
	}
	
	
}
