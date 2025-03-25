package uz.aloqabank.template.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;
import uz.aloqabank.template.dto.filter.TransactionFilter;
import uz.aloqabank.template.model.MTransaction;
import uz.aloqabank.template.repository.TransactionCustomRepository;

import java.util.List;

public class TransactionCustomRepositoryImpl implements TransactionCustomRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<MTransaction> getList(TransactionFilter filter) {
        String sql = " SELECT tr.* FROM " + getTransactionBaseQuery(filter) + " ORDER BY tr.id DESC";
        return entityManager.createNativeQuery(sql, MTransaction.class)
                .setMaxResults(filter.getLimit())
                .setFirstResult(filter.getStart())
                .getResultList();
    }

    @Override
    public Long getCount(TransactionFilter filter) {
        var count = (Long) entityManager.createNativeQuery(" SELECT count(tr.id) FROM " + getTransactionBaseQuery(filter)).getSingleResult();
        return count != null ? count : 0;
    }

    private String getTransactionBaseQuery(TransactionFilter filter) {
        var sql = new StringBuilder(" transaction tr ");
        sql.append(" WHERE tr.deleted is not true ");

        if (StringUtils.isNotBlank(filter.getSearchKey())) {
            var searchKey = filter.getSearchKey().trim().toLowerCase();
            sql.append(" AND ( ");
            sql.append(" lower(tr.name) like '%").append(searchKey).append("%'");
            sql.append(")");
        }
        return sql.toString();
    }
}
