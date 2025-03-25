package uz.aloqabank.template.repository;

import uz.aloqabank.template.dto.filter.TransactionFilter;
import uz.aloqabank.template.model.MTransaction;

import java.util.List;

public interface TransactionCustomRepository {
    List<MTransaction> getList(TransactionFilter filter);

    Long getCount(TransactionFilter filter);
}
