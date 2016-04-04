package pb.repo.admin.wscript;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.common.util.CommonUtil;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.model.MainMsgModel;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminMsgService;
import pb.repo.admin.service.AlfrescoService;
import pb.repo.admin.xmlrpc.PcmInvocationHandler;
import redstone.xmlrpc.XmlRpcArray;
import redstone.xmlrpc.XmlRpcClient;
import redstone.xmlrpc.XmlRpcDispatcher;
import redstone.xmlrpc.XmlRpcJsonSerializer;
import redstone.xmlrpc.XmlRpcServer;
import redstone.xmlrpc.XmlRpcStruct;

import com.github.dynamicextensionsalfresco.webscripts.annotations.HttpMethod;
import com.github.dynamicextensionsalfresco.webscripts.annotations.RequestParam;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript
public class MainXmlRpcWebScript {
	
	private static Logger log = Logger.getLogger(MainXmlRpcWebScript.class);
	
	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/inf";
	
	@Autowired
	AlfrescoService alfrescoService;
	
	@Autowired
	AdminMasterService masterService;
	
	@Autowired
	AdminMsgService msgService;
	
	@Autowired
	AuthenticationService authService;
	
	@Uri(URI_PREFIX+"/start")
	public void handleOdooStart(final WebScriptResponse response) throws Exception {
		log.info("pass 1");
//		XmlRpcParser.setDriver("org.apache.xerces.parsers.SAXParser");
		log.info("pass 2");
//		XmlRpc.setDriver("org.apache.xerces.parsers.SAXParser");

		final XmlRpcClient client = new XmlRpcClient(new URL("http://128.199.255.252:8069/xmlrpc/2/common"), true);
		
		log.info("pass 3");

		List arguments = new ArrayList();
		arguments.add("PABI");
		arguments.add("admin");
		arguments.add("admin");
		arguments.add(new ArrayList());
		
		Object uid = client.invoke("authenticate", arguments);
		
		log.info("obj="+uid.getClass().getName());
		
		String json = null;
		
		try {
			JSONObject jsObj = new JSONObject();
			
			jsObj.put("uid", uid);
			
			json = CommonUtil.jsonSuccess(jsObj);
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
		} finally {
			CommonUtil.responseWrite(response, json);
		}
	}
	
	@Uri(URI_PREFIX+"/search")
	public void handleOdooSearch(@RequestParam(required=false) String t, final WebScriptResponse response) throws Exception {
//		XmlRpcParser.setDriver("org.apache.xerces.parsers.SAXParser");

		String host = "http://localhost:8069";
		
		final XmlRpcClient client = new XmlRpcClient(new URL(host+"/xmlrpc/2/object"),true);

		if (t==null) t = "res.partner";
		
		String db = "V8";
		
		List arguments = new ArrayList();
		arguments.add(db);
		arguments.add(1);
		arguments.add("admin");
		arguments.add(t);
		arguments.add("search");
		
		List a = new ArrayList();
		List b = new ArrayList();
//		List c = new ArrayList();
//		b.add(c);
		
		a.add(b);
		
		arguments.add(a);
//		Map<Object, String> info = (Map)client.invoke("execute_kw", arguments);

		List ids = null;
		XmlRpcArray array = null;
		
		Object obj = client.invoke("execute_kw", arguments);
		log.info("** exec 1");
		
		if (obj.getClass().getName().indexOf("List") >= 0) {
			ids = (List)obj;
			for (Object o : ids) {
				log.info(" -"+o+":"+o.getClass().getName());
			}
		} else {
			array = (XmlRpcArray)obj;
			ids = new ArrayList();
			for(Object o : array) {
				log.info(o+":"+o.getClass().getName());
				ids.add(o);
			}
//			map = (Map)obj;
//			for (Entry<Object,Object> e : map.entrySet()) {
//				log.info(" -"+e.getKey()+":"+e.getValue());
//			}
		}
		
		
		for(Object o : ids) {
			log.info(o+":"+o.getClass().getName());
		}
		
		arguments = new ArrayList();
		arguments.add(db);
		arguments.add(1);
		arguments.add("admin");
		arguments.add(t);
		arguments.add("read");
		
		a = new ArrayList();
		a.add(ids);
		
		arguments.add(a);
		
		obj = client.invoke("execute_kw", arguments);
		log.info("** exec 2");
		
		ids = null;
		array = null;
		
		if (obj.getClass().getName().indexOf("List") >= 0) {
			ids = (List)obj;
			for (Object o : ids) {
				log.info(" -"+o+":"+o.getClass().getName());
			}
		} else {
			array = (XmlRpcArray)obj;
			for(Object o : array) {
				log.info(o+":"+o.getClass().getName());
			}
		}		
		
		String json = null;
		
		try {
			JSONObject jsObj = new JSONObject();
			
			jsObj.put("data", array!=null ? array : ids);
			
			json = CommonUtil.jsonSuccess(jsObj);
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
		} finally {
			CommonUtil.responseWrite(response, json);
		}
	}

	
	@Uri(method=HttpMethod.POST, value=URI_PREFIX+"/invoke")
	public void handleOdooXmlrpc(final WebScriptRequest request, final WebScriptResponse response) throws Throwable {
		
		try {
			log.info("/inf/invoke");
			log.info("  content="+request.getContent().getContent());
			
			XmlRpcServer server = new XmlRpcServer();
			server.addInvocationHandler("pcm", new PcmInvocationHandler());
			
			XmlRpcDispatcher dispatcher = new XmlRpcDispatcher(server, "");
			
			InputStream is = new ByteArrayInputStream(request.getContent().getContent().getBytes());
			dispatcher.dispatch(is, response.getWriter());
			
		} catch (Exception ex) {
			log.error(ex);
		}
		
//		Writer w = response.getWriter();
//		XmlRpcSerializer s = new XmlRpcSerializer();
//		s.writeEnvelopeHeader("", w);
//		s.serialize("Hello",w);
//		s.writeEnvelopeFooter("", w);
//		w.flush();
		
//		CommonUtil.responseWrite(response, "<?xml version=\"1.0\"?>"
//+"<methodResponse>"
//+"  <params>"
//    +"<param>"
//        +"<value><string>OK</string></value>"
//    +"</param>"
//  +"</params>"
//+"</methodResponse>");
	}
	
