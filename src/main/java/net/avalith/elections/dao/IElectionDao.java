package net.avalith.elections.dao;

import net.avalith.elections.models.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IElectionDao extends JpaRepository<Election, Long> {
}
