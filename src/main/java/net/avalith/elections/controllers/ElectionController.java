package net.avalith.elections.controllers;

import net.avalith.elections.entities.BodyElection;
import net.avalith.elections.models.Candidate;
import net.avalith.elections.models.Election;
import net.avalith.elections.models.ElectionCandidate;
import net.avalith.elections.service.ICandidateService;
import net.avalith.elections.service.IElectionCandidateService;
import net.avalith.elections.service.IElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ElectionController {
    @Autowired
    private IElectionService electionService;

    @Autowired
    private IElectionCandidateService entidadCandidateService;

    @Autowired
    private ICandidateService candidateService;

    @PostMapping("/election")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody BodyElection election, BindingResult result) {
        Election electionNew = null;
        //Election election = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {

            if(election.getEndDate().compareTo(election.getStartDate()) == 1) {
                electionNew = new Election();
                electionNew.setElectionCandidates(new ArrayList<>());
                electionNew.setStartDate(election.getStartDate());
                electionNew.setEndDate(election.getEndDate());
                electionNew = electionService.save(electionNew);

                if (election.getElectionCandidates().size() > 0) {
                    List<ElectionCandidate> listexc = new ArrayList<>();
                    for (Long aux : election.getElectionCandidates()) {
                        ElectionCandidate exc = new ElectionCandidate();
                        //Long numInLong = Long.valueOf(aux);
                        Candidate candidate = candidateService.findById(aux);
                        exc.setCandidate(candidate);
                        exc.setElection(electionNew);
                        //entidadCandidateService.save(exc);
                        entidadCandidateService.save(exc);
                        electionNew.getElectionCandidates().add(exc);
                        listexc.add(exc);
                    }
                    electionNew.setElectionCandidates(listexc);
                }
            }else{
                response.put("mensaje", "La fecha de inicio debe ser menor a la de final");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }


        } catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La eleccion ha sido creada con éxito!");
        response.put("election", electionNew.getId());
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }
}
