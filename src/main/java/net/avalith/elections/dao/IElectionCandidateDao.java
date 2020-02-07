package net.avalith.elections.dao;

import net.avalith.elections.models.ElectionCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IElectionCandidateDao extends JpaRepository<ElectionCandidate, Long> {
    @Query(value = "SELECT c.id from elections_candidates ec inner join candidates c on c.id = ec.candidate_id  where ec.election_id = :id", nativeQuery = true)
    List<Long> getCandidatesByElectionId(@Param("id") Long id);

    @Query(value = "SELECT ec.id from election_candidate ec where ec.election_id = :election_id and ec.candidate_id = :candidate_id", nativeQuery = true)
    Long getElectionCandidateId(@Param("candidate_id") Long candidateId, @Param("election_id") Long electionId);
}
