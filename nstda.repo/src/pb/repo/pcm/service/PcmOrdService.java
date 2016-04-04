package pb.repo.pcm.service;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.sql.DataSource;

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
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.common.constant.CommonConstant;
import pb.common.constant.FileConstant;
import pb.common.model.FileModel;
import pb.common.util.DocUtil;
import pb.common.util.FolderUtil;
import pb.common.util.ImageUtil;
import pb.common.util.PersonUtil;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminUserGroupService;
import pb.repo.admin.service.AlfrescoService;
import pb.repo.admin.service.MainSrcUrlService;
import pb.repo.pcm.constant.PcmOrdConstant;
import pb.repo.pcm.dao.PcmOrdDAO;
import pb.repo.pcm.dao.PcmOrdDtlDAO;
import pb.repo.pcm.dao.PcmOrdReviewerDAO;
import pb.repo.pcm.dao.PcmOrdWorkflowDAO;
import pb.repo.pcm.dao.PcmOrdWorkflowHistoryDAO;
import pb.repo.pcm.model.PcmOrdDtlModel;
import pb.repo.pcm.model.PcmOrdModel;
import pb.repo.pcm.model.PcmOrdReviewerModel;
import pb.repo.pcm.model.PcmOrdWorkflowHistoryModel;
import pb.repo.pcm.util.PcmOrdDtlUtil;
import pb.repo.pcm.util.PcmOrdUtil;
import pb.repo.pcm.util.PcmUtil;

@Service
public class PcmOrdService {

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
	PcmOrdWorkflowService pcmOrdWorkflowService;
	
