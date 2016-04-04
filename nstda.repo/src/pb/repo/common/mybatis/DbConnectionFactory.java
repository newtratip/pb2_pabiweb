package pb.repo.common.mybatis;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.log4j.Logger;

import pb.repo.admin.dao.MainCompleteNotificationDAO;
import pb.repo.admin.dao.MainCostCenterOrgRelDAO;
import pb.repo.admin.dao.MainCurrencyDAO;
import pb.repo.admin.dao.MainCurrencyRateDAO;
import pb.repo.admin.dao.MainEmployeeBossDAO;
import pb.repo.admin.dao.MainEmployeeDAO;
import pb.repo.admin.dao.MainEmployeeLevelDAO;
import pb.repo.admin.dao.MainMasterDAO;
import pb.repo.admin.dao.MainMsgDAO;
import pb.repo.admin.dao.MainProductUomDAO;
import pb.repo.admin.model.MainCompleteNotificationModel;
import pb.repo.admin.model.MainCostCenterOrgRelModel;
import pb.repo.admin.model.MainCurrencyModel;
import pb.repo.admin.model.MainCurrencyRateModel;
import pb.repo.admin.model.MainEmployeeBossModel;
import pb.repo.admin.model.MainEmployeeLevelModel;
import pb.repo.admin.model.MainEmployeeModel;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.model.MainMsgModel;
import pb.repo.admin.model.MainProductUomModel;
 
public class DbConnectionFactory {
	
	private static Logger log = Logger.getLogger(DbConnectionFactory.class);

    private static SqlSessionFactory sqlSessionFactory;
    
	private static DataSource dataSource;
 
    static {
 
        try {
        	
            String configFile = "mybatis.xml";

            InputStream ins = DbConnectionFactory.class.getResourceAsStream(configFile);
            Reader reader = new InputStreamReader(ins);
            
            if (sqlSessionFactory == null) {
        		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        		
            }
        }
 
        catch (Exception ex) {
            log.error("", ex);
        }
    }
    
    public static SqlSessionFactory getSqlSessionFactory(DataSource ds) {
    	if (dataSource == null) {
    		dataSource = ds;
    		
    		Configuration config = sqlSessionFactory.getConfiguration(); 
    		
    		config.setEnvironment(new Environment("development", new JdbcTransactionFactory(), dataSource));

    		mapAdminModule(config);
    	}
 
        return sqlSessionFactory;
    }
    
    private static void mapAdminModule(Configuration config) {
        config.getTypeAliasRegistry().registerAlias("mainMasterModel", MainMasterModel.class);
        config.getTypeAliasRegistry().registerAlias("mainMsgModel", MainMsgModel.class);
        config.getTypeAliasRegistry().registerAlias("mainCompleteNotificationModel", MainCompleteNotificationModel.class);
        config.getTypeAliasRegistry().registerAlias("mainEmployeeModel", MainEmployeeModel.class);
        config.getTypeAliasRegistry().registerAlias("mainEmployeeBossModel", MainEmployeeBossModel.class);
        config.getTypeAliasRegistry().registerAlias("mainEmployeeLevelModel", MainEmployeeLevelModel.class);
        config.getTypeAliasRegistry().registerAlias("mainCostCenterOrgRelModel", MainCostCenterOrgRelModel.class);
        config.getTypeAliasRegistry().registerAlias("mainCurrencyModel", MainCurrencyModel.class);
        config.getTypeAliasRegistry().registerAlias("mainCurrencyRateModel", MainCurrencyRateModel.class);
        config.getTypeAliasRegistry().registerAlias("mainProductUomModel", MainProductUomModel.class);
        
        config.addMapper(MainMasterDAO.class);
        config.addMapper(MainMsgDAO.class);
        config.addMapper(MainCompleteNotificationDAO.class);
        config.addMapper(MainEmployeeDAO.class);
        config.addMapper(MainEmployeeBossDAO.class);
        config.addMapper(MainEmployeeLevelDAO.class);
        config.addMapper(MainCostCenterOrgRelDAO.class);
        config.addMapper(MainCurrencyDAO.class);
        config.addMapper(MainCurrencyRateDAO.class);
        config.addMapper(MainProductUomDAO.class);
    }
    
}