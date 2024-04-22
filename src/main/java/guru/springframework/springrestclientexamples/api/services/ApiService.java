package guru.springframework.springrestclientexamples.api.services;

import guru.springframework.springrestclientexamples.api.domain.Root;
import guru.springframework.springrestclientexamples.api.domain.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author john
 * @since 19/04/2024
 */
public interface ApiService {

    List<User> getUsers(Integer page);
    Flux<User> getUsers(Mono<Integer> page);

}
