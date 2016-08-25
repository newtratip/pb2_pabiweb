package pb.repo.exp.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.common.model.FileModel;
import pb.common.util.CommonDateTimeUtil;
import pb.common.util.NodeUtil;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminSectionService;
import pb.repo.admin.service.AdminUserService;
import pb.repo.exp.constant.ExpBrwAttendeeConstant;
import pb.repo.exp.model.ExpBrwModel;
import pb.repo.exp.model.ExpBrwAttendeeModel;
import pb.repo.exp.model.ExpUseModel;
import redstone.xmlrpc.XmlRpcClient;
import redstone.xmlrpc.XmlRpcStruct;

@Service
public class InterfaceService {

	private static Logger log = Logger.getLogger(InterfaceService.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	ExpBrwService expBrwService;
	
	@Autowired
	ExpUseService expUseService;
	
	@Autowired
	AdminMasterService masterService;
	
	@Autowired
	AdminSectionService sectionService;
	
	@Autowired
	AdminUserService userService;
	
	private List<Object> getInitArgs(Map<String, Object> cfg) {
		List<Object> args = new ArrayList();
		args.add(cfg.get("db")); // db name
		args.add(cfg.get("usr")); // uid 1='admin'
		args.add(cfg.get("pwd")); // password
		
		return args;
	}

	private Map<String, Object> getConnectionConfig(String login) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		MainMasterModel sysCfgModel = masterService.getSystemConfig(MainMasterConstant.SCC_EXP_ODOO_URL);
		String host = sysCfgModel.getFlag1();
		
		sysCfgModel = masterService.getSystemConfig(MainMasterConstant.SCC_EXP_ODOO_DB);
		String db = sysCfgModel.getFlag1();
		
		Map<String,Object> user = userService.getByLogin(login);
		
		Integer usr = (Integer)user.get("id"); // uid 1='admin'
		String pwd = "password"; // password
		
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
		log.info("interface : createAV");
		
		Boolean success = false;
		String msgs = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			Map<String, Object> cfg = getConnectionConfig(model.getCreatedBy());
			XmlRpcClient client = getXmlRpcClient(cfg);
			
			List<Map<String, Object>> list = expBrwService.listForInf(model.getId());
			Map<String, Object> data = list.size() > 0 ? list.get(0) : null;
			
			List args = getInitArgs(cfg);
			args.add("hr.expense.expense"); // Remote Object
			args.add("generate_hr_expense"); // method
			
			List a = new ArrayList();
			
			/*
			 * Header
			 */
//			'is_employee_advance': u'True',
//            'number': u'/',  # av_id
//            'employee_code': u'002648',  # req_by
//            'date': u'2016-01-31',  # by_time
//            'write_date': u'2016-01-31 00:00:00',  # updated_time
//            'advance_type': u'attend_seminar',  # or by_product, objective_type
//            'date_back': u'2016-10-30',  # cost_control_to
//            'name': u'Object of this Advance',  # objec
	        map.put("is_employee_advance", "True");
	        map.put("number",data.get("av_id"));
	        map.put("employee_code",data.get("req_by"));
	        map.put("date",CommonDateTimeUtil.convertToOdooFieldDate(data.get("by_time")!=null ? (Timestamp) data.get("by_time") : CommonDateTimeUtil.now()));
	        map.put("write_date",CommonDateTimeUtil.convertToOdooFieldDateTime((Timestamp) data.get("updated_time")));
	        map.put("advance_type",data.get("objective_type"));
	        map.put("date_back",CommonDateTimeUtil.convertToOdooFieldDate(data.get("cost_control_to")!=null ? (Timestamp) data.get("cost_control_to") : CommonDateTimeUtil.now()));
	        map.put("name", data.get("objective"));
	        map.put("apweb_ref_url", "");
	        //map.put("operating_unit_id.id", 14);
	        
	        for(String key : map.keySet()) {
	        	log.info(" - "+key+":"+map.get(key));
	        }
	        
	        /*
	         * Line Item
	         */
			list = expBrwService.listDtlForInf(model.getId());
	        
	        List orderLine = new ArrayList();
	        
//	        'line_ids': (  # 1 line only, Advance
//            {
//             'name': u'Employee Advance',  # Expense Note (not in AF?)
//             'unit_amount': u'2000',  # total
//             'cost_control_id.id': u'',
//             },
//        ),	        
	        
	        for(Map<String, Object> dtl:list) {
		        Map<String, Object> line = new HashMap<String, Object>();
		        line.put("is_advance_product_line","True");
		        line.put("name",dtl.get("activity"));
		        line.put("unit_amount",String.valueOf(dtl.get("amount")));
		        line.put("cost_control_id.id","");
		        orderLine.add(line);
		        
		        for(String key : line.keySet()) {
		        	log.info(" -- "+key+":"+line.get(key));
		        }
	        }
	        
	        map.put("line_ids", orderLine);
	
	        /*
	         * Employee
	         */
	        List<ExpBrwAttendeeModel> empList = expBrwService.listAttendeeByMasterIdAndType(model.getId(), ExpBrwAttendeeConstant.T_EMPLOYEE);
	        log.info("empList.size()="+empList.size());
	        
	        List employees = new ArrayList();
	        
//	          'attendee_employee_ids': (
//	                  {
//	                   'employee_code': u'000143',
//	                   'position_id.id': u'1',
//	                   },
//	                  {
//	                   'employee_code': u'000165',
//	                   'position_id.id': u'2',
//	                   },
//	                  {
//	                   'employee_code': u'000166',
//	                   'position_id.id': u'3',
//	                   },
//	                  {
//	                   'employee_code': u'000177',
//	                   'position_id.id': u'4',
//	                   },
//	              ),	        
	        
	        for(ExpBrwAttendeeModel emp:empList) {
	        	
		        Map<String, Object> employee = new HashMap<String, Object>();
		        employee.put("employee_code", emp.getCode()); 
		        employee.put("position_id.id", emp.getPositionId()!=null ? emp.getPositionId() : "");  ///////////////////////////////// 
		        employees.add(employee);
		        
		        for(String key : employee.keySet()) {
		        	log.info(" ---- "+key+":"+employee.get(key));
		        }
	        }
	        
	        map.put("attendee_employee_ids", employees);
	        
			/*
			 * Non employee
			 */
	        List<ExpBrwAttendeeModel> nonempList = expBrwService.listAttendeeByMasterIdAndType(model.getId(), ExpBrwAttendeeConstant.T_OTHER);
	        log.info("nonempList.size()="+nonempList.size());
	        
	        List nonemployees = new ArrayList();
	        
//            'attendee_external_ids': (
//                    {
//                     'attendee_name': u'Walai Charoenchaimongkol',
//                     'position': u'Manager',
//                     },
//                )
     	        
	        for(ExpBrwAttendeeModel nonemp:nonempList) {
	        	
		        Map<String, Object> nonemployee = new HashMap<String, Object>();
		        nonemployee.put("attendee_name", nonemp.getFname()+" "+nonemp.getLname()); 
		        nonemployee.put("position", nonemp.getPosition()!=null ? nonemp.getPosition() : ""); 
		        nonemployees.add(nonemployee);
		        
		        for(String key : nonemployee.keySet()) {
		        	log.info(" ----- "+key+":"+nonemployee.get(key));
		        }
	        }
	        
	        map.put("attendee_external_ids", nonemployees);
	        
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
		        att.put("url",NodeUtil.trimNodeRef(file.getNodeRef().toString()));
		        attachment.add(att);
		        
		        for(String key : att.keySet()) {
		        	log.info(" ------ "+key+":"+att.get(key));
		        }
	        }
	        
