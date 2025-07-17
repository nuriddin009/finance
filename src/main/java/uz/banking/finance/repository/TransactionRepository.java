package uz.banking.finance.repository;

import uz.banking.finance.model.MTransaction;

import java.util.UUID;

public interface TransactionRepository extends BaseRepository<MTransaction>, TransactionCustomRepository {
    boolean existsByIdAndDeletedFalse(UUID id);
}
