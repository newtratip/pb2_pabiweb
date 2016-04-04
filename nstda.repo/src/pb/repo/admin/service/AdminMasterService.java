package pb.repo.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.alfresco.service.cmr.security.AuthenticationService;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pb.repo.admin.constant.MainMasterConstant;
import pb.repo.admin.dao.MainMasterDAO;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.common.mybatis.DbConnectionFactory;

@Service
public class AdminMasterService {
	
	private static Logger log = Logger.getLogger(AdminMasterService.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	AuthenticationService authService;

	public void save(MainMasterModel model) {
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainMasterDAO masterDAO = session.getMapper(MainMasterDAO.class);
            
    		model.setUpdatedBy(authService.getCurrentUserName());
    		
            if (model.getId() == null) {
        		model.setCreatedBy(model.getUpdatedBy());
            	
            	masterDAO.add(model);
            }
            else {
            	masterDAO.update(model);
            }
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }

	}
	
	public List<MainMasterModel> list(String type, String code) {
		
		List<MainMasterModel> list = null;
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainMasterDAO mainMasterDAO = session.getMapper(MainMasterDAO.class);
            
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("type", type);
    		map.put("code", code);

    		list = mainMasterDAO.list(map);
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	public List<Map<String, Object>> listByType(String type, String searchTerm, Boolean active, Integer start, Integer limit) {
		
		List<Map<String, Object>> list = null;
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainMasterDAO mainMasterDAO = session.getMapper(MainMasterDAO.class);
            
    		if (searchTerm != null && !searchTerm.equals("")) {
    			searchTerm = "%" + searchTerm + "%";
    		}
    		else {
    			searchTerm = null;
    		}
            
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("type",type);
    		map.put("searchTerm", searchTerm);
    		if (active!=null) {
    			map.put("active", active);
    		}
    		else {
    			map.put("active", null);
    		}
    		map.put("start", start);
    		map.put("limit", limit);
    		
    		list = mainMasterDAO.listByType(map);
            
            session.commit();
        } catch (Exception ex) {
			log.error("", ex);
        	session.rollback();
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	public List<MainMasterModel> listAuthType(String type, String flag) {
		
		List<MainMasterModel> list = null;
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainMasterDAO mainMasterDAO = session.getMapper(MainMasterDAO.class);
            
    		Map<String, String> map = new HashMap<String, String>();
    		map.put("type",type);
    		map.put("flag2", flag);

    		list = mainMasterDAO.listAuthType(map);
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	public List<MainMasterModel> listByAuthType(String type) {
		
		List<MainMasterModel> list = null;
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainMasterDAO mainMasterDAO = session.getMapper(MainMasterDAO.class);
            
    		Map<String, String> map = new HashMap<String, String>();
    		map.put("type",type);
    		

    		list = mainMasterDAO.listByAuthType(map);
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	public List<MainMasterModel> listMasterWithOutMatrix(String type, String f1) {
		
		List<MainMasterModel> list = null;
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainMasterDAO mainMasterDAO = session.getMapper(MainMasterDAO.class);
            
    		Map<String, String> map = new HashMap<String, String>();
    		map.put("type",type);
    		
    		if(f1!=null && !f1.equalsIgnoreCase("")) {
    			map.put("field1",f1);
    		}

    		list = mainMasterDAO.listMasterWithOutMatrix(map);
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	public void delete(Long id) throws Exception {
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainMasterDAO mainMasterDAO = session.getMapper(MainMasterDAO.class);

    		mainMasterDAO.delete(id);
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }
	}
	
	public MainMasterModel get(Long id) {
		
		MainMasterModel model = null;
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainMasterDAO mainMasterDAO = session.getMapper(MainMasterDAO.class);
            
    		model = mainMasterDAO.get(id);
    		model.setTotalRowCount(1l);
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }
        
        return model;
	}
	
	public MainMasterModel getSystemConfig(String code) {
		
		MainMasterModel model = null;
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainMasterDAO mainMasterDAO = session.getMapper(MainMasterDAO.class);
            
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(MainMasterConstant.JFN_TYPE, MainMasterConstant.TYPE_SYSTEM_CONFIG);
            params.put(MainMasterConstant.JFN_CODE, code);
            
    		model = mainMasterDAO.getByTypeAndCode(params);
    		model.setTotalRowCount(1l);
            
    		log.info("get "+code+": OK");
            session.commit();
        } catch (Exception ex) {
    		log.error("get "+code+": ERR:"+ex.getMessage());
        	session.rollback();
        } finally {
        	session.close();
        }
        
        return model;
	}
	
	public List<MainMasterModel> listSystemConfig(String code) {
		
		List<MainMasterModel> list = null;
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainMasterDAO mainMasterDAO = session.getMapper(MainMasterDAO.class);
            
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(MainMasterConstant.JFN_TYPE, MainMasterConstant.TYPE_SYSTEM_CONFIG);
            params.put(MainMasterConstant.JFN_CODE, code+"_%");
            
    		list = mainMasterDAO.listByTypeAndCode(params);
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }
        
        return list;
	}
	
	public List<MainMasterModel> listColumnSort(String type, String flag1) {
		
		List<MainMasterModel> list = null;
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainMasterDAO mainMasterDAO = session.getMapper(MainMasterDAO.class);
            
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("type",type);
    		map.put("flag1",flag1);

    		list = mainMasterDAO.listColumnSort(map);
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }
        
        return list;
	}

}
