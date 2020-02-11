package net.avalith.elections.service;

import net.avalith.elections.dao.IElectionHistoriesDao;
import net.avalith.elections.models.Election;
import net.avalith.elections.models.ElectionHistories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectionHistoriesImpl {
    Logger logger = LoggerFactory.getLogger(ElectionHistoriesImpl.class);

    @Autowired
    private IElectionHistoriesDao electionHistoryDao;

    @Autowired
    private VoteServiceImpl voteService;

    @Autowired
    private ElectionServiceImpl electionService;

    @Autowired
    private CandidateServiceImpl candidateService;

    @Autowired
    private ElectionCandidateServiceImpl electionCandidateService;

    @Transactional
    public void save(ElectionHistories electionHistory) {
        this.electionHistoryDao.save(electionHistory);
    }

    @Value("${TIME}")
    @Scheduled(fixedRate = 30000)
    public void saveHistory() {

        electionHistoryDao.saveAll(createHistory());
    }

    private List<ElectionHistories> createHistory() {
        List<Election> elections = electionService.getActiveElections();

        return elections.stream().map(
                election -> {
                    Integer total = getTotalVotes(election);
                    return getWinner(election, total);
                }
        ).collect(Collectors.toList());
    }

    private ElectionHistories getWinner(Election election, Integer total) {

        return election.getElectionCandidates().stream().map(
                electionCandidate -> ElectionHistories.builder().date(Timestamp.from(Instant.now()))
                        .election(electionCandidate.getElection())
                        .votes(electionCandidate.getCountVotes())
                        .percentage((float) (electionCandidate.getCountVotes() / total)).build()
        ).max(
                Comparator.comparingInt(ElectionHistories::getVotes)
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"no se encontro un ganador"));
    }

    private Integer getTotalVotes(Election election) {

        return election.getElectionCandidates().stream()
                .map(electionCandidate -> electionCandidate.getCountVotes()).reduce(
                        0, (a, b) -> Integer.sum(a, b));
    }
}