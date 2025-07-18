package uz.banking.finance.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.banking.finance.constants.ResponseStatus;
import uz.banking.finance.constants.TransactionType;
import uz.banking.finance.controller.request.TransactionRequest;
import uz.banking.finance.dto.ListResult;
import uz.banking.finance.dto.SelectItem;
import uz.banking.finance.dto.Transaction;
import uz.banking.finance.dto.filter.TransactionFilter;
import uz.banking.finance.exception.ExceptionHandler;
import uz.banking.finance.mapper.TransactionMapper;
import uz.banking.finance.model.MTransaction;
import uz.banking.finance.model.MUser;
import uz.banking.finance.repository.TransactionRepository;
import uz.banking.finance.repository.UserRepository;
import uz.banking.finance.service.TransactionService;
import uz.banking.finance.utils.SecurityContextUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService, ExceptionHandler {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;

    @Override
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

    @Transactional
    @Override
    public Transaction create(TransactionRequest request) {
        validation(request);

        MUser mUser = userRepository.findByIdAndDeletedFalse(SecurityContextUtils.getUserId());
        if (request.getTransactionType() == TransactionType.DEBIT && mUser.getBalance().compareTo(request.getAmount()) < 0) {
            exception("Insufficient balance", ResponseStatus.BAD_REQUEST);
        }

        BigDecimal newBalance = mUser.getBalance();
        if (request.getTransactionType() == TransactionType.DEBIT) {
            newBalance = newBalance.subtract(request.getAmount());
        } else if (request.getTransactionType() == TransactionType.CREDIT) {
            newBalance = newBalance.add(request.getAmount());
        }
        mUser.setBalance(newBalance);
        userRepository.save(mUser);
        MTransaction mTransaction = new MTransaction();
        transactionMapper.toEntity(request, mTransaction);

        mTransaction.setUser(mUser);
        transactionRepository.save(mTransaction);
        return getDetails(mTransaction);
    }

    @Transactional
    @Override
    public Transaction update(TransactionRequest request) {
        if (request.getId() == null) {
            exception("Transaction ID not found", ResponseStatus.BAD_REQUEST);
        } else if (transactionRepository.existsByIdAndDeletedFalse(request.getId())) {
            exception("Transaction not found", ResponseStatus.NOT_FOUND);
        }
        validation(request);

        MTransaction mTransaction = transactionRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));

        transactionMapper.toEntity(request, mTransaction);

        //relational table

        transactionRepository.save(mTransaction);
        return getDetails(mTransaction);
    }

    @Override
    public Transaction getTransactionDetail(UUID id) {
        if (transactionRepository.existsByIdAndDeletedFalse(id)) {
            exception("Transaction not found", ResponseStatus.NOT_FOUND);
        }
        MTransaction mTransaction = transactionRepository.getReferenceById(id);
        return getDetails(mTransaction);
    }

    @Override
    public List<SelectItem> getSuggestionList(TransactionFilter filter) {
        return transactionRepository.getList(filter)
                .stream()
                .map(transactionMapper::toSelectItem)
                .toList();
    }

    @Override
    public Transaction deleteTransaction(UUID id) {
        if (transactionRepository.existsByIdAndDeletedFalse(id)) {
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
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            exception("Transaction amount must be positive", ResponseStatus.BAD_REQUEST);
        }
        if (request.getTransactionType() == null) {
            exception("Transaction type is required", ResponseStatus.BAD_REQUEST);
        }
    }
}