	        map.put("attachment_ids", attachment);
	        
	        /*
	         * Final
	         */
			a.add(map);
			
			args.add(a);

	        log.info("args="+args);
	        
	        /*
	         * Call
	         */
			Object res = client.invoke("execute_kw", args);
			
			/*
			 * Result
			 */
			log.info("res="+res);
			
			XmlRpcStruct strc = (XmlRpcStruct)res;
			
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
		log.info("interface : createAP");
		
		Boolean success = false;
		String msgs = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			Map<String, Object> cfg = getConnectionConfig(model.getCreatedBy());
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
	        map.put("is_employee_advance", "False");
	        map.put("name", data.get("objective_type"));
	        map.put("number",data.get("id"));
	        map.put("employee_code",data.get("req_by"));
	        map.put("date",CommonDateTimeUtil.convertToOdooFieldDate((Timestamp) data.get("created_time")));
	        map.put("write_date",CommonDateTimeUtil.convertToOdooFieldDate((Timestamp) data.get("by_time")));
	        map.put("advance_type",data.get("advance_type"));
	        map.put("date_back",CommonDateTimeUtil.convertToOdooFieldDate((Timestamp) data.get("back_time")));
	        
	        for(String key : map.keySet()) {
	        	log.info(" - "+key+":"+map.get(key));
	        }
	        
