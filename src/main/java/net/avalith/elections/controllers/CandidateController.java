package net.avalith.elections.controllers;

import net.avalith.elections.models.Candidate;
import net.avalith.elections.service.CandidateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    @Autowired
    private CandidateServiceImpl candidateService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody Candidate candidate, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        Candidate candidateNew = candidateService.createCandidate(candidate, result);
        response.put("mensaje", "El candidato ha sido creado con éxito!");
        response.put("candidate", candidateNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public Candidate show(@PathVariable Long id) {

        return candidateService.findById(id);
    }
}
