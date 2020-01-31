package net.avalith.elections.service;

import net.avalith.elections.models.ElectionCandidate;

import java.util.List;

public interface IElectionCandidateService {
    public List<ElectionCandidate> findAll();

    public ElectionCandidate save(ElectionCandidate election);

    public ElectionCandidate findById(Long id);

    public void delete(Long id);

}
