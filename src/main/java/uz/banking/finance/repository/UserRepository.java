package uz.banking.finance.repository;

import uz.banking.finance.model.MUser;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends BaseRepository<MUser> {
    Optional<MUser> findByUsername(String username);

    Optional<MUser> findByUsernameAndDeletedFalse(String username);

    boolean existsByIdAndUsername(UUID id, String username);

    boolean existsByUsername(String username);
}
