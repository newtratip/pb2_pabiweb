package pb.repo.pcm.schedule;


import java.io.Serializable;
import java.net.URLEncoder;
import java.security.Security;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.repo.template.DateCompareMethod;
import org.alfresco.repo.template.HasAspectMethod;
import org.alfresco.repo.template.I18NMessageMethod;
import org.alfresco.repo.template.TemplateNode;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.Path;
import org.alfresco.service.cmr.repository.Path.Element;
import org.alfresco.service.cmr.repository.TemplateService;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.cmr.security.PersonService.PersonInfo;
import org.alfresco.service.cmr.workflow.WorkflowInstance;
import org.alfresco.service.cmr.workflow.WorkflowService;
import org.alfresco.service.cmr.workflow.WorkflowTask;
import org.alfresco.service.cmr.workflow.WorkflowTaskQuery;
import org.alfresco.service.cmr.workflow.WorkflowTaskState;
import org.alfresco.service.namespace.QName;
import org.alfresco.util.ISO9075;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;

import pb.common.constant.CommonConstant;
import pb.repo.common.jdbc.JdbcService;
import pb.repo.pcm.util.PcmConfigUtil;

import com.sun.mail.smtp.SMTPMessage;

public class PcmScheduleRewarningWorkflowMail extends ActionExecuterAbstractBase{
	
	private static Logger log = Logger.getLogger(PcmScheduleRewarningWorkflowMail.class);
	
protected NodeService nodeService;
private SearchService searchService;
private PersonService personService;
private TemplateService templateService;
private AuthenticationService authService;
private ServiceRegistry serviceRegistry;
private FileFolderService fileFolderService;
protected WorkflowService workflowService;
private AuthorityService authorityService;

@Override
protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
	paramList.add(new ParameterDefinitionImpl("a-parameter", DataTypeDefinition.TEXT, false, getParamDisplayLabel("a-parameter")));		
}

