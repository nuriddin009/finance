package uz.banking.finance.controller.request;

import lombok.Getter;
import lombok.Setter;
import uz.banking.finance.constants.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class TransactionRequest {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal amount;
    private TransactionType transactionType;
}
