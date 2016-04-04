package pb.repo.admin.service;

import java.io.File;

import javax.sql.DataSource;

import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.common.util.CommonUtil;

@Service
public class AdminViewerService {
	
	private static Logger log = Logger.getLogger(AdminViewerService.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	private FileFolderService fileFolderService;
	
	@Autowired
	private AdminUserGroupService userGroupService;
	
	@Autowired
	AdminMasterService masterService;
	
	@Autowired
	AlfrescoService alfrescoService;
	
	@Autowired
	NodeService nodeService;
	
	@Autowired
	ContentService contentService;
	
	public void prepareForViewer(NodeRef docRef) throws Exception {
		log.info("prepareForViewer("+docRef.toString()+")");
		
		try {
		
			String pdfPath = CommonUtil.getGlobalProperty("pdf.path");
			String docPath = CommonUtil.getGlobalProperty("doc.path");
			String swfExe = CommonUtil.getGlobalProperty("swf.exe");
			
			String versionLabel = alfrescoService.getVersionLabel(docRef);
			if (versionLabel == null) {
				versionLabel = "0.0";
			}
			
			/*
			 * Get File Extension
			 */
			FileInfo fileInfo = fileFolderService.getFileInfo(docRef);
			String name = fileInfo.getName();
			String ext = alfrescoService.getDocExt(name);
			String doc = docRef.getId();
			if (!ext.equals("pdf")) {
				log.info("*****************************");
				log.info("*** Convert Office to PDF ***");
				log.info("*****************************");
				
				String officePath = CommonUtil.getGlobalProperty("office.path");
				String iFileName = officePath + doc + "_" + versionLabel + "." + ext;
				
				File iFile = new File(iFileName);
				if (!iFile.exists()) {
					alfrescoService.downloadContent(docRef, iFileName);
				}
				
				alfrescoService.convertOfficeToPdf(iFileName, pdfPath);
			}
			
			String iFileName = pdfPath + doc + "_" + versionLabel + ".pdf";
			String oFileNameGen = docPath + doc + "_" + versionLabel + ".pdf_%.swf";

			log.info("iFileName="+iFileName);
			
			File iFile = new File(iFileName);
//			if (!iFile.exists()) {
				alfrescoService.downloadContent(docRef, iFileName);
//			}
			
//			int pageInt = Integer.parseInt(page);
//			String[] cmd = {swfExe, iFileName, "-o" , oFileNameGen,"-p",pageInt+"-"+(pageInt+1),"-f","-T","9","-t","-s","storeallcharacters","-s","linknameurl"};
			String[] cmd = {swfExe, iFileName, "-o" , oFileNameGen,"-f","-T","9","-t","-s","storeallcharacters","-s","linknameurl"};
			Process process = Runtime.getRuntime().exec(cmd);
			
			try {
				process.waitFor();
			} catch (Exception ex) {
				log.error("",ex);
			}
		} catch (Exception ex) {
			log.error("",ex);
		}
	}
	
}
