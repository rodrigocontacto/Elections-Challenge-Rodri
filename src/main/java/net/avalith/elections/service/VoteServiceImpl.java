package net.avalith.elections.service;

import net.avalith.elections.dao.IElectionCandidateDao;
import net.avalith.elections.dao.IVoteDao;
import net.avalith.elections.entities.BodyFakeUserVote;
import net.avalith.elections.entities.BodyVote;
import net.avalith.elections.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VoteServiceImpl {

    @Autowired
    private IVoteDao voteDao;

    @Autowired
    private IElectionCandidateDao electionCandidateDao;

    @Autowired
    private ElectionServiceImpl electionService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CandidateServiceImpl candidateService;

    @Autowired
    private ElectionCandidateServiceImpl electionCandidateService;

    @Transactional(readOnly=true)
    public List<Vote> findAll() {
        return (List<Vote>)voteDao.findAll();
    }

    @Transactional
    public Vote save(Vote vote) {
        return voteDao.save(vote);
    }

    @Transactional(readOnly=true)
    public Vote findById(Long id) {
        return voteDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"no existe el voto"));
    }

    @Transactional
    public void delete(Long id) {
        voteDao.deleteById(id);

    }

    public Vote createVote(Long idElection, String idUser, BodyVote bodyVote, BindingResult result) {
        Election electionNew;
        User user;
        Candidate electedCandidate = new Candidate();
        Vote vote = new Vote();
        ElectionCandidate electionCandidate = null;

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"error");
        }

        try {
            electionNew = electionService.findById(idElection);
            user = userService.findById(idUser);
            boolean existCandidateInElection = false;
            for(int i= 0; i< electionNew.getElectionCandidates().size(); i++){
                if(electionNew.getElectionCandidates().get(i).getCandidate().getId() == bodyVote.getId()){
                    electedCandidate = electionNew.getElectionCandidates().get(i).getCandidate();
                    electionCandidate = electionNew.getElectionCandidates().get(i);
                    existCandidateInElection = true;
                }
            }

            if(existCandidateInElection){
                if (this.hasNotVoted(user, electionNew)) {
                    electionCandidate.setCountVotes(electionCandidate.getCountVotes()+1);
                    vote.setElectionCandidate(electionCandidate);
                    vote.setUser(user);
                    this.save(vote);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya has votado en esta eleccion");
                }
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ese candidato no esta en la eleccion");
            }

        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"error al insertar en BD");
        }

        return vote;
    }

    public void createFakeVotes(Long electionId, BodyFakeUserVote candidate, BindingResult result){

        List<User> users = userService.findFakes();
        BodyVote bodyVote = new BodyVote();
        bodyVote.setId(candidate.getId_candidate());
        for(User user : users){
            this.createVote(electionId, user.getId(), bodyVote, result);
        }

    }

    public boolean hasNotVoted(User user, Election election){
        return user.getVotes().stream().noneMatch(it-> it.getElectionCandidate().getElection().getId() == election.getId());
    }
}