	@Uri(URI_PREFIX+"/pr")
	public void handlePostPR(@RequestParam(required=false) String t,
							final WebScriptResponse response) throws Exception {

		String host = "http://pabi2.3roots.info";
		String db = "tr_mockup_280316";
		
		final XmlRpcClient client = new XmlRpcClient(new URL(host+"/xmlrpc/2/object"), true);
		
		if (t==null) t = "purchase.request";
		
		List arguments = new ArrayList();
		arguments.add(db); // db name
		arguments.add(1); // uid 1='admin'
		arguments.add("admin"); // password
		arguments.add(t);
		arguments.add("generate_purchase_request");
		
		List a = new ArrayList();
		Map<String, Object> map = new HashMap<String, Object>();
		
        map.put("name", "PR16000001");
        map.put("requested_by","1");
        map.put("responsible_person","1");
        map.put("date_approved","2016-01-31");
        map.put("prototype","1");
        map.put("total_budget_value","240000");
        map.put("warehouse_id","1");
        map.put("purchase_type_id","1");
        map.put("purchase_method_id","1");
        map.put("description","Put your PR description here.");
        map.put("objective","Put your PR objective here");     
        map.put("currency_id","1");
        map.put("currency_rate","1");
        map.put("delivery_address","Put your PR delivery address here");
        map.put("date_start","2016-01-31");
        map.put("picking_type_id","1");
        map.put("operating_unit_id","1");
        
        List orderLine = new ArrayList();
        
        Map<String, Object> line = new HashMap<String, Object>();
        line.put("product_id","");
        line.put("name","Computer"); 
        line.put("product_qty","20"); 
        line.put("price_unit","10000"); 
        line.put("analytic_accout_id",""); 
        line.put("project_id","");
        line.put("costcenter_id",""); 
        line.put("fixed_asset","");
        line.put("date_required","2016-01-31");         
        orderLine.add(line);
        
        line = new HashMap<String, Object>();
        line.put("product_id","");
        line.put("name","Tablet"); 
        line.put("product_qty","20"); 
        line.put("price_unit","10000"); 
        line.put("analytic_accout_id",""); 
        line.put("project_id","");
        line.put("costcenter_id",""); 
        line.put("fixed_asset","");
        line.put("date_required","2016-01-31");         
        orderLine.add(line);
        
        map.put("order_line", orderLine);

        
        List committee = new ArrayList();

        Map<String, Object> cmt = new HashMap<String, Object>();
        cmt.put("name","นาย เอ เอบีซี"); 
        cmt.put("sequence","1");
        cmt.put("responsible",""); 
        cmt.put("position","ประธาน"); 
        cmt.put("committee_type","จัดซื้อแบบพิเศษ"); 
        committee.add(cmt);
        
        cmt = new HashMap<String, Object>();
        cmt.put("name","นาย เอ เอบีซีดี"); 
        cmt.put("sequence","2");
        cmt.put("responsible",""); 
        cmt.put("position","รองประธาน"); 
        cmt.put("committee_type","จัดซื้อแบบพิเศษ"); 
        committee.add(cmt);
        
        map.put("committee_ids", committee);

        List attachment = new ArrayList();
        
        Map<String, Object> att = new HashMap<String, Object>();
        att.put("name","เอกสารแนบหมายเลข0001"); 
        att.put("file_url","http://google.com");
        orderLine.add(att);
        
        att = new HashMap<String, Object>();
        att.put("name","เอกสารแนบหมายเลข0002"); 
        att.put("file_url","http://google.com");
        orderLine.add(att);
        
        map.put("attachment_ids", attachment);
		
		a.add(map);
		
		arguments.add(a);

		List ids = null;
		XmlRpcArray array = null;
		
		Object obj = client.invoke("execute_kw", arguments);
		log.info("** exec 1");
		
		if (obj.getClass().getName().indexOf("List") >= 0) {
			ids = (List)obj;
			for (Object o : ids) {
				log.info(" -"+o+":"+o.getClass().getName());
			}
		} else {
			array = (XmlRpcArray)obj;
			ids = new ArrayList();
			for(Object o : array) {
				log.info(o+":"+o.getClass().getName());
				ids.add(o);
			}
//			map = (Map)obj;
//			for (Entry<Object,Object> e : map.entrySet()) {
//				log.info(" -"+e.getKey()+":"+e.getValue());
//			}
		}
		
		for(Object o : ids) {
			log.info(o+":"+o.getClass().getName());
		}
		
		String json = null;
		
		try {
			JSONObject jsObj = new JSONObject();
			
			jsObj.put("data", array!=null ? array : ids);
			
			json = CommonUtil.jsonSuccess(jsObj);
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
		} finally {
			CommonUtil.responseWrite(response, json);
		}
	}
	
