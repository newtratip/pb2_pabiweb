package pb.repo.admin.service;

import javax.sql.DataSource;

import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.repo.admin.dao.MainCostCenterOrgRelDAO;
import pb.repo.admin.model.MainCostCenterOrgRelModel;
import pb.repo.common.mybatis.DbConnectionFactory;

@Service
public class AdminCostCenterOrgRelService {
	
	private static Logger log = Logger.getLogger(AdminCostCenterOrgRelService.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	AuthenticationService authService;

	public void save(MainCostCenterOrgRelModel model) {
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainCostCenterOrgRelDAO costCenterOrgRelDAO = session.getMapper(MainCostCenterOrgRelDAO.class);
            
            costCenterOrgRelDAO.add(model);
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }

	}
	
	public void delete(String org, String costCenter) throws Exception {
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainCostCenterOrgRelDAO costCenterOrgRelDAO = session.getMapper(MainCostCenterOrgRelDAO.class);
            
            MainCostCenterOrgRelModel model = new MainCostCenterOrgRelModel();
            model.setOrg(org);
            model.setCostCenter(costCenter);

            costCenterOrgRelDAO.delete(model);
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }
	}

}
