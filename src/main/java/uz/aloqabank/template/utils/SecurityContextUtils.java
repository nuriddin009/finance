package uz.aloqabank.template.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import uz.aloqabank.template.dto.User;

import java.util.List;
import java.util.stream.Collectors;


@UtilityClass
public class SecurityContextUtils {

    public Long getUserId() {
        User user = getUser();
        return user != null ? user.getId() : null;
    }

    public String getToken() {
        User user = getUser();
        return user != null ? user.getToken() : null;
    }

    public String getUsername() {
        UserDetails user = getUser();
        return user != null ? user.getUsername() : "system";
    }

    public String[] getRolesAsArray() {
        List<String> roles = getRoles();

        if (CollectionUtils.isEmpty(roles)) {
            return null;
        }
        return roles.toArray(new String[]{});
    }

    public List<String> getRoles() {
        UserDetails user = getUser();

        if (user == null) {
            return null;
        }
        return getUser().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    public User getUser() {
        SecurityContext context = getContext();
        if (context == null) {
            return null;
        }

        if (context.getAuthentication().getPrincipal() instanceof User) {
            return (User) context.getAuthentication().getPrincipal();
        }
        return null;
    }

    SecurityContext getContext() {
        SecurityContext context = SecurityContextHolder.getContext();

        if (context == null || context.getAuthentication() == null) {
            return null;
        }
        return context;
    }

    public static boolean hasRole(String role) {
        User currentUser = getUser();

        if (currentUser == null || currentUser.getAuthorities().isEmpty()) return false;

        return currentUser.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(role));
    }
}
