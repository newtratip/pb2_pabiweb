package pb.common.util;

import java.util.Set;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.service.cmr.security.PersonService;
import org.alfresco.service.cmr.security.PersonService.PersonInfo;

public class PersonUtil {
	
	protected NodeService nodeService;
	
	public void setNodeService(NodeService nodeService) {this.nodeService = nodeService;}

	public String getFirstName(NodeRef person) {
		return (String)nodeService.getProperty(person, ContentModel.PROP_FIRSTNAME);
	}

	public String getLastName(NodeRef person) {
		return (String)nodeService.getProperty(person, ContentModel.PROP_LASTNAME);
	}
	
	public String getFullName(NodeRef person) {
		String firstName = getFirstName(person);
		String lastName = getLastName(person);
		
		return firstName + (lastName!=null ? " "+lastName : "");
	}
	
	public String getJobTitle(NodeRef person) {
		String title = (String)nodeService.getProperty(person, ContentModel.PROP_JOBTITLE);
		
		return (title!=null ? title : "");
	}
	
	public String getGoogleUserName(NodeRef person) {
		String username = (String)nodeService.getProperty(person, ContentModel.PROP_GOOGLEUSERNAME);
		
		return (username!=null ? username : "");
	}
	
	public static NodeRef getPerson(final String userId, final PersonService personService) {
		return 	AuthenticationUtil.runAs(new RunAsWork<NodeRef>()
			    {
					public NodeRef doWork() throws Exception
					{
						return personService.getPersonOrNull(userId);
					}
			    }, AuthenticationUtil.getAdminUserName());
	}
	
	public static PersonInfo getPerson(final NodeRef user, final PersonService personService) {
		return 	AuthenticationUtil.runAs(new RunAsWork<PersonInfo>()
			    {
					public PersonInfo doWork() throws Exception
					{
						return personService.getPerson(user);
					}
			    }, AuthenticationUtil.getAdminUserName());
	}
	
	public static String getEmail(NodeRef person, NodeService nodeService){
		String email = (String)NodeUtil.getProperty(person, ContentModel.PROP_EMAIL, nodeService);
		
		return (email!=null ? email : "");		
	}
	
	public static Set<String> getContainedAuthorities(final String group, final AuthorityService authorityService) {
		return 	AuthenticationUtil.runAs(new RunAsWork<Set<String>>()
			    {
					public Set<String> doWork() throws Exception
					{
						return authorityService.getContainedAuthorities(AuthorityType.USER, group, false);
					}
			    }, AuthenticationUtil.getAdminUserName());
	}
	
	public static NodeRef getAuthorityNodeRef(final String user, final AuthorityService authorityService) {
		return 	AuthenticationUtil.runAs(new RunAsWork<NodeRef>()
			    {
					public NodeRef doWork() throws Exception
					{
						return authorityService.getAuthorityNodeRef(user);
					}
			    }, AuthenticationUtil.getAdminUserName());
	}
	
}
