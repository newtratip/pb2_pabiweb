package pb.repo.admin.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.repo.admin.dao.MainEmployeeDAO;
import pb.repo.admin.model.MainEmployeeModel;
import pb.repo.common.mybatis.DbConnectionFactory;

@Service
public class AdminEmployeeService {
	
	private static Logger log = Logger.getLogger(AdminEmployeeService.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	AuthenticationService authService;

	public void save(MainEmployeeModel model) {
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainEmployeeDAO employeeDAO = session.getMapper(MainEmployeeDAO.class);
            
            employeeDAO.add(model);
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }

	}
	
	public void delete(String id) throws Exception {
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainEmployeeDAO employeeDAO = session.getMapper(MainEmployeeDAO.class);

            employeeDAO.delete(id);
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }
	}

}
