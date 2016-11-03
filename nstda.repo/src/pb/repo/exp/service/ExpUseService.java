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
import java.util.Arrays;
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
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

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
import pb.common.util.CommonUtil;
import pb.common.util.DocUtil;
import pb.common.util.FolderUtil;
import pb.common.util.ImageUtil;
import pb.common.util.PersonUtil;
import pb.common.util.StringUtil;
import pb.repo.admin.constant.MainBudgetSrcConstant;
import pb.repo.admin.constant.MainHrEmployeeConstant;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.constant.MainWkfConfigDocTypeConstant;
import pb.repo.admin.constant.MainWorkflowConstant;
import pb.repo.admin.dao.MainWkfCmdBossLevelApprovalDAO;
import pb.repo.admin.dao.MainWorkflowDAO;
import pb.repo.admin.dao.MainWorkflowHistoryDAO;
import pb.repo.admin.dao.MainWorkflowNextActorDAO;
import pb.repo.admin.dao.MainWorkflowReviewerDAO;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.model.MainWorkflowHistoryModel;
import pb.repo.admin.model.MainWorkflowNextActorModel;
import pb.repo.admin.model.SubModuleModel;
import pb.repo.admin.service.AdminAccountTaxService;
import pb.repo.admin.service.AdminBankMasterService;
import pb.repo.admin.service.AdminCostControlService;
import pb.repo.admin.service.AdminFundService;
import pb.repo.admin.service.AdminHrEmployeeService;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminProjectService;
import pb.repo.admin.service.AdminSectionService;
import pb.repo.admin.service.AdminUserGroupService;
import pb.repo.admin.service.AdminWkfConfigService;
import pb.repo.admin.service.AlfrescoService;
import pb.repo.admin.service.MainSrcUrlService;
import pb.repo.admin.service.SubModuleService;
import pb.repo.admin.util.MainUserGroupUtil;
import pb.repo.admin.util.RptUtil;
import pb.repo.common.mybatis.DbConnectionFactory;
import pb.repo.exp.constant.ExpUseAttendeeConstant;
import pb.repo.exp.constant.ExpUseConstant;
import pb.repo.exp.constant.ExpUseWorkflowConstant;
import pb.repo.exp.dao.ExpUseAttendeeDAO;
import pb.repo.exp.dao.ExpUseDAO;
import pb.repo.exp.dao.ExpUseDtlDAO;
import pb.repo.exp.model.ExpUseAttendeeModel;
import pb.repo.exp.model.ExpUseDtlModel;
import pb.repo.exp.model.ExpUseModel;
import pb.repo.exp.util.ExpUseAttendeeUtil;
import pb.repo.exp.util.ExpUseDtlUtil;
import pb.repo.exp.util.ExpUseUtil;
import pb.repo.exp.util.ExpUtil;

@Service
public class ExpUseService implements SubModuleService {

	private static Logger log = Logger.getLogger(ExpUseService.class);

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
	ExpUseWorkflowService mainWorkflowService;
	
	@Autowired
	AdminHrEmployeeService adminHrEmployeeService;
	
	@Autowired
	AdminWkfConfigService adminWkfConfigService;
	
	@Autowired
	AdminSectionService adminSectionService;
	
	@Autowired
	AdminProjectService adminProjectService;
	
	@Autowired
	AdminFundService adminFundService;
	
	@Autowired
	AdminCostControlService adminCostControlService;
	
	@Autowired
	AdminAccountTaxService adminAccountTaxService;
	
	@Autowired
	AlfrescoService alfrescoTaxService;
	
	@Autowired
	AdminBankMasterService adminBankMasterService;
	
