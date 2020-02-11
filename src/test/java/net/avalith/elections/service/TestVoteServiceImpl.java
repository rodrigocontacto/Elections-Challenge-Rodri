package net.avalith.elections.service;

import net.avalith.elections.models.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class TestVoteServiceImpl {
    @Autowired
    VoteServiceImpl voteService;

    @Autowired
    @MockBean
    VoteServiceImpl voteMock;

    @Test
    public void createVoteTest(){
        String userId = "34b506d9-8eac-48a7-8192-c91699904190";
        User userResponse = User.builder()
                .id(userId)
                .nombre("Pepe")
                .apellido("Argento")
                .isFake(false)
                .votes(new ArrayList<>())
                .build();

        Candidate candidate = Candidate.builder()
                .id(Long.valueOf(1))
                .name("Mauricio")
                .lastname("Fernandez")
                .electionCandidates(new ArrayList<>())
                .build();

        Election election = Election.builder()
                .id(Long.valueOf(1))
                .electionCandidates(new ArrayList<>())
                .endDate(LocalDateTime.of(2020,2,27,19,0,0))
                .startDate(LocalDateTime.of(2020,3,31,19,0,0))
                .build();

        ElectionCandidate electionCandidateResponse = ElectionCandidate.builder()
                .id(Long.valueOf(1))
                .candidate(candidate)
                .election(election)
                .votes(new ArrayList<>())
                .build();

        candidate.getElectionCandidates().add(electionCandidateResponse);
        election.getElectionCandidates().add(electionCandidateResponse);

        Vote voteNew = Vote.builder()
                .user(userResponse)
                .electionCandidate(electionCandidateResponse)
                .build();

        Vote voteResponse = Vote.builder()
                .id(Long.valueOf(1))
                .user(userResponse)
                .electionCandidate(electionCandidateResponse)
                .build();

        when(voteMock.save(voteNew)).thenReturn(voteResponse);

        Vote voteExpected= voteMock.save(voteNew);

        Assert.assertEquals(voteExpected , voteMock.save(voteNew));
    }
}
