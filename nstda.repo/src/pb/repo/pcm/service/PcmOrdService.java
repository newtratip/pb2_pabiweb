package pb.repo.pcm.service;

import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.activiti.engine.RuntimeService;
import org.alfresco.model.ApplicationModel;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.coci.CheckOutCheckInService;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.TemplateProcessor;
import org.alfresco.service.cmr.repository.TemplateService;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.cmr.workflow.WorkflowService;
import org.alfresco.service.namespace.QName;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pb.common.constant.CommonConstant;
import pb.common.model.FileModel;
import pb.common.util.CommonDateTimeUtil;
import pb.common.util.CommonUtil;
import pb.common.util.FileUtil;
import pb.common.util.FolderUtil;
import pb.common.util.NodeUtil;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.constant.MainWkfConfigDocTypeConstant;
import pb.repo.admin.constant.MainWorkflowConstant;
import pb.repo.admin.dao.MainBossDAO;
import pb.repo.admin.dao.MainWorkflowDAO;
import pb.repo.admin.dao.MainWorkflowHistoryDAO;
import pb.repo.admin.dao.MainWorkflowNextActorDAO;
import pb.repo.admin.dao.MainWorkflowReviewerDAO;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.model.MainWorkflowHistoryModel;
import pb.repo.admin.model.MainWorkflowNextActorModel;
import pb.repo.admin.model.MainWorkflowReviewerModel;
import pb.repo.admin.model.SubModuleModel;
import pb.repo.admin.service.AdminHrEmployeeService;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminUserGroupService;
import pb.repo.admin.service.AdminWkfConfigService;
import pb.repo.admin.service.AlfrescoService;
import pb.repo.admin.service.MainSrcUrlService;
import pb.repo.admin.service.SubModuleService;
import pb.repo.admin.util.MainUserGroupUtil;
import pb.repo.common.mybatis.DbConnectionFactory;
import pb.repo.pcm.constant.PcmOrdConstant;
import pb.repo.pcm.constant.PcmOrdWorkflowConstant;
import pb.repo.pcm.dao.PcmOrdDAO;
import pb.repo.pcm.exception.NotFoundApprovalMatrixException;
import pb.repo.pcm.model.PcmOrdModel;
import pb.repo.pcm.util.PcmOrdUtil;
import pb.repo.pcm.util.PcmUtil;

@Service
public class PcmOrdService implements SubModuleService {

