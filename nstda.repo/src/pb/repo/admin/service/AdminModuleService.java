package pb.repo.admin.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

	public Map<String, Object> getTotalPreBudget(Integer budgetCc, String budgetCcType) {
		
		Map<String, Object> map = null;
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainModuleDAO dao = session.getMapper(MainModuleDAO.class);
            
            Map<String, Object> params = new HashMap<String, Object>();
            
            params.put("budgetCc", budgetCc);
            params.put("budgetCcType", budgetCcType);
            
    		map = dao.getTotalPreBudget(params);
    		
    		if (map==null) {
    			map = new HashMap<String, Object>();
    			map.put("pre", 0.0);
    		}
    		DecimalFormat df = new DecimalFormat("#,##0.00");
    		
    		Double balance = 1000000.0;
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
