package net.avalith.elections.dao;


import net.avalith.elections.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICandidateDao extends JpaRepository<Candidate, Long> {

}
