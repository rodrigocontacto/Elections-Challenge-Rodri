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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
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



        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"errors");
        }

        try {
            if (election.getEndDate().compareTo(election.getStartDate()) == 1) {
                if (election.getElectionCandidates().size() > 0) {
                    final Election electionNew = new Election();
                    electionNew.setElectionCandidates(new ArrayList<>());
                    electionNew.setStartDate(election.getStartDate());
                    electionNew.setEndDate(election.getEndDate());
                    Election electionSaved = this.save(electionNew);

                    election.getElectionCandidates().stream()
                            .map(id->this.candidateService.findById(id))
                            .map(candidate -> ElectionCandidate.builder()
                            .candidate(candidate)
                            .election(electionSaved)
                            .votes(new ArrayList<>())
                            .build())
                            .forEach(electionCandidate -> this.electionCandidateService.save(electionCandidate));

                    return  this.save(electionSaved);

                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Debe tener candidatos");
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"La fecha de inicio debe ser menor a la de final");
            }


        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error al realizar el insert en la base de datos");
        }
    }

    public BodyElectionCandidateResults electionResults(Long electionId){
        Election election = this.findById(electionId);
        BodyElectionCandidateResults ecResults = new BodyElectionCandidateResults();
        ecResults.setIdElection(election.getId());

        List<BodyCandidate> bodyCandidates = election.getElectionCandidates().stream()
                .map( ec -> BodyCandidate.builder()
                        .individualVotes(ec.getVotes().size())
                        .name(ec.getCandidate().getName())
                        .lastName(ec.getCandidate().getLastname())
                        .build())
                .collect(Collectors.toList());

        return BodyElectionCandidateResults.builder()
                .idElection(electionId)
                .candidates(bodyCandidates)
                .totalVotes(election.getElectionCandidates().stream()
                        .mapToInt( ec -> ec.getVotes().size())
                        .sum()
                ).build();
    }

    public List<Election> getActiveElections() {
        return this.electionDao.getActiveElections();
    }

}