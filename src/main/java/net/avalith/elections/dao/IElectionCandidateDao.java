package net.avalith.elections.dao;

import net.avalith.elections.models.ElectionCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IElectionCandidateDao extends JpaRepository<ElectionCandidate, Long> {
}
