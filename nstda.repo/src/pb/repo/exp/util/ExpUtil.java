package pb.repo.exp.util;

import javax.sql.DataSource;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import pb.repo.common.mybatis.DbConnectionFactory;
import pb.repo.exp.dao.ExpBrwDAO;
import pb.repo.exp.dao.ExpBrwVoyagerDAO;
import pb.repo.exp.dao.ExpUseDAO;
import pb.repo.exp.dao.ExpUseDtlDAO;
import pb.repo.exp.dao.ExpUseVoyagerDAO;
import pb.repo.exp.model.ExpBrwVoyagerModel;
import pb.repo.exp.model.ExpBrwModel;
import pb.repo.exp.model.ExpUseDtlModel;
import pb.repo.exp.model.ExpUseVoyagerModel;
import pb.repo.exp.model.ExpUseModel;

public class ExpUtil {
	
	private static Logger log = Logger.getLogger(ExpUtil.class);
	
	public static SqlSession openSession(DataSource dataSource) {
		
		SqlSessionFactory sqlSessionFactory = DbConnectionFactory.getSqlSessionFactory(dataSource);

		Configuration config = sqlSessionFactory.getConfiguration();
		if (!config.hasMapper(ExpBrwDAO.class)) {
	        config.getTypeAliasRegistry().registerAlias("expBrwModel", ExpBrwModel.class);
	        config.getTypeAliasRegistry().registerAlias("expBrwVoyagerModel", ExpBrwVoyagerModel.class);
	        
	        config.addMapper(ExpBrwDAO.class);
	        config.addMapper(ExpBrwVoyagerDAO.class);
		}
		
		if (!config.hasMapper(ExpUseDAO.class)) {
	        config.getTypeAliasRegistry().registerAlias("expUseModel", ExpUseModel.class);
	        config.getTypeAliasRegistry().registerAlias("expUseVoyagerModel", ExpUseVoyagerModel.class);
	        config.getTypeAliasRegistry().registerAlias("expUseDtlModel", ExpUseDtlModel.class);
	        
	        config.addMapper(ExpUseDAO.class);
	        config.addMapper(ExpUseVoyagerDAO.class);
	        config.addMapper(ExpUseDtlDAO.class);
		}		
		
        return sqlSessionFactory.openSession();
	}
	
}
