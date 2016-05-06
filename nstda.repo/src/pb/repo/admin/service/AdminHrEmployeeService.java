package pb.repo.admin.service;

import java.util.Map;

import javax.sql.DataSource;

import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.repo.admin.dao.MainHrEmployeeDAO;
import pb.repo.admin.model.MainHrEmployeeModel;
import pb.repo.common.mybatis.DbConnectionFactory;

@Service
public class AdminHrEmployeeService {
	
	private static Logger log = Logger.getLogger(AdminHrEmployeeService.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	AuthenticationService authService;

	public MainHrEmployeeModel get(String id) {
			
		MainHrEmployeeModel model = null;
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
        	MainHrEmployeeDAO dao = session.getMapper(MainHrEmployeeDAO.class);
    		model = dao.get(id);
    		if (model!=null) {
    			model.setTotalRowCount(1l);
    		}
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
        
        return model;
	}
	
	public Map<String, Object> getWithDtl(String id) {
		
		Map<String, Object> map = null;
		
		SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
        	MainHrEmployeeDAO dao = session.getMapper(MainHrEmployeeDAO.class);
    		map = dao.getWithDtl(id);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
        
        return map;
	}	

}
