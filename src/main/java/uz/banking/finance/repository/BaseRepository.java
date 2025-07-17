package uz.banking.finance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import uz.banking.finance.model.template.MBaseObject;


import java.util.List;
import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<T extends MBaseObject> extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {
    T findByIdAndDeletedFalse(UUID id);
    T trash(UUID id);
    List<T> trashList(List<UUID> ids);
    List<T> findAllDeletedFalse();
    Page<T> findAllDeletedFalse(Pageable pageable);
}