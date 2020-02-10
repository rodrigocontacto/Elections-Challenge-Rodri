package net.avalith.elections.dao;

import net.avalith.elections.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IVoteDao extends JpaRepository<Vote, Long> {


}
