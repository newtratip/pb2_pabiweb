package pb.repo.admin.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.common.constant.CommonConstant;
import pb.common.util.CommonDateTimeUtil;
import pb.repo.admin.constant.InterfaceConstant;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.model.MainMasterModel;
import redstone.xmlrpc.XmlRpcClient;
import redstone.xmlrpc.XmlRpcStruct;

@Service("mainInterfaceService")
public class InterfaceService {

	private static Logger log = Logger.getLogger(InterfaceService.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	AdminMasterService masterService;
	
	@Autowired
	AdminSectionService sectionService;
	
	@Autowired
	AdminUserService userService;
	
	@Autowired
	AuthenticationService authService;
	
	private List<Object> getInitArgs(Map<String, Object> cfg) {
		List<Object> args = new ArrayList();
		args.add(cfg.get("db")); // db name
		args.add(cfg.get("usr")); // uid 1='admin'
		args.add(cfg.get("pwd")); // password
		
		return args;
	}

	private Map<String, Object> getConnectionConfig(String login, String password) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		MainMasterModel sysCfgModel = masterService.getSystemConfig(MainMasterConstant.SCC_EXP_ODOO_URL);
		String host = sysCfgModel.getFlag1();
		
		sysCfgModel = masterService.getSystemConfig(MainMasterConstant.SCC_EXP_ODOO_DB);
		String db = sysCfgModel.getFlag1();
		
		Map<String,Object> user = userService.getByLogin(login);
		
		Integer usr = (Integer)user.get("id"); // uid 1='admin'
		String pwd = password;
		
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
	
	public Map<String, Object> getBudgetControlLevel(Timestamp time, String login) throws Exception {
		log.info("sub interface : getBudgetControlLevel");
		
		log.info("  time:"+time.toString());
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Map<String, Object> cfg = getConnectionConfig(login,"password");
		XmlRpcClient client = getXmlRpcClient(cfg);
		
		List args = getInitArgs(cfg);
		args.add("account.budget"); // Remote Object
		args.add("get_fiscal_and_budget_level");
		
		List a = new ArrayList();
//		Map<String, Object> map = new HashMap<String, Object>();
//		a.add(map);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
		
		a.add(df.format(time));
		
		args.add(a);

		Object obj = client.invoke("execute_kw", args);
		
		
		XmlRpcStruct strc = (XmlRpcStruct)obj;
		
		for(Object k : strc.keySet()) {
			Object v = strc.get(k);

			log.info(" - "+k+" : "+v+":"+v.getClass().getName());
			
			map.put((String)k, v);
		}

		
		return map;
	}	
	
	public Double getBudget(String budgetCcType,Integer budgetCc, Integer fundId) throws Exception {
		log.info("interface : getBudget");
		
		Boolean success = false;
		Map<String, Object> map = new HashMap<String, Object>();
		
		Double balance = 0.0;
		
		try {
			Map<String, Object> cfg = getConnectionConfig(CommonConstant.EXT_ADMIN_USER,CommonConstant.EXT_ADMIN_PASSWORD);
			XmlRpcClient client = getXmlRpcClient(cfg);
			
			List args = getInitArgs(cfg);
			args.add("account.budget"); // Remote Object
			args.add("simple_check_budget"); // method
			
			/*
			 * Parameters
			 */
			List a = new ArrayList();
			
    		String budgetType = (String)InterfaceConstant.BUDGET_TYPE.get(budgetCcType);
    		
	        a.add(CommonDateTimeUtil.convertToOdooFieldDate(CommonDateTimeUtil.now()));
	        a.add(budgetType);
	        
	        a.add(false);
	        a.add(budgetCc);
	        a.add(budgetCcType.equals("P") ? fundId : false);
	        
	        /*
	         * Final
	         */
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
			
			success = (Boolean)strc.get("budget_ok");
			XmlRpcStruct budget = (XmlRpcStruct)strc.get("budget_status");
			balance = (Double)budget.get("amount_balance");
		}
		catch (Exception ex) {
			log.error(map.toString(),ex);
		}
		
		return success ? balance : 0;
	}	
	
}
