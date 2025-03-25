package uz.aloqabank.template.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.aloqabank.template.exception.ExceptionHandler;
import uz.aloqabank.template.model.MUser;
import uz.aloqabank.template.service.UserService;

@Slf4j
@Service
public class AppUserDetailsService implements UserDetailsService, ExceptionHandler {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MUser mUser = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user with username: " + username + " does not exist."));

        if (mUser.isDeleted()) {
            throw new UsernameNotFoundException("user with username: " + username + " is not active.");
        }
        return userService.getById(mUser.getId());
    }
}
