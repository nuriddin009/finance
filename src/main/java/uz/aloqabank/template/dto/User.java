package uz.aloqabank.template.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Setter
@Getter
public class User extends org.springframework.security.core.userdetails.User {
    private Long id;
    private String mac;
    private String firstName;
    private String lastName;
    private String token;

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
