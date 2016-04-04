package pb.repo.pcm.util;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import pb.common.constant.CommonConstant;
import pb.common.constant.JsonConstant;
import pb.common.util.CommonDateTimeUtil;
import pb.common.util.CommonUtil;
import pb.repo.common.mybatis.DbConnectionFactory;
import pb.repo.pcm.constant.PcmReqConstant;
import pb.repo.pcm.dao.PcmFunctionDAO;
import pb.repo.pcm.dao.PcmOrdDAO;
import pb.repo.pcm.dao.PcmOrdDtlDAO;
import pb.repo.pcm.dao.PcmOrdReviewerDAO;
import pb.repo.pcm.dao.PcmOrdWorkflowDAO;
import pb.repo.pcm.dao.PcmOrdWorkflowHistoryDAO;
import pb.repo.pcm.dao.PcmReqCmtDAO;
import pb.repo.pcm.dao.PcmReqCmtDtlDAO;
import pb.repo.pcm.dao.PcmReqCmtHdrDAO;
import pb.repo.pcm.dao.PcmReqDAO;
import pb.repo.pcm.dao.PcmReqDtlDAO;
import pb.repo.pcm.dao.PcmReqReviewerDAO;
import pb.repo.pcm.dao.PcmReqWorkflowDAO;
import pb.repo.pcm.dao.PcmReqWorkflowHistoryDAO;
import pb.repo.pcm.model.PcmOrdDtlModel;
import pb.repo.pcm.model.PcmOrdModel;
import pb.repo.pcm.model.PcmOrdReviewerModel;
import pb.repo.pcm.model.PcmOrdWorkflowHistoryModel;
import pb.repo.pcm.model.PcmOrdWorkflowModel;
import pb.repo.pcm.model.PcmReqCmtDtlModel;
import pb.repo.pcm.model.PcmReqCmtHdrModel;
import pb.repo.pcm.model.PcmReqCmtModel;
import pb.repo.pcm.model.PcmReqDtlModel;
import pb.repo.pcm.model.PcmReqModel;
import pb.repo.pcm.model.PcmReqReviewerModel;
import pb.repo.pcm.model.PcmWorkflowHistoryModel;
import pb.repo.pcm.model.PcmWorkflowModel;

public class PcmUtil {
	
	private static Logger log = Logger.getLogger(PcmUtil.class);
	
	public static SqlSession openSession(DataSource dataSource) {
		
		SqlSessionFactory sqlSessionFactory = DbConnectionFactory.getSqlSessionFactory(dataSource);

		Configuration config = sqlSessionFactory.getConfiguration();
		if (!config.hasMapper(PcmReqDAO.class)) {
	        config.getTypeAliasRegistry().registerAlias("pcmReqModel", PcmReqModel.class);
	        config.getTypeAliasRegistry().registerAlias("pcmReqDtlModel", PcmReqDtlModel.class);
	        config.getTypeAliasRegistry().registerAlias("pcmReqReviewerModel", PcmReqReviewerModel.class);
	        config.getTypeAliasRegistry().registerAlias("pcmReqCmtModel", PcmReqCmtModel.class);
	        config.getTypeAliasRegistry().registerAlias("pcmReqCmtHdrModel", PcmReqCmtHdrModel.class);
	        config.getTypeAliasRegistry().registerAlias("pcmReqCmtDtlModel", PcmReqCmtDtlModel.class);
	        
	        config.addMapper(PcmReqDAO.class);
	        config.addMapper(PcmReqDtlDAO.class);
	        config.addMapper(PcmReqReviewerDAO.class);
	        config.addMapper(PcmReqCmtDAO.class);
	        config.addMapper(PcmReqCmtHdrDAO.class);
	        config.addMapper(PcmReqCmtDtlDAO.class);
		}
		
		if (!config.hasMapper(PcmReqWorkflowDAO.class)) {
	        config.getTypeAliasRegistry().registerAlias("pcmReqWorkflowModel", PcmWorkflowModel.class);
	        
	        config.addMapper(PcmReqWorkflowDAO.class);
		}
		
		if (!config.hasMapper(PcmReqWorkflowHistoryDAO.class)) {
	        config.getTypeAliasRegistry().registerAlias("pcmReqWorkflowHistoryModel", PcmWorkflowHistoryModel.class);
	        
	        config.addMapper(PcmReqWorkflowHistoryDAO.class);
		}
		
		if (!config.hasMapper(PcmFunctionDAO.class)) {
	        //config.getTypeAliasRegistry().registerAlias("memoWorkflowModel", MemoWorkflowModel.class);
	        
	        config.addMapper(PcmFunctionDAO.class);
		}
		
		if (!config.hasMapper(PcmOrdDAO.class)) {
	        config.getTypeAliasRegistry().registerAlias("pcmOrdModel", PcmOrdModel.class);
	        config.getTypeAliasRegistry().registerAlias("pcmOrdDtlModel", PcmOrdDtlModel.class);
	        config.getTypeAliasRegistry().registerAlias("pcmOrdReviewerModel", PcmOrdReviewerModel.class);
	        
	        config.addMapper(PcmOrdDAO.class);
	        config.addMapper(PcmOrdDtlDAO.class);
	        config.addMapper(PcmOrdReviewerDAO.class);
		}		
		
		if (!config.hasMapper(PcmOrdWorkflowDAO.class)) {
	        config.getTypeAliasRegistry().registerAlias("pcmOrdWorkflowModel", PcmOrdWorkflowModel.class);
	        
	        config.addMapper(PcmOrdWorkflowDAO.class);
		}
		
		if (!config.hasMapper(PcmOrdWorkflowHistoryDAO.class)) {
	        config.getTypeAliasRegistry().registerAlias("pcmOrdWorkflowHistoryModel", PcmOrdWorkflowHistoryModel.class);
	        
	        config.addMapper(PcmOrdWorkflowHistoryDAO.class);
		}
		
        return sqlSessionFactory.openSession();
	}
	
}
