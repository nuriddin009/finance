package uz.banking.finance.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uz.banking.finance.constants.ResponseStatus;
import uz.banking.finance.controller.request.TransactionRequest;
import uz.banking.finance.dto.ListResult;
import uz.banking.finance.dto.Response;
import uz.banking.finance.dto.SelectItem;
import uz.banking.finance.dto.Transaction;
import uz.banking.finance.dto.filter.TransactionFilter;
import uz.banking.finance.exception.ExceptionHandler;
import uz.banking.finance.service.TransactionService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/transaction")
public class TransactionController implements ExceptionHandler {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/list")
    public Response<ListResult<Transaction>> getTransactionList(TransactionFilter filter) {
        return Response.<ListResult<Transaction>>ok().setPayload(transactionService.getList(filter));
    }

    @PostMapping("/create")
    public Response<Transaction> createTransaction(@RequestBody TransactionRequest request) {

        if (request == null) {
            exception("Request not found", ResponseStatus.BAD_REQUEST);
        }
        assert request != null;
        request.setId(null);
        Transaction transaction = transactionService.create(request);
        return Response.<Transaction>ok().setPayload(transaction);
    }

    @PutMapping("/update")
    public Response<Transaction> updateTransaction(@RequestBody TransactionRequest request) {
        Transaction transaction = transactionService.update(request);
        return Response.<Transaction>ok().setPayload(transaction);
    }

    @GetMapping("/detail/{id}")
    public Response<Transaction> details(@PathVariable UUID id) {
        Transaction transaction = transactionService.getTransactionDetail(id);
        return Response.<Transaction>ok().setPayload(transaction);
    }

    @GetMapping("suggestions")
    public Response<List<SelectItem>> getTransactionSuggestionList(TransactionFilter filter) {
        List<SelectItem> transactions = transactionService.getSuggestionList(filter);
        return Response.<List<SelectItem>>ok().setPayload(transactions);
    }

    @DeleteMapping("/delete/{id}")
    public Response<Transaction> deleteTransaction(@PathVariable UUID id) {
        Transaction transaction = transactionService.deleteTransaction(id);
        return Response.<Transaction>ok().setPayload(transaction);
    }
}
