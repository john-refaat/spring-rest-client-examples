package guru.springframework.springrestclientexamples.api.services;

import guru.springframework.springrestclientexamples.api.domain.Root;
import guru.springframework.springrestclientexamples.api.domain.User;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author john
 * @since 19/04/2024
 */
@Service
public class ApiServiceImpl implements ApiService {

    private RestTemplate restTemplate;

    private final String baseUrl;

    public ApiServiceImpl(RestTemplate restTemplate, @Value("${api.root.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<User> getUsers(Integer page) {
        Root root = restTemplate.getForObject("/users?page=" + page, Root.class);
        return root.getData();
    }

    @Override
    public Flux<User> getUsers(Mono<Integer> page) {
        return WebClient.builder().baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build().get().uri("/users?page="+page.block()).retrieve().bodyToFlux(Root.class)
                .flatMapIterable(Root::getData);
    }
}
