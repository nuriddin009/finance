package uz.aloqabank.template.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uz.aloqabank.template.constants.ResponseStatus;
import uz.aloqabank.template.controller.request.TransactionRequest;
import uz.aloqabank.template.dto.ListResult;
import uz.aloqabank.template.dto.Response;
import uz.aloqabank.template.dto.SelectItem;
import uz.aloqabank.template.dto.Transaction;
import uz.aloqabank.template.dto.filter.TransactionFilter;
import uz.aloqabank.template.exception.ExceptionHandler;
import uz.aloqabank.template.service.TransactionService;

import java.util.List;

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
    public Response<Transaction> details(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionDetail(id);
        return Response.<Transaction>ok().setPayload(transaction);
    }

    @GetMapping("suggestions")
    public Response<List<SelectItem>> getTransactionSuggestionList(TransactionFilter filter) {
        List<SelectItem> transactions = transactionService.getSuggestionList(filter);
        return Response.<List<SelectItem>>ok().setPayload(transactions);
    }

    @DeleteMapping("/delete/{id}")
    public Response<Transaction> deleteTransaction(@PathVariable Long id) {
        Transaction transaction = transactionService.deleteTransaction(id);
        return Response.<Transaction>ok().setPayload(transaction);
    }
}
