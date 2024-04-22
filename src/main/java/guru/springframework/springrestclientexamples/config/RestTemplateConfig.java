package guru.springframework.springrestclientexamples.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

/**
 * @author john
 * @since 19/04/2024
 */
@Configuration
public class RestTemplateConfig {

    private final Environment env;

    public RestTemplateConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.rootUri(env.getProperty("api.root.url")).build();
    }

}
