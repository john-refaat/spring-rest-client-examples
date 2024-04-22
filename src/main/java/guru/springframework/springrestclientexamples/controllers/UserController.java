package guru.springframework.springrestclientexamples.controllers;

import guru.springframework.springrestclientexamples.api.services.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author john
 * @since 20/04/2024
 */
@Slf4j
@Controller
public class UserController {
    private ApiService apiService;
    public UserController(ApiService apiService) {
        this.apiService = apiService;
    }
   /* @GetMapping("/users/list")
    public String getUsers(@RequestParam("page") Integer pageNumber, Model model ) {
         model.addAttribute("users", apiService.getUsers(pageNumber));
         model.addAttribute("page", pageNumber==null ? 1 : pageNumber);
         return "userlist";
    }*/

    // Reactive Implementation
    @GetMapping("/users/list")
    public String getUsers(@RequestParam("page") Integer pageNumber, Model model) {
        model.addAttribute("users", apiService.getUsers(Mono.just(pageNumber)));
        model.addAttribute("page",Mono.just(pageNumber));
        return "userlist";
    }
}
