package net.avalith.elections.service;

import net.avalith.elections.dao.IElectionCandidateDao;
import net.avalith.elections.models.ElectionCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ElectionCandidateServiceImpl implements IElectionCandidateService {

    @Autowired
    private IElectionCandidateDao electionCandidateDao;

    @Override
    @Transactional(readOnly = true)
    public List<ElectionCandidate> findAll() {
        return (List<ElectionCandidate>) electionCandidateDao.findAll();
    }

    @Override
    @Transactional
    public ElectionCandidate save(ElectionCandidate electionCandidate) {
        return electionCandidateDao.save(electionCandidate);
    }

    @Override
    @Transactional(readOnly = true)
    public ElectionCandidate findById(Long id) {
        return electionCandidateDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        electionCandidateDao.deleteById(id);

    }
}