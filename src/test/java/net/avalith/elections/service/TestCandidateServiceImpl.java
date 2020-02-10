package net.avalith.elections.service;

import net.avalith.elections.entities.CandidateTest;
import net.avalith.elections.models.Candidate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class TestCandidateServiceImpl {
    @Autowired
    CandidateServiceImpl candidateService;

    @Autowired
    @MockBean
    CandidateServiceImpl candidateMock;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void createCandidateTest(){

        Candidate candidate = Candidate.builder()
                .name("Mauricio")
                .lastname("Fernandez")
                .build();

        Candidate candidateSaved = Candidate.builder()
                .id(Long.valueOf(1))
                .name("Mauricio")
                .lastname("Fernandez")
                .build();

        CandidateTest expected = new CandidateTest(candidateSaved.getId(),candidateSaved.getName(),candidateSaved.getLastname());

        when(candidateMock.save(candidate)).thenReturn(candidateSaved);
        Assert.assertEquals(expected.getId(),candidateService.save(candidate).getId());
    }

    @Test
    public void findAllCandidateTest(){
        List<Candidate> candidates = new ArrayList<Candidate>();
        Candidate candidate = new Candidate();
        candidate.setName("Alberto");
        candidate.setLastname("Fernandez");
        candidates.add(candidate);

        when(candidateMock.findAll()).thenReturn(candidates);
        List<Candidate> CandidateList = candidateService.findAll();
        Assert.assertEquals(1,CandidateList.size());
    }
}