package net.avalith.elections.controllers;

import net.avalith.elections.entities.BodyVote;
import net.avalith.elections.models.Vote;
import net.avalith.elections.service.VoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class VoteController {
    @Autowired
    private VoteServiceImpl voteService;

    @PostMapping("/election/{id_election}/vote")
    public ResponseEntity<?> voteResponse(@PathVariable(name = "id_election") Long idElection, @RequestHeader("USER_ID") String idUser, @Valid @RequestBody BodyVote bodyVote, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        try {
            Vote vote = voteService.createVote(idElection, idUser, bodyVote, result);
            response.put("mensaje", "Gracias por tu voto!");
            response.put("vote", vote.getId());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        }catch (ResponseStatusException e){
            response.put("errors", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

    }
}
