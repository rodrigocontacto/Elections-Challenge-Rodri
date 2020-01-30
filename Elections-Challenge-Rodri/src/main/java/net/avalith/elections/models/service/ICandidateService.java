package net.avalith.elections.models.service;

import net.avalith.elections.models.entity.Candidate;

import java.util.List;

public interface ICandidateService {
	public List<Candidate> findAll();

	public Candidate save(Candidate candidate);

	public Candidate findById(Long id);

	public void delete(Long id);

}
