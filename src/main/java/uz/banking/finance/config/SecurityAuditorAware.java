package uz.banking.finance.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import uz.banking.finance.utils.SecurityContextUtils;

import java.util.Optional;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component("securityAuditorAware")
public class SecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityContextUtils.getUsername());
    }
}
