package net.avalith.elections.service;

import net.avalith.elections.models.Election;
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

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class TestElectionServiceImpl {
    @Autowired
    ElectionServiceImpl electionService;

    @Autowired
    @MockBean
    ElectionServiceImpl electionMock;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createElectionTest(){

        Long id = Long.valueOf(1);

        Election electionActual = Election.builder()
                .startDate(LocalDateTime.of(2020,2,27,13,0,0))
                .endDate(LocalDateTime.of(2020,3,31,13,0,0))
                .electionCandidates(new ArrayList<>())
                .build();

        Election electionResponse = Election.builder()
                .id(id)
                .startDate(LocalDateTime.of(2020,2,27,13,0, 0))
                .endDate(LocalDateTime.of(2020,3,31,13,0,0))
                .electionCandidates(new ArrayList<>())
                .build();

        when(electionMock.save(electionActual)).thenReturn(electionResponse);

        Election expected = new Election(electionResponse.getId(),electionResponse.getStartDate(),electionResponse.getEndDate(),electionResponse.getElectionCandidates());

        Assert.assertEquals(expected,electionMock.save(electionActual));
    }
}
