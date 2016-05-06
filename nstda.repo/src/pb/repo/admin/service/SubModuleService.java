package pb.repo.admin.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import pb.repo.admin.model.MainWorkflowNextActorModel;
import pb.repo.admin.model.SubModuleModel;


public interface SubModuleService {
	
	public List<Map<String, Object>> listWorkflowPath(String id);
	
	public void update(SubModuleModel model) throws Exception;
	public Object get(String id);
	public String getWorkflowName() throws Exception;
	public String getWorkflowDescription(SubModuleModel paramModel) throws Exception;
	
	public Map<String, Object> convertToMap(SubModuleModel model);
	public String getSubModuleType();
	
	public void setWorkflowParameters(Map<QName, Serializable> parameters, SubModuleModel model, List<NodeRef> docList, List<NodeRef> attachDocList);
	
	public String getActionCaption(String action);
	
	public List<MainWorkflowNextActorModel> listNextActor(SubModuleModel model);
	
	public String getFirstComment(SubModuleModel model);
	
	public String getNextActionInfo();
	
    public String getModelUri();
    public String getWfUri();
    public String getModelPrefix();
    
	public QName getPropNextReviewers(); 
}
