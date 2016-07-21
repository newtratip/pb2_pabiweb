package pb.repo.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.repo.admin.dao.MainUserDAO;
import pb.repo.common.mybatis.DbConnectionFactory;

@Service
public class AdminUserService {
	
	private static Logger log = Logger.getLogger(AdminUserService.class);

	@Autowired
	DataSource dataSource;
	
	public List<Map<String, Object>> list(String searchTerm, String lang) {
		
		List<Map<String, Object>> list = null;
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
        	MainUserDAO dao = session.getMapper(MainUserDAO.class);
            
        	Map<String, Object> params = new HashMap<String, Object>();
        	
        	if (searchTerm!=null) {
        		String[] terms = searchTerm.split(" ");
        	
        		params.put("terms", terms);
        	}
        	
        	params.put("lang", lang!=null && lang.startsWith("th") ? "_th" : "");
        	
            list = dao.list(params);
            
        } catch (Exception ex) {
        	log.error(ex);
        } finally {
        	session.close();
        }
		
		return list;
	}
}