	@Uri(URI_PREFIX+"/pr2")
	public void handlePostPR2(@RequestParam(required=false) String t,
							final WebScriptResponse response) throws Exception {

		String host = "http://pabi2.3roots.info";
		String db = "tr_mockup_280316";
		
		final XmlRpcClient client = new XmlRpcClient(new URL(host+"/xmlrpc/2/object"), true);
		
		if (t==null) t = "purchase.request";
		
		List arguments = new ArrayList();
		arguments.add(db); // db name
		arguments.add(1); // uid 1='admin'
		arguments.add("admin"); // password
		arguments.add(t);
		arguments.add("generate_purchase_request");
		
		List a = new ArrayList();
		Map<String, Object> map = new HashMap<String, Object>();
		
        map.put("name", "PR16000001");
        map.put("requested_by","Administrator");
        map.put("responsible_person","Administrator");
        map.put("date_approved","2016-01-31");
        map.put("prototype","True");
        map.put("total_budget_value","240000");
        map.put("warehouse_id","Your Company");
        map.put("purchase_type_id","AAAA");
        map.put("purchase_method_id","AAAA");
        map.put("description","Put your PR description here.");
        map.put("objective","Put your PR objective here");     
        map.put("currency_id","THB");
        map.put("currency_rate","1");
        map.put("delivery_address","Put your PR delivery address here");
        map.put("date_start","2016-01-31");
        map.put("picking_type_id","Receipts");
//        map.put("operating_unit_id","1");
//        map.put("activity_group_id","1");
        map.put("line_ids/name","Computer");
        map.put("line_ids/product_qty","20");
        map.put("line_ids/price_unit","10000");
        map.put("line_ids/date_required","2016-01-31");
        
		a.add(map);
		
		arguments.add(a);

		XmlRpcArray msgs = null;
		
		XmlRpcStruct struct = (XmlRpcStruct)client.invoke("execute_kw", arguments);
		for(Object k : struct.keySet()) {
			Object v = struct.get(k);
			log.info("-- "+k+":"+v+":"+(v!=null ? v.getClass().getName() : ""));
			if (k.equals("messages")) {
				msgs = (XmlRpcArray)v;
			}
		}
		
		String json = null;
		
		try {
			JSONObject jsObj = new JSONObject();
			
			jsObj.put("data", msgs);
			
			json = CommonUtil.jsonSuccess(jsObj);
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
		} finally {
			CommonUtil.responseWrite(response, json);
		}
	}
	
}
