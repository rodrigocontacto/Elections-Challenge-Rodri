package net.avalith.elections.dao;

import net.avalith.elections.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVoteDao extends JpaRepository<Vote, Long> {


}
