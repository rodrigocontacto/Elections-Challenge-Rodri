package net.avalith.elections.service;

import net.avalith.elections.dao.ICandidateDao;
import net.avalith.elections.models.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CandidateServiceImpl implements ICandidateService{

	@Autowired
	private ICandidateDao candidateDao;

	@Override
	@Transactional(readOnly=true)
	public List<Candidate> findAll() {
		return (List<Candidate>)candidateDao.findAll();
	}

	@Override
	@Transactional
	public Candidate save(Candidate candidate) {
		return candidateDao.save(candidate);
	}

	@Override
	@Transactional(readOnly=true)
	public Candidate findById(Long id) {
		return candidateDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		candidateDao.deleteById(id);

	}
	

}
