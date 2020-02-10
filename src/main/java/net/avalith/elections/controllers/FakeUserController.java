package net.avalith.elections.controllers;

import net.avalith.elections.entities.BodyFakeUserVote;
import net.avalith.elections.entities.BodyFakeUsersCount;
import net.avalith.elections.entities.BodyMessage;
import net.avalith.elections.service.UserServiceImpl;
import net.avalith.elections.service.VoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/non-alcoholic-beer")
public class FakeUserController {

    @Autowired
    private UserServiceImpl usuarioService;

    @Autowired
    private VoteServiceImpl voteService;


    @PostMapping("")
    public ResponseEntity<?> createFakeUser(@RequestBody BodyFakeUsersCount bodyFakeUsersCount){

        usuarioService.createFakeUser(bodyFakeUsersCount);
        return ResponseEntity.ok(new BodyMessage("Los usuarios fake han sido creados con éxito!"));
    }

    @PostMapping("{id}")
    public ResponseEntity<?> vote(@PathVariable("id")Long electionId, @RequestBody BodyFakeUserVote candidate, BindingResult result){

        voteService.createFakeVotes(electionId, candidate, result);
        return ResponseEntity.ok(new BodyMessage("gracias por votar"));
    }
}