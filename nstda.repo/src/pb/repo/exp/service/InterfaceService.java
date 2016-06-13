package pb.repo.exp.service;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.alfresco.repo.forms.FormException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.DateFormat;

import pb.common.constant.CommonConstant;
import pb.common.model.FileModel;
import pb.common.util.CommonDateTimeUtil;
import pb.common.util.NodeUtil;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminSectionService;
import pb.repo.exp.model.ExpBrwModel;
import pb.repo.exp.model.ExpUseModel;
import redstone.xmlrpc.XmlRpcClient;
import redstone.xmlrpc.XmlRpcException;
import redstone.xmlrpc.XmlRpcStruct;

@Service
public class InterfaceService {

	private static Logger log = Logger.getLogger(InterfaceService.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	ExpBrwService expBrwService;
	
	@Autowired
	AdminMasterService masterService;
	
	@Autowired
	AdminSectionService sectionService;
	
	private List<Object> getInitArgs(Map<String, Object> cfg) {
		List<Object> args = new ArrayList();
		args.add(cfg.get("db")); // db name
		args.add(cfg.get("usr")); // uid 1='admin'
		args.add(cfg.get("pwd")); // password
		
		return args;
	}

	private Map<String, Object> getConnectionConfig() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		MainMasterModel sysCfgModel = masterService.getSystemConfig(MainMasterConstant.SCC_MAIN_ODOO_URL);
//		String host = "http://10.226.202.133:8069";
		String host = sysCfgModel.getFlag1();
		
		sysCfgModel = masterService.getSystemConfig(MainMasterConstant.SCC_MAIN_ODOO_DB);
//		String db = "PABI2";
		String db = sysCfgModel.getFlag1();
		
		Integer usr = 1; // uid 1='admin'
		String pwd = "admin"; // password
		
		log.info("host:"+host);
		log.info("db:"+db);
		log.info("usr:"+usr);
		
		map.put("host", host);
		map.put("db", db);
		map.put("usr", usr);
		map.put("pwd", pwd);

		return map;
	}
	
	private XmlRpcClient getXmlRpcClient(Map<String, Object> cfg) throws Exception {
		return new XmlRpcClient(cfg.get("host")+"/xmlrpc/2/object", false);
	}
	
