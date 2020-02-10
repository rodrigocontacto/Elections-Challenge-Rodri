package net.avalith.elections.service;

import net.avalith.elections.models.User;
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

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class TestUserServiceImpl {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    @MockBean
    UserServiceImpl userMock;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createUserTest() {

        String uuid = "34b506d9-8eac-48a7-8192-c91699904190";
        User userNew = User.builder()
                .id(uuid)
                .nombre("Pepe")
                .apellido("Argento")
                .build();

        User userResponse = User.builder()
                .id(uuid)
                .nombre("Pepe")
                .apellido("Argento")
                .build();

        when(userMock.save(userNew)).thenReturn(userResponse);
        Assert.assertEquals(userResponse, userService.save(userNew));
    }
}
