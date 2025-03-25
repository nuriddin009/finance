package uz.aloqabank.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.aloqabank.template.model.MAuthority;

public interface AuthorityRepository extends JpaRepository<MAuthority, String> {
    MAuthority findByCode(String code);
}