	private static Logger log = Logger.getLogger(PcmOrdService.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	AuthenticationService authService;
	
	@Autowired
	WorkflowService workflowService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	NodeService nodeService;
	
	@Autowired
	AuthorityService authorityService;
	
	@Autowired
	FileFolderService fileFolderService;

	@Autowired
	ContentService contentService;
	
	@Autowired
	AdminMasterService masterService;

	@Autowired
	TemplateService templateService;
	
	@Autowired
	SearchService searchService;
	
	@Autowired
	AdminUserGroupService userGroupService;
	
	@Autowired
	AlfrescoService alfrescoService;
	
	@Autowired
	CheckOutCheckInService checkOutCheckInService;	
	
	@Autowired
	MainSrcUrlService mainSrcUrlService;
	
	@Autowired
	PcmOrdWorkflowService mainWorkflowService;
	
	@Autowired
	AdminWkfConfigService adminWkfConfigService;
	
	@Autowired
	AdminHrEmployeeService adminHrEmployeeService;

	@Autowired
	private RuntimeService runtimeService;
	
	public PcmOrdModel save(PcmOrdModel model,Map<String, Object> docMap, List<Map<String, Object>> attList) throws Exception {
		
        SqlSession session = PcmUtil.openSession(dataSource);
        
        try {
            PcmOrdDAO pcmOrdDAO = session.getMapper(PcmOrdDAO.class);
//            PcmOrdDtlDAO pcmOrdDtlDAO = session.getMapper(PcmOrdDtlDAO.class);
            
            setUserGroupFields(model);
            
    		model.setUpdatedBy(model.getCreatedBy());
    		
        	doCommonSaveProcess(model, docMap, attList);
        	
    		/*
    		 * Add DB
    		 */
        	if (model.getCreatedTime()==null) {
        		pcmOrdDAO.add(model);
        	} else {
        		pcmOrdDAO.update(model);
        	}
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        	throw ex;
        } finally {
        	session.close();
        }

        return model;
	}
	
	public void updateStatus(PcmOrdModel model) throws Exception {
		
        SqlSession session = PcmUtil.openSession(dataSource);
        
        try {
            PcmOrdDAO dao = session.getMapper(PcmOrdDAO.class);
          
            dao.updateStatus(model);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        	throw ex;
        } finally {
        	session.close();
        }

	}
	
	public JSONObject validateAssignee(PcmOrdModel model) throws Exception {
		
		JSONObject result = new JSONObject();
		
        try {
            setUserGroupFields(model);
            
            Set<String> invalidUsers = new HashSet<String>();
            Set<String> invalidGroups = new HashSet<String>();
            
//           	List<MainApprovalMatrixDtlModel> listDtl = adminApprovalMatrixService.listDtl(model.getApprovalMatrixId());
//           	for (MainApprovalMatrixDtlModel dtlModel : listDtl) {
//                invalidUsers.addAll(userGroupService.listInvalidUser(dtlModel.getReviewerUser()));
//                invalidGroups.addAll(userGroupService.listInvalidGroup(dtlModel.getReviewerGroup()));
//           	}
            
            log.info("invalidUsers:"+invalidUsers);
            log.info("invalidGroups:"+invalidGroups);
            
        	if (invalidUsers.size()>0 || invalidGroups.size()>0) {
				result.put("valid", false);
        		result.put("users", invalidUsers);
        		result.put("groups", invalidGroups);
        	} else {
        		result.put("valid", true);
        	}
        	
        } catch (Exception ex) {
			log.error("", ex);
        	throw ex;
        } finally {
        }

        return result;
	}
	
	private void doCommonSaveProcess(PcmOrdModel model, Map<String, Object> docMap, List<Map<String, Object>> attList) throws Exception {
    	/*
    	 * Create ECM Folder
    	 */
		createEcmFolder(model);
		
		NodeRef folderNodeRef = new NodeRef(model.getFolderRef());
		
    	/*
    	 * Delete Old Files
    	 */
    	Set<QName> qnames = new HashSet<QName>();
    	qnames.add(ContentModel.TYPE_CONTENT);
    	qnames.add(ApplicationModel.TYPE_FILELINK);
    	List<ChildAssociationRef> docs = nodeService.getChildAssocs(folderNodeRef, qnames);
    	for(ChildAssociationRef doc : docs) {
    		
    		log.info("doc:"+doc.toString());
    		log.info("   childRef:"+doc.getChildRef().toString());
    		if (!doc.getQName().getLocalName().equals(model.getId()+".pdf")) { // it is not main.pdf
    			alfrescoService.cancelCheckout(doc.getChildRef());
    			alfrescoService.deleteFileFolder(doc.getChildRef().toString());
        		log.info("   delete");
    		}
    	}		
		
		/*
		 * Save Doc
		 */
		model.setDocRef(genDoc(model, folderNodeRef, docMap, getDocDesc()));
    	
    	/*
    	 * Save Attachments
    	 */
    	for(Map<String, Object> attMap : attList) {
    		log.info(" + "+attMap.get("name"));
		    model.setAttachDoc(genDoc(model, folderNodeRef, attMap, ""));
    	}
	}
	
	public String genDoc(PcmOrdModel model, NodeRef folderNodeRef, Map<String, Object> docMap, String docDesc) throws Exception {

		NodeRef docRef = null;
		
		if (docMap.get("url")!=null && !((String)docMap.get("url")).equals("")) {
			String url = (String)docMap.get("url"); 
			docRef = new NodeRef(NodeUtil.fullNodeRef(url));
			log.info("Gen Doc : "+docRef.toString());
			
			NodeRef refDocRef = alfrescoService.createLink(folderNodeRef, docRef, (String)docMap.get("name"));
		} else {
			/*
			 * Convert Base64 String to InputStream 
			 */
	    	FileUtil fileUtil = new FileUtil();
	    	InputStream is = fileUtil.base64InputStream((String)docMap.get("content"));
	    	
	    	String ecmFileName = (String)docMap.get("name");
	    	
	    	log.info("Gen Doc : "+ecmFileName);
	    	/*
	    	 * Put Doc in ECM
	    	 */
	    	NodeRef oldDocRef = alfrescoService.searchSimple(folderNodeRef, ecmFileName);
	    	if (oldDocRef != null) {
	    		alfrescoService.cancelCheckout(oldDocRef);
	    		alfrescoService.deleteFileFolder(oldDocRef.toString());
	    	}
	    	docRef = alfrescoService.createDoc(folderNodeRef, is, ecmFileName, docDesc);
		}
		
		return docRef.toString();
	}
	
	public List<FileModel> listFile(String id) throws Exception {

		final PcmOrdModel model = get(id, null);
		log.info("list file : memoId:"+model.getId());
		
		final NodeRef folderNodeRef = new NodeRef(model.getFolderRef());
		log.info("            folderRef:"+model.getFolderRef());
		
		List<FileModel> files = AuthenticationUtil.runAs(new RunAsWork<List<FileModel>>()
	    {
			public List<FileModel> doWork() throws Exception
			{
				List<FileModel> files = new ArrayList<FileModel>();
				
		    	Set<QName> qnames = new HashSet<QName>();
		    	qnames.add(ContentModel.TYPE_CONTENT);
		    	List<ChildAssociationRef> docs = nodeService.getChildAssocs(folderNodeRef, qnames);
		    	for(ChildAssociationRef doc : docs) {
		    		log.info("doc:"+doc.toString());
		    		log.info("   childRef:"+doc.getChildRef().toString());
		    		log.info("   qname:"+doc.getQName().getLocalName());
		    		
		    		if (!doc.getQName().getLocalName().equals(model.getId()+".pdf")) {
		    			FileModel fileModel = new FileModel();
		    			fileModel.setName(doc.getQName().getLocalName());
		    			fileModel.setNodeRef(doc.getChildRef().toString());
		    			fileModel.setAction("D");
		    			files.add(fileModel);
		    		}
		    	}
		    	return files;
			}
	    }, AuthenticationUtil.getAdminUserName());
		
		return files;
	}
	
	public JSONArray listCriteria() throws Exception {
		
		JSONArray jsArr = new JSONArray();
		
		List<MainMasterModel> criList = masterService.listSystemConfig(MainMasterConstant.SCC_PCM_ORD_CRITERIA);

		for(MainMasterModel model : criList) {
			JSONObject jsObj = new JSONObject();
			
			jsObj.put("emptyText", model.getFlag1());
			
			String[] fields = model.getFlag2().split(",");
			jsObj.put("field", fields[0]);
			if (fields.length > 1) {
				jsObj.put("width", Integer.parseInt(fields[1]));
				if (fields.length > 2) {
					jsObj.put("listWidth", Integer.parseInt(fields[2]));
				}
			}
			
			jsObj.put("url", model.getFlag3());
			jsObj.put("param", model.getFlag4());
			jsObj.put("trigger", model.getFlag5());
			
			jsArr.put(jsObj);
		}
		
		return jsArr;
	}	
	
	public JSONArray listGridField() throws Exception {
		
		JSONArray jsArr = new JSONArray();
		
		List<MainMasterModel> list = masterService.listSystemConfig(MainMasterConstant.SCC_PCM_ORD_GRID_FIELD);

		for(MainMasterModel model : list) {
			JSONObject jsObj = new JSONObject();
			
			jsObj.put("label", model.getFlag1());
			
			String[] fields = model.getFlag2().split(",");
			jsObj.put("field", fields[0]);
			if (fields.length > 1) {
				jsObj.put("width", fields[1]);
				if(fields.length > 2) {
					jsObj.put("align", fields[2]);
					if(fields.length > 3) {
						jsObj.put("wrap", fields[3]);
					}
				}
			}
			
			if (model.getFlag3()!=null && !model.getFlag3().equals("")) {
				jsObj.put("type", model.getFlag3());
			}
			
			jsArr.put(jsObj);
		}
		
		return jsArr;
	}
	
	private void createEcmFolder(PcmOrdModel model) throws Exception {
		
		boolean exists = (model.getFolderRef()!=null) && fileFolderService.exists(new NodeRef(model.getFolderRef()));
		
		if (!exists) {
			JSONObject map = PcmOrdUtil.convertToJSONObject(model);
			Calendar cal = Calendar.getInstance();
    		if (cal.get(Calendar.MONTH) >= 9) { // >= October (Thai Start Budget Year)
    			cal.add(Calendar.YEAR, 1);
    		}
    		Timestamp timestampValue = new Timestamp(cal.getTimeInMillis());
    		map.put(PcmOrdConstant.JFN_FISCAL_YEAR, timestampValue);
			
			
			Iterator it = map.keys();
			while(it.hasNext()) {
				Object obj = it.next();
				log.info("--"+obj.toString());
			}
	
			Writer w = null;
			TemplateProcessor pc = templateService.getTemplateProcessor("freemarker");
			
			MainMasterModel pathFormatModel = masterService.getSystemConfig(MainMasterConstant.SCC_PCM_ORD_PATH_FORMAT);
			String pathFormat = pathFormatModel.getFlag1();
			
			List<Object> paths = new ArrayList<Object>();
			
			String[] formats = pathFormat.split("/");
			for(String format : formats) {
				Map<String, Object> folderMap = new HashMap<String, Object>();
				paths.add(folderMap);
				
				int pos;
				if (format.indexOf(PcmOrdConstant.JFN_FISCAL_YEAR) >= 0
					|| format.indexOf(PcmOrdConstant.JFN_CREATED_TIME) >= 0
					|| format.indexOf(PcmOrdConstant.JFN_UPDATED_TIME) >= 0
					) {
					
					String dFormat = CommonConstant.RDF_DATE;
					pos = format.indexOf("[");
					if (pos >= 0) {
						int pos2 = format.indexOf("]");
						dFormat = format.substring(pos+1, pos2);
					}
					
					format = format.replace("["+dFormat+"]","");
					int rpos = format.indexOf(CommonConstant.REPS_PREFIX);
					int rpos2 = format.indexOf(CommonConstant.REPS_SUFFIX);
					String fieldName = format.substring(rpos+CommonConstant.REPS_PREFIX.length(), rpos2);
					
					SimpleDateFormat df = new SimpleDateFormat(dFormat,Locale.US);
					folderMap.put("name", df.format(map.get(fieldName)));
				}	
				else {
					pos = format.indexOf("[");
					if (pos >= 0) {
						int pos2 = format.indexOf("]");
						
						String descFieldName = format.substring(pos+1, pos2);
						w = new StringWriter();
						pc.processString("${"+descFieldName+"}", map, w);
						folderMap.put("desc", w.toString());
						w.close();
						
						format = format.replace("["+descFieldName+"]","");
					}
					
					w = new StringWriter();
					pc.processString(format, map, w);
					folderMap.put("name", FolderUtil.getValidFolderName(w.toString()));
					w.close();
				}
			} // for
			
			
			MainMasterModel siteModel = masterService.getSystemConfig(MainMasterConstant.SCC_PCM_ORD_SITE_ID);
			String siteId = siteModel.getFlag1();
			
			log.info("site : "+siteId);
			
			FolderUtil folderUtil = new FolderUtil();
			folderUtil.setSearchService(searchService);
			folderUtil.setFileFolderService(fileFolderService);
			folderUtil.setNodeService(nodeService);
			
			NodeRef folderRef = folderUtil.createFolderStructure(paths, siteId);
			
			if (!folderRef.toString().equals(model.getFolderRef())) {
				model.setFolderRef(folderRef.toString());
			}
		}
	}
	
	private void setUserGroupFields(PcmOrdModel model) throws Exception {
		/*
		 * Requester Group
		 */
//		MainApprovalMatrixModel apModel = adminApprovalMatrixService.get(model.getApprovalMatrixId());
//		model.setRequesterUser(apModel.getRequesterUser());
//		model.setRequesterGroup(apModel.getRequesterGroup());
	}
	
	public List<Map<String, Object>> list(Map<String, Object> params) {
		
		List<Map<String, Object>> list = null;
		
		SqlSession session = PcmUtil.openSession(dataSource);
        try {
            PcmOrdDAO dao = session.getMapper(PcmOrdDAO.class);
            log.info("pcm req list param:"+params);
    		list = dao.list(params);
    		
    		String lang = ((String)params.get("lang")).toUpperCase();
    		
    		for(Map<String, Object> map : list) {

				map.put(PcmOrdConstant.JFN_WF_STATUS, 
						map.get(PcmOrdConstant.TFN_WF_BY+lang)
						+"-"+
						map.get(PcmOrdConstant.TFN_WF_STATUS+lang)
						+"-"+
						CommonDateTimeUtil.convertToGridDateTime((Timestamp)map.get(PcmOrdConstant.TFN_WF_BY_TIME))
				);
				map.put(PcmOrdConstant.JFN_CREATED_BY,map.get(PcmOrdConstant.TFN_CREATED_BY+lang));
				map.put(PcmOrdConstant.JFN_CREATED_TIME_SHOW, CommonDateTimeUtil.convertToGridDateTime((Timestamp)map.get(PcmOrdConstant.TFN_CREATED_TIME)));
				map.put(PcmOrdConstant.JFN_ORG_NAME,map.get(PcmOrdConstant.TFN_ORG_NAME+lang));
				
				map.put(PcmOrdConstant.JFN_ACTION, PcmOrdUtil.getAction(map));
				
				map = CommonUtil.removeThElement(map);
			}
            
        } catch (Exception ex) {
			log.error("", ex);
        	throw ex;
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	public void delete(String id) throws Exception {
		
		SqlSession session = PcmUtil.openSession(dataSource);
        try {
            PcmOrdDAO pcmOrdDAO = session.getMapper(PcmOrdDAO.class);
            MainWorkflowDAO workflowDAO = session.getMapper(MainWorkflowDAO.class);
            MainWorkflowHistoryDAO workflowHistoryDAO = session.getMapper(MainWorkflowHistoryDAO.class);
            MainWorkflowReviewerDAO workflowReviewerDAO = session.getMapper(MainWorkflowReviewerDAO.class);
            MainWorkflowNextActorDAO workflowNextActorDAO = session.getMapper(MainWorkflowNextActorDAO.class);

    		PcmOrdModel model = get(id, null);
    		boolean exists = (model.getFolderRef()!=null) && fileFolderService.exists(new NodeRef(model.getFolderRef()));    		
    		if(exists) {
    			alfrescoService.deleteFileFolder(model.getFolderRef());
        	}

    		workflowNextActorDAO.deleteByMasterId(id);
    		workflowHistoryDAO.deleteByMasterId(id);
    		workflowReviewerDAO.deleteByMasterId(id);
    		workflowDAO.deleteByMasterId(id);
    		pcmOrdDAO.delete(id);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
	}
	
	public void deleteReviewerByMasterId(String id) throws Exception {
		SqlSession session = PcmUtil.openSession(dataSource);
        try {
            MainWorkflowReviewerDAO pcmReqReviewerDAO = session.getMapper(MainWorkflowReviewerDAO.class);
            pcmReqReviewerDAO.deleteByMasterId(id);
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
	}
	
	public PcmOrdModel get(String id, String lang) {
		
		PcmOrdModel model = null;
		
		SqlSession session = PcmUtil.openSession(dataSource);
        try {
            PcmOrdDAO pcmOrdDAO = session.getMapper(PcmOrdDAO.class);
            
    		model = pcmOrdDAO.get(id);
    		model.setTotalRowCount(1l);
        } catch (Exception ex) {
			log.error("", ex);
        } finally {
        	session.close();
        }
        
        return model;
	}
	
	public void update(PcmOrdModel model) throws Exception {
		
        SqlSession session = PcmUtil.openSession(dataSource);
        
        try {
            PcmOrdDAO pcmReqDAO = session.getMapper(PcmOrdDAO.class);
            
        	/*
        	 * Update DB
        	 */
            model.setUpdatedTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            
        	pcmReqDAO.update(model);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        	throw ex;
        } finally {
        	session.close();
        }

	}
	
	public void addReviewer(MainWorkflowReviewerModel pcmReqReviewerModel) throws Exception {
		
		SqlSession session = PcmUtil.openSession(dataSource);
        try {
           
            MainWorkflowReviewerDAO pcmReqReviewerDAO = session.getMapper(MainWorkflowReviewerDAO.class);

    		pcmReqReviewerDAO.add(pcmReqReviewerModel);

            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
	}
	
	public MainWorkflowReviewerModel getReviewer(MainWorkflowReviewerModel pcmReqReviewerModel) throws Exception {
		
		MainWorkflowReviewerModel model = null;
		
		SqlSession session = PcmUtil.openSession(dataSource);
        try {
           
            MainWorkflowReviewerDAO pcmReqReviewerDAO = session.getMapper(MainWorkflowReviewerDAO.class);

    		List<MainWorkflowReviewerModel> list = pcmReqReviewerDAO.listByLevel(pcmReqReviewerModel);
    		if (list.size()>0) {
    			model = list.get(0);
    		}
        } catch (Exception ex) {
			log.error("", ex);
        } finally {
        	session.close();
        }
        
        return model;
	}	
	
	public List<Map<String, Object>> listWorkflowPath(String id, String lang) {
		
		List<Map<String, Object>> list = mainWorkflowService.listWorkflowPath(id, lang);
		
		/*
		 * Add Requester
		 */
		PcmOrdModel model = get(id, null);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("LEVEL", mainWorkflowService.getTaskCaption(MainWorkflowConstant.TN_SUPERVISOR, lang, null));
		map.put("U", model.getAppBy());
		map.put("G", "");
		map.put("IRA", false);
		map.put("C", "0");
		
		list.add(0, map);
		
		map = new HashMap<String, Object>();
		map.put("LEVEL", mainWorkflowService.getTaskCaption(MainWorkflowConstant.TN_PREPARER, lang, null));
		map.put("U", model.getCreatedBy());
		map.put("G", "");
		map.put("IRA", false);
		map.put("C", "0");
		
		list.add(0, map);
		
		/*
		 * Add Next Actor
		 */
		List<Map<String, Object>> actorList = null;
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            
            MainWorkflowNextActorDAO dao = session.getMapper(MainWorkflowNextActorDAO.class);

    		actorList = dao.listWorkflowPath(id);
    		
    		list.addAll(actorList);
            
        } catch (Exception ex) {
			log.error("", ex);
        } finally {
        	session.close();
        }
		
        /*
         * Convert Employeee Code to Name
         */
        lang = (lang!=null && lang.startsWith("th") ? "_th" : "");

		for(Map<String, Object> rec:list) {
			String empCode = (String)rec.get("U");
			Map<String, Object> empModel = adminHrEmployeeService.getWithDtl(empCode);
			if (empModel!=null) {
				rec.put("U", empCode + " - " + empModel.get("first_name"));
			}
		}
		
		return list;
	}

	@Override
	public void update(SubModuleModel subModuleModel) throws Exception {
		PcmOrdModel model = (PcmOrdModel)subModuleModel;
		
        SqlSession session = PcmUtil.openSession(dataSource);
        
        try {
            PcmOrdDAO dao = session.getMapper(PcmOrdDAO.class);
            
        	/*
        	 * Update DB
        	 */
            model.setUpdatedTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            
        	dao.update(model);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        	throw ex;
        } finally {
        	session.close();
        }		
	}

	@Override
	public String getWorkflowName() throws Exception {
		return PcmOrdConstant.WF_NAME;
	}

	@Override
	public String getWorkflowDescription(SubModuleModel paramModel)
			throws Exception {
		PcmOrdModel pcmOrdModel = (PcmOrdModel)paramModel;
		
		MainMasterModel descFormatModel = masterService.getSystemConfig(MainMasterConstant.SCC_PCM_ORD_WF_DESC_FORMAT);
		String descFormat = descFormatModel.getFlag1();
		
		JSONObject map = PcmOrdUtil.convertToJSONObject(pcmOrdModel);
		
		TemplateProcessor pc = templateService.getTemplateProcessor("freemarker");
		Writer w = new StringWriter();
		pc.processString(descFormat, map, w);
		
		return w.toString();
	}

	@Override
	public Map<String, Object> convertToMap(SubModuleModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSubModuleType() {
		return CommonConstant.SUB_MODULE_PCM_ORD;	
	}

	@Override
	public void setWorkflowParameters(Map<QName, Serializable> parameters, SubModuleModel paramModel, List<NodeRef> docList, List<NodeRef> attachDocList) {
		PcmOrdModel model = (PcmOrdModel)paramModel;
		
		/*
		 * Common Attribute
		 */
        parameters.put(PcmOrdWorkflowConstant.PROP_ID, model.getId());
        parameters.put(PcmOrdWorkflowConstant.PROP_FOLDER_REF, model.getFolderRef());

        parameters.put(PcmOrdWorkflowConstant.PROP_DOCUMENT, (Serializable)docList);
        parameters.put(PcmOrdWorkflowConstant.PROP_ATTACH_DOCUMENT, (Serializable)attachDocList);
//        parameters.put(PcmOrdWorkflowConstant.PROP_COMMENT_HISTORY, "");
        
        parameters.put(PcmOrdWorkflowConstant.PROP_TASK_HISTORY, "");	
        
        /*
         * Special Attribute
         */
		parameters.put(PcmOrdWorkflowConstant.PROP_OBJECTIVE, model.getObjective());
		parameters.put(PcmOrdWorkflowConstant.PROP_TOTAL, model.getTotal());
		
		Map<String, Object> docType = adminWkfConfigService.getDocType(model.getDocType());
		String desc = (String)docType.get(MainWkfConfigDocTypeConstant.TFN_DESCRIPTION);
		int pos = desc.indexOf("-");
		if (pos>=0) {
			desc = desc.substring(pos+1);
		}
		parameters.put(PcmOrdWorkflowConstant.PROP_METHOD, desc);
		
		
//		MainHrEmployeeModel empModel = adminHrEmployeeService.get(model.getAppBy());
//		parameters.put(PcmOrdWorkflowConstant.PROP_REQUESTER, empModel.getFirstName()+" "+empModel.getLastName());
		parameters.put(PcmOrdWorkflowConstant.PROP_REQUESTER, model.getAppBy());
	}

	@Override
	public String getActionCaption(String action, String lang) {
		StringBuffer sb = new StringBuffer();
		if (lang!=null && lang.indexOf("th")>=0) {
			sb.append(PcmOrdConstant.WF_TASK_ACTIONS_TH.get(action));
		} else {
			sb.append(PcmOrdConstant.WF_TASK_ACTIONS.get(action));
		}
		
		return sb.toString();
	}

	@Override
	public List<MainWorkflowNextActorModel> listNextActor(SubModuleModel model) {
		List<MainWorkflowNextActorModel> list = new ArrayList<MainWorkflowNextActorModel>();
		
//		PcmOrdModel realModel = (PcmOrdModel)model;
//		
//		List<Map<String, Object>> superList = adminWkfConfigService.listSupervisor(realModel.getSectionId());
//		if(superList.size()>0) {
//			Map<String, Object> map = superList.get(0);
//			
//			MainWorkflowNextActorModel actorModel = new MainWorkflowNextActorModel();
//			
//			actorModel.setMasterId(model.getId());
//			actorModel.setLevel(1);
//			actorModel.setActor(PcmReqConstant.NA_BOSS);
//			actorModel.setActorUser(MainUserGroupUtil.code2login((String)map.get(MainHrEmployeeConstant.TFN_EMPLOYEE_CODE)));
//			actorModel.setCreatedBy(model.getUpdatedBy());
//			
//			list.add(actorModel);
//		}
		
		return list;
	}

	@Override
	public String getFirstComment(SubModuleModel model) {
		PcmOrdModel realModel = (PcmOrdModel)model;
		
		return realModel.getObjective();
	}

	@Override
	public String getNextActionInfo(SubModuleModel model, String lang) {
		return mainWorkflowService.getTaskCaption(MainWorkflowConstant.TN_PROCUREMENT,lang,null);
	}

	@Override
	public QName getPropNextReviewers() {
		return PcmOrdWorkflowConstant.PROP_NEXT_REVIEWERS;
	}
	
	@Override
	public String getModelUri() {
		return PcmOrdWorkflowConstant.MODEL_URI;
	}

	@Override
	public String getWfUri() {
		return PcmOrdWorkflowConstant.WF_URI;
	}

	@Override
	public String getModelPrefix() {
		return PcmOrdWorkflowConstant.MODEL_PREFIX;
	}

	@Override
	public Map<String, String> getBossMap(String docType, SubModuleModel subModuleModel) throws Exception {
		
		PcmOrdModel model = (PcmOrdModel)subModuleModel;
		
		/*
		 * Search Original Boss List
		 */
		Map<String, String> tmpMap = new LinkedHashMap<String, String>();
		
		log.info("getBossMap()");
		log.info("  docType:'"+docType+"'");
		log.info("  sectionId:'"+model.getSectionId()+"'");
		log.info("  reqUser:"+model.getCreatedBy());
		log.info("  total:"+model.getTotal());
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
        	MainBossDAO dao = session.getMapper(MainBossDAO.class);
        	
        	Map<String, Object> params = new HashMap<String, Object>();
        	params.put("docType", docType);
       		params.put("sectionId", model.getSectionId());
        	
        	List<Map<String, Object>> bossList = dao.list(params);
        	log.info("  bossList"+bossList);
        	if (bossList==null || bossList.size()==0) {
        		throw new NotFoundApprovalMatrixException();
        	}
        	
        	tmpMap = getUnitBossMap(model, bossList, tmpMap);

            log.info("  tmpMap.size:"+tmpMap.size());                       
        } catch (Exception ex) {
        	log.error(ex);
        	throw ex;
        } finally {
        	session.close();
        }
		
		return tmpMap;
	}
	
	public Map<String, String> getUnitBossMap(PcmOrdModel model, List<Map<String, Object>> bossList,Map<String, String> tmpMap ) throws Exception {
		
		String reqByCode = MainUserGroupUtil.login2code(model.getCreatedBy());
    	
    	Map<String, Object> reservedBoss = null;
    	Map<String, Object> lastBossMap = null;
    	
    	/*
    	 * Find Boss by level, if reqByCode exist in workflow path
    	 */
    	int start = 0;
    	int i = 0;
    	
    	for(Map<String, Object> boss : bossList) {
    		
    		log.info("  "+boss.get("lvl")+" "+boss.get("employee_code"));
    		
    		if (boss.get("employee_code").equals(model.getCreatedBy())) {
    			log.info(model.getCreatedBy()+" is in path");
    			start = i;
    			break;
    		}
    		i++;
    	}    	
    	
    	/*
    	 * Find Boss by money, if reqByCode not exist in workflow path
    	 */
    	Double total = model.getTotal();
    	
    	i = 0;
    	log.info("  start:"+start);
    	for(Map<String, Object> boss : bossList) {
    		
    		log.info("  "+boss.get("lvl")+" "+boss.get("employee_code")+" (amt:"+boss.get("amount_min")+"-"+boss.get("amount_max")+")");
    		
    		if (i>=start) {
    			if (tmpMap.size()==0 || total > (Double)lastBossMap.get("amount_max")) {
    				tmpMap.put((String)boss.get("lvl"), (String)boss.get("employee_code"));
    				lastBossMap = boss;
    			}
    			else
    			if (reservedBoss==null) {
    				reservedBoss = boss;
    				break;
    			}
    		}
    		i++;
    	}
    	
    	log.info("lastBoss:"+lastBossMap.get("employee_code")+", reqBy:"+model.getCreatedBy());
    	/*
    	 * Replace Last Boss if he is Requester
    	 */
    	if (lastBossMap.get("employee_code").equals(model.getCreatedBy())) {
    		if (reservedBoss!=null) {
        		tmpMap.remove(lastBossMap.get("lvl"));
    			tmpMap.put((String)reservedBoss.get("lvl"), (String)reservedBoss.get("employee_code"));
    		}
    		else {
    			// Waiting SA
    			
    		}
    	}
    	
    	
    	/*
    	 * Remove Requester from first position
    	 */
    	if (tmpMap.size()>1) {
    		i = 0;
    		for(Entry<String, String> e : tmpMap.entrySet()) {
    			if(i==0) {
    				if (e.getValue().equals(model.getCreatedBy())) {
    					tmpMap.remove(e.getKey());
    					break;
    				}
    			}
    			i++;
    		}
    	}
    	
    	
//    	/*
//    	 * Remove Requester from Boss List
//    	 */
//    	List<String> rmList = new ArrayList<String>();
//    	int i = 1;
//    	int size = tmpMap.size();
//    	for(Entry<String, String> e : tmpMap.entrySet()) {
//    		log.info("  e.key:"+e.getKey()+", value:"+e.getValue());
//    		
//    		if (i==size) {
//    			break;
//    		}
//    		
//    		if (e.getValue().equals(reqByCode)) {
//    			rmList.add(e.getKey());
//    		}
//    		
//    		i++;
//    	}
//
//    	for(String k : rmList) {
//    		tmpMap.remove(k);
//    	}
		
		 /*
         * Remove duplicated Boss
         */
		Map<String, String> map = removeDuplicatedBoss(tmpMap);
		
		return map;
	}
	
	private Map<String, String> removeDuplicatedBoss(Map<String, String> tmpMap) {
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		Entry<String, String> prevEntry = null;
		for(Entry<String, String> e : tmpMap.entrySet()) {
			
			if (prevEntry==null || !e.getValue().equals(prevEntry.getValue())) {
				map.put(e.getKey(), e.getValue());
			}
			
			prevEntry = e;
		}
		
//		if (prevEntry != null) {
//			map.put((String)prevEntry.getKey(), (String)prevEntry.getValue());
//		}
//		
        log.info("  map="+map);

        return map;
	}
	


	@Override
	public List<String> listRelatedUser(SubModuleModel model) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getDocDesc() {
		return "ใบ พด.";
	}
	
	@Override
	public MainWorkflowHistoryModel getReqByWorkflowHistory(SubModuleModel subModuleModel) {
		return null;
	}
	
	@Override
	public MainWorkflowHistoryModel getAppByWorkflowHistory(SubModuleModel subModuleModel) {
		MainWorkflowHistoryModel hModel = new MainWorkflowHistoryModel();
		
		PcmOrdModel model = (PcmOrdModel)subModuleModel;
		
		hModel.setTime(model.getCreatedTime());
		hModel.setLevel(0);
		hModel.setComment("");
		hModel.setAction(getActionCaption(MainWorkflowConstant.TA_APPROVE,""));
		hModel.setActionTh(getActionCaption(MainWorkflowConstant.TA_APPROVE,"th"));
		hModel.setTask(MainWorkflowConstant.WF_TASK_NAMES.get(MainWorkflowConstant.TN_SUPERVISOR));
		hModel.setTaskTh(MainWorkflowConstant.WF_TASK_NAMES_TH.get(MainWorkflowConstant.TN_SUPERVISOR));
		hModel.setBy(model.getAppBy());
		
		return hModel;
	}	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void continueRequesterTask(final String exeId, String action, PcmOrdModel model, String comment, String docType) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put(PcmOrdWorkflowConstant.MODEL_PREFIX+"reSubmitOutcome", action);
		map.put(PcmOrdWorkflowConstant.MODEL_PREFIX+"workflowStatus", action);
		map.put("reqBy", model.getUpdatedBy());
		map.put("comment", comment);
		map.put("docType", docType);
		
        List<NodeRef> docList = new ArrayList<NodeRef>();
        docList.add(new NodeRef(model.getDocRef()));
		map.put(PcmOrdWorkflowConstant.MODEL_PREFIX+"document", docList);
		
        List<NodeRef> attachDocList = new ArrayList<NodeRef>();
        if(model.getListAttachDoc() !=null) {
        	for(String nodeRef : model.getListAttachDoc()) {
        		attachDocList.add(new NodeRef(nodeRef));
            }
        }
		map.put(PcmOrdWorkflowConstant.MODEL_PREFIX+"attachDocument", attachDocList);

		runtimeService.signal(exeId, map);
	}

	@Override
	public List<String> getSpecialUserForAddPermission(SubModuleModel model) {
		List<String> list = new ArrayList();
		
		PcmOrdModel pcmOrdModel = (PcmOrdModel)model;
		
		list.add(pcmOrdModel.getAppBy());
		
		return list;
	}
	
	@Override
	public String getFirstStatus() {
		return PcmOrdConstant.ST_WAITING;
	}

	@Override
	public Boolean addPermissionToAttached() {
		return true;
	}

	@Override
	public List<String> getSpecialGroupForAddPermission() {
		MainMasterModel topGroup = masterService.getSystemConfig(MainMasterConstant.SCC_PCM_ORD_TOP_GROUP);
		
		String[] tgs = topGroup!=null && topGroup.getFlag1()!=null ? topGroup.getFlag1().split(",") : null;
		
		List<String> list = Arrays.asList(tgs);
		
		return list;
	}

	@Override
	public String getWorkflowDescriptionEn(SubModuleModel paramModel)
			throws Exception {
		PcmOrdModel model = (PcmOrdModel)paramModel;
		
		PcmOrdModel enModel = new PcmOrdModel();
		enModel.setId(model.getId());
		enModel.setObjective(model.getObjective());
		enModel.setStatus("");
		
		return getWorkflowDescription(enModel);
	}

	@Override
	public QName getPropDescEn() {
		return PcmOrdWorkflowConstant.PROP_DESCRIPTION;
	}

	@Override
	public void setFirstTaskAssignee(Map<QName, Serializable> parameters,
			SubModuleModel model) {
	}
}