	public PcmOrdModel save(PcmOrdModel model, String dtls, String files, boolean genDoc) throws Exception {
		
        SqlSession session = PcmUtil.openSession(dataSource);
        
        try {
            PcmOrdDAO pcmOrdDAO = session.getMapper(PcmOrdDAO.class);
            PcmOrdDtlDAO pcmOrdDtlDAO = session.getMapper(PcmOrdDtlDAO.class);
            
            setUserGroupFields(model);
            
    		model.setUpdatedBy(authService.getCurrentUserName());
    		
            if (model.getId() == null) {
        		model.setCreatedBy(model.getUpdatedBy());
        		
    			/*
    			 * Gen New ID
    			 */
        		MainMasterModel masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_PCM_ORD_ID_FORMAT);
        		String idFormat = masterModel.getFlag1();

        		String idPrefix = genNewId(model, idFormat, 1l, true); // genPrefix=true -> BY201508-
        		log.info("idPrefix:"+idPrefix);
        		
        		Map<String, Object> params = new HashMap<String, Object>();
        		params.put("idPrefix", idPrefix+"%");
        		
//        		Map<String, Object> params = new HashMap<String, Object>();
//        		params.put("f2", model.getField2()); // Supalai : f2=project
//        		String newId = pcmReqDAO.genNewId(params);
        		String newId = null;
        		String rawLastId = pcmOrdDAO.getLastId(params);
        		log.info("rawLastId:"+rawLastId);
        		if (rawLastId == null) {
//        			SimpleDateFormat df = new SimpleDateFormat("YYYYMM");
//        			newId = model.getField2() + df.format(new Date()) + "-001";
        			
            		newId = genNewId(model, idFormat, 1l, false); // genPrefix=false -> BY201508-001
        		}
        		else {
            		String lastId = rawLastId.replace(idPrefix, "");
            		Long id = Long.parseLong(lastId)+1;
            		
            		newId = genNewId(model, idFormat, id, false); // genPrefix=false -> BY201508-(lastId+1)
        		}
        		model.setId(newId);
        		log.info("new id:"+newId);
            	doCommonSaveProcess(model, genDoc, files, dtls);
            	
        		/*
        		 * Add DB
        		 */
            	pcmOrdDAO.add(model);
            }
            else {
            	doCommonSaveProcess(model, genDoc, files, dtls);
            	
            	/*
            	 * Update DB
            	 */
            	pcmOrdDAO.update(model);
            	
            	pcmOrdDtlDAO.deleteByMasterId(model.getId());
            }
            
            List<PcmOrdDtlModel> dtlList = PcmOrdDtlUtil.convertJsonToList(dtls, model.getId());
            for(PcmOrdDtlModel dtlModel : dtlList) {
	        	dtlModel.setCreatedBy(model.getUpdatedBy());
	        	dtlModel.setUpdatedBy(model.getUpdatedBy());
	        	
            	pcmOrdDtlDAO.add(dtlModel);
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
	
	public String save(PcmOrdModel model) throws Exception {
		
        SqlSession session = PcmUtil.openSession(dataSource);
        
        try {
            PcmOrdDAO pcmReqDAO = session.getMapper(PcmOrdDAO.class);
            
    		model.setUpdatedBy(authService.getCurrentUserName());
    		
    		NodeRef folderNodeRef = new NodeRef(model.getFolderRef());
    		model = genDoc(model, folderNodeRef);
        	
        	/*
        	 * Update DB
        	 */
        	pcmReqDAO.update(model);
            
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
	
	private String genNewId(PcmOrdModel pcmReqModel, String oriFormat, Long runningNo, boolean genPrefix) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();

		int newFieldIndex = 0;
		
		boolean done = oriFormat.indexOf(PcmOrdConstant.JFN_CREATED_TIME)<0
					&& oriFormat.indexOf(PcmOrdConstant.JFN_REQUESTED_TIME)<0
					&& oriFormat.indexOf(PcmOrdConstant.JFN_UPDATED_TIME)<0
					&& oriFormat.indexOf(PcmOrdConstant.JFN_RUNNING_NO)<0
					;
		while (!done) {
    		if (oriFormat.indexOf(PcmOrdConstant.JFN_CREATED_TIME) >= 0) {
	    		Timestamp timestampValue = pcmReqModel.getCreatedTime()!=null ? pcmReqModel.getCreatedTime() : new Timestamp(Calendar.getInstance().getTimeInMillis());
	    		oriFormat = handleThaiDate("", PcmOrdConstant.JFN_CREATED_TIME, model, oriFormat, newFieldIndex, timestampValue, templateService, false);
				model.put(PcmOrdConstant.JFN_CREATED_TIME,  getDateDefaultFormatValue(timestampValue));
    		}
    		else
    		if (oriFormat.indexOf(PcmOrdConstant.JFN_UPDATED_TIME) >= 0) {
	    		Timestamp timestampValue = pcmReqModel.getUpdatedTime()!=null ? pcmReqModel.getUpdatedTime() : new Timestamp(Calendar.getInstance().getTimeInMillis());
	    		oriFormat = handleThaiDate("", PcmOrdConstant.JFN_UPDATED_TIME, model, oriFormat, newFieldIndex, timestampValue, templateService, false);
				model.put(PcmOrdConstant.JFN_UPDATED_TIME,  getDateDefaultFormatValue(timestampValue));
    		}
    		else
    		if (oriFormat.indexOf(PcmOrdConstant.JFN_REQUESTED_TIME) >= 0) {
	    		Timestamp timestampValue = pcmReqModel.getRequestedTime()!=null ? pcmReqModel.getRequestedTime() : new Timestamp(Calendar.getInstance().getTimeInMillis());
	    		oriFormat = handleThaiDate("", PcmOrdConstant.JFN_REQUESTED_TIME, model, oriFormat, newFieldIndex, timestampValue, templateService, false);
				model.put(PcmOrdConstant.JFN_REQUESTED_TIME,  getDateDefaultFormatValue(timestampValue));
    		}
    		else
    		if (oriFormat.indexOf(PcmOrdConstant.JFN_RUNNING_NO) >= 0) {
    			if (genPrefix) {
    				oriFormat = deleteRunningNoFromFormat(PcmOrdConstant.JFN_RUNNING_NO, oriFormat);
    			} else {
    				oriFormat = handleRunningNo(PcmOrdConstant.JFN_RUNNING_NO, model, oriFormat, newFieldIndex, runningNo);
    				model.put(PcmOrdConstant.JFN_RUNNING_NO,  runningNo);
    			}
    		}
    		
			newFieldIndex++;
			done = oriFormat.indexOf(PcmOrdConstant.JFN_CREATED_TIME)<0
				&& oriFormat.indexOf(PcmOrdConstant.JFN_REQUESTED_TIME)<0
				&& oriFormat.indexOf(PcmOrdConstant.JFN_UPDATED_TIME)<0
				&& oriFormat.indexOf(PcmOrdConstant.JFN_RUNNING_NO)<0
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
	
	public void updateStatus(PcmOrdModel model) throws Exception {
		
        SqlSession session = PcmUtil.openSession(dataSource);
        
        try {
            PcmOrdDAO pcmReqDAO = session.getMapper(PcmOrdDAO.class);
          
            pcmReqDAO.updateStatus(model);
            
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
	
	private void doCommonSaveProcess(PcmOrdModel model, boolean genDoc, String files, String dtls) throws Exception {
		
    	/*
    	 * Create ECM Folder
    	 */
		createEcmFolder(model);
		
		NodeRef folderNodeRef = new NodeRef(model.getFolderRef());
		
		/*
		 * Gen Memo Doc
		 */
		if (genDoc) {
			model = genDoc(model, folderNodeRef);
		}
    	
    	/*
    	 * save files
    	 */
		File file;
		String path;
		
		List<String> oldList = new ArrayList<String>();
		Map<String, String> newMap = new HashMap<String, String>();
		
		/*
		 * Separate Old and New Files
		 */
    	JSONArray jsArr = new JSONArray(files);
    	for(int i=0; i<jsArr.length(); i++) {
    		JSONObject jsObj = jsArr.getJSONObject(i);
    		log.info("  file:"+jsObj.getString(FileConstant.JFN_NAME)
    							   +", "+jsObj.getString(FileConstant.JFN_PATH)
    							   +", "+jsObj.getString(FileConstant.JFN_NODE_REF));
    		
    		path = jsObj.getString(FileConstant.JFN_PATH);
    		if (path!=null && !path.equals("") && !path.equals("null")) {
    			newMap.put(jsObj.getString(FileConstant.JFN_NAME), path);
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
    		if (!doc.getQName().getLocalName().equals(model.getId()+".pdf")) { // not is memo.pdf
	    		if (oldList.indexOf(doc.getChildRef().toString()) < 0) {
	    			
			    	log.info("  is checked out:"+doc.getChildRef().toString());
				    if (checkOutCheckInService.isCheckedOut(doc.getChildRef())) {
				    	log.info("    true");
				    	final NodeRef wNodeRef = alfrescoService.getWorkingCopyNodeRef(doc.getChildRef().toString());
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
    	for(Entry<String, String> e : newMap.entrySet()) {
    		log.info(e.getKey()+":"+e.getValue());
    		file = new File(FolderUtil.getTmpDir()+File.separator+e.getValue()+File.separator+e.getKey());
	    	if (file.exists()) {
	    		NodeRef docRef = alfrescoService.createDoc(folderNodeRef, file, e.getKey());
	    		file.delete();
		    	model.setAttachDoc(docRef.toString());
	    	}
    	}
	}
	
	public PcmOrdModel genDoc(PcmOrdModel model, NodeRef folderNodeRef) throws Exception {
		/*
		 * Gen Memo Doc in tmp folder
		 */
    	String fileName =  doGenDoc("");
		String tmpDir = FolderUtil.getTmpDir();
//    	String fullName = tmpDir + File.separator + fileName+".pdf"; // real code
    	String fullName = tmpDir + File.separator + "PD_FORM.pdf"; // for Demo
    	File file = new File(fullName);
    	String ecmFileName = model.getId()+".pdf";
    	
    	log.info("Gen Memo Doc : "+ecmFileName);
    	/*
    	 * Put Memo Doc in ECM
    	 */
    	NodeRef oldDocRef = alfrescoService.searchSimple(folderNodeRef, ecmFileName);
    	if (oldDocRef != null) {
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

    		alfrescoService.deleteFileFolder(oldDocRef.toString());
    	}
    	NodeRef docRef = alfrescoService.createDoc(folderNodeRef, file, ecmFileName);
    	
    	/*
    	 * Delete File from tmp folder 
    	 */
//    	if (file.exists()) {
//    		file.delete();
//    	}
    	
    	/*
    	 * Update docRef field
    	 */
    	model.setDocRef(docRef.toString());
    	
    	return model;
	}
	
	public String doGenDoc(String content1) throws Exception {
		DocUtil docUtil = new DocUtil();
		String tmpDir = FolderUtil.getTmpDir();
		String fileName = docUtil.genUniqueFileName();
		String fullName = tmpDir + File.separator + fileName;
		
		log.info("fullName="+fullName);
		
		Path outputPath = Paths.get(fullName + ".html");
		try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
			writer.write("<html><head><meta charset=\"UTF-8\"><head><body>");

			writer.write(content1);
			
			writer.write("</body><html>");
			writer.flush();
			writer.close();
			/*
			 * Convert html to pdf
			 */
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fullName", fullName);
			
			MainMasterModel masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_PAGE_MARGIN_T);
			params.put("top", masterModel!=null ? masterModel.getFlag1() : null);
			masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_PAGE_MARGIN_B);
			params.put("bottom", masterModel!=null ? masterModel.getFlag1() : null);
			masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_PAGE_MARGIN_L);
			params.put("left", masterModel!=null ? masterModel.getFlag1() : null);
			masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_PAGE_MARGIN_R);
			params.put("right", masterModel!=null ? masterModel.getFlag1() : null);
			
			masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_HEADER_LEFT);
			params.put("h_left", masterModel!=null ? masterModel.getFlag1() : null);
			masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_HEADER_RIGHT);
			params.put("h_right", masterModel!=null ? masterModel.getFlag1() : null);
			masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_HEADER_CENTER);
			params.put("h_center", masterModel!=null ? masterModel.getFlag1() : null);
			masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_HEADER_SPACING);
			params.put("h_spacing", masterModel!=null ? masterModel.getFlag1() : null);
			masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_HEADER_FONT_NAME);
			params.put("h_font_name", masterModel!=null ? masterModel.getFlag1() : null);
			masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_HEADER_FONT_SIZE);
			params.put("h_font_size", masterModel!=null ? masterModel.getFlag1() : null);
			
			masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_FOOTER_LEFT);
			params.put("f_left", masterModel!=null ? masterModel.getFlag1() : null);
			masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_FOOTER_RIGHT);
			params.put("f_right", masterModel!=null ? masterModel.getFlag1() : null);
			masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_FOOTER_CENTER);
			params.put("f_center", masterModel!=null ? masterModel.getFlag1() : null);
			masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_FOOTER_SPACING);
			params.put("f_spacing", masterModel!=null ? masterModel.getFlag1() : null);
			masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_FOOTER_FONT_NAME);
			params.put("f_font_name", masterModel!=null ? masterModel.getFlag1() : null);
			masterModel = masterService.getSystemConfig(MainMasterConstant.SCC_MEMO_FOOTER_FONT_SIZE);
			params.put("f_font_size", masterModel!=null ? masterModel.getFlag1() : null);
			
			docUtil.convertHtmlToPdf(params);
			
			/*
			 * Delete html file
			 */
			File file = new File(fullName+".html");
			file.delete();
		}
		catch (Exception ex) {
			log.error("", ex);
			throw ex;
		}
		
		return fileName;
	}
	
	public List<FileModel> listFile(String id) throws Exception {

		final PcmOrdModel model = get(id);
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
			}
			
			jsArr.put(jsObj);
		}
		
		return jsArr;
	}
	
	private void createEcmFolder(PcmOrdModel model) throws Exception {
		
		boolean exists = (model.getFolderRef()!=null) && fileFolderService.exists(new NodeRef(model.getFolderRef()));
		
		if (!exists) {
			// format : ${field2}/${field6}/${year}/${id}
			JSONObject memoMap = PcmOrdUtil.convertToJSONObject(model,false);
	
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
				if (format.indexOf("requested_time") >= 0
					|| format.indexOf("created_time") >= 0
					|| format.indexOf("updated_time") >= 0
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
					folderMap.put("name", df.format(memoMap.get(fieldName)));
				}	
				else {
					pos = format.indexOf("[");
					if (pos >= 0) {
						int pos2 = format.indexOf("]");
						
						String descFieldName = format.substring(pos+1, pos2);
						w = new StringWriter();
						pc.processString("${"+descFieldName+"}", memoMap, w);
						folderMap.put("desc", w.toString());
						w.close();
						
						format = format.replace("["+descFieldName+"]","");
					}
					
					w = new StringWriter();
					pc.processString(format, memoMap, w);
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
	
	public List<PcmOrdModel> list(Map<String, Object> params) {
		
		List<PcmOrdModel> list = null;
		
		SqlSession session = PcmUtil.openSession(dataSource);
        try {
            PcmOrdDAO dao = session.getMapper(PcmOrdDAO.class);
            log.info("pcm req list param:"+params);
    		list = dao.list(params);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        	throw ex;
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	public void deleteReviewerByMasterId(String id) throws Exception {
		SqlSession session = PcmUtil.openSession(dataSource);
        try {
            PcmOrdReviewerDAO pcmReqReviewerDAO = session.getMapper(PcmOrdReviewerDAO.class);
            pcmReqReviewerDAO.deleteByMasterId(id);
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
	}
	
	public void delete(String id) throws Exception {
		
		SqlSession session = PcmUtil.openSession(dataSource);
        try {
            PcmOrdDAO pcmReqDAO = session.getMapper(PcmOrdDAO.class);
            PcmOrdReviewerDAO pcmReqReviewerDAO = session.getMapper(PcmOrdReviewerDAO.class);
            PcmOrdDtlDAO pcmReqDtlDAO = session.getMapper(PcmOrdDtlDAO.class);
            PcmOrdWorkflowDAO pcmOrdWorkflowDAO = session.getMapper(PcmOrdWorkflowDAO.class);
            PcmOrdWorkflowHistoryDAO pcmOrdWorkflowHistoryDAO = session.getMapper(PcmOrdWorkflowHistoryDAO.class);

    		PcmOrdModel memoModel = get(id);
    		alfrescoService.deleteFileFolder(memoModel.getFolderRef());
    		
    		pcmOrdWorkflowHistoryDAO.deleteByPcmOrdId(id);
    		pcmOrdWorkflowDAO.deleteByMasterId(id);
    		pcmReqReviewerDAO.deleteByMasterId(id);
    		pcmReqDtlDAO.deleteByMasterId(id);
    		pcmReqDAO.delete(id);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
	}
	
	public PcmOrdModel get(String id) {
		
		PcmOrdModel model = null;
		
		SqlSession session = PcmUtil.openSession(dataSource);
        try {
            PcmOrdDAO pcmOrdDAO = session.getMapper(PcmOrdDAO.class);
            
    		model = pcmOrdDAO.get(id);
    		model.setTotalRowCount(1l);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
        
        return model;
	}
	
	public List<PcmOrdDtlModel> listDtlByMasterId(String masterId) {
		
		List<PcmOrdDtlModel> list = null;
		
		SqlSession session = PcmUtil.openSession(dataSource);
        try {
            PcmOrdDtlDAO dtlDAO = session.getMapper(PcmOrdDtlDAO.class);
            
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("masterId", masterId);

    		list = dtlDAO.list(map);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        	throw ex;
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	public List<PcmOrdDtlModel> listDtlByMasterIdAndFieldName(String masterId, String fieldName) {
		
		List<PcmOrdDtlModel> list = null;
		
		SqlSession session = PcmUtil.openSession(dataSource);
        try {
            PcmOrdDtlDAO dtlDAO = session.getMapper(PcmOrdDtlDAO.class);
            
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("masterId", masterId);
    		map.put("fieldName", fieldName);

    		list = dtlDAO.list(map);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
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
		
		if (dataMap.has(PcmOrdConstant.JFN_ID)) {
			id = dataMap.getString(PcmOrdConstant.JFN_ID);
			
			if (!dataMap.has(PcmOrdConstant.JFN_CREATED_BY)) {
				PcmOrdModel memoModel = get(id);
				reqUser = memoModel.getCreatedBy();
			}
			else {
				reqUser = dataMap.getString(PcmOrdConstant.JFN_CREATED_BY);
			}
			
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("masterId", id);
			params.put("orderDesc", "");
			List<PcmOrdWorkflowHistoryModel> hList = pcmOrdWorkflowService.listHistory(params);

			
			
			PersonUtil personUtil = new PersonUtil();
			personUtil.setNodeService(nodeService);
			
			Set<Integer> lvlSet = new HashSet<Integer>();
			
			if (hList!=null) {
				boolean wfClosed = false;
				for (PcmOrdWorkflowHistoryModel hModel : hList) {
					if (hModel.getAction().indexOf("ปิดงาน") >= 0) {
						wfClosed = true;
						break;
					}
				}
				
				if (wfClosed) {
					for (PcmOrdWorkflowHistoryModel hModel : hList) {
						Integer lvl = hModel.getLevel();
						if ((hModel.getAction().indexOf("อนุมัติ") >= 0 || hModel.getAction().indexOf("ปิดงาน") >= 0)
								&& !lvlSet.contains(lvl)
							) {
							
							String action = hModel.getAction().indexOf("ไม่") >= 0 ? "ไม่อนุมัติ" : "อนุมัติ";
							String reason = hModel.getComment();
							String sign = "${S_APP_SIGN_"+lvl+"}";
							dataMap.put("S_APP_SIGN_"+lvl, hModel.getUser_());
							imageMap.put("S_APP_SIGN_"+lvl,"S_APP_SIGN_"+lvl);
							String name = personUtil.getFullName(PersonUtil.getPerson(hModel.getUser_(), personService));
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
	
	private String handleSystemImage(String rptFormat) throws Exception  {
		/*
		 * Expected : ${S_IMAGE_1}
		 * 			: ${S_IMAGE_1?["100,100"]} 
		 */
		
		/*
		 * prefix
		 */
		String prefix = CommonConstant.REPS_PREFIX
				   + PcmOrdConstant.RPTSV_IMAGE
				   + "_"
				   ; // prefix = ${S_IMAGE_
		int prePos = rptFormat.indexOf(prefix);
		
		/*
		 * While found prefix or prefixF in rptFormat , replace pattern with image
		 */
		String displayFormat = null;
		String pattern = null;
		String suffix = null;
		int sufPos;
		int imgNo;
		
		while (prePos >= 0) {
			displayFormat = null;
			
    		sufPos = rptFormat.indexOf(CommonConstant.REPS_SUFFIX, prePos);
    		
    		pattern = rptFormat.substring(prePos, sufPos + CommonConstant.REPS_SUFFIX.length());
    		suffix = rptFormat.substring(prePos + prefix.length(), sufPos + CommonConstant.REPS_SUFFIX.length());
    		
    		int formatPos = suffix.indexOf(CommonConstant.FMTS_PREFIX);
    		if (formatPos >= 0) {
    			prePos = suffix.indexOf(CommonConstant.FMTS_PREFIX);
    			imgNo = Integer.parseInt(suffix.substring(0, prePos));
    			
    			sufPos = suffix.indexOf(CommonConstant.FMTS_SUFFIX);
    			displayFormat = suffix.substring(prePos + CommonConstant.FMTS_PREFIX.length(), sufPos);
    		} else {
    			sufPos = suffix.indexOf(CommonConstant.REPS_SUFFIX);
    			imgNo = Integer.parseInt(suffix.substring(0, sufPos));
    		}
    		
    		MainMasterModel imgModel = masterService.getSystemConfig(MainMasterConstant.SCC_PCM_ORD_IMAGE+"_"+imgNo);

    		boolean hasValue = imgModel!=null;
    		String value = null;
    		if (hasValue) {
        		value = imgModel.getFlag1();
    			hasValue = (value != null) && !value.toString().equals("");
    		}
    		
    		/*
    		 * Prepare imgStr if found pattern in rptFormat
    		 */
			StringBuffer imgTag = new StringBuffer();
			
    		if (hasValue) {
    			NodeRef imgNodeRef = new NodeRef(value.toString());
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
			
    		rptFormat = rptFormat.replace(pattern, imgTag.toString());
    		
			prePos = rptFormat.indexOf(prefix);
		}
		
		return rptFormat;
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
	        		SimpleDateFormat dateFormat = new SimpleDateFormat(format.replace("&nbsp;"," "));
	        		
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
	
	private String deleteRunningNoFromFormat(String fieldName, String rptFormat) throws Exception  {
		
		String pattern = CommonConstant.REPS_PREFIX
					   + fieldName
					   + CommonConstant.FMTS_PREFIX
					   ;
		
		int prePos = rptFormat.indexOf(pattern);
		while (prePos >= 0) {
    		int sufPos = rptFormat.indexOf(CommonConstant.FMTS_SUFFIX, prePos);
    		
    		rptFormat = rptFormat.substring(0, prePos) 
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
	
	public void addReviewer(PcmOrdReviewerModel pcmReqReviewerModel) throws Exception {
		
		SqlSession session = PcmUtil.openSession(dataSource);
        try {
           
            PcmOrdReviewerDAO pcmReqReviewerDAO = session.getMapper(PcmOrdReviewerDAO.class);

    		pcmReqReviewerDAO.add(pcmReqReviewerModel);

            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
	}
	
	public PcmOrdReviewerModel getReviewer(PcmOrdReviewerModel pcmReqReviewerModel) throws Exception {
		
		PcmOrdReviewerModel model = null;
		
		SqlSession session = PcmUtil.openSession(dataSource);
        try {
           
            PcmOrdReviewerDAO pcmReqReviewerDAO = session.getMapper(PcmOrdReviewerDAO.class);

    		List<PcmOrdReviewerModel> list = pcmReqReviewerDAO.listByLevel(pcmReqReviewerModel);
    		if (list.size()>0) {
    			model = list.get(0);
    		}
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
        
        return model;
	}	
	
	public List<Map<String, Object>> listWorkflowPath(String id) {
		
		List<Map<String, Object>> list = null;
		
		SqlSession session = PcmUtil.openSession(dataSource);
        try {
           
            PcmOrdDAO dao = session.getMapper(PcmOrdDAO.class);

    		list = dao.listWorkflowPath(id);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
        
        return list;
	}
	
}
