package uz.banking.finance.dto;

import lombok.Getter;
import lombok.Setter;
import uz.banking.finance.constants.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Transaction {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private TransactionType transactionType;
}
