package pb.repo.pcm.service;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.common.model.FileModel;
import pb.common.util.CommonDateTimeUtil;
import pb.common.util.NodeUtil;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.pcm.model.PcmOrdModel;
import pb.repo.pcm.model.PcmReqModel;
import redstone.xmlrpc.XmlRpcClient;
import redstone.xmlrpc.XmlRpcStruct;

@Service
public class InterfaceService {

	private static Logger log = Logger.getLogger(InterfaceService.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	PcmReqService pcmReqService;
	
	@Autowired
	AdminMasterService masterService;
	
	public void createPR(PcmReqModel model) throws Exception {
		log.info("interface : createPR");
		
		MainMasterModel sysCfgModel = masterService.getSystemConfig(MainMasterConstant.SCC_MAIN_ODOO_URL);
//		String host = "http://10.226.202.133:8069";
		String host = sysCfgModel.getFlag1();
		
		sysCfgModel = masterService.getSystemConfig(MainMasterConstant.SCC_MAIN_ODOO_DB);
//		String db = "PABI2";
		String db = sysCfgModel.getFlag1();
		
		log.info("host:"+host);
		log.info("db:"+db);
		
		List<Map<String, Object>> list = pcmReqService.listForInf(model.getId());
		Map<String, Object> data = list.size() > 0 ? list.get(0) : null;
		
		final XmlRpcClient client = new XmlRpcClient(new URL(host+"/xmlrpc/2/object"), true);
		log.info("client:"+client);
		
		String rmtObj = "purchase.request";
		
		List arguments = new ArrayList();
		arguments.add(db); // db name
		arguments.add(1); // uid 1='admin'
		arguments.add("admin"); // password
		arguments.add(rmtObj);
		arguments.add("generate_purchase_request");
		
		List a = new ArrayList();
		Map<String, Object> map = new HashMap<String, Object>();
		
		/*
		 * Header
		 */
        map.put("name", data.get("req_id"));
        map.put("requested_by",data.get("req_by"));
        map.put("responsible_user_id",data.get("rp_id")!=null ? data.get("rp_id") : "");
        map.put("assigned_to",data.get("last_approver"));
        map.put("date_approved",CommonDateTimeUtil.convertToOdooFieldDate((Timestamp) data.get("by_time")));
        map.put("total_budget_value",data.get("total"));
        map.put("purchase_prototype_id.id",data.get("is_prototype").equals("1") ? "1" : "2");
        map.put("purchase_type_id.id", data.get("objective_type"));
        map.put("purchase_method_id.id",data.get("method"));
        map.put("purchase_unit_id.id",data.get("pcm_section_id"));
        map.put("description",data.get("reason"));
        map.put("objective",data.get("objective"));
        map.put("currency_id.id",data.get("currency_id"));
        map.put("currency_rate",data.get("currency_rate"));
        map.put("delivery_address", data.get("location"));
        map.put("date_start",CommonDateTimeUtil.convertToOdooFieldDate((Timestamp) data.get("created_time")));
        map.put("operating_unit_id.id",data.get("operating_unit_id"));
        
        for(String key : map.keySet()) {
        	log.info(" - "+key+":"+map.get(key));
        }
        
        /*
         * Line Item
         */
		list = pcmReqService.listDtlForInf(model.getId());
        
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
        }
        
        map.put("line_ids", orderLine);

        /*
         * Committee
         */
		list = pcmReqService.listCmtForInf(model.getId());
		
        List committee = new ArrayList();

        int seq = 1;
        for(Map<String, Object> cmtData:list) {
	        Map<String, Object> cmt = new HashMap<String, Object>();
	        cmt.put("name",cmtData.get("first_name")+" "+cmtData.get("last_name")); 
	        cmt.put("position",cmtData.get("position"));
//	        cmt.put("responsible",cmtData.get("committee")); 
	        cmt.put("committee_type","tor");
	        cmt.put("sequence", String.valueOf(seq++));  /////////////////
	        committee.add(cmt);
	        
	        for(String key : cmtData.keySet()) {
	        	log.info(" -- "+key+":"+cmtData.get(key));
	        }
        }
        
        map.put("committee_ids", committee);

        /*
         * Attachment
         */
        List<FileModel> fileList = pcmReqService.listFile(model.getId(), true);
        log.info("fileList.size()="+fileList.size());
        
        List attachment = new ArrayList();
        
        for(FileModel file:fileList) {
        	log.info("name:"+file.getName());
        	log.info("path:"+file.getPath());
        	log.info("action:"+file.getAction());
        	log.info("Node Ref:"+file.getNodeRef());
// detail : http://localhost:18080/share/page/document-details?nodeRef=workspace://SpacesStore/5190b027-00c6-4322-98c3-1be01314bdd9
//download format url : http://localhost:18080/share/proxy/alfresco/api/node/content/workspace/SpacesStore/<url>/<name>?a=true
//example url : http://localhost:18080/share/proxy/alfresco/api/node/content/workspace/SpacesStore/260cdb8d-5a9c-43af-9c8e-178c75f1fe38/PR16000050.pdf?a=true
        	
	        Map<String, Object> att = new HashMap<String, Object>();
	        att.put("name", file.getName()); 
	        att.put("file_url",NodeUtil.trimNodeRef(file.getNodeRef().toString()));
	        attachment.add(att);
        }
        
        map.put("attachment_ids", attachment);
        
        log.info("map="+map);;
		
		a.add(map);
		
		arguments.add(a);

		List ids = null;
		XmlRpcStruct strc = null;
		
		Object obj = client.invoke("execute_kw", arguments);
		
		if (obj.getClass().getName().indexOf("List") >= 0) {
			ids = (List)obj;
			for (Object o : ids) {
				log.info(" -"+o+":"+o.getClass().getName());
			}
		} else {
			strc = (XmlRpcStruct)obj;
			
			for(Object k : strc.keySet()) {
				Object v = strc.get(k);

				log.info(" - "+k+" : "+v+":"+v.getClass().getName());
			}
		}
		
	}
	
