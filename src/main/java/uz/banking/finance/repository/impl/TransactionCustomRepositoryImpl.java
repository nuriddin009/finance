package uz.banking.finance.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;
import uz.banking.finance.dto.filter.TransactionFilter;
import uz.banking.finance.model.MTransaction;
import uz.banking.finance.repository.TransactionCustomRepository;

import java.util.List;

public class TransactionCustomRepositoryImpl implements TransactionCustomRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<MTransaction> getList(TransactionFilter filter) {
        String sql = buildBaseQuery(filter, false);
        return entityManager.createNativeQuery(sql, MTransaction.class)
                .setMaxResults(filter.getLimit())
                .setFirstResult(filter.getStart())
                .getResultList();
    }

    @Override
    public Long getCount(TransactionFilter filter) {
        String sql = buildBaseQuery(filter, true);
        Object count = entityManager.createNativeQuery(sql).getSingleResult();
        return count != null ? ((Number) count).longValue() : 0L;
    }

    private String buildBaseQuery(TransactionFilter filter, boolean isCount) {
        StringBuilder sql = new StringBuilder();
        if (isCount) {
            sql.append("SELECT COUNT(tr.id) ");
        } else {
            sql.append("SELECT tr.* ");
        }
        sql.append("FROM transactions tr WHERE tr.deleted IS NOT TRUE ");

        if (filter.getUserId() != null) {
            sql.append(" AND tr.user_id = '").append(filter.getUserId()).append("' ");
        }

        if (filter.getFrom() != null) {
            sql.append(" AND DATE(tr.created_at) >= '").append(filter.getFrom()).append("' ");
        }
        if (filter.getTo() != null) {
            sql.append(" AND DATE(tr.created_at) <= '").append(filter.getTo()).append("' ");
        }

        if (StringUtils.isNotBlank(filter.getSearchKey())) {
            String searchKey = filter.getSearchKey().trim().toLowerCase();
            sql.append(" AND (LOWER(tr.description) LIKE '%").append(searchKey).append("%') ");
        }

        if (!isCount) {
            sql.append(" ORDER BY tr.created_at DESC ");
        }

        return sql.toString();
    }
}
