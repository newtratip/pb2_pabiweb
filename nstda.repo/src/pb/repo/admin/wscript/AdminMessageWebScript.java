package pb.repo.admin.wscript;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.surf.util.I18NUtil;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.common.util.CommonUtil;
import pb.repo.admin.util.MainUtil;

import com.github.dynamicextensionsalfresco.webscripts.annotations.RequestParam;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;


@Component
@WebScript
public class AdminMessageWebScript {
	
	private static Logger log = Logger.getLogger(AdminMessageWebScript.class);
	
	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/admin/message";
	
  /**
   * Handles the "get" request. Note the use of Spring MVC-style annotations to map the Web Script URI configuration
   * and request handling objects.
   * 
   * @param name
   * @param response
   * @throws IOException
   */
  @Uri(URI_PREFIX+"/get")
  public void handleGet(@RequestParam final String key, final WebScriptResponse response) throws IOException, JSONException {
	
	  String msg = MainUtil.getMessage(key, I18NUtil.getLocale());
	  
	  if (StringUtils.isEmpty(msg)) {
		msg = CommonUtil.getInvalidKeyMsg(key);
	  }

	  String json = CommonUtil.jsonMessage(msg);
	  
	  CommonUtil.responseWrite(response, json);
  }
  
  /**
   * Handles the "list" request. Note the use of Spring MVC-style annotations to map the Web Script URI configuration
   * and request handling objects.
   * 
   * @param keys
   * @param response
   * @throws IOException
   */
  @Uri(URI_PREFIX+"/list")
  public void handleList(@RequestParam final String keys, final WebScriptResponse response) throws IOException, JSONException {
	
	  String[] keyArr = keys.split(",");
	  
	  List<String> msgs = new LinkedList<String>();
	  
	  for(String key : keyArr) {
		  
		  String msg = MainUtil.getMessage(key, I18NUtil.getLocale());
		  
		  if (StringUtils.isEmpty(msg)) {
			msg = CommonUtil.getInvalidKeyMsg(key);
		  }

		  msgs.add(msg);
	  }
	  
	  String json = CommonUtil.jsonMessage(msgs);
	  
	  CommonUtil.responseWrite(response, json);
  }

  /**
   * Handles the "req" request. Note the use of Spring MVC-style annotations to map the Web Script URI configuration
   * and request handling objects.
   * 
   * @param response
   * @throws IOException
   */
  @Uri(URI_PREFIX+"/lbl")
  public void handleReq(@RequestParam(required=false) String lang,
		  				final WebScriptResponse response) throws IOException, JSONException {
//	  String json = "{a:\"ทดสอบ 2\"}";
	  
	  Locale locale = new Locale(CommonUtil.getValidLang(lang));
	  
	  JSONObject jObj = new JSONObject();
	  /*
	   * Main
	   */
	  JSONObject lbl = new JSONObject();
	  String prefix = "main.";
	  lbl.put("searchTerm", MainUtil.getMessage(prefix+"searchTerm", locale));
	  lbl.put("search", MainUtil.getMessage(prefix+"search", locale));
	  lbl.put("create", MainUtil.getMessage(prefix+"create", locale));
	  lbl.put("edit", MainUtil.getMessage(prefix+"edit", locale));
	  lbl.put("new", MainUtil.getMessage(prefix+"new", locale));
	  lbl.put("add", MainUtil.getMessage(prefix+"add", locale));
	  lbl.put("cancel", MainUtil.getMessage(prefix+"cancel", locale));
	  lbl.put("save", MainUtil.getMessage(prefix+"save", locale));
	  lbl.put("del", MainUtil.getMessage(prefix+"del", locale));
	  lbl.put("yes", MainUtil.getMessage(prefix+"yes", locale));
	  lbl.put("no", MainUtil.getMessage(prefix+"no", locale));
	  lbl.put("confirm", MainUtil.getMessage(prefix+"confirm", locale));
	  
	  lbl.put("org", MainUtil.getMessage(prefix+"org", locale));
	  lbl.put("section", MainUtil.getMessage(prefix+"section", locale));
	  lbl.put("project", MainUtil.getMessage(prefix+"project", locale));
	  lbl.put("emp", MainUtil.getMessage(prefix+"employee", locale));
	  lbl.put("ecode", MainUtil.getMessage(prefix+"employeeCode", locale));
	  lbl.put("nonemp", MainUtil.getMessage(prefix+"nonEmployee", locale));
	  lbl.put("name", MainUtil.getMessage(prefix+"name", locale));
	  lbl.put("fullname", MainUtil.getMessage(prefix+"fullName", locale));
	  lbl.put("fname", MainUtil.getMessage(prefix+"firstName", locale));
	  lbl.put("lname", MainUtil.getMessage(prefix+"lastName", locale));
	  lbl.put("pos", MainUtil.getMessage(prefix+"position", locale));
	  lbl.put("type", MainUtil.getMessage(prefix+"type", locale));
	  
	  jObj.put("m", lbl);
	  
	  /*
	   * Upload
	   */
	  lbl = new JSONObject();
	  prefix = "upload.";
	  lbl.put("file", MainUtil.getMessage(prefix+"file", locale));
	  lbl.put("desc", MainUtil.getMessage(prefix+"descrition", locale));
	  lbl.put("upload", MainUtil.getMessage(prefix+"upload", locale));
	  lbl.put("number", MainUtil.getMessage(prefix+"number", locale));
	  lbl.put("name", MainUtil.getMessage(prefix+"name", locale));
	  lbl.put("browse", MainUtil.getMessage(prefix+"browse", locale));
	  
	  jObj.put("u", lbl);
	  
	  /*
	   * Upload
	   */
	  lbl = new JSONObject();
	  prefix = "budgetSrc.";
	  lbl.put("name", MainUtil.getMessage(prefix+"name", locale));
	  lbl.put("invAsset", MainUtil.getMessage(prefix+"invAsset", locale));
	  lbl.put("constr", MainUtil.getMessage(prefix+"construction", locale));
	  lbl.put("sectName", MainUtil.getMessage(prefix+"sectionName", locale));
	  lbl.put("projName", MainUtil.getMessage(prefix+"projectName", locale));
	  lbl.put("pm", MainUtil.getMessage(prefix+"pm", locale));
	  lbl.put("cpnp", MainUtil.getMessage(prefix+"conProjNamePhase", locale));
	  lbl.put("asset", MainUtil.getMessage(prefix+"assetName", locale));
	  lbl.put("sectLabName", MainUtil.getMessage(prefix+"sectionLabName", locale));
	  lbl.put("researchU", MainUtil.getMessage(prefix+"researchUnit", locale));
	  lbl.put("bamt", MainUtil.getMessage(prefix+"budgetAmt", locale));
	  
	  jObj.put("b", lbl);

	  String json = jObj.toString();
	  
	  CommonUtil.responseWrite(response, json);
  }

}
