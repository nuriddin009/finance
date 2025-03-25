package uz.aloqabank.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.aloqabank.template.model.MRequestLog;

public interface RequestLogRepository extends JpaRepository<MRequestLog, Long> {
}
