package pb.repo.pcm.dao;

import java.util.List;

import pb.repo.pcm.model.PcmOrdReviewerModel;

public interface PcmOrdReviewerDAO {
	
	public void deleteByMasterId(String masterId);
	public void add(PcmOrdReviewerModel pcmOrdReviewerModel);
	
	public List<PcmOrdReviewerModel> listByLevel(PcmOrdReviewerModel pcmOrdReviewerModel);
	
}