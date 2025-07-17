package uz.banking.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.banking.finance.model.MAuthority;

public interface AuthorityRepository extends JpaRepository<MAuthority, String> {
    MAuthority findByCode(String code);
}