	public void updateStatusPD(PcmOrdModel model) throws Exception {
		log.info("interface : updateStatusPD");
		
//		MainMasterModel sysCfgModel = masterService.getSystemConfig(MainMasterConstant.SCC_MAIN_ODOO_URL);
////		String host = "http://10.226.202.133:8069";
//		String host = sysCfgModel.getFlag1();
//		
//		sysCfgModel = masterService.getSystemConfig(MainMasterConstant.SCC_MAIN_ODOO_DB);
////		String db = "PABI2";
//		String db = sysCfgModel.getFlag1();
//		
//		log.info("host:"+host);
//		log.info("db:"+db);
//		
//		List<Map<String, Object>> list = pcmReqService.listForInf(model.getId());
//		Map<String, Object> data = list.size() > 0 ? list.get(0) : null;
//		
//		final XmlRpcClient client = new XmlRpcClient(new URL(host+"/xmlrpc/2/object"), true);
//		log.info("client:"+client);
//		
//		String rmtObj = "purchase.request";
//		
//		List arguments = new ArrayList();
//		arguments.add(db); // db name
//		arguments.add(1); // uid 1='admin'
//		arguments.add("admin"); // password
//		arguments.add(rmtObj);
//		arguments.add("generate_purchase_request");
//		
//		List a = new ArrayList();
//		Map<String, Object> map = new HashMap<String, Object>();
//		
//		/*
//		 * Header
//		 */
//        map.put("name", data.get("req_id"));
//        map.put("requested_by.id",data.get("emp_id"));
//        map.put("responsible_user_id.id",data.get("rp_id")!=null ? data.get("rp_id") : "");
//        map.put("assigned_to.id","1");//////////////////////////////////////////////////////
//        map.put("date_approved",CommonDateTimeUtil.convertToOdooFieldDate((Timestamp) data.get("updated_time")));
//        map.put("total_budget_value",data.get("total"));
//        map.put("purchase_prototype_id.id",data.get("is_prototype").equals("1") ? "1" : "2");
//        map.put("purchase_type_id.id", data.get("objective_type"));
//        map.put("purchase_method_id.id",data.get("method"));
//        map.put("purchase_unit_id.id",data.get("pcm_section_id"));
//        map.put("description",data.get("reason"));
//        map.put("objective",data.get("objective"));
//        map.put("currency_id.id",data.get("currency_id"));
//        map.put("currency_rate",data.get("currency_rate"));
//        map.put("delivery_address", data.get("location"));
//        map.put("date_start",CommonDateTimeUtil.convertToOdooFieldDate((Timestamp) data.get("created_time")));
//        map.put("operating_unit_id.id",data.get("operating_unit_id"));
//        
//        for(String key : map.keySet()) {
//        	log.info(" - "+key+":"+map.get(key));
//        }
//        
//		a.add(map);
//		
//		arguments.add(a);
//
//		List ids = null;
//		XmlRpcStruct strc = null;
//		
//		Object obj = client.invoke("execute_kw", arguments);
//		
//		if (obj.getClass().getName().indexOf("List") >= 0) {
//			ids = (List)obj;
//			for (Object o : ids) {
//				log.info(" -"+o+":"+o.getClass().getName());
//			}
//		} else {
//			strc = (XmlRpcStruct)obj;
//			
//			for(Object k : strc.keySet()) {
//				Object v = strc.get(k);
//
//				log.info(" - "+k+" : "+v+":"+v.getClass().getName());
//			}
//		}
		
	}
	
}
