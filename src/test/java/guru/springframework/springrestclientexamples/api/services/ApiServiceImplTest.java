package guru.springframework.springrestclientexamples.api.services;

import guru.springframework.springrestclientexamples.api.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author john
 * @since 19/04/2024
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiServiceImplTest {

    @Autowired
    ApiService apiService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getUsers() {
        List<User> users = apiService.getUsers(1);
        assertEquals(6, users.size());
    }

    @Test
    public void getUsersReactive() {
        Flux<User> users = apiService.getUsers(Mono.just(1));
        assertNotNull(users);
        assertEquals(6, users.collectList().block().size());
    }
}