package guru.springframework.springrestclientexamples.api.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author john
 * @since 19/04/2024
 */
@Getter
@Setter
public class User {
    public int id;
    public String email;
    public String first_name;
    public String last_name;
    public String avatar;


}