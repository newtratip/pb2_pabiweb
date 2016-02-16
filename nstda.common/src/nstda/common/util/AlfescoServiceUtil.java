package nstda.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.alfresco.service.cmr.security.AuthenticationService;
import org.alfresco.service.cmr.security.AuthorityService;
import org.alfresco.service.cmr.security.AuthorityType;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.json.JSONArray;
import org.json.JSONObject;


public class AlfescoServiceUtil {


	private static AuthenticationService authenticationService;
	private static AuthorityService authorityService;
	public void setAuthorityService(AuthorityService authorityService){this.authorityService = authorityService;}
	public void setAuthenticationService(AuthenticationService authenticationService){this.authenticationService = authenticationService;}
    
    public static JSONArray getAlfGroup() throws Exception {
    	
    	return AuthenticationUtil.runAs(new RunAsWork<JSONArray>()
 			   {
 					public JSONArray doWork() throws Exception
 					{
 						JSONArray jsArr = new JSONArray();
 				    	
 				    	JSONObject jsObj = null;
 				    	
 				    	Set<String> auth = authorityService.getAllAuthorities(AuthorityType.GROUP);
 				    	
 			    		List<String> groupList = new ArrayList<String>();
 			    		groupList.addAll(auth);
 			    		Collections.sort(groupList);
 				    	
 				    	for(String group : groupList) {
 				    		
 				    		if(!group.startsWith("GROUP_site_")) {
 				    			jsObj = new JSONObject();
 				    			jsObj.put("id", group.replace("GROUP_", ""));
 				    			jsObj.put("code", group.replace("GROUP_", ""));
 				    			jsObj.put("name", authorityService.getAuthorityDisplayName(group));
 				    			jsObj.put("type", "G");
 				        		jsObj.put("action", "A");
 				        		
 				        		jsArr.put(jsObj);
 				    		}
 				    	}
 				    	
 				    	return jsArr;
 					}
 	    }, AuthenticationUtil.getAdminUserName());

    }
    
   public static JSONArray getAlfGroupByUser() throws Exception {
    	
	    return AuthenticationUtil.runAs(new RunAsWork<JSONArray>()
	        			   {
	        					public JSONArray doWork() throws Exception
	        					{
	        						JSONArray jsArr = new JSONArray();
	        				    	
	        				    	JSONObject jsObj = null;
	        				    	
	        				    	Set<String> auth = authorityService.getAuthoritiesForUser(authenticationService.getCurrentUserName());
	        				    	
	        				    	for(String group : auth) {
	        				    		
	        				    		if(!group.startsWith("GROUP_site_")) {
	        				    			jsObj = new JSONObject();
	        				    			jsObj.put("id", group.replace("GROUP_", ""));
	        				    			jsObj.put("code", group.replace("GROUP_", ""));
	        				    			jsObj.put("name", authorityService.getAuthorityDisplayName(group));
	        				    			jsObj.put("type", "G");
	        				        		jsObj.put("action", "A");
	        				        		
	        				        		jsArr.put(jsObj);
	        				    		}
	        				    	}
	        				    	
	        				    	return jsArr;
	        					}
	    }, AuthenticationUtil.getAdminUserName());  	
    }
    
    public static JSONArray getAlfUser() throws Exception {
  
    	return	AuthenticationUtil.runAs(new RunAsWork<JSONArray>()
 			   {
 					public JSONArray doWork() throws Exception
 					{
 						JSONArray jsArr = new JSONArray();
 				    	
 				    	JSONObject jsObj;
 				    	
 				    	Set<String> auth = authorityService.getAllAuthorities(AuthorityType.USER);
 				    	
 			    		List<String> userList = new ArrayList<String>();
 			    		userList.addAll(auth);
 			    		Collections.sort(userList);
 				    	
 				    	for(String user : userList) {
 				    		
 				    		jsObj = new JSONObject();
 				    		
 				    		jsObj.put("id", user);
 				    		jsObj.put("code", user);
 				    		jsObj.put("name", authorityService.getAuthorityDisplayName(user));
 				    		jsObj.put("type", "U");
 				    		jsObj.put("action", "A");
 				    		
 				    		jsArr.put(jsObj);	
 				    		
 				    	}
 				    	
 				    	return jsArr;
 					}
 			   }, AuthenticationUtil.getAdminUserName());  	
    	
    	
    }
    
    public List<String> lisAuthRoles() {
		
    	return AuthenticationUtil.runAs(new RunAsWork<List<String>>()
 			   {
 					public List<String> doWork() throws Exception
 					{
 						try {
 							
 							List<String> roles = new ArrayList<String>();
 					    	JSONArray jsArr = getAlfGroupByUser();
 					    	for(int i=0; i<jsArr.length(); i++) {
 					    		JSONObject g = jsArr.getJSONObject(i);
 					    		roles.add(","+g.get("code").toString()+",");
 					    	}
 					    	return roles;
 							
 						}catch(Exception ex) {
 							ex.printStackTrace(System.out);
 						}
 						return null;
 					}
 			   }, AuthenticationUtil.getAdminUserName());  	
    	
		
		
	}
    
}
