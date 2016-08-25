package pb.repo.exp.wscript;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.common.util.CommonUtil;
import pb.repo.exp.util.ExpBrwUtil;
import pb.repo.exp.util.ExpUseUtil;

import com.github.dynamicextensionsalfresco.webscripts.annotations.RequestParam;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;


@Component
@WebScript
public class ExpMessageWebScript {
	
	private static Logger log = Logger.getLogger(ExpMessageWebScript.class);
	
	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/exp/message";
	
  /**
   * Handles the "get" request. Note the use of Spring MVC-style annotations to map the Web Script URI configuration
   * and request handling objects.
   * 
   * @param key
   * @param response
   * @throws IOException
   */
  @Uri(URI_PREFIX+"/get")
  public void handleGet(@RequestParam final String key,
		  				@RequestParam(required=false) String lang
		  				, final WebScriptResponse response) throws IOException, JSONException {

	  log.info(key+":"+lang);
	  
	  lang = CommonUtil.getValidLang(lang);
	
	  String msg = ExpBrwUtil.getMessage(key, new Locale(lang));
	  
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
  public void handleList(@RequestParam final String keys,
		  				 @RequestParam(required=false) String lang,
		  				 final WebScriptResponse response) throws IOException, JSONException {
	  log.info(keys+":"+lang);
	  
	  lang = CommonUtil.getValidLang(lang);
	
	  String[] keyArr = keys.split(",");
	  
	  List<String> msgs = new LinkedList<String>();
	  
	  for(String key : keyArr) {
		  
		  String msg = ExpBrwUtil.getMessage(key, new Locale(lang));
		  
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
  @Uri(URI_PREFIX+"/brw")
  public void handleReq(@RequestParam(required=false) String lang,
		  			final WebScriptResponse response) throws IOException, JSONException {
	  Locale locale = new Locale(CommonUtil.getValidLang(lang));
	  
	  JSONObject jObj = new JSONObject();
	  /*
	   * Main
	   */
	  JSONObject lbl = new JSONObject();
	  String prefix = "av.main.";
	  lbl.put("avNo", ExpBrwUtil.getMessage(prefix+"avNo", locale));
	  lbl.put("avType", ExpBrwUtil.getMessage(prefix+"avType", locale));
	  lbl.put("budget", ExpBrwUtil.getMessage(prefix+"budget", locale));
	  lbl.put("objective", ExpBrwUtil.getMessage(prefix+"objective", locale));
	  lbl.put("amount", ExpBrwUtil.getMessage(prefix+"amount", locale));
	  lbl.put("requester", ExpBrwUtil.getMessage(prefix+"requester", locale));
	  lbl.put("preparer", ExpBrwUtil.getMessage(prefix+"preparer", locale));
	  lbl.put("requestTime", ExpBrwUtil.getMessage(prefix+"requestTime", locale));
	  lbl.put("status", ExpBrwUtil.getMessage(prefix+"status", locale));
	  
	  jObj.put("m", lbl);

	  /*
	   * User Tab
	   */
	  lbl = new JSONObject();
	  prefix = "av.form.tab.usr.";
	  lbl.put("lbw", ExpBrwUtil.getMessage(prefix+"lbw", locale));
	  lbl.put("reqBy", ExpBrwUtil.getMessage(prefix+"reqBy", locale));
	  lbl.put("reqBu", ExpBrwUtil.getMessage(prefix+"reqBu", locale));
	  lbl.put("reqOu", ExpBrwUtil.getMessage(prefix+"reqOu", locale));
	  lbl.put("createdTime", ExpBrwUtil.getMessage(prefix+"createdTime", locale));
	  lbl.put("createdBy", ExpBrwUtil.getMessage(prefix+"createdBy", locale));
	  lbl.put("telNo", ExpBrwUtil.getMessage(prefix+"telNo", locale));
	  
	  jObj.put("u", lbl);
	  
	  /*
	   * Info
	   */
	  lbl = new JSONObject();
	  prefix = "av.form.tab.info.";
	  lbl.put("lbw", ExpBrwUtil.getMessage(prefix+"lbw", locale));
	  lbl.put("objType", ExpBrwUtil.getMessage(prefix+"objectiveType", locale));
	  lbl.put("err_objType", ExpBrwUtil.getMessage(prefix+"err.objectiveType", locale));
	  lbl.put("obj", ExpBrwUtil.getMessage(prefix+"objective", locale));
	  lbl.put("item", ExpBrwUtil.getMessage(prefix+"item", locale));
	  lbl.put("amount", ExpBrwUtil.getMessage(prefix+"amount", locale));
	  lbl.put("total", ExpBrwUtil.getMessage(prefix+"total", locale));
	  lbl.put("budgetSrc", ExpBrwUtil.getMessage(prefix+"budgetSrc", locale));
	  lbl.put("invAsset", ExpBrwUtil.getMessage(prefix+"invAsset", locale));
	  lbl.put("constr", ExpBrwUtil.getMessage(prefix+"construction", locale));
	  lbl.put("itemTitle", ExpBrwUtil.getMessage(prefix+"item.title", locale));
	  lbl.put("reason", ExpBrwUtil.getMessage(prefix+"reason", locale));
	  lbl.put("err_refId", ExpBrwUtil.getMessage(prefix+"err.refId", locale));
	  lbl.put("itemTitle", ExpBrwUtil.getMessage(prefix+"item.title", locale));
	  lbl.put("oweTitle", ExpBrwUtil.getMessage(prefix+"owe.title", locale));
	  lbl.put("oweDocNo", ExpBrwUtil.getMessage(prefix+"owe.docno", locale));
	  lbl.put("oweWait", ExpBrwUtil.getMessage(prefix+"owe.wait", locale));
	  lbl.put("oweBalance", ExpBrwUtil.getMessage(prefix+"owe.balance", locale));
	  lbl.put("methodTitle", ExpBrwUtil.getMessage(prefix+"method.title", locale));
	  lbl.put("bankBbl", ExpBrwUtil.getMessage(prefix+"bank.bbl", locale));
	  lbl.put("bank", ExpBrwUtil.getMessage(prefix+"bank", locale));
	  lbl.put("bankWarn", ExpBrwUtil.getMessage(prefix+"bank.warn", locale));
	  
	  jObj.put("n", lbl);
	  
	  /*
	   * Committee
	   */
	  lbl = new JSONObject();
	  prefix = "pr.form.tab.cmt.";
	  lbl.put("lbw", ExpBrwUtil.getMessage(prefix+"lbw", locale));
	  lbl.put("pcmMethod", ExpBrwUtil.getMessage(prefix+"pcmMethod", locale));
	  
	  jObj.put("c", lbl);
	  
	  /*
	   * File
	   */
	  lbl = new JSONObject();
	  prefix = "av.form.tab.file.";
	  lbl.put("lbw", ExpBrwUtil.getMessage(prefix+"lbw", locale));
	  
	  jObj.put("f", lbl);
	  
	  String json = jObj.toString();
	  
	  CommonUtil.responseWrite(response, json);
  }
  
  /**
   * Handles the "ord" request. Note the use of Spring MVC-style annotations to map the Web Script URI configuration
   * and request handling objects.
   * 
   * @param response
   * @throws IOException
   */
  @Uri(URI_PREFIX+"/use")
  public void handleOrd(@RequestParam(required=false) String lang,
		  			final WebScriptResponse response) throws IOException, JSONException {
	  
	  Locale locale = new Locale(CommonUtil.getValidLang(lang));
	  
	  JSONObject jObj = new JSONObject();
	  /*
	   * Main
	   */
	  JSONObject lbl = new JSONObject();
	  String prefix = "ap.main.";
	  lbl.put("apNo", ExpUseUtil.getMessage(prefix+"apNo", locale));
	  lbl.put("apType", ExpUseUtil.getMessage(prefix+"apType", locale));
	  lbl.put("budget", ExpUseUtil.getMessage(prefix+"budget", locale));
	  lbl.put("objective", ExpUseUtil.getMessage(prefix+"objective", locale));
	  lbl.put("amount", ExpUseUtil.getMessage(prefix+"amount", locale));
	  lbl.put("requester", ExpUseUtil.getMessage(prefix+"requester", locale));
	  lbl.put("preparer", ExpUseUtil.getMessage(prefix+"preparer", locale));
	  lbl.put("requestTime", ExpUseUtil.getMessage(prefix+"requestTime", locale));
	  lbl.put("status", ExpUseUtil.getMessage(prefix+"status", locale));
	  
	  jObj.put("m", lbl);

	  /*
	   * User Tab
	   */
	  lbl = new JSONObject();
	  prefix = "pr.form.tab.usr.";
	  lbl.put("lbw", ExpUseUtil.getMessage(prefix+"lbw", locale));
	  lbl.put("reqBy", ExpUseUtil.getMessage(prefix+"reqBy", locale));
	  lbl.put("reqBu", ExpUseUtil.getMessage(prefix+"reqBu", locale));
	  lbl.put("reqOu", ExpUseUtil.getMessage(prefix+"reqOu", locale));
	  lbl.put("createdTime", ExpUseUtil.getMessage(prefix+"createdTime", locale));
	  lbl.put("createdBy", ExpUseUtil.getMessage(prefix+"createdBy", locale));
	  lbl.put("telNo", ExpUseUtil.getMessage(prefix+"telNo", locale));
	  
	  jObj.put("u", lbl);
	  
	  /*
	   * Info
	   */
	  lbl = new JSONObject();
	  prefix = "pr.form.tab.info.";
	  lbl.put("lbw", ExpUseUtil.getMessage(prefix+"lbw", locale));
	  lbl.put("objType", ExpUseUtil.getMessage(prefix+"objectiveType", locale));
	  lbl.put("err_objType", ExpUseUtil.getMessage(prefix+"err.objectiveType", locale));
	  lbl.put("obj", ExpUseUtil.getMessage(prefix+"objective", locale));
	  lbl.put("reason", ExpUseUtil.getMessage(prefix+"reason", locale));
	  lbl.put("currency", ExpUseUtil.getMessage(prefix+"currency", locale));
	  lbl.put("currencyRate", ExpUseUtil.getMessage(prefix+"currencyRate", locale));
	  lbl.put("budgetSrc", ExpUseUtil.getMessage(prefix+"budgetSrc", locale));
	  lbl.put("invAsset", ExpUseUtil.getMessage(prefix+"invAsset", locale));
	  lbl.put("constr", ExpUseUtil.getMessage(prefix+"construction", locale));
	  lbl.put("isPtt", ExpUseUtil.getMessage(prefix+"isPrototype", locale));
	  lbl.put("err_isPtt", ExpUseUtil.getMessage(prefix+"err.isPrototype", locale));
	  lbl.put("ptt", ExpUseUtil.getMessage(prefix+"prototype", locale));
	  lbl.put("contract", ExpUseUtil.getMessage(prefix+"contract", locale));
	  lbl.put("err_contract", ExpUseUtil.getMessage(prefix+"err.contract", locale));
	  lbl.put("cc", ExpUseUtil.getMessage(prefix+"costControl", locale));
	  lbl.put("loc", ExpUseUtil.getMessage(prefix+"location", locale));
	  lbl.put("isAB", ExpUseUtil.getMessage(prefix+"isAcrossBudget", locale));
	  lbl.put("total", ExpUseUtil.getMessage(prefix+"total", locale));
	  lbl.put("err_total", ExpUseUtil.getMessage(prefix+"err.total", locale));
	  lbl.put("isRefId", ExpUseUtil.getMessage(prefix+"isRefId", locale));
	  lbl.put("refId", ExpUseUtil.getMessage(prefix+"refId", locale));
	  lbl.put("err_refId", ExpUseUtil.getMessage(prefix+"err.refId", locale));
	  
	  jObj.put("n", lbl);
	  
	  /*
	   * Committee
	   */
	  lbl = new JSONObject();
	  prefix = "pr.form.tab.cmt.";
	  lbl.put("lbw", ExpUseUtil.getMessage(prefix+"lbw", locale));
	  lbl.put("pcmMethod", ExpUseUtil.getMessage(prefix+"pcmMethod", locale));
	  
	  jObj.put("c", lbl);
	  
	  /*
	   * File
	   */
	  lbl = new JSONObject();
	  prefix = "pr.form.tab.file.";
	  lbl.put("lbw", ExpUseUtil.getMessage(prefix+"lbw", locale));
	  
	  jObj.put("f", lbl);
	  
	  String json = jObj.toString();
	  
	  CommonUtil.responseWrite(response, json);
  }  

}
