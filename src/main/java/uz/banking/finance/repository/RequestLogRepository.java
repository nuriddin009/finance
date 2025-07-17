package uz.banking.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.banking.finance.model.MRequestLog;

import java.util.UUID;

public interface RequestLogRepository extends JpaRepository<MRequestLog, UUID> {
}
