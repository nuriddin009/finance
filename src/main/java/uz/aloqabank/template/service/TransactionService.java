package uz.aloqabank.template.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import uz.aloqabank.template.constants.ResponseStatus;
import uz.aloqabank.template.controller.request.TransactionRequest;
import uz.aloqabank.template.dto.ListResult;
import uz.aloqabank.template.dto.SelectItem;
import uz.aloqabank.template.dto.Transaction;
import uz.aloqabank.template.dto.filter.TransactionFilter;
import uz.aloqabank.template.exception.ExceptionHandler;
import uz.aloqabank.template.mapper.TransactionMapper;
import uz.aloqabank.template.model.MTransaction;
import uz.aloqabank.template.repository.TransactionRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService implements ExceptionHandler {
    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    public ListResult<Transaction> getList(TransactionFilter filter) {
        Long count = transactionRepository.getCount(filter);
        List<Transaction> transactions = count > 0 ?
                transactionRepository.getList(filter)
                        .stream()
                        .map(transactionMapper::toDto)
                        .toList()
                : Collections.emptyList();

        return new ListResult<>(transactions, count);
    }

    public Transaction create(TransactionRequest request) {
        validation(request);

        MTransaction mTransaction = new MTransaction();
        transactionMapper.toEntity(request, mTransaction);

        //relational table

        transactionRepository.save(mTransaction);
        return getDetails(mTransaction);
    }

    public Transaction update(TransactionRequest request) {
        if (request.getId() == null) {
            exception("Transaction ID not found", ResponseStatus.BAD_REQUEST);
        } else if (!transactionRepository.existsByIdAndDeletedFalse(request.getId())) {
            exception("Transaction not found", ResponseStatus.NOT_FOUND);
        }
        validation(request);

        MTransaction mTransaction = transactionRepository.getReferenceById(request.getId());
        transactionMapper.toEntity(request, mTransaction);

        //relational table

        transactionRepository.save(mTransaction);
        return getDetails(mTransaction);
    }

    public Transaction getTransactionDetail(Long id) {
        if (!transactionRepository.existsByIdAndDeletedFalse(id)) {
            exception("Transaction not found", ResponseStatus.NOT_FOUND);
        }
        MTransaction mTransaction = transactionRepository.getReferenceById(id);
        return getDetails(mTransaction);
    }

    public List<SelectItem> getSuggestionList(TransactionFilter filter) {
        return transactionRepository.getList(filter)
                .stream()
                .map(transactionMapper::toSelectItem)
                .toList();
    }

    public Transaction deleteTransaction(Long id) {
        if (!transactionRepository.existsByIdAndDeletedFalse(id)) {
            exception("Transaction not found", ResponseStatus.NOT_FOUND);
        }
        MTransaction mTransaction = transactionRepository.getReferenceById(id);
        mTransaction.setDeleted(true);
        transactionRepository.save(mTransaction);
        return getDetails(mTransaction);
    }

    private Transaction getDetails(MTransaction mTransaction) {
        return transactionMapper.toDto(mTransaction);
    }

    private void validation(TransactionRequest request) {
        if (StringUtils.isBlank(request.getName())) {
            exception("Transaction name not found", ResponseStatus.BAD_REQUEST);
        }
    }
}
