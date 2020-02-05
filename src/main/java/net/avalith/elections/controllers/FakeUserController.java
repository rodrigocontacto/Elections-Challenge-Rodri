package net.avalith.elections.controllers;

import net.avalith.elections.entities.BodyFakeUserVote;
import net.avalith.elections.entities.BodyFakeUsersCount;
import net.avalith.elections.service.UserServiceImpl;
import net.avalith.elections.service.VoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/non-alcoholic-beer")
public class FakeUserController {

    @Autowired
    private UserServiceImpl usuarioService;

    @Autowired
    private VoteServiceImpl voteService;

    @PostMapping("")
    public ResponseEntity<?> createFakeUser(@RequestBody BodyFakeUsersCount bodyFakeUsersCount){
        Map<String, Object> response = new HashMap<>();
        usuarioService.createFakeUser(bodyFakeUsersCount);
        response.put("mensaje", "Los usuarios fake han sido creados con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PostMapping("{id}")
    public ResponseEntity<?> vote(@PathVariable("id")Long electionId, @RequestBody BodyFakeUserVote candidate, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        voteService.createFakeVotes(electionId, candidate, result);
        response.put("mensaje", "Votos generados correctamente");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