public void setNodeService(NodeService nodeService){this.nodeService = nodeService;}
public void setSearchService(SearchService searchService){this.searchService = searchService;}
public void setPersonService(PersonService personService){this.personService = personService;}
public void setTemplateService(TemplateService templateService){this.templateService = templateService;}
public void setAuthenticationService(AuthenticationService authService){this.authService = authService;}
public void setServiceRegistry(ServiceRegistry serviceRegistry){this.serviceRegistry = serviceRegistry;}
public void setFileFolderService(FileFolderService fileFolderService) {this.fileFolderService = fileFolderService;}
public void setWorkflowService(WorkflowService workflowService) {this.workflowService = workflowService;}
public void setAuthorityService(AuthorityService authorityService){this.authorityService = authorityService;}
final String workflowUri = "{http://www.nstda.or.th/model/workflow/pcmwf/1.0}";
	@Override
	protected void executeImpl(final Action action, final NodeRef actionedUponNodeRef){
		
	try {

		log.info("--- | Workflow Mail Notification(Rewarning Schedule) | ---");
		
		PcmConfigUtil utils = new PcmConfigUtil();
		utils.setNodeService(nodeService);
		utils.setSearchService(searchService);
		HashMap<String, Object> memoConfMap = utils.getConfiguration();
		
		if (memoConfMap == null) {
			return;
		}

		boolean isRewarningMailNotification = (boolean)memoConfMap.get("isRewarningMailNotification");
		
		if(isRewarningMailNotification){
			
			List<WorkflowInstance> wfInstances = workflowService.getActiveWorkflows();
			List<NodeRef> userList = new ArrayList<NodeRef>();
			
			JdbcService jService = new JdbcService();
			List<String> wfNames = jService.listAvailableWorkflowNames();;
			
			for (WorkflowInstance wf : wfInstances)
	        {
			
				if(wfNames.contains(wf.getDefinition().getName()))
	    		{
					log.info("WF Definition ID :: " +  wf.getDefinition().getId());
					log.info("WF Instance ID :: " +  wf.getId());
				
					WorkflowTaskQuery query = new WorkflowTaskQuery();
					query.setActive(true);
					query.setTaskState(WorkflowTaskState.IN_PROGRESS);
					query.setProcessId(wf.getId());
				
					List<WorkflowTask> tasks = this.workflowService.queryTasks(query, true);
					log.info("tasks size :: " +  tasks.size());
					
					//List<String> sendTo = null;
					Map<String, Object> model = null;
					
					QName assigneeQName = QName.createQName("{http://www.alfresco.org/model/content/1.0}owner");
					
					for (WorkflowTask task : tasks)
					{
						log.info("task.name :: " + task.getName());
						
						model = createEmailTemplateModel(task);
						
						Map<QName, Serializable> prop = task.getProperties();
						
						for(QName qName : prop.keySet()){
							
							log.debug("QNAME :: " + qName + " >> " + prop.get(qName));
							
							if(prop.get(qName) instanceof String){
								log.debug("QNAME STRING:: " + qName.getLocalName() + " >> " + prop.get(qName));
								model.put(qName.getLocalName().toString(), prop.get(qName));
							}else if(prop.get(qName) instanceof Date){
								log.debug("  QNAME DATE:: " + qName.getLocalName() + " >> " + prop.get(qName));
								model.put(qName.getLocalName().toString(), prop.get(qName));
							}else{
								log.debug("QNAME :: " + qName.getLocalName() + " >> " + prop.get(qName));
								model.put(qName.getLocalName().toString(), prop.get(qName));
							}
							
						}

						int lvl = 0;

						String currentTaskKey = (String)model.get("currentTaskKey");
						log.info("task.currentTaskKey :: " + currentTaskKey);
						
						if(task.getName().equalsIgnoreCase("memwf:reviewer1ReviewTask")
						   || currentTaskKey.startsWith("reviewer")
						   ){
							
							if(task.getName().equalsIgnoreCase("memwf:reviewer1ReviewTask")){
								lvl = 1;
							} else {
								String iStr = currentTaskKey.substring(8, currentTaskKey.length()-4);
								lvl = Integer.parseInt(iStr);
							}
							
							log.info("  level :: "+lvl);
							
							Object rewarningObj = ObjectUtils.defaultIfNull(model.get("rewarning"+lvl), "");
							Integer rewarning = !rewarningObj.equals("")?Integer.valueOf(rewarningObj.toString()):0;
							log.info("  rewarning  :: "+ rewarning);
							
							if(rewarning > 0) {
								
								Date startDate = null;
								
								if(lvl == 1){
									startDate = (Date) model.get("startDate");
								} else {
									startDate = (Date) model.get("reviewer"+String.valueOf(lvl-1)+"SignDate");
								}
								
								log.info("  startDate :: "+startDate);
								Calendar c = Calendar.getInstance();
								
								c.setTime(startDate);
								c.add(Calendar.DATE, rewarning);
								c.set(Calendar.HOUR_OF_DAY, 0);
								c.set(Calendar.MINUTE, 0);
								c.set(Calendar.SECOND, 0);
								c.set(Calendar.MILLISECOND, 0);
								Date rewarningDate = c.getTime();
								
								
								c.setTime(new Date());
								c.set(Calendar.HOUR_OF_DAY, 0);
								c.set(Calendar.MINUTE, 0);
								c.set(Calendar.SECOND, 0);
								c.set(Calendar.MILLISECOND, 0);
								Date now =  c.getTime();
								
								log.info("  rewarningDate  :: "+ rewarningDate);
								log.info("  now  :: "+ now);
								log.info("  rewarningDate before now ?  :: "+ rewarningDate.before(now));
								
								if(rewarningDate.before(now) && rewarning > 0){
									userList.clear();
									
									String assignee = task.getProperties().get(assigneeQName)!=null ?
											  		  task.getProperties().get(assigneeQName).toString() : "";
									NodeRef assigneeNodeRef = personService.getPerson(assignee);
									userList.add(assigneeNodeRef);
									
									NodeRef emailTemplate = (NodeRef)memoConfMap.get("rewarningEmailTemplate");
					    			String workflowMailSubject = (String)memoConfMap.get("rewarningMailSubject");
					    			String workflowMailFromLabel = (String)memoConfMap.get("rewarningMailFromLabel");
					    			
					    			this.setMailNotify(task, userList, emailTemplate, workflowMailFromLabel, workflowMailSubject);
								}
							}
						}
					}
	    		}
	        }
			
		}
		
		} catch (Exception ex) {
			log.error("", ex);
		}
	}
	
	public void setMailNotify(WorkflowTask task, List<NodeRef> userList, NodeRef mailTemplate, String fromLabel, String subjectLabel)
	{
		log.info("Start Send Mail.........");
		
		List<String> usersList = new ArrayList<String>();
		List<String> usersEmailList = new ArrayList<String>();
		
		for (NodeRef user : userList)
    	{
            PersonInfo person = personService.getPerson(user);
            log.info("User:" + user.getId());
            log.info("Person.getFirstName():" + person.getFirstName());
            usersList.add(person.getFirstName());
            String mail = GetMailByNodeRef(user);
            usersEmailList.add(mail);
			log.info("**** EMAIL : usersEmailList : "+mail);
    	}
		
		try{
			final Properties gProp = PcmConfigUtil.getGlobalProperties();
			Properties props = new Properties();
			props = getMailProperties(gProp);
			
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator()
			{ 
				@Override
				protected PasswordAuthentication getPasswordAuthentication() 
				{
					return new PasswordAuthentication(gProp.getProperty(PcmConfigUtil.MAIL_USERNAME),
							gProp.getProperty(PcmConfigUtil.MAIL_PASSWORD));	
				}
			});
			
			Map<String, Object> model = createEmailTemplateModel(task);
			
			String shareProtocol = gProp.getProperty(CommonConstant.GP_SHARE_PROTOCOL);
			String shareHost = gProp.getProperty(CommonConstant.GP_SHARE_HOST);
			String sharePort = gProp.getProperty(CommonConstant.GP_SHARE_PORT);
			String shareUrl = shareProtocol + "://" +shareHost+":"+sharePort;
			String taskId = task.getId();
			
			// Loop for get all property from task
			Map<QName, Serializable> taskProps = task.getProperties();
			Iterator iterator = taskProps.entrySet().iterator();
			
			while(iterator.hasNext()){
				Map.Entry mapEntry = (Map.Entry) iterator.next();
				QName qName = (QName)mapEntry.getKey();
				
				if(qName.getLocalName().equalsIgnoreCase("folderRef")){
					
					String folderUrl = "";
					
					if(fileFolderService.exists(new NodeRef(mapEntry.getValue().toString()))){
						
						Path path = nodeService.getPath(new NodeRef(mapEntry.getValue().toString()));
						String pathStr = path.toString();
						//log.info("path 1="+path.toString());
						
						String site = null;
						boolean foundSite = false;
						for(Element el:path) {
							log.info("element:"+el.getElementString()+" -- "+el.toString());
							String e = el.toString();
							
							if (e.endsWith("}sites")) {
								foundSite = true;
								continue;
							}
							if (foundSite) {
								int pos = e.indexOf("}");
								site = e.substring(pos+1);
								foundSite = false;
							}
						}
						
						int pos = pathStr.indexOf("documentLibrary/");
						pathStr = pathStr.substring(pos+15);
						
						pos = pathStr.indexOf("{");
						int pos2 = pathStr.indexOf("}");
						while(pos >=0 && pos2>=0) {
							String p = pathStr.substring(0, pos) + pathStr.substring(pos2+1);
							
							pathStr = p;
							
							pos = pathStr.indexOf("{");
							pos2 = pathStr.indexOf("}");
						}
						//log.info("path 2="+pathStr);
						pathStr = ISO9075.decode(pathStr);
						//log.info("path 3="+pathStr);
						
						String pathNormalized = "";
						String s = Normalizer.normalize(pathStr, Normalizer.Form.NFD);
						for(int i=0;i<s.length();++i) {
							String h = Integer.toHexString((int)s.charAt(i));
							if (h.length() == 3) {
								pathNormalized += "%u0" + h.toUpperCase();
							}
							else {
								pathNormalized += s.charAt(i);
							}
						}
						
						//log.info("path 4="+pathNormalized);
						log.info("path 5="+URLEncoder.encode(pathNormalized));
						folderUrl = "page/site/"+site+"/documentlibrary#filter=path%7C"+ URLEncoder.encode(pathNormalized) + "%7C";
						
					}
					model.put(qName.getLocalName().toString(), folderUrl);
				}
				else
				{
					model.put(qName.getLocalName().toString(), mapEntry.getValue());
					
				}
				
				log.info("VARIABLE: " + qName.getLocalName().toString() + "  VALUE:  " + mapEntry.getValue());
				
			}
			
			String emails = usersEmailList.toString().replace("[", "").replace("]", "");
			String users = usersList.toString().replace("[", "").replace("]", "");
			
			model.put("shareUrl", shareUrl);
			model.put("taskId", taskId);
			model.put("toAssignee", users);
			
			String workflowMailSubject = generateTextFromProperties(subjectLabel, model);
			String workflowMailFromLabel = generateTextFromProperties(fromLabel, model); 
			
			log.info("WorkflowMailSubject:" + workflowMailSubject);
			log.info("WorkflowMailFromLabel:" + workflowMailFromLabel);
			
			String text = templateService.processTemplate("freemarker", mailTemplate.toString(), model);
			session.setDebug(gProp.getProperty(PcmConfigUtil.MAIL_DEBUG)=="true"?true:false);
			
			Transport transport;
			InternetAddress addressFrom = null;
			transport = session.getTransport();
			addressFrom = new InternetAddress(gProp.getProperty(PcmConfigUtil.MAIL_USERNAME), workflowMailFromLabel, "UTF-8");
			SMTPMessage message = new SMTPMessage(session);
			message.setFrom(addressFrom);
			message.setSender(addressFrom);
			message.setSubject(workflowMailSubject, "UTF-8");
			
			MimeMultipart multipart = new MimeMultipart("mixed");
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(text, "text/html;charset=UTF-8");
			
			multipart.addBodyPart(textPart);
			message.setContent(multipart);
			
			// -------------------------- Add to recipient --------------------------------
			log.info("**** EMAIL : emails : "+emails);

			
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emails, false));
			message.saveChanges();
			transport.connect();
			Transport.send(message);
			transport.close();
			
			log.info("Sent Mail Successfully!!!");
			
	    }
		catch (Exception ex) {
			Map<QName, Serializable> prop = new HashMap<QName, Serializable>();
			prop.put(QName.createQName(workflowUri + "mailNotifyCount"), 1);
			workflowService.updateTask(task.getId(), prop, null, null);
			log.error("Error Start to Sent Email",ex);
		}
	}
	
	String generateTextFromProperties(String text, Map<String, Object> model){
		
		log.info("generateTextFromProperties...:" + text);
		
		if(text.matches("(.*)\\{(.*)\\}(.*)")){
			String field = text.substring(text.indexOf("{")+1, text.indexOf("}"));
			String _filename = field;
			
			if(model.containsKey(field)){
				if(model.get(field)!=null){
					_filename = model.get(field).toString();
				}
			}
			
			text = text.replace("${"+field+"}", _filename);
			text = generateTextFromProperties(text, model);
		}
		return text;
	}
	
	Properties getMailProperties(Properties gProp){
		
		Properties props = new Properties();
		
		props.setProperty(PcmConfigUtil.MAIL_HOST, gProp.getProperty(PcmConfigUtil.MAIL_HOST));
		
		props.setProperty(PcmConfigUtil.MAIL_TRANSPORT_PROTOCOL, gProp.getProperty(PcmConfigUtil.MAIL_TRANSPORT_PROTOCOL)); 
		props.put(PcmConfigUtil.MAIL_SMTP_AUTH, gProp.getProperty(PcmConfigUtil.MAIL_SMTP_AUTH));
		props.put(PcmConfigUtil.MAIL_SMTP_PORT, gProp.getProperty(PcmConfigUtil.MAIL_SMTP_PORT));
		props.put(PcmConfigUtil.MAIL_DEBUG, true);
		props.setProperty("mail.smtp.dsn.notify", "SUCCESS,FAILURE,DELAY");
		props.setProperty("mail.smtp.reportsuccess", "false");
		
		if(gProp.getProperty(PcmConfigUtil.MAIL_HOST).equals("smtp.gmail.com")){
			props.put(PcmConfigUtil.MAIL_SMTP_SOCKET_PORT, gProp.getProperty(PcmConfigUtil.MAIL_SMTP_SOCKET_PORT));
			props.put(PcmConfigUtil.MAIL_SOCKET_CLASS, gProp.getProperty(PcmConfigUtil.MAIL_SOCKET_CLASS));
		}
		
		props.put(PcmConfigUtil.MAIL_SOCKET_FALLBACK, gProp.getProperty(PcmConfigUtil.MAIL_SOCKET_FALLBACK));
		return props;
		
	}
	
	private String GetMailByNodeRef(final NodeRef personNodeRef){
		
		log.info("**** EMAIL : GetMailByNodeRef : personNodeRef : "+personNodeRef.getId());

		String email = "";
		
		if(nodeService.getProperty(personNodeRef, ContentModel.PROP_EMAIL)!= null && 
				!nodeService.getProperty(personNodeRef, ContentModel.PROP_EMAIL).equals("")){
		
			email = (String)nodeService.getProperty(personNodeRef, ContentModel.PROP_EMAIL);
		}
		return email;
	
	}
	
	private Map<String, Object> createEmailTemplateModel(WorkflowTask task)
	{
		Map<String, Object> model = new HashMap<String, Object>(8, 1.0f);
		//List<Map<String, Object>> docListModels = new ArrayList<Map<String, Object>>();
		
		NodeRef person = personService.getPerson(authService.getCurrentUserName());
		model.put("person", new TemplateNode(person, serviceRegistry, null));
		model.put("date", new Date());
		
		// add custom method objects
		model.put("hasAspect", new HasAspectMethod());
		model.put("message", new I18NMessageMethod());
		model.put("dateCompare", new DateCompareMethod());
		
		for (int i=1; i<=CommonConstant.MAX_APPROVER; i++) {
			Object rewarningString = task.getProperties().get(QName.createQName("{http://www.nstda.or.th/model/workflow/pcmwf/1.0}rewarning"+i))!=null?
					  task.getProperties().get(QName.createQName("{http://www.nstda.or.th/model/workflow/pcmwf/1.0}rewarning"+i)).toString():"0";
		    log.debug("rewarningString"+i+"="+rewarningString);
			model.put("rewarning"+i, rewarningString);
		}
		
		return model;
		
	}
	
	public static void main(String args[]) throws ParseException{
		
		String dt = "2015-03-11";  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.DATE, 1);  // number of days to add
		dt = sdf.format(c.getTime());  // dt is now the new date
		Date a = c.getTime();
		
		log.info(a);
		log.info(new Date());
		
		log.info(a.after(new Date()));
		log.info(a.before(new Date()));
		
	}


}
