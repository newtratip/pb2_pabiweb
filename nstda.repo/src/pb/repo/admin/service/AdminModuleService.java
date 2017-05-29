package pb.repo.admin.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import pb.repo.admin.dao.MainModuleDAO;
import pb.repo.common.mybatis.DbConnectionFactory;

@Service
public class AdminModuleService {
	
	private static Logger log = Logger.getLogger(AdminModuleService.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	AuthenticationService authService;
	
	@Autowired
	@Qualifier("mainInterfaceService")
	InterfaceService interfaceService;

	public Map<String, Object> getTotalPreBudget(String budgetCcType, Integer budgetCc, Integer fundId, String prId, String exId) {
		
		Map<String, Object> map = null;
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainModuleDAO dao = session.getMapper(MainModuleDAO.class);
            
            Map<String, Object> params = new HashMap<String, Object>();
            
            params.put("budgetCc", budgetCc);
            params.put("budgetCcType", budgetCcType);
            params.put("fundId", fundId);
            params.put("prId", prId);
            params.put("exId", exId);
            
    		map = dao.getTotalPreBudget(params);
    		
    		if (map==null) {
    			map = new HashMap<String, Object>();
    			map.put("pre", 0.0);
    		}
    		DecimalFormat df = new DecimalFormat("#,##0.00");
    		
    		Double balance = interfaceService.getBudget(budgetCcType, budgetCc, fundId);
    		
    		map.put("ebalance", df.format(balance-(Double)map.get("pre")));
    		map.put("balance", df.format(balance));
    		map.put("pre", df.format(map.get("pre")));
    		
        } catch (Exception ex) {
        	log.error(ex);
        } finally {
        	session.close();
        }
        
        return map;
	}
	
}
