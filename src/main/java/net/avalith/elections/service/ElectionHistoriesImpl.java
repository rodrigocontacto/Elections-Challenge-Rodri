package net.avalith.elections.service;

import net.avalith.elections.dao.IElectionHistoriesDao;
import net.avalith.elections.entities.BodyElectionCandidateResults;
import net.avalith.elections.models.Election;
import net.avalith.elections.models.ElectionCandidate;
import net.avalith.elections.models.ElectionHistories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

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
    public void save(ElectionHistories electionHistory){
        this.electionHistoryDao.save(electionHistory);
    }

    @Value("${TIME}")
    @Scheduled(fixedRate = 3600000)
    public void createHistory(){
        List<Election> elections = electionService.findAll();
        List<ElectionCandidate> electionCandidate = electionCandidateService.findAll();
        if(!elections.isEmpty() && !electionCandidate.isEmpty()) {// lista de election candidate
            ElectionHistories electionHistories;

            for (int x = 0; x < elections.size(); x++) {
                int i = 0;
                electionHistories = new ElectionHistories();
                electionHistories.setDate(Timestamp.from(Instant.now()));

                while (elections.get(i).getId() == electionCandidate.get(x).getElection().getId()) {
                    electionHistories.setElectionCandidates(new ArrayList<ElectionCandidate>());
                    electionHistories.getElectionCandidates().add(electionCandidate.get(x));
                    i++;
                }
                int totalVotes = 0;
                for (int c = 0; c < electionHistories.getElectionCandidates().size(); c++) {
                    totalVotes = totalVotes + electionHistories.getElectionCandidates().get(c).getCountVotes();

                }
                electionHistories.setVotes(totalVotes);
                electionHistories.setPercentage((float)totalVotes / (float)totalVotes * 100);
                electionHistoryDao.save(electionHistories);
            }
        }

    }
}
