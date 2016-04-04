package pb.repo.admin.wscript;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import pb.common.constant.CommonConstant;
import pb.common.model.ComboBoxModel;
import pb.common.util.CommonUtil;
import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.model.MainCurrencyModel;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.model.MainProductUomModel;
import pb.repo.admin.service.AdminCurrencyService;
import pb.repo.admin.service.AdminMasterService;
import pb.repo.admin.service.AdminProductUomService;
import pb.repo.admin.util.MainCurrencyUtil;
import pb.repo.admin.util.MainMasterUtil;

import com.github.dynamicextensionsalfresco.webscripts.annotations.HttpMethod;
import com.github.dynamicextensionsalfresco.webscripts.annotations.RequestParam;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;

@Component
@WebScript
//@Authentication(value=AuthenticationType.ADMIN)
public class AdminMainCurrencyWebScript {
	
	private static Logger log = Logger.getLogger(AdminMainCurrencyWebScript.class);
	
	private static final String URI_PREFIX = CommonConstant.GLOBAL_URI_PREFIX + "/admin/main/currency";
	
	@Autowired
	private AdminCurrencyService currencyService;


  /**
   * Handles the "list" request. Note the use of Spring MVC-style annotations to map the Web Script URI configuration
   * and request handling objects.
   * 
   * @param response
   * @throws Exception
   */
  @Uri(URI_PREFIX+"/list")
  public void handleList(final WebScriptResponse response)  throws Exception {
    
		String json = null;
		
		try {
			List<MainCurrencyModel> list = currencyService.list();
			List<ComboBoxModel> cmbList = MainCurrencyUtil.convertToComboBoxList(list);
			json = CommonUtil.jsonSuccess(cmbList);
			
		} catch (Exception ex) {
			log.error("", ex);
			json = CommonUtil.jsonFail(ex.toString());
			throw ex;
			
		} finally {
			CommonUtil.responseWrite(response, json);
		}
    
  }
  
}
