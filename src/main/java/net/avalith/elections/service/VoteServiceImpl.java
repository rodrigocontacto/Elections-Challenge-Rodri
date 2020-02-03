package net.avalith.elections.service;

import net.avalith.elections.dao.IElectionCandidateDao;
import net.avalith.elections.dao.IVoteDao;
import net.avalith.elections.entities.BodyVote;
import net.avalith.elections.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private UsuarioServiceImpl userService;

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

    public ResponseEntity<?> createVote(Long idElection, String idUser, BodyVote bodyVote, BindingResult result) {
        Election electionNew = null;
        Usuario user = null;
        Candidate electedCandidate = null;
        Vote vote = new Vote();
        ElectionCandidate electionCandidate = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            electionNew = electionService.findById(idElection);
            user = userService.findById(idUser);
            electedCandidate = candidateService.findById(bodyVote.getId());
            boolean existCandidateInElection = false;
            for(int i = 0; i < electionNew.getElectionCandidates().size(); i++){
                if(electedCandidate.getId().longValue() == electionNew.getElectionCandidates().get(i).getCandidate().getId()){
                    existCandidateInElection = true;
                    Long electionCandidateId = electionCandidateDao.getElectionCandidateId(electedCandidate.getId().longValue(), electionNew.getId());
                    electionCandidate = electionCandidateService.findById(electionCandidateId);
                }
            }

            if(existCandidateInElection && user != null ){
                if (this.hasNotVoted(user, electionNew)) {
                    electionCandidate.setCountVotes(electionCandidate.getCountVotes()+1);
                    vote.setElectionCandidate(electionCandidate);
                    vote.setUser(user);
                    this.save(vote);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya has votado en esta eleccion");
                }
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este usuario no se encuentra registrado");
            }

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Gracias por tu voto!");
        response.put("vote", vote.getId());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<?> createFakeVotes(Long electionId, Long candidateId, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        List<Usuario> users = userService.findFakes();
        BodyVote bodyVote = new BodyVote();
        bodyVote.setId(candidateId);
        for(Usuario user : users){
            this.createVote(electionId, user.getId(), bodyVote, result);
        }
        response.put("mensaje", "Votos generados correctamente");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    public boolean hasNotVoted(Usuario user, Election election){
        return user.getVotes().stream().noneMatch(it-> it.getElectionCandidate().getElection().getId() == election.getId());
    }
}
