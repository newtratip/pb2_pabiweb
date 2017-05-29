package pb.repo.exp.xmlrpc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.security.PermissionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.common.util.CommonDateTimeUtil;
import pb.repo.admin.constant.MainWkfConfigDocTypeConstant;
import pb.repo.admin.constant.MainWorkflowConstant;
import pb.repo.exp.constant.ExpUseConstant;
import pb.repo.exp.model.ExpUseModel;
import pb.repo.exp.service.ExpUseService;
import pb.repo.exp.service.ExpUseWorkflowService;

@Service
public class ExpUseInvocationHandler
{
	
	private static Logger log = Logger.getLogger(ExpUseInvocationHandler.class);

	@Autowired
	private ExpUseService expUseService;
	
	@Autowired
	private ExpUseWorkflowService mainWorkflowService;
	
	@Autowired
	private PermissionService permissionService;
	
    public Map<String, Object> action(Map<String, Object> params)
    {
    	log.info("action()");
    	log.info("params:"+params.toString());
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	try {
    		String statusDesc = null;
    		
	    	ExpUseModel expUseModel = null;
	    	
	    	String action = (String)params.get("action");
	    	String type = null;
	    	String apNo = (String)params.get("exNo");
	    	final String by = (String)params.get("by");

			if (action.equals("1")) {
				type = ExpUseConstant.ST_CLOSED_BY_FIN;
				statusDesc = "Approve";
				
		    	expUseModel = expUseService.get(apNo, null);
				final NodeRef folderNodeRef = new NodeRef(expUseModel.getFolderRef());
				
		        AuthenticationUtil.runAs(new RunAsWork<String>()
	    	    {
	    			public String doWork() throws Exception
	    			{
		        		if(!permissionService.getPermissions(folderNodeRef).contains(by)) {
		        			permissionService.setPermission(folderNodeRef, by, "SiteCollaborator", true);
		        		}

	    	        	return null;
	    			}
	    	    }, AuthenticationUtil.getAdminUserName());  
				
			} else 
			if (action.equals("2")) {
				statusDesc = "Cancel";
				type = ExpUseConstant.ST_CANCEL_BY_FIN;
//			} else
//			if (action.equals("3")) {
//				statusDesc = "Paid";
//				type = ExpUseConstant.ST_PAID_BY_FIN;
			} else {
    			result.put("success",false);
    			result.put("message","Invalid Action : "+action);
    			return result;
			}
    		
			if (expUseModel==null) {
				expUseModel = expUseService.get(apNo, null);
			}
	    	expUseModel.setStatus(type);
	    	expUseService.update(expUseModel);
    		
	    	mainWorkflowService.setModuleService(expUseService);
			mainWorkflowService.saveWorkflowHistory(null, by, MainWorkflowConstant.TN_FINANCE,  (String)params.get("comment"), statusDesc, null,  apNo, null, type);
			
			result.put("success",true);
			result.put("message","Success");
    	} catch (Exception ex) {
    		log.error(ex);
    		ex.printStackTrace();
			result.put("success",false);
			result.put("message","Error : "+ex.toString());
    	}
    	
        return result;
    }
    
    public Map<String, Object> history(Map<String, Object> params)
    {
    	log.info("history()");
    	log.info("params:"+params.toString());
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	try {
	    	String exNo = (String)params.get("exNo");
	    	final String by = (String)params.get("by");
	    	String task = (String)params.get("task");
	    	String taskTh = (String)params.get("task_th");
	    	String status = (String)params.get("status");
	    	String statusTh = (String)params.get("status_th");
	    	
	    	ExpUseModel expUseModel = expUseService.get(exNo, null);
			if (expUseModel==null) {
    			result.put("success",false);
    			result.put("message","Invalid EX NO. : "+exNo);
    			return result;
			}
    		
			mainWorkflowService.saveWorkflowHistory(exNo, by, task, taskTh, status, statusTh, (String)params.get("comment"));
			
			result.put("success",true);
			result.put("message","Success");
    	} catch (Exception ex) {
    		log.error(ex);
    		ex.printStackTrace();
			result.put("success",false);
			result.put("message","Error : "+ex.toString());
    	}
    	
        return result;
    }
    
    public Map<String, Object> create(Map<String, Object> params)
    {
    	log.info("create()");
    	log.info("params:"+params.toString());
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	try {
    		String statusDesc = null;
    		
	    	ExpUseModel expUseModel = null;
	    	
	    	String action = (String)params.get("action");
//	    	String exNo = (String)params.get("exNo");
	    	String exNo = null;
	    	final String by = (String)params.get("by");
	    	
	    	Boolean found = false;
	    	ExpUseModel model = expUseService.get(exNo, null);
    		if (model==null) {
    			model = new ExpUseModel();
        		model.setId(exNo);
        		model.setCreatedBy((String)params.get("reqBy"));
    		} else {
    			found = true;
    		}
    		model.setUpdatedBy(by);

			if (action.equals("1")) {
    			model.setReqBy((String)params.get("reqBy"));
        		
    			model.setObjective((String)params.get("objective"));
    			model.setReason((String)params.get("reason"));
    			model.setNote((String)params.get("note"));
				model.setBudgetCcType((String)params.get("budgetSrcType"));
				model.setBudgetCc(params.get("budgetSrc") != null && !params.get("budgetSrc").equals("") ? Integer.parseInt((String)params.get("budgetSrc")) : null);
				model.setFundId(params.get("fundId") != null && !params.get("fundId").equals("") ? Integer.parseInt((String)params.get("fundId")) : null);
				
				model.setCostControlTypeId(Integer.parseInt((String)params.get("costControlTypeId")));
				model.setCostControlId(Integer.parseInt((String)params.get("costControlId")));
				model.setCostControl((String)params.get("costControl"));
				model.setCostControlFrom(CommonDateTimeUtil.convertOdooStringToTimestamp((String)params.get("costControlFrom")));
				model.setCostControlTo(CommonDateTimeUtil.convertOdooStringToTimestamp((String)params.get("costControlTo")));
				
				model.setBankType((String)params.get("bankType"));
				model.setBank(params.get("bank") != null && !params.get("bank").equals("") ? Integer.parseInt((String)params.get("bank")) : null);
				
				model.setPayType((String)params.get("payType"));
				model.setPayDtl1((String)params.get("payDtl1"));
				model.setPayDtl2((String)params.get("payDtl2"));
				model.setPayDtl3((String)params.get("payDtl3"));
				
	    		model.setTotal(Double.parseDouble((String)params.get("total")));
				
				model.setRequestedTime(CommonDateTimeUtil.now());
				
				model.setStatus(ExpUseConstant.ST_WAITING);
				model.setWaitingLevel(1);

	    		List<Map<String, Object>> fileList = (List<Map<String,Object>>) params.get("attachments");
	    		
	    		Object attendees = params.get("attendees");
	    		Object items = params.get("items");
	    		
				model = expUseService.save(model, attendees, items, fileList, true);
				
				mainWorkflowService.setModuleService(expUseService);
				mainWorkflowService.startWorkflow(model, MainWkfConfigDocTypeConstant.DT_EX);
			} else {
    			result.put("success",false);
    			result.put("message","Invalid Action : "+action);
    			return result;
			}
    		
			result.put("success",true);
			result.put("exNO", model.getId());
			result.put("message","Success");
    	} catch (Exception ex) {
    		log.error(ex);
    		ex.printStackTrace();
			result.put("success",false);
			result.put("message","Error : "+ex.toString());
    	}
    	
        return result;
    }

}