	public String createAV(ExpBrwModel model) throws Exception {
		log.info("interface : createPR");
		
		Boolean success = false;
		String msgs = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			Map<String, Object> cfg = getConnectionConfig();
			XmlRpcClient client = getXmlRpcClient(cfg);
			
			List<Map<String, Object>> list = expBrwService.listForInf(model.getId());
			Map<String, Object> data = list.size() > 0 ? list.get(0) : null;
			
			List args = getInitArgs(cfg);
			args.add("purchase.request"); // Remote Object
			args.add("generate_purchase_request");
			
			List a = new ArrayList();
			
			/*
			 * Header
			 */
	        map.put("name", data.get("req_id"));
	        map.put("requested_by",data.get("req_by"));
	        map.put("responsible_uid",data.get("rp_id")!=null ? data.get("rp_id") : "");
	        map.put("assigned_to",data.get("last_approver"));
	        map.put("date_approve",CommonDateTimeUtil.convertToOdooFieldDate((Timestamp) data.get("by_time")));
	        map.put("total_budget_value",data.get("total"));
	        map.put("purchase_prototype_id.id",data.get("is_prototype").equals("1") ? "1" : "2");
	        map.put("purchase_type_id.id", data.get("objective_type"));
	        map.put("purchase_method_id.id",data.get("method_id"));
	        map.put("description",data.get("reason"));
	        map.put("objective",data.get("objective"));
	        map.put("currency_id.id",data.get("currency_id"));
	        map.put("currency_rate",data.get("currency_rate"));
	        map.put("delivery_address", data.get("location"));
	        map.put("date_start",CommonDateTimeUtil.convertToOdooFieldDate((Timestamp) data.get("created_time")));
//	        map.put("operating_unit_id.id",data.get("operating_unit_id"));
	        map.put("org_id",data.get("pur_org_id"));
	        map.put("request_ref_id", data.get("ref_id"));
	        map.put("purchase_price_range_id.id", data.get("price_range_id")!=null ? data.get("price_range_id") : "");
	        map.put("purchase_condition_id.id", data.get("condition_id")!=null ? data.get("condition_id") : "");
	        map.put("purchase_confidential_id.id", data.get("confidential_id")!=null ? data.get("confidential_id") : "");
	        map.put("confidential_detail", data.get("method_cond2_dtl"));
	        
	        for(String key : map.keySet()) {
	        	log.info(" - "+key+":"+map.get(key));
	        }
	        
	        /*
	         * Line Item
	         */
			list = expBrwService.listDtlForInf(model.getId());
	        
	        List orderLine = new ArrayList();
	        
	        for(Map<String, Object> dtl:list) {
		        Map<String, Object> line = new HashMap<String, Object>();
		        line.put("product_id.id","");
		        line.put("name",dtl.get("description")); 
		        line.put("product_qty",dtl.get("quantity")); 
		        line.put("price_unit",dtl.get("price_cnv")); ////////
		        line.put("product_uom_id.id",dtl.get("unit_id")); 
	//	        line.put("activity_id.id","");
	//	        line.put("date_required",CommonDateTimeUtil.convertToOdooFieldDate((Timestamp) data.get("updated_time"))); 
		        line.put("section_id.id",dtl.get("budget_cc"));
		        line.put("cost_control_id.id",dtl.get("cost_control_id")!=null ? dtl.get("cost_control_id") : "");
		        line.put("fixed_asset","False");
		        line.put("tax_ids",dtl.get("vat_name")!=null ? dtl.get("vat_name") : "");
		        orderLine.add(line);
		        
		        for(String key : line.keySet()) {
		        	log.info(" -- "+key+":"+line.get(key));
		        }
	        }
	        
	        map.put("line_ids", orderLine);
	
	        /*
	         * Attachment
	         */
	        List<FileModel> fileList = expBrwService.listFile(model.getId(), true);
	        log.info("fileList.size()="+fileList.size());
	        
	        List attachment = new ArrayList();
	        
	        for(FileModel file:fileList) {
	// detail : http://localhost:18080/share/page/document-details?nodeRef=workspace://SpacesStore/5190b027-00c6-4322-98c3-1be01314bdd9
	//download format url : http://localhost:18080/share/proxy/alfresco/api/node/content/workspace/SpacesStore/<url>/<name>?a=true
	//example url : http://localhost:18080/share/proxy/alfresco/api/node/content/workspace/SpacesStore/260cdb8d-5a9c-43af-9c8e-178c75f1fe38/PR16000050.pdf?a=true
	        	
		        Map<String, Object> att = new HashMap<String, Object>();
		        att.put("name", file.getName()); 
		        att.put("description", file.getDesc()!=null ? file.getDesc() : ""); 
		        att.put("file_url",NodeUtil.trimNodeRef(file.getNodeRef().toString()));
		        attachment.add(att);
		        
		        for(String key : att.keySet()) {
		        	log.info(" ---- "+key+":"+att.get(key));
		        }
	        }
	        
	        map.put("attachment_ids", attachment);
	        
	        log.info("map="+map);
			
			a.add(map);
			
			args.add(a);

			Object obj = client.invoke("execute_kw", args);
			
			XmlRpcStruct strc = (XmlRpcStruct)obj;
			
			for(Object k : strc.keySet()) {
				Object v = strc.get(k);
	
				log.info(" - "+k+" : "+v+":"+v.getClass().getName());
			}
			
			success = (Boolean)strc.get("is_success");
			msgs = strc.get("messages")!=null ? strc.get("messages").toString() : "";
		}
		catch (Exception ex) {
			return ex.toString()+":"+map.toString();
		}
		
