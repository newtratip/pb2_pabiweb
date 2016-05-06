package pb.repo.admin.service;

import java.math.BigDecimal;
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

import pb.repo.admin.constant.MainEmployeeBossConstant;
import pb.repo.admin.constant.MainEmployeeLevelConstant;
import pb.repo.admin.dao.MainEmployeeBossDAO;
import pb.repo.admin.model.MainEmployeeBossModel;
import pb.repo.admin.util.MainUtil;
import pb.repo.common.mybatis.DbConnectionFactory;

@Service
public class AdminEmployeeBossService {
	
	private static Logger log = Logger.getLogger(AdminEmployeeBossService.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	AuthenticationService authService;

	public void save(MainEmployeeBossModel model) {
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainEmployeeBossDAO employeeBossDAO = session.getMapper(MainEmployeeBossDAO.class);
            
            employeeBossDAO.add(model);
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }

	}
	
	public void delete(String costCenter, String level) throws Exception {
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainEmployeeBossDAO employeeBossDAO = session.getMapper(MainEmployeeBossDAO.class);

            MainEmployeeBossModel model = new MainEmployeeBossModel();
            model.setCostCenter(costCenter);
            model.setLevel(level);
            
            employeeBossDAO.delete(model);
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }
	}
	
	public Map<String, String> getBossMap(String bossModule, String type, String costCenter, String reqUser, Double amount) {
		/*
		 * Search Original Boss List
		 */
		Map<String, String> tmpMap = new LinkedHashMap<String, String>();
		
		log.info("getBossMap bossModule:'"+bossModule+"'");
		log.info("getBossMap type:"+type);
		log.info("getBossMap costCenter:'"+costCenter+"'");
		log.info("getBossMap reqUser:"+reqUser);
		log.info("getBossMap amount:"+amount);
		
        SqlSession session = DbConnectionFactory.getSqlSessionFactory(dataSource).openSession();
        try {
            MainEmployeeBossDAO bossDAO = session.getMapper(MainEmployeeBossDAO.class);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("bossModule", bossModule);
            params.put("costCenter", costCenter);
            
            List<Map<String, Object>> list = bossDAO.listBoss(params);
            log.info("getBossMap size:"+list.size());
            
            for(Map<String,Object> m:list) {
            	
            	for(Entry e : m.entrySet()) {
            		log.info("   e:"+e.getKey()+":"+e.getValue());
            	}
            	
            	Object o = m.get(MainEmployeeLevelConstant.TFN_TO_AMOUNT);
            	log.info("o:"+o.getClass().getName());
            	
            	if (BigDecimal.valueOf(amount).compareTo((BigDecimal)m.get(MainEmployeeLevelConstant.TFN_TO_AMOUNT)) <= 0) {
	                if (type.equals("P")) {
	                	log.info("type-> P");
	                	/* boss include reqUser */
	                	tmpMap.put((String)m.get(MainEmployeeBossConstant.TFN_LEVEL), (String)m.get(MainEmployeeBossConstant.TFN_BOSS_IDS));
	                } else {
	                	log.info("type-> !P");
	                	/* boss exclude reqUser */
	                	String excReqUserIds = MainUtil.excludeString((String)m.get(MainEmployeeBossConstant.TFN_BOSS_IDS),reqUser); 
	                	if (!excReqUserIds.equals("")) {
	                    	tmpMap.put((String)m.get(MainEmployeeBossConstant.TFN_LEVEL), (String)m.get(MainEmployeeBossConstant.TFN_BOSS_IDS));
	                	}
	                }
            	}
            }
            
            if (tmpMap.size()==0) {
            	Map m = list.get(list.size()-1);
            	tmpMap.put((String)m.get(MainEmployeeBossConstant.TFN_LEVEL), (String)m.get(MainEmployeeBossConstant.TFN_BOSS_IDS));
            }
            
            session.commit();
        } catch (Exception ex) {
        	session.rollback();
        } finally {
        	session.close();
        }
        log.info("getBossMap tmpMap.size:"+tmpMap.size());
        
        /*
         * Remove duplicated Boss
         */
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		Entry prevEntry = null;
		for(Entry e : tmpMap.entrySet()) {
			
			if (prevEntry==null || !e.getValue().equals(prevEntry.getValue())) {
				map.put((String)e.getKey(), (String)e.getValue());
			}
			
			prevEntry = e;
		}
		
		if (prevEntry != null) {
			map.put((String)prevEntry.getKey(), (String)prevEntry.getValue());
		}
		
        log.info("getBossMap map.size:"+map.size());
		
		return map;
	}

}
