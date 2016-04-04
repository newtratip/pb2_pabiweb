package pb.repo.pcm.workflow;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("pb.pcm.workflow.StartWorkflow")
public class StartWorkflow implements ExecutionListener {
	
	private static Logger log = Logger.getLogger(StartWorkflow.class);
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		
//		log.info("Start workflow : "+execution.getId()+", "+execution.getProcessDefinitionId());
		
//		List<String> assignee = Arrays.asList("req01", "app01", "app02", "app03");
//		execution.setVariable("memwf_reviewer1List", assignee);

		
	}

}







