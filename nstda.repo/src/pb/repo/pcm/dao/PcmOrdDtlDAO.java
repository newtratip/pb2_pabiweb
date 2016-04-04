package pb.repo.pcm.dao;

import java.util.List;
import java.util.Map;

import pb.repo.pcm.model.PcmOrdDtlModel;

public interface PcmOrdDtlDAO {

	public void add(PcmOrdDtlModel model);

	public void delete(Long id);
	public void deleteByMasterId(String masterId);
	
	public Long count();
	
	public PcmOrdDtlModel get(Long id);

	public List<PcmOrdDtlModel> list(Map<String, Object> params);
	
}