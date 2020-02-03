package net.avalith.elections.controllers;

import net.avalith.elections.entities.BodyElection;
import net.avalith.elections.entities.BodyElectionCandidateResults;
import net.avalith.elections.models.Candidate;
import net.avalith.elections.models.Election;
import net.avalith.elections.models.ElectionCandidate;
import net.avalith.elections.models.Usuario;
import net.avalith.elections.service.CandidateServiceImpl;
import net.avalith.elections.service.ElectionCandidateServiceImpl;
import net.avalith.elections.service.ElectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/election")
public class ElectionController {
    @Autowired
    private ElectionServiceImpl electionService;

    @Autowired
    private ElectionCandidateServiceImpl entidadCandidateService;

    @Autowired
    private CandidateServiceImpl candidateService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody BodyElection election, BindingResult result) {
        return electionService.createElection(election, result);
    }

    @GetMapping("{id_election}")
    public BodyElectionCandidateResults results(@PathVariable Long id_election) {
        return electionService.electionResults(id_election);
    }
}
