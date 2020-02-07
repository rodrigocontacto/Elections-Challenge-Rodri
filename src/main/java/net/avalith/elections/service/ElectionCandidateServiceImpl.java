package net.avalith.elections.service;

import net.avalith.elections.dao.IElectionCandidateDao;
import net.avalith.elections.models.ElectionCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ElectionCandidateServiceImpl {

    @Autowired
    private IElectionCandidateDao electionCandidateDao;

    @Autowired
    private CandidateServiceImpl candidateService;

    @Transactional(readOnly = true)
    public List<ElectionCandidate> findAll() {
        return (List<ElectionCandidate>) electionCandidateDao.findAll();
    }

    @Transactional
    public ElectionCandidate save(ElectionCandidate electionCandidate) {
        return electionCandidateDao.save(electionCandidate);
    }

    @Transactional(readOnly = true)
    public ElectionCandidate findById(Long id) {
        return electionCandidateDao.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Long id) {
        electionCandidateDao.deleteById(id);
    }

    public List<Long> getCandidatesByElectionId(Long electionId){

        return electionCandidateDao.getCandidatesByElectionId(electionId);
    }

    public Long getElectionCandidateId(Long electionId, Long candidateId){

        return electionCandidateDao.getElectionCandidateId(candidateId, electionId);
    }


}