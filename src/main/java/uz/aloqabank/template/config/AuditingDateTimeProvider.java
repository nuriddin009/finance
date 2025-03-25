package uz.aloqabank.template.config;

import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Component("auditingDateTimeProvider")
public class AuditingDateTimeProvider implements DateTimeProvider {
    public AuditingDateTimeProvider() {
    }

    @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(LocalDateTime.now());
    }
}
