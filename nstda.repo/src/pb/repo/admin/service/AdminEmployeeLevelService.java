package pb.repo.admin.service;

import javax.sql.DataSource;

import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.repo.admin.dao.MainEmployeeLevelDAO;
import pb.repo.admin.model.MainEmployeeLevelModel;
import pb.repo.common.mybatis.DbConnectionFactory;

@Service
public class AdminEmployeeLevelService {
	
	private static Logger log = Logger.getLogger(AdminEmployeeLevelService.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	AuthenticationService authService;

	public void save(MainEmployeeLevelModel model) {
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainEmployeeLevelDAO employeeLevelDAO = session.getMapper(MainEmployeeLevelDAO.class);
            
            employeeLevelDAO.add(model);
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }

	}
	
	public void delete(String module, String org, String level) throws Exception {
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainEmployeeLevelDAO employeeLevelDAO = session.getMapper(MainEmployeeLevelDAO.class);
            
            MainEmployeeLevelModel model = new MainEmployeeLevelModel();
            model.setModule(module);
            model.setOrg(org);
            model.setLevel(level);

            employeeLevelDAO.delete(model);
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }
	}

}
