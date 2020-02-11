package net.avalith.elections.service;

import net.avalith.elections.dao.IElectionCandidateDao;
import net.avalith.elections.models.ElectionCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ElectionCandidateServiceImpl {

    @Autowired
    private IElectionCandidateDao electionCandidateDao;

    @Autowired
    private CandidateServiceImpl candidateService;

    @Transactional(readOnly = true)
    public List<ElectionCandidate> findAll() {
        return electionCandidateDao.findAll();
    }

    @Transactional
    public ElectionCandidate save(ElectionCandidate electionCandidate) {
        return electionCandidateDao.save(electionCandidate);
    }

    @Transactional(readOnly = true)
    public ElectionCandidate findById(Long id) {
        return electionCandidateDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"no existe el electioncandidate"));
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