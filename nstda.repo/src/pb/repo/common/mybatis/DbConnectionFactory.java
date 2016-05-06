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

import pb.repo.admin.dao.MainAccountTaxDAO;
import pb.repo.admin.dao.MainCompleteNotificationDAO;
import pb.repo.admin.dao.MainCostCenterDAO;
import pb.repo.admin.dao.MainCostCenterOrgRelDAO;
import pb.repo.admin.dao.MainCurrencyDAO;
import pb.repo.admin.dao.MainCurrencyRateDAO;
import pb.repo.admin.dao.MainDivisionDAO;
import pb.repo.admin.dao.MainEmployeeBossDAO;
import pb.repo.admin.dao.MainEmployeeDAO;
import pb.repo.admin.dao.MainEmployeeLevelDAO;
import pb.repo.admin.dao.MainCostControlDAO;
import pb.repo.admin.dao.MainCostControlTypeDAO;
import pb.repo.admin.dao.MainHrEmployeeDAO;
import pb.repo.admin.dao.MainHrPositionDAO;
import pb.repo.admin.dao.MainMasterDAO;
import pb.repo.admin.dao.MainMsgDAO;
import pb.repo.admin.dao.MainOrgDAO;
import pb.repo.admin.dao.MainPartnerTitleDAO;
import pb.repo.admin.dao.MainProductUomDAO;
import pb.repo.admin.dao.MainProjectDAO;
import pb.repo.admin.dao.MainProjectMemberDAO;
import pb.repo.admin.dao.MainPurchasingUnitSectionRelDAO;
import pb.repo.admin.dao.MainSectionDAO;
import pb.repo.admin.dao.MainWkfCmdApprovalAmountDAO;
import pb.repo.admin.dao.MainWkfCmdBossLevelApprovalDAO;
import pb.repo.admin.dao.MainWkfCmdBossSpecialLevelDAO;
import pb.repo.admin.dao.MainWkfCmdLevelDAO;
import pb.repo.admin.dao.MainWkfCmdSectionAssignDAO;
import pb.repo.admin.dao.MainWkfCmdSpecialAmountProjectApprovalDAO;
import pb.repo.admin.dao.MainWkfConfigDocTypeDAO;
import pb.repo.admin.dao.MainWkfConfigPurchaseUnitDAO;
import pb.repo.admin.dao.MainWkfConfigPurchaseUnitResponsibleDAO;
import pb.repo.admin.dao.MainWorkflowDAO;
import pb.repo.admin.dao.MainWorkflowHistoryDAO;
import pb.repo.admin.dao.MainWorkflowNextActorDAO;
import pb.repo.admin.dao.MainWorkflowReviewerDAO;
import pb.repo.admin.model.MainAccountTaxModel;
import pb.repo.admin.model.MainCompleteNotificationModel;
import pb.repo.admin.model.MainCostCenterModel;
import pb.repo.admin.model.MainCostCenterOrgRelModel;
import pb.repo.admin.model.MainCurrencyModel;
import pb.repo.admin.model.MainCurrencyRateModel;
import pb.repo.admin.model.MainDivisionModel;
import pb.repo.admin.model.MainEmployeeBossModel;
import pb.repo.admin.model.MainEmployeeLevelModel;
import pb.repo.admin.model.MainEmployeeModel;
import pb.repo.admin.model.MainCostControlModel;
import pb.repo.admin.model.MainCostControlTypeModel;
import pb.repo.admin.model.MainHrEmployeeModel;
import pb.repo.admin.model.MainHrPositionModel;
import pb.repo.admin.model.MainMasterModel;
import pb.repo.admin.model.MainMsgModel;
import pb.repo.admin.model.MainOrgModel;
import pb.repo.admin.model.MainPartnerTitleModel;
import pb.repo.admin.model.MainProductUomModel;
import pb.repo.admin.model.MainProjectMemberModel;
import pb.repo.admin.model.MainProjectModel;
import pb.repo.admin.model.MainPurchasingUnitSectionRelModel;
import pb.repo.admin.model.MainSectionModel;
import pb.repo.admin.model.MainWkfCmdApprovalAmountModel;
import pb.repo.admin.model.MainWkfCmdBossLevelApprovalModel;
import pb.repo.admin.model.MainWkfCmdBossSpecialLevelModel;
import pb.repo.admin.model.MainWkfCmdLevelModel;
import pb.repo.admin.model.MainWkfCmdSectionAssignModel;
import pb.repo.admin.model.MainWkfCmdSpecialAmountProjectApprovalModel;
import pb.repo.admin.model.MainWkfConfigDocTypeModel;
import pb.repo.admin.model.MainWkfConfigPurchaseUnitModel;
import pb.repo.admin.model.MainWkfConfigPurchaseUnitResponsibleModel;
import pb.repo.admin.model.MainWorkflowHistoryModel;
import pb.repo.admin.model.MainWorkflowModel;
import pb.repo.admin.model.MainWorkflowNextActorModel;
import pb.repo.admin.model.MainWorkflowReviewerModel;
 
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
        config.getTypeAliasRegistry().registerAlias("mainAccountTaxModel", MainAccountTaxModel.class);
        config.getTypeAliasRegistry().registerAlias("mainEmployeeModel", MainEmployeeModel.class);
        config.getTypeAliasRegistry().registerAlias("mainEmployeeBossModel", MainEmployeeBossModel.class);
        config.getTypeAliasRegistry().registerAlias("mainEmployeeLevelModel", MainEmployeeLevelModel.class);
        config.getTypeAliasRegistry().registerAlias("mainCostCenterOrgRelModel", MainCostCenterOrgRelModel.class);
        config.getTypeAliasRegistry().registerAlias("mainCurrencyModel", MainCurrencyModel.class);
        config.getTypeAliasRegistry().registerAlias("mainCurrencyRateModel", MainCurrencyRateModel.class);
        config.getTypeAliasRegistry().registerAlias("mainProductUomModel", MainProductUomModel.class);
        config.getTypeAliasRegistry().registerAlias("mainWorkflowModel", MainWorkflowModel.class);
        config.getTypeAliasRegistry().registerAlias("mainWorkflowHistoryModel", MainWorkflowHistoryModel.class);
        config.getTypeAliasRegistry().registerAlias("mainWorkflowNextActorModel", MainWorkflowNextActorModel.class);
        config.getTypeAliasRegistry().registerAlias("mainWorkflowReviewerModel", MainWorkflowReviewerModel.class);
        config.getTypeAliasRegistry().registerAlias("mainOrgModel", MainOrgModel.class);
        config.getTypeAliasRegistry().registerAlias("mainCostCenterModel", MainCostCenterModel.class);
        config.getTypeAliasRegistry().registerAlias("mainDivisionModel", MainDivisionModel.class);
        config.getTypeAliasRegistry().registerAlias("mainPartnerTitleModel", MainPartnerTitleModel.class);
        config.getTypeAliasRegistry().registerAlias("mainProjectModel", MainProjectModel.class);
        config.getTypeAliasRegistry().registerAlias("mainProjectMemberModel", MainProjectMemberModel.class);
        config.getTypeAliasRegistry().registerAlias("mainSectionModel", MainSectionModel.class);
        config.getTypeAliasRegistry().registerAlias("mainWkfConfigDocTypeModel", MainWkfConfigDocTypeModel.class);
        config.getTypeAliasRegistry().registerAlias("mainWkfConfigPurchaseUnitModel", MainWkfConfigPurchaseUnitModel.class);
        config.getTypeAliasRegistry().registerAlias("mainWkfConfigPurchaseUnitResponsibleModel", MainWkfConfigPurchaseUnitResponsibleModel.class);
        config.getTypeAliasRegistry().registerAlias("mainWkfCmdApprovalAmountModel", MainWkfCmdApprovalAmountModel.class);
        config.getTypeAliasRegistry().registerAlias("mainWkfCmdBossLevelApprovalModel", MainWkfCmdBossLevelApprovalModel.class);
        config.getTypeAliasRegistry().registerAlias("mainWkfCmdBossSpecialLevelModel", MainWkfCmdBossSpecialLevelModel.class);
        config.getTypeAliasRegistry().registerAlias("mainWkfCmdLevelModel", MainWkfCmdLevelModel.class);
        config.getTypeAliasRegistry().registerAlias("mainWkfCmdSectionAssignModel", MainWkfCmdSectionAssignModel.class);
        config.getTypeAliasRegistry().registerAlias("mainWkfCmdSpecialAmountProjectApprovalModel", MainWkfCmdSpecialAmountProjectApprovalModel.class);
        config.getTypeAliasRegistry().registerAlias("mainHrEmployeeModel", MainHrEmployeeModel.class);
        config.getTypeAliasRegistry().registerAlias("mainHrPositionModel", MainHrPositionModel.class);
        config.getTypeAliasRegistry().registerAlias("mainPurchasingUnitSectionRelModel", MainPurchasingUnitSectionRelModel.class);
        config.getTypeAliasRegistry().registerAlias("mainProjectModel", MainProjectModel.class);
        config.getTypeAliasRegistry().registerAlias("mainSectionModel", MainSectionModel.class);
        config.getTypeAliasRegistry().registerAlias("mainCostControlModel", MainCostControlModel.class);
        config.getTypeAliasRegistry().registerAlias("mainCostControlTypeModel", MainCostControlTypeModel.class);
        
        config.addMapper(MainMasterDAO.class);
        config.addMapper(MainMsgDAO.class);
        config.addMapper(MainCompleteNotificationDAO.class);
        config.addMapper(MainAccountTaxDAO.class);
        config.addMapper(MainEmployeeDAO.class);
        config.addMapper(MainEmployeeBossDAO.class);
        config.addMapper(MainEmployeeLevelDAO.class);
        config.addMapper(MainCostCenterOrgRelDAO.class);
        config.addMapper(MainCurrencyDAO.class);
        config.addMapper(MainCurrencyRateDAO.class);
        config.addMapper(MainProductUomDAO.class);
        config.addMapper(MainWorkflowDAO.class);
        config.addMapper(MainWorkflowHistoryDAO.class);
        config.addMapper(MainWorkflowNextActorDAO.class);
        config.addMapper(MainWorkflowReviewerDAO.class);
        config.addMapper(MainOrgDAO.class);
        config.addMapper(MainCostCenterDAO.class);
        config.addMapper(MainDivisionDAO.class);
        config.addMapper(MainPartnerTitleDAO.class);
        config.addMapper(MainProjectDAO.class);
        config.addMapper(MainProjectMemberDAO.class);
        config.addMapper(MainSectionDAO.class);
        config.addMapper(MainWkfConfigDocTypeDAO.class);
        config.addMapper(MainWkfConfigPurchaseUnitDAO.class);
        config.addMapper(MainWkfConfigPurchaseUnitResponsibleDAO.class);
        config.addMapper(MainWkfCmdApprovalAmountDAO.class);
        config.addMapper(MainWkfCmdBossLevelApprovalDAO.class);
        config.addMapper(MainWkfCmdBossSpecialLevelDAO.class);
        config.addMapper(MainWkfCmdLevelDAO.class);
        config.addMapper(MainWkfCmdSectionAssignDAO.class);
        config.addMapper(MainWkfCmdSpecialAmountProjectApprovalDAO.class);
        config.addMapper(MainHrEmployeeDAO.class);
        config.addMapper(MainHrPositionDAO.class);
        config.addMapper(MainPurchasingUnitSectionRelDAO.class);
		if (!config.hasMapper(MainProjectDAO.class)) {
			config.addMapper(MainProjectDAO.class);
		}
		if (!config.hasMapper(MainSectionDAO.class)) {
			config.addMapper(MainSectionDAO.class);
		}
		if (!config.hasMapper(MainCostControlDAO.class)) {
			config.addMapper(MainCostControlDAO.class);
		}
		if (!config.hasMapper(MainCostControlTypeDAO.class)) {
			config.addMapper(MainCostControlTypeDAO.class);
		}
        
    }
    
}