package nstda.common.util;

import java.util.Map;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import nstda.common.constant.CommonConstant;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.repository.TemplateService;

public class MailUtil {
	
	public static Properties getMailProperties(Properties gProp){
		
		Properties props = new Properties();
		
		props.setProperty(CommonConstant.GP_MAIL_HOST, gProp.getProperty(CommonConstant.GP_MAIL_HOST));
		
		props.setProperty(CommonConstant.GP_MAIL_TRANSPORT_PROTOCOL, gProp.getProperty(CommonConstant.GP_MAIL_TRANSPORT_PROTOCOL)); 
		props.put(CommonConstant.GP_MAIL_SMTP_AUTH, gProp.getProperty(CommonConstant.GP_MAIL_SMTP_AUTH));
		props.put(CommonConstant.GP_MAIL_SMTP_PORT, gProp.getProperty(CommonConstant.GP_MAIL_SMTP_PORT));
		props.put(CommonConstant.GP_MAIL_DEBUG, true);
		props.setProperty("mail.smtp.dsn.notify", "SUCCESS,FAILURE,DELAY");
		props.setProperty("mail.smtp.reportsuccess", "false");
		props.setProperty("mail.smtp.sendpartial", "true");
		
		if(gProp.getProperty(CommonConstant.GP_MAIL_HOST).equals("smtp.gmail.com")){
			props.put(CommonConstant.GP_MAIL_SMTP_SOCKET_PORT, gProp.getProperty(CommonConstant.GP_MAIL_SMTP_SOCKET_PORT));
			//props.put(CommonConstant.GP_MAIL_SOCKET_CLASS, gProp.getProperty(CommonConstant.GP_MAIL_SOCKET_CLASS));
			props.put(CommonConstant.GP_MAIL_SMTP_STARTTLS_ENABLE, "true");
		}
		
		props.put(CommonConstant.GP_MAIL_SOCKET_FALLBACK, gProp.getProperty(CommonConstant.GP_MAIL_SOCKET_FALLBACK));
		
		return props;
	}
	
	public static Session getDefaultInstance(final Properties props, final String user, final String password) {
		return 	AuthenticationUtil.runAs(new RunAsWork<Session>()
			    {
					public Session doWork() throws Exception
					{
						return Session.getInstance(props,new javax.mail.Authenticator()
						{ 
							@Override
							protected PasswordAuthentication getPasswordAuthentication() 
							{
								return new PasswordAuthentication(user, password);
							}
						});
					}
			    }, AuthenticationUtil.getAdminUserName());
	}
	
	public static String processTemplate(final String mailTemplate, final Map<String, Object> model, final TemplateService templateService) {
		return 	AuthenticationUtil.runAs(new RunAsWork<String>()
			    {
					public String doWork() throws Exception
					{
						return templateService.processTemplate("freemarker", mailTemplate, model);
					}
			    }, AuthenticationUtil.getAdminUserName());
	}
	

}
