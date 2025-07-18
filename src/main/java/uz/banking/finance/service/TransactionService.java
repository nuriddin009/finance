package uz.banking.finance.service;

import uz.banking.finance.controller.request.TransactionRequest;
import uz.banking.finance.dto.ListResult;
import uz.banking.finance.dto.SelectItem;
import uz.banking.finance.dto.Transaction;
import uz.banking.finance.dto.filter.TransactionFilter;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    ListResult<Transaction> getList(TransactionFilter filter);
    Transaction create(TransactionRequest request);
    Transaction update(TransactionRequest request);
    Transaction getTransactionDetail(UUID id);
    List<SelectItem> getSuggestionList(TransactionFilter filter);
    Transaction deleteTransaction(UUID id);
}