		return success ? "OK" : msgs+":"+map.toString();
	}
	
	public String createAP(ExpUseModel model) throws Exception {
		log.info("interface : createPR");
		
		Boolean success = false;
		String msgs = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			Map<String, Object> cfg = getConnectionConfig();
			XmlRpcClient client = getXmlRpcClient(cfg);
			
			List<Map<String, Object>> list = expBrwService.listForInf(model.getId());
			Map<String, Object> data = list.size() > 0 ? list.get(0) : null;
			
			List args = getInitArgs(cfg);
			args.add("purchase.request"); // Remote Object
			args.add("generate_purchase_request");
			
			List a = new ArrayList();
			
			/*
			 * Header
			 */
	        map.put("name", data.get("req_id"));
	        map.put("requested_by",data.get("req_by"));
	        map.put("responsible_uid",data.get("rp_id")!=null ? data.get("rp_id") : "");
	        map.put("assigned_to",data.get("last_approver"));
	        map.put("date_approve",CommonDateTimeUtil.convertToOdooFieldDate((Timestamp) data.get("by_time")));
	        map.put("total_budget_value",data.get("total"));
	        map.put("purchase_prototype_id.id",data.get("is_prototype").equals("1") ? "1" : "2");
	        map.put("purchase_type_id.id", data.get("objective_type"));
	        map.put("purchase_method_id.id",data.get("method_id"));
	        map.put("description",data.get("reason"));
	        map.put("objective",data.get("objective"));
	        map.put("currency_id.id",data.get("currency_id"));
	        map.put("currency_rate",data.get("currency_rate"));
	        map.put("delivery_address", data.get("location"));
	        map.put("date_start",CommonDateTimeUtil.convertToOdooFieldDate((Timestamp) data.get("created_time")));
//	        map.put("operating_unit_id.id",data.get("operating_unit_id"));
	        map.put("org_id",data.get("pur_org_id"));
	        map.put("request_ref_id", data.get("ref_id"));
	        map.put("purchase_price_range_id.id", data.get("price_range_id")!=null ? data.get("price_range_id") : "");
	        map.put("purchase_condition_id.id", data.get("condition_id")!=null ? data.get("condition_id") : "");
	        map.put("purchase_confidential_id.id", data.get("confidential_id")!=null ? data.get("confidential_id") : "");
	        map.put("confidential_detail", data.get("method_cond2_dtl"));
	        
	        for(String key : map.keySet()) {
	        	log.info(" - "+key+":"+map.get(key));
	        }
	        
	        /*
	         * Line Item
	         */
			list = expBrwService.listDtlForInf(model.getId());
	        
	        List orderLine = new ArrayList();
	        
	        for(Map<String, Object> dtl:list) {
		        Map<String, Object> line = new HashMap<String, Object>();
		        line.put("product_id.id","");
		        line.put("name",dtl.get("description")); 
		        line.put("product_qty",dtl.get("quantity")); 
		        line.put("price_unit",dtl.get("price_cnv")); ////////
		        line.put("product_uom_id.id",dtl.get("unit_id")); 
	//	        line.put("activity_id.id","");
	//	        line.put("date_required",CommonDateTimeUtil.convertToOdooFieldDate((Timestamp) data.get("updated_time"))); 
		        line.put("section_id.id",dtl.get("budget_cc"));
		        line.put("cost_control_id.id",dtl.get("cost_control_id")!=null ? dtl.get("cost_control_id") : "");
		        line.put("fixed_asset","False");
		        line.put("tax_ids",dtl.get("vat_name")!=null ? dtl.get("vat_name") : "");
		        orderLine.add(line);
		        
		        for(String key : line.keySet()) {
		        	log.info(" -- "+key+":"+line.get(key));
		        }
	        }
	        
	        map.put("line_ids", orderLine);
	
	        /*
	         * Attachment
	         */
	        List<FileModel> fileList = expBrwService.listFile(model.getId(), true);
	        log.info("fileList.size()="+fileList.size());
	        
	        List attachment = new ArrayList();
	        
	        for(FileModel file:fileList) {
	// detail : http://localhost:18080/share/page/document-details?nodeRef=workspace://SpacesStore/5190b027-00c6-4322-98c3-1be01314bdd9
	//download format url : http://localhost:18080/share/proxy/alfresco/api/node/content/workspace/SpacesStore/<url>/<name>?a=true
	//example url : http://localhost:18080/share/proxy/alfresco/api/node/content/workspace/SpacesStore/260cdb8d-5a9c-43af-9c8e-178c75f1fe38/PR16000050.pdf?a=true
	        	
		        Map<String, Object> att = new HashMap<String, Object>();
		        att.put("name", file.getName()); 
		        att.put("description", file.getDesc()!=null ? file.getDesc() : ""); 
		        att.put("file_url",NodeUtil.trimNodeRef(file.getNodeRef().toString()));
		        attachment.add(att);
		        
		        for(String key : att.keySet()) {
		        	log.info(" ---- "+key+":"+att.get(key));
		        }
	        }
	        
	        map.put("attachment_ids", attachment);
	        
	        log.info("map="+map);
			
			a.add(map);
			
			args.add(a);

			Object obj = client.invoke("execute_kw", args);
			
			XmlRpcStruct strc = (XmlRpcStruct)obj;
			
			for(Object k : strc.keySet()) {
				Object v = strc.get(k);
	
				log.info(" - "+k+" : "+v+":"+v.getClass().getName());
			}
			
			success = (Boolean)strc.get("is_success");
			msgs = strc.get("messages")!=null ? strc.get("messages").toString() : "";
		}
		catch (Exception ex) {
			return ex.toString()+":"+map.toString();
		}
		
		return success ? "OK" : msgs+":"+map.toString();
	}
	
}
