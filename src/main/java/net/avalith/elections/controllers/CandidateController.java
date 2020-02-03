package net.avalith.elections.controllers;

import net.avalith.elections.models.Candidate;
import net.avalith.elections.models.Usuario;
import net.avalith.elections.service.CandidateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    @Autowired
    private CandidateServiceImpl candidateService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody Candidate candidate, BindingResult result) {

        return candidateService.createCandidate(candidate, result);
    }

    @GetMapping("{id}")
    public Candidate show(@PathVariable Long id) {

        return candidateService.findById(id);
    }
}
