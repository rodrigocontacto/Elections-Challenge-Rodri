package net.avalith.elections.models.dao;


import net.avalith.elections.models.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICandidateDao extends JpaRepository<Candidate, Long> {

}
