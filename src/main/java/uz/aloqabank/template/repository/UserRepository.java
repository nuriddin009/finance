package uz.aloqabank.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.aloqabank.template.model.MUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MUser, Long> {
    Optional<MUser> findByUsername(String username);

    Optional<MUser> findByUsernameAndDeletedFalse(String username);

    boolean existsByIdAndUsername(Long id, String username);

    boolean existsByUsername(String username);
}
