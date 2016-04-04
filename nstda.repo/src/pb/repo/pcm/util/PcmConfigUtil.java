package pb.repo.pcm.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.namespace.RegexQNamePattern;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PcmConfigUtil {
	
	private static Logger log = Logger.getLogger(PcmConfigUtil.class);
	
public static final String PATH_CONFIG = "/app:company_home/app:dictionary/cm:PB/cm:Pcm";
public static final String NAMESPACE_MEMF_SYSCONFIG = "{http://www.nstda.or.th/model/content/pcmconfig/1.0}";

public static final String MAIL_HOST = "mail.host";
public static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";

public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
public static final String MAIL_SMTP_PORT = "mail.smtp.port";
public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
public static final String MAIL_DEBUG = "mail.debug";

public static final String MAIL_SMTP_SOCKET_PORT = "mail.smtp.socketFactory.port";//May be not used
public static final String MAIL_SOCKET_CLASS = "mail.smtp.socketFactory.class";//May be not used
public static final String MAIL_SOCKET_FALLBACK = "mail.smtp.socketFactory.fallback";

public static final String MAIL_USERNAME = "mail.username";
public static final String MAIL_PASSWORD = "mail.password";
public static final String MAIL_WORKFLOW_TEMPLATE = "mail.iso.workflow.template";

@Autowired
private SearchService searchService;

@Autowired
private NodeService nodeService;

public void setSearchService(SearchService searchService) {this.searchService = searchService;}
public void setNodeService(NodeService nodeService) {this.nodeService = nodeService;}

public HashMap<String, Object> getConfiguration() throws Exception {
		log.info("Get Config :: " + NAMESPACE_MEMF_SYSCONFIG);
		HashMap<String, Object> configMap = new HashMap<String, Object>();
		
		NodeRef confNodeRef = this.getFolderNodeRefByXPath(PATH_CONFIG);
		
		if (confNodeRef == null) {
			log.info("Path not found : "+NAMESPACE_MEMF_SYSCONFIG);
			return null;
		}
		
		List<AssociationRef> refs = nodeService.getTargetAssocs(confNodeRef, RegexQNamePattern.MATCH_ALL);
		
		for (AssociationRef ref : refs)
        {
			String qname = ref.getTypeQName().toString();
			log.info(qname);
			if(qname.equals(NAMESPACE_MEMF_SYSCONFIG + "memoFolderStore"))
            {
	            NodeRef node = ref.getTargetRef();
	            configMap.put("memoFolderStore", node);
            }
			else if(qname.equals(NAMESPACE_MEMF_SYSCONFIG + "tempFolderStore"))
            {
	            NodeRef node = ref.getTargetRef();
	            configMap.put("tempFolderStore", node);
            }
			else if(qname.equals(NAMESPACE_MEMF_SYSCONFIG + "excelUserDetail"))
            {
	            NodeRef node = ref.getTargetRef();
	            configMap.put("excelUserDetail", node);
            }
			
			else if(qname.equals(NAMESPACE_MEMF_SYSCONFIG + "workflowEmailTemplate"))
            {
	            NodeRef node = ref.getTargetRef();
	            configMap.put("workflowEmailTemplate", node);
            }
			else if(qname.equals(NAMESPACE_MEMF_SYSCONFIG + "relatedPersonEmailTemplate"))
            {
	            NodeRef node = ref.getTargetRef();
	            configMap.put("relatedPersonEmailTemplate", node);
            }
			else if(qname.equals(NAMESPACE_MEMF_SYSCONFIG + "rewarningEmailTemplate"))
            {
	            NodeRef node = ref.getTargetRef();
	            configMap.put("rewarningEmailTemplate", node);
            }
			else if(qname.equals(NAMESPACE_MEMF_SYSCONFIG + "watermarkImage"))
            {
	            NodeRef node = ref.getTargetRef();
	            configMap.put("NAMESPACE_MEMF_SYSCONFIG", node);
            }
			else if(qname.equals(NAMESPACE_MEMF_SYSCONFIG + "signatureFolderStore"))
            {
	            NodeRef node = ref.getTargetRef();
	            log.info("node :: " + node.toString());
	            configMap.put("signatureFolderStore", node);
            }
			else if(qname.equals(NAMESPACE_MEMF_SYSCONFIG + "logoFolderStore"))
            {
	            NodeRef node = ref.getTargetRef();
	            configMap.put("logoFolderStore", node);
            }
        }
		
		boolean isWorkflowMailNotification = (Boolean)(nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"isWorkflowMailNotification")) != null ?
				nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"isWorkflowMailNotification")) : false);
		boolean isRelatedPersonMailNotification = (Boolean)(nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"isRelatedPersonMailNotification")) != null ?
				nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"isRelatedPersonMailNotification")) : false);
		
		boolean isRewarningMailNotification = (Boolean)(nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"isRewarningMailNotification")) != null ?
				nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"isRewarningMailNotification")) : false);
		
		int repeatMailWorkflow = nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"repeatMailWorkflow"))!=null?
				(Integer)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"repeatMailWorkflow")):0;
		
		String workflowMailSubject = (String)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"workflowMailSubject"));
		String workflowMailFromLabel = (String)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"workflowMailFromLabel"));
		
		String relatedPersonMailSubject = (String)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"relatedPersonMailSubject"));
		String relatedPersonMailFromLabel = (String)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"relatedPersonMailFromLabel"));
		
		String rewarningMailSubject = (String)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"rewarningMailSubject"));
		String rewarningMailFromLabel = (String)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"rewarningMailFromLabel"));
		
		//Mail Configuration
		configMap.put("isWorkflowMailNotification", isWorkflowMailNotification);
		configMap.put("isRelatedPersonMailNotification", isRelatedPersonMailNotification);
		configMap.put("repeatMailWorkflow", repeatMailWorkflow);
		
		configMap.put("workflowMailSubject", workflowMailSubject);
		configMap.put("workflowMailFromLabel", workflowMailFromLabel);
		configMap.put("relatedPersonMailSubject", relatedPersonMailSubject);
		configMap.put("relatedPersonMailFromLabel", relatedPersonMailFromLabel);
		
		configMap.put("isRewarningMailNotification", isRewarningMailNotification);
		configMap.put("rewarningMailSubject", rewarningMailSubject);
		configMap.put("rewarningMailFromLabel", rewarningMailFromLabel);
		
		String watermarkImagePosition = (String)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"watermarkImagePosition"));
		String watermarkText = (String)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"watermarkText"));
		String watermarkTextPosition= (String)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"watermarkTextPos"));
		
		int watermarkTextSize = nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"watermarkTextSize"))!=null?
								(Integer)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"watermarkTextSize")):0;
								
		int watermarkTextRotate = nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"watermarkTextRotate"))!=null?
								(Integer)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"watermarkTextRotate")):0;
								
		String watermarkTextColor= (String)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"watermarkTextColor"));
		
		configMap.put("watermarkImagePosition", watermarkImagePosition);
		configMap.put("watermarkText", watermarkText);
		configMap.put("watermarkTextPosition", watermarkTextPosition);
		configMap.put("watermarkTextSize", watermarkTextSize);
		
		String reviewer1SignPosition= (String)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"reviewer1SignPosition"));
		String reviewer2SignPosition= (String)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"reviewer2SignPosition"));
		String reviewer3SignPosition= (String)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"reviewer3SignPosition"));
		String reviewer4SignPosition= (String)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"reviewer4SignPosition"));
		String reviewer5SignPosition= (String)nodeService.getProperty(confNodeRef, QName.createQName(NAMESPACE_MEMF_SYSCONFIG+"reviewer5SignPosition"));
		
		configMap.put("watermarkTextRotate", watermarkTextRotate);
		configMap.put("watermarkTextColor", watermarkTextColor);
		configMap.put("reviewer1SignPosition", reviewer1SignPosition);
		configMap.put("reviewer2SignPosition", reviewer2SignPosition);
		configMap.put("reviewer3SignPosition", reviewer3SignPosition);
		configMap.put("reviewer4SignPosition", reviewer4SignPosition);
		configMap.put("reviewer5SignPosition", reviewer5SignPosition);
		
		return configMap;
	}

	public NodeRef getFolderNodeRefByXPath(String xPath){
		NodeRef nodeRef = null;
		StoreRef storeRef = new StoreRef(StoreRef.PROTOCOL_WORKSPACE, "SpacesStore");
		ResultSet rs = null;
		try {
			rs = searchService.query(storeRef, SearchService.LANGUAGE_XPATH, xPath);
			if(rs.length() > 0){
				for(int i = 0; i < rs.length(); i++){
					if(!rs.getNodeRef(i).getId().equalsIgnoreCase("missing")){
						return rs.getNodeRef(i); 
					}
				}
			}
		}
		catch (Throwable e) {
			System.err.println(e.getMessage());
		}
		finally {
		if (rs != null){
			  rs.close();
		  }
		}
		return nodeRef;
	}
	
	public static Properties getGlobalProperties(){
    	log.info("getGlobalProperties");
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    	log.info(classLoader.toString());
		InputStream inputStream = classLoader.getResourceAsStream("alfresco-global.properties");
		log.info(inputStream);
		Properties properties = new Properties();
		
		try {
			properties.load(inputStream);
		} catch (IOException ex) {
		     log.error("", ex);
		} finally {
			 if (inputStream != null) {
			    try {
			       inputStream.close();
			    } catch (IOException e) {
			       log.error("", e);
			    }
			 }
		}
		return properties;
    }
	
}
