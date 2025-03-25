package uz.aloqabank.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.aloqabank.template.model.MTransaction;

public interface TransactionRepository extends JpaRepository<MTransaction, Long>, TransactionCustomRepository {
    boolean existsByIdAndDeletedFalse(Long id);
}
