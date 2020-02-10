package net.avalith.elections.dao;

import net.avalith.elections.models.ElectionHistories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IElectionHistoriesDao extends JpaRepository<ElectionHistories, Long> {
}
