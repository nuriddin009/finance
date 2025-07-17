package uz.banking.finance.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

@Setter
@Getter
public class User extends org.springframework.security.core.userdetails.User {
    private UUID id;
    private String mac;
    private String firstName;
    private String lastName;
    private String token;

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
