package net.avalith.elections.controllers;

import net.avalith.elections.entities.BodyMessage;
import net.avalith.elections.entities.BodyVote;
import net.avalith.elections.models.Vote;
import net.avalith.elections.service.VoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class VoteController {
    @Autowired
    private VoteServiceImpl voteService;

    @PostMapping("/election/{id_election}/vote")
    public ResponseEntity<?> voteResponse(@PathVariable(name = "id_election") Long idElection, @RequestHeader("USER_ID") String idUser, @Valid @RequestBody BodyVote bodyVote, BindingResult result){

        Vote vote = voteService.createVote(idElection, idUser, bodyVote, result);
        return ResponseEntity.ok(new BodyMessage("gracias por votar"));

    }
}
