package net.avalith.elections.controllers;

import net.avalith.elections.entities.BodyMessage;
import net.avalith.elections.models.Candidate;
import net.avalith.elections.service.CandidateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    @Autowired
    private CandidateServiceImpl candidateService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody Candidate candidate, BindingResult result) {

        Candidate candidateNew = candidateService.createCandidate(candidate, result);
        return ResponseEntity.ok(new BodyMessage("EL candidato ha sido creado con exito"));
    }

    @GetMapping("{id}")
    public Candidate show(@PathVariable Long id) {

        return candidateService.findById(id);
    }
}
