package uz.banking.finance.repository;

import uz.banking.finance.dto.filter.TransactionFilter;
import uz.banking.finance.model.MTransaction;

import java.util.List;

public interface TransactionCustomRepository {
    List<MTransaction> getList(TransactionFilter filter);

    Long getCount(TransactionFilter filter);
}
