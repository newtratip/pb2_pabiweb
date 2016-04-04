package pb.repo.admin.service;

import java.util.List;

import javax.sql.DataSource;

import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.repo.admin.dao.MainMsgDAO;
import pb.repo.admin.dao.MainProductUomDAO;
import pb.repo.admin.model.MainProductUomModel;
import pb.repo.common.mybatis.DbConnectionFactory;

@Service
public class AdminProductUomService {
	
	private static Logger log = Logger.getLogger(AdminProductUomService.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	AuthenticationService authService;
	
	public List<MainProductUomModel> list() {
		
		List<MainProductUomModel> list = null;
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
        	MainProductUomDAO dao = session.getMapper(MainProductUomDAO.class);
            
            list = dao.list();
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }
		
		return list;
	}

}
