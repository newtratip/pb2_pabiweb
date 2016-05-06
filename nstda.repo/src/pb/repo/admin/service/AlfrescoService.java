package pb.repo.admin.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.MimetypeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.Path;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.cmr.version.Version;
import org.alfresco.service.cmr.version.VersionService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.common.util.CommonUtil;

@Service
public class AlfrescoService {
	
	private static Logger log = Logger.getLogger(AlfrescoService.class);

	@Autowired
	ContentService contentService;
	
	@Autowired
	NodeService nodeService;
	
	@Autowired
	FileFolderService fileFolderService;
	
	@Autowired
	MimetypeService mimeTypeService;
	
	@Autowired
	PermissionService permissionService;
	
	@Autowired
	VersionService versionService;
	
    public ContentReader getContentByNodeRef(final NodeRef nodeRef) {
		
		ContentReader contentReader = AuthenticationUtil.runAs(new RunAsWork<ContentReader>()
			    {
					public ContentReader doWork() throws Exception
					{
						return contentService.getReader(nodeRef, ContentModel.PROP_CONTENT);
					}
			    }, AuthenticationUtil.getAdminUserName());
		
		return contentReader;

	}
    
    public NodeRef searchSimple(final NodeRef parentNodeRef, final String name) {
		
		NodeRef nodeRef = AuthenticationUtil.runAs(new RunAsWork<NodeRef>()
			    {
					public NodeRef doWork() throws Exception
					{
						return fileFolderService.searchSimple(parentNodeRef,  name);
					}
			    }, AuthenticationUtil.getAdminUserName());
		
		return nodeRef;

	}
    
    public NodeRef createDoc(NodeRef parentFolder, Object obj, String ecmFileName) throws Exception {
//		byte[] fileContent = IOUtils.toByteArray(new FileInputStream(file));
//		
//		InputStream inputStream = new ByteArrayInputStream(fileContent);
		FileInfo fileInfo = fileFolderService.create(parentFolder, ecmFileName, ContentModel.TYPE_CONTENT);
	    NodeRef newNode = fileInfo.getNodeRef();

	    String fileName = null;
        if (obj instanceof File) {
        	fileName = ((File)obj).getName();
        } else {
            fileName = ecmFileName;
        }
	    
	    String ext = FilenameUtils.getExtension(fileName);
	    String mimeType = mimeTypeService.getMimetypesByExtension().get(ext);
        log.info("Mimetype : "+mimeType);

        ContentWriter contentWriter = contentService.getWriter(newNode, ContentModel.PROP_CONTENT, true);
        contentWriter.setEncoding("UTF-8");
        contentWriter.setMimetype(mimeType);
        
        InputStream is = null;
        if (obj instanceof File) {
        	is = new FileInputStream((File)obj);
        } else {
            is = (InputStream)obj;
        }
        
        contentWriter.putContent(is);

        return newNode;
    }
    
    public String getFolderPath(NodeRef folderRef) throws Exception {
    	Path path = nodeService.getPath(folderRef);
    	String parentPath = path.toDisplayPath(nodeService, permissionService);
    	log.info("parentPath:"+parentPath);
    	
    	int pos = parentPath.indexOf("/", 1);
    	
    	FileInfo fileInfo = fileFolderService.getFileInfo(folderRef);
    	
    	String fullPath = parentPath.substring(pos) + "/" + fileInfo.getName();
    	
    	return URLEncoder.encode(fullPath, StandardCharsets.UTF_8.toString()); 
    }
    
    public void deleteFileFolder(String nodeRefStr) {
		
    	if (nodeRefStr!=null && !nodeRefStr.equals("")) {
	    	final NodeRef nodeRef = new NodeRef(nodeRefStr);
			AuthenticationUtil.runAs(new RunAsWork<String>()
		    {
				public String doWork() throws Exception
				{
					fileFolderService.delete(nodeRef);
					return null;
				}
		    }, AuthenticationUtil.getAdminUserName());
    	}
	}
    
