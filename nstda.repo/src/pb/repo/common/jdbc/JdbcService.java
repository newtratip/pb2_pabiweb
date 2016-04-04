package pb.repo.common.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import pb.repo.admin.constant.MainMasterConstant;

public class JdbcService {

	private static Logger log = Logger.getLogger(JdbcService.class);

	public List<Map<String, Object>> listAvailableWorkflow() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    	Connection conn = null;

    	PreparedStatement ps = null;
    	
        try {
        	conn = JdbcConnectionFactory.getConnection();

        	String sql = "SELECT * FROM "+MainMasterConstant.TABLE_NAME+" WHERE type=?";
 	        ps = conn.prepareStatement(sql);
        	
        	ps.setString(1, MainMasterConstant.TYPE_WORKFLOW);
        	
 	        ResultSet rs = ps.executeQuery();
 	        
 	       Map<String, Object> map = null;
 	        
 	        while (rs.next()) {
 	        	map = new HashMap<String, Object>();
 	        	
 	        	map.put(MainMasterConstant.TFN_CODE, rs.getString(MainMasterConstant.TFN_CODE));
 	        	map.put(MainMasterConstant.TFN_NAME, rs.getString(MainMasterConstant.TFN_NAME));
 	        	map.put(MainMasterConstant.TFN_FLAG1, rs.getString(MainMasterConstant.TFN_FLAG1));
 	        	
 	        	list.add(map);
            }
	        
 	        rs.close();
            
        } catch (Exception ex) {
            log.error("",ex);
        } finally {
            try {
            	if (ps!=null) ps.close();
                if (conn!=null) conn.close();
            } catch (Exception ex) {
                log.error("",ex);
            }
        }
		
		return list;
	}
	
	public List<String> listAvailableWorkflowNames() {
		
		List<String> wfNames = new ArrayList<String>();
		
		List<Map<String, Object>> awfList = listAvailableWorkflow();
		
		for(Map<String, Object> map : awfList) {
			wfNames.add("activiti$"+map.get(MainMasterConstant.TFN_FLAG1));
		}
		
		return wfNames;

	}
}
