package net.avalith.elections.dao;

import net.avalith.elections.models.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IElectionDao extends JpaRepository<Election, Long> {
    @Query(value = "select * from elections where start_date < now() and end_date > now()", nativeQuery = true)
    List<Election> getActiveElections();
}
