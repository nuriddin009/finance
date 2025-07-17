package uz.banking.finance.repository.impl;


import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import uz.banking.finance.model.template.MBaseObject;
import uz.banking.finance.repository.BaseRepository;


import java.util.List;
import java.util.UUID;

public class BaseRepositoryImpl<T extends MBaseObject> extends SimpleJpaRepository<T, UUID> implements BaseRepository<T> {

    private final Specification<T> isNotDeletedSpecification = (root, query, cb) -> cb.equal(root.get("deleted"), false);

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Transactional
    @Override
    public T findByIdAndDeletedFalse(UUID id) {
        T entity = findById(id).orElse(null);
        return (entity != null && !entity.isDeleted()) ? entity : null;
    }

    @Transactional
    @Override
    public T trash(UUID id) {
        T entity = findById(id).orElse(null);
        if (entity != null) {
            entity.setDeleted(true);
            save(entity);
        }
        return entity;
    }

    @Override
    public List<T> findAllDeletedFalse() {
        return findAll(isNotDeletedSpecification);
    }

    @Override
    public Page<T> findAllDeletedFalse(Pageable pageable) {
        return findAll(isNotDeletedSpecification, pageable);
    }

    @Override
    public List<T> trashList(List<UUID> ids) {
        return ids.stream().map(this::trash).toList();
    }
}