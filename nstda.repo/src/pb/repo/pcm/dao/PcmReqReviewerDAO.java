package pb.repo.pcm.dao;

import java.util.List;

import pb.repo.pcm.model.PcmReqReviewerModel;

public interface PcmReqReviewerDAO {
	
	public void deleteByMasterId(String masterId);
	public void add(PcmReqReviewerModel pcmReqReviewerModel);
	
	public List<PcmReqReviewerModel> listByLevel(PcmReqReviewerModel pcmReqReviewerModel);
	
}