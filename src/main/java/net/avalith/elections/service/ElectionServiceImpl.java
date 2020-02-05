package net.avalith.elections.service;

import net.avalith.elections.dao.IElectionCandidateDao;
import net.avalith.elections.dao.IElectionDao;
import net.avalith.elections.entities.BodyCandidate;
import net.avalith.elections.entities.BodyElection;
import net.avalith.elections.entities.BodyElectionCandidateResults;
import net.avalith.elections.models.Candidate;
import net.avalith.elections.models.Election;
import net.avalith.elections.models.ElectionCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ElectionServiceImpl {
    @Autowired
    CandidateServiceImpl candidateService;

    @Autowired
    ElectionCandidateServiceImpl electionCandidateService;

    @Autowired
    private IElectionDao electionDao;

    @Autowired
    private IElectionCandidateDao electionCandidateDao;

    @Transactional(readOnly = true)
    public List<Election> findAll() {
        return (List<Election>) electionDao.findAll();
    }

    @Transactional
    public Election save(Election election) {
        return electionDao.save(election);
    }

    public Election findById(Long id) {
        return electionDao.findById(id).orElseThrow(ArithmeticException::new);
    }

    @Transactional
    public void delete(Long id) {
        electionDao.deleteById(id);

    }

    public Election createElection(BodyElection election, BindingResult result){
        Election electionNew;


        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"errors");
        }

        try {

            if (election.getEndDate().compareTo(election.getStartDate()) == 1) {
                electionNew = new Election();
                electionNew.setElectionCandidates(new ArrayList<>());
                electionNew.setStartDate(election.getStartDate());
                electionNew.setEndDate(election.getEndDate());
                electionNew = this.save(electionNew);

                if (election.getElectionCandidates().size() > 0) {
                    List<ElectionCandidate> listexc = new ArrayList<>();

                    for (Long aux : election.getElectionCandidates()) {
                        ElectionCandidate exc = new ElectionCandidate();
                        Candidate candidate = candidateService.findById(aux);
                        exc.setCandidate(candidate);
                        exc.setElection(electionNew);
                        electionCandidateService.save(exc);
                        electionNew.getElectionCandidates().add(exc);
                        listexc.add(exc);
                    }
                    electionNew.setElectionCandidates(listexc);
                    electionNew = this.save(electionNew);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"La fecha de inicio debe ser menor a la de final");
            }


        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error al realizar el insert en la base de datos");
        }

        return electionNew;
    }

    public BodyElectionCandidateResults electionResults(Long electionId){
        Election election = this.findById(electionId);
        BodyElectionCandidateResults ecResults = new BodyElectionCandidateResults();
        BodyCandidate bodyCandidate = null;
        ecResults.setId_election(election.getId());
        int count= 0;

        for(int i = 0; i < election.getElectionCandidates().size(); i++){
            bodyCandidate = new BodyCandidate();
            bodyCandidate.setName(election.getElectionCandidates().get(i).getCandidate().getName());
            bodyCandidate.setLastName(election.getElectionCandidates().get(i).getCandidate().getLastname());
            bodyCandidate.setIndividualVotes(election.getElectionCandidates().get(i).getCountVotes());
            ecResults.getCandidates().add(bodyCandidate);
            count = count + election.getElectionCandidates().get(i).getCountVotes();
        }
        ecResults.setTotal_votes(count);
        return ecResults;

    }

    public List<Election> getActiveElections() {
        return this.electionDao.getActiveElections();
    }

}