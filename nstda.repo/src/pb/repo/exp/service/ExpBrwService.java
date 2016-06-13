package pb.repo.exp.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.coci.CheckOutCheckInService;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentReader;
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
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.surf.util.I18NUtil;
import org.springframework.stereotype.Service;

import pb.common.constant.CommonConstant;
import pb.common.constant.FileConstant;
import pb.common.model.FileModel;
import pb.common.util.CommonDateTimeUtil;
import pb.common.util.DocUtil;
import pb.common.util.FolderUtil;
import pb.common.util.ImageUtil;
import pb.common.util.PersonUtil;
import pb.repo.admin.constant.MainHrEmployeeConstant;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.constant.MainWkfConfigDocTypeConstant;
import pb.repo.admin.constant.MainWorkflowConstant;
import pb.repo.admin.dao.MainWkfCmdBossLevelApprovalDAO;
import pb.repo.admin.dao.MainWorkflowDAO;
import pb.repo.admin.dao.MainWorkflowHistoryDAO;
import pb.repo.admin.dao.MainWorkflowNextActorDAO;
import pb.repo.admin.dao.MainWorkflowReviewerDAO;
import pb.repo.admin.model.MainHrEmployeeModel;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.model.MainWorkflowHistoryModel;
import pb.repo.admin.model.MainWorkflowNextActorModel;
import pb.repo.admin.model.SubModuleModel;
import pb.repo.admin.service.AdminAccountTaxService;
import pb.repo.admin.service.AdminCostControlService;
import pb.repo.admin.service.AdminHrEmployeeService;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminProjectService;
import pb.repo.admin.service.AdminSectionService;
import pb.repo.admin.service.AdminUserGroupService;
import pb.repo.admin.service.AdminWkfConfigService;
import pb.repo.admin.service.AlfrescoService;
import pb.repo.admin.service.MainSrcUrlService;
import pb.repo.admin.service.MainWorkflowService;
import pb.repo.admin.service.SubModuleService;
import pb.repo.admin.util.MainUserGroupUtil;
import pb.repo.common.mybatis.DbConnectionFactory;
import pb.repo.exp.constant.ExpBrwConstant;
import pb.repo.exp.constant.ExpBrwVoyagerConstant;
import pb.repo.exp.constant.ExpBrwWorkflowConstant;
import pb.repo.exp.dao.ExpBrwDAO;
import pb.repo.exp.dao.ExpBrwVoyagerDAO;
import pb.repo.exp.model.ExpBrwVoyagerModel;
import pb.repo.exp.model.ExpBrwModel;
import pb.repo.exp.util.ExpBrwVoyagerUtil;
import pb.repo.exp.util.ExpBrwUtil;
import pb.repo.exp.util.ExpUtil;

@Service
public class ExpBrwService implements SubModuleService {

	private static Logger log = Logger.getLogger(ExpBrwService.class);

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
	MainWorkflowService mainWorkflowService;
	
	@Autowired
	AdminHrEmployeeService adminHrEmployeeService;
	
	@Autowired
	AdminWkfConfigService adminWkfConfigService;
	
	@Autowired
	AdminSectionService adminSectionService;
	
	@Autowired
	AdminProjectService adminProjectService;
	
	@Autowired
	AdminCostControlService adminCostControlService;
	
	@Autowired
	AdminAccountTaxService adminAccountTaxService;
	
	@Autowired
	AlfrescoService alfrescoTaxService;
	