	public ExpUseModel save(ExpUseModel model, String attendees, String items, String files, boolean genDoc) throws Exception {
		
        SqlSession session = ExpUtil.openSession(dataSource);
        
        try {
            ExpUseDAO expUseDAO = session.getMapper(ExpUseDAO.class);
            ExpUseAttendeeDAO expUseAttendeeDAO = session.getMapper(ExpUseAttendeeDAO.class);
            ExpUseDtlDAO expUseDtlDAO = session.getMapper(ExpUseDtlDAO.class);
            
            setUserGroupFields(model);
            
    		model.setUpdatedBy(authService.getCurrentUserName());
    		model = lookupOtherFields(model);
    		
			model.setAttendeeList(ExpUseAttendeeUtil.convertJsonToList(attendees, model.getId()));
			model.setDtlList(ExpUseDtlUtil.convertJsonToList(items, model.getId()));
    		
            if (model.getId() == null) {
        		model.setCreatedBy(model.getUpdatedBy());
        		
    			/*
    			 * Gen New ID
    			 */
        		MainMasterModel masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_EXP_USE_ID_FORMAT);
        		String idFormat = masterModel.getFlag1();

            	Long id = expUseDAO.getNewRunningNo();
            	
            	String newId = genNewId(model, idFormat, id); // PR16000001
            	
        		model.setId(newId);
        		log.info("new id:"+newId);
            	doCommonSaveProcess(model, genDoc, attendees, items, files);
            	
        		/*
        		 * Add DB
        		 */
            	expUseDAO.add(model);
            }
            else {
            	doCommonSaveProcess(model, genDoc, attendees, items, files);
            	
            	/*
            	 * Update DB
            	 */
            	expUseDAO.update(model);
            	
            	expUseAttendeeDAO.deleteByMasterId(model.getId());
            	expUseDtlDAO.deleteByMasterId(model.getId());
            }
            
            List<ExpUseDtlModel> dtlList = model.getDtlList();
            for(ExpUseDtlModel dtlModel : dtlList) {
            	dtlModel.setMasterId(model.getId());
	        	dtlModel.setCreatedBy(model.getUpdatedBy());
	        	dtlModel.setUpdatedBy(model.getUpdatedBy());
	        	
            	expUseDtlDAO.add(dtlModel);
            }
            
            List<ExpUseAttendeeModel> vList = model.getAttendeeList();
            for(ExpUseAttendeeModel vModel : vList) {
            	vModel.setMasterId(model.getId());
	        	vModel.setCreatedBy(model.getUpdatedBy());
	        	vModel.setUpdatedBy(model.getUpdatedBy());
	        	
            	expUseAttendeeDAO.add(vModel);
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
	
	private ExpUseModel lookupOtherFields(ExpUseModel model) {
		List<Map<String, Object>> list = adminWkfConfigService.listPurchasingUnit(model.getBudgetCc());
		if (list!=null && list.size()>0) {
			Map<String, Object> map = list.get(0);
			
		}
		
		return model;
	}
	
	public String save(ExpUseModel model) throws Exception {
		
        SqlSession session = ExpUtil.openSession(dataSource);
        
        try {
            ExpUseDAO expUseDAO = session.getMapper(ExpUseDAO.class);
            
    		model.setUpdatedBy(authService.getCurrentUserName());
    		
    		NodeRef folderNodeRef = new NodeRef(model.getFolderRef());
    		model = genDoc(model, folderNodeRef);
        	
        	/*
        	 * Update DB
        	 */
        	expUseDAO.update(model);
            
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
	
	private String genNewId(ExpUseModel expUseModel, String oriFormat, Long runningNo) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();

		int newFieldIndex = 0;
		
		boolean done = oriFormat.indexOf(ExpUseConstant.JFN_CREATED_TIME)<0
					&& oriFormat.indexOf(ExpUseConstant.JFN_UPDATED_TIME)<0
					&& oriFormat.indexOf(ExpUseConstant.JFN_RUNNING_NO)<0
					&& oriFormat.indexOf(ExpUseConstant.JFN_FISCAL_YEAR)<0
					;
		while (!done) {
    		if (oriFormat.indexOf(ExpUseConstant.JFN_CREATED_TIME) >= 0) {
	    		Timestamp timestampValue = expUseModel.getCreatedTime()!=null ? expUseModel.getCreatedTime() : new Timestamp(Calendar.getInstance().getTimeInMillis());
	    		oriFormat = handleThaiDate("", ExpUseConstant.JFN_CREATED_TIME, model, oriFormat, newFieldIndex, timestampValue, templateService, false);
				model.put(ExpUseConstant.JFN_CREATED_TIME,  getDateDefaultFormatValue(timestampValue));
    		}
    		else
    		if (oriFormat.indexOf(ExpUseConstant.JFN_UPDATED_TIME) >= 0) {
	    		Timestamp timestampValue = expUseModel.getUpdatedTime()!=null ? expUseModel.getUpdatedTime() : new Timestamp(Calendar.getInstance().getTimeInMillis());
	    		oriFormat = handleThaiDate("", ExpUseConstant.JFN_UPDATED_TIME, model, oriFormat, newFieldIndex, timestampValue, templateService, false);
				model.put(ExpUseConstant.JFN_UPDATED_TIME,  getDateDefaultFormatValue(timestampValue));
    		}
    		else
    		if (oriFormat.indexOf(ExpUseConstant.JFN_RUNNING_NO) >= 0) {
				oriFormat = handleRunningNo(ExpUseConstant.JFN_RUNNING_NO, model, oriFormat, newFieldIndex, runningNo);
				model.put(ExpUseConstant.JFN_RUNNING_NO,  runningNo);
    		}
    		else
    		if (oriFormat.indexOf(ExpUseConstant.JFN_FISCAL_YEAR) >= 0) {
    			Calendar cal = Calendar.getInstance();
	    		if (cal.get(Calendar.MONTH) >= 9) { // >= October (Thai Start Budget Year)
	    			cal.add(Calendar.YEAR, 1);
	    		}
	    		Timestamp timestampValue = new Timestamp(cal.getTimeInMillis());
	    		oriFormat = handleThaiDate("", ExpUseConstant.JFN_FISCAL_YEAR, model, oriFormat, newFieldIndex, timestampValue, templateService, false);
				model.put(ExpUseConstant.JFN_FISCAL_YEAR,  getDateDefaultFormatValue(timestampValue));
    		}
    		
			newFieldIndex++;
			done = oriFormat.indexOf(ExpUseConstant.JFN_CREATED_TIME)<0
				&& oriFormat.indexOf(ExpUseConstant.JFN_UPDATED_TIME)<0
				&& oriFormat.indexOf(ExpUseConstant.JFN_RUNNING_NO)<0
				&& oriFormat.indexOf(ExpUseConstant.JFN_FISCAL_YEAR)<0
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
	
	public void updateStatus(ExpUseModel model) throws Exception {
		
        SqlSession session = ExpUtil.openSession(dataSource);
        
        try {
            ExpUseDAO dao = session.getMapper(ExpUseDAO.class);
          
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
	
	public JSONObject validateAssignee(ExpUseModel model) throws Exception {
		
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
	
	public JSONObject validateWfPath(ExpUseModel model) throws Exception {
		
		JSONObject result = new JSONObject();
		
        try {
        	
        	Map<String, String> bossMap = getBossMap(MainWkfConfigDocTypeConstant.DT_AP, model);

        	if (bossMap==null || bossMap.size()==0) {
				result.put("valid", false);
				
				String msg = ExpUseUtil.getMessage("ERR_NOT_FOUND_WORKFLOW_PATH", I18NUtil.getLocale());
				result.put("msg",  msg.split(",")[1]);
        	} else {
        		result.put("valid", true);
        	}
        	
        } catch (Exception ex) {
        	result.put("valid", false);
			String msg = ExpUseUtil.getMessage("ERR_NOT_FOUND_WORKFLOW_PATH", I18NUtil.getLocale());
			result.put("msg",  msg.split(",")[1]);
			log.error("", ex);
        }

        return result;
	}	
	
	private ExpUseModel doCommonSaveProcess(ExpUseModel model, boolean genDoc, String attendees, String dtls, String files) throws Exception {
		
		/*
		 * Find Pcm Section Id
		 */
		Integer pcmSectionId = null;
		
		Integer sectionId = null;
		
		if (model.getBudgetCcType().equals(ExpUseConstant.BCCT_PROJECT)) {
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
			model.setDtlList(ExpUseDtlUtil.convertJsonToList(dtls, model.getId()));
			model.setAttendeeList(ExpUseAttendeeUtil.convertJsonToList(attendees, model.getId()));
			
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
	
	public ExpUseModel genDoc(ExpUseModel model, NodeRef folderNodeRef) throws Exception {
		/*
		 * Gen Doc in tmp folder
		 */
    	String fileName =  doGenDoc(ExpUseConstant.JR_EX, model);
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
	
	public void updateDoc(ExpUseModel model) throws Exception {
		/*
		 * Gen Doc in tmp folder
		 */
    	String fileName =  doGenDoc(ExpUseConstant.JR_EX, model);
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
	
	public String doGenDoc(String name, ExpUseModel model) throws Exception {
		
		String lang = "_th";
		
		DecimalFormat df = new DecimalFormat(CommonConstant.MONEY_FORMAT);
		
		List<Map<String, Object>> empList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> othList = new ArrayList<Map<String, Object>>();
		Map<String, Object> attendeeMap = null;
		
        List<ExpUseAttendeeModel> tmpAttendeeList = model.getAttendeeList();
        for(ExpUseAttendeeModel tmpAttendee : tmpAttendeeList) {
        	attendeeMap = new HashMap<String, Object>();
			
        	attendeeMap.put("code", tmpAttendee.getCode());
        	attendeeMap.put("name", StringUtil.replaceNBSP(tmpAttendee.getTitle()+" "+tmpAttendee.getFname()+" "+tmpAttendee.getLname()));
        	attendeeMap.put("section", StringUtil.replaceNBSP(tmpAttendee.getUnitType()));
        	attendeeMap.put("position", tmpAttendee.getPosition());
			
			if (tmpAttendee.getType().equals(ExpUseAttendeeConstant.T_EMPLOYEE)) {
				empList.add(attendeeMap);
			} else {
				othList.add(attendeeMap);
			}
        }
        log.info("empList.size="+empList.size());
        log.info("othList.size="+othList.size());
        
		/*
		 * Dtl
		 */
		List<Map<String, Object>> dtlList = new ArrayList<Map<String, Object>>();
		Map<String, Object> dtlMap = null;
		
        List<ExpUseDtlModel> tmpDtlList = model.getDtlList();
        for(ExpUseDtlModel tmpDtl : tmpDtlList) {
        	dtlMap = new HashMap<String, Object>();
			
        	dtlMap.put("actGrp", tmpDtl.getActGrpName());
        	dtlMap.put("act", tmpDtl.getActName());
        	dtlMap.put("item", tmpDtl.getActivity());
        	dtlMap.put("cond1", tmpDtl.getCondition1());
        	dtlMap.put("amount", df.format(tmpDtl.getAmount()));
			
        	dtlList.add(dtlMap);
        }
		
		/*
		 * Parameters
		 */
		Map parameters = new HashMap();
		parameters.put("imgPath", CommonConstant.GP_REPORT_IMG_PATH);
		
		if (empList.size()>0) {
			log.info("empList.size()>0");
			parameters.put("EmpDataSource", new JRMapCollectionDataSource(new ArrayList<Map<String,?>>(empList)));
		}
		if (othList.size()>0) {
			parameters.put("OthDataSource", new JRMapCollectionDataSource(new ArrayList<Map<String,?>>(othList)));
		}
		if (dtlList.size()>0) {
			parameters.put("DtlDataSource", new JRMapCollectionDataSource(new ArrayList<Map<String,?>>(dtlList)));
		}
		
		parameters.put("total", df.format(model.getTotal()));
		
		/*
		 * Data
		 */
		List<Map<String, Object>> listData = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> map = new HashMap<>();
		map.put("id", model.getId() != null ? model.getId() : "");
		
		Map<String,Object> empDtl = adminHrEmployeeService.getWithDtl(model.getReqBy());
		map.put("reqBy", StringUtil.replaceNBSP(empDtl.get("title"+lang)+" "+empDtl.get("first_name"+lang)+" "+empDtl.get("last_name"+lang)));
		map.put("reqOrg", StringUtil.replaceNBSP((String)empDtl.get("org_desc"+lang)));
		map.put("reqSection", StringUtil.replaceNBSP((String)empDtl.get("section_desc"+lang)));
		String mphone = StringUtils.defaultIfEmpty((String)empDtl.get("mobile_phone"),"");
		String wphone = StringUtils.defaultIfEmpty((String)empDtl.get("work_phone"),"");
		String comma = (!mphone.equals("") && !wphone.equals("")) ? "," : "";
		map.put("reqPhone", wphone+comma+mphone);
		
		empDtl = adminHrEmployeeService.getWithDtl(model.getCreatedBy()!=null ? model.getCreatedBy() : authService.getCurrentUserName());
		map.put("createdBy", StringUtil.replaceNBSP(empDtl.get("title"+lang)+" "+empDtl.get("first_name"+lang)+" "+empDtl.get("last_name"+lang)));
		map.put("createdTime", CommonDateTimeUtil.convertToGridDateTime(model.getCreatedTime()!=null?model.getCreatedTime():new Timestamp(Calendar.getInstance().getTimeInMillis())));
		mphone = StringUtils.defaultIfEmpty((String)empDtl.get("mobile_phone"),"");
		wphone = StringUtils.defaultIfEmpty((String)empDtl.get("work_phone"),"");
		comma = (!mphone.equals("") && !wphone.equals("")) ? "," : "";
		map.put("createdPhone", wphone+comma+mphone);
		
		Map<String, Object> firstAppMap = getFirstApprover(model.getId());
		log.info("model.id:"+model.getId());
		log.info("firstAppMap:"+firstAppMap);
		map.put("firstAppBy", firstAppMap != null ? firstAppMap.get("approver") : "");
		map.put("firstAppTime", firstAppMap != null ? CommonDateTimeUtil.convertToGridDateTime((Timestamp)firstAppMap.get("time")) : "");
		
		Map<String, Object> lastAppMap = getLastApprover(model.getId());
		log.info("lastAppMap:"+lastAppMap);
		map.put("lastAppBy", lastAppMap != null ? lastAppMap.get("approver") : "");
		map.put("lastAppTime", lastAppMap != null ? CommonDateTimeUtil.convertToGridDateTime((Timestamp)lastAppMap.get("time")) : "");
		
		map.put("objective", model.getObjective());
		map.put("reason", model.getReason());
		
		String budgetCcName = null;
		if (model.getBudgetCcType().equals(MainBudgetSrcConstant.TYPE_PROJECT)) {
			Map<String, Object> prjMap = adminProjectService.get(model.getBudgetCc());
			budgetCcName = "["+prjMap.get("code")+ "] "+prjMap.get("name"+lang);			
		} else {
			Map<String, Object> sectMap = adminSectionService.get(model.getBudgetCc());
			budgetCcName = (String)sectMap.get("description"+lang);
		}
		map.put("budgetCc", StringUtil.replaceNBSP(budgetCcName));
		
		Map<String, Object> fundMap = adminFundService.get(model.getFundId());
		map.put("fund", fundMap.get("name"));

		Map<String, Object> ccMap = adminCostControlService.get(model.getCostControlId());
		String ccName = ccMap!=null ? (String)ccMap.get("name") : null;
		
		map.put("costControl", StringUtils.defaultIfBlank(ccName,""));
		
		map.put("supName", model.getPayType().equals("1") ? model.getPayDtl1() : "");
		map.put("avCode", model.getPayType().equals("2") ? model.getPayDtl1() : "");
		
		map.put("pay1", RptUtil.radio(model.getPayType().equals("0")));
		map.put("pay2", RptUtil.radio(model.getPayType().equals("1")));
		map.put("pay3", RptUtil.radio(model.getPayType().equals("2")));
		map.put("pay4", RptUtil.radio(model.getPayType().equals("3")));
		map.put("pay5", RptUtil.radio(model.getPayType().equals("4")));
		
		Boolean useBank = model.getPayType().equals("0") || model.getPayType().equals("2");
		
		Map<String, Object> bankMap = adminBankMasterService.get(model.getBank());
		
		map.put("bank", bankMap!=null ? bankMap.get("name") : "");
		map.put("bank1", RptUtil.radio(useBank && model.getBankType().equals("0")));
		map.put("bank2", RptUtil.radio(useBank && model.getBankType().equals("1")));
		
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
	
	public String doGenPaymentDoc(String name, ExpUseModel model) throws Exception {
		
		String lang = "_th";
		
		DecimalFormat df = new DecimalFormat(CommonConstant.MONEY_FORMAT);
		
		List<Map<String, Object>> empList = new ArrayList<Map<String, Object>>();
		Map<String, Object> attendeeMap = null;
		
        List<ExpUseAttendeeModel> tmpAttendeeList = model.getAttendeeList();
        for(ExpUseAttendeeModel tmpAttendee : tmpAttendeeList) {
        	attendeeMap = new HashMap<String, Object>();
			
//        	attendeeMap.put("code", tmpAttendee.getCode());
        	attendeeMap.put("name", StringUtil.replaceNBSP(tmpAttendee.getTitle()+" "+tmpAttendee.getFname()+" "+tmpAttendee.getLname()));
//        	attendeeMap.put("section", StringUtil.replaceNBSP(tmpAttendee.getUnitType()));
        	attendeeMap.put("position", tmpAttendee.getPosition());
			
			if (tmpAttendee.getType().equals(ExpUseAttendeeConstant.T_EMPLOYEE)) {
				empList.add(attendeeMap);
			} else {
				empList.add(attendeeMap);
			}
        }
        
		/*
		 * Parameters
		 */
		Map parameters = new HashMap();
		parameters.put("imgPath", CommonConstant.GP_REPORT_IMG_PATH);
		
		if (empList.size()>0) {
			parameters.put("DtlDataSource", new JRMapCollectionDataSource(new ArrayList<Map<String,?>>(empList)));
		}
		
		parameters.put("total", df.format(model.getTotal()));
		
		/*
		 * Data
		 */
		List<Map<String, Object>> listData = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> map = new HashMap<>();
		
		Map<String,Object> empDtl = adminHrEmployeeService.getWithDtl(model.getCreatedBy()!=null ? model.getCreatedBy() : authService.getCurrentUserName());
		map.put("createdBy", StringUtil.replaceNBSP(empDtl.get("title"+lang)+" "+empDtl.get("first_name"+lang)+" "+empDtl.get("last_name"+lang)));
		map.put("createdTime", CommonDateTimeUtil.convertToGridDateTime(model.getCreatedTime()!=null?model.getCreatedTime():new Timestamp(Calendar.getInstance().getTimeInMillis())));
		map.put("position", StringUtil.replaceNBSP((String)empDtl.get("position"+lang)));
		map.put("objective", model.getObjective());
		
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
		String fullName = tmpDir + File.separator + fileName + ".xls";
			
		log.info("fullName="+fullName);			
			
//		JasperExportManager.exportReportToPdfFile(jasperPrint, fullName);
		
        JRXlsExporter xlsExporter = new JRXlsExporter();

        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fullName));
        SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
        xlsReportConfiguration.setOnePagePerSheet(false);
        xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
        xlsReportConfiguration.setDetectCellType(false);
        xlsReportConfiguration.setWhitePageBackground(false);
        xlsExporter.setConfiguration(xlsReportConfiguration);

        xlsExporter.exportReport();
			
		return fileName;
	}
	
	public List<FileModel> listFile(String id,final Boolean allFile) throws Exception {

		final ExpUseModel model = get(id, null);
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
		
		List<MainMasterModel> criList = masterService.listSystemConfig(MainMasterConstant.SCC_EXP_USE_CRITERIA);

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
		
		List<MainMasterModel> list = masterService.listSystemConfig(MainMasterConstant.SCC_EXP_USE_GRID_FIELD);

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
	
	private void createEcmFolder(ExpUseModel model) throws Exception {
		
		boolean exists = (model.getFolderRef()!=null) && fileFolderService.exists(new NodeRef(model.getFolderRef()));
		
		if (!exists) {
			JSONObject map = ExpUseUtil.convertToJSONObject(model,false);
			Calendar cal = Calendar.getInstance(Locale.US);
    		if (cal.get(Calendar.MONTH) >= 9) { // >= October (Thai Start Budget Year)
    			cal.add(Calendar.YEAR, 1);
    		}
    		Timestamp timestampValue = new Timestamp(cal.getTimeInMillis());
    		map.put(ExpUseConstant.JFN_FISCAL_YEAR, timestampValue);
			
			Iterator it = map.keys();
			while(it.hasNext()) {
				Object obj = it.next();
				log.info("--"+obj.toString());
			}
	
			Writer w = null;
			TemplateProcessor pc = templateService.getTemplateProcessor("freemarker");
			
			MainMasterModel pathFormatModel = masterService.getSystemConfig(MainMasterConstant.SCC_EXP_USE_PATH_FORMAT);
			String pathFormat = pathFormatModel.getFlag1();
			
			List<Object> paths = new ArrayList<Object>();
			
			String[] formats = pathFormat.split("/");
			for(String format : formats) {
				Map<String, Object> folderMap = new HashMap<String, Object>();
				paths.add(folderMap);
				
				int pos;
				if (format.indexOf(ExpUseConstant.JFN_FISCAL_YEAR) >= 0
					|| format.indexOf(ExpUseConstant.JFN_CREATED_TIME) >= 0
					|| format.indexOf(ExpUseConstant.JFN_UPDATED_TIME) >= 0
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
					
					SimpleDateFormat df = new SimpleDateFormat(dFormat, Locale.US);
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
			
			
			MainMasterModel siteModel = masterService.getSystemConfig(MainMasterConstant.SCC_EXP_USE_SITE_ID);
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
	
	private void setUserGroupFields(ExpUseModel model) throws Exception {
		/*
		 * Requester Group
		 */
//		MainApprovalMatrixModel apModel = adminApprovalMatrixService.get(model.getApprovalMatrixId());
//		model.setRequesterUser(apModel.getRequesterUser());
//		model.setRequesterGroup(apModel.getRequesterGroup());
	}
	
	public List<Map<String, Object>> list(Map<String, Object> params) {
		
		List<Map<String, Object>> list = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpUseDAO dao = session.getMapper(ExpUseDAO.class);
            log.info("exp use list param:"+params);
    		list = dao.list(params);

    		String lang = ((String)params.get("lang")).toUpperCase();
    		
    		for(Map<String,Object> map: list) {
    			map.put(ExpUseConstant.JFN_WF_STATUS, 
    					map.get(ExpUseConstant.TFN_WF_BY+lang)
    					+"-"+
    					map.get(ExpUseConstant.TFN_WF_STATUS+lang)
    					+"-"+
    					CommonDateTimeUtil.convertToGridDateTime((Timestamp)map.get(ExpUseConstant.TFN_WF_BY_TIME))
    			);
    			map.put(ExpUseConstant.JFN_BUDGET_CC_NAME,map.get(ExpUseConstant.TFN_BUDGET_CC_NAME+lang));
    			map.put(ExpUseConstant.JFN_CREATED_BY,map.get(ExpUseConstant.TFN_CREATED_BY+lang));
    			map.put(ExpUseConstant.JFN_REQ_BY,map.get(ExpUseConstant.TFN_REQ_BY+lang));
    			map.put(ExpUseConstant.JFN_PAY_TYPE_NAME,map.get(ExpUseConstant.TFN_PAY_TYPE_NAME+lang));
    			map.put(ExpUseConstant.JFN_CREATED_TIME_SHOW, CommonDateTimeUtil.convertToGridDateTime((Timestamp)map.get(ExpUseConstant.TFN_CREATED_TIME)));
    			
    			map.put(ExpUseConstant.JFN_ACTION, ExpUseUtil.getAction(map));
    			
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
	
	public List<ExpUseModel> listForSearch(Map<String, Object> params) {
		
		List<ExpUseModel> list = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpUseDAO dao = session.getMapper(ExpUseDAO.class);
            log.info("exp use list for search param:"+params);
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
            ExpUseDAO expUseDAO = session.getMapper(ExpUseDAO.class);
            ExpUseAttendeeDAO expUseAttendeeDAO = session.getMapper(ExpUseAttendeeDAO.class);
            ExpUseDtlDAO expUseDtlDAO = session.getMapper(ExpUseDtlDAO.class);
            MainWorkflowDAO workflowDAO = session.getMapper(MainWorkflowDAO.class);
            MainWorkflowHistoryDAO workflowHistoryDAO = session.getMapper(MainWorkflowHistoryDAO.class);
            MainWorkflowReviewerDAO workflowReviewerDAO = session.getMapper(MainWorkflowReviewerDAO.class);
            MainWorkflowNextActorDAO workflowNextActorDAO = session.getMapper(MainWorkflowNextActorDAO.class);

    		ExpUseModel model = get(id, null);
    		boolean exists = (model.getFolderRef()!=null) && fileFolderService.exists(new NodeRef(model.getFolderRef()));    		
    		if(exists) {
    			alfrescoService.deleteFileFolder(model.getFolderRef());
        	}

    		workflowNextActorDAO.deleteByMasterId(id);
    		workflowReviewerDAO.deleteByMasterId(id);
    		workflowHistoryDAO.deleteByMasterId(id);
    		workflowDAO.deleteByMasterId(id);
    		expUseAttendeeDAO.deleteByMasterId(id);
    		expUseDtlDAO.deleteByMasterId(id);
    		expUseDAO.delete(id);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
	}
	
	public ExpUseModel get(String id, String lang) {
		
		ExpUseModel model = null;
		
		lang = lang!=null && lang.startsWith("th") ? "_th" : "";
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpUseDAO expUseDAO = session.getMapper(ExpUseDAO.class);
            
            Map<String,Object> params = new HashMap<String,Object>();
            
            params.put("id", id);
            params.put("lang", lang);
            
    		model = expUseDAO.get(params);
    		model.setTotalRowCount(1l);
        } catch (Exception ex) {
			log.error("", ex);
        } finally {
        	session.close();
        }
        
        return model;
	}
	
	public List<Map<String, Object>> listAttendeeByMasterIdAndType(String masterId, String type, String lang) {
		
		List<Map<String, Object>> list = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpUseAttendeeDAO dao = session.getMapper(ExpUseAttendeeDAO.class);
            
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("masterId", masterId);
    		params.put("type", type);

    		list = dao.list(params);
    		
    		lang = (lang!=null && lang.startsWith("th") ? "_th" : "");
    		
    		for(Map<String, Object> map:list) {
    			if (map.get("code")!=null && !map.get("code").equals("")) {
    				map.put("title", map.get("etitle"+lang));
    				map.put("fname", map.get("efirst_name"+lang));
    				map.put("lname", map.get("elast_name"+lang));
    				map.put("position", map.get("eposition"+lang));
    				map.put("utype", "["+map.get("scode")+"] "+map.get("esection"+lang));
    				
    				map.remove("etitle");
    				map.remove("efirst_name");
    				map.remove("elast_name");
    				map.remove("eposition");
    				map.remove("esection");
    				
    				map = CommonUtil.removeThElement(map);
    			}
    		}
        } catch (Exception ex) {
			log.error("", ex);
        	throw ex;
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	public List<ExpUseDtlModel> listDtlByMasterId(String masterId) {
		
		List<ExpUseDtlModel> list = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpUseDtlDAO dao = session.getMapper(ExpUseDtlDAO.class);
            
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("masterId", masterId);

    		list = dao.list(map);
        } catch (Exception ex) {
			log.error("", ex);
        	throw ex;
        } finally {
        	session.close();
        }
        
        return list;
	}
	
//	public List<ExpUseAttendeeModel> listDtlByMasterIdAndFieldName(String masterId, String fieldName) {
//		
//		List<ExpUseAttendeeModel> list = null;
//		
//		SqlSession session = ExpUtil.openSession(dataSource);
//        try {
//            ExpUseAttendeeDAO dtlDAO = session.getMapper(ExpUseAttendeeDAO.class);
//            
//    		Map<String, Object> map = new HashMap<String, Object>();
//    		map.put("masterId", masterId);
//    		map.put("fieldName", fieldName);
//
//    		list = dtlDAO.list(map);
//        } catch (Exception ex) {
//			log.error("", ex);
//        	throw ex;
//        } finally {
//        	session.close();
//        }
//        
//        return list;
//	}
	
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
		
		if (dataMap.has(ExpUseConstant.JFN_ID)) {
			id = dataMap.getString(ExpUseConstant.JFN_ID);
			
			if (!dataMap.has(ExpUseConstant.JFN_CREATED_BY)) {
				ExpUseModel docModel = get(id, null);
				reqUser = docModel.getCreatedBy();
			}
			else {
				reqUser = dataMap.getString(ExpUseConstant.JFN_CREATED_BY);
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
		
		ExpUseModel model = (ExpUseModel)subModuleModel;
		
        SqlSession session = ExpUtil.openSession(dataSource);
        
        try {
            ExpUseDAO dao = session.getMapper(ExpUseDAO.class);
            
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
	
	public List<Map<String, Object>> listWorkflowPath(String id,String lang) {
		
		List<Map<String, Object>> list = mainWorkflowService.listWorkflowPath(id, lang);
		
		/*
		 * Add Preparer
		 */
		ExpUseModel model = get(id, null);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("LEVEL", mainWorkflowService.getTaskCaption(MainWorkflowConstant.TN_PREPARER, lang, null));
		map.put("U", model.getCreatedBy());
		map.put("G", "");
		map.put("IRA", false);
		
		list.add(0, map);
		
		/*
		 * Add Requester
		 */
		map = new HashMap<String, Object>();
		map.put("LEVEL", mainWorkflowService.getTaskCaption(MainWorkflowConstant.TN_REQUESTER, lang, null));
		map.put("U", model.getReqBy());
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
    		
    		for(Map<String, Object> actor : actorList) {
    			actor.put("LEVEL", mainWorkflowService.getTaskCaption((String)actor.get("LEVEL"),lang,null));
    		}
    		
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
			Map<String,Object> empModel = adminHrEmployeeService.getWithDtl(empCode);
			if (empModel!=null) {
				rec.put("U", empCode + " - " + empModel.get("first_name"+lang));
			} else {
				rec.put("U", "");
			}
		}
		
		return list;
	}

	@Override
	public String getWorkflowDescription(SubModuleModel paramModel) throws Exception {
		
		ExpUseModel expUseModel = (ExpUseModel)paramModel;
		
		MainMasterModel descFormatModel = masterService.getSystemConfig(MainMasterConstant.SCC_EXP_USE_WF_DESC_FORMAT);
		String descFormat = descFormatModel.getFlag1();
		
		JSONObject map = ExpUseUtil.convertToJSONObject(expUseModel,false);
		
		Writer w = null;
		TemplateProcessor pc = templateService.getTemplateProcessor("freemarker");
		w = new StringWriter();
		pc.processString(descFormat, map, w);
		
		return w.toString();
	}

	@Override
	public Map<String, Object> convertToMap(SubModuleModel model) {
		return ExpUseUtil.convertToMap((ExpUseModel)model, false);
	}

	@Override
	public String getWorkflowName() {
		return ExpUseConstant.WF_NAME;
	}
	
	@Override
	public String getSubModuleType() {
		return CommonConstant.SUB_MODULE_EXP_USE;
	}
	
	public String copy(String id) throws Exception {
		
        SqlSession session = ExpUtil.openSession(dataSource);
        
        ExpUseModel model = new ExpUseModel();
        
        try {
            ExpUseDAO expUseDAO = session.getMapper(ExpUseDAO.class);
            ExpUseAttendeeDAO expUseAttendeeDAO = session.getMapper(ExpUseAttendeeDAO.class);
            ExpUseDtlDAO expUseDtlDAO = session.getMapper(ExpUseDtlDAO.class);
            
            /*
             * Get Old Exp Use
             */
            ExpUseModel oModel = get(id,null);
            
            /*
             * Prepare New Exp Use
             */
            model.setStatus(ExpUseConstant.ST_DRAFT);
            
            Map<String, Object> reqBy = adminHrEmployeeService.getWithDtl(authService.getCurrentUserName());
            
        	model.setReqBy(authService.getCurrentUserName());
        	
        	model.setObjective(oModel.getObjective());
        	
        	model.setBudgetCcType(oModel.getBudgetCcType());
        	model.setBudgetCc(oModel.getBudgetCc());
        	model.setFundId(oModel.getFundId());
        	
        	model.setCostControlTypeId(oModel.getCostControlTypeId());
        	model.setCostControlId(oModel.getCostControlId());
        	model.setCostControl(oModel.getCostControl());
        	model.setCostControlFrom(oModel.getCostControlFrom());
        	model.setCostControlTo(oModel.getCostControlTo());
        	
        	model.setBankType(oModel.getBankType());
        	model.setBank(oModel.getBank());
        	
        	model.setPayType(oModel.getPayType());
        	if (!oModel.getPayType().equals("3")) { // clear employee AV
        		model.setPayDtl1(oModel.getPayDtl1());
        	}
        	model.setPayDtl2(oModel.getPayDtl2());
        	model.setPayDtl3(oModel.getPayDtl3());
        	
        	model.setTotal(oModel.getTotal());
        	
        	model.setRewarning(oModel.getRewarning());
        	model.setWaitingDay(oModel.getWaitingDay());
        	model.setWfStatus(oModel.getWfStatus());
            
    		model.setUpdatedBy(authService.getCurrentUserName());
    		model.setCreatedBy(model.getUpdatedBy());
    		
			/*
			 * Gen New ID
			 */
    		MainMasterModel masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_EXP_USE_ID_FORMAT);
    		String idFormat = masterModel.getFlag1();

        	Long runningNo = expUseDAO.getNewRunningNo();
        	
        	String newId = genNewId(model, idFormat, runningNo); // PR16000001
        	
    		model.setId(newId);
    		log.info("new id:"+newId);
    		
            /*
             * Create New Folder
             */
    		createEcmFolder(model);
    		
    		/*
    		 * Create New Exp Use 
    		 */
    		expUseDAO.add(model);
            
            /*
             * Create New Attendees
             */
    		List<ExpUseAttendeeModel> attendeeList = listAttendeeByMasterId(oModel.getId());
            for(ExpUseAttendeeModel vModel : attendeeList) {
            	vModel.setMasterId(model.getId());
	        	vModel.setCreatedBy(model.getUpdatedBy());
	        	vModel.setUpdatedBy(model.getUpdatedBy());
	        	
            	expUseAttendeeDAO.add(vModel);
            }
            
            /*
             * Create New Items
             */
    		List<ExpUseDtlModel> dtlList = listDtlByMasterId(oModel.getId());
            for(ExpUseDtlModel dtlModel : dtlList) {
            	dtlModel.setMasterId(model.getId());
	        	dtlModel.setCreatedBy(model.getUpdatedBy());
	        	dtlModel.setUpdatedBy(model.getUpdatedBy());
	        	
            	expUseDtlDAO.add(dtlModel);
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
		
		ExpUseModel model = (ExpUseModel)paramModel;
		
		/*
		 * Common Attribute
		 */
        parameters.put(ExpUseWorkflowConstant.PROP_ID, model.getId());
        parameters.put(ExpUseWorkflowConstant.PROP_FOLDER_REF, model.getFolderRef());

        parameters.put(ExpUseWorkflowConstant.PROP_DOCUMENT, (Serializable)docList);
        parameters.put(ExpUseWorkflowConstant.PROP_ATTACH_DOCUMENT, (Serializable)attachDocList);
//        parameters.put(ExpUseWorkflowConstant.PROP_COMMENT_HISTORY, "");
        
        parameters.put(ExpUseWorkflowConstant.PROP_TASK_HISTORY, "");
        
		/*
		 * Special Attribute
		 */
		parameters.put(ExpUseWorkflowConstant.PROP_OBJECTIVE, model.getObjective());
		parameters.put(ExpUseWorkflowConstant.PROP_TOTAL, model.getTotal());
		
//		Map<String, Object> map = null;
//		if (model.getBudgetCcType().equals(ExpUseConstant.BCCT_UNIT)) {
//			map = adminSectionService.get(model.getBudgetCc());
//			model.setBudgetCcName((String)map.get("name"));
//		} else {
//			map = adminProjectService.get(model.getBudgetCc());
//			model.setBudgetCcName((String)map.get("code")+" - "+(String)map.get("name"));
//		}
//		
//		parameters.put(ExpUseWorkflowConstant.PROP_BUDGET_CC, model.getBudgetCcName());
		
		parameters.put(ExpUseWorkflowConstant.PROP_BUDGET_CC, model.getBudgetCcType() + "," + model.getBudgetCc());
		
//		parameters.put(ExpUseWorkflowConstant.PROP_PAYMENT_TYPE, getPayTypeName(model));
		parameters.put(ExpUseWorkflowConstant.PROP_PAYMENT_TYPE, model.getPayType());
	}
	
	private String getPayTypeName(ExpUseModel model) {
		
		String name = null;
		
		if (model.getPayType()!=null) {
			MainMasterModel payTypeModel = masterService.getByTypeAndCode(MainMasterConstant.TYPE_EXP_EXP_TYPE, model.getPayType());
			name = payTypeModel.getName();
			
			if (model.getPayType().equals("1")) {
				name += " " + model.getPayDtl1();
			}
			else
			if (model.getPayType().equals("2")) {
				name += " " + model.getPayDtl1();
			}
			else
			if (model.getPayType().equals("3")) {
				String dtl = null;
				if (model.getPayDtl2().equals(MainBudgetSrcConstant.TYPE_UNIT)) {
					Map<String, Object> map = adminSectionService.get(Integer.parseInt(model.getPayDtl1()));
					dtl = map.get("code")+" - "+map.get("name");
				}
				else {
					Map<String, Object> map = adminProjectService.get(Integer.parseInt(model.getPayDtl1()));
					dtl = map.get("code")+" - "+map.get("name");
				}
				name += " " + dtl;
			}
		}
		
		return name;
	}

	@Override
	public String getActionCaption(String action, String lang) {
		StringBuffer sb = new StringBuffer();

		if (lang!=null && lang.indexOf("th")>=0) {
			sb.append(ExpUseConstant.WF_TASK_ACTIONS_TH.get(action));
		} else {
			sb.append(ExpUseConstant.WF_TASK_ACTIONS.get(action));
		}
		
		return sb.toString();
	}

	@Override
	public List<MainWorkflowNextActorModel> listNextActor(SubModuleModel model) {
		List<MainWorkflowNextActorModel> list = new ArrayList<MainWorkflowNextActorModel>();
		
//		ExpUseModel realModel = (ExpUseModel)model;
		
		MainWorkflowNextActorModel actorModel = new MainWorkflowNextActorModel();
		
		actorModel.setMasterId(model.getId());
		actorModel.setLevel(1);
		actorModel.setActor(MainWorkflowConstant.TN_FINANCE_OFFICER);
		actorModel.setActorUser(CommonConstant.DUMMY_EMPLOYEE_CODE);
		actorModel.setCreatedBy(model.getUpdatedBy());
		
		list.add(actorModel);
		
		return list;
	}

	@Override
	public String getFirstComment(SubModuleModel model) {
		ExpUseModel realModel = (ExpUseModel)model;
		
		return realModel.getObjective();
	}

	@Override
	public String getNextActionInfo(SubModuleModel model, String lang) {
		ExpUseModel exModel = (ExpUseModel)model;
		if (exModel!=null 
				&& (exModel.getStatus().equals(ExpUseConstant.ST_CANCEL_BY_FIN) 
				|| exModel.getStatus().equals(ExpUseConstant.ST_CANCEL_BY_REQ))
			) {
			return "";
		} else {
			return mainWorkflowService.getTaskCaption(MainWorkflowConstant.TN_FINANCE,lang,null);
		}
	}
	
	public List<Map<String, Object>> listForInf(String id) {
		
		List<Map<String, Object>> list = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpUseDAO dao = session.getMapper(ExpUseDAO.class);
            
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
            ExpUseDtlDAO dao = session.getMapper(ExpUseDtlDAO.class);
            
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
		return ExpUseWorkflowConstant.PROP_NEXT_REVIEWERS;
	}

	@Override
	public String getModelUri() {
		return ExpUseWorkflowConstant.MODEL_URI;
	}

	@Override
	public String getWfUri() {
		return ExpUseWorkflowConstant.WF_URI;
	}

	@Override
	public String getModelPrefix() {
		return ExpUseWorkflowConstant.MODEL_PREFIX;
	}

	@Override
	public Map<String, String> getBossMap(String docType, SubModuleModel subModuleModel) {
		
		ExpUseModel model = (ExpUseModel)subModuleModel;
		
		/*
		 * Search Original Boss List
		 */
		Map<String, String> tmpMap = new LinkedHashMap<String, String>();
		
		log.info("getBossMap()");
		log.info("  docType:'"+docType+"'");
		log.info("  budgetCcType:"+model.getBudgetCcType());
		log.info("  reqUser:"+model.getReqBy());
		log.info("  total:"+model.getTotal());
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
        	MainWkfCmdBossLevelApprovalDAO dao = session.getMapper(MainWkfCmdBossLevelApprovalDAO.class);
        	
        	Map<String, Object> params = new HashMap<String, Object>();
        	params.put("docType", docType);
        	if (model.getBudgetCcType().equals(ExpUseConstant.BCCT_UNIT)) {
        		params.put("sectionId", model.getBudgetCc());
        	} else {
        		// find project manager section id
        		List<Map<String, Object>> list = adminProjectService.listProjectManager(model.getBudgetCc());
        		Map<String, Object> map = list.get(0);
        		params.put("sectionId", map.get(MainHrEmployeeConstant.TFN_SECTION_ID));
        		
        		String pm = MainUserGroupUtil.code2login((String)map.get("employee_code"));
        		if (!model.getReqBy().equals(pm)) {
        			tmpMap.put("00", pm);
        		}
        	}
        	
        	List<Map<String, Object>> bossList = dao.listBoss(params);
        	log.info("  bossList"+bossList);
        	
        	if (model.getBudgetCcType().equals(ExpUseConstant.BCCT_UNIT)) {
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
	
	public Map<String, String> getUnitBossMap(ExpUseModel model, List<Map<String, Object>> bossList,Map<String, String> tmpMap ) throws Exception {
		
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

	public Map<String, String> getProjectBossMap(ExpUseModel model, List<Map<String, Object>> bossList,Map<String, String> tmpMap) throws Exception {
		
    	/*
    	 * Find Boss by money, if reqByCode not exist in workflow path
    	 */
    	Double total = model.getTotal();
    	log.info("total:"+total);
    	
    	/*
    	 * Case PM get special budget
    	 */
		String pm = tmpMap.get("00");
    	log.info("pm:"+pm);
    	
		if (pm!=null && !pm.equals(model.getReqBy())) {
			List<Map<String, Object>> list = adminProjectService.listPMSpecialBudget(model.getBudgetCc(),"EX");
			if (list!=null && list.size()>0) {
				Map<String, Object> map = list.get(0);
				log.info("min:"+map.get("amount_min")+",max:"+map.get("amount_max"));
				if (total <= (Double)map.get("amount_max")) {
					log.info("total<=max");
					return tmpMap;
				}
			}
		}
    	
		
		/*
		 * Other cases
		 */
    	
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
    	
    	Map<String, Object> reservedBoss = null;
    	Map<String, Object> lastBossMap = new HashMap<String, Object>();
    	lastBossMap.put("amount_max", (Double)0.0);
    	i = 0;
    	log.info("  start:"+start);
    	for(Map<String, Object> boss : bossList) {
    		
    		log.info("  "+boss.get("lvl")+" "+boss.get("employee_code")+" (amt:"+boss.get("amount_min")+"-"+boss.get("amount_max")+")");
    		
    		if (i>=start) {
    			if (total > (Double)lastBossMap.get("amount_max")) {
    				log.info("   add");
    				tmpMap.put((String)boss.get("lvl"), (String)boss.get("employee_code"));
    				lastBossMap = boss;
    			}
    			else
    			if (reservedBoss==null) {
    				log.info("   reserve");
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
    		tmpMap.remove(lastBossMap.get("lvl"));
    		if (reservedBoss!=null) {
    			tmpMap.put((String)reservedBoss.get("lvl"), MainUserGroupUtil.code2login((String)reservedBoss.get("employee_code")));
    		}
    		else {
    			// Waiting SA
    		}
    	}
    	
		/*
         * Remove duplicated Boss
         */
		tmpMap = removeDuplicatedBoss(tmpMap);
    	
    	/*
    	 * Remove Requester
    	 */
		List<String> rmList = new ArrayList<String>();
		
		for(Entry<String, String> e : tmpMap.entrySet()) {
			if (e.getValue().equals(model.getReqBy())) {
				rmList.add(e.getKey());
			}
		}

    	for(String k:rmList) {
    		tmpMap.remove(k);
    	}
    	
		return tmpMap;
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
		
        log.info("  map="+map);

        return map;
	}
	

	private Map<String, Object> getFirstApprover(String id) {
		Map<String, Object> map = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpUseDAO dao = session.getMapper(ExpUseDAO.class);
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
            ExpUseDAO dao = session.getMapper(ExpUseDAO.class);
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
		
		ExpUseModel model = (ExpUseModel)subModuleModel;
		
		list.add(model.getReqBy());
		
		return list;
	}

	@Override
	public String getDocDesc() {
		return "ใบเบิกจ่าย";
	}
	
	@Override
	public MainWorkflowHistoryModel getReqByWorkflowHistory(SubModuleModel subModuleModel) {
		MainWorkflowHistoryModel hModel = new MainWorkflowHistoryModel();
		
		ExpUseModel model = (ExpUseModel)subModuleModel;
		
		hModel.setTime(model.getCreatedTime());
		hModel.setLevel(0);
		hModel.setComment("");
		hModel.setAction("");
		hModel.setActionTh("");
		hModel.setTask(MainWorkflowConstant.WF_TASK_NAMES.get(MainWorkflowConstant.TN_REQUESTER));
		hModel.setTaskTh(MainWorkflowConstant.WF_TASK_NAMES_TH.get(MainWorkflowConstant.TN_REQUESTER));
		hModel.setBy(model.getReqBy());
		
		return hModel;
	}
	
	public List<Map<String, Object>> listPO(Map<String, Object> params) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "PO16000001");
		map.put("name", "PO16000001");
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("id", "PO16000002");
		map.put("name", "PO16000002");
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
	
	public List<Map<String, Object>> listAsset(Map<String, Object> params) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "ASO16000001");
		map.put("name", "AS16000001");
		list.add(map);
		
		map = new HashMap<String, Object>();
		map.put("id", "AS16000002");
		map.put("name", "AS16000002");
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

	@Override
	public MainWorkflowHistoryModel getAppByWorkflowHistory(SubModuleModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getSpecialUserForAddPermission(SubModuleModel model) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getFirstStatus() {
		return ExpUseConstant.ST_WAITING;
	}

	@Override
	public Boolean addPermissionToAttached() {
		return false;
	}
	
	public List<ExpUseAttendeeModel> listAttendeeByMasterId(String masterId) {
		
		List<ExpUseAttendeeModel> list = null;
		
		SqlSession session = ExpUtil.openSession(dataSource);
        try {
            ExpUseAttendeeDAO attDAO = session.getMapper(ExpUseAttendeeDAO.class);
            
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("masterId", masterId);

    		list = attDAO.listByMasterId(params);
    		
        } catch (Exception ex) {
			log.error("", ex);
        	throw ex;
        } finally {
        	session.close();
        }
        
        return list;
	}

	@Override
	public List<String> getSpecialGroupForAddPermission() {
		MainMasterModel topGroup = masterService.getSystemConfig(MainMasterConstant.SCC_EXP_USE_TOP_GROUP);
		
		String[] tgs = topGroup!=null && topGroup.getFlag1()!=null ? topGroup.getFlag1().split(",") : null;
		
		List<String> list = Arrays.asList(tgs);
		
		return list;
	}

	@Override
	public String getWorkflowDescriptionEn(SubModuleModel paramModel)
			throws Exception {
		ExpUseModel model = (ExpUseModel)paramModel;
		
		ExpUseModel enModel = new ExpUseModel();
		enModel.setId(model.getId());
		enModel.setObjective(model.getObjective());
		enModel.setStatus("");
		
		return getWorkflowDescription(enModel);
	}

	@Override
	public QName getPropDescEn() {
		return ExpUseWorkflowConstant.PROP_DESCRIPTION;
	}
	
}
