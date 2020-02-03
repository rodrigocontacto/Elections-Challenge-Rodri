package net.avalith.elections.service;

import net.avalith.elections.dao.ICandidateDao;
import net.avalith.elections.dao.IElectionCandidateDao;
import net.avalith.elections.models.Candidate;
import net.avalith.elections.models.ElectionCandidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class CandidateServiceImpl{

	@Autowired
	private ICandidateDao candidateDao;

	@Autowired
	private IElectionCandidateDao electionCandidateDao;

	@Transactional(readOnly=true)
	public List<Candidate> findAll() {
		return candidateDao.findAll();
	}

	@Transactional
	public Candidate save(Candidate candidate) {
		return candidateDao.save(candidate);
	}

	@Transactional(readOnly=true)
	public Candidate findById(Long id) {
		return candidateDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"no existe el candidate"));
	}

	@Transactional
	public void delete(Long id) {
		candidateDao.deleteById(id);

	}

	public ResponseEntity<?> createCandidate(@Valid @RequestBody Candidate candidate, BindingResult result){
		Candidate candidateNew = null;
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
			candidateNew = new Candidate();
			candidateNew = this.save(candidate);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El candidato ha sido creado con éxito!");
		response.put("candidate", candidateNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

}