	public ExpBrwModel save(ExpBrwModel model, String items, String files, boolean genDoc) throws Exception {
		
        SqlSession session = ExpUtil.openSession(dataSource);
        
        try {
            ExpBrwDAO expBrwDAO = session.getMapper(ExpBrwDAO.class);
            ExpBrwVoyagerDAO expBrwDtlDAO = session.getMapper(ExpBrwVoyagerDAO.class);
            
            setUserGroupFields(model);
            
    		model.setUpdatedBy(authService.getCurrentUserName());
    		model = lookupOtherFields(model);
    		
			model.setVoyagerList(ExpBrwVoyagerUtil.convertJsonToList(items, model.getId()));
    		
            if (model.getId() == null) {
        		model.setCreatedBy(model.getUpdatedBy());
        		
    			/*
    			 * Gen New ID
    			 */
        		MainMasterModel masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_EXP_BRW_ID_FORMAT);
        		String idFormat = masterModel.getFlag1();

            	Long id = expBrwDAO.getNewRunningNo();
            	
            	String newId = genNewId(model, idFormat, id); // PR16000001
            	
        		model.setId(newId);
        		log.info("new id:"+newId);
            	doCommonSaveProcess(model, genDoc, items, files);
            	
        		/*
        		 * Add DB
        		 */
            	expBrwDAO.add(model);
            }
            else {
            	doCommonSaveProcess(model, genDoc, items, files);
            	
            	/*
            	 * Update DB
            	 */
            	expBrwDAO.update(model);
            	
            	expBrwDtlDAO.deleteByMasterId(model.getId());
            }
            
            List<ExpBrwVoyagerModel> dtlList = model.getVoyagerList();
            for(ExpBrwVoyagerModel dtlModel : dtlList) {
            	dtlModel.setMasterId(model.getId());
	        	dtlModel.setCreatedBy(model.getUpdatedBy());
	        	dtlModel.setUpdatedBy(model.getUpdatedBy());
	        	
            	expBrwDtlDAO.add(dtlModel);
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
	
	private ExpBrwModel lookupOtherFields(ExpBrwModel model) {
		List<Map<String, Object>> list = adminWkfConfigService.listPurchasingUnit(model.getBudgetCc());
		if (list!=null && list.size()>0) {
			Map<String, Object> map = list.get(0);
			
		}
		
		return model;
	}
	
	public String save(ExpBrwModel model) throws Exception {
		
        SqlSession session = ExpUtil.openSession(dataSource);
        
        try {
            ExpBrwDAO expBrwDAO = session.getMapper(ExpBrwDAO.class);
            
    		model.setUpdatedBy(authService.getCurrentUserName());
    		
    		NodeRef folderNodeRef = new NodeRef(model.getFolderRef());
    		model = genDoc(model, folderNodeRef);
        	
        	/*
        	 * Update DB
        	 */
        	expBrwDAO.update(model);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        	throw ex;
        } finally {
        	session.close();
        }

        return model.getId();
	}
	
	private String genNewId(ExpBrwModel expBrwModel, String oriFormat, Long runningNo) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();

		int newFieldIndex = 0;
		
		boolean done = oriFormat.indexOf(ExpBrwConstant.JFN_CREATED_TIME)<0
					&& oriFormat.indexOf(ExpBrwConstant.JFN_UPDATED_TIME)<0
					&& oriFormat.indexOf(ExpBrwConstant.JFN_RUNNING_NO)<0
					&& oriFormat.indexOf(ExpBrwConstant.JFN_FISCAL_YEAR)<0
					;
		while (!done) {
    		if (oriFormat.indexOf(ExpBrwConstant.JFN_CREATED_TIME) >= 0) {
	    		Timestamp timestampValue = expBrwModel.getCreatedTime()!=null ? expBrwModel.getCreatedTime() : new Timestamp(Calendar.getInstance().getTimeInMillis());
	    		oriFormat = handleThaiDate("", ExpBrwConstant.JFN_CREATED_TIME, model, oriFormat, newFieldIndex, timestampValue, templateService, false);
				model.put(ExpBrwConstant.JFN_CREATED_TIME,  getDateDefaultFormatValue(timestampValue));
    		}
    		else
    		if (oriFormat.indexOf(ExpBrwConstant.JFN_UPDATED_TIME) >= 0) {
	    		Timestamp timestampValue = expBrwModel.getUpdatedTime()!=null ? expBrwModel.getUpdatedTime() : new Timestamp(Calendar.getInstance().getTimeInMillis());
	    		oriFormat = handleThaiDate("", ExpBrwConstant.JFN_UPDATED_TIME, model, oriFormat, newFieldIndex, timestampValue, templateService, false);
				model.put(ExpBrwConstant.JFN_UPDATED_TIME,  getDateDefaultFormatValue(timestampValue));
    		}
    		else
    		if (oriFormat.indexOf(ExpBrwConstant.JFN_RUNNING_NO) >= 0) {
				oriFormat = handleRunningNo(ExpBrwConstant.JFN_RUNNING_NO, model, oriFormat, newFieldIndex, runningNo);
				model.put(ExpBrwConstant.JFN_RUNNING_NO,  runningNo);
    		}
    		else
    		if (oriFormat.indexOf(ExpBrwConstant.JFN_FISCAL_YEAR) >= 0) {
    			Calendar cal = Calendar.getInstance();
	    		if (cal.get(Calendar.MONTH) >= 9) { // >= October (Thai Start Budget Year)
	    			cal.add(Calendar.YEAR, 1);
	    		}
	    		Timestamp timestampValue = new Timestamp(cal.getTimeInMillis());
	    		oriFormat = handleThaiDate("", ExpBrwConstant.JFN_FISCAL_YEAR, model, oriFormat, newFieldIndex, timestampValue, templateService, false);
				model.put(ExpBrwConstant.JFN_FISCAL_YEAR,  getDateDefaultFormatValue(timestampValue));
    		}
    		
			newFieldIndex++;
			done = oriFormat.indexOf(ExpBrwConstant.JFN_CREATED_TIME)<0
				&& oriFormat.indexOf(ExpBrwConstant.JFN_UPDATED_TIME)<0
				&& oriFormat.indexOf(ExpBrwConstant.JFN_RUNNING_NO)<0
				&& oriFormat.indexOf(ExpBrwConstant.JFN_FISCAL_YEAR)<0
				|| newFieldIndex>10
				;
			
//			log.info("oriFormat:"+oriFormat);
		}			
		
		Writer w = new StringWriter();
		
		if (oriFormat!=null && !oriFormat.trim().equals("")) {
			TemplateProcessor pc = templateService.getTemplateProcessor("freemarker");
			pc.processString(oriFormat, model, w);
		}
		
		return w.toString();
	}
	
	public void updateStatus(ExpBrwModel model) throws Exception {
		
        SqlSession session = ExpUtil.openSession(dataSource);
        
        try {
            ExpBrwDAO dao = session.getMapper(ExpBrwDAO.class);
          
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
	
	public JSONObject validateAssignee(ExpBrwModel model) throws Exception {
		
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
	
	public JSONObject validateWfPath(ExpBrwModel model) throws Exception {
		
		JSONObject result = new JSONObject();
		
        try {
        	
        	Map<String, String> bossMap = getBossMap(MainWkfConfigDocTypeConstant.DT_PR, model);

        	if (bossMap==null || bossMap.size()==0) {
				result.put("valid", false);
				
				String msg = ExpBrwUtil.getMessage("ERR_NOT_FOUND_WORKFLOW_PATH", I18NUtil.getLocale());
				result.put("msg",  msg.split(",")[1]);
        	} else {
        		result.put("valid", true);
        	}
        	
        } catch (Exception ex) {
        	result.put("valid", false);
			String msg = ExpBrwUtil.getMessage("ERR_NOT_FOUND_WORKFLOW_PATH", I18NUtil.getLocale());
			result.put("msg",  msg.split(",")[1]);
			log.error("", ex);
        }

        return result;
	}	
	
	private ExpBrwModel doCommonSaveProcess(ExpBrwModel model, boolean genDoc, String voyagers, String files) throws Exception {
		
		/*
		 * Find Pcm Section Id
		 */
		Integer pcmSectionId = null;
		
		Integer sectionId = null;
		
		if (model.getBudgetCcType().equals(ExpBrwConstant.BCCT_PROJECT)) {
			 List<Map<String, Object>> list = adminProjectService.listProjectManager(model.getBudgetCc());
			 for(Map<String, Object> map : list) {
				 sectionId = (Integer)map.get("section_id");
			 }
		} else {
			sectionId = model.getBudgetCc();
		}
		
		List<Map<String, Object>> pcmSectionList = adminWkfConfigService.listPurchasingUnit(sectionId);
		if (pcmSectionList != null) {
			for(Map<String, Object>  map : pcmSectionList) {
				pcmSectionId = (Integer)map.get("purchasing_unit_id");
			}
		}
		
    	/*
    	 * Create ECM Folder
    	 */
		createEcmFolder(model);
		
		NodeRef folderNodeRef = new NodeRef(model.getFolderRef());
		
		/*
		 * Gen Doc
		 */
		if (genDoc) {
			model.setVoyagerList(ExpBrwVoyagerUtil.convertJsonToList(voyagers, model.getId()));
			
			model = genDoc(model, folderNodeRef);
		}
    	
    	/*
    	 * save files
    	 */
		File file;
		String path;
		
		List<String> oldList = new ArrayList<String>();
		Map<String, JSONObject> newMap = new HashMap<String, JSONObject>();
		
		/*
		 * Separate Old and New Files
		 */
    	JSONArray jsArr = new JSONArray(files);
    	for(int i=0; i<jsArr.length(); i++) {
    		JSONObject jsObj = jsArr.getJSONObject(i);
    		log.info("  file:"+jsObj.getString(FileConstant.JFN_NAME)
					   			   +", "+jsObj.getString(FileConstant.JFN_DESC)
    							   +", "+jsObj.getString(FileConstant.JFN_PATH)
    							   +", "+jsObj.getString(FileConstant.JFN_NODE_REF));
    		
    		path = jsObj.getString(FileConstant.JFN_PATH);
    		if (path!=null && !path.equals("") && !path.equals("null")) {
    			newMap.put(jsObj.getString(FileConstant.JFN_NAME), jsObj);
    		} else {
    			oldList.add(jsObj.getString(FileConstant.JFN_NODE_REF));
    		}
    	}
    
    	/*
    	 * Delete Old Files
    	 */
    	Set<QName> qnames = new HashSet<QName>();
    	qnames.add(ContentModel.TYPE_CONTENT);
    	List<ChildAssociationRef> docs = nodeService.getChildAssocs(folderNodeRef, qnames);
    	for(ChildAssociationRef doc : docs) {
    		
    		log.info("doc:"+doc.toString());
    		log.info("   childRef:"+doc.getChildRef().toString());
    		if (!doc.getQName().getLocalName().equals(model.getId()+".pdf")) { // it is not main.pdf
	    		if (oldList.indexOf(doc.getChildRef().toString()) < 0) {
	    			alfrescoService.cancelCheckout(doc.getChildRef());
	    			alfrescoService.deleteFileFolder(doc.getChildRef().toString());
	        		log.info("   delete");
	    		}
	    		else {
	    			model.setAttachDoc(doc.getChildRef().toString());
	    		}
    		}
    	}
    	
    	/*
    	 * Create New Files
    	 */
    	for(Entry<String, JSONObject> e : newMap.entrySet()) {
    		log.info(e.getKey()+":"+e.getValue());
    		file = new File(FolderUtil.getTmpDir()+File.separator+e.getValue().getString(FileConstant.JFN_PATH)+File.separator+e.getKey());
	    	if (file.exists()) {
	    		NodeRef docRef = alfrescoService.createDoc(folderNodeRef, file, e.getKey(), e.getValue().getString(FileConstant.JFN_DESC));
	    		file.delete();
		    	model.setAttachDoc(docRef.toString());
	    	}
    	}
    	
    	return model;
	}
	
	public ExpBrwModel genDoc(ExpBrwModel model, NodeRef folderNodeRef) throws Exception {
		/*
		 * Gen Doc in tmp folder
		 */
    	String fileName =  doGenDoc("av", model);
		String tmpDir = FolderUtil.getTmpDir();
    	String fullName = tmpDir + File.separator + fileName+".pdf"; // real code
    	File file = new File(fullName);
    	String ecmFileName = model.getId()+".pdf";
    	
    	log.info("Gen Doc : "+ecmFileName);
    	/*
    	 * Put Main Doc in ECM
    	 */
    	NodeRef oldDocRef = alfrescoService.searchSimple(folderNodeRef, ecmFileName);
    	if (oldDocRef != null) {
    		alfrescoService.cancelCheckout(oldDocRef);
    		alfrescoService.deleteFileFolder(oldDocRef.toString());
    	}
    	NodeRef docRef = alfrescoService.createDoc(folderNodeRef, file, ecmFileName, getDocDesc());
    	
    	/*
    	 * Delete File from tmp folder 
    	 */
    	if (file.exists()) {
    		file.delete();
    	}
    	
    	/*
    	 * Update docRef field
    	 */
    	model.setDocRef(docRef.toString());
    	
    	return model;
	}
	
	public void updateDoc(ExpBrwModel model) throws Exception {
		/*
		 * Gen Doc in tmp folder
		 */
    	String fileName =  doGenDoc("av", model);
		String tmpDir = FolderUtil.getTmpDir();
    	String fullName = tmpDir + File.separator + fileName+".pdf"; 
    	File file = new File(fullName);
    	String ecmFileName = model.getId()+".pdf";
    	
    	log.info("Update Doc : "+ecmFileName);
    	/*
    	 * Put Main Doc in ECM
    	 */
    	NodeRef docRef = new NodeRef(model.getDocRef());
    	alfrescoService.cancelCheckout(docRef);
    	alfrescoService.updateDoc(docRef, file);
    	
    	/*
    	 * Delete File from tmp folder 
    	 */
//    	if (file.exists()) {
//    		file.delete();
//    	}
	}
	
	public String doGenDoc(String name, ExpBrwModel model) throws Exception {
		
		DecimalFormat df = new DecimalFormat(CommonConstant.MONEY_FORMAT);
		
		List<Map<String, Object>> empList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> othList = new ArrayList<Map<String, Object>>();
		Map<String, Object> voyagerMap = null;
		
        List<ExpBrwVoyagerModel> tmpDtlList = model.getVoyagerList();
        for(ExpBrwVoyagerModel tmpDtl : tmpDtlList) {
        	voyagerMap = new HashMap<String, Object>();
			
			voyagerMap.put("code", tmpDtl.getCode());
			voyagerMap.put("name", tmpDtl.getName());
			voyagerMap.put("utype", tmpDtl.getUnitType());
			voyagerMap.put("position", tmpDtl.getPosition());
			
			if (tmpDtl.getType().equals(ExpBrwVoyagerConstant.T_EMPLOYEE)) {
				empList.add(voyagerMap);
			} else {
				othList.add(voyagerMap);
			}
        }
        
		Collection<Map<String, ?>> empCol = new ArrayList<Map<String,?>>(empList);
		JRMapCollectionDataSource emp = new JRMapCollectionDataSource(empCol);
		
		Collection<Map<String, ?>> othCol = new ArrayList<Map<String,?>>(othList);
		JRMapCollectionDataSource oth = new JRMapCollectionDataSource(othCol);
		/*
		 * Parameters
		 */
		Map parameters = new HashMap();
		parameters.put("imgPath", CommonConstant.GP_REPORT_IMG_PATH);
		parameters.put("EmpDataSource", emp);
		parameters.put("OthDataSource", oth);
		
		parameters.put("total", df.format(model.getTotal()));
		
		/*
		 * Data
		 */
		List<Map<String, Object>> listData = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> map = new HashMap<>();
		map.put("id", model.getId() != null ? model.getId() : "");
		
		Map<String,Object> empDtl = adminHrEmployeeService.getWithDtl(model.getReqBy());		
		map.put("reqBy", empDtl.get("first_name")+" "+empDtl.get("last_name"));
		map.put("reqOrg", empDtl.get("org_desc"));
		map.put("reqSection", empDtl.get("section_name"));
		
		empDtl = adminHrEmployeeService.getWithDtl(model.getCreatedBy()!=null ? model.getCreatedBy() : authService.getCurrentUserName());
		map.put("createdBy", empDtl.get("first_name")+" "+empDtl.get("last_name"));
		map.put("createdTime", CommonDateTimeUtil.convertToGridDateTime(model.getCreatedTime()!=null?model.getCreatedTime():new Timestamp(Calendar.getInstance().getTimeInMillis())));
		map.put("createdPhone", StringUtils.defaultIfBlank((String)empDtl.get("work_phone"),""));
		
		Map<String, Object> firstAppMap = getFirstApprover(model.getId());
		log.info("model.id:"+model.getId());
		log.info("firstAppMap:"+firstAppMap);
		map.put("firstAppBy", firstAppMap != null ? firstAppMap.get("approver") : "");
		map.put("firstAppTime", firstAppMap != null ? CommonDateTimeUtil.convertToGridDateTime((Timestamp)firstAppMap.get("time")) : "");
		
		Map<String, Object> lastAppMap = getLastApprover(model.getId());
		log.info("lastAppMap:"+lastAppMap);
		map.put("lastAppBy", lastAppMap != null ? lastAppMap.get("approver") : "");
		map.put("lastAppTime", lastAppMap != null ? CommonDateTimeUtil.convertToGridDateTime((Timestamp)lastAppMap.get("time")) : "");
		
		map.put("objectiveType", model.getObjectiveType());
		map.put("objective", model.getObjective());
		map.put("reason", model.getReason());
		
		String budgetCcName = null;
		if (model.getBudgetCcType().equals("P")) {
			Map<String, Object> prjMap = adminProjectService.get(model.getBudgetCc());
			budgetCcName = (String)prjMap.get("description");
		} else {
			Map<String, Object> sectMap = adminSectionService.get(model.getBudgetCc());
			budgetCcName = (String)sectMap.get("name");
		}
		map.put("budgetCc", budgetCcName);

		Map<String, Object> ccMap = adminCostControlService.get(model.getCostControlId());
		String ccName = ccMap!=null ? (String)ccMap.get("name") : null;
		
		map.put("costControl", StringUtils.defaultIfBlank(ccName,""));
		
		map.put("totalType", "aa");
		map.put("total", "aa");
		
		map.put("bank", "aa");
		map.put("bank1", (model.getBankType().equals("0") ? "/radio_on.png" : "/radio_off.png"));
		map.put("bank2", (model.getBankType().equals("1") ? "/radio_on.png" : "/radio_off.png"));

		map.put("cc1", (model.getCostControlTypeId() == 0 ? "/radio_on.png" : "/radio_off.png"));
		map.put("cc2", (model.getCostControlTypeId() == 1 ? "/radio_on.png" : "/radio_off.png"));
		map.put("cc3", (model.getCostControlTypeId() == 2 ? "/radio_on.png" : "/radio_off.png"));
		
		map.put("costControlId", "aa");
		map.put("costControl", "aa");
		map.put("cc1From", "aa");
		map.put("cc1To", "aa");
		map.put("cc2From", "aa");
		map.put("cc2To", "aa");
		
		listData.add(map);

		Collection<Map<String, ?>> collection = new ArrayList<Map<String,?>>(listData);
		JRMapCollectionDataSource data = new JRMapCollectionDataSource(collection);
		  
		/*
		 * Gen PDF 
		 */
		String rptName = CommonConstant.GP_REPORT_PATH + "/" + CommonConstant.MODULE_EXP.toLowerCase() + "/" + name + ".jasper";
		InputStream is = getClass().getResourceAsStream(rptName);

		JasperReport jasperReport = (JasperReport)JRLoader.loadObject(is);
		JasperPrint jasperPrint = JasperFillManager.fillReport(
															jasperReport, 
															parameters, 
															data
													);
			
		DocUtil docUtil = new DocUtil();
		String tmpDir = FolderUtil.getTmpDir();
		String fileName = docUtil.genUniqueFileName();
		String fullName = tmpDir + File.separator + fileName + ".pdf";
			
		log.info("fullName="+fullName);			
			
		JasperExportManager.exportReportToPdfFile(jasperPrint, fullName);
			
		return fileName;
	}
	
	public List<FileModel> listFile(String id,final Boolean allFile) throws Exception {

		final ExpBrwModel model = get(id);
		log.info("list file : id:"+model.getId());
		
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
		    		
		    		if (!doc.getQName().getLocalName().equals(model.getId()+".pdf") || allFile) {
		    			FileModel fileModel = new FileModel();
		    			fileModel.setName(doc.getQName().getLocalName());
		    			String desc = (String)nodeService.getProperty(doc.getChildRef(), ContentModel.PROP_DESCRIPTION);
		    			fileModel.setDesc(desc);
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
		
		List<MainMasterModel> criList = masterService.listSystemConfig(MainMasterConstant.SCC_EXP_BRW_CRITERIA);

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
		
		List<MainMasterModel> list = masterService.listSystemConfig(MainMasterConstant.SCC_EXP_BRW_GRID_FIELD);

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
	
	private void createEcmFolder(ExpBrwModel model) throws Exception {
		
		boolean exists = (model.getFolderRef()!=null) && fileFolderService.exists(new NodeRef(model.getFolderRef()));
		
		if (!exists) {
			JSONObject map = ExpBrwUtil.convertToJSONObject(model,false);
			Calendar cal = Calendar.getInstance();
    		if (cal.get(Calendar.MONTH) >= 9) { // >= October (Thai Start Budget Year)
    			cal.add(Calendar.YEAR, 1);
    		}
    		Timestamp timestampValue = new Timestamp(cal.getTimeInMillis());
    		map.put(ExpBrwConstant.JFN_FISCAL_YEAR, timestampValue);
			
			
			Iterator it = map.keys();
			while(it.hasNext()) {
				Object obj = it.next();
				log.info("--"+obj.toString());
			}
	
			Writer w = null;
			TemplateProcessor pc = templateService.getTemplateProcessor("freemarker");
			
			MainMasterModel pathFormatModel = masterService.getSystemConfig(MainMasterConstant.SCC_EXP_BRW_PATH_FORMAT);
			String pathFormat = pathFormatModel.getFlag1();
			
			List<Object> paths = new ArrayList<Object>();
			
			String[] formats = pathFormat.split("/");
			for(String format : formats) {
				Map<String, Object> folderMap = new HashMap<String, Object>();
				paths.add(folderMap);
				
				int pos;
				if (format.indexOf(ExpBrwConstant.JFN_FISCAL_YEAR) >= 0
					|| format.indexOf(ExpBrwConstant.JFN_CREATED_TIME) >= 0
					|| format.indexOf(ExpBrwConstant.JFN_UPDATED_TIME) >= 0
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
					
					SimpleDateFormat df = new SimpleDateFormat(dFormat);
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
			
			
			MainMasterModel siteModel = masterService.getSystemConfig(MainMasterConstant.SCC_EXP_BRW_SITE_ID);
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
	
	private void setUserGroupFields(ExpBrwModel model) throws Exception {
		/*
		 * Requester Group
		 */
//		MainApprovalMatrixModel apModel = adminApprovalMatrixService.get(model.getApprovalMatrixId());
//		model.setRequesterUser(apModel.getRequesterUser());
//		model.setRequesterGroup(apModel.getRequesterGroup());
	}
	
	public List<ExpBrwModel> list(Map<String, Object> params) {
		
		List<ExpBrwModel> list = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpBrwDAO dao = session.getMapper(ExpBrwDAO.class);
            log.info("exp brw list param:"+params);
    		list = dao.list(params);
            
        } catch (Exception ex) {
			log.error("", ex);
        	throw ex;
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	public List<Map<String, Object>> listOld(Map<String, Object> params) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "AV16000001");
		map.put("name", "ยอดคงค้างผู้ขอเบิก 1");
		map.put("waitAmt", "1000");
		map.put("balance", "2000");
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("id", "AV16000002");
		map.put("name", "ยอดคงค้างผู้ขอเบิก 2");
		map.put("waitAmt", "2000");
		map.put("balance", "4000");
		list.add(map);
		
		
//		SqlSession session = ExpUtil.openSession(dataSource);
//        try {
//            ExpBrwDAO dao = session.getMapper(ExpBrwDAO.class);
//            log.info("exp brw list param:"+params);
//    		list = dao.list(params);
//            
//        } catch (Exception ex) {
//			log.error("", ex);
//        	throw ex;
//        } finally {
//        	session.close();
//        }
        
        return list;
	}
	
	public List<ExpBrwModel> listForSearch(Map<String, Object> params) {
		
		List<ExpBrwModel> list = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpBrwDAO dao = session.getMapper(ExpBrwDAO.class);
            log.info("exp brw list for search param:"+params);
    		list = dao.listForSearch(params);
            
        } catch (Exception ex) {
			log.error("", ex);
        	throw ex;
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	public void delete(String id) throws Exception {
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpBrwDAO expBrwDAO = session.getMapper(ExpBrwDAO.class);
            ExpBrwVoyagerDAO expBrwDtlDAO = session.getMapper(ExpBrwVoyagerDAO.class);
            MainWorkflowDAO workflowDAO = session.getMapper(MainWorkflowDAO.class);
            MainWorkflowHistoryDAO workflowHistoryDAO = session.getMapper(MainWorkflowHistoryDAO.class);
            MainWorkflowReviewerDAO workflowReviewerDAO = session.getMapper(MainWorkflowReviewerDAO.class);
            MainWorkflowNextActorDAO workflowNextActorDAO = session.getMapper(MainWorkflowNextActorDAO.class);

    		ExpBrwModel model = get(id);
    		boolean exists = (model.getFolderRef()!=null) && fileFolderService.exists(new NodeRef(model.getFolderRef()));    		
    		if(exists) {
    			alfrescoService.deleteFileFolder(model.getFolderRef());
        	}

    		workflowNextActorDAO.deleteByMasterId(id);
    		workflowHistoryDAO.deleteByMasterId(id);
    		workflowReviewerDAO.deleteByMasterId(id);
    		workflowDAO.deleteByMasterId(id);
    		expBrwDtlDAO.deleteByMasterId(id);
    		expBrwDAO.delete(id);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
	}
	
	public ExpBrwModel get(String id) {
		
		ExpBrwModel model = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpBrwDAO expBrwDAO = session.getMapper(ExpBrwDAO.class);
            
    		model = expBrwDAO.get(id);
    		model.setTotalRowCount(1l);
        } catch (Exception ex) {
			log.error("", ex);
        } finally {
        	session.close();
        }
        
        return model;
	}
	
	public List<ExpBrwVoyagerModel> listVoyagerByMasterIdAndType(String masterId, String type) {
		
		List<ExpBrwVoyagerModel> list = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpBrwVoyagerDAO dtlDAO = session.getMapper(ExpBrwVoyagerDAO.class);
            
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("masterId", masterId);
    		map.put("type", type);

    		list = dtlDAO.list(map);
        } catch (Exception ex) {
			log.error("", ex);
        	throw ex;
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	public List<ExpBrwVoyagerModel> listOthDtlByMasterId(String masterId) {
		
		List<ExpBrwVoyagerModel> list = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpBrwVoyagerDAO dtlDAO = session.getMapper(ExpBrwVoyagerDAO.class);
            
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("masterId", masterId);

    		list = dtlDAO.list(map);
        } catch (Exception ex) {
			log.error("", ex);
        	throw ex;
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	
	public List<ExpBrwVoyagerModel> listDtlByMasterIdAndFieldName(String masterId, String fieldName) {
		
		List<ExpBrwVoyagerModel> list = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpBrwVoyagerDAO dtlDAO = session.getMapper(ExpBrwVoyagerDAO.class);
            
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("masterId", masterId);
    		map.put("fieldName", fieldName);

    		list = dtlDAO.list(map);
        } catch (Exception ex) {
			log.error("", ex);
        	throw ex;
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	private String encodedImage(String confName) throws Exception {
		
		MainMasterModel imgModel = masterService.getSystemConfig(confName);
		
		NodeRef imgNodeRef = new NodeRef(imgModel.getFlag1());
		ContentReader reader = alfrescoService.getContentByNodeRef(imgNodeRef);
		BufferedImage img = ImageIO.read(reader.getContentInputStream());
		
		StringBuffer imgTag = new StringBuffer();
		imgTag.append("<img src=\"data:image/png;base64,"+ImageUtil.encodeToString(img, "png")+"\"");
		imgTag.append("/>");
		
		return imgTag.toString();
	}
	
	private void prepareSystemFields(Map<String, Object> model, JSONObject dataMap, Map<String, String> imageMap, Map<String, Timestamp> dateTimeMap) throws Exception {
		
		String id = null;
		String reqUser = null;
		
		for (int lvl=1; lvl<=CommonConstant.MAX_APPROVER; lvl++) {
			String v = "S_APP_ACTION_"+lvl; 
			model.put(v, "${"+v+"}");

			v = "S_APP_REASON_"+lvl;
			model.put(v, "${"+v+"}");
			
			v = "S_APP_SIGN_"+lvl;
			model.put(v, "${"+v+"}");
			
			v = "S_APP_NAME_"+lvl;
			model.put(v, "${"+v+"}");
			
			v = "S_APP_TIME_"+lvl;
			model.put(v, "${"+v+"}");
			
			dateTimeMap.put("APP_TIME_"+lvl, null);
		}		
		
		if (dataMap.has(ExpBrwConstant.JFN_ID)) {
			id = dataMap.getString(ExpBrwConstant.JFN_ID);
			
			if (!dataMap.has(ExpBrwConstant.JFN_CREATED_BY)) {
				ExpBrwModel memoModel = get(id);
				reqUser = memoModel.getCreatedBy();
			}
			else {
				reqUser = dataMap.getString(ExpBrwConstant.JFN_CREATED_BY);
			}
			
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("masterId", id);
			params.put("orderDesc", "");
			List<MainWorkflowHistoryModel> hList = mainWorkflowService.listHistory(params);

			
			
			PersonUtil personUtil = new PersonUtil();
			personUtil.setNodeService(nodeService);
			
			Set<Integer> lvlSet = new HashSet<Integer>();
			
			if (hList!=null) {
				boolean wfClosed = false;
				for (MainWorkflowHistoryModel hModel : hList) {
					if (hModel.getAction().indexOf("ปิดงาน") >= 0) {
						wfClosed = true;
						break;
					}
				}
				
				if (wfClosed) {
					for (MainWorkflowHistoryModel hModel : hList) {
						Integer lvl = hModel.getLevel();
						if ((hModel.getAction().indexOf("อนุมัติ") >= 0 || hModel.getAction().indexOf("ปิดงาน") >= 0)
								&& !lvlSet.contains(lvl)
							) {
							
							String action = hModel.getAction().indexOf("ไม่") >= 0 ? "ไม่อนุมัติ" : "อนุมัติ";
							String reason = hModel.getComment();
							String sign = "${S_APP_SIGN_"+lvl+"}";
							dataMap.put("S_APP_SIGN_"+lvl, hModel.getBy());
							imageMap.put("S_APP_SIGN_"+lvl,"S_APP_SIGN_"+lvl);
							String name = personUtil.getFullName(PersonUtil.getPerson(hModel.getBy(), personService));
							String time = hModel.getTime().toString();
							dateTimeMap.put("APP_TIME_"+lvl, hModel.getTime());
							dataMap.put("S_APP_TIME_"+lvl, hModel.getTime());
							
							lvlSet.add(lvl);
							
							model.put("S_APP_ACTION_"+lvl, action!=null ? action : "");
							model.put("S_APP_REASON_"+lvl, reason!=null ? reason : "");
							model.put("S_APP_SIGN_"+lvl, sign!=null ? sign : "");
							model.put("S_APP_NAME_"+lvl, name!=null ? name : "");
							model.put("S_APP_TIME_"+lvl, time!=null ? time : "");
						}
					}
				}
			}
		}
		else {
			reqUser = authService.getCurrentUserName();
		}
		
		NodeRef person = personService.getPerson(reqUser);

		PersonUtil personUtil = new PersonUtil();
		personUtil.setNodeService(nodeService);
		String fullName = personUtil.getFullName(person);
		String googleUserName = personUtil.getGoogleUserName(person);
		
		String jobTitle = personUtil.getJobTitle(person);
		
		model.put("S_REQ_NAME", fullName);
		model.put("S_REQ_JOB_TITLE", jobTitle);
		model.put("S_REQ_G_USERNAME", googleUserName);
		
		model.put("S_ID", id!=null ? id : "${S_ID}");
	}
	
	private String handleSignatureImage(String rptFormat, JSONObject dataMap, Map<String, String> prefixMap) throws Exception  {
		/*
		 * Expected : ${S_SIGN_REQ}
		 * 			: ${S_SIGN_REQ?["100,100"]} 
		 */
		
		/*
		 * prefix
		 */
		for(Entry<String, String> e:prefixMap.entrySet()) {
			String prefix = CommonConstant.REPS_PREFIX
					   + e.getKey()
					   ; // prefix = ${S_SIGN_REQ
			int prePos = rptFormat.indexOf(prefix);
			
			/*
			 * While found prefix or prefixF in rptFormat , replace pattern with image
			 */
			String displayFormat = null;
			String pattern = null;
			String suffix = null;
			int sufPos;
			
			while (prePos >= 0) {
				displayFormat = null;
				
	    		sufPos = rptFormat.indexOf(CommonConstant.REPS_SUFFIX, prePos);
	    		
	    		pattern = rptFormat.substring(prePos, sufPos + CommonConstant.REPS_SUFFIX.length());
	    		suffix = rptFormat.substring(prePos + prefix.length(), sufPos + CommonConstant.REPS_SUFFIX.length());
	    		
	    		int formatPos = suffix.indexOf(CommonConstant.FMTS_PREFIX);
	    		if (formatPos >= 0) {
	    			prePos = suffix.indexOf(CommonConstant.FMTS_PREFIX);
	    			
	    			sufPos = suffix.indexOf(CommonConstant.FMTS_SUFFIX);
	    			displayFormat = suffix.substring(prePos + CommonConstant.FMTS_PREFIX.length(), sufPos);
	    		} else {
	    			sufPos = suffix.indexOf(CommonConstant.REPS_SUFFIX);
	    		}
	    		
	    		MainMasterModel folderModel = masterService.getSystemConfig(MainMasterConstant.SCC_MAIN_SIGNATURE_PATH);
	
	    		boolean hasValue = folderModel!=null;
	    		String value = null;
	    		if (hasValue) {
	        		value = folderModel.getFlag1();
	    			hasValue = (value != null) && !value.toString().equals("");
	    		}
	    		
	    		/*
	    		 * Prepare imgStr if found pattern in rptFormat
	    		 */
				StringBuffer imgTag = new StringBuffer();
				
	    		if (hasValue) {
	    			NodeRef folderNodeRef = new NodeRef(value.toString());
	    			
	    			NodeRef imgNodeRef = alfrescoService.searchSimple(folderNodeRef, dataMap.get(e.getValue())+".png");
	    			
	    			log.info("signature:"+imgNodeRef.toString());
	    			
	    		    if (imgNodeRef != null) {
		    			ContentReader reader = alfrescoService.getContentByNodeRef(imgNodeRef);
		    			BufferedImage img = ImageIO.read(reader.getContentInputStream());
		    			imgTag.append("<img src=\"data:image/png;base64,"+ImageUtil.encodeToString(img, "png")+"\"");
		    			
		    			if (displayFormat!=null && !displayFormat.equals("")) {
		    				String[] pos = displayFormat.trim().split(","); 
		    				
		    				imgTag.append(" style=\"position:fixed;");
		    				imgTag.append("left:"+pos[0]+"px;");
		    				imgTag.append("top:"+pos[1]+"px\"");
		    			}
		    			
		    			imgTag.append("/>");
	    		    }
	    		}
				
	    		rptFormat = rptFormat.replace(pattern, imgTag.toString());
	    		
				prePos = rptFormat.indexOf(prefix);
			}
		}
		
		return rptFormat;
	}	
	
	private String handleImage(String fieldNamePrefix, String fieldName, String rptFormat, Object value) throws Exception  {

		boolean hasValue = value != null && !value.toString().equals("");
		
		String prefix = CommonConstant.REPS_PREFIX
				   + fieldNamePrefix
				   + fieldName
				   + CommonConstant.REPS_SUFFIX
				   ;
		int prePos = rptFormat.indexOf(prefix);
		
		/*
		 * prefix with format
		 */
		String prefixF = CommonConstant.REPS_PREFIX
				   + fieldNamePrefix
				   + fieldName
				   + CommonConstant.FMTS_PREFIX
				   ;
		int prePosF = rptFormat.indexOf(prefixF);
		
		/*
		 * Prepare imgStr if found pattern in rptFormat
		 */
		String imgPrefix = null;
		if (hasValue && ((prePos >= 0) || (prePosF >= 0))) {
			NodeRef imgNodeRef = new NodeRef(value.toString());
			ContentReader reader = alfrescoService.getContentByNodeRef(imgNodeRef);
			BufferedImage img = ImageIO.read(reader.getContentInputStream());
			imgPrefix = "<img src=\"data:image/png;base64,"+ImageUtil.encodeToString(img, "png")+"\"";
		}
		else {
			imgPrefix = "";
		}
		
		/*
		 * While found prefix or prefixF in rptFormat , replace pattern with image
		 */
		String displayFormat = null;
		String pattern = null;
		
		while ((prePos >= 0) || (prePosF >= 0)) {
			
    		displayFormat = null;
    		
    		int sufPos;
    		if (prePos >= 0) {
    			sufPos = rptFormat.indexOf(CommonConstant.REPS_SUFFIX, prePos);
	    		pattern = rptFormat.substring(prePos, sufPos + CommonConstant.REPS_SUFFIX.length());
    		}
    		else {
	    		sufPos = rptFormat.indexOf(CommonConstant.FMTS_SUFFIX, prePosF);
	    		pattern = rptFormat.substring(prePosF, sufPos + CommonConstant.FMTS_SUFFIX.length());
	    		if (hasValue) {
	    			displayFormat = rptFormat.substring(prePosF + prefixF.length(), sufPos);
	    		}
    		}
    		
			StringBuffer imgTag = new StringBuffer(imgPrefix);
			
			if (displayFormat!=null && !displayFormat.equals("")) {
				String[] pos = displayFormat.trim().split(","); 
				
				imgTag.append(" style=\"position:fixed;");
				imgTag.append("left:"+pos[0]+"px;");
				imgTag.append("top:"+pos[1]+"px\"");
			}
			
			if (hasValue) {
				imgTag.append("/>");
			}
			
    		rptFormat = rptFormat.replace(pattern, imgTag.toString());
    		
			prePos = rptFormat.indexOf(prefix);
			prePosF = rptFormat.indexOf(prefixF);
		}
		
		return rptFormat;
	}	
	
	private String handleThaiDate(String fieldNamePrefix, String fieldName, Map<String, Object> model, String rptFormat, int preIndex, Timestamp timestamp, TemplateService templateService, boolean nullNotChange) throws Exception  {
		
		String pattern = CommonConstant.REPS_PREFIX
					   + fieldNamePrefix
					   + fieldName
					   + CommonConstant.FMTS_PREFIX
					   ;
		
		log.info("pattern="+pattern);
		
		String value;
		int newFieldIndex = 0;
		int prePos = rptFormat.indexOf(pattern);
		while (prePos >= 0) {
    		int sufPos = rptFormat.indexOf(CommonConstant.FMTS_SUFFIX, prePos);
    		
    		if (timestamp==null) {
//    			value = CommonConstant.REPS_PREFIX+fieldNamePrefix+fieldName+"=null"+CommonConstant.REPS_SUFFIX;
    			if (nullNotChange) {
    				value = rptFormat.substring(prePos, sufPos + CommonConstant.FMTS_SUFFIX.length());
    			} else {
    				value = "";
    			}
    		}
    		else {

    			// ว d ด ป
    			// ${d} d ${m} ${y}
    			
	    		String format = rptFormat.substring(prePos + pattern.length(), sufPos);

	    		int bePrePos = format.indexOf(CommonConstant.DATE_FORMAT_BUDDHIST_ERA_YEAR);
	    		int tmPrePos = format.indexOf(CommonConstant.DATE_FORMAT_THAI_MONTH);
	    		int tdPrePos = format.indexOf(CommonConstant.DATE_FORMAT_THAI_DAY);
	    		
	    		if ((bePrePos >= 0) || (tdPrePos>=0) || (tmPrePos>=0)) {
	    			
	    			DateFormat df = new SimpleDateFormat(format); 
	    			format = df.format(timestamp);

	    			Map<String, String> formatMap = new HashMap<String, String>();
	    			
	    			df = new SimpleDateFormat("u");
	    			int date = Integer.parseInt(df.format(timestamp))-1;
	    			
	    			String code = CommonConstant.DATE_FORMAT_THAI_DAY + CommonConstant.DATE_FORMAT_THAI_DAY; 
	    			format = format.replace(code, "${dd}");
	    			formatMap.put("dd", CommonConstant.THAI_DAY.get(code).get(date));
	    			
	    			code = CommonConstant.DATE_FORMAT_THAI_DAY; 
	    			format = format.replace(code, "${d}");
	    			formatMap.put("d", CommonConstant.THAI_DAY.get(code).get(date));
	    			
	    			df = new SimpleDateFormat("M");
	    			int month = Integer.parseInt(df.format(timestamp))-1;
	    			
	    			code = CommonConstant.DATE_FORMAT_THAI_MONTH + CommonConstant.DATE_FORMAT_THAI_MONTH; 
	    			format = format.replace(code, "${mm}");
	    			formatMap.put("mm", CommonConstant.THAI_MONTH.get(code).get(month));
	    			
	    			code = CommonConstant.DATE_FORMAT_THAI_MONTH; 
	    			format = format.replace(code, "${m}");
	    			formatMap.put("m", CommonConstant.THAI_MONTH.get(code).get(month));

	    			df = new SimpleDateFormat("yyyy");
	    			String year = String.valueOf(Integer.parseInt(df.format(timestamp)) + CommonConstant.DIFF_BE_AD);
	    			
	    			code = CommonConstant.DATE_FORMAT_BUDDHIST_ERA_YEAR + CommonConstant.DATE_FORMAT_BUDDHIST_ERA_YEAR; 
	    			format = format.replace(code, "${yy}");
	    			formatMap.put("yy", year);
	    			
	    			code = CommonConstant.DATE_FORMAT_BUDDHIST_ERA_YEAR; 
	    			format = format.replace(code, "${y}");
	    			formatMap.put("y", year.substring(year.length()-2));
	    			
	    			log.info("format:"+format);
	    			for(Entry<String, String> e:formatMap.entrySet()) {
	    				log.info(e.getKey() + ":" +e.getValue());
	    			}
	    			
	    			Writer w = new StringWriter();
	    			
	    			TemplateProcessor pc = templateService.getTemplateProcessor("freemarker");
	    			pc.processString(format.replace("&nbsp;", " "), formatMap, w);
	    			
	    			value = w.toString();
	    			log.info("value="+value);
	        		
	    		} else {
	        		SimpleDateFormat dateFormat = new SimpleDateFormat(format.replace("&nbsp;"," "), Locale.US);
	        		
	        		value = dateFormat.format(timestamp);
	    		}
    		}
    		
    		String newFieldName = fieldNamePrefix + "BG__" + preIndex + "_" + newFieldIndex;
    		model.put(newFieldName, value);
    		newFieldIndex++;
    		
    		rptFormat = rptFormat.substring(0, prePos) 
    				+ CommonConstant.REPS_PREFIX 
    				+ newFieldName 
    				+ CommonConstant.REPS_SUFFIX
    				+ rptFormat.substring(sufPos + CommonConstant.FMTS_SUFFIX.length())
    				;
    		
    		prePos = rptFormat.indexOf(pattern);
		}
		
		return rptFormat;
	}
	
	private String getDateDefaultFormatValue(Timestamp timestamp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CommonConstant.RDF_DATE);
		
		return timestamp == null ? "" : dateFormat.format(timestamp);
	}
	
	private String getFloatDefaultFormatValue(Double doubleValue) {
		DecimalFormat decFormat = new DecimalFormat(CommonConstant.RDF_FLOAT);
		
		return decFormat.format(doubleValue);
	}
	
	private String handleDouble(String fieldNamePrefix, String fieldName, Map<String, Object> model, String rptFormat, Double doubleValue) throws Exception  {
		
		String pattern = CommonConstant.REPS_PREFIX
					   + fieldNamePrefix
					   + fieldName
					   + CommonConstant.REPS_SUFFIX
					   ;
		
		String value = getFloatDefaultFormatValue(doubleValue);
		
		rptFormat = rptFormat.replace(pattern, value);
		
		return rptFormat;

	}
	
	private String handleRunningNo(String fieldName, Map<String, Object> model, String rptFormat, int preIndex, Long runningNo) throws Exception  {
		
		String pattern = CommonConstant.REPS_PREFIX
					   + fieldName
					   + CommonConstant.FMTS_PREFIX
					   ;
		
		String value;
		int newFieldIndex = 0;
		int prePos = rptFormat.indexOf(pattern);
		while (prePos >= 0) {
    		int sufPos = rptFormat.indexOf(CommonConstant.FMTS_SUFFIX, prePos);
    		
    		String format = rptFormat.substring(prePos + pattern.length(), sufPos);
    		
    		DecimalFormat df = new DecimalFormat(format);
    		value = df.format(runningNo);
    		
    		String newFieldName = "BG__" + preIndex + "_" + newFieldIndex;
    		model.put(newFieldName, value);
    		newFieldIndex++;
    		
    		rptFormat = rptFormat.substring(0, prePos) 
    				+ CommonConstant.REPS_PREFIX 
    				+ newFieldName 
    				+ CommonConstant.REPS_SUFFIX
    				+ rptFormat.substring(sufPos + CommonConstant.FMTS_SUFFIX.length())
    				;
    		
    		prePos = rptFormat.indexOf(pattern);
		}
		
		return rptFormat;
	}
	
	private String handleStringFunction(String fieldNamePrefix, String fieldName, Map<String, Object> model, String rptFormat, int preIndex, String srcValue) throws Exception  {
		
		String pattern = CommonConstant.REPS_PREFIX
					   + fieldNamePrefix
					   + fieldName
					   + CommonConstant.FMTS_PREFIX
					   ;
		
		log.info("handleStringFunction() : "+pattern);
		
		String value;
		int newFieldIndex = 0;
		int prePos = rptFormat.indexOf(pattern);
		while (prePos >= 0) {
    		int sufPos = rptFormat.indexOf(CommonConstant.FMTS_SUFFIX, prePos);
    		
    		if (srcValue==null) {
    			value = "";
    		}
    		else {

    			// fullname()
    			
	    		String format = rptFormat.substring(prePos + pattern.length(), sufPos);
	    		
	    		PersonUtil personUtil = new PersonUtil();
	    		personUtil.setNodeService(nodeService);

        		value = srcValue;

        		if (!srcValue.equals("") && srcValue.indexOf(",")<0) {
		    		if (format.indexOf(CommonConstant.FUNC_FULL_NAME) >= 0) {
		    			value = personUtil.getFullName(PersonUtil.getPerson(srcValue, personService));
		    		} else 
		    		if (format.indexOf(CommonConstant.FUNC_FIRST_NAME) >= 0) {
		    			value = personUtil.getFirstName(PersonUtil.getPerson(srcValue, personService));
		    		} else 
		    		if (format.indexOf(CommonConstant.FUNC_LAST_NAME) >= 0) {
		    			value = personUtil.getLastName(PersonUtil.getPerson(srcValue, personService));
		    		} else
		    		if (format.startsWith("$")) {
		    			value = model.get(format)!=null ? (model.get(format).equals("1") ? srcValue : "") : "";
		    		}
	    		}
        		else {
        			value = "";
        		}
    		}
			log.info("  value="+value);
    		
    		String newFieldName = fieldNamePrefix + "BG__" + preIndex + "_" + newFieldIndex;
    		model.put(newFieldName, value);
    		newFieldIndex++;
    		
    		rptFormat = rptFormat.substring(0, prePos) 
    				+ CommonConstant.REPS_PREFIX 
    				+ newFieldName 
    				+ CommonConstant.REPS_SUFFIX
    				+ rptFormat.substring(sufPos + CommonConstant.FMTS_SUFFIX.length())
    				;
    		
    		prePos = rptFormat.indexOf(pattern);
		}
		
		return rptFormat;
	}
	
	@Override
	public void update(SubModuleModel subModuleModel) throws Exception {
		
		ExpBrwModel model = (ExpBrwModel)subModuleModel;
		
        SqlSession session = ExpUtil.openSession(dataSource);
        
        try {
            ExpBrwDAO dao = session.getMapper(ExpBrwDAO.class);
            
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
	
	public List<Map<String, Object>> listWorkflowPath(String id) {
		
		List<Map<String, Object>> list = mainWorkflowService.listWorkflowPath(id);
		
		/*
		 * Add Requester
		 */
		ExpBrwModel model = get(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("LEVEL", MainWorkflowConstant.TN_REQUESTER_CAPTION);
		map.put("U", model.getCreatedBy());
		map.put("G", "");
		map.put("IRA", false);
		
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
		for(Map<String, Object> rec:list) {
			String empCode = (String)rec.get("U");
			MainHrEmployeeModel empModel = adminHrEmployeeService.get(empCode);
			if (empModel!=null) {
				rec.put("U", empCode + " - " + empModel.getFirstName());
			}
		}
		
		return list;
	}

	@Override
	public String getWorkflowDescription(SubModuleModel paramModel) throws Exception {
		
		ExpBrwModel expBrwModel = (ExpBrwModel)paramModel;
		
		MainMasterModel descFormatModel = masterService.getSystemConfig(MainMasterConstant.SCC_EXP_BRW_WF_DESC_FORMAT);
		String descFormat = descFormatModel.getFlag1();
		
		JSONObject map = ExpBrwUtil.convertToJSONObject(expBrwModel,false);
		
		Writer w = null;
		TemplateProcessor pc = templateService.getTemplateProcessor("freemarker");
		w = new StringWriter();
		pc.processString(descFormat, map, w);
		
		return w.toString();
	}

	@Override
	public Map<String, Object> convertToMap(SubModuleModel model) {
		return ExpBrwUtil.convertToMap((ExpBrwModel)model, false);
	}

	@Override
	public String getWorkflowName() {
		return ExpBrwConstant.WF_NAME;
	}
	
	@Override
	public String getSubModuleType() {
		return CommonConstant.SUB_MODULE_EXP_BRW;
	}
	
	public String copy(String id) throws Exception {
		
        SqlSession session = ExpUtil.openSession(dataSource);
        
        ExpBrwModel model = new ExpBrwModel();
        
        try {
            ExpBrwDAO expBrwDAO = session.getMapper(ExpBrwDAO.class);
            ExpBrwVoyagerDAO expBrwVoyagerDAO = session.getMapper(ExpBrwVoyagerDAO.class);
            
            /*
             * Get Old Exp Brw
             */
            ExpBrwModel oModel = get(id);
            
            /*
             * Prepare New Exp Brw
             */
            model.setStatus(ExpBrwConstant.ST_DRAFT);
            
            Map<String, Object> reqBy = adminHrEmployeeService.getWithDtl(authService.getCurrentUserName());
            
        	model.setReqBy(authService.getCurrentUserName());
        	model.setReqSectionId((Integer)reqBy.get("section_id"));
        	
        	model.setObjectiveType(oModel.getObjectiveType());
        	model.setObjective(oModel.getObjective());
        	model.setReason(oModel.getReason());
        	
        	model.setBudgetCcType(oModel.getBudgetCcType());
        	model.setBudgetCc(oModel.getBudgetCc());
        	
        	model.setCostControlTypeId(oModel.getCostControlTypeId());
        	model.setCostControlId(oModel.getCostControlId());
        	model.setCostControl(oModel.getCostControl());
        	model.setCostControlFrom(oModel.getCostControlFrom());
        	model.setCostControlTo(oModel.getCostControlTo());
        	
        	model.setTotalType(oModel.getTotalType());
        	model.setTotal(oModel.getTotal());
        	
        	model.setBankType(oModel.getBankType());
        	model.setBank(oModel.getBank());
        	
        	model.setRewarning(oModel.getRewarning());
        	model.setWaitingDay(oModel.getWaitingDay());
        	model.setWfStatus(oModel.getWfStatus());
            
    		model.setUpdatedBy(authService.getCurrentUserName());
    		model.setCreatedBy(model.getUpdatedBy());
    		
			/*
			 * Gen New ID
			 */
    		MainMasterModel masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_EXP_BRW_ID_FORMAT);
    		String idFormat = masterModel.getFlag1();

        	Long runningNo = expBrwDAO.getNewRunningNo();
        	
        	String newId = genNewId(model, idFormat, runningNo); // PR16000001
        	
    		model.setId(newId);
    		log.info("new id:"+newId);
    		
            /*
             * Create New Folder
             */
    		createEcmFolder(model);
    		
    		/*
    		 * Create New Exp Brw 
    		 */
    		expBrwDAO.add(model);
            
            /*
             * Create New Voyagers
             */
    		List<ExpBrwVoyagerModel> voyagerList = listVoyagerByMasterIdAndType(oModel.getId(), null);
            for(ExpBrwVoyagerModel vModel : voyagerList) {
            	vModel.setMasterId(model.getId());
	        	vModel.setCreatedBy(model.getUpdatedBy());
	        	vModel.setUpdatedBy(model.getUpdatedBy());
	        	
            	expBrwVoyagerDAO.add(vModel);
            }
            
            /*
             * Create New Attach File
             */
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        	throw ex;
        } finally {
        	session.close();
        }

        return model.getId();
	}	

	@Override
	public void setWorkflowParameters(Map<QName, Serializable> parameters, SubModuleModel paramModel, List<NodeRef> docList, List<NodeRef> attachDocList) {
		
		ExpBrwModel model = (ExpBrwModel)paramModel;
		
		/*
		 * Common Attribute
		 */
        parameters.put(ExpBrwWorkflowConstant.PROP_ID, model.getId());
        parameters.put(ExpBrwWorkflowConstant.PROP_FOLDER_REF, model.getFolderRef());

        parameters.put(ExpBrwWorkflowConstant.PROP_DOCUMENT, (Serializable)docList);
        parameters.put(ExpBrwWorkflowConstant.PROP_ATTACH_DOCUMENT, (Serializable)attachDocList);
//        parameters.put(ExpBrwWorkflowConstant.PROP_COMMENT_HISTORY, "");
        
        parameters.put(ExpBrwWorkflowConstant.PROP_TASK_HISTORY, "");
        
		/*
		 * Special Attribute
		 */
		parameters.put(ExpBrwWorkflowConstant.PROP_OBJECTIVE_TYPE, model.getObjectiveType());
		parameters.put(ExpBrwWorkflowConstant.PROP_OBJECTIVE, model.getObjective());
		parameters.put(ExpBrwWorkflowConstant.PROP_REASON, model.getReason());
		parameters.put(ExpBrwWorkflowConstant.PROP_TOTAL, model.getTotal());
		
		Map<String, Object> map = null;
		if (model.getBudgetCcType().equals(ExpBrwConstant.BCCT_UNIT)) {
			map = adminSectionService.get(model.getBudgetCc());
			model.setBudgetCcName((String)map.get("description"));
		} else {
			map = adminProjectService.get(model.getBudgetCc());
			model.setBudgetCcName((String)map.get("name")+" - "+(String)map.get("description"));
		}
		
		parameters.put(ExpBrwWorkflowConstant.PROP_BUDGET_CC, model.getBudgetCcName());
		
	}

	@Override
	public String getActionCaption(String action) {
		
		Map<String, String> WF_TASK_ACTIONS = new HashMap<String, String>();
		
    	WF_TASK_ACTIONS.put(MainWorkflowConstant.TA_START, "ขออนุมัติ");
		
    	WF_TASK_ACTIONS.put(MainWorkflowConstant.TA_APPROVE, "อนุมัติ");
    	WF_TASK_ACTIONS.put(MainWorkflowConstant.TA_REJECT, "ไม่อนุมัติ");
    	WF_TASK_ACTIONS.put(MainWorkflowConstant.TA_CONSULT, "ขอคำปรึกษา");
    	
    	WF_TASK_ACTIONS.put(MainWorkflowConstant.TA_COMMENT, "ให้ความเห็น");
    	
    	WF_TASK_ACTIONS.put(MainWorkflowConstant.TA_RESUBMIT, "ขออนุมัติใหม่");
    	WF_TASK_ACTIONS.put(MainWorkflowConstant.TA_CANCEL, "ยกเลิก");
    	
    	WF_TASK_ACTIONS.put(MainWorkflowConstant.TA_COMPLETE, "ส่งงานให้พัสดุ");
		
		return WF_TASK_ACTIONS.get(action);
	}

	@Override
	public List<MainWorkflowNextActorModel> listNextActor(SubModuleModel model) {
		List<MainWorkflowNextActorModel> list = new ArrayList<MainWorkflowNextActorModel>();
		
		ExpBrwModel realModel = (ExpBrwModel)model;
		
		List<Map<String, Object>> superList = adminWkfConfigService.listSupervisor(realModel.getCostControlId());
		if(superList.size()>0) {
			Map<String, Object> map = superList.get(0);
			
			MainWorkflowNextActorModel actorModel = new MainWorkflowNextActorModel();
			
			actorModel.setMasterId(model.getId());
			actorModel.setLevel(1);
			actorModel.setActor(ExpBrwConstant.NA_BOSS);
			actorModel.setActorUser(MainUserGroupUtil.code2login((String)map.get(MainHrEmployeeConstant.TFN_EMPLOYEE_CODE)));
			actorModel.setCreatedBy(model.getUpdatedBy());
			
			list.add(actorModel);
		}
		
		return list;
	}

	@Override
	public String getFirstComment(SubModuleModel model) {
		ExpBrwModel realModel = (ExpBrwModel)model;
		
		return realModel.getObjectiveType() + " " + realModel.getObjective() + " " + realModel.getReason();
	}

	@Override
	public String getNextActionInfo() {
		return "ฝ่ายพัสดุ";
	}
	
	public List<Map<String, Object>> listForInf(String id) {
		
		List<Map<String, Object>> list = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpBrwDAO dao = session.getMapper(ExpBrwDAO.class);
            
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("id", id);

    		list = dao.listForInf(map);
            
        } catch (Exception ex) {
			log.error("", ex);
        	throw ex;
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	public List<Map<String, Object>> listDtlForInf(String id) {
		
		List<Map<String, Object>> list = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpBrwVoyagerDAO dao = session.getMapper(ExpBrwVoyagerDAO.class);
            
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("id", id);

    		list = dao.listForInf(map);
            
        } catch (Exception ex) {
			log.error("", ex);
        	throw ex;
        } finally {
        	session.close();
        }
        
        return list;
	}	
	
	@Override
	public QName getPropNextReviewers() {
		return ExpBrwWorkflowConstant.PROP_NEXT_REVIEWERS;
	}

	@Override
	public String getModelUri() {
		return ExpBrwWorkflowConstant.MODEL_URI;
	}

	@Override
	public String getWfUri() {
		return ExpBrwWorkflowConstant.WF_URI;
	}

	@Override
	public String getModelPrefix() {
		return ExpBrwWorkflowConstant.MODEL_PREFIX;
	}

	@Override
	public Map<String, String> getBossMap(String docType, SubModuleModel subModuleModel) {
		
		ExpBrwModel model = (ExpBrwModel)subModuleModel;
		
		/*
		 * Search Original Boss List
		 */
		Map<String, String> tmpMap = new LinkedHashMap<String, String>();
		
		log.info("getBossMap()");
		log.info("  docType:'"+docType+"'");
		log.info("  budgetCcType:"+model.getBudgetCcType());
		log.info("  sectionId:'"+model.getReqSectionId()+"'");
		log.info("  reqUser:"+model.getReqBy());
		log.info("  total:"+model.getTotal());
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
        	MainWkfCmdBossLevelApprovalDAO dao = session.getMapper(MainWkfCmdBossLevelApprovalDAO.class);
        	
        	Map<String, Object> params = new HashMap<String, Object>();
        	params.put("docType", docType);
        	if (model.getBudgetCcType().equals(ExpBrwConstant.BCCT_UNIT)) {
        		params.put("sectionId", model.getBudgetCc());
        	} else {
        		// find project manager section id
        		List<Map<String, Object>> list = adminProjectService.listProjectManager(model.getBudgetCc());
        		Map<String, Object> map = list.get(0);
        		params.put("sectionId", map.get(MainHrEmployeeConstant.TFN_SECTION_ID));
        		
        		tmpMap.put("00", MainUserGroupUtil.code2login((String)map.get("employee_code")));
        	}
        	
        	List<Map<String, Object>> bossList = dao.listBoss(params);
        	log.info("  bossList"+bossList);
        	
        	if (model.getBudgetCcType().equals(ExpBrwConstant.BCCT_UNIT)) {
        		tmpMap = getUnitBossMap(model, bossList, tmpMap);
        	}
        	else {
        		tmpMap = getProjectBossMap(model, bossList, tmpMap);
        	}

            log.info("  tmpMap.size:"+tmpMap.size());
            
//        	- not exist in bossList -> start from first item -> find upper boundary by money
//        	- exist in bossList
//        		- 1 time -> find boss -> find upper boundary by money
//        		- many times -> find boss -> find upper boundary by money
        	
//        	- if top boss is requester -> find special user
            
        } catch (Exception ex) {
        	log.error(ex);
        } finally {
        	session.close();
        }
		
		return tmpMap;
	}
	
	public Map<String, String> getUnitBossMap(ExpBrwModel model, List<Map<String, Object>> bossList,Map<String, String> tmpMap ) throws Exception {
		
		String reqByCode = MainUserGroupUtil.login2code(model.getReqBy());
    	
    	Map<String, Object> reservedBoss = null;
    	Map<String, Object> lastBossMap = null;
    	
    	/*
    	 * Find Boss by level, if reqByCode exist in workflow path
    	 */
    	int start = 0;
    	int i = 0;
    	
    	for(Map<String, Object> boss : bossList) {
    		
    		log.info("  "+boss.get("lvl")+" "+boss.get("employee_code"));
    		
    		if (boss.get("employee_code").equals(model.getReqBy())) {
    			log.info(model.getReqBy()+" is in path");
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
    	
    	log.info("lastBoss:"+lastBossMap.get("employee_code")+", reqBy:"+model.getReqBy());
    	/*
    	 * Replace Last Boss if he is Requester
    	 */
    	if (lastBossMap.get("employee_code").equals(model.getReqBy())) {
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
    				if (e.getValue().equals(model.getReqBy())) {
    					tmpMap.remove(e.getKey());
    					break;
    				}
    			}
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

	public Map<String, String> getProjectBossMap(ExpBrwModel model, List<Map<String, Object>> bossList,Map<String, String> tmpMap ) throws Exception {
		
		String reqByCode = MainUserGroupUtil.login2code(model.getReqBy());
    	
    	Map<String, Object> reservedBoss = null;
    	Map<String, Object> lastBossMap = null;
    	
    	/*
    	 * Find Boss by money
    	 */
    	for(Map<String, Object> boss : bossList) {
    		
    		log.info("  "+boss.get("lvl")+" "+boss.get("employee_code"));
    		log.info("    amt_min:"+boss.get("amount_min"));
    		if ((Double)boss.get("amount_min") <= model.getTotal()) {
        		if (boss.get("employee_code").equals(reqByCode)) {
        			tmpMap.clear();
        		}
    			tmpMap.put((String)boss.get("lvl"), MainUserGroupUtil.code2login((String)boss.get("employee_code")));
    			lastBossMap = boss;
    		} else {
    			if (reservedBoss==null) {
    				reservedBoss = boss;
    			}
    		}
    		
    		if (reservedBoss!=null) {
    			break;
    		}
    	}
    	
    	/*
    	 * Replace Last Boss if he is Requester
    	 */
    	if (lastBossMap.get("employee_code").equals(reqByCode)) {
    		if (reservedBoss!=null) {
        		tmpMap.remove(lastBossMap.get("lvl"));
    			tmpMap.put((String)reservedBoss.get("lvl"), MainUserGroupUtil.code2login((String)reservedBoss.get("employee_code")));
    		}
    		else {
    			// Waiting SA
    			
    		}
    	}
    	
    	
    	/*
    	 * Remove Requester from Boss List
    	 */
    	List<String> rmList = new ArrayList<String>();
    	int i = 1;
    	int size = tmpMap.size();
    	for(Entry<String, String> e : tmpMap.entrySet()) {
    		log.info("  e.key:"+e.getKey()+", value:"+e.getValue());
    		
    		if (i==size) {
    			break;
    		}
    		
    		if (e.getValue().equals(reqByCode)) {
    			rmList.add(e.getKey());
    		}
    		
    		i++;
    	}

    	for(String k : rmList) {
    		tmpMap.remove(k);
    	}
    	
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
		
		if (prevEntry != null) {
			map.put((String)prevEntry.getKey(), (String)prevEntry.getValue());
		}
		
        log.info("  map="+map);

        return map;
	}
	

	private Map<String, Object> getFirstApprover(String id) {
		Map<String, Object> map = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpBrwDAO dao = session.getMapper(ExpBrwDAO.class);
    		map = dao.getFirstApprover(id);
            
        } catch (Exception ex) {
			log.error("", ex);
        	throw ex;
        } finally {
        	session.close();
        }
        
        return map;
	}
	
	private Map<String, Object> getLastApprover(String id) {
		Map<String, Object> map = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpBrwDAO dao = session.getMapper(ExpBrwDAO.class);
    		map = dao.getLastApprover(id);
            
        } catch (Exception ex) {
			log.error("", ex);
        	throw ex;
        } finally {
        	session.close();
        }
        
        return map;
	}
	
	@Override
	public List<String> listRelatedUser(SubModuleModel subModuleModel) {
		List<String> list = new ArrayList<String>();
		
		ExpBrwModel model = (ExpBrwModel)subModuleModel;
		
		list.add(model.getReqBy());
		
		return list;
	}
	
	@Override
	public String getDocDesc() {
		return "ใบเบิกเงินยืม";
	}	

	@Override
	public MainWorkflowHistoryModel getReqByWorkflowHistory(SubModuleModel subModuleModel) {
		MainWorkflowHistoryModel hModel = new MainWorkflowHistoryModel();
		
		ExpBrwModel model = (ExpBrwModel)subModuleModel;
		
		hModel.setTime(model.getCreatedTime());
		hModel.setLevel(0);
		hModel.setComment("");
		hModel.setAction("");
		hModel.setTask(MainWorkflowConstant.TN_REAL_REQUESTER_CAPTION);
		hModel.setBy(model.getReqBy());
		
		return hModel;
	}
}
