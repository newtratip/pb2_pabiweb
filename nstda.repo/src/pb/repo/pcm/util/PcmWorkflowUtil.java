package pb.repo.pcm.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pb.repo.pcm.model.PcmWorkflowHistoryModel;

public class PcmWorkflowUtil {

	 public static String genWorkflowCommentHistory(String user, String action, String stateTask, String taskComment, PcmWorkflowHistoryModel memoWorkflowHistoryModel){
			
			SimpleDateFormat formatter = new SimpleDateFormat("EEE d MMM yyyy HH:mm", Locale.ENGLISH);
			Date today = new Date();
			String timeAction = formatter.format(today);
			
			// Build JSON string
			StringBuffer commentSb = new StringBuffer();
			
			commentSb.append("{");
			commentSb.append("\"time\":\""+timeAction+"\",");
			commentSb.append("\"user\":\""+user+"\",");
			commentSb.append("\"action\":\""+action+"\",");
			commentSb.append("\"task\":\""+stateTask+"\",");
			commentSb.append("\"comment\":\""+(taskComment != null ? org.json.simple.JSONObject.escape(taskComment) : "")+"\"");
			commentSb.append("}");
			
			memoWorkflowHistoryModel.setTime(new Timestamp(today.getTime()));
			memoWorkflowHistoryModel.setUser_(user);
			memoWorkflowHistoryModel.setAction(action);
			memoWorkflowHistoryModel.setTask(stateTask);
			memoWorkflowHistoryModel.setComment(taskComment);
			//globalValue.put("time", timeAction);
			//globalValue.put("user", user);
			//globalValue.put("action", action);
			//globalValue.put("task", stateTask);
			//globalValue.put("comment", taskComment);
			
			return commentSb.toString();
	}
}	