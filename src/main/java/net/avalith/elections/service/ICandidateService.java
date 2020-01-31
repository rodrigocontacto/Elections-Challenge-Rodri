package net.avalith.elections.service;

import net.avalith.elections.models.Candidate;

import java.util.List;

public interface ICandidateService {
	public List<Candidate> findAll();

	public Candidate save(Candidate candidate);

	public Candidate findById(Long id);

	public void delete(Long id);

}