	        /*
	         * Line Item
	         */
			list = expBrwService.listDtlForInf(model.getId());
	        
	        List orderLine = new ArrayList();
	        
//	        'line_ids': (  # 1 line only, Advance
//	                {
//	                 'name': u'Employee Advance',  # Expense Note (not in AF?)
//	                 'unit_amount': u'2000',  # total
//	                 'cost_control_id.id': u'',
//	                 },
//	            ),	        
	        
	        for(Map<String, Object> dtl:list) {
		        Map<String, Object> line = new HashMap<String, Object>();
		        line.put("name",dtl.get("description")); 
		        line.put("unit_amount",dtl.get("total")); 
		        line.put("cost_control_id.id",dtl.get("cost_control_id")!=null ? dtl.get("cost_control_id") : "");
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
	        
	        List employees = new ArrayList();
	        
//	          'attendee_employee_ids': (
//	                  {
//	                   'employee_code': u'000143',
//	                   'position_id.id': u'1',
//	                   },
//	                  {
//	                   'employee_code': u'000165',
//	                   'position_id.id': u'2',
//	                   },
//	                  {
//	                   'employee_code': u'000166',
//	                   'position_id.id': u'3',
//	                   },
//	                  {
//	                   'employee_code': u'000177',
//	                   'position_id.id': u'4',
//	                   },
//	              ),	        
	        
	        for(FileModel file:fileList) {
	// detail : http://localhost:18080/share/page/document-details?nodeRef=workspace://SpacesStore/5190b027-00c6-4322-98c3-1be01314bdd9
	//download format url : http://localhost:18080/share/proxy/alfresco/api/node/content/workspace/SpacesStore/<url>/<name>?a=true
	//example url : http://localhost:18080/share/proxy/alfresco/api/node/content/workspace/SpacesStore/260cdb8d-5a9c-43af-9c8e-178c75f1fe38/PR16000050.pdf?a=true
	        	
		        Map<String, Object> att = new HashMap<String, Object>();
		        att.put("employee_code", file.getName()); 
		        att.put("position_id.id", file.getDesc()!=null ? file.getDesc() : ""); 
		        employees.add(att);
		        
		        for(String key : att.keySet()) {
		        	log.info(" ---- "+key+":"+att.get(key));
		        }
	        }
	        
	        map.put("attendee_employee_ids", employees);
	        
	        log.info("map="+map);
			
			a.add(map);
			
			/*
			 * Non employee
			 */
	        List nonemployees = new ArrayList();
	        
//            'attendee_external_ids': (
//                    {
//                     'attendee_name': u'Walai Charoenchaimongkol',
//                     'position': u'Manager',
//                     },
//                )
     	        
	        for(FileModel file:fileList) {
	// detail : http://localhost:18080/share/page/document-details?nodeRef=workspace://SpacesStore/5190b027-00c6-4322-98c3-1be01314bdd9
	//download format url : http://localhost:18080/share/proxy/alfresco/api/node/content/workspace/SpacesStore/<url>/<name>?a=true
	//example url : http://localhost:18080/share/proxy/alfresco/api/node/content/workspace/SpacesStore/260cdb8d-5a9c-43af-9c8e-178c75f1fe38/PR16000050.pdf?a=true
	        	
		        Map<String, Object> att = new HashMap<String, Object>();
		        att.put("attendee_name", file.getName()); 
		        att.put("position", file.getDesc()!=null ? file.getDesc() : ""); 
		        nonemployees.add(att);
		        
		        for(String key : att.keySet()) {
		        	log.info(" ---- "+key+":"+att.get(key));
		        }
	        }
	        
	        map.put("attendee_external_ids", nonemployees);
	        
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