	public NodeRef getWorkingCopyNodeRef(String srcNodeRef){
		log.info("srcNodeRef="+srcNodeRef);
		Map<QName,Serializable> propss = null;
//		Map<QName,Serializable> propss = nodeService.getProperties(new NodeRef(srcNodeRef));
//		for(Entry<QName, Serializable> e:propss.entrySet()) {
//			log.info("  src : "+e.getValue()+"  :  "+e.getKey());
//		}
		
//		List<ChildAssociationRef> nodes = nodeService.getChildAssocs(new NodeRef(srcNodeRef));
//		for (ChildAssociationRef element : nodes) {
//			log.info("  asso : "+element.getChildRef());
//			propss = nodeService.getProperties(element.getChildRef());
//			for(Entry<QName, Serializable> e:propss.entrySet()) {
//				log.info("         "+e.getValue()+"  :  "+e.getKey());
//			}
//		}
//		
//		log.info("****** source asso ****");
//		List<AssociationRef> assos = nodeService.getSourceAssocs(new NodeRef(srcNodeRef), ContentModel.ASSOC_WORKING_COPY_LINK);
//		for (AssociationRef element : assos) {
//			log.info("  t asso s : "+element.getSourceRef());
//			propss = nodeService.getProperties(element.getSourceRef());
//			for(Entry<QName, Serializable> e:propss.entrySet()) {
//				log.info("         "+e.getValue()+"  :  "+e.getKey());
//			}
//			log.info("  t asso t : "+element.getTargetRef());
//			propss = nodeService.getProperties(element.getTargetRef());
//			for(Entry<QName, Serializable> e:propss.entrySet()) {
//				log.info("         "+e.getValue()+"  :  "+e.getKey());
//			}
//		}
//		
		NodeRef nodeRef = null;
		
		log.info("****** target asso ****");
		List<AssociationRef> assos = nodeService.getTargetAssocs(new NodeRef(srcNodeRef), ContentModel.ASSOC_WORKING_COPY_LINK);
		for (AssociationRef element : assos) {
			log.info("  t asso s : "+element.getSourceRef());
			propss = nodeService.getProperties(element.getSourceRef());
			for(Entry<QName, Serializable> e:propss.entrySet()) {
				log.info("         "+e.getValue()+"  :  "+e.getKey());
			}
			log.info("  t asso t : "+element.getTargetRef());
			propss = nodeService.getProperties(element.getTargetRef());
			for(Entry<QName, Serializable> e:propss.entrySet()) {
				log.info("         "+e.getValue()+"  :  "+e.getKey());
			}
			nodeRef = element.getTargetRef();
		}
		
		return nodeRef;
	}
	
	public String getDocExt(String name) {
		int pos = name.lastIndexOf(".");
		return name.substring(pos+1);
	}
	
	public void downloadContent(NodeRef nodeRef, String fileName) throws Exception {
		ContentReader contentReader = contentService.getReader(nodeRef, ContentModel.PROP_CONTENT);
	    InputStream is = contentReader.getContentInputStream();

		BufferedInputStream in = null;
        FileOutputStream fout = null;
        try
        {
            in = new BufferedInputStream(is);
            fout = new FileOutputStream(fileName, false);

            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1)
            {
                fout.write(data, 0, count);
            }
        }
        catch (Exception ex) {
        	log.error("",ex);
        }
        finally
        {
            if (in != null)
                in.close();
            if (fout != null)
                fout.close();
        }
	}
	
	public void convertOfficeToPdf(String fileName, String pdfPath) {
		String oooExe = CommonUtil.getGlobalProperty("ooo.exe");
		
		String[] cmd = {oooExe, "--headless", "--convert-to", "pdf", "--outdir", pdfPath, fileName};
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			process.waitFor();
			
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
	}

	public String getVersionLabel(NodeRef nodeRef) {
		final Version version = versionService.getCurrentVersion(nodeRef);
		return AuthenticationUtil.runAs(new RunAsWork<String>()
			    {
					public String doWork() throws Exception
					{
						return version!=null ? version.getVersionLabel() : null;
					}
			    }, AuthenticationUtil.getAdminUserName());
	}
}
